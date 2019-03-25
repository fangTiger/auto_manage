package com.xl.manage.bolt;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.WebOldBean;
import com.xl.basic.common.MessageScheme;
import com.xl.manage.bean.ArticleBean;
import com.xl.manage.common.CommonData;
import com.xl.manage.service.DataProService;
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
 * web数据格式化处理bolt
 * @Author:lww
 * @Date:18:30 2017/9/21
 */
public class DataProBolt extends BaseRichBolt {
	private OutputCollector outputCollector;
	private static final Logger log = LoggerFactory.getLogger(DataProBolt.class);

	//prepare 方法worker初始化task的时候调用
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.outputCollector = outputCollector;
	}

	@Override
	public void execute(Tuple tuple) {
		ArticleBean webBean;
		WebOldBean bean = (WebOldBean)tuple.getValueByField(MessageScheme.STRING_SCHEME_KEY);
		DataProService service = new DataProService();

		try{

			webBean = service.dealData(bean);
			if(webBean!=null){
				if(!"-1".equals(webBean.getAid())){//若AID为-1 此数据为过滤数据，不再入es
					outputCollector.emit(tuple,new Values(webBean));
				}
				//对元组作为应答 成功时
				outputCollector.ack(tuple);
			}else{
				outputCollector.fail(tuple);
				LogHelper.info("-------------------------------"+ CommonData.LOG_OUT_INFO+"数据格式化处理bolt失败！WebOldBean["+ JSONObject.toJSONString(bean)+"]");
			}
		}catch (Exception e){
			outputCollector.fail(tuple);
			LogHelper.info("----------------------"+ CommonData.LOG_OUT_INFO+"数据格式化处理bolt出现异常！DataProBolt.execute; WebOldBean["+ JSONObject.toJSONString(bean)+"]"+e.getMessage());
		}
	}
	//declearOutputFields方法仅在有新的topology提交到服务器, 用来决定输出内容流的格式(相当于定义spout/bolt之间传输stream的name:value格式), 在topology执行的过程中并不会被调用.
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields(MessageScheme.STRING_SCHEME_KEY));
	}
}
