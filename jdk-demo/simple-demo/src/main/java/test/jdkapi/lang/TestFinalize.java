package test.jdkapi.lang;

public class TestFinalize {
    public static void main( String[] args ) {
        TestFinalize f = new TestFinalize();
        System.out.println( f );
        f = null;
        System.gc();
        System.out.println( "" ); // 打断点
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println( "finalize" ); // 打断点
    }
}