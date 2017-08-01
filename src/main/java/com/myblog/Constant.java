package com.myblog;

public class Constant {
    public static String mUserDir = System.getProperty("user.dir");
    public static final String PATH_STAGE_FILES = "stageFiles";
    public static final String FILE_FREQ_OF_WORDS = "freqOfWords.txt";
    public final static String FILE_STAGE_WORDS_FILES = "stageWordsFiles.txt";

    // String databaseUrl = "jdbc:h2:./LevelDict.h2";
    public final static String URL_DATABASE = "jdbc:sqlite:/LevelDict.db3";

    /**
     * @param args
     */
    public static void main(String[] args) {
        // System.out.println("DumpANC2FreqWordDB,mUserDir:" + mUserDir);
        System.out.println("main,mStageFileList: " + FILE_STAGE_WORDS_FILES);
    }
}
