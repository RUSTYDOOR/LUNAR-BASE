package lunar.common.date;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import lunar.common.text.TextUtil;

public class DateUtil {
	public DateUtil() {
		//
	}

	/**
	 * <PRE>
	 * Descriptions : 특정형태의 날짜를 다른 형태의 포맷으로 변경한다.
	 * </PRE>
	 *
	 * @param sDate String 변경하고자 하는 날짜. "2005-01-17"
	 * @param fromFormat   String sDate가 가지는 날짜 형식 "yyyy-MM-dd"
	 * @param toFormat     변환하고자 하는 날짜 형식 "MM/dd/yyyy hh:mm:ss a"
	 * @return String      형 변환 후 리턴되는 값.
	 */
	public static String convertDateFormat(String sDate, String fromFormat, String toFormat) {
		String sRet = sDate;
		try {
			SimpleDateFormat sd = new SimpleDateFormat(fromFormat, Locale.KOREA);
			Date d = sd.parse(sDate);
			sd.applyPattern(toFormat);
			sRet = sd.format(d);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return sRet;
	}

	/**
	 * <PRE>
	 * Descriptions : 문자열을 파싱하여 날짜형으로 리턴한다.
	 * </PRE>
	 *
	 * @param sDate String
	 * @param sFormat String
	 * @return Date
	 */
	public static Date convertToDate(String sDate, String sFormat) {
		Date d = null;

		try {
			if (TextUtil.isNullOrEmpty(sDate))
				return d;

			SimpleDateFormat sd = new SimpleDateFormat(sFormat, Locale.KOREA);
			d = sd.parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}

	// ***************************************************************************************
	// get Current date string
	// ***************************************************************************************
	public static String getCurrentDateString(String sFormat) {
		Date d = new Date();
		String sRet = "";

		try {
			SimpleDateFormat sd = new SimpleDateFormat(sFormat, Locale.KOREA);
			sRet = sd.format(d);
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return sRet;
	}

	// ***************************************************************************************
	// get Current date string
	// ***************************************************************************************
	public static String getCurrentDateString(Date date, String sFormat) {
		Date d = new Date();
		String sRet = "";

		try {
			SimpleDateFormat sd = new SimpleDateFormat(sFormat, Locale.KOREA);
			sRet = sd.format(d);
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return sRet;
	}

	// ***************************************************************************************
	// get Current year
	// ***************************************************************************************
	public static String getCurrentYear(String sFormatYear) {
		return getCurrentDateString(sFormatYear);
	}

	// ***************************************************************************************
	// get Current month
	// ***************************************************************************************
	public static String getCurrentMonth(String sFormatMonth) {
		return getCurrentDateString(sFormatMonth);
	}

	// ***************************************************************************************
	// get Current month
	// ***************************************************************************************
	public static String getCurrentDay(String sFormatDay) {
		return getCurrentDateString(sFormatDay);
	}

	/*
	 * <p> <pre> long date = DateUtil.getDifferDays("20100101", "20100202");
	 */
	public static long getDifferDays(String startDate, String endDate) {
		GregorianCalendar StartDate = getGregorianCalendar(startDate);
		GregorianCalendar EndDate = getGregorianCalendar(endDate);
		long difer = (EndDate.getTime().getTime() - StartDate.getTime().getTime()) / 86400000;

		return difer;
	}

	public static GregorianCalendar getGregorianCalendar(String yyyymmdd) {
		int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
		int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
		int dd = Integer.parseInt(yyyymmdd.substring(6));

		GregorianCalendar calendar = new GregorianCalendar(yyyy, mm - 1, dd, 0, 0, 0);

		return calendar;
	}

	public static String getFormatDate(String date, String orignalformat, String wantfromat) {
		Date d = null;
		SimpleDateFormat dd = new SimpleDateFormat(orignalformat);
		ParsePosition parse = new ParsePosition(0);
		d = dd.parse(date, parse);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		SimpleDateFormat sdf = new SimpleDateFormat(wantfromat);
		String day = sdf.format(cal.getTime());

		return day;
	}

	/**
	 * 두날짜 사이의 일수를 리턴
	 *
	 * @param fromDate yyyyMMdd 형식의 시작일
	 * @param toDate   yyyyMMdd 형식의 종료일
	 * @return 두날짜 사이의 일수
	 */
	public static int getDiffDayCount(String fromDate, String toDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		try {
			return (int) ((sdf.parse(toDate).getTime() - sdf.parse(fromDate).getTime()) / 1000 / 60 / 60 / 24);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 시작일부터 종료일까지 사이의 날짜를 배열에 담아 리턴 ( 시작일과 종료일을 모두 포함한다 )
	 *
	 * @param fromDate yyyyMMdd 형식의 시작일
	 * @param toDate   yyyyMMdd 형식의 종료일
	 * @return yyyyMMdd 형식의 날짜가 담긴 배열
	 */
	public static String[] getDiffDays(String fromDate, String toDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Calendar cal = Calendar.getInstance();

		try {
			cal.setTime(sdf.parse(fromDate));
		} catch (Exception e) {
			//
		}

		int count = getDiffDayCount(fromDate, toDate);

		// 시작일부터
		cal.add(Calendar.DATE, -1);

		// 데이터 저장
		List list = new ArrayList();

		for (int i = 0; i <= count; i++) {
			cal.add(Calendar.DATE, 1);

			list.add(sdf.format(cal.getTime()));
		}

		String[] result = new String[list.size()];

		list.toArray(result);

		return result;
	}

	/**
	 * 현재 날짜에서 변경 요청한 날짜 더하거나 빼서 리턴
	 *
	 * @param '숫자(일수)' 집어넣기
	 * @return yyyyMMdd 형식의 날짜가 담긴 날짜
	 */
	public static String previosDate(int sub) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		Date date = null;

		try {
			date = fmt.parse(fmt.format(new Date()));
			c.setTime(date);
			c.add(Calendar.DAY_OF_YEAR, sub);
		} catch (Exception ex) {
			//
		}

		return fmt.format(c.getTime());
	}

	/**
	 * 날짜에 - 를 넣는다.
	 *
	 * @param 'yyyyMMdd'    형식
	 * @return 'yyyy-MM-dd' 형식의 날짜가 담긴 날짜
	 */
	public static String addDash(String dash) {
		String dash_temp = "";

		if ((dash == null) || (dash.equals(""))) {
			return "";
		} else {
			dash_temp = dash.replace("-", "");
			return dash_temp.substring(0, 4) + "-" + dash_temp.substring(4, 6) + "-" + dash_temp.substring(6, 8);
		}
	}

	/* 입력한 날짜기 유효한 날짜인지 체크 */
	public static boolean checkDate(String year, String month, String day) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			Date result = formatter.parse(year + "." + month + "." + day);
			String resultStr = formatter.format(result);

			if (resultStr.equalsIgnoreCase(year + "." + month + "." + day))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/***
	 * 입력한 시간이 유효한지 체크
	 *
	 * @param hh 시
	 * @param mm
	 * @param ss
	 * @return boolean
	 */
	public static boolean checkTime(String hh, String mm, String ss) {
		try {
			int h = Integer.parseInt(hh);
			int m = Integer.parseInt(mm);
			int s = Integer.parseInt(ss);

			if (h < 0 || h > 23)
				return false;
			if (m < 0 || m > 59)
				return false;
			if (s < 0 || s > 50)
				return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/***
	 * 현재 년도 가져오기
	 *
	 * @return String
	 */
	public static String nowYearGet() {
		Calendar calendar = Calendar.getInstance();
		return Integer.toString(calendar.get(Calendar.YEAR));
	}

	/***
	 * 현재 월 가져오기
	 *
	 * @return String
	 */
	public static String nowMonthGet() {
		Calendar calendar = Calendar.getInstance();
		int nMonth = calendar.get(Calendar.MONTH) + 1;

		if (nMonth < 10)
			return "0" + Integer.toString(nMonth);
		else
			return Integer.toString(nMonth);
	}

	/***
	 * 현재 일 가져오기
	 *
	 * @return String
	 */
	public static String nowDayGet() {
		Calendar calendar = Calendar.getInstance();
		int nDay = calendar.get(Calendar.DAY_OF_MONTH);

		if (nDay < 10)
			return "0" + Integer.toString(nDay);
		else
			return Integer.toString(nDay);
	}

	/***
	 * 현재 시간 가져오기
	 *
	 * @return String
	 */
	public static String nowHourGet() {
		Calendar calendar = Calendar.getInstance();
		int nHour = calendar.get(Calendar.HOUR_OF_DAY);

		if (nHour < 10)
			return "0" + Integer.toString(nHour);
		else
			return Integer.toString(nHour);
	}

	/***
	 * 현재 분 가져오기
	 *
	 * @return String
	 */
	public static String nowMinuteGet() {
		Calendar calendar = Calendar.getInstance();
		int nMinute = calendar.get(Calendar.MINUTE);

		if (nMinute < 10)
			return "0" + Integer.toString(nMinute);
		else
			return Integer.toString(nMinute);
	}

	/**
	 * 현재 년월일시분초 값을 가져옴
	 *
	 * @return yyyyMMddHHmmss 형식의 날짜
	 */
	public static String nowDate() {
		Calendar cal = Calendar.getInstance();
		Date currentTime = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String ndate = formatter.format(currentTime);

		return ndate;
	}

	public static String cutDate(String sTmpDate, String flag) {
		String sRtnValue = null;

		if (flag.equals("YYYY")) {
			sRtnValue = sTmpDate.substring(0, 4);
		} else if (flag.equals("MM")) {
			sRtnValue = sTmpDate.substring(4, 6);
		} else if (flag.equals("DD")) {
			sRtnValue = sTmpDate.substring(6, 8);
		}

		return sRtnValue;
	}

	private static SimpleDateFormat getDateFormat(String format) {
		return new SimpleDateFormat(format, Locale.KOREA);
	}

	/**
	 * 현재 시간에 대한 timestamp
	 *
	 * @return long
	 */
	public static long mktime() {
		Calendar cal = Calendar.getInstance();
		return cal.getTimeInMillis();
	}

	/**
	 * <pre>
	 *
	 * 지정된 날짜/포맷에 대한 timestampe
	 *
	 * mktime("2013-01-05", "yyyy-MM-dd");
	 * </pre>
	 *
	 * @param value
	 * @param dateFormat
	 * @return
	 * @throws ParseException
	 */
	public static long mktime(String value, String dateFormat) throws ParseException {
		SimpleDateFormat simpleDateFormat = getDateFormat(dateFormat);
		Date parsed = simpleDateFormat.parse(value);

		return parsed.getTime();
	}

	/**
	 * timestamp 에 대한 현재시간
	 */
	public static String convertTimestampToDate(long lTime, String dateFormat) throws Exception {
		Date d = new Date(lTime);

		String sRet = getTimestampToDateString(d, dateFormat);

		return sRet;
	}

	// ***************************************************************************************
	// get Current date string
	// ***************************************************************************************
	public static String getTimestampToDateString(Date date, String sFormat) {
		String sRet = "";

		try {
			SimpleDateFormat sd = new SimpleDateFormat(sFormat, Locale.KOREA);
			sRet = sd.format(date);
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return sRet;
	}
}