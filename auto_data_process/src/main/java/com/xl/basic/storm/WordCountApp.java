package com.xl.basic.storm;


/*
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
*/
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class WordCountApp {
    //public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException {
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException, InterruptedException {

        String path = WordCountApp.class.getResource("/").getPath();
        if(path.startsWith("/")){//linux不需要此步骤
            path = path.substring(1,path.length());
        }

        //定义拓扑
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader" , new WordReader());
        builder.setBolt("word-normalizer" , new WordNormalizer()).shuffleGrouping("word-reader" );
        builder.setBolt("word-counter" , new WordCounter()).fieldsGrouping("word-normalizer" , new Fields("word"));
        StormTopology topology = builder.createTopology();
        //配置

        Config conf = new Config();
        String fileName =path + "/words.txt" ;
        conf.put("fileName" , fileName );
        conf.setDebug(false);
        conf.setMaxTaskParallelism(1);

        //运行拓扑
/*
       LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Getting-Started-Topologie" , conf , topology );
        Thread. sleep(5000);
        cluster.shutdown();
*/
        //提交top

        if(args !=null&&args.length>0){ //有参数时，表示向集群提交作业，并把第一个参数当做topology名称
            StormSubmitter. submitTopology(args[0], conf, topology);
        } else{//没有参数时，本地提交
            LocalCluster localCluster=new LocalCluster();
            localCluster.submitTopology("wordcountapp" , conf , topology);
            Thread. sleep(100000);
            localCluster.shutdown();
        }
    }
}