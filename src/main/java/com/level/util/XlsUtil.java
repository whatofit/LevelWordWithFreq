package com.level.util;

import java.io.File;
import java.util.List;

import com.level.model.XmlSent;
import com.level.model.XmlWord;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class XlsUtil {

    /** 创建Excel表 */
    public static WritableWorkbook createXLS(String filename) {
        try {
            // 打开文件
            // "test1.xls "
            WritableWorkbook book = Workbook.createWorkbook(new File(filename));
            return book;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /** 创建Excel表 */
    public static WritableWorkbook createXLS(String filename, String tag,
            int index) {
        try {
            // 打开文件
            // "test1.xls "
            WritableWorkbook book = Workbook.createWorkbook(new File(filename));
            // WritableSheet sheet = createSheet(book, tag, index);
            createSheet(book, tag, index);
            return book;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /** 创建Excel表 */
    public static WritableSheet createSheet(WritableWorkbook book, String tag,
            int index) {
        try {
            // 生成名为“第一页”的工作表，参数0表示这是第一页
            // int nSheetsCnt = book.getNumberOfSheets();
            // if (index > nSheetsCnt) {
            // index = nSheetsCnt;
            // }
            WritableSheet sheet = book.createSheet(tag, index);
            return sheet;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /** 创建Excel表 */
    public static boolean addXLS(WritableSheet sheet, List<String> record,
            int row) {
        try {
            int column = 0;
            for (String cell : record) {
                // 单元格内容为cell
                Label label = new Label(column, row, cell);
                // 将定义好的单元格添加到工作表中
                sheet.addCell(label);
                column++;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    /** 创建Excel表 */
    public static boolean addXLS(WritableSheet sheet, XmlWord word, int row) {
        try {
            List<String> partsOfSpeech = word.getPartsOfSpeech();// 词性
            // System.out.println("partsOfSpeech.size()=" +
            // partsOfSpeech.size());
            List<String> meanings = word.getMeaning(); // 意义
            List<XmlSent> sents = word.getSents(); // 例句
            int height = partsOfSpeech.size() - 1;
            if (height < 0) {
                height = 0;
            }

            int column = 0; // wordFrequency;//词频 0
            XlsUtil.mergeCells(sheet, column, row, column + 0, row + height,
                    word.getFrequency()+"");

            column++; // Spelling //原词 1
            XlsUtil.mergeCells(sheet, column, row, column + 0, row + height,
                    word.getKey());

            column++; // SymbolEnglish //英式音标
            XlsUtil.mergeCells(sheet, column, row, column + 0, row + height,
                    word.getPs());

            // if (partsOfSpeech.size() == 0) {
            // return true;
            // }

            // column++; // 词性
            // XlsUtil.mergeCells(sheet, column+, row, 1, partsOfSpeech.size(),
            // word.getPs());

            column++; // 词性&词义
            int r2 = row;
            for (int i = 0; i < partsOfSpeech.size(); i++) {
                String partOfSpeech = partsOfSpeech.get(i);
                String meaning = meanings.get(i);
                // 单元格内容为cell
                Label label1 = new Label(column, r2, partOfSpeech); // column
                Label label2 = new Label(column + 1, r2, meaning); // column + 1
                // 将定义好的单元格添加到工作表中
                sheet.addCell(label1);
                sheet.addCell(label2);
                r2++;
            }

            column += 2; // 例句
            int i = 1;
            StringBuffer sb = new StringBuffer();
            for (XmlSent sent : sents) {
                sb.append(i + ". " + sent.getOrig().trim()); // 原词例句
                sb.append("\r\n");
                sb.append("   " + sent.getTrans().trim()); // 例句翻译
                sb.append("\r\n");
                i++;
            }
            XlsUtil.mergeCells(sheet, column, row, column + 0, row + height,
                    sb.toString());
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    /**
     * merge Cells Excel表 int column 左上角的列位置 int row, 左上角的行位置 int
     * column2,右下角的列位置 int row2,右下角的行位置
     */
    public static boolean mergeCells(WritableSheet sheet, int column, int row,
            int column2, int row2, String text) {
        try {
            sheet.mergeCells(column, row, column2, row2); // 合并单元格
            Label label = new Label(column, row, text); // 添加内容
            sheet.addCell(label);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    /** colse Excel表 */
    public static boolean closeXLS(WritableWorkbook book) {
        try {
            // 写入数据并关闭文件
            book.write();
            book.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
