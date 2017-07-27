package com.myblog.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

//talk
//conversation
//dialogue
//(多人)情景对话/情景会话:：
//比如A,B,C,D 四人对话
//D,sents
//C,sents
//A,sents
//C,sents
//B,sents

/*
 * 对话例子
{
    "category": "分类/种类：时尚购物/讨价还价",
    "occasion": "场合:商场",
    "EnglishTitle": "I’m looking for a bookcase",
    "ChineseTitle": "标题:我想买个书架",
    "talk": [
        {
            "person": "A",
            "sentences": [
                {
                    "English": "Why are you reading the classifieds? What do you need?",
                    "Chinese": "你为什么在看这些分类广告？你需要买什么？"
                }
            ]
        },
        {
            "person": "B",
            "sentences": [
                {
                    "English": "I’m looking for a bookcase, but I don’t want to buy a new one.",
                    "Chinese": "我想找个书架，但是我不想要一个新的。"
                }
            ]
        }
    ]
}
 * */

@DatabaseTable(tableName = "levelTalks")
public class Talk {
    // for QueryBuilder to be able to find the fields
    public static final String FIELD_NAME_ENGLISH_TITLE = "EnglishTitle";
    public static final String FIELD_NAME_CHINESE_TITLE = "ChineseTitle";
    public static final String FIELD_NAME_CATEGORY = "category";
    public static final String FIELD_NAME_OCCASION = "occasion";
    public static final String FIELD_NAME_LEVEL = "level";
    public static final String FIELD_NAME_TALK_TEXT = "talkText";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_NAME_ENGLISH_TITLE, canBeNull = false)
    private String englishTitle;

    @DatabaseField(columnName = FIELD_NAME_CHINESE_TITLE)
    private String chineseTitle;

    @DatabaseField(columnName = FIELD_NAME_CATEGORY)
    private String category;

    //, DbType="NVarChar(20) NOT NULL"
    @DatabaseField(columnName = FIELD_NAME_OCCASION)
    private String occasion;

    @DatabaseField(columnName = FIELD_NAME_LEVEL)
    private String level;

    @DatabaseField(columnName = FIELD_NAME_TALK_TEXT)
    private String talkText;// persion/English/Chinese...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }

    public String getChineseTitle() {
        return chineseTitle;
    }

    public void setChineseTitle(String chineseTitle) {
        this.chineseTitle = chineseTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTalkText() {
        return talkText;
    }

    public void setTalkText(String talkText) {
        this.talkText = talkText;
    }

    @Override
    public int hashCode() {
        String key = talkText;
        if (englishTitle != null) {
            key = key + englishTitle;
        }
        return key.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        Talk oTalk = (Talk) other;
        if (talkText.equals(oTalk.talkText)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("category = " + category);
        sb.append(",occasion =" + occasion);
        sb.append(",EnglishTitle =" + englishTitle);
        sb.append(",ChineseTitle =" + chineseTitle);
        sb.append(",talkText =" + talkText);
        return sb.toString();
    }
}
