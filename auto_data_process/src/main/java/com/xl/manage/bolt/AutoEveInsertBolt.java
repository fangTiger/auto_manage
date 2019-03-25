package com.xl.manage.bolt;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.common.MessageScheme;
import com.xl.manage.bean.esBean.ArticleBean;
import com.xl.manage.service.AutoEveInsertService;
import com.xl.manage.service.AutoSubInsertService;
import com.xl.tool.LogHelper;
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
 * 汽车数据入库
 * @Author:lww
 * @Date:11:22 2017/9/8
 */
public class AutoEveInsertBolt extends BaseRichBolt {

	private OutputCollector outputCollector;
	private static final Logger log = LoggerFactory.getLogger(AutoEveInsertBolt.class);

	//prepare 方法worker初始化task的时候调用
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.outputCollector = outputCollector;
	}

	@Override
	public void execute(Tuple tuple) {

		boolean result;

		AutoEveInsertService service = new AutoEveInsertService();

		ArticleBean bean = (ArticleBean)tuple.getValueByField(MessageScheme.STRING_SCHEME_KEY);

		try{
			result = service.insertBean(bean);
			if(result){
				outputCollector.emit(tuple,new Values(bean));
				//对元组作为应答 成功时
				outputCollector.ack(tuple);
			}else{//处理异常 返回处理失败
				outputCollector.fail(tuple);
				LogHelper.info("-----------------------活动数据入库失败！AutoEveInsertBolt.execute ArticleBean["+ JSONObject.toJSONString(bean)+"]--------------------------------------");
			}
		}catch (Exception e){
			outputCollector.fail(tuple);
			LogHelper.error("----------------------活动数据入库出现异常！AutoEveInsertBolt.execute; ArticleBean["+ JSONObject.toJSONString(bean)+"]--------------------------",e);
		}
	}
	//declearOutputFields方法仅在有新的topology提交到服务器, 用来决定输出内容流的格式(相当于定义spout/bolt之间传输stream的name:value格式), 在topology执行的过程中并不会被调用.
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields(MessageScheme.STRING_SCHEME_KEY));
	}
}
