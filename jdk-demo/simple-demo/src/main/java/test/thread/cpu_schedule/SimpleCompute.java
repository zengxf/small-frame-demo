package test.thread.cpu_schedule;

public class SimpleCompute {
    private static final int num = 100_0000;

    public static void main( String[] args ) throws InterruptedException {
        Runnable target = () -> {
            for ( int i = 0; i < num; i++ )
                System.out.println( i );
        };
        new Thread( target, "线程1" ).start();
        new Thread( target, "线程2" ).start();
        new Thread( target, "线程3" ).start();
        new Thread( target, "线程4" ).start();
    }

}
