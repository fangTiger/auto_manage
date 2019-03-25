package com.xl.manage;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.WbOldBean;
import com.xl.basic.common.KafKaCommonData;
import com.xl.manage.bolt.*;
import com.xl.manage.common.WeiboCommonData;
import com.xl.manage.common.WeiboSpoutProducer;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.topology.TopologyBuilder;

/**
 * @Author:lww
 * @Date:13:56 2017/10/12
 */
public class WeiboMainProcess {
	public static void main(String[] args)throws Exception {
		BrokerHosts broker = new ZkHosts(KafKaCommonData.ZK_HOSTS);

		SpoutConfig tfSpout = WeiboSpoutProducer.getSpout(broker, WeiboCommonData.WEIBO_STORM);

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("weiboSpout", new KafkaSpout(tfSpout), 1);
		builder.setBolt("weiboDataProBolt", new WeiboDataProBolt(),3).localOrShuffleGrouping("weiboSpout");
		builder.setBolt("weiboFingerprintBolt", new WeiboFingerprintBolt(),3).localOrShuffleGrouping("weiboDataProBolt");
		builder.setBolt("weiboAffectionBolt", new WeiboAffectionBolt(),3).localOrShuffleGrouping("weiboFingerprintBolt");
		builder.setBolt("weiboMClassProBolt", new WeiboMClassProBolt(),3).localOrShuffleGrouping("weiboAffectionBolt");
		builder.setBolt("weiboTClassProBolt", new WeiboTClassProBolt(),3).localOrShuffleGrouping("weiboMClassProBolt");
		builder.setBolt("weiboWClassProBolt", new WeiboWClassProBolt(),3).localOrShuffleGrouping("weiboTClassProBolt");
		builder.setBolt("weiboInsertProBolt", new WeiboInsertProBolt(),3).localOrShuffleGrouping("weiboWClassProBolt");

		Config config = new Config();
		config.setDebug(false);
		config.setMessageTimeoutSecs(7200);

		// 本地运行或者提交到集群
		if (args != null & args.length == 1) {
			// 集群运行
			StormSubmitter.submitTopology(args[0], //mytopology22
					config, //
					builder.createTopology());
		} else {
			// 本地运行
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("local22222222", //
					config,//
					builder.createTopology());
			// cluster.shutdown();
		}
	}
}
