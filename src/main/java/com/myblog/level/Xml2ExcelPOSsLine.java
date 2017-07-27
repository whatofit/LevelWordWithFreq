package com.myblog.level;

import com.myblog.model.XmlWord;
import com.myblog.util.Utils;
import com.myblog.util.XlsUtil;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Xml2ExcelPOSsLine extends XmlWordIntoSqlite {
	protected static String mXlsFile = "./output/LevelDict.xls";
    private static WritableWorkbook book = null;
    private static WritableSheet sheet = null;
    private static int mRow = 0;// 单词的词频/已生成的excel行数

    // 1.读xml单词文件
    // 2.解析成JavaBean/Model
    // 3.写入文件/数据库
    public Xml2ExcelPOSsLine() {
        super();
    }

    // 所有词性/词义在一行
    public void word2Vector(String line) {
        XmlWord word = wordParser.getXmlWord(line);
        XlsUtil.addXLS(sheet, word, mRow);
        if (word.getPartsOfSpeech().size() == 0) {
            mRow += 1; // 累加写入excel下一个单词的起始位置
        } else {
            mRow += word.getPartsOfSpeech().size();
        }
        // sheet = XlsUtil.addXLS(sheet, word.toStringList(arr[0]),
        // Integer.parseInt(arr[0])-1);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Utils.deleteFile(mXlsFile);
            // book = XlsUtil.createXLS("LevelDict.xls", "vocabulary", 0);
            // sheet = book.getSheet(0);
            book = XlsUtil.createXLS(mXlsFile);
            sheet = XlsUtil.createSheet(book, "vocabulary", 0);
            // // 打开文件
            // book = Workbook.createWorkbook(new File("LevelDict.xls"));
            // // 生成名为“第一页”的工作表，参数0表示这是第一页
            // sheet = book.createSheet("vocabulary", 0);

            Xml2ExcelPOSsLine levelSqlite = new Xml2ExcelPOSsLine();
            levelSqlite.xmlFiles2Words();
            XlsUtil.closeXLS(book);
            // String sqlCreate =
            // "CREATE TABLE IF NOT EXISTS LevelWordTab (frequency,spelling,DJ,KK,level,posMeanings,sents);";
            // String sqlInsert =
            // "INSERT INTO LevelWordTab VALUES(?,?,?,?,?,?,?)";
            // levelSqlite.doInsert2DB(sqlCreate, sqlInsert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}