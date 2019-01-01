package com.level.loadword2db;

import java.util.List;

import com.level.Constant;
import com.level.model.Word;
import com.level.model.XmlSent;
import com.level.model.XmlWord;

public class Xml2SqliteMeaningsLine extends FmtWordVectorIntoSqlite {
    protected final String regexSemicolon = "[；;]";// 以中文分号或英文分号分割

    public Xml2SqliteMeaningsLine() {
        super();
    }

    // 每一个单词,以中文分号；分割词义,插入中文词义个数条记录,词频/单词/音标等,都是重复的
    public void line2WordVector(String line) {
        XmlWord xmlWord = wordParser.getXmlWord(line);
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
        List<String> partsOfSpeech = xmlWord.getPartsOfSpeech();
        List<String> meaning = xmlWord.getMeaning();
        for (int i = 0; i < meaning.size(); i++) {
            // String meanings =
            // "采用（某种方式）；穿着，带着；（表示位置）在…里面，（表示领域，范围）在…以内；（表示品质、能力等）在…之中；";
            String meanings = meaning.get(i);
            String[] acceptation = meanings.split(regexSemicolon);
            for (int j = 0; j < acceptation.length; j++) {
                // System.out.println("meanings,regex="+
                // Arrays.toString(acceptation));
                // System.out.println("meanings,regex="+ acceptation[i]);
                Word dbWord = new Word();
                dbWord.setId(vecWords.size());
                dbWord.setFrequency(xmlWord.getFrequency());
                dbWord.setSpelling(xmlWord.getKey());
                dbWord.setPhoneticDJ(xmlWord.getPs());
                dbWord.setPhoneticKK(xmlWord.getPs2());
                dbWord.setLevel(null);
                dbWord.setPartsOfSpeech(partsOfSpeech.get(i));
                dbWord.setMeanings(acceptation[j]);
                if (i == 0 && j == 0) {
                    dbWord.setSentences(sentences);// 例句只放在第一条记录里
                } else {
                    dbWord.setSentences(null);
                }
                vecWords.add(dbWord);
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Xml2SqliteMeaningsLine levelSqlite = new Xml2SqliteMeaningsLine();
            levelSqlite.loadFile2WordVector(Constant.FILE_FREQ_OF_WORDS);
            levelSqlite.createOrUpdateWordDB(Word.FIELD_NAME_SPELLING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
