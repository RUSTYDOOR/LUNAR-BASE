import lunar.common.date.DateUtil;
import org.apache.commons.lang3.math.NumberUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainTemplate {

    public static void main(String[] args) {
        long lTimeStamp = 208710000000L;

        Date nowDate = new Date(lTimeStamp);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		System.out.println(simpleDateFormat.format(nowDate));

        simpleDateFormat = new SimpleDateFormat("yyyy");
		System.out.println(simpleDateFormat.format(nowDate));

        simpleDateFormat = new SimpleDateFormat("MM");
		System.out.println(simpleDateFormat.format(nowDate));

        simpleDateFormat = new SimpleDateFormat("dd");
		System.out.println(simpleDateFormat.format(nowDate));

		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(simpleDateFormat.format(nowDate));

		simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd a HH:mm:ss");
		System.out.println(simpleDateFormat.format(nowDate));

		simpleDateFormat = new SimpleDateFormat("오늘은 E요일 입니다.");
		System.out.println(simpleDateFormat.format(nowDate));

        try {
            System.out.println("yyyy : " + DateUtil.convertTimestampToDate(lTimeStamp, "yyyy") );
            System.out.println("MM : " +  DateUtil.convertTimestampToDate(lTimeStamp, "MM") );
            System.out.println("dd : " +  DateUtil.convertTimestampToDate(lTimeStamp, "dd") );
        } catch (Exception e) {
            e.printStackTrace();
        }


/*        BigDecimal bd1 = new BigDecimal("3");
        BigDecimal bd2 = new BigDecimal("1");*/

        // expect value : 2
//        System.out.println("#1 : " + bd1.subtract(bd2));

        //
//        BigDecimal bd3 = new BigDecimal("3");
//        for (; bd3.compareTo(NumberUtils.createBigDecimal("1")) > 0; bd3 = bd3.subtract(NumberUtils.createBigDecimal("1"))) {
//            System.out.println("#LOOP 3 : " + bd3);
//        }

        //
/*        BigDecimal bd1st = new BigDecimal("100");
        BigDecimal bd2nd = new BigDecimal("10");
        while (bd1st.compareTo(NumberUtils.createBigDecimal("90")) > 0) {
            System.out.println("============================");

            bd2nd = bd2nd.subtract(NumberUtils.createBigDecimal("1"));
            bd1st = bd1st.subtract(bd2nd);

            System.out.println("#LOOP 1st : " + bd1st);
            System.out.println("#LOOP 2nd : " + bd2nd);
        }*/

/*        BigDecimal bd1st = new BigDecimal("100");
        BigDecimal bd2nd = new BigDecimal("10");
        for (; bd1st.compareTo(NumberUtils.createBigDecimal("90")) > 0; bd2nd = bd2nd.subtract(NumberUtils.createBigDecimal("1")), bd1st = bd1st.subtract(bd2nd)) {
            System.out.println("============================");
            System.out.println("#LOOP 1st : " + bd1st);
            System.out.println("#LOOP 2nd : " + bd2nd);
        }

        for (; bd1st.compareTo(NumberUtils.createBigDecimal("80")) > 0; bd2nd = bd2nd.subtract(NumberUtils.createBigDecimal("1")), bd1st = bd1st.subtract(bd2nd)) {
            System.out.println("============================");
            System.out.println("#LOOP 1st : " + bd1st);
            System.out.println("#LOOP 2nd : " + bd2nd);
        }*/

/*        System.out.println("\n\n#FINAL 1st : " + bd1st);
        System.out.println("#FINAL 2nd : " + bd2nd);*/
    }
}