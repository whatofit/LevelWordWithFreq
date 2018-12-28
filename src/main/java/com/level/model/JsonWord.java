package com.level.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringEscapeUtils;

class JsonDes {
    private String p;// = ""; // 词性
    private String d;// = ""; // 词义
    private String s;// = ""; // 本词性对应的所有中/英文例句

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("p = " + p);
        sb.append("d =" + d);
        return sb.toString();
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}

class JsonS {
    private String es;// = ""; // 英文例句
    private String cs;// = ""; // 例句中文翻译

    public String getEs() {
        return es;
    }

    public void setEs(String es) {
        this.es = es;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("es = " + es);
        sb.append("cs =" + cs);
        return sb.toString();
    }
}

class JsonSen {
    private String p;// = ""; // 词性
    private List<JsonS> s;// = new ArrayList<JsonS>(); // 例句

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public List<JsonS> getS() {
        return s;
    }

    public void setS(List<JsonS> s) {
        this.s = s;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("p = " + p);
        sb.append("s =" + s.toString());
        return sb.toString();
    }
}

class JsonMor {
    private String c;// = ""; // 第三人称单数/动词过去式/过去分词/现在分词
    private String m;// = ""; // 单词

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("c = " + c);
        sb.append("m =" + m);
        return sb.toString();
    }
}

public class JsonWord {
    private String frequency;// = ""; // 词频
    private String word;// = ""; // 单词
    private List<String> pho;// = new ArrayList<String>(); // 音标
    private List<JsonDes> des = new ArrayList<JsonDes>(); // 词性/词义
    private List<JsonSen> sen = new ArrayList<JsonSen>(); // 词性/英/中文例句
    private List<JsonMor> mor;// = new ArrayList<JsonMor>(); // 单词变形

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getPho() {
        return pho;
    }

    public void setPho(List<String> pho) {
        this.pho = pho;
    }

    public List<JsonDes> getDes() {
        return des;
    }

    public void setDes(List<JsonDes> des) {
        this.des = des;
    }

    public List<JsonSen> getSen() {
        return sen;
    }

    public void setSen(List<JsonSen> sen) {
        this.sen = sen;
    }

    public List<JsonMor> getMor() {
        return mor;
    }

    public void setMor(List<JsonMor> mor) {
        this.mor = mor;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("word = " + word);// 原词
        return sb.toString();
    }

    //
    public void add2Vector(Vector<Word> vecWords, JsonWord jsonWord) {
        List<JsonDes> desList = jsonWord.getDes();
        List<JsonSen> senList = jsonWord.getSen();
        for (int m = 0; m < senList.size(); m++) {// 把本单词的例句拼接后,与词性/词义关联
            JsonSen sen = senList.get(m);
            List<JsonS> sList = sen.getS();
            String sentences = "";
            for (int u = 0; u < sList.size(); u++) {// 拼接本词性下的所有例句
                JsonS sent = sList.get(u);
                if (!sentences.isEmpty()) {
                    // sentence = sentence + "||";
                }
                sentences = sentences + (u + 1) + ". " + sent.getEs() + "/"
                        + sent.getCs() + " ";
            }
            for (int n = 0; n < desList.size(); n++) {// 把拼接后的例句与词性关联
                JsonDes des = desList.get(n);
                // System.out.println("add2Vector des=" + des);
                if (des.getP() != null && sen.getP() != null
                        && des.getP().equals(sen.getP())) {
                    des.setS(sentences);
                    break;
                }
            }
        }
        if (desList.size() == 0) {// 0个词性
            Word dbWord = new Word();
            dbWord.setId(vecWords.size());
            dbWord.setFrequency(jsonWord.getFrequency());// 词频
            dbWord.setSpelling(jsonWord.getWord());// 单词
            vecWords.add(dbWord);
        } else {
            List<String> phoList = jsonWord.getPho();
            for (int i = 0; i < desList.size(); i++) {
                JsonDes des = desList.get(i);
                Word dbWord = new Word();
                dbWord.setId(vecWords.size());
                dbWord.setFrequency(jsonWord.getFrequency());
                dbWord.setSpelling(StringEscapeUtils.unescapeHtml3(jsonWord
                        .getWord()));// 单词
                dbWord.setPhoneticDJ(StringEscapeUtils.unescapeHtml3(phoList
                        .get(0)));// 英语音标
                dbWord.setPhoneticKK(null); // 美语音标
                dbWord.setLevel(null);
                dbWord.setPartsOfSpeech(StringEscapeUtils.unescapeHtml3(des
                        .getP()));// 词性
                dbWord.setMeanings(StringEscapeUtils.unescapeHtml3(des.getD()));// 词义列表
                dbWord.setSentences(StringEscapeUtils.unescapeHtml3(des.getS()));// 本词性对应的所有中英文例句
                vecWords.add(dbWord);
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }
}
