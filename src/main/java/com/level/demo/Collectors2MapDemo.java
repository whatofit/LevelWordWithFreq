package com.level.demo;

import java.util.ArrayList;  
import java.util.List;  
import java.util.Map;  
import java.util.function.Function;  
import java.util.stream.Collectors;  
import java.util.stream.Stream;

import com.level.model.Word;  

public class Collectors2MapDemo {  
  public static void main(String[] args) {  

      List<Word> list = new ArrayList<Word>();  
//      list.add(new Word("circle", "circle	\"circular\"	四"));  
//      list.add(new Word("really", "real	\"really,reality\"	四"));  
//      list.add(new Word("hope", "hope	\"hopeful,hopeless\"	四"));  
//      list.add(new Word("circle", "circle	circular	四"));  
//      list.add(new Word("really", "real	really,reality	四"));  
//      list.add(new Word("hope", "hope	hopeful,hopeless	四"));  
      
      list.add(new Word("circle", "circle	circular	四"));  
      list.add(new Word("really", "real	really,reality	四"));  
      list.add(new Word("hope", "hopeful"));  


//      Map<String, Word> mapp = list.stream().collect(Collectors.toMap(Word::getFrequency, Function.identity()));  
//      System.out.println(mapp);  
//      System.out.println(mapp.get("really").getSpelling());  
      Map<String, String> map = list.stream().collect(Collectors.toMap(Word::getFrequency, Word::getSpelling));  
      System.out.println(map);  
      String word = "hopeful";
      if (map.containsKey(word) || map.containsValue(word)) {//value中不包含带英文单词的其他项
    	  System.out.println("in map "); 
      }else {
    	  System.out.println("not in map"); 
      }
  }  

}  