package com.level.util;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * fastjson util
 * 
 * @author magic_yy
 * @see https://github.com/alibaba/fastjson
 * @see http://code.alibabatech.com/wiki/display/FastJSON
 */
public class FastJsonUtil {

//    private static SerializeConfig configMapping = new SerializeConfig();
//
//    static {
//    	configMapping.put(Date.class, new SimpleDateFormatSerializer(
//                "yyyy-MM-dd HH:mm:ss"));
//    }

    private static final SerializerFeature[] features = {
    		SerializerFeature.PrettyFormat,//JSON格式化输出
    		// SerializerFeature.WriteMapNullValue, // 输出空置字段
            // SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            // SerializerFeature.WriteNullNumberAsZero,// 数值字段如果为null，输出为0，而不是null
            // SerializerFeature.WriteNullBooleanAsFalse,// Boolean字段如果为null，输出为false，而不是null
            // SerializerFeature.WriteNullStringAsEmpty,// 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteDateUseDateFormat // 日期格式化yyyy-MM-dd HH:mm:ss
    };

    /**
     * javaBean、list、map convert to json string
     */
    public static String obj2json(Object obj) {
        // return
        // JSON.toJSONString(obj,SerializerFeature.UseSingleQuotes);//使用单引号
        // return JSON.toJSONString(obj,true);//格式化数据，方便阅读
    	return JSON.toJSONString(obj, features);
        //return JSON.toJSONString(obj, mapping);
        //return JSON.toJSONString(obj, configMapping, features);
    }

    /**
     * json string convert to javaBean、map
     */
    public static <T> T json2obj(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {
        return JSON.parseArray(jsonArrayStr, clazz);
    }

    /**
     * json string convert to map
     */
    public static <T> Map<String, Object> json2map(String jsonStr) {
        return json2obj(jsonStr, Map.class);
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz) {
        Map<String, T> map = JSON.parseObject(jsonStr,
                new TypeReference<Map<String, T>>() {
                });
        for (Entry<String, T> entry : map.entrySet()) {
            JSONObject obj = (JSONObject) entry.getValue();
            map.put(entry.getKey(), JSONObject.toJavaObject(obj, clazz));
        }
        return map;
    }
}