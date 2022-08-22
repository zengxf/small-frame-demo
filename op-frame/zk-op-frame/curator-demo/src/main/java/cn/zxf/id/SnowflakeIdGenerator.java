package cn.zxf.id;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/22.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SnowflakeIdGenerator {

    /*** 单例 */
    public static SnowflakeIdGenerator instance = new SnowflakeIdGenerator();

    /*** 初始化单例，节点 Id 最大 8091 */
    public synchronized void init(long workerId) {
        if (workerId > MAX_WORKER_ID) {
            // zk 分配的 workerId 过大
            throw new IllegalArgumentException("worker Id wrong: " + workerId);
        }
        instance.workerId = workerId;
    }

    /*** 开始使用该算法的时间为: 2017-01-01 00:00:00 */
    private static final long START_TIME = 1483200000000L;
    /*** worker devId 的位数，最多支持 8192 个节点 */
    private static final int WORKER_ID_BITS = 13;
    /*** 序列号，支持单节点最高每毫秒的最大 ID 位数 1024 */
    private final static int SEQUENCE_BITS = 10;
    /*** 最大的 worker devId，8091。-1 的补码（二进制全 1）右移 13 位, 然后取反 */
    private final static long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    /*** 最大的序列号，1023 */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);
    /*** worker 节点编号的移位 */
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /*** 时间戳的移位 */
    private final static long TIMESTAMP_LEFT_SHIFT = WORKER_ID_BITS + SEQUENCE_BITS;
    /*** 该项目的 worker 节点 devId */
    private long workerId;
    /*** 上次生成 ID 的时间戳 */
    private long lastTimestamp = -1L;
    /*** 当前毫秒生成的序列 */
    private long sequence = 0L;

    public Long nextId() {
        return generateId();
    }

    /*** 生成唯一 id 的具体实现 */
    private synchronized long generateId() {
        long current = System.currentTimeMillis();
        if (current < lastTimestamp) {
            // 如果当前时间小于上一次 ID 生成的时间戳，说明系统时钟回退过，出现问题返回 -1
            return -1;
        }
        if (current == lastTimestamp) {
            // 如果当前生成id的时间还是上次的时间，那么对 sequence 序列号进行 +1
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == MAX_SEQUENCE) {
                // 当前毫秒生成的序列数已经大于最大值，那么阻塞到下一个毫秒再获取新的时间戳
                current = this.nextMs(lastTimestamp);
            }
        } else {
            // 当前的时间戳已经是下一个毫秒
            sequence = 0L;
        }
        // 更新上次生成 id 的时间戳
        lastTimestamp = current;
        // 进行移位操作生成 int64 的唯一 ID
        // 时间戳右移动 23 位
        long time = (current - START_TIME) << TIMESTAMP_LEFT_SHIFT;
        // workerId 右移动 10 位
        long workerId = this.workerId << WORKER_ID_SHIFT;
        return time | workerId | sequence;
    }

    /*** 阻塞到下一个毫秒 */
    private long nextMs(long timeStamp) {
        long current = System.currentTimeMillis();
        while (current <= timeStamp) {
            current = System.currentTimeMillis();
        }
        return current;
    }

}