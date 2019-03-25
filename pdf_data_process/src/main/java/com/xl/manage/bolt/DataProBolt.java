package com.xl.manage.bolt;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.AtomicIntegerBean;
import com.xl.basic.bean.KafkaBean;
import com.xl.basic.common.MessageScheme;
import com.xl.bean.article.ArticleBean;
import com.xl.manage.common.CommonData;
import com.xl.manage.service.DataProService;
import com.xl.tools.LogHelper;
import com.xl.tools.StringUtil;
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
		KafkaBean bean = (KafkaBean)tuple.getValueByField(MessageScheme.STRING_SCHEME_KEY);
		DataProService service = new DataProService();

		try{

			if(("http://192.168.10.29/pdf/".equals(StringUtil.toTrim(bean.getPdfArticle().getArticleThumbName()))&&
					"http://192.168.10.29/pdf/".equals(StringUtil.toTrim(bean.getPdfArticle().getImageName()))&&
					StringUtil.toTrim(bean.getPdfArticle().getUrl()).length()>5000)||
					("无标题".equals(StringUtil.toTrim(bean.getPdfArticle().getTitle()))&&"".equals(StringUtil.toTrim(bean.getPdfArticle().getContentText())))){
				LogHelper.info("获取数据格式异常！跳过！媒体名称："+bean.getPdfArticle().getMediaNameCn());
				//对元组作为应答 成功时
				outputCollector.ack(tuple);
				AtomicIntegerBean.reduceAndGet();
			}else{
				//20180516 不再做限定拦截
				webBean = service.dealData(bean);
				if(webBean!=null){
					if(!"-1".equals(webBean.getAid())){//若AID为-1 此数据为过滤数据，不再入es
						outputCollector.emit(tuple,new Values(webBean));
					}else{
						AtomicIntegerBean.reduceAndGet();
					}
//						outputCollector.emit(tuple, new Values(webBean));
					//对元组作为应答 成功时
					outputCollector.ack(tuple);
				}else{
					AtomicIntegerBean.reduceAndGet();
					outputCollector.fail(tuple);
					LogHelper.info("-------------------------------"+ CommonData.LOG_OUT_INFO+"数据格式化处理bolt失败！WebOldBean["+ JSONObject.toJSONString(bean)+"]");
				}

				/*if(CommonData.mediaNameList.contains(StringUtil.toTrim(bean.getPdfArticle().getMediaNameCn()))){

				}else{
					AtomicIntegerBean.reduceAndGet();
					//对元组作为应答 成功时
					outputCollector.ack(tuple);
				}*/
			}
		}catch (Exception e){
			AtomicIntegerBean.reduceAndGet();
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
