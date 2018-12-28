package com.level.model;

import org.apache.commons.lang3.StringUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 词组/短语/固定搭配表
 */
@DatabaseTable(tableName = "levelPhrases")
public class Phrase {
    // for QueryBuilder to be able to find the fields
    public static final String FIELD_NAME_PHRASE = "phrase";
    public static final String FIELD_NAME_MEANINGS = "meanings";
    public static final String FIELD_NAME_LEVEL = "level";
    public static final String FIELD_NAME_SENTENCES = "sentences";// example
                                                                  // sentences

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_NAME_PHRASE, canBeNull = false)
    private String phrase;

    @DatabaseField(columnName = FIELD_NAME_LEVEL)
    private String level;

    @DatabaseField(columnName = FIELD_NAME_MEANINGS)
    private String meanings;

    @DatabaseField(columnName = FIELD_NAME_SENTENCES, width = 4096)
    private String sentences;

    public Phrase() {
        // all persisted classes must define a no-arg constructor with at least
        // package visibility
    }

    public Phrase(String phrase) {
        this.phrase = phrase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMeanings() {
        return meanings;
    }

    public void setMeanings(String meanings) {
        this.meanings = meanings;
    }

    public String getSentences() {
        return sentences;
    }

    public void setSentences(String sentences) {
        this.sentences = sentences;
    }

    @Override
    public int hashCode() {
        String key = phrase == null ? "" : phrase;
        return key.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        Phrase oPhrase = (Phrase) other;
        return StringUtils.equals(phrase, oPhrase.phrase);
    }

    @Override
    public String toString() {
        return "id=" + id + ",phrase=" + phrase + ",level" + level
                + ", meanings=" + meanings + ", sentences=" + sentences;
    }
}
