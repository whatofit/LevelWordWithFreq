package com.myblog.download;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.myblog.Constant;
import com.myblog.util.HttpUtil;
import com.myblog.util.ParseHtmlUtil;
import com.myblog.util.ResourceUtil;

//下载在线词典,如：
//http://dict.cn/review
//http://www.iciba.com/dirty
//# https://cn.bing.com/dict/search?q=dirty
//# http://dict.youdao.com/w/dirty
public class DownlaodWords_Dict {
    // private static final Logger LOGGER =
    // LoggerFactory.getLogger(DownlaodWords_Dict.class);

    public DownlaodWords_Dict() {
    }

    // 解析Word level，如： layer 考研/CET6/CET4/TOEFL/IELTS
    // 返回固定的6列：CET4 CET6 考研 IELTS TOEFL GRE
    public static String parseWordLevel(String wordAllLevels) {
        String[] subLevels = { "CET4", "CET6", "考研", "IELTS", "TOEFL", "GRE" };
        for (int i = 0; i < subLevels.length; i++) {
            Pattern p = Pattern.compile(subLevels[i]);
            Matcher m = p.matcher(wordAllLevels);
            if (m.find()) {
                subLevels[i] = "1";
            } else {
                subLevels[i] = "";
            }

        }
        return String.join("\t", subLevels);
    }

    public static void main(String[] args) {
        // http://dict.cn/dirty
        String downloa_host_url = "http://dict.cn"; // 下载单词网址
        String download_words_file = "/vocabulary_ciba2.txt"; // 待下载的单词列表文件
        String download_save_path = "/vocabulary_dict"; // 保存已下载的单词文件的文件夹
        String download_save_file = "/vocabulary_dict.txt"; // 保存已下载的单词列表的文件

        // 读取单词列表，下载并过滤考级单词保存
        List<String> lines = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + download_words_file);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (!line.startsWith("#") && !"".equals(line)) {
                String[] segments = line.split("\\s+");// 以空白(空格/tab键/回车/换行)分割
                String spelling = segments[0];
                String htmlTxt = HttpUtil.sendPost(downloa_host_url + "/" + spelling, null, false);// spelling.toLowerCase()
                String[] dictWordCotent = ParseHtmlUtil.parseDictWordContent(htmlTxt);
                String wordLine = spelling + "\t"+ dictWordCotent[1] + "\t"+ dictWordCotent[3] + "\t"+parseWordLevel(segments[1]) ;
                ResourceUtil.writerFile(Constant.PATH_RESOURCES + download_save_path + File.separator + spelling + ".html", htmlTxt, false);
                ResourceUtil.writerFile(Constant.PATH_RESOURCES + download_save_file, wordLine, true);
            }
        }
        System.out.println("done!");

        // String url = "http://www.iciba.com";
        // String url = "http://dict.cn";
        // String spelling = "take";
        // String spelling = "make";
        // String sr = HttpUtil.sendPost(url + "/" + spelling, null, false);//
        // spelling.toLowerCase()
        // String level = parseBaseLevel(sr);
        // if (!"".equals(level)) {
        // ResourceUtil.writerFile(Constant.PATH_DICT + File.separator +
        // spelling + ".html", sr, false);
        // String shapes = parseChangeShape(sr);
        // ResourceUtil.writerFile(Constant.PATH_RESOURCES +
        // "/word_level_dict.txt", spelling + "\t" + level +"\t" + shapes,
        // true);
        // }

        // String url = "http://dict.cn/dir/base5-0.html";
        // String sr = HttpUtil.sendPost(url, null, false);//
        // spelling.toLowerCase()
        // ResourceUtil.writerFile(Constant.PATH_DICT + File.separator +
        // "base5-0.html", sr, false);
    }
}
