package com.myblog.statistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA. User: FLY Date: 11-9-13 Time: 下午3:59 To change this
 * template use File | Settings | File Templates.
 */
public class FileWordCount {
    public FileWordCount() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("E:/workspace_4.6.3_LevelWord_2017-07-26/LevelWordWithFreq/src/main/resources/WordStatistics.txt"));
            String s;
            StringBuffer sb = new StringBuffer();
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            Map<String, Integer> map = new HashMap<String, Integer>();
            StringTokenizer st = new StringTokenizer(sb.toString(), ",.! \n");
            while (st.hasMoreTokens()) {
                String letter = st.nextToken();
                int count;
                if (map.get(letter) == null) {
                    count = 1;
                } else {
                    count = map.get(letter).intValue() + 1;
                }
                map.put(letter, count);
            }
            Set<WordEntity> set = new TreeSet<WordEntity>();
            for (String key : map.keySet()) {
                set.add(new WordEntity(key, map.get(key)));
            }
            // 自己拼接字符串，输出我们想要的字符串格式
            System.out.println("输出形式一：");
            for (Iterator<WordEntity> it = set.iterator(); it.hasNext();) {
                WordEntity w = it.next();
                System.out.println("单词:" + w.getKey() + " 出现的次数为： " + w.getCount());
            }
            // 直接打印 WordEntity 对象，实现我们想要的输出效果，只需在WordEntity类中重写toString()方法
            System.out.println("输出形式二：");
            for (Iterator<WordEntity> it = set.iterator(); it.hasNext();) {
                WordEntity w = it.next();
                System.out.println(w);
            }
            // 我们可以控制只输出前三名来
            System.out.println("输出形式三：");
            int count = 1;
            for (Iterator<WordEntity> it = set.iterator(); it.hasNext();) {
                WordEntity w = it.next();
                System.out.println("第" + count + "名为单词:" + w.getKey() + " 出现的次数为： " + w.getCount());
                if (count == 3)// 当输出3个后跳出循环
                    break;
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到~！");
        } catch (IOException e) {
            System.out.println("文件读异常~！");
        }
    }
}
