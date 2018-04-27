package com.myblog.set;

import java.util.ArrayList;
import java.util.List;

import com.myblog.Constant;
import com.myblog.util.RegEx;
import com.myblog.util.ResourceUtil;

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
