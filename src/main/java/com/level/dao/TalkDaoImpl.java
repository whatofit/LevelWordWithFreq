package com.level.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.level.model.Talk;

public class TalkDaoImpl extends BaseDaoImpl<Talk, String>
// implements Dao<Talk, String>
{
    private Dao<Talk, String> talkDao;

    public TalkDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Talk.class);

        // Setup our database and DAOs
        talkDao = DaoManager.createDao(connectionSource, Talk.class);
        // create table
        TableUtils.createTableIfNotExists(connectionSource, Talk.class);
    }

    public Vector<String> getTableTitle() {
        Vector<String> titleVector = new Vector<String>(); // headVector/column
                                                           // Names/表头集合
        try {
            titleVector.addElement("id");
            titleVector.addElement(Talk.FIELD_NAME_ENGLISH_TITLE);
            titleVector.addElement(Talk.FIELD_NAME_CHINESE_TITLE);
            titleVector.addElement(Talk.FIELD_NAME_CATEGORY);
            titleVector.addElement(Talk.FIELD_NAME_OCCASION);
            titleVector.addElement(Talk.FIELD_NAME_LEVEL);
            titleVector.addElement(Talk.FIELD_NAME_TALK_TEXT);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        titleVector.addElement("operate");// todo
        return titleVector;
    }

    public Vector<Vector<Object>> selectAll2Vector() {
        Vector<Vector<Object>> cellsVector = new Vector<Vector<Object>>(); // rowsVector/rows
                                                                           // data/数据体集合
        try {
            // List<Talk> talkList = talkDao.queryForAll();
            // List<Talk> talkList = talkDao.queryBuilder().orderBy(Talk.FIELD_NAME_ENGLISH_TITLE, true).query();
            QueryBuilder<Talk, String> qb = talkDao.queryBuilder();
            qb.orderBy(Talk.FIELD_NAME_ENGLISH_TITLE, true);
            List<Talk> talkList = qb.query();
            for (Talk dbTalk : talkList) {
                // curRow.add(rs.getString(i));//ResultSetMetaData rsmd
                Vector<Object> curRow = new Vector<Object>();
                curRow.addElement(dbTalk.getId());
                curRow.addElement(dbTalk.getEnglishTitle());
                curRow.addElement(dbTalk.getChineseTitle());
                curRow.addElement(dbTalk.getCategory());
                curRow.addElement(dbTalk.getOccasion());
                curRow.addElement(dbTalk.getLevel());
                curRow.addElement(dbTalk.getTalkText());
                
                cellsVector.addElement(curRow); // rows.add(curRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cellsVector;
    }

    //只查找talk
    public Vector<Vector<Object>> findBySpelling(String englishTitle) {
        Vector<Vector<Object>> cellsVector = new Vector<Vector<Object>>();
        try {
            List<Talk> talkList = talkDao.queryForEq(Talk.FIELD_NAME_ENGLISH_TITLE, englishTitle);
            for (Talk dbTalk : talkList) {
                Vector<Object> curRow = new Vector<Object>();
                curRow.addElement(dbTalk.getId());
                curRow.addElement(dbTalk.getEnglishTitle());
                curRow.addElement(dbTalk.getChineseTitle());
                curRow.addElement(dbTalk.getCategory());
                curRow.addElement(dbTalk.getOccasion());
                curRow.addElement(dbTalk.getLevel());
                curRow.addElement(dbTalk.getTalkText());
                cellsVector.addElement(curRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cellsVector;
    }
}
