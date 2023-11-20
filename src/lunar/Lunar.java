package lunar;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Project: Lunar :: 양력/음력 변환 클래스<br>
 * File:    Lunar
 *
 * 이 패키지는 양력/음력간의 변환을 제공한다.
 *
 * 1852년 10월 15일 이전의 양력 날짜는 율리우스력으로 취급을 하며,
 * 내부 계산시에 그레고리력으로 변환을 하여 계산을 한다.
 *
 * 제공 되는 기능은 다음과 같다.
 *
 * 1. 양력/음력 변환 API
 * 2. 절기 API
 * 3. 합삭/망 정보 API
 * 4. 세차/월간/일진 API 등
 *
 * 이 변환 API의 유효기간은 다음과 같다.
 *
 * <pre>
 *   * 32bit
 *     + -2087-02-09(음력 -2087-01-01) ~ 6078-01-29(음 6077-12-29)
 *     + -2087-07-05(음력 -2087-05-29) 이전은 계산이 무지 느려짐..
 *
 *   * 64bit
 *     + -4712-02-08 ~ 9999-12-31
 *     + API의 연도 체크가 4자리 까지이므로 10000년 이상은 확인 못함
 *     + 64bit 계산이 가능한 시점까지 가능할 듯..
 *     + 기원전의 경우 Julian date가 BC 4713년 1월 1일 부터이므로
 *       Gregorian calendar 변환이 가능한 BC 4713년 2월 8일부터 가능
 * </pre>
 *
 * 계산 처리 시간상, 과거 2000년전과 미래 100년후의 시간은 왠만하면 웹에서는
 * 사용하는 것을 권장하지 않음!
 *
 * 주의!
 *
 * Lunar package는 2가지 라이센스를 가지고 있다.
 * Lunar 패키지의 Core API (Lunar/LunarBase.java)는
 * 고영창님의 '진짜만세력' 코드를 PHP 에서 Java 로 포팅한 것으로 고영창님에게 라이센스가 있으며,
 * front end API(Lunar.java)는 김정균이 작성한 PHP 코드를 Java 로 포팅한 것으로 BSD license 를 따른다.
 *
 * @category    Calendar
 * @package     Lunar
 * @author      izoka <http://izoka.pe.kr>
 * @copyright   (c) 2016 izoka.pe.kr
 * @license     BSD (Lunar.java) And 고영창(Lunar/LunarBase.java)
 * @since       File available since release 0.0.1
 */

import lunar.common.date.DateUtil;
import lunar.common.text.TextUtil;
import lunar.consts.LunarConstants;

public class Lunar extends LunarBase {
    /**
     * 입력된 날짜 형식을 연/월/일의 멤버를 가지는 배열로 반환한다.
     * 입력된 변수 값은 YYYY-MM-DD 형식으로 변환 된다.
     *
     *
     * @access public
     *   <pre>
     *       Array
     *       (
     *           [0] => 2013
     *           [1] => 7
     *           [2] => 16
     *       )
     *   </pre>
     * @param v|int 날짜형식
     *
     *   - unixstmap (1970년 12월 15일 이후부터 가능)
     *   - Ymd or Y-m-d
     *   - null data (현재 시간)
     *
     * @return array
     */
    public int[] toargs (String v) throws Exception {
        int y = 0;
        int m = 0;
        int d = 0;

        long lTimeStamp;

        if ( TextUtil.isNullOrEmpty(v) ) {
            y = Integer.valueOf( DateUtil.getCurrentYear("yyyy") );
            m = Integer.valueOf( DateUtil.getCurrentMonth("MM") );
            d = Integer.valueOf( DateUtil.getCurrentDay("dd") );
        } else {
            // 날짜문자열 분리부분
            y = Integer.valueOf( DateUtil.cutDate(v, "yyyy") );
            m = Integer.valueOf( DateUtil.cutDate(v, "MM") );
            d = Integer.valueOf( DateUtil.cutDate(v, "dd") );

            if ( y > 1969 && y < 2038 ) {
                lTimeStamp = DateUtil.mktime(DateUtil.addDash(v), "yyyy-MM-dd");

                y = Integer.valueOf( DateUtil.convertTimestampToDate(lTimeStamp, "yyyy") );
                m = Integer.valueOf( DateUtil.convertTimestampToDate(lTimeStamp, "MM") );
                d = Integer.valueOf( DateUtil.convertTimestampToDate(lTimeStamp, "dd") );
            } else {
                if ( m > 12 || d > 31 ) {
                    throw new Exception ("Invalid Date Format");
                }
            }
        }

        //- Not Used
        //- $v = sprintf ('%d-%d-%d', $y, $m, $d);

        //
        int[] iArr = {y, m, d};

        return iArr;
    }


    /**
     * 연도를 human readable하게 표시
     *
     * @access public
     * @param y 연도
     * @return string   AD/BC type의 연도
     */
    public String human_year(int y) {
        String t;

        if (y < 1) {
            y = (y * -1) + 1;
            t = "BC";
        } else {
            t = "AD";
        }

        return String.format("%s %d", t, y);
    }


    /**
     * 해당 날자가 gregorian 범위인지 체크
     *
     * @param iYear     연도
     * @param iMonth    월
     * @param iDay      일
     * @return  boolean
     */
    public boolean is_gregorian(int iYear, int iMonth, int iDay) {
        int chk = iYear * 10000 + iMonth * 100 + iDay;

        if ( chk < 15821015 )
            return false;

        return true;
    }


    /**
     * YYYY-MM-DD 또는 array ((string) YYYY, (string) MM, (string) DD)
     * 입력값을 * array ((int) $y, (int) $m, (int) $d)으로 변환
     *
     * @access public
     * @return array array ((int) $y, (int) $m, (int) $d)
     * @param date|string
     *     - YYYY-MM-DD
     *     - array ((string) YYYY, (string) MM, (stirng) DD)
     *
     * @return
     */
    public int[] splitDate(String date) {
        if (date.startsWith("-")) {
            date = date.substring(1);
        }

        String[] parts = date.split("-");
        int[] result = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i]);
        }

        if (date.startsWith("-")) {
            result[0] *= -1;
        }

        return result;
    }


    /**
     * YYYY-MM-DD 형식의 날짜를 반환
     *
     * @access private
     * @return string
     * @param v
     */
    private String regdate (int[] v) {
        int year  = v[0];
        int month = v[1];
        int day   = v[2];

        // '%d-%s%d-%s%d'
        //return (year + "-" + ((month < 10) ? "0" : "") + month + "-" + ((day < 10) ? "0" : "") + day);

        return String.format(
                "%d-%s%d-%s%d",
                year,
                (month < 10) ? "0" : "",
                month,
                (day < 10) ? "0" : "",
                day
        );
    }


    /**
     * YYYY-MM-DD 형식의 날짜를 반환
     *
     * @access private
     * @return string
     * @param v
     */
    private String regdate (String[] v) {
        int year  = Integer.valueOf( v[0] );
        int month = Integer.valueOf( v[1] );
        int day   = Integer.valueOf( v[2] );

        // '%d-%s%d-%s%d'
        return String.format(
                "%d-%s%d-%s%d",
                year,
                (month < 10) ? "0" : "",
                month,
                (day < 10) ? "0" : "",
                day
        );
    }


    /**
     * 윤년 체크
     *
     * 1. 서력 기원 연수가 4로 나누어 떨어지는 해는 윤년으로 한다. (2004년, 2008년, 2012년, 2016년, 2020년…)
     * 2. 이 중에서 100으로 나누어 떨어지는 해는 평년으로 한다.    (1900년, 2100년, 2200년, 2300년…)
     * 3. 그중에 400으로 나누어 떨어지는 해는 윤년으로 둔다.       (1600년, 2000년, 2400년 …)
     *
     * @access public
     * @return bool
     * @param y 년도
     */
    public boolean is_yoon (int y) {
        if ( (y % 400) == 0 )
            return true;

        if ( ((y % 4 ) == 0) && ((y % 100) == 0) )
            return true;

        return false;
    }


    /**
     * 양력 날짜를 음력으로 변환
     *
     * @access public
     *    <ul>
     *        <li>date => YYYY-MM-DD 형식의 음력 날짜</li>
     *        <li>dangi => 단기</li>
     *        <li>hyear => AD/BC 형식 년도</li>
     *        <li>year => 년도</li>
     *        <li>month => 월</li>
     *        <li>day => 일</li>
     *        <li>moonyoon => 윤년 여부</li>
     *        <li>largemonth => 평달/큰달 여부</li>
     *        <li>week => 요일</li>
     *        <li>hweek => 요일 (한자)</li>
     *        <li>unixstamp => unixstamp (양력)</li>
     *        <li>ganji => 간지</li>
     *        <li>hganji => 간지 (한자)</li>
     *        <li>gan => 10간</li>
     *        <li>hgan => 10간 (한자)</li>
     *        <li>ji => 12지</li>
     *        <li>hji => 12지 (한자)</li>
     *        <li>ddi => 띠</li>
     *    </ul>
     * @param v|string 날짜형식
     *    <ul>
     *        <li>unixstmap (1970년 12월 15일 이후부터 가능)</li>
     *        <li>Ymd or Y-m-d</li>
     *        <li>null data (현재 시간<li>
     *    </ul>
     *
     * @return object
     */
    public HashMap<?, ?> tolunar (String v) throws Exception {
        HashMap<String, Object> hmArray = new HashMap<String, Object>();

        int y;
        int m;
        int d;
        int year;
        int month;
        int day;
        int myoon;
        String lmonth;
        int k1;
        int k2;

        int[] iArr = this.toargs (v);
        y = iArr[0];
        m = iArr[1];
        d = iArr[2];

        String[] r = this.solartolunar (y, m, d);
        year       = Integer.valueOf( r[0] );
        month      = Integer.valueOf( r[1] );
        day        = Integer.valueOf( r[2] );
        myoon      = Integer.valueOf( r[3] );
        lmonth     = r[4];

        int w = this.getweekday (y, m, d);

        k1 = (year + 6) % 10;
        k2 = (year + 8) % 12;

        if ( k1 < 0 ) k1 += 10;
        if ( k2 < 0 ) k2 += 12;

        hmArray.put( "date",       this.regdate(r)       );
        hmArray.put( "dangi",      year + 2333           );
        hmArray.put( "hyear",      this.human_year(year) );
        hmArray.put( "year",       year                  );
        hmArray.put( "month",      month                 );
        hmArray.put( "day",        day                   );
        hmArray.put( "moonyoon",   myoon                 );
        hmArray.put( "largemonth", lmonth                );
        hmArray.put( "week",       this.week[w]          );
        hmArray.put( "hweek",      this.hweek[w]         );
        hmArray.put( "unixstamp",  DateUtil.mktime(this.regdate(new int[] {y, m, d}), "yyyy-MM-dd"));
        hmArray.put( "ganji",      LunarConstants.gan[k1] + LunarConstants.ji[k2]);
        hmArray.put( "hganji",     LunarConstants.hgan[k1] + LunarConstants.hji[k2]);
        hmArray.put( "gan",        LunarConstants.gan[k1]);
        hmArray.put( "hgan",       LunarConstants.hgan[k1]);
        hmArray.put( "ji", 	       LunarConstants.ji[k2]);
        hmArray.put( "hji",	       LunarConstants.hji[k2]);
        hmArray.put( "ddi",        LunarConstants.ddi[k2]);

        return hmArray;
    }


    /**
     * 음력 날짜를 양력으로 변환
     *
     * @access public
     *    <ul>
     *        <li>date => YYYY-MM-DD 형식의 양력 날짜</li>
     *        <li>dangi => 단기</li>
     *        <li>hyear => AD/BC 형식 년도</li>
     *        <li>year => 년도</li>
     *        <li>month => 월</li>
     *        <li>day => 일</li>
     *        <li>week => 요일</li>
     *        <li>hweek => 요일 (한자)</li>
     *        <li>unixstamp => unixstamp (양력)</li>
     *        <li>ganji => 간지</li>
     *        <li>hganji => 간지 (한자)</li>
     *        <li>gan => 10간</li>
     *        <li>hgan => 10간 (한자)</li>
     *        <li>ji => 12지</li>
     *        <li>hji => 12지 (한자)</li>
     *        <li>ddi => 띠</li>
     *    </ul>
     * @param v|string 날짜형식
     *    <ul>
     *        <li>unixstmap (1970년 12월 15일 이후부터 가능)</li>
     *        <li>Ymd or Y-m-d</li>
     *        <li>null data (현재 시간<li>
     *    </ul>
     * @param yoon 윤달여부
     * @return object
     */
    public HashMap<?, ?> tosolar (String v, boolean yoon) throws Exception {
        HashMap<String, Object> hmArray = new HashMap<String, Object>();

        int y;
        int m;
        int d;
        int year;
        int month;
        int day;
        int k1;
        int k2;

        int[] iArr = this.toargs (v);
        y = iArr[0];
        m = iArr[1];
        d = iArr[2];

        int[] r = this.lunartosolar (y, m, d, yoon);
        year  = Integer.valueOf( r[0] );
        month = Integer.valueOf( r[1] );
        day   = Integer.valueOf( r[2] );

        int w = this.getweekday (year, month, day);

        k1 = (y + 6) % 10;
        k2 = (y + 8) % 12;

        if ( k1 < 0 ) k1 += 10;
        if ( k2 < 0 ) k2 += 12;

        hmArray.put( "date",       this.regdate(r) 		 );
        hmArray.put( "dangi",      year + 2333     		 );
        hmArray.put( "hyear",      this.human_year(year) );
        hmArray.put( "year",       year					 );
        hmArray.put( "month",      month				 );
        hmArray.put( "day",        day					 );
        hmArray.put( "week",       this.week[w]			 );
        hmArray.put( "hweek",      this.hweek[w]		 );
        hmArray.put( "unixstamp",  DateUtil.mktime(this.regdate(new int[] {y, m, d}), "yyyy-MM-dd") );
        hmArray.put( "ganji",      LunarConstants.gan[k1] + LunarConstants.ji[k2]   );
        hmArray.put( "hganji",     LunarConstants.hgan[k1] + LunarConstants.hji[k2] );
        hmArray.put( "gan",        LunarConstants.gan[k1]  );
        hmArray.put( "hgan",       LunarConstants.hgan[k1] );
        hmArray.put( "ji",         LunarConstants.ji[k2]   );
        hmArray.put( "hji",        LunarConstants.hji[k2]  );
        hmArray.put( "ddi",        LunarConstants.ddi[k2]  );

        return hmArray;
    }


    /**
     * 일진 데이터를 구한다.
     *
     * @access public
     * @param v|string 날짜형식
     *    <ul>
     *        <li>unixstmap (1970년 12월 15일 이후부터 가능)</li>
     *        <li>Ymd or Y-m-d</li>
     *        <li>null data (현재 시간<li>
     *    </ul>
     * @return object
     */
    public HashMap<?, ?> dayfortune (String v) throws Exception {
        HashMap<String, Object> hmArray = new HashMap<String, Object>();

        int y;
        int m;
        int d;
        int so24;
        int year;
        int month;
        int day;
        int hour;

        int[] iArr = this.toargs (v);
        y = iArr[0];
        m = iArr[1];
        d = iArr[2];

        // so24, so24year, so24month, so24day, so24hour
        int[] so = this.sydtoso24yd (y, m, d, 1, 0);
        so24  = so[0];
        year  = so[1];
        month = so[2];
        day   = so[3];
        hour  = so[4];

        //
        iArr[0] = year;
        iArr[1] = month;
        iArr[2] = day;

        hmArray.put( "date",   iArr               );  // 'data' => (object) array ('y' => $year, 'm' => $month, 'd' => $day)
        hmArray.put( "year",   this.ganji[year]   );
        hmArray.put( "month",  this.ganji[month]  );
        hmArray.put( "day",    this.ganji[day]    );
        hmArray.put( "hyear",  this.hganji[year]  );
        hmArray.put( "hmonth", this.hganji[month] );
        hmArray.put( "hday",   this.hganji[day]   );

        return hmArray;
    }


    /**
     * 특정일의 28수를 구한다.
     *
     * @access public
     * @return object
     * @param v|string 날짜형식
     *    <ul>
     *        <li>unixstmap (1970년 12월 15일 이후부터 가능)</li>
     *        <li>Ymd or Y-m-d</li>
     *        <li>null data (현재 시간<li>
     *    </ul>
     */
    public HashMap<?, ?> s28day (String v) throws Exception {
        HashMap<String, Object> hmArray = new HashMap<String, Object>();

        // 기존에 존재하는 v->data 부분은 사용하지 않고 애매한 부분이 있어서 구현하지 않고 SKIP
        // 자세한 부분은 Lunar.php 를 참

        int y;
        int m;
        int d;

        int[] iArr = this.toargs (v);
        y = iArr[0];
        m = iArr[1];
        d = iArr[2];

        int r = this.get28sday (y, m, d);

        hmArray.put( "date", r );
        hmArray.put( "k",    this.s28days_hangul[r] );
        hmArray.put( "h",    this.s28days[r] );

        return hmArray;
    }


    /**
     * 절기 시간 구하기
     *
     * @access public
     * @param v|string 날짜형식
     *    <ul>
     *        <li>unixstmap (1970년 12월 15일 이후부터 가능)</li>
     *        <li>Ymd or Y-m-d</li>
     *        <li>null data (현재 시간<li>
     *    </ul>
     * @return array
     */
    public HashMap<?, ?> seasondate (String v) throws Exception {
        HashMap<String, HashMap<String, Object>> hmArray = new HashMap<String, HashMap<String, Object>>();

        HashMap<String, Object> center  = new HashMap<String, Object>();
        HashMap<String, Object> ccenter = new HashMap<String, Object>();
        HashMap<String, Object> ncenter = new HashMap<String, Object>();

        int y;
        int m;
        int d;

        int[] iArr = this.toargs (v);
        y = iArr[0];
        m = iArr[1];
        d = iArr[2];

        /*
          $inginame, $ingiyear, $ingimonth, $ingiday, $ingihour, $ingimin,			0~5
          $midname1, $midyear1, $midmonth1, $midday1, $midhour1, $midmin1,			6~11
          $outginame, $outgiyear, $outgimonth, $outgiday, $outgihour, $outgimin		12~17
        */
        int[] list1st = this.solortoso24 (y, m, 20, 1, 0);

        //
        center.put("name",  this.month_st[list1st[0]]);
        center.put("hname", this.hmonth_st[list1st[0]]);
        center.put("hyear", this.human_year(list1st[1]));
        center.put("year",  list1st[1]);        // ingiyear
        center.put("month", list1st[2]);        // ingimonth
        center.put("day",   list1st[3]);        // ingiday
        center.put("hour",  list1st[4]);        // ingihour
        center.put("min",   list1st[5]);        // ingimin

        //
        ccenter.put("name",  this.month_st[list1st[6]]);
        ccenter.put("hname", this.hmonth_st[list1st[6]]);
        ccenter.put("hyear", this.human_year(list1st[1]));
        ccenter.put("year",  list1st[7]);       // midyear1
        ccenter.put("month", list1st[8]);       // midmonth1
        ccenter.put("day",   list1st[9]);       // midday1
        ccenter.put("hour",  list1st[10]);      // midhour1
        ccenter.put("min",   list1st[11]);      // midmin1

        //
        ncenter.put("name",  this.month_st[list1st[12]]);
        ncenter.put("hname", this.hmonth_st[list1st[12]]);
        ncenter.put("hyear", this.human_year(list1st[1]));
        ncenter.put("year",  list1st[13]);      // outgiyear
        ncenter.put("month", list1st[14]);      // outgimonth
        ncenter.put("day",   list1st[15]);      // outgiday
        ncenter.put("hour",  list1st[16]);      // outgihour
        ncenter.put("min",   list1st[17]);      // outgimin

        //
        hmArray.put("center",  center);
        hmArray.put("ccenter", ccenter);
        hmArray.put("ncenter", ncenter);

        return hmArray;
    }


    /**
     * 합삭/망 데이터 구하기
     *
     * @access public
     * @return object
     * @param v|string 날짜형식
     *    <ul>
     *        <li>unixstmap (1970년 12월 15일 이후부터 가능)</li>
     *        <li>Ymd or Y-m-d</li>
     *        <li>null data (현재 시간<li>
     *    </ul>
     */
    public HashMap<?, ?> moonstatus (String v) throws Exception {
        HashMap<String, HashMap<String, Object>> hmArray = new HashMap<String, HashMap<String, Object>>();

        HashMap<String, Object> neo  = new HashMap<String, Object>();
        HashMap<String, Object> full = new HashMap<String, Object>();

        int y;
        int m;
        int d;

        int[] iArr = this.toargs (v);
        y = iArr[0];
        m = iArr[1];
        d = iArr[2];

        /*
            $y1, $mo1, $d1, $h1, $mi1,			0~4
            $ym, $mom, $dm, $hm, $mim,			5~9
            $y2, $m2, $d2, $h2, $mi2			10~14
        */
        int[] list1st = this.getlunarfirst (y, m, d);

        //
        neo.put("hyear", this.human_year(list1st[0]));
        neo.put("year",  list1st[0]);
        neo.put("month", list1st[1]);
        neo.put("day",   list1st[2]);
        neo.put("hour",  list1st[3]);
        neo.put("min",   list1st[4]);

        //
        full.put("hyear", this.human_year(list1st[5]));
        full.put("year",  list1st[5]);
        full.put("month", list1st[6]);
        full.put("day",   list1st[7]);
        full.put("hour",  list1st[8]);
        full.put("min",   list1st[9]);

        hmArray.put("neo",  neo);
        hmArray.put("full", full);

        return hmArray;
    }


    /**
     * @access public
     * @return string
     * @param no ganji index number
     * @param mode 출력 모드 (false => 한글, true => 한자)
     */
    public String ganji_ref (String no, boolean mode) {
        int iNo = Integer.valueOf(no);

        if ( iNo > 59 )
            iNo -= 60;

        if (mode) {
            return this.hganji[iNo];
        } else {
            return this.ganji[iNo];
        }
    }

    /**
     * 윤년 체크
     *
     * 예제:
     *
     * @access public
     * @return bool
     * @param 'int 년도'
     * @param 'bool Julian 여부'
     * <p>
     * 1582년 이전은 Julian calender로 판단하여 이 값이
     * false라도 율리우스력으로 간주하여 판단한다. (sinse 1.0.1)
     * </p>
     */
    public boolean is_leap (int iYear, boolean bJulian) {

        // Julian의 윤년은 4로 나누어지면 된다.
        if ( bJulian || iYear < 1583 ) {
            return (iYear % 4) == 0 ? false : true;
        } else {
            return (iYear % 400 == 0) || ((iYear % 4 == 0) && (iYear % 100 != 0));
        }
    }

    /**
     * @access public
     * @return string
     * @param 'int ganji' index number
     * @param 'bool 출력 모드' (false => 한글, true => 한자)
     */
    public String ganji_ref (int iNo, boolean bMode) {
        if ( iNo > 59 )
            iNo -= 60;

        if (bMode == true) {
            return this.hganji[iNo];
        } else {
            return this.ganji[iNo];
        }
    }
}