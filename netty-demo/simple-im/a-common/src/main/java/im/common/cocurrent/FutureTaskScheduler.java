package im.common.cocurrent;

import im.common.utils.ThreadUtils;

import java.util.concurrent.ThreadPoolExecutor;

public class FutureTaskScheduler {

    static final ThreadPoolExecutor mixPool;

    static {
        mixPool = ThreadUtils.getMixedTargetThreadPool();
    }

    /*** 添加任务 */
    public static void add(ExecuteTask executeTask) {
        mixPool.submit(executeTask::execute);
    }

}
