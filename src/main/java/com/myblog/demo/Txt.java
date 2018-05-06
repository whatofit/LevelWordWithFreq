package com.myblog.demo;

//Configuration

public class Txt {

    public Txt() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
    
//    String regex = "[.,\"!--;:?'\\]]"; //remove all punctuation  
//    line = line.toLowerCase().replaceAll(regex, " "); //remove all punctuation  
//    //split all words of line  
//    StringTokenizer tokenizer = new StringTokenizer(line);  
//    while (tokenizer.hasMoreTokens()) {  
//        s = tokenizer.nextToken();  
//        if(!stopWordSet.contains(s)){  
//            word.set(s);  
//            context.write(word, one);  
//        }                 
//    }  
    
    // 将复数单词转为单数单词
    public static String ChangeDoubleToOne(String token) {
        if (token.equalsIgnoreCase("feet")) {
            token = "foot";
        } else if (token.equalsIgnoreCase("geese")) {
            token = "goose";
        } else if (token.equalsIgnoreCase("lice")) {
            token = "louse";
        } else if (token.equalsIgnoreCase("mice")) {
            token = "mouse";
        } else if (token.equalsIgnoreCase("teeth")) {
            token = "tooth";
        } else if (token.equalsIgnoreCase("oxen")) {
            token = "ox";
        } else if (token.equalsIgnoreCase("children")) {
            token = "child";
        } else if (token.endsWith("men")) {
            token = token.substring(0, token.length() - 3) + "man";
        } else if (token.endsWith("ies")) {
            token = token.substring(0, token.length() - 3) + "y";
        } else if (token.endsWith("ves")) {
            if (token.equalsIgnoreCase("knives") || token.equalsIgnoreCase("wives")
                    || token.equalsIgnoreCase("lives")) {
                token = token.substring(0, token.length() - 3) + "fe";
            } else {
                token = token.substring(0, token.length() - 3) + "f";
            }
        } else if (token.endsWith("oes") || token.endsWith("ches") || token.endsWith("shes") || token.endsWith("ses")
                || token.endsWith("xes")) {
            token = token.substring(0, token.length() - 2);
        } else if (token.endsWith("s")) {
            token = token.substring(0, token.length() - 1);
        }
        return token;
    }
}
