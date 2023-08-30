package test.jdkapi.juc.structure;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.IntStream;

/**
 * 测试（时间窗口加优先级的）排序，
 * <p>
 * 是用二叉树实现排序的，forEach 是不能顺序输出的
 * <br/>
 * Created by ZXFeng on  2021/9/14.
 */
public class TestPriorityQueueSort {

    static long TIME_WINDOW = 60_000; // 一分钟

    public static void main(String[] args) {
        Comparator<Task> comparator = (t1, t2) -> {
//            long tms1 = (t1.timestamp - (t1.timestamp % timeWindow)) / timeWindow;
//            long tms2 = (t2.timestamp - (t2.timestamp % timeWindow)) / timeWindow;
            long tms1 = t1.timestamp / TIME_WINDOW;
            long tms2 = t2.timestamp / TIME_WINDOW;
            if (tms1 > tms2)
                return 1;
            if (tms1 < tms2)
                return -1;
            return t1.priority - t2.priority;
        };
//        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>(10, comparator);
        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>(10);
//        PriorityQueue<Task> queue = new PriorityQueue<>(10, comparator);
        List<Task> list = new ArrayList<>();

        long start = System.currentTimeMillis();

        IntStream.rangeClosed(1, 5)
                .mapToObj(i -> Task.of(i, start)) // 当前时间
                .peek(queue::add)
                .forEach(list::add);
        IntStream.rangeClosed(1, 5)
                .mapToObj(i -> Task.of(i, start + 10_000L)) // 加 10 秒
                .peek(queue::add)
                .forEach(list::add);
        IntStream.rangeClosed(3, 8)
                .mapToObj(i -> Task.of(i, start + 10_000L)) // 加 10 秒
                .peek(queue::add)
                .forEach(list::add);
        IntStream.rangeClosed(1, 5)
                .mapToObj(i -> Task.of(i, start + 70_000L)) // 加 70 秒
                .peek(queue::add)
                .forEach(list::add);
        IntStream.rangeClosed(1, 5)
                .mapToObj(i -> Task.of(i, start - 10_000L)) // 减 10 秒
                .peek(queue::add)
                .forEach(list::add);
        IntStream.rangeClosed(1, 5)
                .mapToObj(i -> Task.of(i, start - 70_000L)) // 减 70 秒
                .peek(queue::add)
                .forEach(list::add);

        System.out.println("\n");
        while (true) {
            Task task = queue.poll();
            if (task == null)
                break;
            System.out.println(task.getDesc());
        }
        // 是用二叉树实现排序，forEach 是不能顺序输出的
//        queue.stream()
//                .map(Task::getDesc)
//                .forEach(System.out::println);

//        list.stream()
//                .sorted(comparator)
//                .map(Task::getDesc)
//                .forEach(System.out::println);
        System.out.println("\n");
    }

    @Data
    @Accessors(chain = true)
    static class Task implements Comparable<Task> {
        int priority;
        long timestamp;
        String desc;

        static Task of(int priority, long timestamp) {
            long ts = (timestamp / TIME_WINDOW) * TIME_WINDOW;
            return new Task()
                    .setPriority(priority)
                    .setTimestamp(timestamp)
                    .setDesc(String.format("ts：[%tT]，时间：[%tT]，优先级：[%d]", ts, timestamp, priority));
        }

        @Override
        public int compareTo(Task t2) {
            Task t1 = this;
            long tms1 = t1.timestamp / TIME_WINDOW;
            long tms2 = t2.timestamp / TIME_WINDOW;
            if (tms1 > tms2)
                return 1;
            if (tms1 < tms2)
                return -1;
            return t1.priority - t2.priority;
        }
    }

}
