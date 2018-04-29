package com.myblog.txt;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.poi.hwpf.extractor.WordExtractor;

//https://blog.csdn.net/zlb824/article/details/7020191
//以下是Java对几种文本文件内容读取代码。
//其中，OFFICE文档（WORD,EXCEL）使用了POI控件，
//PDF使用了PDFBOX控件。
public class ConvertTxtFmt {

	public static void main(String[] args) {

	}

	/**
	 * WORD格式转换成text文本
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 读出的Word的内容
	 */
	public String getTextFromWord(String filePath) {
		String result = null;
		File file = new File(filePath);
		try {
			FileInputStream fis = new FileInputStream(file);
			WordExtractor wordExtractor = new WordExtractor(fis);
			result = wordExtractor.getText();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		;
		return result;
	}

	/**
	 * @param filePath
	 *            文件路径
	 * @return 读出的pdf的内容
	 */
	public String getTextFromPdf(String filePath) {
		String result = null;
		// FileInputStream is = null;
		// PDDocument document = null;
		// try {
		// is = new FileInputStream(filePath);
		// PDFParser parser = new PDFParser(is);
		// parser.parse();
		// document = parser.getPDDocument();
		// PDFTextStripper stripper = new PDFTextStripper();
		// result = stripper.getText(document);
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// if (is != null) {
		// try {
		// is.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// if (document != null) {
		// try {
		// document.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		return result;
	}

	/**
	 * 直接读取text文本
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 读出的txt的内容
	 */
	public String getTextFromTxt(String filePath) throws Exception {

		FileReader fr = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fr);
		StringBuffer buff = new StringBuffer();
		String temp = null;
		while ((temp = br.readLine()) != null) {
			buff.append(temp + "\r\n");
		}
		br.close();
		return buff.toString();
	}

	/**
	 * @param filePath
	 *            文件路径
	 * @return 读出的rtf的内容
	 */
	public String getTextFromRtf(String filePath) {
		String bodyText = null;
		DefaultStyledDocument styledDoc = new DefaultStyledDocument();
		File file = new File(filePath);
		try {
			InputStream is = new FileInputStream(file);
			new RTFEditorKit().read(is, styledDoc, 0);
			bodyText = new String(styledDoc.getText(0, styledDoc.getLength()).getBytes("ISO8859_1"));
			// 提取文本，读取中文需要使用ISO8859_1编码，否则会出现乱码
		} catch (IOException e) {
			e.printStackTrace();
			//throw new DocumentHandlerException("不能从RTF中摘录文本!", e);
		} catch (BadLocationException e) {
			e.printStackTrace();
			//throw new DocumentHandlerException("不能从RTF中摘录文本!", e);
		}
		return bodyText;
	}

	/**
	 * @param filePath
	 *            文件路径
	 * @return 获得html的全部内容
	 */
	public String readHtml(String filePath) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GB2312"));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * @param filePath
	 *            文件路径
	 * @return 获得的html文本内容
	 */
	public String getTextFromHtml(String filePath) {
		// 得到body标签中的内容
		String str = readHtml(filePath);
		StringBuffer buff = new StringBuffer();
		int maxindex = str.length() - 1;
		int begin = 0;
		int end;
		// 截取>和<之间的内容
		while ((begin = str.indexOf('>', begin)) < maxindex) {
			end = str.indexOf('<', begin);
			if (end - begin > 1) {
				buff.append(str.substring(++begin, end));
			}
			begin = end + 1;
		}
		;
		return buff.toString();
	}
	
    //String rtfText = ...;
    //String htmlText = rtfToHtml(new StringReader(rtfText));
    
    //String htmlText = rtfToHtml(new FileReader(new File("myfile.rtf")));
    public static String rtfToHtml(Reader rtf) throws IOException {
        JEditorPane p = new JEditorPane();
        p.setContentType("text/rtf");
        EditorKit kitRtf = p.getEditorKitForContentType("text/rtf");
        try {
            kitRtf.read(rtf, p.getDocument(), 0);
            kitRtf = null;
            EditorKit kitHtml = p.getEditorKitForContentType("text/html");
            Writer writer = new StringWriter();
            kitHtml.write(writer, p.getDocument(), 0, p.getDocument().getLength());
            return writer.toString();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return null;
    }
    
	private static String convertToRTF(String htmlStr) {
        OutputStream os = new ByteArrayOutputStream();
        HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
        RTFEditorKit rtfEditorKit = new RTFEditorKit();
        String rtfStr = null;

        htmlStr = htmlStr.replaceAll("<br.*?>", "#NEW_LINE#");
        htmlStr = htmlStr.replaceAll("</p>", "#NEW_LINE#");
        htmlStr = htmlStr.replaceAll("<p.*?>", "");
        InputStream is = new ByteArrayInputStream(htmlStr.getBytes());
        try {
            Document doc = htmlEditorKit.createDefaultDocument();
            htmlEditorKit.read(is, doc, 0);
            rtfEditorKit.write(os, doc, 0, doc.getLength());
            rtfStr = os.toString();
            rtfStr = rtfStr.replaceAll("#NEW_LINE#", "\\\\par ");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return rtfStr;
    }
}

//相关控件的下载地址和配置方法:
//一、POI 
//POI是Apache的Jakata项目，POI 代表 Poor Obfuscation Implementation，即不良模糊化实现。POI 的目标就是提供一组 Java API 来使得基于 Microsoft OLE 2 Compound Document 格式的 Microsoft Office 文件易于操作。
//
//下载地址 ：http://apache.etoak.com/poi/release/bin/
//
//相关配置 ：
//（1） 把下载的 poi-bin-3.5-FINAL-20090928.tar.gz 解压。
//（2） 将以下四个jar包拷入项目的lib文件夹下（如还未建立lib目录，则先创建一个， 不一定要是这个位置，只要classpath能找到jar就可以）。 
//poi-3.5-FINAL-20090928.jar
//poi-contrib-3.5-FINAL-20090928.jar
//poi-ooxml-3.5-FINAL-20090928.jar
//poi-scratchpad-3.5-FINAL-20090928.jar
//（3） 在工程上单击右键，在弹出的快捷菜单中选择“Build Path->Config Build Path->Add Jars”命令进行添加。 
//（4）在Order and Export标签页勾选库文件。
//
//二、PDFBOX 
//PDFBOX是一个为开发人员读取和创建PDF文档而准备的纯Java类库。
//
//下载地址：http://sourceforge.net/projects/pdfbox/
//
//相关配置：
//（1）把下载的 PDFBox-0.7.3.zip 解压
//（2）进入external目录，这里包括了PDFBox所有用到的外部包。复制以下Jar包到工程lib目录下 
//bcmail-jdk14-132.jar
//bcprov-jdk14-132.jar
//checkstyle-all-4.2.jar
//FontBox-0.1.0-dev.jar
//lucene-core-2.0.0.jar
//（3）然后再从PDFBox的lib目录下，复制PDFBox-0.7.3.jar到工程的lib目录下 
//（4）在工程上单击右键，在弹出的快捷菜单中选择“Build Path->Config Build Path->A5dd Jars”命令，把工程lib目录下面的包都加入工程的Build Path。
//（ 5 ）在Order and Export标签页勾选库文件。
//
//三、JDOM 
//JDOM是一个开源项目，它基于树型结构，利用纯JAVA的技术对XML文档实现解析、生成、序列化以及多种操作。 
//下载地址 ： http://www.jdom.org/downloads/index.html
//相关配置 ：
//（1）把下载的jdom-1.1.1.tar.gz 解压 
//（2） 进入build目录 ，把jdom.jar 拷入项目的lib文件夹下 
//（3） 在工程上单击右键，在弹出的快捷菜单中选择“Build Path->Config Build Path->Add Jars”命令进行添加。 
//（4）在Order and Export标签页勾选库文件。
//
//
//转载地址：http://www.javawind.net/fdc1ee6a2413479a01245c44b9880f78.jhtml
