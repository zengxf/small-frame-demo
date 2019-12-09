package cn.zxf.mybatis.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.zxf.mybatis.test.java_config.BlogMapper;
import cn.zxf.mybatis.test.java_config.JavaConfigSessionUtil;
import cn.zxf.mybatis.test.xml_config.XmlConfigSessionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSession {

    public static void main( String[] args ) {
        // testByXml();
        testByJava();
    }

    static void testByXml() {
        SqlSessionFactory sqlSessionFactory = XmlConfigSessionUtil.sessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Blog blog = (Blog) session.selectOne( "cn.zxf.mybatis.BlogMapper.selectBlog", 1 );
            log.info( "\nblog: {}\n", blog );

            List<Blog> blogList = session.selectList( "cn.zxf.mybatis.BlogMapper.selectAllBlog" );
            log.info( "\nblogList: {}\n", blogList );
        } finally {
            session.close();
        }
    }

    static void testByJava() {
        SqlSessionFactory sqlSessionFactory = JavaConfigSessionUtil.sessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BlogMapper blogMapper = session.getMapper( BlogMapper.class );
            Blog blog = blogMapper.selectBlog( 1 );
            log.info( "\nblog: {}\n", blog );

            List<Blog> blogList;
            long start;

            start = System.currentTimeMillis();
            blogList = blogMapper.selectAllBlog();
            log.info( "\nblogList: {}\n", blogList );
            log.info( "\nuse time: {}\n", System.currentTimeMillis() - start );

            start = System.currentTimeMillis();
            blogList = blogMapper.selectAllBlog();
            log.info( "\nblogList: {}\n", blogList );
            log.info( "\nuse time: {}\n", System.currentTimeMillis() - start );

        } finally {
            session.close();
        }
    }

}
