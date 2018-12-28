package com.level.loadword2db;

import java.util.List;

import com.level.model.Word;
import com.level.model.XmlSent;
import com.level.model.XmlWord;

public class Xml2Sqlite1Line extends XmlWordIntoSqlite {

    public Xml2Sqlite1Line() {
        super();
    }

    // 所有词性/词义在一行
    public void line2WordVector(String line) {
        XmlWord xmlWord = wordParser.getXmlWord(line);

        Word dbWord = new Word();
        dbWord.setId(vecWords.size());
        dbWord.setFrequency(xmlWord.getFrequency());
        dbWord.setSpelling(xmlWord.getKey());
        dbWord.setPhoneticDJ(xmlWord.getPs());
        dbWord.setPhoneticKK(xmlWord.getPs2());
        dbWord.setLevel(null);
        List<String> partsOfSpeech = xmlWord.getPartsOfSpeech();
        List<String> meaning = xmlWord.getMeaning();
        String posMeanings = "";
        for (int i = 0; i < partsOfSpeech.size(); i++) {
            String pos = partsOfSpeech.get(i);
            String mean = meaning.get(i);
            if (!posMeanings.isEmpty()) {
                // posMeanings = posMeanings + "||";
            }
            posMeanings = posMeanings + pos + mean;
        }
        dbWord.setPartsOfSpeech(posMeanings);
        dbWord.setMeanings(null);

        List<XmlSent> sents = xmlWord.getSents();
        String sentences = "";
        for (int i = 0; i < sents.size(); i++) {
            XmlSent sent = sents.get(i);
            if (!sentences.isEmpty()) {
                // sentence = sentence + "||";
            }
            sentences = sentences + (i + 1) + ". " + sent.getOrig() + "/"
                    + sent.getTrans() + " ";
        }
        dbWord.setSentences(sentences);
        vecWords.add(dbWord);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Xml2Sqlite1Line levelSqlite = new Xml2Sqlite1Line();
            levelSqlite.loadFile2WordVector();
            levelSqlite.createOrUpdateWordDB(Word.FIELD_NAME_SPELLING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
