package lunar.consts;

public class LunarConstants {
    // integer Array
    public static final int[] month = {0, 21355, 42843, 64498, 86335, 108366, 130578, 152958,
                                        175471, 198077, 220728, 243370, 265955, 288432, 310767,
                                        332928, 354903, 376685, 398290, 419736, 441060, 462295,
                                        483493, 504693, 525949};

    /**
     * 십간(十干) 데이터
     * String array
     */
    public static final String[] gan = {"갑", "을", "병", "정", "무", "기", "경", "신", "임", "계"};
    
    /**
     * 십간(十干) 한자 데이터
     * String array
     */
    public static final String[] hgan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    
    /**
     * 십이지(十二支) 데이터
     * String array
     */
    public static final String[] ji = {"자", "축", "인", "묘", "진", "사", "오", "미", "신", "유", "술", "해"};
    
    /**
     * 십이지(十二支) 한자 데이터
     * String array
     */
    public static final String[] hji = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    /**
     * 띠 데이터
     * String array
     */
    public static final String[] ddi = {"쥐", "소", "호랑이", "토끼", "용", "뱀", "말", "양", "원숭이", "닭", "개", "돼지"};
    
    /**
     * 병자년 경인월 신미일 기해시 입춘 데이터
     * integer
     */
    public static final int unityear  = 1996;
    public static final int unitmonth = 2;
    public static final int unitday   = 4;
    public static final int unithour  = 22;
    public static final int unitmin   = 8;
    public static final int unitsec   = 0;

    /**
     * 병자년 데이터
     * integer
     */
    public static final int uygan = 2;
    public static final int uyji  = 0;
    public static final int uysu  = 12;

    /**
     * 경인년 데이터
     * integer
     */
    public static final int umgan = 6;
    public static final int umji  = 2;
    public static final int umsu  = 26;

    /**
     * 신미일 데이터
     * integer
     */
    public static final int udgan = 7;
    public static final int udji  = 7;
    public static final int udsu  = 7;

    /**
     * 기해시 데이터
     * integer
     */
    public static final int uhgan = 5;
    public static final int uhji  = 11;
    public static final int uhsu  = 35;

    /**
     * 정월 초하루 합삭 시간
     * integer
     */
    public static final int unitmyear  = 1996;
    public static final int unitmmonth = 2;
    public static final int unitmday   = 19;
    public static final int unitmhour  = 8;
    public static final int unitmmin   = 30;
    public static final int unitmsec   = 0;
    public static final int moonlength = 42524;
}
