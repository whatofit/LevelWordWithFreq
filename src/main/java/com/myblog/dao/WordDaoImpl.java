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
                        if (curWord == null) {
                            continue;
                        }
                        try {
                            Class<?> cWord = curWord.getClass();
                            //获取name 字段
                            Field nameField = cWord.getDeclaredField(fieldName) ;
                            //打破封装 ,设置是否允许访问，因为该变量是private的，所以要手动设置允许访问，如果fieldName是public的就不需要这行了。
                            nameField.setAccessible(true);
                            // 获取 nameField 的值
//                            int freq = (int) nameField.get(cWord);
//                            System.out.println("createOrUpdate,freq:" +freq);
                            //int freq = (int) nameField.get(nameField);//获取静态变量值
                            // Method method = cWord.getMethod(methodName);//获取方法
                            // int mFieldValue = (int) method.invoke(curWord);
                            //List<Word> wordList = mWordDao.queryBuilder().where().eq(Word.FIELD_NAME_SPELLING, escapeSql(curWord.getSpelling())).and().eq(fieldName, nameField.get(curWord)).query();
                            List<Word> wordList = mWordDao.queryBuilder().where().eq(Word.FIELD_NAME_SPELLING, escapeSql(curWord.getSpelling())).query();
//                            List<Word> wordList = mWordDao.queryForEq(Word.FIELD_NAME_SPELLING,
//                                    escapeSql(curWord.getSpelling()));
                            int numRows = 0;
                            if (wordList == null || wordList.size() == 0) {
                                numRows = create(curWord);
                                //numLinesCreated +=numRows;
                                numLinesChanged += numRows;
                            } else {
                                Word dbWord = wordList.get(0);
                                String dbFieldValue = (String)nameField.get(dbWord);
                                String curFieldValue = (String)nameField.get(curWord);
                                if (!StringUtils.equals(curFieldValue, dbFieldValue)) {
                                    nameField.set(dbWord, curFieldValue);//只更新指定的字段，沒有指定的字段不处理
                                    numRows = update(dbWord);
                                    //numLinesUpdated +=numRows;
                                    numLinesChanged += numRows;
                                }
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
