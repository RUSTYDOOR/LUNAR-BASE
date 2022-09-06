package lunar.common.text;

import org.apache.commons.lang3.StringUtils;

public class TextUtil {
    public static String convertString(Object oVar) throws Exception {
        String sValidationVal = null;
        String sRtnVal = null;

        if (oVar != null) {
            sValidationVal = String.valueOf(oVar);
            sRtnVal = StringUtils.isNotEmpty(sValidationVal) ? sValidationVal : "";
        }

        return sRtnVal;
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().length() == 0 || "null".equalsIgnoreCase(s.trim());
    }

    public static boolean isNotNullAndNotEmpty(String s) {
        return !isNullOrEmpty(s);
    }
}