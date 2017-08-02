package com.myblog.level;

import com.myblog.model.Word;
import com.myblog.model.XmlWord;
import com.myblog.util.FastJsonUtil;

public class Xml2SqliteJson1Line extends XmlWordIntoSqlite {

    public Xml2SqliteJson1Line() {
        super();
    }

    // 所有词性/词义在一行的json中
    public void line2WordVector(String line) {
        XmlWord xmlWord = wordParser.getXmlWord(line);
        String wordJson = FastJsonUtil.obj2json(xmlWord);
        Word dbWord = new Word();
        dbWord.setId(vecWords.size());
        dbWord.setFrequency(xmlWord.getFrequency());
        dbWord.setSpelling(xmlWord.getKey());// 单词
        dbWord.setPhoneticDJ(null);// 英语音标
        dbWord.setPhoneticKK(null); // 美语音标
        dbWord.setLevel(null);
        dbWord.setPartsOfSpeech(null);// 词性
        dbWord.setMeanings(null);// 词义列表
        dbWord.setSentences(wordJson);// 本词性对应的所有字段
        vecWords.add(dbWord);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Xml2SqliteJson1Line levelSqlite = new Xml2SqliteJson1Line();
            levelSqlite.loadFile2WordVector();
            levelSqlite.createOrUpdateWordDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
