import lunar.common.date.DateUtil;
import org.apache.commons.lang3.math.NumberUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainTemplate {

    public static void main(String[] args) {
        int i = 1;
        BigDecimal bd1st = new BigDecimal("-166.1484501810230884281");

        if (bd1st.compareTo(NumberUtils.createBigDecimal("360")) >= 0) System.out.println("360 true"); else System.out.println("360 false");

        if (bd1st.compareTo(NumberUtils.createBigDecimal("1")) < 0) System.out.println("0 true"); else System.out.println("0 false");

        while ( bd1st.compareTo(NumberUtils.createBigDecimal("360")) >= 0 || bd1st.compareTo(NumberUtils.createBigDecimal("0")) < 0 ) {

            if ( bd1st.compareTo(NumberUtils.createBigDecimal("0")) > 0 ) {
                // di -= 360;
                bd1st = bd1st.subtract(NumberUtils.createBigDecimal("360"));
            } else {
                // di += 360;
                bd1st = bd1st.add(NumberUtils.createBigDecimal("360"));
            }
        }

        System.out.println("bd1st : " + bd1st);


        //
        /*BigDecimal bd1st = new BigDecimal("100");
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