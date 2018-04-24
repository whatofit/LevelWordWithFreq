package com.myblog.set;

import java.util.Set;

import com.myblog.Constant;
import com.myblog.model.Word;
import com.myblog.util.CfgUtil;

public class Duplicate {

    public Duplicate() {
    }

    public static void main(String[] args) {
        getDuplicate();
    }
    /**
     * 求文件重复单词集合
     * 
     * @param first
     * @param second
     * @return
     */
    public static void getDuplicate() {
        long startTime =System.currentTimeMillis();
        // 1.读取单词集合路径
        String duplicate_file = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "duplicate_file");
        // 2.加载集合
        Set<Word> set1 = WordFilesMgr.loadWord("", duplicate_file.split(","));
        // 4.保存结果集合C到文件
        String result_set_minus = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "resultSetFile");
        WordFilesMgr.saveWordSet(set1, Constant.PATH_RESOURCES + result_set_minus);
        
        long endTime =System.currentTimeMillis();
        System.out.println("执行耗时 : "+(endTime-startTime)/1000f+" 秒 ");
       
        return ;
    }
}
