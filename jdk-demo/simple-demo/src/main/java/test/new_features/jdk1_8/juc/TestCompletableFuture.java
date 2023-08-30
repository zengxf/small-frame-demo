package test.new_features.jdk1_8.juc;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import util.SleepUtils;

/**
 * 都有对应的异步方法 xxAsync()
 * 
 * <p>
 * Created by zengxf on 2017-11-28
 */
@Slf4j
public class TestCompletableFuture {

    static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main( String[] args ) throws ExecutionException, InterruptedException {
        // testThenApply();
        // testThenAccept();
        // testThenRun();
        // testThenCompose();
        // testThenCombine();
        // testThenAcceptBoth();
        // testApplyToEither();
        // testAcceptEither();
        // testAllOf();
        // testAnyOf();
        // testHandle();
        // testExceptionally();
        // howStart();
        // testWhenComplete();
        // test_runAsync();

        // solveCallbackHell();

        test_get();
        // test_getNow();

        executor.shutdown();
    }

    // 会等待
    static void test_get() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync( () -> {
            System.out.println( "------ 1" );
            SleepUtils.second( 1 );
            System.out.println( "------ 2" );
            return 100;
        } );
        System.out.println( "res: " + cf.get() );
    }

    // 不等待，直接返回默认值
    static void test_getNow() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync( () -> {
            System.out.println( "------ 1" );
            SleepUtils.second( 1 );
            System.out.println( "------ 2" );
            return 100;
        } );
        System.out.println( "res: " + cf.getNow( 0 ) );
    }

    // 解决**回调地狱**问题
    static void solveCallbackHell() {
        long l = System.currentTimeMillis();

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync( () -> {
            System.out.println( "在回调中执行耗时操作..." );
            timeConsumingOperation();
            return 100;
        } );
        completableFuture = completableFuture.thenCompose( i -> {
            return CompletableFuture.supplyAsync( () -> {
                System.out.println( "在回调的回调中执行耗时操作..." );
                timeConsumingOperation();
                return i + 100;
            } );
        } );// <1>
        completableFuture.whenComplete( ( result, e ) -> {
            System.out.println( "计算结果:" + result );
        } );

        System.out.println( "主线程运算耗时:" + ( System.currentTimeMillis() - l ) + " ms" );

        SleepUtils.second( 3 );
    }

    static void timeConsumingOperation() {
        try {
            Thread.sleep( 1000 );
            System.out.println( "执行耗时操作...1000ms" );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    static void test_runAsync() {
        Runnable runnable = () -> {
            log.info( "async --> start ..." );
            sleep( 2000 );
            log.info( "async --> end ..." );
        };
        CompletableFuture.runAsync( runnable, executor );
        log.info( "async --> return !!!" );
    }

    static void sleep( int millis ) {
        try {
            Thread.sleep( millis );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

    /**
     * 完成后处理
     */
    public static void testWhenComplete() throws InterruptedException, ExecutionException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            System.out.println( "f1 run ..." );
            if ( System.currentTimeMillis() % 2 == 0 ) {
                throw new RuntimeException( "random error !!!" );
            }
            return "zero";
        }, executor );
        f1.whenComplete( ( str, e ) -> {
            System.out.println( "result: " + str );
            System.out.println( "e: " + e );
        } );
        // System.out.println( "f1 get result: " + f1.join() );
    }

    /**
     * 如何创建和获取
     */
    public static void howStart() throws InterruptedException, ExecutionException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            System.out.println( "f1 run ..." );
            return "zero";
        }, executor );
        System.out.println( "f1 get result: " + f1.get() );

        CompletableFuture<Void> f2 = CompletableFuture.runAsync( () -> {
            System.out.println( "f2 run ..." );
        }, executor );
        System.out.println( "f2 get result: " + f2.get() );
    }

    /**
     * 结果和异常处理
     */
    public static void testHandle() throws InterruptedException, ExecutionException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            return "zero";
        }, executor );
        CompletableFuture<String> cf = f1.handle( ( String t, Throwable u ) -> {
            System.out.println( "result: " + t );
            System.out.println( "u: " + u );
            return "test-" + t;
        } );
        System.out.println( "cf result: " + cf.get() );
    }

    /**
     * 异常处理
     */
    public static void testExceptionally() throws InterruptedException, ExecutionException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            return "zero";
        }, executor );
        CompletableFuture<String> cf = f1.exceptionally( ( t ) -> {
            System.out.println( "t: " + t ); // 异常时才调用
            return "def";
        } );
        System.out.println( "cf result: " + cf.get() );
    }

    /**
     * 等待多个 future 当中最快的一个返回
     */
    public static void testAnyOf() throws InterruptedException {
        List<CompletableFuture<String>> futures = IntStream.range( 1, 10 )//
                .mapToObj( i -> longCost( i ) )//
                .collect( Collectors.toList() );
        CompletableFuture<Object> firstCompleted = CompletableFuture.anyOf( futures.toArray( new CompletableFuture[] {} ) );
        firstCompleted.thenAccept( ( Object result ) -> {
            System.out.println( "get at: " + System.currentTimeMillis() + ", first result: " + result );
        } );
    }

    /**
     * 等待多个 future 返回
     */
    public static void testAllOf() throws InterruptedException {
        List<CompletableFuture<String>> futures = IntStream.range( 1, 10 ) //
                .mapToObj( i -> longCost( i ) )//
                .collect( Collectors.toList() );
        CompletableFuture<Void> allCompleted = CompletableFuture.allOf( futures.toArray( new CompletableFuture[] {} ) );
        allCompleted.thenRun( () -> {
            futures.stream()
                    .forEach( future -> {
                        try {
                            System.out.println( "get future at:" + System.currentTimeMillis() + ", result:" + future.get() );
                        } catch ( InterruptedException | ExecutionException e ) {
                            e.printStackTrace();
                        }
                    } );
        } );
    }

    static CompletableFuture<String> longCost( int i ) {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            try {
                System.out.println( "f1-" + i + " start to sleep at:" + System.currentTimeMillis() );
                TimeUnit.SECONDS.sleep( i );
                System.out.println( "f1-" + i + " stop sleep at:" + System.currentTimeMillis() );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return "i-" + i;
        }, executor );
        return f1;
    }

    /**
     * 取其中返回最快的一个<br>
     * 当任意一个 CompletionStage 完成的时候，action 这个消费者就会被执行。这个方法返回 CompletableFuture<Void>
     */
    public static void testAcceptEither() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            try {
                System.out.println( "f1 start to sleep at:" + System.currentTimeMillis() );
                TimeUnit.SECONDS.sleep( 3 );
                System.out.println( "f1 stop sleep at:" + System.currentTimeMillis() );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return "zero";
        }, executor );
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync( () -> {
            try {
                System.out.println( "f2 start to sleep at:" + System.currentTimeMillis() );
                TimeUnit.SECONDS.sleep( 5 );
                System.out.println( "f2 stop sleep at:" + System.currentTimeMillis() );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return "hello";
        }, executor );

        CompletableFuture<Void> reslutFuture = f1.acceptEither( f2, r -> {
            System.out.println( "quicker result: " + r );
        } );
        System.out.println( "result: " + reslutFuture.get() );// should be null , wait for complete
    }

    /**
     * 当任意一个 CompletionStage 完成的时候，fn 会被执行,它的返回值会当做新的 CompletableFuture<U> 的计算结果
     */
    public static void testApplyToEither() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            try {
                System.out.println( "f1 start to sleep at:" + System.currentTimeMillis() );
                TimeUnit.SECONDS.sleep( 5 );
                System.out.println( "f1 stop sleep at:" + System.currentTimeMillis() );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return "fromF1";
        }, executor );
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync( () -> {
            try {
                System.out.println( "f2 start to sleep at:" + System.currentTimeMillis() );
                TimeUnit.SECONDS.sleep( 2 );
                System.out.println( "f2 stop sleep at:" + System.currentTimeMillis() );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return "fromF2";
        }, executor );

        CompletableFuture<String> reslutFuture = f1.applyToEither( f2, i -> i.toString() );
        System.out.println( "result: " + reslutFuture.get() ); // should not be null , wait for complete
    }

    /**
     * thenAcceptBoth 用于组合两个并发的任务, 产生新的 future 没有返回值
     */
    public static void testThenAcceptBoth() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            try {
                System.out.println( "f1 start to sleep at:" + System.currentTimeMillis() );
                TimeUnit.SECONDS.sleep( 1 );
                System.out.println( "f1 stop sleep at:" + System.currentTimeMillis() );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return "zero";
        }, executor );
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync( () -> {
            try {
                System.out.println( "f2 start to sleep at:" + System.currentTimeMillis() );
                TimeUnit.SECONDS.sleep( 3 );
                System.out.println( "f2 stop sleep at:" + System.currentTimeMillis() );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return "hello";
        }, executor );

        CompletableFuture<Void> reslutFuture = f1.thenAcceptBoth( f2, ( t, u ) -> {
            System.out.println( "f3 start to accept at:" + System.currentTimeMillis() );
            System.out.println( t + " over" );
            System.out.println( u + " over" );
        } );

        System.out.println( reslutFuture.get() );
        System.out.println( "finish accept at:" + System.currentTimeMillis() );
    }

    /**
     * thenCombine 用于组合两个并发的任务, 产生新的 future 有返回值
     */
    public static void testThenCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            try {
                System.out.println( "f1 start to sleep at:" + System.currentTimeMillis() );
                Thread.sleep( 1000 );
                System.out.println( "f1 finish sleep at:" + System.currentTimeMillis() );
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
            return "zero";
        }, executor );

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync( () -> {
            try {
                System.out.println( "f2 start to sleep at:" + System.currentTimeMillis() );
                Thread.sleep( 3000 );
                System.out.println( "f2 finish sleep at:" + System.currentTimeMillis() );
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
            return "hello";
        }, executor );

        BiFunction<String, String, String> fn = ( t, u ) -> {
            System.out.println( "f3 start to combine at:" + System.currentTimeMillis() );
            return t + "-" + u;
        };
        CompletableFuture<String> reslutFuture = f1.thenCombine( f2, fn );

        System.out.println( "result: " + reslutFuture.get() ); // zero-hello
        System.out.println( "finish combine at:" + System.currentTimeMillis() );
        executor.shutdown();
    }

    /**
     * compose 相当于 flatMap, 避免 CompletableFuture<CompletableFuture<String>> 这种
     */
    public static void testThenCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            return "zero";
        }, executor );
        CompletableFuture<CompletableFuture<String>> f4 = f1.thenApply( TestCompletableFuture::calculate );
        System.out.println( "f4.get: " + f4.get() );
        System.out.println( "f4.get.get: " + f4.get()
                .get() );

        CompletableFuture<String> f5 = f1.thenCompose( TestCompletableFuture::calculate );
        System.out.println( "f5.get: " + f5.get() );

        System.out.println( f1.get() );
    }

    public static CompletableFuture<String> calculate( String input ) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync( () -> {
            System.out.println( input );
            return input + "---" + input.length();
        }, executor );
        return future;
    }

    /**
     * 结果传递(变换)
     */
    public static void testThenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            return "zero";
        }, executor );

        CompletableFuture<Integer> f2 = f1.thenApply( ( t ) -> {
            System.out.println( t );
            return Integer.valueOf( t.length() );
        } );

        CompletableFuture<Double> f3 = f2.thenApply( r -> r * 2.0 );
        System.out.println( f3.get() );
    }

    /**
     * future 完成处理, 可获取结果
     */
    public static void testThenAccept() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            return "zero";
        }, executor );
        f1.thenAccept( e -> {
            System.out.println( "get result: " + e );
        } );
    }

    /**
     * future 完成处理
     */
    public static void testThenRun() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync( () -> {
            return "zero";
        }, executor );
        f1.thenRun( () -> {
            System.out.println( "finished" );
        } );
    }

}
