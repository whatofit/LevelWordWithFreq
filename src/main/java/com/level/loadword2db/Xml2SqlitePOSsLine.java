package com.level.loadword2db;

import java.util.List;

import com.level.model.Word;
import com.level.model.XmlSent;
import com.level.model.XmlWord;

public class Xml2SqlitePOSsLine extends XmlWordIntoSqlite {
    public Xml2SqlitePOSsLine() {
        super();
    }

    // 每一个单词,插入词性个数条记录,词频/单词/音标等,都是重复的
    public void line2WordVector(String line) {
        XmlWord word = wordParser.getXmlWord(line);
        List<XmlSent> sents = word.getSents();
        String sentences = "";
        for (int i = 0; i < sents.size(); i++) {
            XmlSent sent = sents.get(i);
            if (!sentences.isEmpty()) {
                // sentence = sentence + "||";
            }
            sentences = sentences + (i + 1) + ". " + sent.getOrig() + "/"
                    + sent.getTrans() + " ";
        }
        List<String> partsOfSpeech = word.getPartsOfSpeech();
        List<String> meaning = word.getMeaning();
        for (int i = 0; i < partsOfSpeech.size(); i++) {
            Word dbWord = new Word();
            dbWord.setId(vecWords.size());
            dbWord.setFrequency(word.getFrequency());
            dbWord.setSpelling(word.getKey());
            dbWord.setPhoneticDJ(word.getPs());
            dbWord.setPhoneticKK(word.getPs2());
            dbWord.setLevel(null);
            dbWord.setPartsOfSpeech(partsOfSpeech.get(i));
            dbWord.setMeanings(meaning.get(i));
            if (i == 0) {
                dbWord.setSentences(sentences);// 把所有例句放到第一个单词词性中
            } else {
                dbWord.setSentences(null);
            }
            vecWords.add(dbWord);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Xml2SqlitePOSsLine levelSqlite = new Xml2SqlitePOSsLine();
            levelSqlite.loadFile2WordVector();
            System.out.println("levelSqlite.vecWords.size()=" + levelSqlite.vecWords.size());
            levelSqlite.createOrUpdateWordDB(Word.FIELD_NAME_POS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
