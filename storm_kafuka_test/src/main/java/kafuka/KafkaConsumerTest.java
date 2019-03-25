package kafuka;

import kafka.serializer.StringEncoder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerTest {







    public static void main(String[] args) {

        Properties props = new Properties();

        props.put("bootstrap.servers", "59.110.53.221:9092");
        props.put("zookeeper.connect", "59.110.53.221:2181");// 声明zk
        props.put("serializer.class", StringEncoder.class.getName());
        props.put("metadata.broker.list", "59.110.53.221");// 声明kafka
        //每个消费者分配独立的组号
        props.put("group.id", "test");

        //如果value合法，则自动提交偏移量
        props.put("enable.auto.commit", "true");

        //设置多久一次更新被消费消息的偏移量
        props.put("auto.commit.interval.ms", "1000");

        //设置会话响应的时间，超过这个时间kafka可以选择放弃消费或者消费下一条消息
        props.put("session.timeout.ms", "30000");

        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList("producer_test"));

        System.out.println("Subscribed to topic " + "producer_test");
        int i = 0;

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

            System.out.print("ddddddddddd"+records.count());


            for (ConsumerRecord<String, String> record : records) {

                // print the offset,key and value for the consumer records.
                System.out.printf("offset = %d, key = %s, value = %s\n",
                        record.offset(), record.key(), record.value());
            }
            consumer.close();

            break;
        }

    }
}
