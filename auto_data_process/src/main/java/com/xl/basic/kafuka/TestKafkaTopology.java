package com.xl.basic.kafuka;

import com.xl.basic.storm.WordCountTopology;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TestKafkaTopology {
    private static final Logger log = LoggerFactory.getLogger(WordCountTopology.class);
/*
    下面是SpoutConfig对象的一些字段的含义，其实是继承的KafkaConfig的字段，可看源码

　　public int fetchSizeBytes = 1024 * 1024; //发给Kafka的每个FetchRequest中，用此指定想要的response中总的消息的大小
    public int socketTimeoutMs = 10000;//与Kafka broker的连接的socket超时时间
    public int fetchMaxWait = 10000;   //当服务器没有新消息时，消费者会等待这些时间
    public int bufferSizeBytes = 1024 * 1024;//SimpleConsumer所使用的SocketChannel的读缓冲区大小
    public MultiScheme scheme = new RawMultiScheme();//从Kafka中取出的byte[]，该如何反序列化
    public boolean forceFromStart = false;//是否强制从Kafka中offset最小的开始读起
    public long startOffsetTime = kafka.api.OffsetRequest.EarliestTime();//从何时的offset时间开始读，默认为最旧的offset
    public long maxOffsetBehind = Long.MAX_VALUE;//KafkaSpout读取的进度与目标进度相差多少，相差太多，Spout会丢弃中间的消息
　 　public boolean useStartOffsetTimeIfOffsetOutOfRange = true;//如果所请求的offset对应的消息在Kafka中不存在，是否使用startOffsetTime
　 　public int metricsTimeBucketSizeInSecs = 60;//多长时间统计一次metrics
*/
    public static void main(String[] args) {



        // zookeeper的服务器地址 多个逗号分隔
        String zks = "59.110.53.221:2181";
        // 消息的topic
        String topic = "producer_test";
        // kafka-strom在zookeeper上的根 用于记录其消费的offset位置
        String zkRoot = "/producer_test";
        // 类似group name
        String id = "spout-12";

        BrokerHosts brokerHosts = new ZkHosts(zks);
        SpoutConfig spoutConf = new SpoutConfig(brokerHosts, topic, zkRoot,
                id);
        //spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());

        spoutConf.scheme = new SchemeAsMultiScheme(new MessageScheme());

        // 记录Spout读取进度所用的zookeeper的host,即记录offset位置的zk
        List<String> servers = new ArrayList<>();
        servers.add("59.110.53.221");
        spoutConf.zkServers = servers;
        spoutConf.zkPort = 2181;
       // spoutConf.startOffsetTime  = kafka.api.OffsetRequest.EarliestTime();
        spoutConf.ignoreZkOffsets  = true;
       // TopologyBuilder topologyBuilder = new TopologyBuilder();
/////////////////////////////////////


     // kafka配置
/*
       BrokerHosts brokerHosts = new ZkHosts("59.110.53.221:2181");


       //ZkHosts zkhosts  = new ZkHosts("59.110.53.221:2181");

        String topic = "producer_test";
        String zkRoot = "/producer_test";
        String spoutId = null;


        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, topic, zkRoot, UUID.randomUUID().toString());


        spoutConfig.scheme=new SchemeAsMultiScheme(new StringScheme());//定义输出为string类型

*/
       // spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme());


        TopologyBuilder builder = new TopologyBuilder();


        builder.setSpout("spout", new KafkaSpout(spoutConf), 1);
       // builder.setSpout("spout", new KafkaSpout(spoutConfig), 1);

        builder.setBolt("bolt1", new MyKafkaBolt(),1).shuffleGrouping("spout");

        Config config = new Config();

/*
        Map<String, String> map = new HashMap<String, String>();
         map.put("metadata.broker.list", "59.110.53.221:9092");
         map.put("serializer.class", "kafka.serializer.StringEncoder");
         config.put("kafka.broker.properties", map);

*/

        //config.setDebug(true);
        LocalCluster localCluster=new LocalCluster();
        localCluster.submitTopology("mytopology22", config,  builder.createTopology());


//http://www.cnblogs.com/zhoudayang/p/5066956.html



    }
}
