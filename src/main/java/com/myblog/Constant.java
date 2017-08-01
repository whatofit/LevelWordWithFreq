package com.myblog;

public class Constant {
    public static final String mUserDir = System.getProperty("user.dir");
    public static final String PATH_STAGE_FILES = "stageFiles";
    public static final String FILE_FREQ_OF_WORDS = "freqOfWords.txt";
    public static final String FILE_STAGE_WORDS_FILES = "stageWordsFiles.txt";

    // public static final String PROJECT_BIN_DIR =
    // ClassLoader.getSystemResource("").getFile();
    // public static final String PATH_STAGE_FILES = PROJECT_BIN_DIR +
    // "stageFiles";
    // public static final String FILE_FREQ_OF_WORDS = PROJECT_BIN_DIR +
    // "freqOfWords.txt";
    //
    // //ClassLoader.getSystemResource(stageWordsFiles.txt)
    // public static final String FILE_STAGE_WORDS_FILES = PROJECT_BIN_DIR +
    // "stageWordsFiles.txt";

    // String databaseUrl = "jdbc:h2:./LevelDict.h2";
    public static final String DB_URL = "jdbc:sqlite:";
    // "./LevelDict.db3";"D:/my/testDB.db3";
    public static final String DB_NAME = "LevelDict.db3";
    public final static String URL_DATABASE = Constant.DB_URL + ClassLoader.getSystemResource("").getFile()
            + Constant.DB_NAME;

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("DumpANC2FreqWordDB,mUserDir:" + mUserDir);
        // System.out.println("DumpANC2FreqWordDB,PROJECT_BIN_DIR:" +
        // PROJECT_BIN_DIR);
        // System.out.println("main,mStageFileList: " + FILE_STAGE_WORDS_FILES);
    }
}
