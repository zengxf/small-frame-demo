package test_juc;

import java.util.concurrent.*;

/**
 * <p/>
 * Created by ZXFeng on 2024/7/26
 */
public class ForkJoinPoolTest {

    public static void main(String[] args) throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1, 10);
        // System.out.println(task.compute());  // 也可直接用默认的 fj 线程池执行
        Future<Integer> result = forkJoinPool.submit(task);
        System.out.println("结果：" + result.get());
    }

    public static class CountTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 2;     // 阈值
        private final int start;
        private final int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer compute() {
            int sum = 0;

            try {
                Thread.sleep(200L);
                System.out.printf("cur-thread: [%s].%n", Thread.currentThread().getName());
            } catch (InterruptedException e) {
            }

            // 如果任务足够小就计算任务
            boolean canCompute = (end - start) <= THRESHOLD;
            if (canCompute) {
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                // 如果任务大于阈值，就分裂成两个子任务计算
                int middle = (start + end) / 2;
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle + 1, end);

                // 执行子任务
                leftTask.fork();
                rightTask.fork();

                // 等待子任务执行完，并得到其结果
                int leftResult = leftTask.join();
                int rightResult = rightTask.join();

                // 合并子任务结果
                sum = leftResult + rightResult;
            }
            return sum;
        }
    }

}
