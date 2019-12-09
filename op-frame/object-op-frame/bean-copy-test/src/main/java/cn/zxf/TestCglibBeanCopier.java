package cn.zxf;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.DebuggingClassWriter;

// class 路径在 */empty/Object$$*.class
public class TestCglibBeanCopier {

    public static void main( String[] args ) {
        System.setProperty( DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\Users\\Administrator\\Desktop\\aa\\class" );

        User user = User.testData();
        UserDto dto = new UserDto();

        BeanCopier bc = BeanCopier.create( User.class, UserDto.class, false );
        bc.copy( user, dto, null );

        System.out.println( bc );
        System.out.println( "==================" );

        System.out.println( user );
        System.out.println( "==================" );
        System.out.println( dto );
    }

}
