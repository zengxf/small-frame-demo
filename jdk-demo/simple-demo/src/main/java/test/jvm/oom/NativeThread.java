package test.jvm.oom;

/**
 * -Xmx200m -Xms200m
 * 
 * <p>
 * Created by zengxf on 2017-11-24
 */
public class NativeThread {

    public static void main( String[] args ) {
        NativeThread nt = new NativeThread();
        nt.runThreads();
    }

    public void runThreads() {
        while ( true ) {
            new Thread( new Runnable() {
                public void run() {
                    try {
                        Thread.sleep( 10000000 );
                    } catch ( InterruptedException e ) {
                    }
                }
            } ).start();
        }
    }

}