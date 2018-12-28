package com.level.rtf2txt;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.extractor.WordExtractor;

import com.level.util.ResourceUtil;

//https://blog.csdn.net/zlb824/article/details/7020191
//以下是Java对几种文本文件内容读取代码。
//其中，OFFICE文档（WORD,EXCEL）使用了POI控件，
//PDF使用了PDFBOX控件。
public class RtfUtil {

	private static WordExtractor wordExtractor;

	public static void main(String[] args) {

	}

	/**
	 * pdf to text into same dir
	 */
	public static void pdf2Text(String pdfFilename) {
		// 1.读取pdf文件路径
		String text = RtfUtil.readPdf2Txt(pdfFilename);
		// 2.生成pdf对应的text文件名称//.pdf$"匹配到行尾
		String outTextFilename = pdfFilename.replaceAll(".pdf$", ".txt");
		// 3.保存为文本文件
		ResourceUtil.writerFile(outTextFilename, text, false);
		System.out.println("done!outTextFilename=" + outTextFilename);
	}

	/**
	 * doc to text into same dir
	 */
	public static void doc2Text(String docFilename) {
		// 1.读取doc文件路径
		String text = RtfUtil.getTextFromWord(docFilename);
		// 2.生成doc对应的text文件名称//.docx?$匹配到行尾,.docx?匹配.docx或.doc
		String outTextFilename = docFilename.replaceAll(".docx?$", ".txt");
		// 3.保存为文本文件
		ResourceUtil.writerFile(outTextFilename, text, false);
		System.out.println("done!outTextFilename=" + outTextFilename);
	}

	/**
	 * WORD格式转换成text文本
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 读出的Word的内容
	 */
	public static String getTextFromWord(String filePath) {
		String result = null;
		File file = new File(filePath);
		try {
			FileInputStream fis = new FileInputStream(file);
			wordExtractor = new WordExtractor(fis);
			result = wordExtractor.getText();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param filePath
	 *            文件路径
	 * @return 读出的pdf的内容
	 */
	public String getTextFromPdf(String filePath) {
		String result = null;
		FileInputStream is = null;
		PDDocument document = null;
		try {
			File pdfFile = new File(filePath);
			// 方式一：
			InputStream input = new FileInputStream(pdfFile); // 加载 pdf 文档
			PDFParser parser = new PDFParser(new RandomAccessBuffer(input));
			parser.parse();
			document = parser.getPDDocument();
			// 方式二：
			// document = PDDocument.load(pdfFile);

			PDFTextStripper stripper = new PDFTextStripper();
			// 设置是否排序
			stripper.setSortByPosition(true);
			// 设置起始页
			stripper.setStartPage(1);
			// 设置结束页
			int endPage = document.getNumberOfPages();// 获取页码
			stripper.setEndPage(endPage);
			result = stripper.getText(document);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	// PDF文本内容提取
	public static String readPdf2Txt(String filename) {
		// pdf文件名
		File pdfFile = new File(filename);
		// // 输入文本文件名称
		// String outTextFilename = null;
		// // 编码方式
		// String encoding = "UTF-8";
		// 开始提取页数
		int startPage = 1;
		// 结束提取页数
		int endPage = Integer.MAX_VALUE;
		// 是否排序
		boolean isSort = false;
		// 文件输入流，生成文本文件
		// Writer output = null;
		// 内存中存储的PDF Document
		PDDocument document = null;
		try {
			// 方式一：
			/**
			 * InputStream input = new FileInputStream( pdfFile ); //加载 pdf 文档 PDFParser
			 * parser = new PDFParser(new RandomAccessBuffer(input)); parser.parse();
			 * document = parser.getPDDocument();
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

			// //写文件
			// if (pdfFile.length() > 4) {
			// outTextFilename = pdfFile.getCanonicalPath().replace(".pdf$", ".txt");
			// //匹配到行尾
			// }
			// // 文件输入流，写入文件倒textFile
			// output = new OutputStreamWriter(new FileOutputStream(outTextFilename),
			// encoding);
			// stripper.writeText(document, output);
			return content;
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			// try {
			// if (output != null) {
			// // 关闭输出流
			// output.close();
			// }
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
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

	// PDF to doc
	public static boolean readPdf2Doc(String filename) {
		// pdf文件名
		File pdfFile = new File(filename);
		// 输入文本文件名称
		String outTextFilename = null;
		// 编码方式
		String encoding = "UTF-8";
		// 开始提取页数
		int startPage = 1;
		// 结束提取页数
		int endPage = Integer.MAX_VALUE;
		// 是否排序
		boolean isSort = false;
		// 文件输入流，生成文本文件
		Writer outputWriter = null;
		// 内存中存储的PDF Document
		PDDocument document = null;
		try {
			// 方式一：
			/**
			 * InputStream input = new FileInputStream( pdfFile ); //加载 pdf 文档 PDFParser
			 * parser = new PDFParser(new RandomAccessBuffer(input)); parser.parse();
			 * document = parser.getPDDocument();
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
			// String content = stripper.getText(document);
			// System.out.println(content);

			// 写文件
			if (pdfFile.length() > 4) {
				outTextFilename = pdfFile.getCanonicalPath().replace(".pdf$", ".doc"); // 匹配到行尾
			}
			System.out.println("outTextFilename=" + outTextFilename);
			// 文件输入流，写入文件倒textFile
			FileOutputStream fos = new FileOutputStream(outTextFilename);
			outputWriter = new OutputStreamWriter(fos, encoding);
			stripper.writeText(document, outputWriter);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (outputWriter != null) {
					// 关闭输出流
					outputWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		return false;
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
			// throw new DocumentHandlerException("不能从RTF中摘录文本!", e);
		} catch (BadLocationException e) {
			e.printStackTrace();
			// throw new DocumentHandlerException("不能从RTF中摘录文本!", e);
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

	// String rtfText = ...;
	// String htmlText = rtfToHtml(new StringReader(rtfText));

	// String htmlText = rtfToHtml(new FileReader(new File("myfile.rtf")));
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

	public static String convertToRTF(String htmlStr) {
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

	// PDF图片提取
	public static void readPdfImage() {
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

		System.out.println("getAllPages===============" + pages_size);
		int j = 0;

		for (int i = 0; i < pages_size; i++) {
			PDPage page = document.getPage(i);
			PDPage page1 = document_out.getPage(0);
			PDResources resources = page.getResources();
			Iterable<?> xobjects = resources.getXObjectNames();

			if (xobjects != null) {
				Iterator<?> imageIter = xobjects.iterator();
				while (imageIter.hasNext()) {
					COSName key = (COSName) imageIter.next();
					if (resources.isImageXObject(key)) {
						try {
							PDImageXObject image = (PDImageXObject) resources.getXObject(key);

							// 方式一：将PDF文档中的图片 分别存到一个空白PDF中。
							PDPageContentStream contentStream = new PDPageContentStream(document_out, page1,
									AppendMode.APPEND, true);

							float scale = 1f;
							contentStream.drawImage(image, 20, 20, image.getWidth() * scale, image.getHeight() * scale);
							contentStream.close();
							document_out.save("/Users/xiaolong/Downloads/123" + j + ".pdf");

							System.out.println(image.getSuffix() + "," + image.getHeight() + "," + image.getWidth());

							/**
							 * // 方式二：将PDF文档中的图片 分别另存为图片。 File file = new
							 * File("/Users/xiaolong/Downloads/123"+j+".png"); FileOutputStream out = new
							 * FileOutputStream(file);
							 * 
							 * InputStream input = image.createInputStream();
							 * 
							 * int byteCount = 0; byte[] bytes = new byte[1024];
							 * 
							 * while ((byteCount = input.read(bytes)) > 0) { out.write(bytes,0,byteCount); }
							 * 
							 * out.close(); input.close();
							 **/

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// image count
						j++;
					}
				}
			}
		}

		System.out.println(j);
	}
}

// 相关控件的下载地址和配置方法:
// 一、POI
// POI是Apache的Jakata项目，POI 代表 Poor Obfuscation Implementation，即不良模糊化实现。POI
// 的目标就是提供一组 Java API 来使得基于 Microsoft OLE 2 Compound Document 格式的 Microsoft
// Office 文件易于操作。
//
// 下载地址 ：http://apache.etoak.com/poi/release/bin/
//
// 相关配置 ：
// （1） 把下载的 poi-bin-3.5-FINAL-20090928.tar.gz 解压。
// （2） 将以下四个jar包拷入项目的lib文件夹下（如还未建立lib目录，则先创建一个， 不一定要是这个位置，只要classpath能找到jar就可以）。
// poi-3.5-FINAL-20090928.jar
// poi-contrib-3.5-FINAL-20090928.jar
// poi-ooxml-3.5-FINAL-20090928.jar
// poi-scratchpad-3.5-FINAL-20090928.jar
// （3） 在工程上单击右键，在弹出的快捷菜单中选择“Build Path->Config Build Path->Add Jars”命令进行添加。
// （4）在Order and Export标签页勾选库文件。
//
// 二、PDFBOX
// PDFBOX是一个为开发人员读取和创建PDF文档而准备的纯Java类库。
//
// 下载地址：http://sourceforge.net/projects/pdfbox/
//
// 相关配置：
// （1）把下载的 PDFBox-0.7.3.zip 解压
// （2）进入external目录，这里包括了PDFBox所有用到的外部包。复制以下Jar包到工程lib目录下
// bcmail-jdk14-132.jar
// bcprov-jdk14-132.jar
// checkstyle-all-4.2.jar
// FontBox-0.1.0-dev.jar
// lucene-core-2.0.0.jar
// （3）然后再从PDFBox的lib目录下，复制PDFBox-0.7.3.jar到工程的lib目录下
// （4）在工程上单击右键，在弹出的快捷菜单中选择“Build Path->Config Build Path->A5dd
// Jars”命令，把工程lib目录下面的包都加入工程的Build Path。
// （ 5 ）在Order and Export标签页勾选库文件。
//
// 三、JDOM
// JDOM是一个开源项目，它基于树型结构，利用纯JAVA的技术对XML文档实现解析、生成、序列化以及多种操作。
// 下载地址 ： http://www.jdom.org/downloads/index.html
// 相关配置 ：
// （1）把下载的jdom-1.1.1.tar.gz 解压
// （2） 进入build目录 ，把jdom.jar 拷入项目的lib文件夹下
// （3） 在工程上单击右键，在弹出的快捷菜单中选择“Build Path->Config Build Path->Add Jars”命令进行添加。
// （4）在Order and Export标签页勾选库文件。
//
//
// 转载地址：http://www.javawind.net/fdc1ee6a2413479a01245c44b9880f78.jhtml
