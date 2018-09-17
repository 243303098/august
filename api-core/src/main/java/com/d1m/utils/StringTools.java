package com.d1m.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTools {
    /**
     * This method is for judge the string null or not null
     *
     * @param str
     * @return
     * @author d1m
     */
    public static boolean isNull(String str) {
        return (str == null ? true : false);
    }

    /**
     * This method is for judge the string is empty or not
     *
     * @param str
     * @return
     * @author d1m
     */
    public static boolean isEmpty(String str) {
        return (str.equals("") ? true : false);
    }

    /**
     * This method is for judge null or empty
     *
     * @param str
     * @return
     * @author d1m
     */
    public static boolean isNullOrEmpty(String str) {
        return (isNull(str) || isEmpty(str));
    }

    /**
     * This method is for get the matcher string
     *
     * @param source
     * @param rex
     * @return
     * @author d1m
     */
    public static String getMatch(String source, String rex) {
        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return source.substring(matcher.start(), matcher.end());
        }
        return null;

    }

    /**
     * get the match group
     *
     * @param source
     * @param rex
     * @param groupIndex
     * @return
     * @author d1m
     */
    public static String getMatchGroup(String source, String rex, int groupIndex) {
        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group(groupIndex);
        }
        return null;

    }

    /**
     * This method is for replaceAll the string
     *
     * @param source
     * @param rex
     * @param replaceBy
     * @author d1m
     */
    public static void replaceAll(String source, String rex, String replaceBy) {
        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            matcher.replaceAll(replaceBy);
        }
    }

    /**
     * This method is for judge is match
     *
     * @param source
     * @param rex
     * @return
     * @author d1m
     */
    public static boolean isMatch(String source, String rex) {
        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(source);
        return matcher.matches();
    }

    /**
     * @param str
     * @return
     * @author d1m
     */
    public static String chineseToUnicode(String str) {
        char[] chars = str.toCharArray();
        String result = "";
        for (int i = 0; i < chars.length; i++) {
            result += "\\u" + Integer.toString(chars[i], 16);
        }
        return result;
    }

    /**
     * @param unicode
     * @return
     * @author d1m
     */
    public static String unicodeToChinese(String unicode) {
        // unicode start with \\u
        String[] strs = unicode.split("\\\\u");
        String result = "";
        for (int i = 1; i < strs.length; i++) {
            result += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return result;
    }
}
