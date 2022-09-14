package lunar;

import com.sun.xml.internal.bind.v2.TODO;
import lunar.common.text.TextUtil;

import java.util.HashMap;

public class LunarMain {
    public static void main(String[] args) {
        Lunar lunar = new Lunar();

        try {
            /*
             * lunar.toargs (&$v)
             *
             * input:
             *        2013-07-13
             *        2013-7-13
             *        20130713
             *        1373641200
             *        Null
             *
             * output:
             *       Array
             *       (
             *           [0] => 2013
             *           [1] => 7
             *           [2] => 13
             *       )
             *
             * and reference variavble $v is changed to '2013-07-13'
             */
            String sDate = "19760813";
            int[] iArr = lunar.toargs(sDate);

            System.out.println("### " + sDate + "\n");
            System.out.println("iArr : " + iArr.toString() + "\n");


            /*
             * lunar.human_year ($y)
             *
             * input:
             *        -2334
             *
             * output:
             *        BC 2333
             */
            System.out.println("lunar.human_year(-2333) : " + lunar.human_year(-2333));


            /*
             * $lunar->is_reap ($y)
             *
             * input:
             *        1992 (양력)
             *        1582년 이전이라면 bJulian 은 True
             *
             * output:
             *        true
             */
            if ( lunar.is_leap(1976, false) ) {
                System.out.println("This is Yoon Year");
            } else {
                System.out.println("This is not Yoon Year");
            }


            /*
             * $lunar.tolunar ($date)
             *
             * input:
             *        2013-07-16 or
             *        2013-7-16  or
             *        20130716   or
             *        1373900400 or
             *        NULL
             *
             * output
             *        stdClass Object
             *        (
             *            [fmt] => 2013-06-09
             *            [dangi] => 4346
             *            [hyear] => AD 2013
             *            [year] => 2013
             *            [month] => 6
             *            [day] => 9
             *            [leap] =>
             *            [largemonth] => 1
             *            [week] => 화
             *            [hweek] => 火
             *            [unixstamp] => 1373900400
             *            [ganji] => 계사
             *            [hganji] => 癸巳
             *            [gan] => 계
             *            [hgan] => 癸
             *            [ji] => 사
             *            [hji] => 巳
             *            [ddi] => 뱀
             *        )
             */
            HashMap<?, ?> hmToLunar = lunar.tolunar("19760813");
            System.out.println("largemonth : " + TextUtil.convertString(hmToLunar.get("largemonth")) );


            /*
             * lunar.tosolar ($date)
             *
             * input:
             *        2013-06-09 or
             *        2013-6-09  or
             *        20130609   or
             *        NULL
             *
             * output
             *        stdClass Object
             *        (
             *            [fmt] => 2013-07-16
             *            [dangi] => 4346
             *            [hyear] => AD 2013
             *            [year] => 2013
             *            [month] => 7
             *            [day] => 16
             *            [week] => 화
             *            [hweek] => 火
             *            [unixstamp] => 1373900400
             *            [ganji] => 계사
             *            [hganji] => 癸巳
             *            [gan] => 계
             *            [hgan] => 癸
             *            [ji] => 사
             *            [hji] => 巳
             *            [ddi] => 뱀
             *        )
             */
            boolean bYoon = Boolean.valueOf( TextUtil.convertString(hmToLunar.get("largemonth")) );
            System.out.println( lunar.tosolar ("2013-06-09", bYoon));


            /*
             * TODO: 윤달 확인 Method 제작 필요
             * 구하는 음력월의 윤달 여부를 모른다면 다음과 같이 확인
             * 과정이 필요하다.
            // $lun = '2013-06-09';
            // $solv = $lunar->tosolar ($lun);
            // $lunv = $lunar->tolunar ($sol->fmt);
            // if ( $lun != $lunv->fmt )
            //     $solv = $lunar->tosolar ($lun, true);
            */

            /*
             * lunar.dayfortuen ($date)
             *
             * input:
             *        2013-07-16 or
             *        2013-7-16  or
             *        20130716   or
             *        1373900400 or
             *        NULL
             *
             * output:
             *        stdClass Object
             *        (
             *            [data] => stdClass Object
             *                (
             *                     [y] => 29           // 세차 index
             *                     [m] => 55           // 월건 index
             *                     [d] => 19           // 일진 index
             *                )
             *
             *            [year] => 계사               // 세차 값
             *            [month] => 기미              // 월건 값
             *            [day] => 계미                // 일진 값
             *            [hyear] => 癸巳              // 한자 세차 값
             *            [hmonth] => 己未             // 한자 월건 값
             *            [hday] => 癸未               // 한자 일진 값
             *        )
             */
            System.out.println("lunar.dayfortune : " + lunar.dayfortune("1976-08-13"));


            /*
             * 일진을 구하는 방법 (Bad Case)
             * 8월 1일 부터 30일 까지의 일진을 구할 경우
             * 다음은 아주 안좋은 방법이다.
             */
            String[] arrIljin = new String[31];
            for ( int i=1; i < 31; i++ ) {
                HashMap<?, ?> hmDayfortune = lunar.dayfortune("1976-08-" + i);
                arrIljin[i] = TextUtil.convertString( hmDayfortune.get("day") );
            }
            System.out.println("(Bad Case) iljin : " + arrIljin.toString());


            /*
             * 일진을 구하는 방법 (Good Case)
             * 위의 경우는 아래와 같이 $lunar->ganji_ref method를 이용하여
             * 성능을 높일 수 있다.
             */
            HashMap<?, ?> hmDayfortune = lunar.dayfortune("1976-08-13");
            arrIljin     = new String[31];
            arrIljin[13] = TextUtil.convertString( hmDayfortune.get("day") );
            int iIndex   = ((int[])hmDayfortune.get("date"))[2];

            for ( int i=2; i<31; i++ ) {
                iIndex++;
                if ( iIndex >= 60 )
                    iIndex -= 60;
                arrIljin[i] = lunar.ganji_ref(iIndex, true);
            }
            System.out.println("(Good Case) iljin : " + arrIljin.toString());


            /*
             * $lunar.s28day ($date)
             *
             * input:
             *        2013-07-16 or
             *        2013-7-16  or
             *        20130716   or
             *        1373900400 or
             *        NULL
             *
             * output:
             *        stdClass Object
             *        (
             *            [data] => 5
             *            [k] => 미
             *            [h] => 尾
             *        )
             */
            System.out.println("lunar.s28day : " + lunar.s28day("1976-08-13"));


            /*
             * TODO: 특정일의 28수를 구하는 부분 로직 구현 필요
             * 역시 7/1 부터 7/30 까지의 28수를 구할 경우에는 다음과 같이
             * 하면 성능이 매우 좋아진다.
             */
            //$s28 = null;
            //for ( $i=0; $i<30; $i++ ) {
            //    if ( $s28 === null )
            //        $s28 = $lunar->s28day ('2013-07-01');
            //    else
            //        $s28 = $lunar->s28day ($s28);
            //
            //    $s28v[$i] = $s28->k;
            //}


            /*
             * lunar.seasondate ($date)
             *
             * input:
             *        2013-07-16 or
             *        2013-7-16  or
             *        20130716   or
             *        1373900400 or
             *        NULL
             *
             * output:
             *        stdClass Object
             *        (
             *            [center] => stdClass Object
             *                (
             *                    [name] => 소서
             *                    [hname] => 小暑
             *                    [hyear] => AD 2013
             *                    [year] => 2013
             *                    [month] => 7
             *                    [day] => 7
             *                    [hour] => 7
             *                    [min] => 49
             *                )
             *
             *            [ccenter] => stdClass Object
             *                (
             *                    [name] => 대서
             *                    [hname] => 大暑
             *                    [hyear] => AD 2013
             *                    [year] => 2013
             *                    [month] => 7
             *                    [day] => 23
             *                    [hour] => 1
             *                    [min] => 11
             *                )
             *
             *            [nenter] => stdClass Object
             *                (
             *                    [name] => 입추
             *                    [hname] => 立秋
             *                    [hyear] => AD 2013
             *                    [year] => 2013
             *                    [month] => 8
             *                    [day] => 7
             *                    [hour] => 17
             *                    [min] => 36
             *                )
             *        )
             */
            System.out.println("lunar.seasondate : " + lunar.seasondate("1976-08-13"));


            /*
             * lunar.moonstatus ($date)
             *
             * input:
             *        2013-07-16 or
             *        2013-7-16  or
             *        20130716   or
             *        1373900400 or
             *        NULL
             *
             * output:
             *   stdClass Object
             *   (
             *       [new] => stdClass Object
             *           (
             *               [hyear] => AD 2013
             *               [year] => 2013
             *               [month] => 7
             *               [day] => 8
             *               [hour] => 16
             *               [min] => 15
             *           )
             *
             *       [full] => stdClass Object
             *           (
             *               [hyear] => AD 2013
             *               [year] => 2013
             *               [month] => 7
             *               [day] => 23
             *               [hour] => 2
             *               [min] => 59
             *           )
             *   )
             */
            System.out.println("lunar.moonstatus : " + lunar.moonstatus("1976-08-13"));


            /*
             * 합삭/망 정보의 경우, 한달에 음력월이 2개가 있으므로,
             * 1일의 정보만 얻어서는 합삭/망 중에 1개의 정보만 나올 수 있다.
             * 그러므로, 1일의 데이터를 얻은 다음, 음력 1일의 정보까지 구하면
             * 한달의 합삭/망 정보를 모두 표현할 수 있다.
             */
            int iPlus;

            HashMap<?, ?> hmTolunar = lunar.tolunar ("2013-07-01");

            if ( Boolean.valueOf(TextUtil.convertString(hmToLunar.get("largemonth"))) ) // 평달의 경우 마지막이 29일이고 큰달은 30일이다.
                iPlus = 29 - TextUtil.convertInteger( hmTolunar.get("day") );
            else
                iPlus = 30 - TextUtil.convertInteger( hmTolunar.get("day") );

            HashMap<?, ?> hmMoonstatus1 = lunar.moonstatus ("2013-07-01");                  // 음력 2013-05-23
            HashMap<?, ?> hmMoonstatus2 = lunar.moonstatus ("2013-07-" + (1 + iPlus));      // 음력 2013-06-01

            System.out.println("lunar.moonstatus : " + hmMoonstatus1.toString());
            System.out.println("lunar.moonstatus : " + hmMoonstatus2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}