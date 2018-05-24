package com.myblog.rtf2txt;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;
import com.myblog.util.ResourceUtil;

public class ParsePdf2Doc {

    public ParsePdf2Doc() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        try {
            // 1.读取pdf文件路径
            // "/美国家庭万用亲子英文英语8000句.pdf"
            String cfg_pdf = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_english_pdf");
            String pdfFilename = Constant.PATH_RESOURCES + cfg_pdf;
            RtfUtil.readPdf2Doc(pdfFilename);
            // 2.写入对应的text文件
            //String outTextFilename = pdfFilename.replaceAll(".pdf$", ".txt"); //匹配到行尾
            // 生成文本文件
            //ResourceUtil.writerFile(outTextFilename, text, false);
            //System.out.println("done!outTextFilename="+outTextFilename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
