package com.level.set;

import java.util.Set;

import com.level.Constant;
import com.level.model.Word;

public class FrySightWords {

    public FrySightWords() {
    }

    public static void main(String[] args) {
        loadFrySightWords();
    }
    
    //加载FrySightWords，By Frequency
    //设置Groups属性，ByGroups
    //设置Order属性，inAlphabeticalOrder
    //保存FrySightWords到文件
    public static void loadFrySightWords() {
        Set<Word> setFreq   = WordFilesMgr.loadWordListOfFreq("/FrySightWordsListedByFrequency.txt");
        Set<Word> setGroups = WordFilesMgr.loadWordListOfFreq("/FrySightWordsListedByGroups.txt");
        Set<Word> setInOrder= WordFilesMgr.loadWordListOfFreq("/FSWsinAlphabeticalOrder.txt");
        for (Word freq:setFreq) {
            for (Word group:setGroups) {
                if (group.getSpelling().equals(freq.getSpelling())) {
                    freq.setLevel(group.getFrequency());
                    break;
                }
            }
        }

        for (Word freq:setFreq) {
            for (Word InOrder:setInOrder) {
                if (InOrder.getSpelling().equals(freq.getSpelling())) {
                    System.out.println("InOrder.getFrequency()："+InOrder.getFrequency());
                    //freq.setId(Integer.valueOf(InOrder.getFrequency().trim()).intValue());
                    try {
                        freq.setId(Integer.parseInt(InOrder.getFrequency().trim()));
                    }catch(Exception e)  {
                        freq.setId(0);
                    }
                    break;
                }
            }
        }
        WordFilesMgr.saveWordSet(setFreq, Constant.PATH_RESOURCES + "/resultSetFilefly.txt");
        System.out.println("done!");
    }
}
