package com.myblog.util;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * xml工具类 Created by dave on 2017/06/08.
 * 
 * @author dave
 * @date 2017/06/08
 */
public class Xml2Json {
    public static void main(String[] args) throws Exception {
        String filename = "E:/workspace/FmyVocabulary/vocabulary_ciba/00013-on.xml";
        JSONObject json = xmlFile2Json(filename);
        System.out.println("xml2Json:" + json.toJSONString());
    }

    /**
     * xml转json
     * 
     * @param xmlStr
     * @return
     * @throws DocumentException
     */
    public static JSONObject xmlFile2Json(String xmlFilename) throws DocumentException {
        String xmlString = FileUtil.readFile3(xmlFilename);
        return xml2Json(xmlString);
    }

    /**
     * xml转json
     * 
     * @param xmlStr
     * @return
     * @throws DocumentException
     */
    public static JSONObject xml2Json(String xmlString) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlString);
        JSONObject json = new JSONObject();
        element2Json(doc.getRootElement(), json);
        return json;
    }

    /**
     * xml转json
     * 
     * @param element
     * @param json
     */
    public static void element2Json(Element element, JSONObject json) {
        // 如果是属性
        // todo::打开注释
        // for (Attribute attr : element.attributes()) {
        // if (!isEmpty(attr.getValue())) {
        // json.put("@" + attr.getName(), attr.getValue().trim());
        // }
        // }
        List<Element> chdEl = element.elements();
        if (chdEl.isEmpty() && !isEmpty(element.getTextTrim())) {// 如果没有子元素,只有一个值
            json.put(element.getName(), element.getTextTrim());
        }

        for (Element e : chdEl) {// 有子元素
            if (!e.elements().isEmpty()) {// 子元素也有子元素
                JSONObject chdjson = new JSONObject();
                element2Json(e, chdjson);
                Object o = json.get(e.getName());
                if (o != null) {
                    JSONArray jsona = null;
                    if (o instanceof JSONObject) {// 如果此元素已存在,则转为jsonArray
                        JSONObject jsono = (JSONObject) o;
                        json.remove(e.getName());
                        jsona = new JSONArray();
                        jsona.add(jsono);
                        jsona.add(chdjson);
                    }
                    if (o instanceof JSONArray) {
                        jsona = (JSONArray) o;
                        jsona.add(chdjson);
                    }
                    json.put(e.getName(), jsona);
                } else {
                    if (!chdjson.isEmpty()) {
                        json.put(e.getName(), chdjson);
                    }
                }

            } else {// 子元素没有子元素
                for (Object o : element.attributes()) {
                    Attribute attr = (Attribute) o;
                    if (!isEmpty(attr.getValue())) {
                        json.put("@" + attr.getName(), attr.getValue().trim());
                    }
                }
                if (!e.getText().isEmpty()) {
                    json.put(e.getName(), e.getTextTrim()); // e.getText()
                }
            }
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.trim().isEmpty() || "null".equals(str)) {
            return true;
        }
        return false;
    }
}