package com.xl.tf.bolt;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.OverseaEsBean;
import com.xl.basic.common.MessageScheme;
import com.xl.tf.bean.TfBean;
import com.xl.tf.service.TfAffectionService;
import com.xl.tf.service.TfDataProService;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;
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
 * @Author:lww
 * @Date:18:30 2017/9/21
 */
public class TfDataProBolt   extends BaseRichBolt {
	private OutputCollector outputCollector;
	private static final Logger log = LoggerFactory.getLogger(TfAffectionBolt.class);

	//prepare 方法worker初始化task的时候调用
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.outputCollector = outputCollector;
	}

	@Override
	public void execute(Tuple tuple) {
		TfBean tfBean;
		OverseaEsBean bean = (OverseaEsBean)tuple.getValueByField(MessageScheme.STRING_SCHEME_KEY);
		TfDataProService service = new TfDataProService();

		try{
			tfBean = service.dealData(bean);
			if(tfBean!=null){
				if(!"".equals(StringUtil.toTrim(tfBean.getStatusText()))){
					outputCollector.emit(tuple,new Values(tfBean));
					//对元组作为应答 成功时
					outputCollector.ack(tuple);
				}else{
					outputCollector.ack(tuple);
				}
			}else{
				outputCollector.fail(tuple);
				LogHelper.info("-------------------------------Tf数据处理失败！OverseaEsBean["+ JSONObject.toJSONString(bean)+"]---------------------------------------------");
			}
		}catch (Exception e){
			outputCollector.fail(tuple);
			LogHelper.error("----------------------Tf数据处理出现异常！TfAffectionBolt.execute; OverseaEsBean["+ JSONObject.toJSONString(bean)+"]--------------------------",e);
		}
	}
	//declearOutputFields方法仅在有新的topology提交到服务器, 用来决定输出内容流的格式(相当于定义spout/bolt之间传输stream的name:value格式), 在topology执行的过程中并不会被调用.
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields(MessageScheme.STRING_SCHEME_KEY));
	}
}
