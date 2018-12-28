package com.level.download;

import java.util.List;

import com.level.Constant;
import com.level.util.HttpUtil;
import com.level.util.ParseHtmlUtil;
import com.level.util.ResourceUtil;

//下载在线词典,如：
//http://dict.cn/review
//http://www.iciba.com/dirty
//# https://cn.bing.com/dict/search?q=dirty
//# http://dict.youdao.com/w/dirty
public class DownloadWords_iCIBA {
//    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadWords_iCIBA.class);


    public DownloadWords_iCIBA() {
    }

    public static void main(String[] args) {
        // http://www.iciba.com/dirty
        String downloa_host_url    = "http://www.iciba.com";    //下载单词网址
        String download_words_file = "/vocabulary_all_20180426.txt";    //待下载的单词列表文件
        String download_save_path  = "/vocabulary_ciba2";       //保存已下载的单词文件的文件夹
        String download_save_file  = "/vocabulary_ciba2.txt";   //保存已下载的单词列表的文件

        // 读取单词列表，下载并过滤考级单词保存
        List<String> lines = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + download_words_file);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (!line.startsWith("#") && !"".equals(line)) {
                String[] segments = line.split("\\s+");// 以空白(空格/tab键/回车/换行)分割
                String spelling = "";
                // String freq = "99999";// 默认较大的频率
                if (segments.length <= 1) {
                    spelling = segments[0];
                } else {
                    spelling = segments[1];
                    // freq = segments[0];
                }
                String wordFileName = Constant.PATH_RESOURCES + download_save_path + "/" + spelling + ".html";
                String htmlTxt = HttpUtil.sendPost(downloa_host_url + "/" + spelling, null, false);// spelling.toLowerCase()
                String wordLevels = ParseHtmlUtil.parseIcibaBaseLevel(htmlTxt);
                if (!"".equals(wordLevels)) {
                    ResourceUtil.writerFile(wordFileName, htmlTxt, false);
                    ResourceUtil.writerFile(Constant.PATH_RESOURCES + download_save_file, spelling + "\t" + wordLevels,
                            true);
                }
            }
        }
        System.out.println("done!");
    }
}
