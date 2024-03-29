<?php
/*
 * Lunar API import
 */
/*
 * oops/KASI_Lunar pcakge가 설치 되어 있으면, 1391-02-05 ~ 2050-12-31 까지의 음력 데이터를
 * 한국 천문 연구소의 데이터를 이용한다.
 */
require_once 'KASI_Lunar.php';
require_once 'Lunar.php';


// $target = $argv[1] ? $argv[1] : date ('Ymd', time ());

$lunar = new oops\Lunar;


/*
 * $lunar->toargs (&$v)
 *
 * input:
 *        2013-07-13
 *        1976-09-06
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

$v = '1976-09-06';
$r = $lunar->toargs ($v);
echo "### $v\n";
print_r ($r);
echo "\n<BR/><BR/>";

/*
 * $lunar->human_year ($y)
 *
 * input:
 *        -2334
 *
 * output:
 *        BC 2333
 */

echo $lunar->human_year (-2333);
echo "\n<BR/><BR/>";

/*
 * $lunar->is_reap ($y)
 *
 * input:
 *        1992 (양력)
 *
 * output:
 *        true
 */

if ( $lunar->is_leap (1976) )
    echo "This is Yoon Year\n";
else
    echo "This is not Yoon Year\n";

echo "\n<BR/><BR/>";

/*
 * $lunar->tolunar ($date)
 *
 * input:
 *        1976-09-06 or
 *        2013-7-16  or
 *        20130716   or
 *        1373900400 or
 *        NULL
 *
 * output
 *        stdClass Object
 *        (
 *            [fmt] => 1976-08-13
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

print_r ($lunar->tolunar ('1976-09-06'));
echo "\n<BR/><BR/>";

/*
 * $lunar->tosolar ($date)
 *
 * input:
 *        1976-08-13 or
 *        2013-6-09  or
 *        20130609   or
 *        NULL
 *
 * output
 *        stdClass Object
 *        (
 *            [fmt] => 1976-09-06
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

print_r ($lunar->tosolar ('1976-08-13'));
echo "\n<BR/><BR/>";

/*
 * 구하는 음력월의 윤달 여부를 모른다면 다음과 같이 확인
 * 과정이 필요하다.
 */
$lun = '1976-08-13';
$solv = $lunar->tosolar ($lun);
$lunv = $lunar->tolunar ($solv->fmt);
if ( $lun != $lunv->fmt )
    $solv = $lunar->tosolar ($lun, true);
echo "\n<BR/><BR/>";


/*
 * $lunar->dayfortuen ($date)
 *
 * input:
 *        1976-09-06 or
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

print_r ($lunar->dayfortune ('1976-09-06'));
echo "\n<BR/><BR/>";

/*
 * 7월 1일 부터 30일 까지의 일진을 구할 경우
 * 다음은 아주 안좋은 방법이다.
 */

for ( $i=1; $i<31; $i++ ) {
    $r = $lunar->dayfortune ('1976-08-' . $i);
    $iljin[$i] = $r->day;
}
echo "\n<BR/><BR/>";

/*
 * 위의 경우는 아래와 같이 $lunar->ganji_ref method를 이용하여
 * 성능을 높일 수 있다.
 */

$r = $lunar->dayfortune ('1976-08-01');
$iljin[$i] = $r->day;
$gindex = $r->data->d;

for ( $i=2; $i<31; $i++ ) {
    $gindex++;
    if ( $gindex >= 60 )
        $gindex -= 60;
    $iljin[$i] = $lunar->ganji_ref[$gindex];
}
echo "\n<BR/><BR/>";

/*
 * $lunar->s28day ($date)
 *
 * input:
 *        1976-09-06 or
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

print_r ($lunar->s28day ('1976-09-06'));
echo "\n<BR/><BR/>";

/*
 * 역시 7/1 부터 7/30 까지의 일진을 구할 경우에는 다음과 같이
 * 하면 성능이 매우 좋아진다.
 */

$s28 = null;
for ( $i=0; $i<30; $i++ ) {
    if ( $s28 === null )
        $s28 = $lunar->s28day ('1976-08-01');
    else
        $s28 = $lunar->s28day ($s28);

    $s28v[$i] = $s28->k;
}
echo "\n<BR/><BR/>";

/*
 * $lunar->seasondate ($date)
 *
 * input:
 *        1976-09-06 or
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

print_r ($lunar->seasondate ('1976-09-06'));
echo "\n<BR/><BR/>";

/*
 * $lunar->moonstatus ($date)
 *
 * input:
 *        1976-09-06 or
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

print_r ($lunar->moonstatus ('1976-08-01'));
echo "\n<BR/><BR/>";

/*
 * 합삭/망 정보의 경우, 한달에 음력월이 2개가 있으므로,
 * 1일의 정보만 얻어서는 합삭/망 중에 1개의 정보만 나올 수 있다.
 * 그러므로, 1일의 데이터를 얻은 다음, 음력 1일의 정보까지 구하면
 * 한달의 합삭/망 정보를 모두 표현할 수 있다.
 */

$lun = $lunar->tolunar ('1976-08-01');

if ( $lun->largemonth ) // 평달의 경우 마지막이 29일이고 큰달은 30일이다.
    $plus = 29 - $lun->day;
else
    $plus = 30 - $lun->day;

$r1 = $lunar->moonstatus ('1976-08-01');           // 음력 2013-05-23
$r2 = $lunar->moonstatus ('1976-08-' . 1 + $plus); // 음력 2013-06-01

print_r ($r1);
echo "\n<BR/>";
print_r ($r2);

?>