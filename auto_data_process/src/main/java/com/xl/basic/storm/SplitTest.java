package com.xl.basic.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

/**
 * @Author:lww
 * @Date:16:42 2017/9/8
 */
public class SplitTest {
	public static void main(String[] args) {

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new SentenceSpout(), 1);
		builder.setBolt("bolt",new SplitSentenceBolt() , 1).globalGrouping("spout");
		builder.setBolt("bolt1",new SplitSentenceBolt1() , 1).globalGrouping("bolt");
		Config conf = new Config();
		conf.setDebug(false);

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("toplogy", conf, builder.createTopology());
		Utils.sleep(100000);
		cluster.shutdown();
	}
}
