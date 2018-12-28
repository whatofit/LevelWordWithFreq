package com.level;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Constant {
    /**
     * 
     */
    public Constant() {
    }

    public static String PROJECT_BIN_DIR;
    static {
        try {
            URL URL_PROJECT_BIN_DIR = ClassLoader.getSystemResource("");
            PROJECT_BIN_DIR = Paths.get(URL_PROJECT_BIN_DIR.toURI()).toAbsolutePath().toString() + File.separator;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public static final String mUserDir = System.getProperty("user.dir");
    public static final String PATH_MYBLOG       = "src/main/java/com/myblog";
    public static final String PATH_RESOURCES       = "src/main/resources";
//    public static final String FOLDER_STAGE       = "/stageFiles";
    public static final String PATH_STAGE           = PATH_RESOURCES + "/stageFiles";
    public static final String PATH_CIBA            = PATH_RESOURCES + "/vocabulary_ciba";
    public static final String PATH_CIBA2           = PATH_RESOURCES + "/vocabulary_ciba2";
    public static final String PATH_DICT            = PATH_RESOURCES + "/vocabulary_dict";
    public static final String PATH_DICT_STAR       = PATH_RESOURCES + "/vocabulary_dict_star";
    
    
    public static final String PATH_QQ              = PATH_RESOURCES + "/vocabulary_QQ";
    public static final String FILE_CONFIG_FILE     = PATH_RESOURCES + "/config.properties";
    public static final String FILE_FREQ_OF_WORDS   = PATH_RESOURCES + "/freqOfWords.txt";
    public static final String FILE_STAGE_FILES     = PATH_RESOURCES + "/stageWordsFiles.txt";

    // public static final String PROJECT_BIN_DIR =
    // Paths.get().toAbsolutePath().toString();

    // public static final String PATH_STAGE_FILES = PROJECT_BIN_DIR +
    // "stageFiles";
    // public static final String FILE_FREQ_OF_WORDS = PROJECT_BIN_DIR +
    // "freqOfWords.txt";
    //
    // // ClassLoader.getSystemResource(stageWordsFiles.txt)
    // public static final String FILE_STAGE_WORDS_FILES = PROJECT_BIN_DIR +
    // "stageWordsFiles.txt";

    // String databaseUrl = "jdbc:h2:./LevelDict.h2";
    public static final String DB_URL = "jdbc:sqlite:";
    // "./LevelDict.db3";"D:/my/testDB.db3";
    public static final String DB_NAME = "/LevelDict.db3";
    //public final static String URL_DATABASE = Constant.DB_URL + ClassLoader.getSystemResource("").getFile() + Constant.DB_NAME;
    public final static String URL_DATABASE = Constant.DB_URL + PATH_RESOURCES + Constant.DB_NAME;

    /**
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println("DumpANC2FreqWordDB,mUserDir:" + mUserDir);
//        System.out.println("DumpANC2FreqWordDB,PROJECT_BIN_DIR:" + PROJECT_BIN_DIR);
        System.out.println("main,FILE_FREQ_OF_WORDS: " + FILE_FREQ_OF_WORDS);
        System.out.println("main,FILE_STAGE_FILES: " + FILE_STAGE_FILES);
        
        System.out.println("main,URL_DATABASE: " + URL_DATABASE);
    }
}
