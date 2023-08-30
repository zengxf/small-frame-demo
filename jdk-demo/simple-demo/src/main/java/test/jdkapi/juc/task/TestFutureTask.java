package test.jdkapi.juc.task;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import util.SleepUtils;

public class TestFutureTask {

    public static void main( String[] args ) {
        Callable<String> callable = () -> {
            System.out.println( "----- 1" );
            SleepUtils.second( 2 );
            System.out.println( "----- 2" );
            return "test";
        };
        
        FutureTask<String> task = new FutureTask<>( callable );
        // task.run(); // 同步执行
        CompletableFuture.runAsync( task );
        
        try {
            System.out.println( "-------- 3" );
            String res = task.get( 1, TimeUnit.SECONDS );
            System.out.println( "-------- 4" );
            System.out.println( res );
        } catch ( InterruptedException | ExecutionException | TimeoutException e ) {
            e.printStackTrace();
        }
    }

}
