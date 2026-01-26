package test.biz;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.io.LocalInputFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/26
 */
@Slf4j
public class DataReader {

    public static List<UserPO> readFromParquet() {
        /**
         * 报错：
         *   java.io.FileNotFoundException: Hadoop bin directory does not exist: D:\Data\hadoop\bin
         * 可忽略。
         */
        System.setProperty("hadoop.home.dir", "D:\\Data\\hadoop");

        List<UserPO> data = new ArrayList<>();

        Configuration conf = new Configuration();

        Path path = Path.of(DataWriter.FILE_PATH);
        LocalInputFile file = new LocalInputFile(path);
        try {
            try (ParquetReader<GenericRecord> reader = AvroParquetReader
                    .<GenericRecord>builder(file)
                    .withConf(conf)
                    .build()
            ) {
                GenericRecord record;
                int recordCount = 0;
                while ((record = reader.read()) != null) {
                    UserPO klinePO = convertToPO(record);
                    data.add(klinePO);
                    recordCount++;
                }
                log.info("读取 Parquet 文件完成, 记录数 {}", recordCount);
            }
        } catch (IOException e) {
            log.error("读取 Parquet 文件失败", e);
        }
        return data;
    }

    /**
     * 将 GenericRecord 转换为 PO
     */
    private static UserPO convertToPO(GenericRecord record) {
        UserPO po = new UserPO();

        // 根据 Parquet 文件的 schema 进行字段映射，使用工具类安全访问字段

        po.setId(DataAccessUtils.getInteger(record, "id"));
        po.setName(DataAccessUtils.getString(record, "name"));
        po.setTimestamp(DataAccessUtils.getLong(record, "timestamp"));
        po.setProperty(DataAccessUtils.getBigDecimal(record, "property"));

        String updateTimeStr = DataAccessUtils.getString(record, "updateTime");
        if (updateTimeStr != null) {
            LocalDateTime time = LocalDateTime.parse(updateTimeStr);
            po.setUpdateTime(time);
        }

        po.setEx1(DataAccessUtils.getString(record, "ex1"));
        po.setEx2(DataAccessUtils.getString(record, "ex2"));

        return po;
    }


    /**
     * 创建 Hadoop 配置，支持 OSS 文件系统
     * <p/>
     * 使用
     * <pre>
     * 依赖：
     *
     * implementation group: 'org.apache.hadoop', name: 'hadoop-aliyun', version: '3.4.2'
     *
     *
     * Java:
     *
     * // 将相对路径转换为 OSS URI 格式: oss://bucket-name/path/to/file.parquet
     * String ossUri = "oss://" + parquetOssConfig.getOSS_BUCKET_NAME() + "/" + file;
     * org.apache.hadoop.fs.Path ossPath = new org.apache.hadoop.fs.Path(ossUri);
     *
     * try (ParquetReader<GenericRecord> reader = ParquetReader
     *      .<GenericRecord>builder(new AvroReadSupport<>(), ossPath)
     *      .withConf(conf)
     *      .build()
     * ) {
     *    ....
     * }
     * </pre>
     */
    @Deprecated(since = "用来参考：使用 aliyun 的配置")
    public static Configuration createHadoopConfiguration() {
        Configuration conf = new Configuration();
        try {
            // 配置 OSS 文件系统
            conf.set("fs.oss.impl", "org.apache.hadoop.fs.aliyun.oss.AliyunOSSFileSystem");
            conf.set("fs.oss.endpoint", "oss-cn-shenzhen-internal.aliyuncs.com");
            conf.set("fs.oss.accessKeyId", "xxxx1");
            conf.set("fs.oss.accessKeySecret", "xxxx2");

            // 设置 OSS 连接参数
            conf.setInt("fs.oss.connection.maximum", 256);
            conf.setInt("fs.oss.connection.timeout", 5000);

            // 配置 Parquet 读取参数：处理已废弃的 INT96 类型
            // INT96 通常用于时间戳，新版本 Parquet 已废弃，需要启用此标志以兼容旧文件
            conf.setBoolean("parquet.avro.readInt96AsFixed", true);
        } catch (Exception e) {
            log.error("创建 Hadoop 配置失败", e);
        }
        return conf;
    }

}
