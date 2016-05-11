package lunar.common.text;

import org.apache.commons.lang3.StringUtils;

public class TextUtil {
	public static String convertString(Object oVar) throws Exception {
		String sValidationVal = null;
		String sRtnVal        = null;

		if (oVar != null) {
			sValidationVal = String.valueOf(oVar);
			sRtnVal        = StringUtils.isNotEmpty(sValidationVal) ? sValidationVal : "" ;
		}

		return sRtnVal;
	}
}
