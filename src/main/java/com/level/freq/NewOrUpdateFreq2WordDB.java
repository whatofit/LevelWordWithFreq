/**
 * 
 */
package com.level.freq;

import com.level.loadword2db.XmlWordIntoSqlite;
import com.level.model.Word;

/**
 * @author Administrator
 *
 */
public class NewOrUpdateFreq2WordDB extends XmlWordIntoSqlite {

    /**
     * 
     */
    public NewOrUpdateFreq2WordDB() {
    }

    @Override
    public void line2WordVector(String line) {
        //System.out.println("NewOrUpdateFreq2WordDB,line2WordVector,line:" + line);
        String[] arr = line.trim().split("\t");
        // line = line.trim().replaceFirst("\t", "-");
        // String xmlFileName = arr[0] + "-" + arr[1] + ".xml";
        Word dbWord = new Word();
        dbWord.setId(vecWords.size());
        dbWord.setFrequency(arr[0]);
        dbWord.setSpelling(arr[1]);
        vecWords.add(dbWord);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            NewOrUpdateFreq2WordDB freqSqlite = new NewOrUpdateFreq2WordDB();
            // 1.读取Freq/Level of word文件到vector
            freqSqlite.loadFile2WordVector();
            // 2.把vector中的单词更新到数据库
            freqSqlite.createOrUpdateWordDB(Word.FIELD_NAME_FREQUENCY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
