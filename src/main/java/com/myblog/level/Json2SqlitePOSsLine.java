package com.myblog.level;

import com.myblog.model.JsonWord;

public class Json2SqlitePOSsLine extends XmlWordIntoSqlite {
    public Json2SqlitePOSsLine() {
        super();
    }

    // 每一个单词,插入词性个数条记录,词频/单词/音标等,都是重复的
    public void word2Vector(String line) {
        JsonWord word = wordParser.getJsonWord(line);
        word.add2Vector(vecWords, word);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Json2SqlitePOSsLine levelSqlite = new Json2SqlitePOSsLine();
            levelSqlite.xmlFiles2Words();
            // levelSqlite.word2Vector("00007	that");
            levelSqlite.doInsert2DB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
