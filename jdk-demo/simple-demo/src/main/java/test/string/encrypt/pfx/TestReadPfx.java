package test.string.encrypt.pfx;

public class TestReadPfx {
    public static void main( String[] args ) {
        String pwd = "123456";
        String path = TestReadPfx.class.getResource( "admin-ad889cc33.pfx" )
                .getPath();
//        String path = TestReadPfx.class.getResource( "sss.p7b" )
//                .getPath();
        System.out.println( path );
        new ReadPFX().GetPvkformPfx( path, pwd );
    }
}
