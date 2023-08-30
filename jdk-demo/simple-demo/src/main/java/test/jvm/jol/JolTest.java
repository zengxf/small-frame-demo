package test.jvm.jol;

import java.util.List;

import lombok.Data;

// 下载地址：http://central.maven.org/maven2/org/openjdk/jol/jol-cli

// M:\zxf-demo-github\zxf_super_demo\simple-demo\bin\main\test\jvm\jol
// java -jar jol-cli.jar internals -cp . test.jvm.jol.JolTest
// java -jar jol-cli.jar internals -cp M:\zxf-demo-github\zxf_super_demo\simple-demo\bin\main test.jvm.jol.JolTest
@Data
public class JolTest {

    private String       aa;
    private List<String> list;
    private int          aab;

    public static void main( String[] args ) {
        System.out.println( "sssssssss === " + new JolTest() );
    }

}
