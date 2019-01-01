package com.level.loadword2db;

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
import com.level.Constant;
import com.level.model.JsonWord;
import com.level.model.XmlSent;
import com.level.model.XmlWord;
import com.level.util.RegEx;
import com.level.util.ResourceUtil;
import com.level.util.Utils;

public class XmlWordParser extends VisitorSupport {
    //protected final String mFileFolderXml = "vocabulary_ciba";
    //protected final String mFileFolderJson = "vocabulary_QQ";
    protected static String mErrFileList = "/ErrFile.txt";
    private String mXmlWordFile = "";
    // private Document doc;
    private XmlWord mWord;
    // private List<Sent> sents = new ArrayList<Sent>();
    private XmlSent sent = null;
    // private String curPos; // 当前词性
    // 用来存放每次遍历后的元素名称(节点名称)
    private String tagName;

    /** 构造函数 */
    public XmlWordParser() {
        // System.out.println("--------------WordsVisitor()-------");
    }
    
    public XmlWord getXmlWord(String line) {
        String[] arr = line.trim().split("\t");
        // line = line.trim().replaceFirst("\t", "-");
        String xmlFileName = String.format("%05d-%s.xml", Integer.valueOf(arr[0]),arr[1]);
        String xmlWordFile = Constant.PATH_CIBA + File.separator + xmlFileName;
        System.out.println(xmlWordFile);
        try {
            getDocument(xmlWordFile).accept(this);
            mWord.setFrequency(arr[0]);
            return mWord;
        } catch (Exception ex) {
            ResourceUtil.writerFile(mErrFileList, xmlWordFile,true);
            XmlWord word = new XmlWord();
            word.setFrequency(arr[0]);
            word.setKey(arr[1]);
            return word;
        }
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
            String body = ResourceUtil.readFile(mXmlWordFile);
            String retXml = Utils.replaceAngleBrackets(body);
            // System.out.println("--ret---" + retXml);
            // document = saxReader.read(new File(mXmlWordFile));
            // Document doc = builder.parse();
            document = saxReader.read(new InputSource(new ByteArrayInputStream(retXml.getBytes("utf-8"))));
        } catch (DocumentException e) {
            mWord = new XmlWord();
            String[] name2 = Utils.splitFileName(mXmlWordFile);
            mWord.setFrequency(name2[0]);
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
            String data = node.getText().trim();
            if (tagName.equals("key")) {
                mWord.setKey(data);
            } else if (tagName.equals("ps")) {
                if (mWord.getPs() == null) {
                    //mWord.setPs("[" + data + "]");
                    mWord.setPs(data);
                } else {
                    //mWord.setPs2("[" + data + "]");
                    mWord.setPs2(data);
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
                mWord.addMeaning(RegEx.replacemptyCharsWith1Blank(data));
            } else if (tagName.equals("orig")) {
                sent.setOrig(RegEx.replacemptyCharsWith1Blank(data));
                // } else if (tagName.equals("pron")) {
                // sent.setPronUrl(data);
            } else if (tagName.equals("trans")) {
                sent.setTrans(RegEx.replacemptyCharsWith1Blank(data));
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

    /**
     * @param args
     */
    public static void main(String[] args) {

    }
}
