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
 * xml工具类
 * 
 * @author dave
 * @date 2017-06-08
 */
public class XmlToJson {
    public static void main(String[] args) throws Exception {
        String filename = "E:/workspace/FmyVocabulary/vocabulary_ciba/00013-on.xml";
        JSONObject json = xmlFileToJson(filename);
        System.out.println("xml2Json:" + json.toJSONString());
    }

    /**
     * org.dom4j.Document 转 com.alibaba.fastjson.JSONObject
     * 
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static JSONObject xmlFileToJson(String xmlFilename)
            throws DocumentException {
        String xmlString = FileUtil.readFile(xmlFilename);
        return xmlToJson(xmlString);
    }

    /**
     * org.dom4j.Document 转 com.alibaba.fastjson.JSONObject
     * 
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static JSONObject xmlToJson(String xmlString)
            throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlString);
        return elementToJson(doc.getRootElement());
    }

    // /**
    // * String 转 org.dom4j.Document
    // * @param xml
    // * @return
    // * @throws DocumentException
    // */
    // public static Document xmlFileToDocument(String xmlFilename) throws
    // DocumentException {
    // String xmlString = FileUtil.readFile3(xmlFilename);
    // return xmlStingToDocument(xmlString);
    // }

    // /**
    // * String 转 org.dom4j.Document
    // * @param xml
    // * @return
    // * @throws DocumentException
    // */
    // public static Document xmlStingToDocument(String xmlString) throws
    // DocumentException {
    // return DocumentHelper.parseText(xmlString);
    // }

    /**
     * 递归实现xml node to json
     * 
     * org.dom4j.Element 转 com.alibaba.fastjson.JSONObject
     * 
     * @param node
     * @return
     */
    public static JSONObject elementToJson(Element node) {
        JSONObject result = new JSONObject();
        // 当前节点的名称、文本内容和属性
        List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
        for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
            result.put("@" + attr.getName(), attr.getValue());
        }
        // 递归遍历当前节点所有的子节点
        List<Element> listElement = node.elements();// 所有一级子节点的list
        if (!listElement.isEmpty()) {
            for (Element e : listElement) {// 遍历所有一级子节点
                if (e.attributes().isEmpty() && e.elements().isEmpty()) // 判断一级节点是否有属性和子节点
                    result.put(e.getName(), e.getTextTrim());// 沒有则将当前节点作为上级节点的属性对待
                else {
                    if (!result.containsKey(e.getName())) {// 判断父节点是否存在该一级节点名称的属性
                        result.put(e.getName(), new JSONArray());// 没有则创建（第一次遍历）
                    }
                    JSONArray jsonArray = (JSONArray) result.get(e.getName());
                    jsonArray.add(elementToJson(e));// 将该一级节点放入该节点名称的属性对应的值中
                }
            }
        } else {
        }
        return result;
    }

}