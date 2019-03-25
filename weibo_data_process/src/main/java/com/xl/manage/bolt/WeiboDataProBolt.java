package com.xl.manage.bolt;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.WbOldBean;
import com.xl.basic.common.MessageScheme;
import com.xl.manage.bean.WeiboBean;
import com.xl.manage.service.WeiboDataProService;
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

import java.util.List;
import java.util.Map;

/**
 * @Author:lww
 * @Date:18:30 2017/9/21
 */
public class WeiboDataProBolt extends BaseRichBolt {
	private OutputCollector outputCollector;
	private static final Logger log = LoggerFactory.getLogger(WeiboDataProBolt.class);

	//prepare 方法worker初始化task的时候调用
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.outputCollector = outputCollector;
	}

	@Override
	public void execute(Tuple tuple) {
		List<WeiboBean> weiboList;
		WbOldBean bean = (WbOldBean)tuple.getValueByField(MessageScheme.STRING_SCHEME_KEY);
		WeiboDataProService service = new WeiboDataProService();

		try{
			if(bean.getWbstatusbean()!=null){
				weiboList = service.dealData(bean);
				if(weiboList!=null){
					for(WeiboBean weiboBean:weiboList){
						if(!"1".equals(StringUtil.toTrim(weiboBean.getStatusId()))){//等于1 为已入库数据
							outputCollector.emit(tuple,new Values(weiboBean));
						}
					}
					//对元组作为应答 成功时
					outputCollector.ack(tuple);
				}else{
					outputCollector.fail(tuple);
					LogHelper.info("-------------------------------weibo数据处理失败！WeiboOldBean["+ JSONObject.toJSONString(bean)+"]---------------------------------------------");
				}
			}else{
				LogHelper.info("数据Wbstatusbean对象为空！");
				outputCollector.ack(tuple);
			}
		}catch (Exception e){
			outputCollector.fail(tuple);
			LogHelper.info("----------------------weibo数据处理出现异常！WeiboDataProBolt.execute; WeiboOldBean["+ JSONObject.toJSONString(bean)+"]--------------------------"+e.getMessage());
		}
	}
	//declearOutputFields方法仅在有新的topology提交到服务器, 用来决定输出内容流的格式(相当于定义spout/bolt之间传输stream的name:value格式), 在topology执行的过程中并不会被调用.
	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields(MessageScheme.STRING_SCHEME_KEY));
	}
}
