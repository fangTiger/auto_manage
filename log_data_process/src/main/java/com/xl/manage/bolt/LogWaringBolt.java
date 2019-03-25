package com.xl.manage.bolt;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.LogOldBean;
import com.xl.basic.common.MessageScheme;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.service.LogWarningService;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.MessageId;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 日志预警bolt
 * @Author:lww
 * @Date:16:34 2017/11/8
 */
public class LogWaringBolt extends BaseRichBolt{
	private OutputCollector outputCollector;
	private static final Logger log = LoggerFactory.getLogger(LogWaringBolt.class);
	//prepare 方法worker初始化task的时候调用
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.outputCollector = outputCollector;
	}

	@Override
	public void execute(Tuple tuple) {

		LogOldBean logOldBean = (LogOldBean)tuple.getValueByField(MessageScheme.STRING_SCHEME_KEY);

		LogWarningService service = new LogWarningService();

		try{
			logOldBean = service.warning(logOldBean);
			if (logOldBean != null) {
				outputCollector.emit(tuple, new Values(logOldBean));
				//对元组作为应答 成功时
				outputCollector.ack(tuple);
			} else {
				outputCollector.fail(tuple);
				LogHelper.info("-------------------------------" + LogCommonData.LOG_OUT_INFO + "日志预警失败！logOldBean[" + JSONObject.toJSONString(logOldBean) + "]---------------------------------------------");
			}
		}catch (Exception e){
			outputCollector.fail(tuple);
			LogHelper.error("----------------------"+ LogCommonData.LOG_OUT_INFO+"日志预警出现异常！LogWaringBolt.execute; logOldBean["+ JSONObject.toJSONString(logOldBean)+"]--------------------------",e);
		}
	}
	//declearOutputFields方法仅在有新的topology提交到服务器, 用来决定输出内容流的格式(相当于定义spout/bolt之间传输stream的name:value格式), 在topology执行的过程中并不会被调用.
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields(MessageScheme.STRING_SCHEME_KEY));
	}
}
