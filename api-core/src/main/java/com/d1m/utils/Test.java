package com.d1m.utils;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.d1m.Entity.CaseDetailsEntity;
import com.d1m.manage.CaseDetailsEntityManager;

import java.util.*;

import static com.d1m.utils.Constants.*;

public class Test {

    public static void main(String[] args) {
        //String result = "{\"msg\":\"SUCCESS\",\"data\":{\"token\":\"c48c9307ed1199cf1e931d884c4ee500\"},\"resultCode\":\"1\"}";
//        for (int i = 1; i < 1001; i++) {
//            String result = HttpClientUtil.sendHttpsGet("https://rimowaestore.fgcndigital.com/api/v4/estore/member/getFakeToken/33/43/"+i+"/123");
//            List<Object> list = (List<Object>)JSONPath.eval(JSONObject.parse(result), "$..token");
//            System.out.println(list.get(0).toString());
//        }
        List list = new ArrayList();
        list.add("111");
        list.add("222");
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).toString().replaceAll("1","2"));
            System.out.println(list.get(i));
        }

        Map map = new HashMap();
        map.put("11","22");
    }

}
