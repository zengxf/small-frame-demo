package test.biz;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.io.LocalOutputFile;
import org.apache.parquet.io.OutputFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/26
 */
@Slf4j
public class DataWriter {

    public static final String FILE_PATH = "test-parquet.db";

    // 数据 Avro Schema
    public static final String SCHEMA_JSON = """
            {
              "type": "record",
              "name": "UserData",
              "namespace": "cn.zxf.avro",
              "fields": [
                {"name": "id", "type": "int"},
                {"name": "name", "type": "string"},
                {"name": "timestamp", "type": "long"},
                {"name": "property", "type": "double"},
                {"name": "updateTime", "type": ["null", "string"], "default": null},
                {"name": "ex1", "type": ["null", "string"], "default": null},
                {"name": "ex2", "type": ["null", "string"], "default": null}
              ]
            }
            """;

    public static final Schema SCHEMA;

    static {
        try {
            Schema.Parser parser = new Schema.Parser();
            SCHEMA = parser.parse(SCHEMA_JSON);
        } catch (Exception e) {
            throw new RuntimeException("初始化数据 Schema 失败", e);
        }
    }


    /**
     * 将数据写入 Parquet 文件
     */
    public static void writeToParquet(List<UserPO> data) {
        try {
            Path path = Path.of(FILE_PATH);
            Files.deleteIfExists(path);

            Configuration conf = new Configuration();
            OutputFile file = new LocalOutputFile(path);

            try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(file)
                    .withSchema(SCHEMA)
                    .withConf(conf)
                    .build()
            ) {
                for (UserPO item : data) {
                    GenericRecord record = buildGenericRecord(item);
                    writer.write(record);
                }
            }

            log.info("写入成功，文件在：{}", file.getPath());
        } catch (IOException e) {
            log.error("写入 Parquet 文件失败", e);
        }
    }


    /**
     * 将 PO 转换为 GenericRecord
     */
    private static GenericRecord buildGenericRecord(UserPO po) {
        GenericRecord record = new GenericData.Record(SCHEMA);

        record.put("id", po.getId());
        record.put("name", po.getName());
        record.put("timestamp", po.getTimestamp());
        record.put("property", po.getProperty());

        // 可选字段
        record.put("updateTime", po.getUpdateTime() != null ? po.getUpdateTime() : null);
        record.put("ex1", po.getEx1());
        record.put("ex2", po.getEx2());
        // record.put("ex21", po.getEx2()); // AvroRuntimeException: Not a valid schema field: ex21

        return record;
    }

}
