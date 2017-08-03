package com.myblog.dao;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.myblog.model.Word;

public class WordDaoImpl extends BaseDaoImpl<Word, String>
// implements Dao<Word, String>
{
    private Dao<Word, String> mWordDao;

    public WordDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Word.class);

        // Setup our database and DAOs
        mWordDao = DaoManager.createDao(connectionSource, Word.class);
        // create table
        TableUtils.createTableIfNotExists(connectionSource, Word.class);
    }

    public Vector<String> getTableTitle() {
        Vector<String> titleVector = new Vector<String>(); // headVector/column
                                                           // Names/表头集合
        try {
            // GenericRawResults<String[]> rawResults =
            // wordDao.queryRaw("select * from " + wordDao.getTableName());
            // if (rawResults == null) {
            // System.out.println("rawResults == null");
            // } else {
            // //List<String[]> results = rawResults.getResults();
            // String[] columnNames = rawResults.getColumnNames();
            // titleVector = new Vector<String>(Arrays.asList(columnNames));//
            // array -> vector
            // }
            titleVector.addElement("id");
            titleVector.addElement(Word.FIELD_NAME_FREQUENCY);
            titleVector.addElement(Word.FIELD_NAME_SPELLING);
            titleVector.addElement(Word.FIELD_NAME_DJ);
            titleVector.addElement(Word.FIELD_NAME_KK);
            titleVector.addElement(Word.FIELD_NAME_LEVEL);
            titleVector.addElement(Word.FIELD_NAME_POS);
            titleVector.addElement(Word.FIELD_NAME_MEANINGS);
            titleVector.addElement(Word.FIELD_NAME_SENTENCES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        titleVector.addElement("operate");// todo
        return titleVector;
    }

    public Vector<Vector<Object>> selectAll2Vector() {
        Vector<Vector<Object>> cellsVector = new Vector<Vector<Object>>(); // rowsVector/rows
                                                                           // data/数据体集合
        try {
            // List<Word> wordList = wordDao.queryForAll();
            // List<Word> wordList =
            // wordDao.queryBuilder().orderBy(Word.FIELD_NAME_FREQUENCY,
            // true).query();
            QueryBuilder<Word, String> qb = mWordDao.queryBuilder();
            qb.orderBy(Word.FIELD_NAME_FREQUENCY, true);
            List<Word> wordList = qb.query();
            for (Word dbWord : wordList) {
                // curRow.add(rs.getString(i));//ResultSetMetaData rsmd
                Vector<Object> curRow = new Vector<Object>();
                curRow.addElement(dbWord.getId());
                curRow.addElement(dbWord.getFrequency());
                curRow.addElement(dbWord.getSpelling());
                curRow.addElement(dbWord.getPhoneticDJ());
                curRow.addElement(dbWord.getPhoneticKK());
                curRow.addElement(dbWord.getLevel());
                curRow.addElement(dbWord.getPartsOfSpeech());
                curRow.addElement(dbWord.getMeanings());
                curRow.addElement(dbWord.getSentences());
                cellsVector.addElement(curRow); // rows.add(curRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cellsVector;
    }

    public Vector<Vector<Object>> findBySpelling(String spelling) {
        Vector<Vector<Object>> cellsVector = new Vector<Vector<Object>>();
        try {
            List<Word> wordList = mWordDao.queryForEq(Word.FIELD_NAME_SPELLING, spelling);
            for (Word dbWord : wordList) {
                Vector<Object> curRow = new Vector<Object>();
                curRow.addElement(dbWord.getId());
                curRow.addElement(dbWord.getFrequency());
                curRow.addElement(dbWord.getSpelling());
                curRow.addElement(dbWord.getPhoneticDJ());
                curRow.addElement(dbWord.getPhoneticKK());
                curRow.addElement(dbWord.getLevel());
                curRow.addElement(dbWord.getPartsOfSpeech());
                curRow.addElement(dbWord.getMeanings());
                curRow.addElement(dbWord.getSentences());
                cellsVector.addElement(curRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cellsVector;
    }

    //只更新table中spelling等于word的spelling记录的fieldName字段值
    //分2类操作数据库
    //第1类.词性、词义、例句：每个单词可以有多个词性词义及例句,添加或修改某条记录，针对3个字段，要同时进行，不能单独对一个字段操作
    //第1类.词频:每个单词只有一个词频;stage:每个单词在一个stage阶段中，只有一个stage值，比如：是否属于高中词汇：是或否
    public int createOrUpdate(final Collection<Word> vecWords, String fieldName) {
        try {
            // 批处理
            int numLinesChanged = mWordDao.callBatchTasks(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    // int numLinesCreated = 0;
                    // int numLinesUpdated = 0;
                    int numLinesChanged = 0;
                    for (Word curWord : vecWords) {
                        if (curWord == null) { continue; }
                        try {
                            Class<?> cWord = curWord.getClass();
                            //获取name 字段
                            Field nameField = cWord.getDeclaredField(fieldName) ;
                            //打破封装 ,设置是否允许访问，因为该变量是private的，所以要手动设置允许访问，如果fieldName是public的就不需要这行了。
                            nameField.setAccessible(true);
                            // 获取 nameField 的值
                            String curFieldValue = (String) nameField.get(curWord);
//                            System.out.println("createOrUpdate,freq:" +freq);
                            // Method method = cWord.getMethod(methodName);//获取方法
                            // String mFieldValue = (String) method.invoke(curWord);
                            //List<Word> wordList = mWordDao.queryBuilder().where().eq(Word.FIELD_NAME_SPELLING, escapeSql(curWord.getSpelling())).and().eq(fieldName, nameField.get(curWord)).query();
//                            List<Word> wordList = mWordDao.queryBuilder().where().eq(Word.FIELD_NAME_SPELLING, escapeSql(curWord.getSpelling())).query();
//                            List<Word> wordList = mWordDao.queryForEq(Word.FIELD_NAME_SPELLING, escapeSql(curWord.getSpelling()));
                            QueryBuilder<Word, String> qb =  mWordDao.queryBuilder();
                            Where<Word, String> where = qb.where();
                            where.eq(Word.FIELD_NAME_SPELLING, escapeSql(curWord.getSpelling()));
                            if (!StringUtils.isBlank(curFieldValue)) {
                                if(Word.FIELD_NAME_POS.equals(fieldName) || Word.FIELD_NAME_MEANINGS.equals(fieldName) || Word.FIELD_NAME_SENTENCES.equals(fieldName)) {//相同的spelling，可以有多个词性、词义及例句
                                    where.and();
                                    where.eq(fieldName, curFieldValue);
                                }
                            }
                            List<Word> wordList = where.query();
                            int numRows = 0;
                            //创建时，包括word的所有字段都会创建
                            if (wordList == null || wordList.size() == 0) {
                                numRows = create(curWord);
                                numLinesChanged += numRows;
                            } else {
                                if(Word.FIELD_NAME_POS.equals(fieldName)) {//相同的spelling，可以有多个词性
                                    boolean isFound = false; // 是否在数据库中已找到相同记录
                                    int idx = 0;
                                    while (idx < wordList.size()) {
                                        Word dbWord = wordList.get(idx);
                                        String dbFieldValue = (String) nameField.get(dbWord);
                                        if (StringUtils.isBlank(dbFieldValue) && !StringUtils.isBlank(curFieldValue)) {//本单词第一个词性/词义或例句为空时，把令两个同时更新
                                            //第1类.词性、词义、例句,要同时进行修改
                                            //nameField.set(dbWord, curFieldValue);
                                            dbWord.setPartsOfSpeech(curWord.getPartsOfSpeech());
                                            dbWord.setMeanings(curWord.getMeanings());
                                            dbWord.setSentences(curWord.getSentences());
                                            numRows = update(dbWord);
                                            isFound = true;
                                            break;
                                        } else if (StringUtils.equals(curFieldValue, dbFieldValue)) {// StringUtils.isBlank(dbFieldValue)
                                            isFound = true;
                                            break;
                                        }
                                        idx++;
                                    }
                                    if (!isFound) { // 找到列表最后，也没有找到相同记录，做就create/insert操作
                                        numRows = create(curWord);
                                        numLinesChanged += numRows;
                                    }
                                } else {
                                    if (wordList.size() == 1) { //相同的spelling，对于词频或stage只能有一个记录
                                        Word dbWord = wordList.get(0);
                                        String dbFieldValue = (String) nameField.get(dbWord);
                                        if (!StringUtils.equals(curFieldValue, dbFieldValue)) {
                                            nameField.set(dbWord, curFieldValue);// 只更新指定的字段，沒有指定的字段不处理，每次只更新一个字段
                                            numRows = update(dbWord);
                                            numLinesChanged += numRows;
                                        }
                                    } else {

                                    }
                                }
//                                

//                            } else {
//                                boolean isFound = false; //是否在数据库中已找到本记录
//                                String curFieldValue = (String)nameField.get(curWord);
//                                int idx = 0;
//                                while(idx < wordList.size()) {
//                                    Word dbWord = wordList.get(idx);
//                                    String dbFieldValue = (String)nameField.get(dbWord);
//                                    if (!StringUtils.equals(curFieldValue, dbFieldValue)) {//StringUtils.isBlank(dbFieldValue) || 
//                                        isFound = true;
//                                        nameField.set(dbWord, curFieldValue);//只更新指定的字段，沒有指定的字段不处理，每次只更新一个字段
//                                        numRows = update(dbWord);
//                                        numLinesChanged += numRows;
//                                    }
//                                    idx++;
//                                }
//                                if (idx == wordList.size() && !isFound) { //找到列表最后，也没有找到记录，做就create/insert操作
//                                    numRows = create(curWord);
//                                    numLinesChanged += numRows;
//                                }
                            }
                            // System.out.println("createOrUpdate,numRows:" +
                            // numRows);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    return numLinesChanged;
                }
            });
            return numLinesChanged;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("'", "''");
    }
}
