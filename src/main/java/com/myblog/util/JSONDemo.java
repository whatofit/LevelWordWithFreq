package com.myblog.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class JSONDemo {
    // 遍历json数组
    public void jsonArray() {
        // 方法１
        String json = "[{\"companyId\":\"111111111\",\"companyName\":\"Huuuu\",\"_uid\":10,\"_index\":0,\"_state\":\"modified\"},{\"companyId\":\"000000000000000000\",\"companyName\":\"cx01\",\"_uid\":11,\"_index\":1,\"_state\":\"modified\"},{\"companyId\":\"9999999999999\",\"companyName\":\"ttt\",\"_uid\":12,\"_index\":2,\"_state\":\"modified\"}]";
        System.out.println(json);
        List<HashMap> list = JSON.parseArray(json, HashMap.class);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).get("companyId"));
        }
        // 方法２
        JSONArray jarr = JSONArray.parseArray(json);
        for (Iterator iterator = jarr.iterator(); iterator.hasNext();) {
            JSONObject job = (JSONObject) iterator.next();
            System.out.println(job.get("companyId").toString());
        }

    }

    // 无序/有序遍历json数组
    public void orderJsonArray() {
        String jsonStr = "{\"size\":\"7.5\",\"width\":\"M (B)\"}";
        System.out.println(jsonStr);
        System.out.println("-------------------");
        System.out.println("无序遍历结果：");
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        System.out.println("-------------------");
        System.out.println("有序遍历结果：");
        LinkedHashMap<String, String> jsonMap = JSON.parseObject(jsonStr,
                new TypeReference<LinkedHashMap<String, String>>() {
                });
        for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    public static void main(String[] args) {
        JSONDemo demo = new JSONDemo();
        // demo.jsonArray();
        demo.orderJsonArray();
    }
}