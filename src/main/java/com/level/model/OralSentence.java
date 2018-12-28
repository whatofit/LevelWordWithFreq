package com.level.model;


import org.apache.commons.lang3.StringUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 句子/常用口语表:800句
 */
@DatabaseTable(tableName = "oralSentence")
public class OralSentence {
    // for QueryBuilder to be able to find the fields
    public static final String FIELD_NAME_SENTENCE = "sentence";
    public static final String FIELD_NAME_MEANING = "meaning";
    public static final String FIELD_NAME_LEVEL = "level";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_NAME_SENTENCE, canBeNull = false)
    private String sentence;

    @DatabaseField(columnName = FIELD_NAME_LEVEL)
    private String level;

    @DatabaseField(columnName = FIELD_NAME_MEANING)
    private String meaning;


    public OralSentence() {
        // all persisted classes must define a no-arg constructor with at least
        // package visibility
    }

    public OralSentence(String sentence) {
        this.sentence = sentence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getLevel() {
    return level;
}

public void setLevel(String level) {
    this.level = level;
}
    @Override
    public int hashCode() {
        String key = sentence == null ? "" : sentence;
        return key.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        OralSentence oSentence = (OralSentence) other;
        return StringUtils.equals(sentence, oSentence.sentence);
    }

    @Override
    public String toString() {
        return "id=" + id + ",sentence=" + sentence + ",level" + level
                + ", meaning=" + meaning;
    }
}