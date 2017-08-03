package com.myblog.util;

import java.util.Map;

import com.myblog.model.Word;

public class ParseInt {

    public ParseInt() {
    }

    public static void main(String[] args) {
//        String freq = "1";
//        Integer nFreq = Integer.parseInt(freq.trim());
//        System.out.println("nFreq: " + nFreq);
        String line = "1    the";
        String[] segments = line.split("\\s+");// 以空白(空格/tab键/回车/换行)分割
        String word = "";
        String freq = "99999";// 默认较大的频率
        if (segments.length <= 1) {
            word = segments[0];
        } else {
            word = segments[1];
            freq = segments[0];
        }
        System.out.println("freq,word:" + freq + "\t" + word);
        StringBuilder sb = new StringBuilder();
        // sb.append(freq + "\t" + word + "\t");
        Word dbWord = new Word();
        dbWord.setSpelling(word);
        //Integer nFreq = Integer.parseInt(freq);
        dbWord.setFrequency(freq);
        dbWord.setLevel(sb.toString());
        
        System.out.println("word:" + dbWord);
    }
}
