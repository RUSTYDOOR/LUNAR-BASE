import lunar.common.date.DateUtil;
import org.apache.commons.lang3.math.NumberUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainTemplate {

    public static void main(String[] args) {
        int i = 1;
        System.out.println(String.format("%02d", i));
        System.out.println(String.format("%02d", 11));


        BigDecimal bd1st = new BigDecimal("100");
        BigDecimal bd2nd = new BigDecimal("5");

        do {
            if (bd1st.compareTo(NumberUtils.createBigDecimal("97")) > 0)
                break;

            bd2nd = bd2nd.subtract(NumberUtils.createBigDecimal("1"));
            bd1st = bd1st.subtract(bd2nd);

            System.out.println("bd1st : " + bd1st);
            System.out.println("bd2nd : " + bd2nd);
        } while ( bd1st.compareTo(NumberUtils.createBigDecimal("97")) > 0 );


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