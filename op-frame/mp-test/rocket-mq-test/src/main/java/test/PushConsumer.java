package test;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

import java.util.stream.Collectors;

/**
 * <p/>
 * Created by ZXFeng on 2024/9/18
 */
public class PushConsumer {

    public static final String CONSUMER_GROUP = "test_push_group_1";
    public static final String NAME_SRV_ADDR = "127.0.0.1:9876";
    public static final String TOPIC = "TopicTest";

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
        consumer.setNamesrvAddr(NAME_SRV_ADDR);

        // 指定在全新的消费者组的情况下从哪里开始。
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe(TOPIC, "*");
        consumer.registerMessageListener((MessageListenerConcurrently) (msgList, context) -> {
            String msg = msgList.stream()
                    .map(item -> String.format("  msg-item: [%s]. %n", new String(item.getBody())))
                    .collect(Collectors.joining());
            System.out.printf("%n[%s] Receive New Messages: %n%s%n", Thread.currentThread().getName(), msg);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        System.out.printf("Consumer Started. %n");
    }

}
