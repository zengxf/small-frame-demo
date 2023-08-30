package test.jdkapi.ref;

/**
 * <pre>
 * -XX:+PrintGCDetails
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-10-13
 */
public class FinalizeCase {
    private static Block holder = null;

    public static void main( String[] args ) throws Exception {
        holder = new Block();
        holder = null;
        System.out.println( holder );
        System.gc();
        // System.in.read();
    }

    static class Block {
        byte[] _200M = new byte[200 * 1024 * 1024];

        @Override
        protected void finalize() throws Throwable {
            System.out.println( "invoke finalize" );
        }
    }
}
