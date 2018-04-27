package com.myblog.level;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.myblog.Constant;
import com.myblog.dao.WordDaoImpl;
import com.myblog.model.Word;
import com.myblog.util.ResourceUtil;

/**
 * implementation
 * 
 * @author Dave
 * 
 * @version 1.0 2017/06/10
 */
public abstract class XmlWordIntoSqlite {
    public static final String FILE_FREQ_OF_WORDS = "freqOfWords.txt";
    // protected final String mWordListFile = "./input/vocabulary.txt";
    // protected final String mXmlFileFolder = "./vocabulary_ciba";
    protected static String mErrFileList = "/ErrFile.txt";
    protected XmlWordVisitor wordParser = new XmlWordVisitor();
    protected Vector<Word> vecWords = new Vector<Word>();

    // 1.读xml单词文件
    // 2.解析成JavaBean/Model
    // 3.写入文件/数据库
    public XmlWordIntoSqlite() {
        ResourceUtil.deleteFile(mErrFileList);
    }

    // 根据filename中单词的顺序,读取vocabulary_ciba文件夹下的xml文件列表
    public void loadFile2WordVector() {
        try {
            List<String> lines = ResourceUtil.readFileLines(Constant.FILE_FREQ_OF_WORDS);
            for (String line : lines) {// 遍历set去出里面的的Key
                line2WordVector(line);
            }

            //FileInputStream fis = new FileInputStream(mWordListFile);
            //// DataInputStream dr = new DataInputStream(f);
            //BufferedReader dr = new BufferedReader(new InputStreamReader(fis));
            //String line = dr.readLine();
            //while (line != null) {
            //    line2WordVector(line);
            //    line = dr.readLine();
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 子类中重写本函数
    public abstract void line2WordVector(String line);

    /*
     * 
     */
    public int createOrUpdateWordDB(String fieldName) {
        ConnectionSource connectionSource;
        try {
            // String databaseUrl = "jdbc:h2:./output/LevelDict.h2";
            //String databaseUrl = "jdbc:sqlite:./output/LevelDict.db3";
            //connectionSource = new JdbcConnectionSource(databaseUrl);
            System.out.println("createOrUpdateWordDB,URL_DATABASE=" + Constant.URL_DATABASE);
            connectionSource = new JdbcConnectionSource(Constant.URL_DATABASE);
            WordDaoImpl wordDao = new WordDaoImpl(connectionSource);
            int affectRowCount = wordDao.createOrUpdate(vecWords,fieldName);
            System.out.println("affectRowCount=" + affectRowCount);
            return affectRowCount;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

} // End of Class XmlWordIntoSqlite
