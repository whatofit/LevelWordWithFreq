//package com.level.demo;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.dom4j.Attribute;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
///**
// * Created by chengsheng on 2015/8/19.
// */
//public class Xml2JsonDemo {
//	public static void main(String[] args) throws Exception {
//		String filename = "E:/workspace/FmyVocabulary/vocabulary_ciba/00015-with.xml";
//		JSONObject json = xmlFile2Json(filename);
//		System.out.println("xml2Json:" + json.toJSONString());
//	}
//
//	/**
//	 * xmlתjson
//	 * 
//	 * @param xmlStr
//	 * @return
//	 * @throws DocumentException
//	 * @throws IOException 
//	 */
//	public static JSONObject xmlFile2Json(String xmlFilename) throws DocumentException, IOException {
//		String xmlStr = FileUtil.getFileContent(xmlFilename);
//		return xml2Json(xmlStr);
//	}
//
//	
//	/**
//	 * xmlתjson
//	 * 
//	 * @param xmlStr
//	 * @return
//	 * @throws DocumentException
//	 */
//	public static JSONObject xml2Json(String xmlStr) throws DocumentException {
//		Document doc = DocumentHelper.parseText(xmlStr);
//		JSONObject json = new JSONObject();
//		dom4j2Json(doc.getRootElement(), json);
//		return json;
//	}
//
//	/**
//	 * xmlתjson
//	 * 
//	 * @param element
//	 * @param json
//	 */
//	public static void dom4j2Json(Element element, JSONObject json) {
//		// ���������
//		for (Attribute attr : element.attributes()) {
//			if (!isEmpty(attr.getValue())) {
//				json.put("@" + attr.getName(), attr.getValue().trim());
//			}
//		}
//		List<Element> chdEl = element.elements();
//		if (chdEl.isEmpty() && !isEmpty(element.getTextTrim())) {// ���û����Ԫ��,ֻ��һ��ֵ
//			json.put(element.getName(), element.getTextTrim());
//		}
//
//		for (Element e : chdEl) {// ����Ԫ��
//			if (!e.elements().isEmpty()) {// ��Ԫ��Ҳ����Ԫ��
//				JSONObject chdjson = new JSONObject();
//				dom4j2Json(e, chdjson);
//				Object o = json.get(e.getName());
//				if (o != null) {
//					JSONArray jsona = null;
//					if (o instanceof JSONObject) {// �����Ԫ���Ѵ���,��תΪjsonArray
//						JSONObject jsono = (JSONObject) o;
//						json.remove(e.getName());
//						jsona = new JSONArray();
//						jsona.add(jsono);
//						jsona.add(chdjson);
//					}
//					if (o instanceof JSONArray) {
//						jsona = (JSONArray) o;
//						jsona.add(chdjson);
//					}
//					json.put(e.getName(), jsona);
//				} else {
//					if (!chdjson.isEmpty()) {
//						json.put(e.getName(), chdjson);
//					}
//				}
//
//			} else {// ��Ԫ��û����Ԫ��
//				for (Object o : element.attributes()) {
//					Attribute attr = (Attribute) o;
//					if (!isEmpty(attr.getValue())) {
//						json.put("@" + attr.getName(), attr.getValue().trim());
//					}
//				}
//				if (!e.getText().isEmpty()) {
//					json.put(e.getName(), e.getTextTrim());	//e.getText()
//				}
//			}
//		}
//	}
//
//	public static boolean isEmpty(String str) {
//		if (str == null || str.trim().isEmpty() || "null".equals(str)) {
//			return true;
//		}
//		return false;
//	}
//}