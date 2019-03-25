package com.xl.manage;

import com.xl.basic.common.KafKaCommonData;
import com.xl.manage.bolt.*;
import com.xl.manage.common.AutoCommonData;
import com.xl.manage.common.AutoSpoutProducer;
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
 * @Date:10:29 2017/9/8
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

		SpoutConfig autoSpout = AutoSpoutProducer.getSpout(broker, AutoCommonData.AUTO_STORM);

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("autoSpout", new KafkaSpout(autoSpout), 1);

		builder.setBolt("autoDataProBolt", new AutoDataProBolt(),1).shuffleGrouping("autoSpout");

		builder.setBolt("subjectSourceBolt", new AutoSubSourceBolt(),1).shuffleGrouping("autoDataProBolt");
		builder.setBolt("eventSourceBolt", new AutoEveSourceBolt(),1).shuffleGrouping("autoDataProBolt");

		builder.setBolt("subjectContentBolt", new AutoSubContentBolt(),1).shuffleGrouping("subjectSourceBolt");
		builder.setBolt("eventContentBolt", new AutoEveContentBolt(),1).shuffleGrouping("eventSourceBolt");

		builder.setBolt("subjectInsertBolt", new AutoSubInsertBolt(),1).shuffleGrouping("subjectContentBolt");
		builder.setBolt("eventInsertBolt", new AutoEveInsertBolt(),1).shuffleGrouping("eventContentBolt");

		Config config = new Config();
		config.setDebug(false);
		config.setMessageTimeoutSecs(3600);

		// 本地运行或者提交到集群
		if (args != null && args.length == 1) {
			// 集群运行
			StormSubmitter.submitTopology(args[0], //mytopology22
					config, //
					builder.createTopology());
		} else {
			// 本地运行
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("localAutoTopology11", //
					config,//
					builder.createTopology());
			// cluster.shutdown();
		}
	}
}
