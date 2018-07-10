package com.thkmon.util.date;

import java.util.Calendar;

public class DateUtil {
	
	public static String getTodayDateTime() {
		Calendar cal = Calendar.getInstance();
		StringBuffer dateTime = new StringBuffer();
		dateTime.append(String.format("%04d", cal.get(Calendar.YEAR)));
		dateTime.append(String.format("%02d", cal.get(Calendar.MONTH) + 1));
		dateTime.append(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
		dateTime.append(String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)));
		dateTime.append(String.format("%02d", cal.get(Calendar.MINUTE)));
		dateTime.append(String.format("%02d", cal.get(Calendar.SECOND)));
		return dateTime.toString();
	}
	
	
	public static String getTodayDateTimeMilSec() {
		Calendar cal = Calendar.getInstance();
		StringBuffer dateTime = new StringBuffer();
		dateTime.append(String.format("%04d", cal.get(Calendar.YEAR)));
		dateTime.append(String.format("%02d", cal.get(Calendar.MONTH) + 1));
		dateTime.append(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
		dateTime.append(String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)));
		dateTime.append(String.format("%02d", cal.get(Calendar.MINUTE)));
		dateTime.append(String.format("%02d", cal.get(Calendar.SECOND)));
		dateTime.append(String.format("%03d", cal.get(Calendar.MILLISECOND)));
		return dateTime.toString();
	}
}