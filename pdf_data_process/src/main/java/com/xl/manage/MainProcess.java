package com.xl.manage;

import com.xl.basic.common.KafKaCommonData;
import com.xl.manage.bolt.*;
import com.xl.manage.common.CommonData;
import com.xl.manage.common.SpoutProducer;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.topology.TopologyBuilder;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author:lww
 * @Date:13:56 2017/10/12
 */
public class MainProcess {

	public static void main(String[] args)throws Exception {
		BrokerHosts broker = new ZkHosts(KafKaCommonData.ZK_HOSTS);

		SpoutConfig webSpout = SpoutProducer.getSpout(broker, CommonData.STORM);

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new KafkaSpout(webSpout), 1);
		builder.setBolt("dataProBolt", new DataProBolt(),1).localOrShuffleGrouping("spout");
		builder.setBolt("PDFProBolt", new PDFProBolt(),1).localOrShuffleGrouping("dataProBolt");
		builder.setBolt("thumbProBolt", new ThumbProBolt(),1).localOrShuffleGrouping("PDFProBolt");
		builder.setBolt("advBolt", new AdvBolt(),1).localOrShuffleGrouping("thumbProBolt");
		builder.setBolt("affectionBolt", new AffectionBolt(),1).localOrShuffleGrouping("advBolt");
		builder.setBolt("fingerprintBolt", new FingerprintBolt(),1).localOrShuffleGrouping("affectionBolt");
		builder.setBolt("summaryProBolt", new SummaryProBolt(),1).localOrShuffleGrouping("fingerprintBolt");
		builder.setBolt("extractionProBolt", new ExtractionProBolt(),1).localOrShuffleGrouping("summaryProBolt");
		builder.setBolt("mClassProBolt", new MClassProBolt(),1).localOrShuffleGrouping("extractionProBolt");
		builder.setBolt("tClassProBolt", new TClassProBolt(),1).localOrShuffleGrouping("mClassProBolt");
		builder.setBolt("wClassProBolt", new WClassProBolt(),1).localOrShuffleGrouping("tClassProBolt");
		builder.setBolt("insertProBolt", new InsertProBolt(),1).localOrShuffleGrouping("wClassProBolt");

		Config config = new Config();
		config.setDebug(false);
		config.setMessageTimeoutSecs(7200);

		// 本地运行或者提交到集群
		if (args != null & args.length >= 1) {
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
