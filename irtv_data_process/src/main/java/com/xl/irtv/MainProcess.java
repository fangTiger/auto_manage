package com.xl.irtv;

import com.xl.baisc.common.KafKaCommonData;
import com.xl.irtv.bolt.*;
import com.xl.irtv.common.IrtvCommonData;
import com.xl.irtv.common.IrtvSpoutProducer;
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

		SpoutConfig autoSpout = IrtvSpoutProducer.getSpout(broker, IrtvCommonData.IRTV_STORM);

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("irtvSpout", new KafkaSpout(autoSpout), 1);

		builder.setBolt("irtvDataProBolt", new IrtvDataProBolt(),1).localOrShuffleGrouping("irtvSpout");

		builder.setBolt("irtvMClassProbolt", new IrtvMClassProBolt(),1).localOrShuffleGrouping("irtvDataProBolt");

		builder.setBolt("irtvTClassProBolt", new IrtvTClassProBolt(),1).localOrShuffleGrouping("irtvMClassProbolt");

		builder.setBolt("irtvWClassProBolt", new IrtvWClassProBolt(),1).localOrShuffleGrouping("irtvTClassProBolt");

		builder.setBolt("irtvExtractionProBolt", new IrtvExtractionProBolt(),1).localOrShuffleGrouping("irtvWClassProBolt");

		builder.setBolt("irtvInsertProBolt", new IrtvInsertProBolt(),1).localOrShuffleGrouping("irtvExtractionProBolt");

		Config config = new Config();
		config.setDebug(false);
		config.setMessageTimeoutSecs(3600);//设置超时时间 1个小时

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
