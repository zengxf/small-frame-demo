package cn.zxf.mybatis.test.java_config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class JavaConfigSessionUtil {

    public static SqlSessionFactory sessionFactory() {
        DataSourceFactory dsFactory = new PooledDataSourceFactory();
        Properties props = new Properties();
        props.setProperty( "driver", "com.mysql.jdbc.Driver" );
        props.setProperty( "url", "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC" );
        props.setProperty( "username", "root" );
        props.setProperty( "password", "admin" );
        dsFactory.setProperties( props );

        DataSource dataSource = dsFactory.getDataSource(); // PooledDataSource
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment( "development", transactionFactory, dataSource );
        
        Configuration configuration = new Configuration( environment );
        configuration.addMapper( BlogMapper.class );

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build( configuration );

        return sqlSessionFactory;
    }

}
