package com.comba.android.combacommon.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	/* 时间处理 */
	private static final SimpleDateFormat sdf_yyyymmddhhmiss = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
	private static final SimpleDateFormat sdf_yyyy_mm_dd_hh_mi_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
	private static final SimpleDateFormat sdf_yyyy_mm_dd_hh_mi_ss2 = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss", Locale.CHINA);
	private static final SimpleDateFormat sdf_yy_mm_dd_hh_mi_ss = new SimpleDateFormat("yyMMddHHmmss", Locale.CHINA);
	private static final SimpleDateFormat sdf_yyyy_mm_dd_hh_mi = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
	private static final SimpleDateFormat sdf_yyyymmdd = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
	private static final SimpleDateFormat sdf_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
	private static final SimpleDateFormat sdf_yyyy_mm_dd2 = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
	private static final SimpleDateFormat sdf_yyyymm = new SimpleDateFormat("yyyyMM", Locale.CHINA);
	private static final SimpleDateFormat sdf_yyyy_mm = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
	private static final SimpleDateFormat sdf_yymmdd = new SimpleDateFormat("yyMMdd", Locale.CHINA);
	private static final SimpleDateFormat sdf_hhmiss = new SimpleDateFormat("HHmmss", Locale.CHINA);
	private static final SimpleDateFormat sdf_hh_mi_ss = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
	private static final SimpleDateFormat sdf_e = new SimpleDateFormat("E", Locale.CHINA);

	/**
	 * 返回yyyymmddhhmiss
	 */
	public static String getStringForYYYYMMDDHHMISS(Date date) {
		return sdf_yyyymmddhhmiss.format(date);
	}

	public static Date getDateForYYYYMMDDHHMISS(String date) throws ParseException {
		return sdf_yyyymmddhhmiss.parse(date);
	}

	/**
	 * 返回yyyy-mm-dd hh:mi:ss
	 */
	public static String getStringForYYYY_MM_DD_HH_MI_SS(Date date) {
		return sdf_yyyy_mm_dd_hh_mi_ss.format(date);
	}

	/**
	 * 根据yyyy-mm-dd hh:mi:ss 返回DATE
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateForYYYY_MM_DD_HH_MI_SS(String date) throws ParseException {
		return sdf_yyyy_mm_dd_hh_mi_ss.parse(date);
	}

	/**
	 * 返回yyyy_mm_dd hh_mi_ss
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringForYYYY_MM_DD_HH_MI_SS2(Date date) {
		return sdf_yyyy_mm_dd_hh_mi_ss2.format(date);
	}

	/**
	 * 返回yy_mm_dd hh_mi_ss
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringForYY_MM_DD_HH_MI_SS(Date date) {
		return sdf_yy_mm_dd_hh_mi_ss.format(date);
	}

	/**
	 * 返回yyyy-mm-dd hh:mi
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringForYYYY_MM_DD_HH_MI(Date date) {
		return sdf_yyyy_mm_dd_hh_mi.format(date);
	}

	/**
	 * 返回yyyymmdd
	 */
	public static String getStringForYYYYMMDD(Date date) {
		return sdf_yyyymmdd.format(date);
	}

	/**
	 * 返回yyyy-mm-dd
	 */
	public static String getStringForYYYY_MM_DD(Date date) {
		return sdf_yyyy_mm_dd.format(date);
	}

	/**
	 * 返回yyyy/mm/dd
	 */
	public static String getStringForYYYY_MM_DD2(Date date) {
		return sdf_yyyy_mm_dd2.format(date);
	}

	/**
	 * 根据yyyy-mm-dd 返回DATE
	 * 
	 * @throws ParseException
	 */
	public static Date getDateForYYYY_MM_DD(String date) throws ParseException {
		return sdf_yyyy_mm_dd.parse(date);
	}

	/**
	 * 返回yyyymm
	 */
	public static String getStringForYYYYMM(Date date) {
		return sdf_yyyymm.format(date);
	}

	/**
	 * 返回yyyy-mm
	 */
	public static String getStringForYYYY_MM(Date date) {
		return sdf_yyyy_mm.format(date);
	}

	/**
	 * 返回yymmdd
	 */
	public static String getStringForYYMMDD(Date date) {
		return sdf_yymmdd.format(date);
	}

	/**
	 * 返回hhmiss
	 */
	public static String getStringForHHMISS(Date date) {
		return sdf_hhmiss.format(date);
	}

	/**
	 * 返回hh_mi_ss
	 */
	public static String getStringForHH_MI_SS(Date date) {
		return sdf_hh_mi_ss.format(date);
	}

	/**
	 * 获得yyyy-mm-dd hh:mi格式的long值
	 * 
	 * @param strdate
	 * @return
	 */
	public static long getStringTimeToLong(String strdate) {
		try {
			Date date = sdf_yyyy_mm_dd_hh_mi.parse(strdate);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取星期描述
	 */
	public static String getWeekday(Date date) {
		return sdf_e.format(date);
	}

	/**
	 * 获取星期几
	 */
	public static String getShortWeekday(Date date) {
		String str = sdf_e.format(date);
		return str.substring(str.length() - 1, str.length());
	}

	/**
	 * 获取星期几
	 */
	public static int getShortWeekdayForNumber(Date date) {
		String str = sdf_e.format(date);
		str = str.substring(str.length() - 1, str.length());
		if ("一".equals(str)) {
			return 1;
		} else if ("二".equals(str)) {
			return 2;
		} else if ("三".equals(str)) {
			return 3;
		} else if ("四".equals(str)) {
			return 4;
		} else if ("五".equals(str)) {
			return 5;
		} else if ("六".equals(str)) {
			return 6;
		} else if ("日".equals(str)) {
			return 7;
		}
		return 0;
	}

	/**
	 * 比较时间
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int comparTwoDate(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		Calendar anotherCalendar = Calendar.getInstance();
		anotherCalendar.setTime(date2);
		return anotherCalendar.compareTo(cal);
	}

	/**
	 * 偏移天数
	 * 
	 * @param date
	 * @param dayNum
	 *            正数向后调、负数向后调
	 * @return
	 */
	public static Date offsetDay(Date date, int dayNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, dayNum);
		return cal.getTime();
	}

	/**
	 * 判断两个时间是否符合要求 1不是同一个年月 2结束时间小于开始时间 0正常
	 */
	public static int time2Beyang(String bTime, String endTime) {
		try {
			Date date1 = sdf_yyyy_mm_dd.parse(bTime);
			Date date2 = sdf_yyyy_mm_dd.parse(endTime);
			if (date1.getMonth() != date2.getMonth() || date1.getYear() != date2.getYear())
				return 1;
			else if (date2.getTime() < date1.getTime())
				return 2;
			return 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 格式化时长，到天
	 * 
	 * @param second
	 * @return
	 */
	public static String formatSecond2Day(long second) {
		if (second < 60) {
			return second + "秒";
		} else if (second < 60 * 60) {
			int m = (int) (second / 60);
			int s = (int) (second % 60);
			return m + "分" + s + "秒";
		} else if (second < 60 * 60 * 24) {
			int h = (int) (second / 60 / 60);
			int m = (int) (second / 60 % 60);
			int s = (int) (second % 60);
			return h + "时" + m + "分" + s + "秒";
		} else {
			int d = (int) (second / 60 / 60 / 24);
			int h = (int) (second / 60 / 60 % 24);
			int m = (int) (second / 60 % 60);
			int s = (int) (second % 60);
			return d + "天" + h + "时" + m + "分" + s + "秒";
		}
	}
}
