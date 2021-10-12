package cn.zxf.assertj;

import static org.assertj.core.api.Assertions.*;

public class TestUser {

    public static void main( String[] args ) {
        User userNull = null;
        User user = new User();
        assertThat( userNull ).isNull();
        assertThat( user.getAge() ).isNotNull().as( "check age", user.getName() ).isEqualTo( 0 );

        // gradle build --stacktrace
        user.setName( "zxf" );
        user.setAge( 2 );
        UserAssert.assertThat( user ) //
                .as( "名称[%s]不对", user.getName() ).hasName( "zxf" ) //
                .as( "年龄[%d]不对", user.getAge() ).hasAge( 2 );
    }

}
