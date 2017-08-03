package com.myblog.level;

import com.myblog.model.JsonWord;
import com.myblog.model.Word;

public class Json2SqlitePOSsLine extends XmlWordIntoSqlite {
    public Json2SqlitePOSsLine() {
        super();
    }

    // 每一个单词,插入词性个数条记录,词频/单词/音标等,都是重复的
    public void line2WordVector(String line) {
        JsonWord word = wordParser.getJsonWord(line);
        word.add2Vector(vecWords, word);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Json2SqlitePOSsLine levelSqlite = new Json2SqlitePOSsLine();
            levelSqlite.loadFile2WordVector();
            //levelSqlite.line2WordVector("00007   that");
            levelSqlite.createOrUpdateWordDB(Word.FIELD_NAME_SPELLING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
