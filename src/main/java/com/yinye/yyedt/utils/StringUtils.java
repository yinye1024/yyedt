package com.yinye.yyedt.utils;

public class StringUtils {
    public static String substringBeforeLast(String str, String separator) {
        if (!isEmpty(str) && !isEmpty(separator)) {
            int pos = str.lastIndexOf(separator);
            return pos == -1 ? str : str.substring(0, pos);
        } else {
            return str;
        }
    }
    public static String substringBefore(String str, int separator) {
        if (isEmpty(str)) {
            return str;
        } else {
            int pos = str.indexOf(separator);
            return pos == -1 ? str : str.substring(0, pos);
        }
    }
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static String getLineSeparator(){
        return System.getProperty("line.separator");
    }
}
