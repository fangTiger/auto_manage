package com.xl.manage.bolt;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.AtomicIntegerBean;
import com.xl.basic.common.MessageScheme;
import com.xl.bean.article.ArticleBean;
import com.xl.manage.common.CommonData;
import com.xl.manage.service.AdvProService;
import com.xl.tools.LogHelper;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 情感值获取bolt
 * @Author:lww
 * @Date:15:30 2017/9/21
 */
public class AdvBolt extends BaseRichBolt {
	private OutputCollector outputCollector;
	private static final Logger log = LoggerFactory.getLogger(AdvBolt.class);

	//prepare 方法worker初始化task的时候调用
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.outputCollector = outputCollector;
	}

	@Override
	public void execute(Tuple tuple) {

		ArticleBean bean = (ArticleBean)tuple.getValueByField(MessageScheme.STRING_SCHEME_KEY);

		AdvProService service = new AdvProService();

		try{
			bean = service.dealData(bean);
			if(bean!=null){
				if(!"-1".equals(bean.getAid())){//若AID为-1 此数据为过滤数据，不再入es
					outputCollector.emit(tuple,new Values(bean));
				}else{
					AtomicIntegerBean.reduceAndGet();
				}
				//对元组作为应答 成功时
				outputCollector.ack(tuple);
			}else{
				AtomicIntegerBean.reduceAndGet();
				outputCollector.fail(tuple);
				LogHelper.info("-------------------------------"+ CommonData.LOG_OUT_INFO+"广告价值处理失败！ArticleBean["+ JSONObject.toJSONString(bean)+"]---------------------------------------------");
			}
		}catch (Exception e){
			AtomicIntegerBean.reduceAndGet();
			outputCollector.fail(tuple);
			LogHelper.info("----------------------"+ CommonData.LOG_OUT_INFO+"广告价值处理出现异常！AdvBolt.execute; ArticleBean["+ JSONObject.toJSONString(bean)+"]--------------------------"+e.getMessage());
		}
	}
	//declearOutputFields方法仅在有新的topology提交到服务器, 用来决定输出内容流的格式(相当于定义spout/bolt之间传输stream的name:value格式), 在topology执行的过程中并不会被调用.
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields(MessageScheme.STRING_SCHEME_KEY));
	}
}
