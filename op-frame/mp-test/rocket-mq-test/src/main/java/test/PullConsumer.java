package test;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p/>
 * Created by ZXFeng on 2024/9/18
 */
public class PullConsumer {

    public static final String CONSUMER_GROUP = "test_pull_group_1";
    public static final String NAME_SRV_ADDR = "127.0.0.1:9876";
    public static final String TOPIC = "TopicTest";

    public static void main(String[] args) throws MQClientException {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer(CONSUMER_GROUP);
        consumer.setNamesrvAddr(NAME_SRV_ADDR);

        // 指定在全新的消费者组的情况下从哪里开始。
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(TOPIC, "*");
        consumer.setPullBatchSize(1);
        consumer.start();
        System.out.printf("Consumer Started. %n");

        for (int i = 0; i < 2; i++) {
            List<MessageExt> msgList = consumer.poll(); // def: 5s timeout
            String msg = msgList.stream()
                    .map(item -> String.format("  msg-item: [%s]. %n", new String(item.getBody())))
                    .collect(Collectors.joining());
            System.out.printf("%n[%s] Receive New Messages: %n%s%n", Thread.currentThread().getName(), msg);
        }

        consumer.shutdown();
    }

}
