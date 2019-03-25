package com.xl.tool;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateHelper {

	private String minDate;
	private String maxDate;
	
	public String getMaxDate() {
		return maxDate;
	}

	public String getMinDate() {
		return minDate;
	}
	public static Date string2Date(String dateString)throws Exception{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		date = df.parse(dateString);
		return date;
	}


	public static String getBeforeByTime(String dateTime,int day,String format)throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		Date date=new Date();
		date = sdf.parse(dateTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		date = calendar.getTime();
		return sdf.format(date);
	}
	
	public static Date string3Date(String dateString)throws Exception{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = df.parse(dateString);
		return date;
	}
	public static String formatDateToStr(int date){
		SimpleDateFormat simpleDateFormat4Time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat4Time.format(date * 1000);
	}
	public void setDateType(int type) {
		Date date = new Date();
		Date today = new Date(System.currentTimeMillis());
		String mixDate = "";
		String maxDate = "";
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
		maxDate = simpleDateFormat.format(today).toString();
		if(type==1){
			//3天内
			date = new Date(System.currentTimeMillis()-(0x5265c00L*(3-1)));
			mixDate = simpleDateFormat.format(date).toString();
		}else if(type==2){
			//一周内
			date = new Date(System.currentTimeMillis()-(0x5265c00L*(7-1)));
			mixDate = simpleDateFormat.format(date).toString();
		}else if(type==3){
			//一月内
			date = new Date(System.currentTimeMillis()-(0x5265c00L*(30-1)));
			mixDate = simpleDateFormat.format(date).toString();
		}else if(type==4){
			//三月内
			date = new Date(System.currentTimeMillis()-(0x5265c00L*(90-1)));
			mixDate = simpleDateFormat.format(date).toString();
		}
		this.maxDate = maxDate;
		this.minDate = mixDate;
		//return simpleDateFormat.format(date);
	}
	/**
	 * 3天内，一周内，3个月内
	 * @Title: setDateType
	 * @data:2012-9-17下午05:58:30
	 * @author:zxd
	 *
	 * @param type
	 * @param datetype
	 */
	public void setDateType(int type,String datetype) {
		Date date = new Date();
		Date today = new Date(System.currentTimeMillis());
		String mixDate = "";
		String maxDate = "";
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(datetype);
		maxDate = simpleDateFormat.format(today).toString();
		if(type==1){
			//3天内
			date = new Date(System.currentTimeMillis()-(0x5265c00L*(3-1)));
			mixDate = simpleDateFormat.format(date).toString();
		}else if(type==2){
			//一周内
			date = new Date(System.currentTimeMillis()-(0x5265c00L*(7-1)));
			mixDate = simpleDateFormat.format(date).toString();
		}else if(type==3){
			//一月内
			date = new Date(System.currentTimeMillis()-(0x5265c00L*(30-1)));
			mixDate = simpleDateFormat.format(date).toString();
		}else if(type==4){
			//三月内
			date = new Date(System.currentTimeMillis()-(0x5265c00L*(90-1)));
			mixDate = simpleDateFormat.format(date).toString();
		}else if(type==0){
			date = new Date(System.currentTimeMillis()-(0x5265c00L*(0-1)));
			maxDate = simpleDateFormat.format(date).toString();
			mixDate = simpleDateFormat.format(today).toString();
		}
		this.maxDate = maxDate;
		this.minDate = mixDate;
	}
	/**
	 * 改变日志格式
	 * @param datetime
	 * @param oldFormat
	 * @param newFormat
	 * @return
	 */
	public static String formatDateString(String datetime,String oldFormat,String newFormat){
		Date date = null;
		try {
			if(datetime==null||datetime.equals("")){
				return "";
			}
			SimpleDateFormat formatter = new SimpleDateFormat(oldFormat);
			date=formatter.parse(datetime);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return getSpecifiedDate(date.getTime(), newFormat);
	}
	
	/**
	 * 将本地时间转换为其他格式
	 * @Title: formatLocaleDate
	 * @data:2016-11-15上午10:44:15
	 * @author:zhangshiyuan
	 *
	 * @param localDatetime
	 * @param format
	 * @return
	 */
	public static String formatLocaleDate(String localDatetime,String format){
		Date date = null;
		SimpleDateFormat formatter = null;
		try {
			if(localDatetime==null||localDatetime.equals("")){
				return "";
			}
			formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("ENGLISH", "CHINA"));
			date = formatter.parse(localDatetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		formatter = new SimpleDateFormat(format, new Locale("CHINESE", "CHINA"));
		return formatter.format(date);
	}
	
	/**
	 * 根据指定的格式显示时间
	 * 
	 * @param time
	 *            long
	 * @param format
	 *            String
	 * author xieyan           
	 * @return String
	 */
	public static String getSpecifiedDate(long time, String format) {
		if (time == 0) {
			return "-";
		}

		Date timeVal = new Date(time);

		String strTime = null;
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
			strTime = simpledateformat.format(timeVal);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strTime;
	}
	/**
	 * 格式 ： 输出指定格式类型的时间
	 * 
	 * @param formater
	 *            String
	 * author xieyan           
	 * @return String
	 */
	public static String getNowDate(String formater) {
		String strTime = null;
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(formater);
			strTime = simpledateformat.format(new Date());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strTime;
	}
	
	public static Date stringDate(String dateString)throws Exception{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = df.parse(dateString);
		return date;
	}
	
	/**
	 * string转换成long时间
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static long formatStringToLong(String dateString,String format){
		long result = 0l;
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		try {
			 result = simpledateformat.parse(dateString).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  result;
	}
	 public static String addDate(String strDate, int days) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date cDate = df.parse(strDate);
		GregorianCalendar gcalendar = new GregorianCalendar();
		gcalendar.setTime(cDate);
		gcalendar.add(Calendar.DATE, days);
		return df.format(gcalendar.getTime()).toString();
	}
	 
	 public static String getDayEndSecond(String dateTime){
		 //String result = "";
		 if(!dateTime.equals("")){
			 try {
				 dateTime = DateHelper.formatDateString(dateTime+" 23:59:59", DateHelper.FMT_DATE_DATETIME, FMT_DATE_DATETIME);
			} catch (Exception e) {
			}
		 }
		 
		 return dateTime;
	 }
	 
	public static Timestamp getTimestampNowDate() {
		Timestamp timestamp = null;
		try {
			timestamp = new Timestamp(new Date().getTime());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return timestamp;
	}
	 
    public static final String FMT_DATE_YYYY = "yyyy";
    public static final String FMT_DATE_YYMMDD = "yyMMdd";
    public static final String FMT_DATE_YYYYMMDD = "yyyyMMdd";
    public static final String FMT_DATE_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FMT_DATE_YYYY_MM_DD2 = "yyyy/MM/dd";
    public static final String FMT_DATE_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_DATE_DATETIME3 = "yyyy-MM-dd HH:mm:sss";
    public static final String FMT_DATE_DATETIME2 = "yyyy-MM-dd HH:mm";
    public static final String FMT_DATE_DATETIME4 = "yyyy/MM/dd HH:mm";
    public static final String FMT_DATE_DATETIME_TIGHT = "yyyyMMddHHmmss";
    public static final String FMT_DATE_YY_MM = "yy-MM";
    public static final String FMT_DATE_YYYY_MM = "yyyy-MM";
    public static final String FMT_DATA_YY_MM_DD="yy-MM-dd";
    public static final String FMT_DATE_MM = "MM";
    public static final String FMT_DATE_DD = "dd";
    public static final String FMT_DATE_TIME = "HH:mm:ss";
    public static final String FMT_DATE_HH = "HH";

    public static Map<String, String> MONTH = new HashMap<String, String>();//月份英文缩写形式转换
	static{
		MONTH.put("01","Jan");MONTH.put("02","Feb");MONTH.put("03","Mar");MONTH.put("04","Apr");MONTH.put("05","May");MONTH.put("06","Jun");
		MONTH.put("07","Jul");MONTH.put("08","Aug");MONTH.put("09","Sep");MONTH.put("10","Oct");MONTH.put("11","Nov");MONTH.put("12","Dec");
	}
   public static Map<String, String> MONTHALL = new HashMap<String, String>();//月份全英文缩写形式转换
		static{
			MONTHALL.put("01","January");MONTHALL.put("02","February");MONTHALL.put("03","March");MONTHALL.put("04","April");MONTHALL.put("05","May");MONTHALL.put("06","June");
			MONTHALL.put("07","July");MONTHALL.put("08","August");MONTHALL.put("09","September");MONTHALL.put("10","October");MONTHALL.put("11","November");MONTHALL.put("12","December");
		}
		
    private static Map<String, String> MONTH2 = new HashMap<String, String>();//NOV-11
	static{
		MONTH2.put("Jan","01");MONTH2.put("Feb","02");MONTH2.put("Mar","03");MONTH2.put("Apr","04");MONTH2.put("May","05");MONTH2.put("June","06");
		MONTH2.put("July","07");MONTH2.put("Aug","08");MONTH2.put("Sep","09");MONTH2.put("Oct","10");MONTH2.put("Nov","11");MONTH2.put("Dec","12");
		MONTH2.put("Jul","07");MONTH2.put("Jun", "06");
	}
    
	/**
	 * 英文时间字符串转换(月为双位)
	 * 2012-02-01  --->  01-Feb
	 * @Title: getENTimeStr
	 * @data:2012-8-6上午09:36:16
	 * @author:liweidong
	 *
	 * @param str
	 * @return
	 */
    public static String getENTimeStr(String str){
    	String result = "";
    	if(str!=null && str.length()==10){
    		try {
        		String month = str.substring(5, 7);
        		result = str.substring(str.lastIndexOf("-")+1) + "-" + MONTH.get(month);
			} catch (Exception e) {
				result = str;
				e.printStackTrace();
			}
    	}
    	return result;
    }
    /**
     * 专函月份
     * @Title: getEnMonth
     * @data:2012-9-7下午03:23:59
     * @author:zxd
     *
     * @param Month APR-04
     * @return
     */
    public static String getEnMonth(String Month){
    	String result = "";
    	if(Month!=null && !Month.equals("")){
    		result =  MONTH2.get(Month);
    	}
    	return result;
    }
    public static String getEnMonth1(String Month){
    	String result = "";
    	if(Month!=null && !Month.equals("")){
    		result =  MONTH.get(Month);
    	}
    	return result;
    }
    
    public static String getEnMonthAll(String Month){
    	String result = "";
    	if(Month!=null && !Month.equals("")){
    		result =  MONTHALL.get(Month);
    	}
    	return result;
    }
    /**
     * 转换日期格式Jul.26.2011-2011-7-26
					
     * @Title: changeDate
     * @data:2012-9-7下午03:34:23
     * @author:zxd
     *
     * @param date
     * @return
     */
    public static String changeDate(String date){
    	String result = "";
    	if(date!=null && !date.equals("")){
    		//月份
    		String mm = date.substring(0, date.indexOf("."));
    		//天
    		String dd = date.substring(0, date.lastIndexOf("."));
    		dd = dd.substring(dd.indexOf(".")+1);
    		//year
    		String yy = date.substring(date.lastIndexOf(".")+1,date.length());
    		mm = getEnMonth(mm);
    		result = yy+"-"+mm+"-"+dd;
    	}
		return result;
    }
    
    /**
     * 时间字符串转换
     * 2012-08-01  --->  8月1日
     * @Title: getCNTimeStr
     * @data:2012-9-3下午02:04:17
     * @author:liweidong
     *
     * @param str
     * @return
     */
    public static String getCNTimeStr(String str){
    	String result = "";
    	if(str!=null && str.length()==10){
    		try {
        		String month = str.substring(5, 7);
        		String day = str.substring(8);
        		if(month.startsWith("0")){
        			month = month.substring(1);
        		}
        		if(day.startsWith("0")){
        			day = day.substring(1);
        		}
        		result = month + "月" + day + "日";
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return result;
    }
    
    /**
     * 固定格式字符串转换
	 * 2012-08-21   ----->   Aug.21.2012
     * @Title: getTimeStr
     * @data:2012-9-10下午03:10:40
     * @author:liweidong
     *
     * @param str
     * @return
     */
    public static String getTimeStr(String str){
    	String result = "";
    	if(str!=null && str.length()==10){
    		try {
    			String month = str.substring(5, 7);
    			String day = str.substring(8);
    			String year = str.substring(0,4);
    			result = MONTH.get(month)+"."+day+"."+year;
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return result;
    }
    public static String getTimeStr2(String str){
    	String result = "";
    	if(str!=null && str.length()==10){
    		try {
    			String month = str.substring(5, 7);
    			String day = str.substring(8);
    			String year = str.substring(0,4);
    			result = day+"-"+MONTH.get(month)+"-"+year;
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return result;
    }
    /**
	 * 获取距离当前时间 days 天的时间
	 * @Title: setDate
	 * @data:2012-12-24下午04:09:50
	 * @author:liweidong
	 *
	 * @param days
	 * @param datetype
	 */
	public void setDate(int days,String datetype) {
		Date date = new Date();
		Date today = new Date(System.currentTimeMillis());
		String mixDate = "";
		String maxDate = "";
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(datetype);
		maxDate = simpleDateFormat.format(today).toString();
		date = new Date(System.currentTimeMillis()-(0x5265c00L*days));
		mixDate = simpleDateFormat.format(date).toString();
		this.maxDate = maxDate;
		this.minDate = mixDate;
	}
	
	/**
	 * 获取上周的周一和周天日期
	 * @Title: getLastWeekDate
	 * @data:2015-6-27下午1:20:14
	 * @author:zhangshiyuan
	 *
	 * @param chooseDate
	 * @return
	 * @throws ParseException
	 */
	public static String[] getLastWeekDate(String chooseDate,String newFormart) throws ParseException{
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date date =sdf.parse(chooseDate);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date);
		int dayOfWeek=calendar1.get(Calendar.DAY_OF_WEEK)-1; 
		int offset1=1-dayOfWeek; 
		int offset2=7-dayOfWeek; 
		calendar1.add(Calendar.DATE, offset1-7); 
		calendar2.add(Calendar.DATE, offset2-7); 
		String[] times = new String[2];
		sdf = new SimpleDateFormat(newFormart);
		times[0] = sdf.format(calendar1.getTime());//周一
		times[1] = sdf.format(calendar2.getTime());//周天
		return times;
	}
	
	/**
	 * 获取时间段内的每一天
	 * @Title: findDates
	 * @data:2014-3-26下午2:23:03
	 * @author:zhangshiyuan
	 *
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param formart 两个时间参数的格式
	 * @return
	 * @throws ParseException
	 */
	public static List<Date> findEveryDates(String startTime, String endTime,
			String formart) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(formart);
		Date dBegin = sdf.parse(startTime);
		Date dEnd = sdf.parse(endTime);
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}

	/**
	 * 判断时间段是否重叠
	 * @Author: lww
	 * @Description:
	 * @Date: 17:12 2017/7/8
	 * @params:
	 */
	public static Boolean isOverLaps(String beginDate1, String endDate1, String beginDate2, String endDate2, String dateformat){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
			Date date1b = sdf.parse(beginDate1);
			Date date1e = sdf.parse(endDate1);
			Date date2b = sdf.parse(beginDate2);
			Date date2e = sdf.parse(endDate2);

			if(date2b.compareTo(date1e)*date2e.compareTo(date1b)>0) return false;
			else{//判断是否是时间交接点:08:00-12:00 12:00-15:00
				/*if(date2b.equals(date1e) || date2e.equals(date1b)){
					return false;
				}else return true;*/
				return true;//若时间交叠,则说明存在重叠区域
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断时间格式是否正确
	 * @Author: lww
	 * @Description:
	 * @Date: 17:31 2017/7/8
	 * @params:
	 */
	public static boolean isValidDate(String str, String format) {
		//String str = "2007-01-02";
		DateFormat formatter = new SimpleDateFormat(format);
		try{
			Date date = (Date)formatter.parse(str);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * 判断时间是否在时间段内
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)||date.equals(begin)||date.equals(end)) {
			return true;
		} else {
			return false;
		}
	}
	

	public static Date stringToDate(String str,String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			// Fri Feb 24 00:00:00 CST 2012
			date = dateFormat.parse(str);
//			date = java.sql.Date.valueOf(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 获取多久之前的时间
	 * @Author: lww
	 * @Description:
	 * @Date: 15:58 2017/7/13
	 * @params:
	 */
	public static String getBeforeTime(String time,String format,int day) throws Exception {

		String dataTime = "";
		Calendar calendar;
		SimpleDateFormat sdf;

		try{

			sdf = new SimpleDateFormat(format);
			calendar = Calendar.getInstance();//获取日历实例
			calendar.setTime(sdf.parse(time));

			calendar.add(Calendar.DAY_OF_MONTH, day);  //正值设置为后一天 负值为前一天
			dataTime= sdf.format(calendar.getTime());//获得后一天
		}catch (Exception e){
			LogHelper.error("----------- DateHelper -> 解析时间出现异常(getBeforeTime)",e);
		}

		return dataTime;
	}

	/**
	 * 获取多少秒前/后的时间
	 * @Author: lww
	 * @Description:
	 * @Date: 9:46 2017/7/31
	 * @params:
	 */
	public static String getBeforeBySecond(String dateTime,String format,int second)throws Exception{
		String result = "";
		long dateLong = 0l;
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		Date date;
		try {
			dateLong = simpledateformat.parse(dateTime).getTime();
			date = new Date(dateLong+(second*1000));
			result = simpledateformat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  result;
	}

	/**
	 * 判断时间段1是否包含了时间段2
	 * @Author: lww
	 * @Description:
	 * @Date: 17:12 2017/7/8
	 * @params:
	 */
	public static Boolean isAllOverLaps(String beginDate1, String endDate1, String beginDate2, String endDate2, String dateformat){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
			Date date1b = sdf.parse(beginDate1);
			Date date1e = sdf.parse(endDate1);
			Date date2b = sdf.parse(beginDate2);
			Date date2e = sdf.parse(endDate2);

			if(date2b.compareTo(date1e)*date2e.compareTo(date1b)>0) return false;
			else{//判断是否是时间交接点:08:00-12:00 12:00-15:00
				if(date2b.compareTo(date1b)!=-1&&date1e.compareTo(date2e)!=-1){
					return true;
				}else return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}


	public static void main(String[] args)throws Exception {
		System.out.println(DateHelper.getBeforeBySecond("2017-06-01 23:59:59",DateHelper.FMT_DATE_DATETIME,1));

//		System.out.println(DateHelper.isOverLaps("2017-06-01 00:00:00","2017-06-01 00:00:02","2017-06-01 00:00:01","2017-06-01 00:00:03",DateHelper.FMT_DATE_DATETIME));
	}

}
