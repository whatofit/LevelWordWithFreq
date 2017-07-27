package com.myblog.model;

import java.util.List;

//talk
//conversation
//dialogue
//(多人)情景对话/情景会话:：
//比如A,B,C,D 四人对话
//D,sents
//C,sents
//A,sents
//C,sents
//B,sents
public class Dialogue {
    private List<DialogSentence> dialogSents;// 例句

    public List<DialogSentence> getDialogSents() {
        return dialogSents;
    }

    public void setDialogSents(List<DialogSentence> dialogSents) {
        this.dialogSents = dialogSents;
    }
}

class DialogSentence {
    private String person;// 说话者：person A;person B or person C
    private String sentence;// 说话内容，可多行（以换行符分割）

    public DialogSentence(String person, String sentence) {
        this.person = person;
        this.sentence = sentence;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("person = " + person);
        sb.append("sentence =" + sentence.toString());
        return sb.toString();
    }
}