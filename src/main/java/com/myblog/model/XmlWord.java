package com.myblog.model;

import java.util.ArrayList;
import java.util.List;

public class XmlWord {
    private String frequency;//词频
    private String key; // 原词
    private String ps; // 英式音标
    private String pron; // 英式发音地址
    private String ps2; // 美式音标
    private String pron2; // 美式发音地址
    // 英语词性分类
    // n = 名词，noun的缩写
    // v = 动词，兼指及物动词和不及物动词，verb的缩写
    // vi = 不及物动词，intransitive verb的缩写
    // vt = 及物动词，transitive verb的缩写
    // a = 形容词，adjective的缩写
    // ad = 副词，adverb的缩写
    // num = 数词，numeral的缩写
    // int = 感叹词，interjection的缩写
    // pron = 代名词，pronoun的缩写
    // prep = 介系词；前置词，preposition的缩写
    // art = 冠词，article的缩写
    // conj = 连接词 ，conjunction的缩写

    // aux.v = 助动词 ，auxiliary的缩写
    // u = 不可数名词，uncountable noun的缩写
    // c = 可数名词，countable noun的缩写
    // pl = 复数，plural的缩写
    // s = 主词
    // sc = 主词补语
    // o = 受词
    // oc = 受词补语

    // private Map<String, String> mapAcceptation= new HashMap<String,
    // String>();
    // private String curPos; //词性
    // private String acceptation; //意义
    private List<String> partsOfSpeech = new ArrayList<String>(); // 词性
    private List<String> meaning = new ArrayList<String>(); // 意义
    private List<XmlSent> sents = new ArrayList<XmlSent>(); // 例句

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getPron() {
        return pron;
    }

    public void setPron(String pron) {
        this.pron = pron;
    }

    public String getPs2() {
        return ps2;
    }

    public void setPs2(String ps2) {
        this.ps2 = ps2;
    }

    public String getPron2() {
        return pron2;
    }

    public void setPron2(String pron2) {
        this.pron2 = pron2;
    }

    // public Map<String, String> getMapAcceptation() {
    // return mapAcceptation;
    // }
    // public void setMapAcceptation(Map<String, String> mapAcceptation) {
    // this.mapAcceptation = mapAcceptation;
    // }
    //
    // public void setAcceptation(String curPos, String acceptation) {
    // this.mapAcceptation.put(curPos, acceptation);
    // }

    public List<String> getPartsOfSpeech() {
        return partsOfSpeech;
    }

    public void setPartsOfSpeech(List<String> partsOfSpeech) {
        this.partsOfSpeech = partsOfSpeech;
    }

    public List<String> getMeaning() {
        return meaning;
    }

    public void setMeaning(List<String> meaning) {
        this.meaning = meaning;
    }

    public List<XmlSent> getSents() {
        return sents;
    }

    public void setSents(List<XmlSent> sents) {
        this.sents = sents;
    }

    public void addSent(XmlSent sent) {
        this.sents.add(sent);
    }

    public void addPathsOfSpeech(String partsOfSpeech) {
        this.partsOfSpeech.add(partsOfSpeech == null ? null : partsOfSpeech
                .trim());
    }

    public void addMeaning(String meaning) {
        this.meaning.add(meaning == null ? null : meaning.trim());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("key = " + key);// 原词
        sb.append("ps =" + ps); // 英式发音
        sb.append("pron =" + pron);// 英式发音地址
        sb.append("ps2 =" + ps2);// 美式发音
        sb.append("pron2 =" + pron2);// 美式发音地址
        // //sb.append("mapAcceptation =" + mapAcceptation.toString());//词性&意义
        // for (Map.Entry<String, String> m : mapAcceptation.entrySet()) {
        // //System.out.println(m.getKey() + "<====>" + m.getValue());
        // sb.append(m.getKey() + "<====>" + m.getValue());
        // }
        // sb.append("sents =" + sents.toString());//例句
        int i = 1;
        for (XmlSent sent : sents) {
            sb.append(i + ".Orig =" + sent.getOrig());// 原词例句
            sb.append(i + ".Trans =" + sent.getTrans());// 例句翻译
            i++;
        }

        return sb.toString();
    }

    public List<String> toStringList(String wordFrequency) {
        List<String> record = new ArrayList<String>();
        record.add(wordFrequency);// 词频
        record.add(key);// Spelling //原词
        record.add(ps);// SymbolEnglish//英式音标
        record.add(pron);// PronunciationEnglish //英式发音地址
        record.add(ps2);// SymbolAmerica//美式音标
        record.add(pron2);// PronunciationAmerica//美式发音地址

        String PartsOfSpeechMeaning = "";
        // //名词
        // String noun = mapAcceptation.get("n.");
        // if (noun == null) {
        // noun = "";
        // }
        // record.add(noun);
        // if (!Utils.isEmpty(noun)) {
        // PartsOfSpeechMeaning += "n. "+ noun;
        // }
        //
        // //动词
        // String verb = "";
        // String v = mapAcceptation.get("v.");
        // if (v != null) {
        // verb += v;
        // verb += "\r\n";
        // }
        // String vt = mapAcceptation.get("vt.");
        // if (vt != null) {
        // verb += vt;
        // verb += "\r\n";
        // }
        // String vi = mapAcceptation.get("vi.");
        // if (vi != null) {
        // verb += vi;
        // //verb += "\r\n";
        // }
        // record.add(verb);
        // if (!Utils.isEmpty(verb)) {
        // PartsOfSpeechMeaning += "v. "+ verb;
        // }
        //
        // //形容词
        // String a = mapAcceptation.get("a.");
        // if (a == null) {
        // a = "";
        // }
        // record.add(a);
        // if (!Utils.isEmpty(a)) {
        // PartsOfSpeechMeaning += "a. "+ a;
        // }
        //
        // //副词
        // String ad = mapAcceptation.get("ad.");
        // if (ad == null) {
        // ad = "";
        // }
        // record.add(ad);
        // if (!Utils.isEmpty(ad)) {
        // PartsOfSpeechMeaning += "ad. "+ ad;
        // }
        //
        // //数词
        // String num = mapAcceptation.get("num.");
        // if (num == null) {
        // num = "";
        // }
        // record.add(num);
        // if (!Utils.isEmpty(num)) {
        // PartsOfSpeechMeaning += "num. "+ num;
        // }
        //
        // //感叹词
        // String interjection = mapAcceptation.get("int.");
        // if (interjection == null) {
        // interjection = "";
        // }
        // record.add(interjection);
        // if (!Utils.isEmpty(interjection)) {
        // PartsOfSpeechMeaning += "int. "+ interjection;
        // }
        //
        // //代名词
        // String pron = mapAcceptation.get("pron.");
        // if (pron == null) {
        // pron = "";
        // }
        // record.add(pron);
        // if (!Utils.isEmpty(pron)) {
        // PartsOfSpeechMeaning += "pron. "+ pron;
        // }
        //
        // //介系词
        // String prep = mapAcceptation.get("prep.");
        // if (prep == null) {
        // prep = "";
        // }
        // record.add(prep);
        // if (!Utils.isEmpty(prep)) {
        // PartsOfSpeechMeaning += "prep. "+ prep;
        // }
        //
        // //冠词
        // String art = mapAcceptation.get("art.");
        // if (art == null) {
        // art = "";
        // }
        // record.add(art);
        // if (!Utils.isEmpty(art)) {
        // PartsOfSpeechMeaning += "art. "+ art;
        // }
        //
        // //连接词
        // String conj = mapAcceptation.get("conj.");
        // if (conj == null) {
        // conj = "";
        // }
        // record.add(conj);
        // if (!Utils.isEmpty(conj)) {
        // PartsOfSpeechMeaning += "conj. "+ conj;
        // }

        // 总词性和词义
        record.add(PartsOfSpeechMeaning);

        // n = 名词，noun的缩写
        // v = 动词，兼指及物动词和不及物动词，verb的缩写
        // vi = 不及物动词，intransitive verb的缩写
        // vt = 及物动词，transitive verb的缩写
        // a = 形容词，adjective的缩写
        // ad = 副词，adverb的缩写
        // num = 数词，numeral的缩写
        // int = 感叹词，interjection的缩写
        // pron = 代名词，pronoun的缩写
        // prep = 介系词；前置词，preposition的缩写
        // art = 冠词，article的缩写
        // conj = 连接词 ，conjunction的缩写

        // 例句
        int i = 1;
        StringBuffer sb = new StringBuffer();
        for (XmlSent sent : sents) {
            sb.append(i + ". " + sent.getOrig().trim());// 原词例句
            sb.append("\r\n");
            sb.append("   " + sent.getTrans().trim());// 例句翻译
            sb.append("\r\n");
            i++;
        }
        record.add(sb.toString());

        return record;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }

}
