package com.xl.basic.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SplitSentenceBolt1 extends BaseRichBolt {
    private OutputCollector outputCollector;
    private static final Logger log = LoggerFactory.getLogger(SplitSentenceBolt1.class);
    //prepare 方法worker初始化task的时候调用
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    //execute方法在每次有tuple进来的时候被调用
    public void execute(Tuple tuple) {
        String sentence = tuple.getStringByField("field0");

       // log.info("--------------------------分支流打印-------------------");

        log.info("--------------------------分支流打印-------------------"+sentence);


        /*String[] words = sentence.split(" ");
        for(String word : words){
            outputCollector.emit(new Values(word));
        }*/
    }

    //declearOutputFields方法仅在有新的topology提交到服务器, 用来决定输出内容流的格式(相当于定义spout/bolt之间传输stream的name:value格式), 在topology执行的过程中并不会被调用.
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("field0"));
    }
}
