package com.level.loadword2db;

import java.util.List;

import com.level.Constant;
import com.level.model.Word;
import com.level.model.XmlWord;
import com.level.util.FastJsonUtil;

public class Xml2SqliteJson1Line extends FmtWordVectorIntoSqlite {

	public Xml2SqliteJson1Line() {
		super();
	}

	// 所有词性/词义在一行的json中
	public void line2WordVector(String line) {
		XmlWord xmlWord = wordParser.getXmlWord(line);
		String wordJson = FastJsonUtil.obj2json(xmlWord);
		Word dbWord = new Word();
		dbWord.setId(vecWords.size());
		// dbWord.setFrequency(xmlWord.getFrequency());
		dbWord.setFreqWritten(xmlWord.getFrequency());
		dbWord.setSpelling(xmlWord.getKey());// 单词
		dbWord.setPhoneticDJ(xmlWord.getPs());// 英语音标
		dbWord.setPhoneticKK(xmlWord.getPs()); // 美语音标
		dbWord.setLevel(null);
		dbWord.setPartsOfSpeech(null);// 词性
		dbWord.setMeanings(mergePoSsAndMeanings(xmlWord));// 词义列表
		dbWord.setSentences(wordJson);// 本词性对应的所有字段
		vecWords.add(dbWord);
	}

	// 合并词性与词义到一个字段中
	public String mergePoSsAndMeanings(XmlWord xmlWord) {
		String strPoSsAndMeanings = "";
		List<String> partsOfSpeech = xmlWord.getPartsOfSpeech();
		List<String> meaning = xmlWord.getMeaning();
		for (int i = 0; i < partsOfSpeech.size(); i++) {
			String pos = partsOfSpeech.get(i);
			if ("".equals(strPoSsAndMeanings)) {
				strPoSsAndMeanings = (pos + meaning.get(i));
			} 
			else {
				strPoSsAndMeanings += ("\n" + pos + meaning.get(i) );
			}
		}
		return strPoSsAndMeanings;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Xml2SqliteJson1Line levelSqlite = new Xml2SqliteJson1Line();
			levelSqlite.loadFile2WordVector(Constant.FILE_FREQ_OF_WORDS);
			levelSqlite.createOrUpdateWordDB(Word.FIELD_NAME_SPELLING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
