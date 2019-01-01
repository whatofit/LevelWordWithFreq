package com.level.loadword2db;

import java.io.File;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.level.Constant;
import com.level.model.JsonWord;
import com.level.model.Word;
import com.level.util.ResourceUtil;

public class Json2SqlitePOSsLine extends FmtWordVectorIntoSqlite {
    public Json2SqlitePOSsLine() {
        super();
    }

	public JsonWord getQQJsonWord(String line) {
		String[] arr = line.trim().split("\t");
		// line = line.trim().replaceFirst("\t", "-");
		String jsonFileName = arr[0] + "-" + arr[1] + ".json";
		String jsonWordFile = Constant.PATH_QQ + File.separator + jsonFileName;
		System.out.println(jsonWordFile);
		String body = ResourceUtil.readFile(jsonWordFile);
		// System.out.println("body:"+body);
		JSONObject jsonObjWord = JSONObject.parseObject(body);
		// mWord = FastJsonUtil.json2obj(body, Word.class);
		// mWord.setWordFrequency(arr[0]);
		// mWord.setKey(arr[1]);
		// System.out.println("getJsonWord,Key=" + mWord.getKey() + ",name＝"
		// + mWord.getWordFrequency());
		JsonWord jsonWord = new JsonWord();
		jsonWord.setFrequency(arr[0]);
		jsonWord.setWord(arr[1]);
		if (jsonObjWord == null) {
			return jsonWord;
		}

		Object objLocal = jsonObjWord.get("local");
		if (objLocal == null) {
			return jsonWord;
		}
		if (objLocal instanceof JSONArray) {
		}
		// System.out.println("getJsonWord,local=" + objLocal);
		// JSONObject.toJavaObject(objLocal, JsonWord.class);
		List<JsonWord> words = JSON.parseArray(objLocal.toString(), JsonWord.class);
		jsonWord = words.get(0);
		jsonWord.setFrequency(arr[0]);
		// jsonWord.setWord(arr[1]);
		return jsonWord;
	}
	
    // 每一个单词,插入词性个数条记录,词频/单词/音标等,都是重复的
    public void line2WordVector(String line) {
        JsonWord word = getQQJsonWord(line);
        word.add2Vector(vecWords, word);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Json2SqlitePOSsLine levelSqlite = new Json2SqlitePOSsLine();
            levelSqlite.loadFile2WordVector(Constant.FILE_FREQ_OF_WORDS);
            //levelSqlite.line2WordVector("00007   that");
            levelSqlite.createOrUpdateWordDB(Word.FIELD_NAME_SPELLING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
