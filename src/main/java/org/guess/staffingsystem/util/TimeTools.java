package org.guess.staffingsystem.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 关于时间的工具类，包含一些常用处理时间的函数
 * @author Junv
 *
 */
public class TimeTools {
	
	/**
	 * 以“yyyy-MM-dd   hh:mm:ss”形式返回当前时间，精确到秒
	 * @return  {@link String}
	 */
	public static String getCurrentTime(){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
		//设置时间
		format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		Date date = new Date(System.currentTimeMillis());
		return format.format(date);
	}
	
	/**
	 * 获取当前日期，精确到天，格式为：yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//设置时间
		format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		Date date = new Date(System.currentTimeMillis());
		return format.format(date);
	}
	
	/**
	 * 获取当前时间，只是精确到分钟，格式为：yyyy-MM-dd hh:mm
	 * @return
	 */
	public static String getCurrentTimeNoSeconds(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//设置时间
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		Date date = new Date(System.currentTimeMillis());
		return format.format(date);
	}
	
	/**
	 * 以“yyyy-MM-dd   hh:mm:ss”形式返回格林威治时间，精确到秒
	 * @return  {@link String}
	 */
	public static String getGMT(){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		//设置时间
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = new Date(System.currentTimeMillis());
		return format.format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(TimeTools.getGMT());
		
	}

}
