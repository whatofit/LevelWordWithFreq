package com.myblog.demo;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.myblog.Constant;
import com.myblog.util.CfgUtil;


public class ConvertPdf2Word {

    public static void main(String[] args) throws IOException {
        System.out.println("Document converted started");
        XWPFDocument doc = new XWPFDocument();
        String cfg_pdf = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_english_pdf");
		String pdfFilename = Constant.PATH_RESOURCES + cfg_pdf;
        //String pdf = "/home/yc/Documents/p1.pdf";
        PdfReader reader = new PdfReader(pdfFilename);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            TextExtractionStrategy strategy = parser.processContent(i,
                    new SimpleTextExtractionStrategy());
            String text = strategy.getResultantText();
            XWPFParagraph p = doc.createParagraph();
            XWPFRun run = p.createRun();
            run.setText(text);
            run.addBreak(BreakType.PAGE);
        }
        FileOutputStream out = new FileOutputStream(Constant.PATH_RESOURCES +"/CET4,CET6《全国大学英语四、六级考试大纲（2016年修订版）》.docx");
        doc.write(out);
        out.close();
        reader.close();
        System.out.println("Document converted successfully");
    }
}
