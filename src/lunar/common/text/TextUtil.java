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
        public static int convertInteger(Object oVar) throws Exception {
        String sValidationVal = null;
        int iRtnVal = 0;

        if (oVar != null) {
            sValidationVal = String.valueOf(oVar);
            iRtnVal = StringUtils.isNotEmpty(sValidationVal) ? Integer.valueOf((String) sValidationVal) : 0;
        }

        return iRtnVal;
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().length() == 0 || "null".equalsIgnoreCase(s.trim());
    }

    public static boolean isNotNullAndNotEmpty(String s) {
        return !isNullOrEmpty(s);
    }
}