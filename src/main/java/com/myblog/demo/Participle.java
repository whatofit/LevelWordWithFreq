package com.myblog.demo;

//问题描述： 
//给定一个文本文件，按以下要求进行分词统计：
//时间限制:5000ms 
//内存限制:256MB
//要求1：读取文本信息（input.txt），设置分词大小，输出相应词频信息 
//要求2：统计一个单词在文本中的出现频率（一个单词出现次数/总单词数），排序输出结果

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class Participle {

    public static void solutionOne(){
        Scanner in = new Scanner(System.in);
        print("请输入分词大小: ");
        int inputPartiSize = 0;
        while(in.hasNextInt()){
            inputPartiSize = in.nextInt();
            break;
        }

        final Map<String,Integer> dictFreq = new HashMap<String,Integer>();
        final int partiSize = inputPartiSize;

        readInput(new LineSolution(){

            public void solveLine(String line){
                String[] lineDicts = lineParser(line, partiSize);
                if(lineDicts != null){
                    for(int i=0; i<lineDicts.length; i++){
                        String word = lineDicts[i];
                        if(dictFreq.containsKey(word)){
                            int num = dictFreq.get(word);
                            dictFreq.put(word, ++num);
                        }
                        else {
                            dictFreq.put(word, 1);
                        }
                    }
                }
            }
        });

        for(Map.Entry<String,Integer> entry : dictFreq.entrySet()){
            print(entry.getKey() + "\t times: " + entry.getValue() + '\n');
        }
    }

    public static void solutionTwo(){
        print("方案二:\n");

        final Map<String,Integer> singleDictFreq = new HashMap<String,Integer>();
        final Map<String,Integer> callResult = new HashMap<String,Integer>();
        callResult.put("sum", 0);

        readInput(new LineSolution(){

            public void solveLine(String line){
                String[] lineDicts = lineParser(line, 1);
                if(lineDicts != null){
                    callResult.put("sum", callResult.get("sum") + lineDicts.length);

                    for(int i=0; i<lineDicts.length; i++){
                        String word = lineDicts[i];
                        if(singleDictFreq.containsKey(word)){
                            int num = singleDictFreq.get(word);
                            singleDictFreq.put(word, ++num);
                        }
                        else {
                            singleDictFreq.put(word, 1);
                        }
                    }
                }
            }
        });

        Map<String,Double> singleDictFreqCalc = new HashMap<String,Double>();

        int sum = callResult.get("sum");
        for(Map.Entry<String,Integer> entry : singleDictFreq.entrySet()){
            singleDictFreqCalc.put(entry.getKey(), divide(entry.getValue(), sum));
        }

        for(Map.Entry<String,Double> entry : singleDictFreqCalc.entrySet()){
            print(entry.getKey() + "\t frequency: " + entry.getValue() + '\n');
        }
    }

    private static String[] lineParser(String line, int scale){
        String[] lineDicts = null;
        if(line != null && !"".equals(line.trim())){
            String[] spliter = line.split("\\s+|,");
            List<String> container = new ArrayList<String>();

            for(int i=0; i<spliter.length; i += scale){
                StringBuilder phase = new StringBuilder("");

                for(int j=0; (i+j) < spliter.length && j<scale; j++){
                    phase.append(spliter[i+j]).append(" ");
                } 

                String phaseStr = phase.toString().trim();
                if("".equals(phaseStr)){
                    continue;
                }
                container.add(phaseStr);
            }
            lineDicts = new String[container.size()];
            container.toArray(lineDicts);
        }
        return lineDicts;
    } 

    private static void readInput(LineSolution solution){
        try{
            String dir = System.getProperty("java.class.path");
            FileReader fr = new FileReader(dir + "/input.txt");
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            while(line != null){
                line = br.readLine();
                if(line == null){
                    break;
                }

                solution.solveLine(line);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static double divide(double divisor, double dividend){
        return new BigDecimal(divisor)
            .divide(new BigDecimal(dividend), 5, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void main(String[] args){

        String Q1 = "1：读取文本信息（input.txt），设置分词大小，输出相应词频信息";
        String Q2 = "2：统计一个单词在文本中的出现频率（一个单词出现次数/总单词数），排序输出结果";

        print(Q1+'\n');
        print(Q2+'\n');
        print("\n");

        print("请输入问题序号: ");
        Scanner in = new Scanner(System.in);

        while(in.hasNextInt()){
            int num = in.nextInt();
            if(num == 1){
                solutionOne();
                break;
            }
            else if(num == 2){
                solutionTwo();
                break;
            }
            else {
                print("\n请输入有效问题序号: ");
            }
        }

    }

    private static void print(String str){
        System.out.print(str);
    }
}

interface LineSolution {
    void solveLine(String line);
}