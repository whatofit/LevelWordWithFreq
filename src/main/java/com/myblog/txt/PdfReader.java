package com.myblog.txt;

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

public class PdfReader {
    public PdfReader() {
    }

    //PDF文本内容提取
    public String readPdf2Txt(String filename) {
        // pdf文件名
        File pdfFile = new File(filename);
//        // 输入文本文件名称
//        String outTextFilename = null;
//        // 编码方式
//        String encoding = "UTF-8";
        // 开始提取页数
        int startPage = 1;
        // 结束提取页数
        int endPage = Integer.MAX_VALUE;
        // 是否排序
        boolean isSort = false;
        // 文件输入流，生成文本文件
//        Writer output = null;
        // 内存中存储的PDF Document
        PDDocument document = null;
        try {
            // 方式一：
            /**
             * InputStream input = new FileInputStream( pdfFile ); //加载 pdf 文档
             * PDFParser parser = new PDFParser(new RandomAccessBuffer(input));
             * parser.parse(); document = parser.getPDDocument();
             **/
            // 方式二：
            document = PDDocument.load(pdfFile);
            // PDFTextStripper来提取文本,// 读文本内容
            PDFTextStripper stripper = new PDFTextStripper();
            // 设置是否排序
            stripper.setSortByPosition(isSort);
            // 设置起始页
            stripper.setStartPage(startPage);
            // 设置结束页
            endPage = document.getNumberOfPages();// 获取页码
            stripper.setEndPage(endPage);
            // 调用PDFTextStripper的writeText提取并输出文本
            String content = stripper.getText(document);
            // System.out.println(content);

//            //写文件
//            if (pdfFile.length() > 4) {
//                outTextFilename = pdfFile.getCanonicalPath().replace(".pdf$", ".txt"); //匹配到行尾
//            }
//            // 文件输入流，写入文件倒textFile
//            output = new OutputStreamWriter(new FileOutputStream(outTextFilename), encoding);
//            stripper.writeText(document, output);
            return content;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
//            try {
//                if (output != null) {
//                    // 关闭输出流
//                    output.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            // 关闭PDF Document
            try {
                if (document != null) {
                    document.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        PdfReader pdfReader = new PdfReader();
        try {
            // 1.读取pdf文件路径
            // "/美国当代英语语料库COCA词频20000.pdf"
            String cfg_pdf = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_pdf");
            String pdfFilename = Constant.PATH_RESOURCES + cfg_pdf;
            String text = pdfReader.readPdf2Txt(pdfFilename);
            // 2.写入对应的text文件
            String outTextFilename = pdfFilename.replaceAll(".pdf$", ".txt"); //匹配到行尾
            // 生成文本文件
            ResourceUtil.writerFile(outTextFilename, text, false);
            System.out.println("done!outTextFilename="+outTextFilename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //PDF图片提取
    public static void readPdfImage(){
        // 待解析PDF
        File pdfFile = new File("/Users/xiaolong/Downloads/test.pdf");      
        // 空白PDF
        File pdfFile_out = new File("/Users/xiaolong/Downloads/testout.pdf");

        PDDocument document = null;  
        PDDocument document_out = null;  
        try {  
            document = PDDocument.load(pdfFile);  
            document_out = PDDocument.load(pdfFile_out);  
        } catch (IOException e) {  
            e.printStackTrace();
        }  

        int pages_size = document.getNumberOfPages();

        System.out.println("getAllPages==============="+pages_size);  
        int j=0;

        for(int i=0;i<pages_size;i++) {  
            PDPage page = document.getPage(i);
            PDPage page1 = document_out.getPage(0);
            PDResources resources = page.getResources();  
            Iterable xobjects = resources.getXObjectNames();

            if (xobjects != null) {  
                Iterator imageIter = xobjects.iterator();  
                while (imageIter.hasNext()) {  
                    COSName key = (COSName) imageIter.next();  
                    if(resources.isImageXObject(key)){              
                        try {
                            PDImageXObject image = (PDImageXObject) resources.getXObject(key);

                            // 方式一：将PDF文档中的图片 分别存到一个空白PDF中。
                            PDPageContentStream contentStream = new PDPageContentStream(document_out,page1,AppendMode.APPEND,true);

                            float scale = 1f;
                            contentStream.drawImage(image, 20,20,image.getWidth()*scale,image.getHeight()*scale);
                            contentStream.close();
                            document_out.save("/Users/xiaolong/Downloads/123"+j+".pdf");

                            System.out.println(image.getSuffix() + ","+image.getHeight() +"," + image.getWidth());

                            /**
                            // 方式二：将PDF文档中的图片 分别另存为图片。
                            File file = new File("/Users/xiaolong/Downloads/123"+j+".png");
                            FileOutputStream out = new FileOutputStream(file);

                            InputStream input = image.createInputStream();                   

                            int byteCount = 0;
                            byte[] bytes = new byte[1024];

                            while ((byteCount = input.read(bytes)) > 0)
                            {                       
                                out.write(bytes,0,byteCount);       
                            }

                            out.close();
                            input.close();
                            **/

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } 
                        //image count
                        j++;  
                    }                 
                }  
            } 
        } 

        System.out.println(j);
    }  
}