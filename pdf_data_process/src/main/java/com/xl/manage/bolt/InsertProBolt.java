package com.xl.manage.bolt;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.AtomicIntegerBean;
import com.xl.basic.common.MessageScheme;
import com.xl.bean.article.ArticleBean;
import com.xl.manage.common.CommonData;
import com.xl.manage.service.InsertProService;
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
 * 入库处理
 * @Author:lww
 * @Date:14:56 2017/9/14
 */
public class InsertProBolt extends BaseRichBolt{
	private OutputCollector outputCollector;
	private static final Logger log = LoggerFactory.getLogger(InsertProBolt.class);

	//prepare 方法worker初始化task的时候调用
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.outputCollector = outputCollector;
	}

	@Override
	public void execute(Tuple tuple) {

		ArticleBean bean = (ArticleBean)tuple.getValueByField(MessageScheme.STRING_SCHEME_KEY);
		InsertProService service = new InsertProService();
		try{
			bean = service.insertData(bean);
			if(bean!=null){
				outputCollector.emit(tuple,new Values(bean));
				//对元组作为应答 成功时
				AtomicIntegerBean.reduceAndGet();
				outputCollector.ack(tuple);
			}else{
				AtomicIntegerBean.reduceAndGet();
				outputCollector.fail(tuple);
				LogHelper.info("-------------------------------"+ CommonData.LOG_OUT_INFO+"数据入库失败！ArticleBean["+ JSONObject.toJSONString(bean)+"]---------------------------------------------");
			}
		}catch (Exception e){
			AtomicIntegerBean.reduceAndGet();
			outputCollector.fail(tuple);
			LogHelper.info("----------------------"+ CommonData.LOG_OUT_INFO+"入库bolt出现异常！InsertProBolt.execute; ArticleBean["+ JSONObject.toJSONString(bean)+"]--------------------------"+e.getMessage());
		}
	}
	//declearOutputFields方法仅在有新的topology提交到服务器, 用来决定输出内容流的格式(相当于定义spout/bolt之间传输stream的name:value格式), 在topology执行的过程中并不会被调用.
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields(MessageScheme.STRING_SCHEME_KEY));
	}
}
