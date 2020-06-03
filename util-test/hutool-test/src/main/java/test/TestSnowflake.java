package test;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class TestSnowflake {

    public static void main( String[] args ) {
        // 参数 1 为终端 ID
        // 参数 2 为数据中心 ID
        Snowflake snowflake = IdUtil.createSnowflake( 10, 18 );
        long id = snowflake.nextId();
        System.out.println( id );
    }

}
