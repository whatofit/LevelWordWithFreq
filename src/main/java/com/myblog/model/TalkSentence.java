package com.myblog.model;

public class TalkSentence {
    private String person;// 说话者：person A;person B or person C
    private String es;// = ""; // 英文句子
    private String cs;// = ""; // 中文翻译

    public TalkSentence() {
        this.person = "";
        this.es = "";
        this.cs = "";
    }
    
    public TalkSentence(String person) {
        this.person = person;
        this.es = "";
        this.cs = "";
    }

    public TalkSentence(String person, String es, String cs) {
        this.person = person;
        this.es = es;
        this.cs = cs;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

   
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
        sb.append("person = " + person);
        sb.append(",es =" + es);
        sb.append(",cs =" + cs);
        return sb.toString();
    }
}