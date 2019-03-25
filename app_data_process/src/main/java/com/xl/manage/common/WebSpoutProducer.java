package com.xl.manage.common;

import com.xl.basic.common.KafKaCommonData;
import com.xl.basic.common.MessageScheme;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;

import java.util.Arrays;

/**
 * @Author:lww
 * @Date:10:35 2017/9/8
 */
public class WebSpoutProducer {

	/**
	 * 获取spout对象
	 *   public final BrokerHosts hosts; //用以获取Kafka broker和partition的信息
		 public final String topic;//从哪个topic读取消息
		 public final String clientId; // SimpleConsumer所用的client id

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
	 * @return org.apache.storm.kafka.SpoutConfig
	 * @Author: lww
	 * @Description:
	 * @Date: 10:42 2017/9/8
	 * @param broker BrokerHosts对象
	 * @param stormId storm定义ID
	 */
	public static SpoutConfig getSpout(BrokerHosts broker,String stormId){

		SpoutConfig spoutConf = new SpoutConfig(broker, WebCommonData.WEB_TOPIC, WebCommonData.WEB_ZOOKEEPER_ROOT,
				stormId);
		//spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());

		spoutConf.scheme = new SchemeAsMultiScheme(new MessageScheme());

		// 记录Spout读取进度所用的zookeeper的host,即记录offset位置的zk
		spoutConf.zkServers = Arrays.asList(KafKaCommonData.ZK_SERVERS);
		spoutConf.zkPort = KafKaCommonData.ZK_PORT;

		spoutConf.startOffsetTime  = kafka.api.OffsetRequest.EarliestTime();
		spoutConf.ignoreZkOffsets  = false;

		return spoutConf;
	}
}
