package cn.zxf.mybatis.test.xml_config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class XmlConfigSessionUtil {

    public static SqlSessionFactory sessionFactory() {
        String resource = "mybatis-xml/mybatis-config.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream( resource );
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build( inputStream );
            return sqlSessionFactory;
        } catch ( IOException e ) {
            throw new RuntimeException( "读取配置异常", e );
        }
    }

}
