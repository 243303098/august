package com.d1m.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: Leo.hu
 * @Date: 2018/9/10 13:39
 * @Description:
 */
public class RegExp {

    public boolean match(String reg, String str) {
        return Pattern.matches(reg, str);
    }

    public List<String> find(String reg, String str) {
        Matcher matcher = Pattern.compile(reg).matcher(str);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    public List<String> find(String reg, String str, int index) {
        Matcher matcher = Pattern.compile(reg).matcher(str);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            list.add(matcher.group(index));
        }
        return list;
    }

    public String findString(String reg, String str, int index) {
        String returnStr = null;
        List<String> list = this.find(reg, str, index);
        if (list.size() != 0)
            returnStr = list.get(0);
        return returnStr;
    }

    public String findString(String reg, String str) {
        String returnStr = null;
        List<String> list = this.find(reg, str);
        if (list.size() != 0)
            returnStr = list.get(0);
        return returnStr;
    }

    public static List<String> getKeywords(String p){
        String reg = "(?<=(?<!\\\\)\\$\\{)(.*?)(?=(?<!\\\\)\\})";
        RegExp re = new RegExp();
        List<String> list = re.find(reg, p);
        return list;
    }

}
