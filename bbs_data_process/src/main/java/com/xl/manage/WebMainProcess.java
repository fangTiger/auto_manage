package com.xl.manage;

import com.xl.basic.common.KafKaCommonData;
import com.xl.manage.bolt.*;
import com.xl.manage.common.WebCommonData;
import com.xl.manage.common.WebSpoutProducer;
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
public class WebMainProcess {

	public static void main(String[] args)throws Exception {
		BrokerHosts broker = new ZkHosts(KafKaCommonData.ZK_HOSTS);

		SpoutConfig webSpout = WebSpoutProducer.getSpout(broker, WebCommonData.WEB_STORM);

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("webSpout", new KafkaSpout(webSpout), 1);
		builder.setBolt("webDataProBolt", new WebDataProBolt(),3).localOrShuffleGrouping("webSpout");
		builder.setBolt("webAdvBolt", new WebAdvBolt(),3).localOrShuffleGrouping("webDataProBolt");
		builder.setBolt("webAffectionBolt", new WebAffectionBolt(),3).localOrShuffleGrouping("webAdvBolt");
		builder.setBolt("webFingerprintBolt", new WebFingerprintBolt(),3).localOrShuffleGrouping("webAffectionBolt");
		builder.setBolt("webSummaryProBolt", new WebSummaryProBolt(),3).localOrShuffleGrouping("webFingerprintBolt");
		builder.setBolt("webExtractionProBolt", new WebExtractionProBolt(),3).localOrShuffleGrouping("webSummaryProBolt");
		builder.setBolt("webMClassProBolt", new WebMClassProBolt(),3).localOrShuffleGrouping("webExtractionProBolt");
		builder.setBolt("webTClassProBolt", new WebTClassProBolt(),3).localOrShuffleGrouping("webMClassProBolt");
		builder.setBolt("webWClassProBolt", new WebWClassProBolt(),3).localOrShuffleGrouping("webTClassProBolt");
		builder.setBolt("webInsertProBolt", new WebInsertProBolt(),3).localOrShuffleGrouping("webWClassProBolt");

		Config config = new Config();
		config.setDebug(false);
		config.setMessageTimeoutSecs(72000);

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
