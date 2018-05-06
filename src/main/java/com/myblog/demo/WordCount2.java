package com.myblog.demo;
//package com.myblog.statistics;
//
////JDK8一句话实现MapReduce WordCount
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Paths;
//import java.util.AbstractMap;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//import java.util.function.BinaryOperator;
//import java.util.function.ToIntFunction;
//
//import com.google.common.io.Files;
//
//public class WordCount2 {
//
//    public WordCount2() {
//        // TODO Auto-generated constructor stub
//    }
//
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//
//    }
//    //txt统计单词数量程序
//    public void fileWordCount() throws IOException {
//        
////        static int tmp_count = 0;
////
////        Files.readAllLines(Paths.get("D:\\tmp\\CDMI.txt"))
////                        .stream()
////                        .flatMap(x -> Stream.of(x.trim().split(" ")))
////                        .sorted()
////                        .reduce((x, y) -> {
////                            if (x.equals(y)) {
////                                tmp_count += 1;
////                            } else {
////                                System.out.println(x + "," + tmp_count);
////                                tmp_count = 1;
////                            }
////                            return y;
////                        });
//        
//        //特殊文件需要格式转换为txt
//        Files.readAllLines(Paths.get("D:\\jd.txt"), StandardCharsets.UTF_8).parallelStream()
//                //将多个流融合为一个
//                .flatMap(line -> Arrays.stream(line.trim().split("\\s")))
//                .filter(word -> word.length() > 0)
//                .map(word -> new AbstractMap.SimpleEntry<>(word, 1))
//                .collect(groupingBy(AbstractMap.SimpleEntry :: getKey, counting()))
//                .entrySet().forEach(System.out :: println);
//    }
//    
//    //List统计单词数量程序
//    public void listWordCount(){
//        List<String> stringList = Arrays.asList("a","b","c","a");
//        stringList.stream()
//                .map(s -> new AbstractMap.SimpleEntry<>(s, 1))
//                .collect(groupingBy(AbstractMap.SimpleEntry :: getKey, counting()))
//                .entrySet().stream()
//                .forEach(System.out :: println);
//        System.out.println("---------------------------------------------------");
//        //通过自定义reduce统计，其实counting（）也使用的是reduce
//        //记住：凡是在中间操作使用了map，接口定义都需要声明出来，直接使用lambda表达式会有1.无法读取method，2.类型检查不到 的问题
//        BinaryOperator<Integer> binaryOperator2 = Integer::sum;
//        //排序的转换规则接口
//        ToIntFunction<Map.Entry> sortMapFunction = (Map.Entry se) -> Integer.valueOf(se.getValue().toString()).intValue();
//        stringList
//                .stream()
//                .map(s -> new AbstractMap.SimpleEntry<>(s, 1))
//                .collect(groupingBy(AbstractMap.SimpleEntry::getKey,
//                        reducing(0, AbstractMap.SimpleEntry::getValue,binaryOperator2)))
//                .entrySet()
//                .stream()
//                .sorted(Comparator.comparingInt(sortMapFunction))
//                .forEach(System.out::println);
//    }
//}
