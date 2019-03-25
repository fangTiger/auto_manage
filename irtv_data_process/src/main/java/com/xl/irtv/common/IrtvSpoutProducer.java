package com.xl.irtv.common;

import com.xl.baisc.common.KafKaCommonData;
import com.xl.baisc.common.MessageScheme;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;

import java.util.Arrays;

/**
 * @Author:lww
 * @Date:10:35 2017/9/8
 */
public class IrtvSpoutProducer {

	/**
	 * 获取spout对象
	 * @return org.apache.storm.kafka.SpoutConfig
	 * @Author: lww
	 * @Description:
	 * @Date: 10:42 2017/9/8
	 * @param broker BrokerHosts对象
	 * @param stormId storm定义ID
	 */
	public static SpoutConfig getSpout(BrokerHosts broker,String stormId){

		SpoutConfig spoutConf = new SpoutConfig(broker, IrtvCommonData.IRTV_TOPIC, IrtvCommonData.IRTV_ZOOKEEPER_ROOT,
				stormId);
		//spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
		spoutConf.scheme = new SchemeAsMultiScheme(new MessageScheme());

		// 记录Spout读取进度所用的zookeeper的host,即记录offset位置的zk
		spoutConf.zkServers = Arrays.asList(KafKaCommonData.ZK_SERVERS);
		spoutConf.zkPort = KafKaCommonData.ZK_PORT;
		spoutConf.startOffsetTime  = kafka.api.OffsetRequest.EarliestTime();//从最早的消息开始
//		spoutConf.startOffsetTime = kafka.api.OffsetRequest.LatestTime();//从最新的消息开始，即从队列队伍最末端开始。
		spoutConf.ignoreZkOffsets  = false;
		return spoutConf;
	}
}
