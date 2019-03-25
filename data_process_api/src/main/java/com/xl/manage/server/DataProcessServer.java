package com.xl.manage.server;

import com.xl.manage.bean.RequestBean;
import com.xl.manage.bean.ThumbnailBean;

/**
 * @Author:lww
 * @Date:10:22 2018/3/23
 */
public interface DataProcessServer {

	/**
	 * 添加任务
	 * @return
	 * @Author: lww
	 * @Description:
	 * @Date: 10:45 2018/3/23
	 * @param bean
	 */
	String putTask(RequestBean bean);

	/**
	 * 缩略图入库
	 * @return
	 * @Author: lww
	 * @Description:
	 * @Date: 14:20 2018/3/28
	 * @param bean
	 */
	String thumbnailToSql(ThumbnailBean bean);

	/**
	 * 大图处理
	 * @return
	 * @Author: lww
	 * @Description:
	 * @Date: 16:39 2018/3/28
	 */
	String bigImage(RequestBean bean);

}
