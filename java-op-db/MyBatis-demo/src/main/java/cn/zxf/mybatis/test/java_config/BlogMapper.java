package cn.zxf.mybatis.test.java_config;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import cn.zxf.mybatis.test.Blog;

public interface BlogMapper {

    @Select( "SELECT * FROM blog WHERE id = #{id}" )
    Blog selectBlog( int id );

    @Select( "SELECT * FROM blog" )
    List<Blog> selectAllBlog();

}
