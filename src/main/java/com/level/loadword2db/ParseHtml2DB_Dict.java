package com.level.loadword2db;

import java.io.File;

import com.level.Constant;
import com.level.model.Word;
import com.level.util.ParseHtmlUtil;
import com.level.util.ResourceUtil;

public class ParseHtml2DB_Dict extends FmtWordVectorIntoSqlite {
    public ParseHtml2DB_Dict() {
        super();
    }

    // 每一个单词,有如下字段：
	//(书面)频率/freqWritten
	//口语频率/freqSpoken
	//词干/lemma(word spelling)
	//美音音标/phoneticKK
	//英音音标/phoneticDJ
	//分级/MinLevel
	//常见词性&词义s/PoSs&meanings
    //wordJson字段
    public void line2WordVector(String line) {
        line = line.trim();
        if (!line.startsWith("#") && !"".equals(line)) {
            String[] segments = line.split("\\s+");// 以空白(空格/tab键/回车/换行)分割
            String spelling = segments[1];
        	// 1.读取单词集合路径
            String wordFile = Constant.PATH_DICT + File.separator + spelling + ".html";
        	// 2.加载file
            String htmlFileBody = ResourceUtil.readFile(wordFile);
            // 3.解析数据字段
            String[] dictWordCotent = ParseHtmlUtil.parseDictWordContent(htmlFileBody);
            // 4.设置数据库字段
            Word dbWord = new Word();
    		dbWord.setId(vecWords.size());
    		// dbWord.setFrequency(xmlWord.getFrequency());
    		dbWord.setFreqWritten(segments[0]);
    		dbWord.setSpelling(spelling);// 单词
    		dbWord.setPhoneticDJ(dictWordCotent[4]);// 英语音标
    		dbWord.setPhoneticKK(dictWordCotent[5]); // 美语音标
    		dbWord.setLevel(null);
    		//dbWord.setPartsOfSpeech(null);// 词性
    		dbWord.setSimplify(dictWordCotent[3]);// 精简的词义
    		dbWord.setMeanings(dictWordCotent[2]);// 词义列表
    		//dbWord.setSentences(wordJson);// 本词性对应的所有字段
    		// 5.加到Vector中,继而会更新到数据库中
    		vecWords.add(dbWord);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
        	ParseHtml2DB_Dict levelSqlite = new ParseHtml2DB_Dict();
            levelSqlite.loadFile2WordVector(Constant.FILE_FREQ_OF_WORDS);
            //levelSqlite.line2WordVector("00007   take");
            //levelSqlite.line2WordVector("00128	take 	");
            levelSqlite.createOrUpdateWordDB(Word.FIELD_NAME_SPELLING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
