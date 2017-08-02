package com.myblog.level;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myblog.model.JsonWord;
import com.myblog.model.XmlSent;
import com.myblog.model.XmlWord;
import com.myblog.util.FileUtil;
import com.myblog.util.Utils;

public class XmlWordVisitor extends VisitorSupport {
    protected final String mFileFolderXml = "vocabulary_ciba";
    protected final String mFileFolderJson = "vocabulary_QQ";
    protected static String mErrFileList = "ErrFile.txt";
    private String mXmlWordFile = "";
    // private Document doc;
    private XmlWord mWord;
    // private List<Sent> sents = new ArrayList<Sent>();
    private XmlSent sent = null;
    // private String curPos; // 当前词性
    // 用来存放每次遍历后的元素名称(节点名称)
    private String tagName;

    /** 构造函数 */
    public XmlWordVisitor() {
        // System.out.println("--------------WordsVisitor()-------");
    }

    public Document getDocument(String xmlWordFile) {
        // System.out.println("--------------getDocument(xmlWordFile)-------");
        this.mXmlWordFile = xmlWordFile;

        SAXReader saxReader = new SAXReader();
        // this.doc = saxReader.read(file);
        Document document = null;
        try {
            // 实体转换:
            // & ==> &amp;
            // < ==> &lt;
            // > ==> &gt;
            // " ==> &quot;
            // ' ==> apos;

            // aa = aa.replaceAll("mapWidth='\\d*'","mapWidth='1000'");
            String body = FileUtil.readFile(mXmlWordFile);
            String retXml = Utils.replaceAngleBrackets(body);
            // System.out.println("--ret---" + retXml);
            // document = saxReader.read(new File(mXmlWordFile));
            // Document doc = builder.parse();
            document = saxReader.read(new InputSource(new ByteArrayInputStream(
                    retXml.getBytes("utf-8"))));
        } catch (DocumentException e) {
            mWord = new XmlWord();
            String[] name2 = Utils.splitFileName(mXmlWordFile);
            mWord.setFrequency(Integer.parseInt(name2[0]));
            mWord.setKey(name2[1]);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 对于元素节点，判断是否只包含文本内容，如是，则打印标记的名字和 元素的内容。如果不是，则只打印标记的名字
     */
    public void visit(Element node) {
        if (node.isTextOnly()) {
            // System.out.println("element : " + node.getName() + " = "
            // + node.getText());
            tagName = node.getName();
            String data = node.getText();
            if (tagName.equals("key")) {
                mWord.setKey(data);
            } else if (tagName.equals("ps")) {
                if (mWord.getPs() == null) {
                    mWord.setPs("[" + data + "]");
                } else {
                    mWord.setPs2("[" + data + "]");
                }
            } else if (tagName.equals("pron")) {
                if (mWord.getPron() == null) {
                    mWord.setPron(data);
                } else {
                    mWord.setPron2(data);
                }
                // } else if (tagName.equals("pos")) {
                // curPos = data;
                // } else if (tagName.equals("acceptation")) {
                // mWord.setAcceptation(curPos, data);
            } else if (tagName.equals("pos")) {
                mWord.addPathsOfSpeech(data);
            } else if (tagName.equals("acceptation")) {
                mWord.addMeaning(data);
            } else if (tagName.equals("orig")) {
                sent.setOrig(data);
                // } else if (tagName.equals("pron")) {
                // sent.setPronUrl(data);
            } else if (tagName.equals("trans")) {
                sent.setTrans(data);
                // sents.add(sent);
                mWord.addSent(sent);
                sent = null;
            }
        } else {
            // System.out.println("--------" + node.getName() + "--------");
            String qName = node.getName();
            if (qName.equals("sent")) {
                // System.out.println("-----qName.equals(sent)---");
                sent = new XmlSent();
            } else if (qName.equals("dict")) {
                // System.out.println("-----qName.equals(dict)---");
                mWord = new XmlWord();
                // System.out.println("--------------visit()--new Word()");
            }
            tagName = qName;
        }
    }

    // public Word getWord() {
    // return mWord;
    // }

    public XmlWord getXmlWord(String line) {
        String[] arr = line.trim().split("\t");
        // line = line.trim().replaceFirst("\t", "-");
        String xmlFileName = arr[0] + "-" + arr[1] + ".xml";
        String xmlWordFile = mFileFolderXml + File.separator + xmlFileName;
        System.out.println(xmlWordFile);
        try {
            getDocument(xmlWordFile).accept(this);
            mWord.setFrequency(Integer.parseInt(arr[0]));
            return mWord;
        } catch (Exception ex) {
            Utils.writerFileTest(mErrFileList, xmlWordFile);
            XmlWord word = new XmlWord();
            word.setFrequency(Integer.parseInt(arr[0]));
            word.setKey(arr[1]);
            return word;
        }
    }

    public JsonWord getJsonWord(String line) {
        String[] arr = line.trim().split("\t");
        // line = line.trim().replaceFirst("\t", "-");
        String jsonFileName = arr[0] + "-" + arr[1] + ".json";
        String jsonWordFile = mFileFolderJson + File.separator + jsonFileName;
        System.out.println(jsonWordFile);
        String body = FileUtil.readFile(jsonWordFile);
        // System.out.println("body:"+body);
        JSONObject jsonWord = JSONObject.parseObject(body);
        // mWord = FastJsonUtil.json2obj(body, Word.class);
        // mWord.setWordFrequency(arr[0]);
        // mWord.setKey(arr[1]);
        // System.out.println("getJsonWord,Key=" + mWord.getKey() + ",name＝"
        // + mWord.getWordFrequency());
        JsonWord word = new JsonWord();
        word.setFrequency(Integer.parseInt(arr[0]));
        word.setWord(arr[1]);
        if (jsonWord == null) {
            return word;
        }

        Object objLocal = jsonWord.get("local");
        if (objLocal == null) {
            return word;
        }
        if (objLocal instanceof JSONArray) {
        }
        // System.out.println("getJsonWord,local=" + objLocal);
        // JSONObject.toJavaObject(objLocal, JsonWord.class);
        List<JsonWord> words = JSON.parseArray(objLocal.toString(),
                JsonWord.class);
        word = words.get(0);
        word.setFrequency(Integer.parseInt(arr[0]));
        // word.setWord(arr[1]);
        return word;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }
}
