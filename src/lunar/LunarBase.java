package lunar;

import java.math.BigDecimal;

import lunar.common.text.TextUtil;
import org.apache.commons.lang3.math.NumberUtils;

import lunar.common.math.MathUtil;
import lunar.consts.LunarConstants;

/**
 * Project: LunarBase :: 양력/음력 변환 코어 클래스<br>
 * File:    LunarBase.java
 *
 * 이 패키지는 양력/음력간의 변환을 제공하는 API로, 
 * 고영창님의 '진짜만세력' 0.92(Perl version)와 0.93(Pascal version)버전을 
 * 김정균님이 PHP로 포팅한 걸 한번 더 Java 로 포팅.
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
 *     + -9999-01-01 ~ 9999-12-31
 *     + 64bit 계산이 가능한 시점까지 가능할 듯..
 *
 * </pre>
 *
 * @category    Calendar
 * @package     Lunar
 * @author      izoka <http://izoka.pe.kr>
 * @copyright   (c) 2016 izoka.pe.kr
 * @license     고영창 (http://afnmp3.homeip.net/~kohyc/calendar/index.cgi)
 */

/**
 * Lunar Core API
 *
 * 이 패키지는 양력/음력간의 변환을 제공하는 API로, 
 * 고영창님의 '진짜만세력' 0.92(Perl version)와 0.93(Pascal version)버전을 
 * 김정균님이 PHP로 포팅한 걸 한번 더 Java 로 포팅.
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
 *     + -9999-01-01 ~ 9999-12-31
 *     + 64bit 계산이 가능한 시점까지 가능할 듯..
 *
 * </pre>
 *
 * @package     Lunar
 */

public class LunarBase {
    /**
     * 절기 데이터
     * String array
     */
    protected String[] month_st = {
        "입춘", "우수", "경칩", "춘분", "청명", "곡우",
        "입하", "소만", "망종", "하지", "소서", "대서",
        "입추", "처서", "백로", "추분", "한로", "상강",
        "입동", "소설", "대설", "동지", "소한", "대한",
        "입춘"
    };

    /**
     * 절기(한자) 데이터
     * String array
     */
    protected String[] hmonth_st = {
        "立春", "雨水", "驚蟄", "春分", "淸明", "穀雨",
        "立夏", "小滿", "芒種", "夏至", "小暑", "大暑",
        "立秋", "處暑", "白露", "秋分", "寒露", "霜降",
        "立冬", "小雪", "大雪", "冬至", "小寒", "大寒",
        "立春"
    };

    /**
     * 60간지 데이터
     * String array
     */
    protected String[] ganji = {
        "갑자", "을축", "병인", "정묘", "무진", "기사", "경오", "신미", "임신", "계유", "갑술", "을해",
        "병자", "정축", "무인", "기묘", "경진", "신사", "임오", "계미", "갑신", "을유", "병술", "정해", 
        "무자", "기축", "경인", "신묘", "임진", "계사", "갑오", "을미", "병신", "정유", "무술", "기해", 
        "경자", "신축", "임인", "계묘", "갑신", "을사", "병오", "정미", "무신", "기유", "경술", "신해",
        "임자", "계축", "갑인", "을묘", "병진", "정사", "무오", "기미", "경신", "신유", "임술", "계해"
    };
    
    /**
     * 60간지 한자 데이터
     * String array
     */
    protected String[] hganji = {
        "甲子","乙丑","丙寅","丁卯","戊辰","己巳","庚午","辛未","壬申","癸酉","甲戌","乙亥",
        "丙子","丁丑","戊寅","己卯","庚辰","辛巳","壬午","癸未","甲申","乙酉","丙戌","丁亥",
        "戊子","己丑","庚寅","辛卯","壬辰","癸巳","甲午","乙未","丙申","丁酉","戊戌","己亥",
        "庚子","辛丑","壬寅","癸卯","甲辰","乙巳","丙午","丁未","戊申","己酉","庚戌","辛亥",
        "壬子","癸丑","甲寅","乙卯","丙辰","丁巳","戊午","己未","庚申","辛酉","壬戌","癸亥"
    };

    /**
     * 요일 데이터
     * String array
     */
    protected String[] week = {"일","월","화","수","목","금","토"};
    
    /**
     * 요일 한자 데이터
     * String array
     */
    protected String[] hweek = {"日","月","火","水","木","金","土"};
    
    /**
     * 28일 데이터
     * String array
     */
    protected String[] s28days = {
        "角","亢","氐","房","心","尾","箕",
        "斗","牛","女","虛","危","室","壁",
        "奎","婁","胃","昴","畢","觜","參",
        "井","鬼","柳","星","張","翼","軫"
    };
    
    protected String[] s28days_hangul = {
        "각", "항", "저", "방", "심", "미", "기",
        "두", "우", "녀", "허", "위", "실", "벽",
        "규", "수", "위", "묘", "필", "자", "삼",
        "정", "귀", "류", "성", "장", "익", "진"
    };
    
    
    /**
     * 정수 몫을 반환
     *
     * @access protected
     * @param bdVal1
     * @param bdVal2
     *
     * @return int
     */
    protected int div (BigDecimal bdVal1, BigDecimal bdVal2) {
        return (bdVal1.divide(bdVal2, 8, BigDecimal.ROUND_CEILING)).intValue();
    }
    
    
    /**
     * year의 1월 1일부터 해당 일자까지의 날짜수
     *
     * @access public
     * @param  iYear 년
     * @param  iMonth 월
     * @param  iDay 일
     * @return 날짜수
     */
    protected int disptimeday (int iYear, int iMonth, int iDay) {
        int iTimeCount = 0;

        for ( int i=1; i < iMonth; i++ ) {
            iTimeCount += 31;

            if ( i == 2 || i == 4 || i == 6 || i == 9 || i == 11 )
                iTimeCount--;

            if ( i == 2 ) {
                iTimeCount -= 2;

                if ( (iYear % 4) == 0 ) iTimeCount++;
                if ( (iYear % 100) == 0 ) iTimeCount--;
                if ( (iYear % 400) == 0 ) iTimeCount++;
                if ( (iYear % 4000) == 0 ) iTimeCount--;
            }
        }

        iTimeCount += iDay;

        return iTimeCount;
    }
    
    
    /**
     * iYear1, iMonth1, iDay1일부터 iYear2, iMonth2, iDay2까지의 일수 계산
     *
     * @access protected
     * @param 'from year'
     * @param 'from month'
     * @param 'from day'
     * @param 'until year'
     * @param 'until month'
     * @param 'until day'
     * @return 'int 날짜수'
     */
    protected int disp2days (int iYear1, int iMonth1, int iDay1, int iYear2, int iMonth2, int iDay2) {
        int p1   = 0;
        int p2   = 0; 
        int p1n  = 0; 
        int pp1  = 0; 
        int pp2  = 0; 
        int pr   = 0; 
        int dis  = 0; 
        int ppp1 = 0; 
        int ppp2 = 0; 
        int k    = 0;

        if ( iYear2 > iYear1 ) {
            p1  = this.disptimeday (iYear1, iMonth1, iDay1);
            p1n = this.disptimeday (iYear1, 12, 31);
            p2  = this.disptimeday (iYear2, iMonth2, iDay2);
            pp1 = iYear1;
            pp2 = iYear2;
            pr  = -1;
        } else {
            p1  = this.disptimeday (iYear2, iMonth2, iDay2);
            p1n = this.disptimeday (iYear2, 12, 31);
            p2  = this.disptimeday (iYear1, iMonth1, iDay1);
            pp1 = iYear2;
            pp2 = iYear1;
            pr  = 1;
        }

        if ( iYear2 == iYear1 )
            dis = p2 - p1;
        else {
            dis  = p1n - p1;
            ppp1 = pp1 + 1;
            ppp2 = pp2 - 1;

            for ( k = ppp1; k <= ppp2; k++ ) {
                if ( k == -2000 && ppp2 > 1990 ) {
                    dis += 1457682;
                    k = 1991;
                } else if ( k == -1750 && ppp2 > 1990 ) {
                    dis += 1366371;
                    k = 1991;
                } else if ( k ==-1500 && ppp2 > 1990 ) {
                    dis += 1275060;
                    k = 1991;
                } else if ( k ==-1250 && ppp2 > 1990 ) {
                    dis += 1183750;
                    k = 1991;
                } else if ( k ==-1000 && ppp2 > 1990 ) {
                    dis += 1092439;
                    k = 1991;
                } else if ( k == -750 && ppp2 > 1990 ) {
                    dis += 1001128;
                    k = 1991;
                } else if ( k == -500 && ppp2 > 1990 ) {
                    dis += 909818;
                    k = 1991;
                } else if ( k == -250 && ppp2 > 1990 ) {
                    dis += 818507;
                    k = 1991;
                } else if ( k == 0 && ppp2 > 1990 ) {
                    dis += 727197;
                    k = 1991;
                } else if ( k == 250 && ppp2 > 1990 ) {
                    dis += 635887;
                    k = 1991;
                } else if ( k == 500 && ppp2 > 1990 ) {
                    dis += 544576;
                    k = 1991;
                } else if ( k == 750 && ppp2 > 1990 ) {
                    dis += 453266;
                    k = 1991;
                } else if ( k == 1000 && ppp2 > 1990 ) {
                    dis += 361955;
                    k = 1991;
                } else if ( k == 1250 && ppp2 > 1990 ) {
                    dis += 270644;
                    k = 1991;
                } else if ( k == 1500 && ppp2 > 1990 ) {
                    dis += 179334;
                    k = 1991;
                } else if ( k == 1750 && ppp2 > 1990 ) {
                    dis += 88023;
                    k = 1991;
                }

                dis += this.disptimeday (k, 12, 31);
            }

            dis += p2;
            dis *= pr;
        }

        return dis;
    }


    /**
     * uy, umm, ud, uh, umin 과 y1, mo1, d1, h1, mm1사이의 시간(분)
     *
     * @param uy
     * @param umm
     * @param ud
     * @param uh
     * @param umin
     * @param y1
     * @param mo1
     * @param d1
     * @param h1
     * @param mm1
     * @return int 분
     */
    public int getminbytime (int uy, int umm, int ud, int uh, int umin, int y1, int mo1, int d1, int h1, int mm1) {
        int iMin     = 0;
        int iDispday = 0;

        iDispday = this.disp2days (uy, umm, ud, y1, mo1, d1);
        iMin     = iDispday * 24 * 60 + (uh - h1) * 60 + (umin - mm1);

        return iMin;
    }


    /**
     * uyear, umonth, uday, uhour, umin으로부터 tmin(분)떨이진 시점의
     * 년월일시분(태양력) 구하는 프로시져
     *
     * @param tmin
     * @param uyear
     * @param umonth
     * @param uday
     * @param uhour
     * @param umin
     * @return Integer array
     */
    protected int[] getdatebymin (int tmin, int uyear, int umonth, int uday, int uhour, int umin) {
        int y1  = 0;
        int mo1 = 0;
        int d1  = 0;
        int h1  = 0;
        int mi1 = 0;
        int t   = 0;

        y1 = uyear - this.div(MathUtil.convertIntToBigDecimal(tmin), NumberUtils.createBigDecimal("525949"));
        
        if ( tmin > 0 ) {
            y1 += 2;
            do {
                y1--;
                t = this.getminbytime (uyear, umonth, uday, uhour, umin, y1, 1, 1, 0, 0);
            } while ( t < tmin );

            mo1 = 13 ;
            do {
                mo1--;
                t = this.getminbytime (uyear, umonth, uday, uhour, umin, y1, mo1, 1, 0, 0);
            } while ( t < tmin );

            d1 = 32;
            do {
                d1--;
                t = this.getminbytime (uyear, umonth, uday, uhour, umin, y1, mo1, d1, 0, 0);
            } while ( t < tmin );

            h1 = 24;
            do {
                h1--;
                t = this.getminbytime (uyear, umonth, uday, uhour, umin, y1, mo1, d1, h1, 0);
            } while ( t < tmin );

            t = this.getminbytime ( uyear, umonth, uday, uhour, umin, y1, mo1, d1, h1, 0);
            mi1 =  t - tmin;
        } else {
            y1 -= 2;
            do {
                y1++;
                t = this.getminbytime (uyear, umonth, uday, uhour, umin, y1, 1, 1, 0, 0);
            } while ( t >= tmin );

            y1--;
            mo1 = 0;
            do {
                mo1++;
                t = this.getminbytime (uyear, umonth, uday, uhour, umin, y1, mo1, 1, 0, 0);
            } while ( t >= tmin );

            mo1--;
            d1 = 0;
            do {
                d1 = d1 + 1;
                t = this.getminbytime (uyear, umonth, uday, uhour, umin, y1, mo1, d1, 0, 0);
            } while ( t >= tmin );

            d1--;
            h1 = -1 ;
            do {
                h1++;
                t = this.getminbytime (uyear, umonth, uday, uhour, umin, y1, mo1, d1, h1, 0);
            } while ( t >= tmin );

            h1--;
            t = this.getminbytime (uyear, umonth, uday, uhour, umin, y1, mo1, d1, h1, 0);
            mi1 = t - tmin;
        }

        //
        int[] iArr = {y1, mo1, d1, h1, mi1};

        return iArr; 
    }
    
    
    /**
     * 그레고리력의 년월시일분으로 60년의 배수, 세차, 월건(태양력),
     * 일진, 시주를 구함
     *
     * @access protected
     *
     *   <pre>
     *   Array
     *   (
     *       [0] => -17  // 60년의 배수
     *       [1] => 29   // 60간지의 연도 배열 index
     *       [2] => 55   // 60간지의 월 배열 index
     *       [3] => 11   // 60간지의 일 배열 index
     *       [4] => 20   // 60간지의 시 배열 index
     *   )
     *   </pre>
     * 
     * @param 'int soloryear'
     * @param 'int solormonth'
     * @param 'int solorday'
     * @param 'int solorhour'
     * @param 'int solormin'
     * @return Integer array
     */
    protected int[] sydtoso24yd (int soloryear, int solormonth, int solorday, int solorhour, int solormin) {
        int displ2min   = 0;
        int displ2day   = 0;
        int so24        = 0;
        int so24year    = 0;
        int so24month   = 0;
        int so24day     = 0;
        int so24hour    = 0;
        int monthmin100 = 0;

        displ2min = this.getminbytime (
            LunarConstants.unityear, LunarConstants.unitmonth, LunarConstants.unitday, LunarConstants.unithour, LunarConstants.unitmin,
            soloryear, solormonth, solorday, solorhour, solormin
        );

        displ2day = this.disp2days (
            LunarConstants.unityear, LunarConstants.unitmonth, LunarConstants.unitday,
            soloryear, solormonth, solorday
        );

        // 무인년(1996)입춘시점부터 해당일시까지 경과년수
        so24 = this.div (MathUtil.convertIntToBigDecimal(displ2min), NumberUtils.createBigDecimal("525949"));

        if ( displ2min >= 0 )
            so24++;

        // 년주 구하기
        so24year = (so24 % 60) * -1;
        so24year += 12;
        
        if ( so24year < 0 )
            so24year += 60;
        else if ( so24year > 59 )
            so24year -= 60;

        monthmin100 = displ2min % 525949;
        monthmin100 = 525949 - monthmin100;

        if ( monthmin100 < 0 )
            monthmin100 += 525949;
        else if ( monthmin100 >= 525949 )
            monthmin100 -= 525949;

        for ( int i=0,j=0; i <= 11; i++ ) {
            j = i * 2;

            if ( (LunarConstants.month[j] <= monthmin100) && (monthmin100 < LunarConstants.month[j+2]))
                so24month = i;

            // release variable
            j = 0;
        };

        // 월주 구하기
        int i = so24month;
        int t = so24year % 10 ;
        t %= 5 ;
        t = t * 12 + 2 + i;
        so24month = t ;
        
        if ( so24month > 59 ) 
            so24month -= 60;

        so24day = displ2day % 60;

        // 일주 구하기
        so24day *= -1;
        so24day += 7;
        if ( so24day < 0 )
            so24day += 60;
        else if ( so24day > 59 )
            so24day -= 60;

        if ( (solorhour == 0 || solorhour == 1) && solormin < 30 ) {
            i = 0;
        } else if ( (solorhour == 1 && solormin >= 30) || solorhour == 2 || (solorhour == 3 && solormin < 30) ) {
            i = 1;
        } else if ( (solorhour == 3 && solormin >= 30) || solorhour == 4 || (solorhour == 5 && solormin < 30) ) {
            i = 2;
        } else if ( (solorhour == 5 && solormin >= 30) || solorhour == 6 || (solorhour == 7 && solormin < 30) ) {
            i = 3;
        } else if ( (solorhour == 7 && solormin >= 30) || solorhour == 8 || (solorhour == 9 && solormin < 30) ) {
            i = 4;
        } else if ( (solorhour == 9 && solormin >= 30) || solorhour == 10 || (solorhour == 11 && solormin < 30) ) {
            i = 5;
        } else if ( (solorhour == 11 && solormin >= 30) || solorhour == 12 || (solorhour == 13 && solormin < 30) ) {
            i = 6;
        } else if ( (solorhour == 13 && solormin >= 30) || solorhour == 14 || (solorhour == 15 && solormin < 30) ) {
            i = 7;
        } else if ( (solorhour == 15 && solormin >= 30) || solorhour == 16 || (solorhour == 17 && solormin < 30) ) {
            i = 8;
        } else if ( (solorhour == 17 && solormin >= 30) || solorhour == 18 || (solorhour == 19 && solormin < 30) ) {
            i = 9;
        } else if ( (solorhour == 19 && solormin >= 30) || solorhour == 20 || (solorhour == 21 && solormin < 30) ) {
            i = 10;
        } else if ( (solorhour == 21 && solormin >= 30) || solorhour == 22 || (solorhour == 23 && solormin < 30) ) {
            i = 11;
        } else if ( solorhour == 23 && solormin >= 30 ) {
            so24day++;
            if ( so24day == 60 )
                so24day = 0;

            i=0;
        }

        t = so24day % 10;
        t %= 5;
        t = t * 12 + i;
        so24hour = t;

        //
        int[] iArr = {so24, so24year, so24month, so24day, so24hour};

        return iArr;
    }
    
    
    /**
     * 절기 시간 구하기
     *
     * 그레고리력의 년월일시분이 들어있는 절기의 이름번호,
     * 년월일시분을 얻음
     *
     * @access protected
     * @param 'int soloryear'
     * @param 'int solormonth'
     * @param 'int solorday'
     * @param 'int solorhour'
     * @param 'int solormin'
     * @return Integer array
     */
    protected int[] solortoso24 (int soloryear, int solormonth, int solorday, int solorhour, int solormin) {
        int displ2min   = 0;
        int monthmin100 = 0;
        int monthmin    = 0;
        int i           = 0;
        int j           = 0;
        int inginame    = 0;
        int midname     = 0;
        int outginame   = 0;
        int tmin        = 0;
        int ingiyear, ingimonth, ingiday, ingihour, ingimin;
        int midyear, midmonth, midday, midhour, midmin;
        int outgiyear, outgimonth, outgiday, outgihour, outgimin;

        // return Array Values = {so24, so24year, so24month, so24day, so24hour}
        int[] list1st = this.sydtoso24yd (soloryear, solormonth, solorday, solorhour, solormin);

        displ2min = this.getminbytime (
            LunarConstants.unityear, LunarConstants.unitmonth, LunarConstants.unitday, LunarConstants.unithour, LunarConstants.unitmin,
            soloryear, solormonth, solorday, solorhour, solormin
        );

        /** 이거 고민좀 해 봐야 할 듯!!
        $monthmin100 = $displ2min % 525949;
        $monthmin100 = 525949 - $monthmin100;
        */
        monthmin100 = (displ2min % 525949) * -1;

        if ( monthmin100 < 0 )
            monthmin100 += 525949;
        else if ( monthmin100 >= 525949 )
            monthmin100 = monthmin - 525949;

        i = list1st[2] % 12 - 2;
        if ( i == -2 ) 
            i = 10;
        else if ( i == -1 )
            i = 11;

        inginame  = i * 2 ;
        midname   = i * 2 + 1;
        outginame = i * 2 + 2;

        j = i * 2;
        tmin = displ2min + (monthmin100 - LunarConstants.month[j]);
        
        // return Array Values = {y1, mo1, d1, h1, mi1}
        int[] list2nd = this.getdatebymin (tmin, LunarConstants.unityear, LunarConstants.unitmonth, LunarConstants.unitday, LunarConstants.unithour, LunarConstants.unitmin);

        ingiyear  = list2nd[0];
        ingimonth = list2nd[1];
        ingiday   = list2nd[2];
        ingihour  = list2nd[3];
        ingimin   = list2nd[4];

        tmin = displ2min + (monthmin100 - LunarConstants.month[j+1]);
        
        // return Array Values = {y1, mo1, d1, h1, mi1}
        int[] list3rd = this.getdatebymin (tmin, LunarConstants.unityear, LunarConstants.unitmonth, LunarConstants.unitday, LunarConstants.unithour, LunarConstants.unitmin);

        midyear  = list3rd[0];
        midmonth = list3rd[1];
        midday   = list3rd[2];
        midhour  = list3rd[3];
        midmin   = list3rd[4];

        tmin = displ2min + (monthmin100 - LunarConstants.month[j+2]);
        
        // return Array Values = {y1, mo1, d1, h1, mi1}
        int[] list4rd = this.getdatebymin (tmin, LunarConstants.unityear, LunarConstants.unitmonth, LunarConstants.unitday, LunarConstants.unithour, LunarConstants.unitmin);

        outgiyear  = list4rd[0];
        outgimonth = list4rd[1];
        outgiday   = list4rd[2];
        outgihour  = list4rd[3];
        outgimin   = list4rd[4];

        //
        int[] iArr = {inginame,  ingiyear,  ingimonth,  ingiday,  ingihour,  ingimin,
                      midname,   midyear,   midmonth,   midday,   midhour,   midmin,
                      outginame, outgiyear, outgimonth, outgiday, outgihour, outgimin};

        return iArr;
    }


    /**
     * 미지의 각도를 0~360도 이내로 만듬
     *
     * @param 'BigDeciam d'
     * @return integer
     */
    protected BigDecimal degreelow (BigDecimal d) {
        BigDecimal di = d;
        BigDecimal i  = MathUtil.convertIntToBigDecimal( this.div(d, NumberUtils.createBigDecimal("360")) );

        // $di = $d - ($i * 360);
        di = d.subtract(i).multiply(NumberUtils.createBigDecimal("360"));

        while ( di.intValue() >= 360 || di.intValue() < 0 ) {
            if ( di.intValue() > 0 ) {
                // di -= 360;
                di = di.subtract(NumberUtils.createBigDecimal("360"));
            } else {
                // di += 360;
                di = di.add(NumberUtils.createBigDecimal("360"));
            }
        }

        return di;
    }


    /**
     * 태양황력과 달황경의 차이 (1996 기준)
     *
     * @access protected
     * @param 'int date'
     * @return Integer
     */
    protected BigDecimal moonsundegree (BigDecimal day) {
        // 평균 황경
        // (day * 0.98564736 + 278.956807)
        BigDecimal sl        = day.multiply(NumberUtils.createBigDecimal("0.98564736")).add(NumberUtils.createBigDecimal("278.956807"));

        // 근일점 황경
        // (day * 282.869498 + 0.00004708)
        BigDecimal smin      = day.multiply(NumberUtils.createBigDecimal("282.869498")).add(NumberUtils.createBigDecimal("0.00004708"));

        // 근점이각
        // 3.14159265358979 * (sl - smin) / 180
        BigDecimal sminangle = NumberUtils.createBigDecimal("3.14159265358979").multiply(sl.subtract(smin)).divide(NumberUtils.createBigDecimal("180"), 8, BigDecimal.ROUND_CEILING);

        // 황경차
        // 1.919 * Math.sin (sminangle) + 0.02 * Math.sin (2 * sminangle);
        BigDecimal sd        = (NumberUtils.createBigDecimal("1.919").multiply(MathUtil.convertDoubleToBigDecimal((Math.sin(sminangle.doubleValue())))))
                                .add(NumberUtils.createBigDecimal("0.02").multiply(MathUtil.convertDoubleToBigDecimal((Math.sin(sminangle.multiply(NumberUtils.createBigDecimal("2")).doubleValue())))));

        // 진황경
        BigDecimal sreal     = this.degreelow(sl.add(sd));
        
        // 평균 황경
        // 27.836584 + 13.17639648 * day
        BigDecimal ml        = (day.multiply(NumberUtils.createBigDecimal("13.17639648"))).add(NumberUtils.createBigDecimal("27.836584"));
        
        // 근지점 황경
        // 280.425774 + 0.11140356 * day
        BigDecimal mmin      = (day.multiply(NumberUtils.createBigDecimal("0.11140356"))).add(NumberUtils.createBigDecimal("280.425774"));

        // 근점이각
        // 3.14159265358979 * (ml - mmin) / 180
        BigDecimal mminangle = NumberUtils.createBigDecimal("3.14159265358979").multiply(ml.subtract(mmin)).divide(NumberUtils.createBigDecimal("180"), 8, BigDecimal.ROUND_CEILING);

        // 교점황경
        // 202.489407 - 0.05295377 * day
        BigDecimal msangle   = (day.multiply(NumberUtils.createBigDecimal("0.05295377"))).subtract(NumberUtils.createBigDecimal("202.489407"));
        
        // 3.14159265358979 * (ml - msangle) / 180;
        BigDecimal msdangle  = NumberUtils.createBigDecimal("3.14159265358979").multiply(ml.subtract(msangle)).divide(NumberUtils.createBigDecimal("180"), 8, BigDecimal.ROUND_CEILING);
        
        // 황경차
        /** double md = 5.06889 * Math.sin (mminangle)
                    + 0.146111 * Math.sin (2 * mminangle)
                    + 0.01 * Math.sin (3 * mminangle)
                    - 0.238056 * Math.sin (sminangle)
                    - 0.087778 * Math.sin (mminangle + sminangle)
                    + 0.048889 * Math.sin (mminangle - sminangle)
                    - 0.129722 * Math.sin (2 * msdangle)
                    - 0.011111 * Math.sin (2 * msdangle - mminangle)
                    - 0.012778 * Math.sin (2 * msdangle + mminangle);*/
        BigDecimal md = NumberUtils.createBigDecimal("5.06889").multiply(            BigDecimal.valueOf(Math.sin(mminangle.doubleValue())) )
                        .add(     NumberUtils.createBigDecimal("0.146111").multiply( BigDecimal.valueOf(Math.sin(NumberUtils.createBigDecimal("2").multiply(mminangle).doubleValue())) ))
                        .add(     NumberUtils.createBigDecimal("0.01").multiply(     BigDecimal.valueOf(Math.sin(NumberUtils.createBigDecimal("3").multiply(mminangle).doubleValue())) ))
                        .subtract(NumberUtils.createBigDecimal("0.238056").multiply( BigDecimal.valueOf(Math.sin(sminangle.doubleValue())) ))
                        .subtract(NumberUtils.createBigDecimal("0.087778").multiply( BigDecimal.valueOf(Math.sin(mminangle.add(sminangle).doubleValue())) ))
                        .add(     NumberUtils.createBigDecimal("0.048889").multiply( BigDecimal.valueOf(Math.sin(mminangle.subtract(sminangle).doubleValue())) ))
                        .subtract(NumberUtils.createBigDecimal("0.129722").multiply( BigDecimal.valueOf(Math.sin(NumberUtils.createBigDecimal("2").multiply(msdangle).doubleValue())) ))
                        .subtract(NumberUtils.createBigDecimal("0.011111").multiply( BigDecimal.valueOf(Math.sin(NumberUtils.createBigDecimal("2").multiply(msdangle).subtract(mminangle).doubleValue())) ))
                        .subtract(NumberUtils.createBigDecimal("0.012778").multiply( BigDecimal.valueOf(Math.sin(NumberUtils.createBigDecimal("2").multiply(msdangle).add(mminangle).doubleValue())) ));

        // 진황경
        BigDecimal mreal = this.degreelow(ml.add(md));

        BigDecimal re = this.degreelow(mreal.subtract(sreal));

        return re;
    }


    /**
     * 그레고리력 년월일이 들어있는 
     * 태음월의 시작합삭일지, 망일시, 끝합삭일시를 구함
     *
     * @access protected
     *
     *   <pre>
     *   Array
     *   (
     *       [0] => 2013    // 시작 합삭 년도
     *       [1] => 7       // 시작 합삭 월
     *       [2] => 8       // 시작 합삭 일
     *       [3] => 16      // 시작 합삭 시
     *       [4] => 15      // 시작 합삭 분
     *       [5] => 2013    // 망 연도
     *       [6] => 7       // 망 월
     *       [7] => 23      // 망 일
     *       [8] => 2       // 망 시
     *       [9] => 59      // 망 분
     *       [10] => 2013   // 끝 합삭 년도
     *       [11] => 8      // 끝 합삭 월
     *       [12] => 7      // 끝 합삭 일
     *       [13] => 6      // 끝 합삭 시
     *       [14] => 50     // 끝 합삭 분
     *   )
     *   </pre>
     *
     * @param 'int 년'
     * @param 'int 월'
     * @param 'int 일'
     * @return Integer array
     */
    protected int[] getlunarfirst (int syear, int smonth, int sday) {
        int dm         = this.disp2days (syear, smonth, sday, 1995, 12, 31);
        BigDecimal dem = this.moonsundegree (MathUtil.convertIntToBigDecimal(dm));

        BigDecimal d  = MathUtil.convertIntToBigDecimal(dm);
        BigDecimal de = dem;

        do {
            if (de.compareTo(NumberUtils.createBigDecimal("13.5")) > 0) break;

            d  = d.subtract(NumberUtils.createBigDecimal("1"));
            de = this.moonsundegree (d);
        } while ( de.compareTo(NumberUtils.createBigDecimal("13.5")) > 0 );

        do {
            if (de.compareTo(NumberUtils.createBigDecimal("1")) > 0) break;

            d  = d.subtract(NumberUtils.createBigDecimal("0.04166666666"));
            de = this.moonsundegree (d);
        } while ( de.compareTo(NumberUtils.createBigDecimal("1")) > 0 );

        do {
            if (de.compareTo(NumberUtils.createBigDecimal("359.99")) < 0) break;

            d  = d.subtract(NumberUtils.createBigDecimal("0.000694444"));
            de = this.moonsundegree (d);
        } while ( de.compareTo(NumberUtils.createBigDecimal("359.99")) < 0 );

        d = d.add(NumberUtils.createBigDecimal("0.375"));
        d = d.multiply(NumberUtils.createBigDecimal("1440"));

        int i = (d.multiply(NumberUtils.createBigDecimal("-1"))).intValue();
        
        // list ($year, $month, $day, $hour, $min)
        int[] list0ro = this.getdatebymin (i, 1995, 12, 31, 0, 0);

        d  = MathUtil.convertIntToBigDecimal(dm);
        de = dem;

        do {
            if (de.compareTo(NumberUtils.createBigDecimal("346.5")) < 0) break;

            d  = d.add(NumberUtils.createBigDecimal("1"));
            de = this.moonsundegree (d);
        } while ( de.compareTo(NumberUtils.createBigDecimal("346.5")) < 0);

        do {
            if (de.compareTo(NumberUtils.createBigDecimal("359")) < 0) break;

            d  = d.add(NumberUtils.createBigDecimal("0.04166666666"));
            de = this.moonsundegree (d);
        } while ( de.compareTo(NumberUtils.createBigDecimal("359")) < 0 );

        do {
            if (de.compareTo(NumberUtils.createBigDecimal("0.01")) > 0) break;

            d  = d.add(NumberUtils.createBigDecimal("0.000694444"));
            de = this.moonsundegree (d);
        } while ( de.compareTo(NumberUtils.createBigDecimal("0.01")) > 0 );

        BigDecimal pd = d;
        d = d.add(NumberUtils.createBigDecimal("0.375"));
        d = d.multiply(NumberUtils.createBigDecimal("1440"));
        i = (d.multiply(NumberUtils.createBigDecimal("-1"))).intValue();

        // list ($year2, $month2, $day2, $hour2, $min2)
        int[] list2nd = this.getdatebymin (i, 1995, 12, 31, 0, 0);

        if ( smonth == list2nd[1] && sday == list2nd[2] ) {
            list0ro[0] = list2nd[0];        // $year  = $year2;
            list0ro[1] = list2nd[1];        // $month = $month2;
            list0ro[2] = list2nd[2];        // $day   = $day2;
            list0ro[3] = list2nd[3];        // $hour  = $hour2;
            list0ro[4] = list2nd[4];        // $min   = $min2;

            d = pd.add(NumberUtils.createBigDecimal("26"));
            de = this.moonsundegree (d);

            do {
                if (de.compareTo(NumberUtils.createBigDecimal("346.5")) < 0) break;

                d  = d.add(NumberUtils.createBigDecimal("1"));
                de = this.moonsundegree (d);
            } while ( de.compareTo(NumberUtils.createBigDecimal("346.5")) < 0 );

            do {
                if (de.compareTo(NumberUtils.createBigDecimal("359")) < 0) break;

                d  = d.add(NumberUtils.createBigDecimal("0.04166666666"));
                de = this.moonsundegree (d);
            } while ( de.compareTo(NumberUtils.createBigDecimal("359")) < 0 );

            do {
                if (de.compareTo(NumberUtils.createBigDecimal("0.01")) > 0) break;

                d  = d.add(NumberUtils.createBigDecimal("0.000694444"));
                de = this.moonsundegree (d);
            } while ( de.compareTo(NumberUtils.createBigDecimal("0.01")) > 0 );

            d = d.add(NumberUtils.createBigDecimal("0.375"));
            d = d.multiply(NumberUtils.createBigDecimal("1440"));
            i = (d.multiply(NumberUtils.createBigDecimal("-1"))).intValue();
            
            // list ($year2, $month2, $day2, $hour2, $min2)
            list2nd = this.getdatebymin (i, 1995, 12, 31, 0, 0);
        };

        d = MathUtil.convertIntToBigDecimal( this.disp2days (list0ro[0], list0ro[1], list0ro[2], 1995, 12, 31) );
        d = d.add(NumberUtils.createBigDecimal("12"));

        de = this.moonsundegree (d);

        do {
            if (de.compareTo(NumberUtils.createBigDecimal("166.5")) < 0) break;

            d  = d.add(NumberUtils.createBigDecimal("1"));
            de = this.moonsundegree (d);
        } while ( de.compareTo(NumberUtils.createBigDecimal("166.5")) < 0 );

        do {
            if (de.compareTo(NumberUtils.createBigDecimal("179")) < 0) break;

            d  = d.add(NumberUtils.createBigDecimal("0.04166666666"));
            de = this.moonsundegree (d);
        } while ( de.compareTo(NumberUtils.createBigDecimal("179")) < 0 );

        do {
            if (de.compareTo(NumberUtils.createBigDecimal("179.999")) < 0) break;

            d  = d.add(NumberUtils.createBigDecimal("0.000694444"));
            de = this.moonsundegree (d);
        } while ( de.compareTo(NumberUtils.createBigDecimal("179.999")) < 0 );

        d = d.add(NumberUtils.createBigDecimal("0.375"));
        d = d.multiply(NumberUtils.createBigDecimal("1440"));
        i = (d.multiply(NumberUtils.createBigDecimal("-1"))).intValue();

        // list ($year1, $month1, $day1, $hour1, $min1)
        int[] list1st = this.getdatebymin (i, 1995, 12, 31, 0, 0);

        //
        int[] iArr = {list0ro[0], list0ro[1], list0ro[2], list0ro[3], list0ro[4],
                        list1st[0], list1st[1], list1st[2], list1st[3], list1st[4],
                        list2nd[0], list2nd[1], list2nd[2], list2nd[3], list2nd[4]};

        return iArr;
    }
    
    
    /**
     * 양력 날짜를 음력 날짜로 변환
     *
     * @access protected
     *
     *   <pre>
     *   Array
     *   (
     *       [0] => 2013   // 음력 연도
     *       [1] => 6      // 음력 월
     *       [2] => 9      // 음력 일
     *       [3] =>        // 음력 윤달 여부 (boolean)
     *       [4] => 1      // 평달(false)/큰달(true) 여부 (boolean)
     *   )
     *   </pre>
     *
     * @param 'int 년'
     * @param 'int 월'
     * @param 'int 일'
     * @return String Array  (int 와 boolean 이 혼용)
     */
    protected String[] solartolunar (int solyear, int solmon, int solday) {
        int largemonth    = 0;
        int midname2      = 0;
        int lmoonyun      = 0;
        int lyear         = 0;
        BigDecimal lmonth = null;

        try {
            /*$smoyear, $smomonth, $smoday, $smohour, $smomin,      0~4
               $y0, $mo0, $d0, $h0, $mi0,                           5~9
               $y1, $mo1, $d1, $h1, $mi1                            10~14
               */
            int[] list1st = this.getlunarfirst(solyear, solmon, solday);

            int lday = this.disp2days(solyear, solmon, solday, list1st[0], list1st[1], list1st[2]) + 1;

            int i = Math.abs(this.disp2days(list1st[0], list1st[1], list1st[2], list1st[10], list1st[11], list1st[12]));

            if (i == 30)
                largemonth = 1;     // 대월
            else if (i == 29)
                largemonth = 0;     // 소월

            /*$inginame, $ingiyear, $ingimonth, $ingiday, $ingihour, $ingimin,              0~5
                $midname1, $midyear1, $midmonth1, $midday1, $midhour1, $midmin1,            6~11
                $outginame, $outgiyear, $outgimonth, $outgiday, $outgihour, $outgimin       12~17
                */
            int[] list2nd = this.solortoso24(list1st[0], list1st[1], list1st[2], list1st[3], list1st[4]);

            midname2 = list2nd[6] + 2;

            if (midname2 > 24)
                midname2 = 1;

            BigDecimal s0 = MathUtil.convertIntToBigDecimal(LunarConstants.month[midname2] - LunarConstants.month[list2nd[6]]);

            if (s0.compareTo(NumberUtils.createBigDecimal("0")) < 0)
                s0 = s0.add(NumberUtils.createBigDecimal("525949"));

            s0 = s0.multiply(NumberUtils.createBigDecimal("-1"));

            // $midyear2, $midmonth2, $midday2, $midhour2, $midmin2
            int[] list3rd = this.getdatebymin(s0.intValue(), list2nd[7], list2nd[8], list2nd[9], list2nd[10], list2nd[11]);

            if ((list2nd[8] == list1st[1] && list2nd[9] >= list1st[2]) || (list2nd[8] == list1st[11] && list2nd[9] < list1st[12])) {
                // ($midname1 - 1) / 2 + 1
                lmonth = (MathUtil.convertIntToBigDecimal(list2nd[6]).subtract(NumberUtils.createBigDecimal("1"))).divide(NumberUtils.createBigDecimal("2"), 0, BigDecimal.ROUND_CEILING).add(NumberUtils.createBigDecimal("1"));
                lmoonyun = 0;
            } else {
                if ((list3rd[1] == list1st[11] && list3rd[2] < list1st[12]) || (list3rd[1] == list1st[1] && list3rd[2] >= list1st[2])) {
                    // ($midname2 - 1) / 2 + 1;
                    lmonth = (MathUtil.convertIntToBigDecimal(midname2).subtract(NumberUtils.createBigDecimal("1"))).divide(NumberUtils.createBigDecimal("2"), 0, BigDecimal.ROUND_CEILING).add(NumberUtils.createBigDecimal("1"));
                    lmoonyun = 0;
                } else {
                    if (list1st[1] < list3rd[1] && list3rd[1] < list1st[11]) {
                        // ($midname2 - 1) / 2 + 1;
                        lmonth = (MathUtil.convertIntToBigDecimal(midname2).subtract(NumberUtils.createBigDecimal("1"))).divide(NumberUtils.createBigDecimal("2"), 0, BigDecimal.ROUND_CEILING).add(NumberUtils.createBigDecimal("1"));
                        lmoonyun = 0;
                    } else {
                        // ($midname1 - 1) / 2 + 1
                        lmonth = (MathUtil.convertIntToBigDecimal(list2nd[6]).subtract(NumberUtils.createBigDecimal("1"))).divide(NumberUtils.createBigDecimal("2"), 0, BigDecimal.ROUND_CEILING).add(NumberUtils.createBigDecimal("1"));
                        lmoonyun = 1;
                    }
                }
            }

            lyear = list1st[0];
            if (lmonth.intValue() == 12 && list1st[1] == 1)
                lyear--;

            if ((lmonth.intValue() == 11 && lmoonyun == 1) || lmonth.intValue() == 12 || lmonth.intValue() < 6) {
                // $midyear1, $midmonth1, $midday1, $midhour1, $midmin1     (Notify : list2nd[7] ~ list2nd[11] 까지 중복임, 별도의 이름으로 재정의)
                int[] list4th = this.getdatebymin(2880, list1st[0], list1st[1], list1st[2], list1st[3], list1st[4]);

                // $outgiyear, $outgimonth, $outgiday, $lnp, $lnp2          (Notify : list2nd[13] ~ list2nd[15] 까지 중복임, 별도의 이름으로 재정의)
                String[] list5th = this.solartolunar(list4th[0], list4th[1], list4th[2]);

                int outgimonth = Integer.valueOf(list5th[1]);
                int outgiday = Integer.valueOf(list5th[2]);

                outgiday = lmonth.subtract(NumberUtils.createBigDecimal("1")).intValue();
                if (outgiday == 0)
                    outgiday = 12;

                if (outgiday == outgimonth) {
                    if (lmoonyun == 1)
                        lmoonyun = 0;
                } else {
                    if (lmoonyun == 1) {
                        if (lmonth.intValue() != outgimonth) {
                            lmonth = lmonth.subtract(NumberUtils.createBigDecimal("1"));
                            if (lmonth.intValue() == 0) {
                                lyear--;
                                lmonth = NumberUtils.createBigDecimal("12");
                            }
                            ;
                            lmoonyun = 0;
                        }
                    } else {
                        if (lmonth.intValue() == outgimonth) {
                            lmoonyun = 1;
                        } else {
                            lmonth = lmonth.subtract(NumberUtils.createBigDecimal("1"));
                            if (lmonth.intValue() == 0) {
                                lyear--;
                                lmonth = NumberUtils.createBigDecimal("12");
                            }
                        }
                    }
                }
            }

            // array ($lyear, $lmonth, $lday, $lmoonyun ? true : false, $largemonth ? true : false);
            String[] sArr = {
                                TextUtil.convertString(lyear),
                                TextUtil.convertString(lmonth),
                                TextUtil.convertString(lday),
                                TextUtil.convertString(lmoonyun),
                                (lmoonyun == 1 ? "true" : "false"),
                                (largemonth == 1 ? "true" : "false")
                            };

            return sArr;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 음력 날짜를 양력 날짜로 변환
     *
     * @access protected
     *
     *   <pre>
     *   Array
     *   (
     *       [0] => 2013   // 양력 연도
     *       [1] => 6      // 양력 월
     *       [2] => 9      // 양력 일
     *   )
     *   </pre>
     *
     * @param 'int  년'
     * @param 'int  월'
     * @param 'int  일'
     * @param 'bool 음력 윤달 여부'
     * @return Integer array
     */
    protected int[] lunartosolar (int lyear, int lmonth, int lday, boolean moonyun) {
        int syear  = 0;
        int smonth = 0;
        int sday   = 0;

        /* $inginame, $ingiyear, $ingimonth, $ingiday, $ingihour, $ingimin,             0~5
            $midname, $midyear, $midmonth, $midday, $midhour, $midmin,                  6~11
            $outginame, $outgiyear, $outgimonth, $outgiday, $outgihour, $outgimin       12~17
        */
        int[] list1st = this.solortoso24 (lyear, 2, 15, 0, 0);

        list1st[6] = lmonth * 2 - 1;
        BigDecimal tmin = MathUtil.convertIntToBigDecimal(LunarConstants.month[list1st[6]]).multiply(NumberUtils.createBigDecimal("-1"));
        
        // $midyear, $midmonth, $midday, $midhour, $midmin
        int[] list2nd = this.getdatebymin (tmin.intValue(), list1st[1], list1st[2], list1st[3], list1st[4], list1st[5]);

        /* $outgiyear, $outgimonth, $outgiday, $hour, $min,                             0~4
            $yearm, $monthm1, $daym, $hourm, $minm,                                     5~9
            $year1, $month1, $day1, $hour1, $min1                                       10~14
        */
        int[] list3rd = this.getlunarfirst (list2nd[1], list2nd[2], list2nd[3]);

        // $lyear2, $lmonth2, $lday2, $lnp, $lnp2
        String[] list4th = this.solartolunar (list3rd[0], list3rd[1], list3rd[2]);

        if ( Integer.valueOf(list4th[0]) == lyear && lmonth == Integer.valueOf(list4th[1]) ) {
            // 평달, 윤달
            tmin = (NumberUtils.createBigDecimal("-1440").multiply(MathUtil.convertIntToBigDecimal(lday))).add(NumberUtils.createBigDecimal("10"));

            // $syear, $smonth, $sday, $hour, $min
            int[] list5th = this.getdatebymin (tmin.intValue(), list3rd[0], list3rd[1], list3rd[2], 0, 0);

            // assign "Return Value" - 01
            syear  = list5th[0];
            smonth = list5th[1];
            sday   = list5th[2];

            if ( moonyun ) {
                // $lyear2, $lmonth2, $lday2, $lnp, $lnp2
                String[] list6th = this.solartolunar (list3rd[10], list3rd[11], list3rd[12]);
                if ( Integer.valueOf(list6th[0]) == lyear && lmonth == Integer.valueOf(list6th[1]) ) {
                    tmin = (NumberUtils.createBigDecimal("-1440").multiply(MathUtil.convertIntToBigDecimal(lday))).add(NumberUtils.createBigDecimal("10"));

                    // $syear, $smonth, $sday, $hour, $min
                    int[] list7th = this.getdatebymin (tmin.intValue(), list3rd[10], list3rd[11], list3rd[12], 0, 0);

                    // assign "Return Value" - 02
                    syear  = list7th[0];
                    smonth = list7th[1];
                    sday   = list7th[2];
                }
            }
        } else {
            // ㅈ우기가 두번든 달의 전후
            // $lyear2, $lmonth2, $lday2, $lnp, $lnp2
            String[] list8th = this.solartolunar (list3rd[10], list3rd[11], list3rd[12]);
            if ( Integer.valueOf(list8th[0]) == lyear && lmonth == Integer.valueOf(list8th[1]) ) {
                tmin = (NumberUtils.createBigDecimal("-1440").multiply(MathUtil.convertIntToBigDecimal(lday))).add(NumberUtils.createBigDecimal("10"));

                // $syear, $smonth, $sday, $hour, $min
                int[] list9th = this.getdatebymin (tmin.intValue(), list3rd[10], list3rd[11], list3rd[12], 0, 0);

                // assign "Return Value" - 03
                syear  = list9th[0];
                smonth = list9th[1];
                sday   = list9th[2];
            }
        }

        //
        int[] iArr = {syear, smonth, sday};

        return iArr;
    }
    
    
    /**
     * 그레고리력 날짜를 요일의 배열 번호로 변환
     *
     * @access protected
     * @param 'int 년'
     * @param 'int 월'
     * @param 'int 일'
     * @return int
     */
    protected int getweekday (int syear, int smonth, int sday) {
        int d = this.disp2days ( syear, smonth, sday, LunarConstants.unityear, LunarConstants.unitmonth, LunarConstants.unitday );

        int i = this.div (MathUtil.convertIntToBigDecimal(d), NumberUtils.createBigDecimal("7") );
        d -= i * 7;

        while ( d > 6 || d < 0 ) {
            if (d > 6)
                d -= 7;
            else
                d += 7;
        }

        if ( d < 0 )
            d += 7;

        return d;
    }
    
    
    /**
     * 그레고리력의 날짜에 대한 28수를 구함
     *
     * @access protected
     * @param 'int 년'
     * @param 'int 월'
     * @param 'int 일'
     * @return int
     */
    protected int get28sday (int syear, int smonth, int sday) {
        int d = this.disp2days ( syear, smonth, sday, LunarConstants.unityear, LunarConstants.unitmonth, LunarConstants.unitday );

        int i = this.div (MathUtil.convertIntToBigDecimal(d), NumberUtils.createBigDecimal("28"));
        d -= i * 28;

        while ( d > 27 || d < 0 ) {
            if (d > 27)
                d -= 28;
            else
                d += 28;
        }

        if ( d < 0 )
            d += 7;

        d -= 11;

        if ( d < 0 )
            d += 28;

        return d;
    }
}
