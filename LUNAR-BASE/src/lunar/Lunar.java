package lunar;

/**
 * Project: Lunar :: 양력/음력 변환 클래스<br>
 * File:    Lunar
 *
 * 이 패키지는 양력/음력간의 변환을 제공한다.
 *
 * 1852년 10월 15일 이전의 양력 날자는 율리우스력으로 취급을 하며,
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

import lunar.LunarBase;

public class Lunar {

}
