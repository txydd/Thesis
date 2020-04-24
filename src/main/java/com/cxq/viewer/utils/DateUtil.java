package com.cxq.viewer.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtil
{
	//yyyy-MM-dd
	public static String dateToString(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (date == null) {
			return "";
		}
		return sdf.format(date);
	}

	public static Date stringToDate(String time)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		Date date;
		try {
			date = sdf.parse(time);
			return date;
		}catch (ParseException e){
			log.error(e.getMessage());
			return new Date();
		}
	}

	public static Date stringToDateTime(String time)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setLenient(false);
		Date date;
		try {
			date = sdf.parse(time);
			return date;
		}catch (ParseException e){
			log.error(e.getMessage());
			return new Date();
		}
	}

	public static String getDaysDiff(Date start,Date end){
		long from  = start.getTime();
		long to = end.getTime();
		Long diff = to-from;
		double diff_d = diff.doubleValue();
		System.out.println(diff);
		DecimalFormat df = new DecimalFormat("0.00");
		double res = diff/(double)(1000*24*60*60);
		return df.format(res);
	}

	public static Date addDayToNow(Integer days){
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH,days);
		return now.getTime();
	}
	public static Date addMonthToNow(Integer month){
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH,month);
		return now.getTime();
	}
}
