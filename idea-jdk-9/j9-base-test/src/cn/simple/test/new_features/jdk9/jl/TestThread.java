package cn.simple.test.new_features.jdk9.jl;

/**
 * Thread.onSpinWait() 某些处理器可以暗示线程处于旋转等待状态，如果可能，可以优化资源使用
 * <p>
 * Created by zengxf on 2017/10/10.
 */
public class TestThread {

    public static void main(String[] arr) throws InterruptedException {
        SpinWaitTest test = new SpinWaitTest();
        new Thread(test).start();
        Thread.sleep(1000L);
        test.setDataReady(true);
    }

    static class SpinWaitTest implements Runnable {
        private volatile boolean dataReady = false;

        @Override
        public void run() {
            while (!dataReady) {
                // hint a spin-wait
                Thread.onSpinWait();
            }
            processData();
        }

        private void processData() {
            System.out.println("ok");
        }

        void setDataReady(boolean dataReady) {
            this.dataReady = dataReady;
        }
    }

}
