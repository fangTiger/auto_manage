package com.xl.tf;

import com.xl.basic.common.KafKaCommonData;
import com.xl.tf.bolt.*;
import com.xl.tf.common.TfCommonData;
import com.xl.tf.common.TfSpoutProducer;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.topology.TopologyBuilder;

/**
 * @Author:lww
 * @Date:16:51 2017/9/13
 */
public class MainProcess {

	/**
	 * 数据处理入库方法
	 * @return
	 * @Author: lww
	 * @Description:
	 * @Date: 10:31 2017/9/8
	 */
	public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
		BrokerHosts broker = new ZkHosts(KafKaCommonData.ZK_HOSTS);

		SpoutConfig tfSpout = TfSpoutProducer.getSpout(broker, TfCommonData.TF_STORM);

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("tfSpout", new KafkaSpout(tfSpout), 1);
		//数据处理
		builder.setBolt("tfDataProBolt", new TfDataProBolt(),1).localOrShuffleGrouping("tfSpout");
		//语义指纹获取
		builder.setBolt("tfFingerprintBolt", new TfFingerprintBolt(),1).localOrShuffleGrouping("tfDataProBolt");
		//情感获取
		builder.setBolt("tfAffectionBolt", new TfAffectionBolt(),1).localOrShuffleGrouping("tfFingerprintBolt");
		//监测项获取
		builder.setBolt("tfMClassProBolt", new TfMClassProBolt(),1).localOrShuffleGrouping("tfAffectionBolt");
		//标签获取
		builder.setBolt("tfTClassProBolt", new TfTClassProBolt(),1).localOrShuffleGrouping("tfMClassProBolt");
		//预警获取
		builder.setBolt("tfWClassProBolt", new TfWClassProBolt(),1).localOrShuffleGrouping("tfTClassProBolt");
		//入库
		builder.setBolt("tfInsertProBolt", new TfInsertProBolt(),1).localOrShuffleGrouping("tfWClassProBolt");

		Config config = new Config();
		config.setDebug(false);

		// 本地运行或者提交到集群
		if (args != null && args.length == 1) {
			// 集群运行
			StormSubmitter.submitTopology(args[0], //mytopology22
					config, //
					builder.createTopology());
		} else {
			// 本地运行
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("local333333", //
					config,//
					builder.createTopology());
		}
	}
}
