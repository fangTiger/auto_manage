package com.xl.manage;

import com.xl.basic.common.KafKaCommonData;
import com.xl.manage.bolt.LogDataProBolt;
import com.xl.manage.bolt.LogInsertProBolt;
import com.xl.manage.bolt.LogWaringBolt;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.LogSpoutProducer;
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
 * @Date:16:28 2017/11/8
 */
public class LogMainProcess {


	public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
		BrokerHosts broker = new ZkHosts(KafKaCommonData.ZK_HOSTS);

		SpoutConfig autoSpout = LogSpoutProducer.getSpout(broker, LogCommonData.LOG_STORM);

		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("logSpout", new KafkaSpout(autoSpout), 1);

		builder.setBolt("logWaringBolt", new LogWaringBolt(),1).shuffleGrouping("logSpout");

		builder.setBolt("logDataProBolt", new LogDataProBolt(),5).shuffleGrouping("logWaringBolt");

		builder.setBolt("logInsertProBolt", new LogInsertProBolt(),5).shuffleGrouping("logDataProBolt");

		Config config = new Config();
		config.setDebug(false);
		config.setMessageTimeoutSecs(86400);
		// 本地运行或者提交到集群
		if (args != null && args.length == 1) {
			// 集群运行
			StormSubmitter.submitTopology(args[0], //mytopology22
					config, //
					builder.createTopology());
		} else {
			// 本地运行
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("local", //
					config,//
					builder.createTopology());
			// cluster.shutdown();
		}

	}


}
