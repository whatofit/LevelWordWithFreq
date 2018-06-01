package com.myblog.demo;

import java.util.ArrayList;  
import java.util.List;  
import java.util.Map;  
import java.util.function.Function;  
import java.util.stream.Collectors;  
import java.util.stream.Stream;

import com.myblog.model.Word;  

public class Collectors2MapDemo {  
  public static void main(String[] args) {  

      List<Word> list = new ArrayList<Word>();  
      list.add(new Word("1", "haha"));  
      list.add(new Word("2", "rere"));  
      list.add(new Word("3", "fefe"));  


        
      Map<String, Word> mapp = list.stream().collect(Collectors.toMap(Word::getFrequency, Function.identity()));  
        
      System.out.println(mapp);  
        
      System.out.println(mapp.get("1").getSpelling());  
        
      Map<String, String> map = list.stream().collect(Collectors.toMap(Word::getFrequency, Word::getSpelling));  

      System.out.println(map);  

  }  

}  