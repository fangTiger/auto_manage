package com.xl.basic.storm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;

public class SentenceSpout extends BaseRichSpout {


    private SpoutOutputCollector spoutOutputCollector;


//    //为了简单,定义一个静态数据模拟不断的数据流产生
//      private static final String[] sentences={
//                        "The logic for a realtime",
//                        "A Storm topology is analogous to a MapReduce job"
//
//
//    };
    String[] array0 =  {"aa","bb","cc","aa","bb","bb","bb","cc","dd","","jj","mm"};
    String[] array1 =  {"11","22","33","11","55","22","66","11","33","","44","22"};
//    String[] array0 =  {"aa","bb","cc"};
//    String[] array1 =  {"11","22","33"};

    private int index=0;


    //初始化操作
     public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
                this.spoutOutputCollector = spoutOutputCollector;
            }




    //核心逻辑
    public void nextTuple() {

                if(index>=array0.length){
//                    index=0;
                    Utils.sleep(10000);
                    return;
                }
        spoutOutputCollector.emit(new Values(array0[index]),array0[index]);
        ++index;

    }



    //向下游输出
     public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

        // System. out.println("WordReader.declareOutputFields(OutputFieldsDeclarer declarer)");
                 outputFieldsDeclarer.declare(new Fields("field0"));
             }

}
