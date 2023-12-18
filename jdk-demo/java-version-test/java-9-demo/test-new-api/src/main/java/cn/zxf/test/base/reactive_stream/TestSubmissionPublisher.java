package cn.zxf.test.base.reactive_stream;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.LongStream;

/**
 * Created by zengxf on 2018/9/18.
 */
public class TestSubmissionPublisher {

    public static void main(String[] arr) throws Exception {
//        testPubSub();
        SubmissionPublisher<Long> pub = new SubmissionPublisher();
        LongStream.range(1L, 6L)
                .forEach(pub::submit);
    }

    static void testPubSub() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> subTask;

        try (SubmissionPublisher<Long> pub = new SubmissionPublisher()) {
            System.out.println("Subscriber Buffer Size: " + pub.getMaxBufferCapacity());
            subTask = pub.consume(System.out::println);
            LongStream.range(1L, 6L)
                    .forEach(pub::submit);
        }

        subTask.get(); // 方法阻塞，直到订阅者完成
    }

}
