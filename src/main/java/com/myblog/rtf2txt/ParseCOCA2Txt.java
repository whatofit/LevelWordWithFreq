package com.myblog.rtf2txt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;
import com.myblog.util.ResourceUtil;

public class ParseCOCA2Txt {
    public ParseCOCA2Txt() {
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        ParseCOCA2Txt pdfReader = new ParseCOCA2Txt();
        try {
            // 1.读取pdf文件路径
            // "/美国当代英语语料库COCA词频20000.pdf"
            String cfg_pdf = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_english_pdf");
            String pdfFilename = Constant.PATH_RESOURCES + cfg_pdf;
            String text = RtfUtil.readPdf2Txt(pdfFilename);
            // 2.写入对应的text文件
            String outTextFilename = pdfFilename.replaceAll(".pdf$", ".txt"); //匹配到行尾
            // 生成文本文件
            ResourceUtil.writerFile(outTextFilename, text, false);
            System.out.println("done!outTextFilename="+outTextFilename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}