package storm;

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

//    JSONObject jsonObject = JSONObject.parseObject("{\"TitleContent\":\"TEXT\",\"WgRelation\":\"and\",\"KeyWord\":\"麦当劳,McDonald's,McDonald,麦咖啡\",\"Rate\":2}");
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
        NameTest test = new NameTest();
                test.setName("zhangsan");
                test.setAge(10);
                test.set_alias("abc");
        ClassBean bean = new ClassBean();
        bean.setName("一年级");
        bean.setClassId(11);
        test.setClassBean(bean);
        SchoolBean schoolBean = new SchoolBean();
        schoolBean.setName("xue xiao");
        schoolBean.setAddress("dizhi");
        bean.setSchoolBean(schoolBean);
        spoutOutputCollector.emit(new Values(test));
        ++index;

    }



    //向下游输出
     public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

        // System. out.println("WordReader.declareOutputFields(OutputFieldsDeclarer declarer)");
                 outputFieldsDeclarer.declare(new Fields("field"));
             }

}
