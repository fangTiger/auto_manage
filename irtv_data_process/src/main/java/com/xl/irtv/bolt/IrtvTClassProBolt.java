package com.xl.irtv.bolt;

import com.alibaba.fastjson.JSONObject;
import com.xl.baisc.bean.AtomicIntegerBean;
import com.xl.baisc.common.MessageScheme;
import com.xl.bean.irtv.BroadcastBean;
import com.xl.irtv.service.IrtvClassProService;
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
 * 标签分类处理
 * @Author:lww
 * @Date:14:56 2017/9/14
 */
public class IrtvTClassProBolt extends BaseRichBolt{
	private OutputCollector outputCollector;
	private static final Logger log = LoggerFactory.getLogger(IrtvTClassProBolt.class);

	//prepare 方法worker初始化task的时候调用
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.outputCollector = outputCollector;
	}

	@Override
	public void execute(Tuple tuple) {

		IrtvClassProService service = new IrtvClassProService();

		BroadcastBean broadcastBean = (BroadcastBean)tuple.getValueByField(MessageScheme.STRING_SCHEME_KEY);
		try{
			broadcastBean = service.dealClass(broadcastBean,"2");
			if(broadcastBean!=null){
				outputCollector.emit(tuple,new Values(broadcastBean));
				//对元组作为应答 成功时
				outputCollector.ack(tuple);
			}else{
				AtomicIntegerBean.reduceAndGet();
				outputCollector.fail(tuple);
				LogHelper.info("------------------------------获取标签失败！BroadcastBean["+ JSONObject.toJSONString(broadcastBean)+"]---------------------------------------------");
			}
		}catch (Exception e){
			AtomicIntegerBean.reduceAndGet();
			outputCollector.fail(tuple);
			LogHelper.error("----------------------Irtv获取标签bolt出现异常！IrtvTClassProBolt.execute; BroadcastBean["+ JSONObject.toJSONString(broadcastBean)+"]--------------------------",e);
		}
	}
	//declearOutputFields方法仅在有新的topology提交到服务器, 用来决定输出内容流的格式(相当于定义spout/bolt之间传输stream的name:value格式), 在topology执行的过程中并不会被调用.
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields(MessageScheme.STRING_SCHEME_KEY));
	}
}
