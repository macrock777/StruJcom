package com.efive.agencyonline.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EfiveUtils {

	public static final String mailaddress = "email-smtp.us-east-1.amazonaws.com";

	public static long getTxnnumber() {
		Calendar calendar = Calendar.getInstance();
		String year = "" + calendar.get(Calendar.YEAR);
		String month = "" + (calendar.get(Calendar.MONTH) + 1);
		String day = "" + calendar.get(Calendar.DATE);
		String hour = "" + calendar.get(Calendar.HOUR);
		String minutes = "" + calendar.get(Calendar.MINUTE);
		String second = "" + calendar.get(Calendar.SECOND);
		StringBuilder sbNumber = new StringBuilder("" + year);
		if (month.length() == 1)
			month = "0" + month;
		if (day.length() == 1)
			day = "0" + day;
		if (hour.length() == 1)
			hour = "0" + hour;
		if (minutes.length() == 1)
			minutes = "0" + minutes;
		if (second.length() == 1)
			second = "0" + second;
		sbNumber.append(month);
		sbNumber.append(day);
		sbNumber.append(hour);
		sbNumber.append(minutes);
		sbNumber.append(second);

		return Long.parseLong(sbNumber.toString());
	}

	public static long getTxnnumberms() {
		Calendar calendar = Calendar.getInstance();
		String year = "" + calendar.get(Calendar.YEAR);
		String month = "" + (calendar.get(Calendar.MONTH) + 1);
		String day = "" + calendar.get(Calendar.DATE);
		String hour = "" + calendar.get(Calendar.HOUR);
		String minutes = "" + calendar.get(Calendar.MINUTE);
		String second = "" + calendar.get(Calendar.SECOND);
		String milisecond = "" + calendar.get(Calendar.MILLISECOND);
		StringBuilder sbNumber = new StringBuilder("" + year);
		if (month.length() == 1)
			month = "0" + month;
		if (day.length() == 1)
			day = "0" + day;
		if (hour.length() == 1)
			hour = "0" + hour;
		if (minutes.length() == 1)
			minutes = "0" + minutes;
		if (second.length() == 1)
			second = "0" + second;
		if (milisecond.length() == 1)
			second = "0" + second;
		sbNumber.append(month);
		sbNumber.append(day);
		sbNumber.append(hour);
		sbNumber.append(minutes);
		sbNumber.append(second);
		sbNumber.append(milisecond);

		return Long.parseLong(sbNumber.toString());
	}

	public static Timestamp getTodayTimestamp() {
		java.util.Date date = null;
		java.sql.Timestamp timeStamp = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			java.sql.Date dt = new java.sql.Date(calendar.getTimeInMillis());
			java.sql.Time sqlTime = new java.sql.Time(calendar.getTime()
					.getTime());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"MM/dd/yyyy hh:mm:ss");
			date = simpleDateFormat.parse(dt.toString() + " "
					+ sqlTime.toString());
			timeStamp = new java.sql.Timestamp(date.getTime());
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return timeStamp;
	}
}
