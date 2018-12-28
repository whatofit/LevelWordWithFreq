package com.level.set;

import java.util.ArrayList;
import java.util.List;

import com.level.Constant;
import com.level.util.RegEx;
import com.level.util.ResourceUtil;

//VOA慢速英语美音版词汇手册.txt
public class ParseWordbookOfVOA {

    public ParseWordbookOfVOA() {
    }

    public static void main(String[] args) {
        List<String> wordLines = new ArrayList<String>();
        List<String> voaLines = WordFilesMgr.loadWordsFromFile("/wordbook_VOA.txt");
        for(String line:voaLines){
            String wordLine = RegEx.parseWordOfVOA(line);
            wordLines.add(wordLine);
        }
        ResourceUtil.writerFile(Constant.PATH_RESOURCES + "/vocabulary_VOA.txt", wordLines, false);
    }

}
