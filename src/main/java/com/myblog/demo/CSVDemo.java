package com.myblog.demo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

import com.myblog.model.Word;
import com.myblog.util.CSVUtil;

public class CSVDemo {
	public static void csvTest() throws IOException {
		CSVParser parser = CSVUtil.getCSVParser("D://vabulary_csvDemo1.csv");
		CSVPrinter printer = CSVUtil.getCSVPrinter("D://vabulary_csvDemo2.csv");
		Iterator<CSVRecord> iterator = parser.iterator();
		printer.printRecord(parser.getHeaderMap().keySet());// 写CSV第一行
		while (iterator.hasNext()) {
			printer.printRecord(iterator.next());
		}
		IOUtils.closeQuietly(parser);
		IOUtils.closeQuietly(printer);
	}

	public static void csvTest2() throws IOException {
		final String[] FILE_HEADERs = { "freq", "lemmaWord", "level", "meanings" };
		Word nicely = new Word("6102", "nicely", "GRE", "恰好地；精确地；漂亮地");
		Word shooting = new Word("2846", "shooting", "高中", "射击；发射；猎场");
		Word tee = new Word("6392", "tee", "SAT", "字母t；形状像字母T的东西");
		Word turf = new Word("6715", "turf", "IELTS", "草皮；泥炭；赛马；赛马场;铺草皮");
		Word gosh = new Word("7791", "gosh", "TOEFL", "唉；糟了");
		Word untrue = new Word("12282", "untrue", "GMAT", "不真实的；不正确的；不忠实的；不符合(标准、原则等)的");
		Word incomparable = new Word("19614", "incomparable", "专八", "无可比拟的；无比的；无双的");

		List<Word> words = new ArrayList<>();
		words.add(nicely);
		words.add(shooting);
		words.add(tee);
		words.add(turf);
		words.add(gosh);
		words.add(untrue);
		words.add(incomparable);

		// 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
		CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADERs).withSkipHeaderRecord();

		// 这是写入CSV的代码
		final String FILE_NAME = "D://vabulary_csvDemo1.csv";
		try (Writer out = new FileWriter(FILE_NAME); CSVPrinter printer = new CSVPrinter(out, format)) {
			for (Word word : words) {
				List<String> records = new ArrayList<>();
				records.add(word.getFrequency());
				records.add(word.getSpelling());
				records.add(word.getLevel());
				records.add(word.getMeanings());
				printer.printRecord(records);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 这是从上面写入的文件中读出数据的代码,缺少headline,因为前面跳过了head/但第一行也被跳过了
		try (Reader in = new FileReader(FILE_NAME)) {
			Iterable<CSVRecord> records = format.parse(in);
			String strFreq;
			String strLemma;
			for (CSVRecord record : records) {
				strFreq = record.get("freq");
				strLemma = record.get("lemmaWord");
				System.out.println(strFreq + " " + strLemma);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public static void csvTest3() throws IOException {
//		// 数据写入csv
//		String[] csvHeaders = new String[] { "freq", "lemmaWord", "level", "meanings" };
//		List<String[]> words = new ArrayList<String[]>();
//		words.add(new String[] { "6102", "nicely", "GRE", "恰好地；精确地；漂亮地" });
//		words.add(new String[] { "2846", "shooting", "高中", "射击；发射；猎场" });
//		words.add(new String[] { "6392", "tee", "SAT", "字母t；形状像字母T的东西" });
//		words.add(new String[] { "6715", "turf", "IELTS", "草皮；泥炭；赛马；赛马场;铺草皮" });
//		words.add(new String[] { "7791", "gosh", "TOEFL", "唉；糟了" });
//		words.add(new String[] { "12282", "untrue", "GMAT", "不真实的；不正确的；不忠实的；不符合(标准、原则等)的" });
//		words.add(new String[] { "19614", "incomparable", "专八", "无可比拟的；无比的；无双的" });
//
//		// 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
//		CSVFormat format = CSVFormat.DEFAULT.withHeader(csvHeaders).withSkipHeaderRecord();
//
//		// 这是写入CSV的代码
//		final String FILE_NAME = "D://vabulary_csvDemo1.csv";
//		try {
//			Writer out = new FileWriter(FILE_NAME);
//			CSVPrinter printer = new CSVPrinter(out, format);
//			for (String[] word : words) {
//				List<String> records = Arrays.asList(word);
//				printer.printRecord(records);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// 这是从上面写入的文件中读出数据的代码,缺少headline,因为前面跳过了head/但第一行也被跳过了
//		try (Reader in = new FileReader(FILE_NAME)) {
//			Iterable<CSVRecord> records = format.parse(in);
//			String strFreq;
//			String strLemma;
//			for (CSVRecord record : records) {
//				strFreq = record.get("freq");
//				strLemma = record.get("lemmaWord");
//				System.out.println(strFreq + " " + strLemma);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void csvTest3() throws IOException {
		String[] csvHeaders = new String[] { "freq", "lemmaWord", "level", "meanings" };
		List<String[]> words = new ArrayList<String[]>();
		words.add(new String[] { "6102", "nicely", "GRE", "恰好地；精确地；漂亮地" });
		words.add(new String[] { "2846", "shooting", "高中", "射击；发射；猎场" });
		words.add(new String[] { "6392", "tee", "SAT", "字母t；形状像字母T的东西" });
		words.add(new String[] { "6715", "turf", "IELTS", "草皮；泥炭；赛马；赛马场;铺草皮" });
		words.add(new String[] { "7791", "gosh", "TOEFL", "唉；糟了" });
		words.add(new String[] { "12282", "untrue", "GMAT", "不真实的；不正确的；不忠实的；不符合(标准、原则等)的" });
		words.add(new String[] { "19614", "incomparable", "专八", "无可比拟的；无比的；无双的" });

		// 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
		CSVFormat format = CSVFormat.DEFAULT.withHeader(csvHeaders).withSkipHeaderRecord();

		// 这是写入CSV的代码
		final String FILE_NAME = "D://vabulary_csvDemo1.csv";
		try (Writer out = new FileWriter(FILE_NAME); CSVPrinter printer = new CSVPrinter(out, format)) {
			for (String[] word : words) {
				printer.printRecord(Arrays.asList(word));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 这是从上面写入的文件中读出数据的代码,缺少headline,因为前面跳过了head/但第一行也被跳过了
		try (Reader in = new FileReader(FILE_NAME)) {
			Iterable<CSVRecord> records = format.parse(in);
			String strFreq;
			String strLemma;
			for (CSVRecord record : records) {
				strFreq = record.get("freq");
				strLemma = record.get("lemmaWord");
				System.out.println(strFreq + " " + strLemma);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] arg) throws IOException {
		//csvTest2();
		//csvTest();
		csvTest3();
	}
}