/**
 * 
 */
package com.myblog.freq;

import com.myblog.level.Xml2Sqlite1Line;
import com.myblog.level.XmlWordIntoSqlite;

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
        
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            NewOrUpdateFreq2WordDB freqSqlite = new NewOrUpdateFreq2WordDB();
            //1.读取Freq/Level of word文件到vector
            freqSqlite.loadFile2WordVector();
            //2.把vector中的单词更新到数据库
            freqSqlite.createOrUpdateWordDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
