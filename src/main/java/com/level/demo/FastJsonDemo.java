package com.level.demo;

//fastjson.jar下载地址
//http://mvnrepository.com/artifact/com.alibaba/fastjson

//Fastjson API入口类是com.alibaba.fastjson.JSON，常用的序列化操作都可以在JSON类上的静态方法直接完成。
//public static final Object parse(String text); // 把JSON文本parse为JSONObject或者JSONArray 
//public static final JSONObject parseObject(String text)； // 把JSON文本parse成JSONObject    
//public static final <T> T parseObject(String text, Class<T> clazz); // 把JSON文本parse为JavaBean 
//public static final JSONArray parseArray(String text); // 把JSON文本parse成JSONArray 
//public static final <T> List<T> parseArray(String text, Class<T> clazz); //把JSON文本parse成JavaBean集合 
//public static final String toJSONString(Object object); // 将JavaBean序列化为JSON文本 
//public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本 
//public static final Object toJSON(Object javaObject); 将JavaBean转换为JSONObject或者JSONArray。

//序列化：
//String jsonString = JSON.toJSONString(obj);
//反序列化：
//VO vo = JSON.parseObject("...", VO.class);
//泛型反序列化：
//import com.alibaba.fastjson.TypeReference;
//List<VO> list = JSON.parseObject("...", new TypeReference<List<VO>>() {})

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.level.util.FastJsonUtil;

public class FastJsonDemo {
    /**
     * @param args
     */
    public static void main(String[] args) {
        FastJsonDemo fastJson = new FastJsonDemo();
        fastJson.test_dateFormat();
        fastJson.test_obj2json();
        fastJson.test_json2obj();
        fastJson.test_json2list();
        fastJson.test_json2map();
    }

    public void test_dateFormat() {
        Date date = new Date();
        String json = FastJsonUtil.obj2json(date);
        // String expected = "\""
        // + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
        // + "\"";
        System.out.println("test_dateFormat:" + json);
    }

    public void test_obj2json() {
        User user = new User(1, "张三");
        String json = FastJsonUtil.obj2json(user);
        System.out.println("test_obj2json:json1=" + json);
        List<User> list = new ArrayList<User>();
        list.add(new User(1, "张三"));
        list.add(new User(2, "李四"));
        String json2 = FastJsonUtil.obj2json(list);
        System.out.println("test_obj2json:json2=" + json2);
        Map<String, User> map = new HashMap<String, User>();
        map.put("user1", new User(1, "张三"));
        map.put("user2", new User(2, "李四"));
        String json3 = FastJsonUtil.obj2json(map);
        System.out.println("test_obj2json:json3=" + json3);
    }

    public void test_json2obj() {
        String json = "{\"id\":1,\"name\":\"张三\"}";
        User user = FastJsonUtil.json2obj(json, User.class);
        System.out.println("test_json2obj,id=" + user.getId() + ",name＝"
                + user.getName());
    }

    public void test_json2list() {
        String json = "[{\"id\":1,\"name\":\"张三\"},{\"id\":2,\"name\":\"李四\"}]";
        List<User> list = FastJsonUtil.json2list(json, User.class);
        User user1 = list.get(0);
        User user2 = list.get(1);
        System.out.println("test_json2list1,id=" + user1.getId() + ",name＝"
                + user1.getName());
        System.out.println("test_json2list2,id=" + user2.getId() + ",name＝"
                + user2.getName());
    }

    public void test_json2map() {
        String json = "{\"id\":1,\"name\":\"张三\"}";
        Map<String, Object> map = FastJsonUtil.json2map(json);
        System.out.println("test_json2map,map.toString()=" + map.toString());
        String json2 = "{\"user2\":{\"id\":2,\"name\":\"李四\"},\"user1\":{\"id\":1,\"name\":\"张三\"}}";
        Map<String, User> map2 = FastJsonUtil.json2map(json2, User.class);
        User user1 = map2.get("user1");
        User user2 = map2.get("user2");
        System.out.println("test_json2map1,id=" + user1.getId() + ",name＝"
                + user1.getName());
        System.out.println("test_json2map2,id=" + user2.getId() + ",name＝"
                + user2.getName());
    }

    private static class User {
        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "{\"id\":" + id + ",\"name\":\"" + name + "\"}";
        }

        public int getId() {
            return id;
        }

        // public void setId(int id) {
        // this.id = id;
        // }

        public String getName() {
            return name;
        }

        // public void setName(String name) {
        // this.name = name;
        // }
    }
}