package kafuka;

import kafka.serializer.StringEncoder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducerTest {





    public static void main(String[] args) {


        Properties props = new Properties();

        props.put("bootstrap.servers", "59.110.53.221:9092");
        props.put("zookeeper.connect", "59.110.53.221:2181");// 声明zk
        props.put("serializer.class", StringEncoder.class.getName());
       props.put("metadata.broker.list", "59.110.53.221");// 声明kafka


        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //KafkaProducer<String, String>  key用来分区使用 value是数据
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        for(int i=400;i<500;i++) {

            ProducerRecord<String, String> record = new ProducerRecord<String, String>("producer_test", "5555"+i,
                    "message: 中文测试"+i);
            producer.send(record);
        }
        producer.close();;
        System.out.print("完成");









        //new KafkaProducerTest("idoall_testTopic").start();// 使用kafka集群中创建好的主题 test

    }
}
