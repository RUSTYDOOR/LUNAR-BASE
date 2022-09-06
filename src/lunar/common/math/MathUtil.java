package lunar.common.math;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;

public class MathUtil {
	
	/**
	 * Convert int to BigDeciaml
	 * 
	 * @param iVal
	 * @return BigDecimal
	 * @throws Exception
	 */
	public static BigDecimal convertIntToBigDecimal(int iVal) {
		return (NumberUtils.createBigDecimal(String.valueOf(iVal))); 
	}
	
	/**
	 * Convert double to BigDecimal
	 * 
	 * @param dVal
	 * @return BigDecimal
	 */
	public static BigDecimal convertDoubleToBigDecimal(double dVal) {
		return (NumberUtils.createBigDecimal(String.valueOf(dVal))); 
	}
}
