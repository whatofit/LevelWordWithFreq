package com.level.model;

public class Sentence {
    private String es;// = ""; // 英文句子
    private String cs;// = ""; // 中文翻译

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
