/**
 * 
 */
package com.myblog.model;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Administrator
 * 
 *         单词表/词汇表
 */
@DatabaseTable(tableName = "levelWords")
public class Word implements Comparable {
	// for QueryBuilder to be able to find the fields
	public static final String FIELD_NAME_FREQUENCY = "frequency";
	public static final String FIELD_NAME_SPELLING = "spelling";
	public static final String FIELD_NAME_DJ = "phoneticDJ";
	public static final String FIELD_NAME_KK = "phoneticKK";
	public static final String FIELD_NAME_LEVEL = "level";
	public static final String FIELD_NAME_POS = "partsOfSpeech";
	public static final String FIELD_NAME_MEANINGS = "meanings";
	public static final String FIELD_NAME_SENTENCES = "sentences";// exampleSentences

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = FIELD_NAME_FREQUENCY)
	private int frequency;

	@DatabaseField(columnName = FIELD_NAME_SPELLING, canBeNull = false)
	private String spelling;

	@DatabaseField(columnName = FIELD_NAME_DJ)
	private String phoneticDJ;

	@DatabaseField(columnName = FIELD_NAME_KK)
	private String phoneticKK;

	@DatabaseField(columnName = FIELD_NAME_LEVEL)
	private String level;

	@DatabaseField(columnName = FIELD_NAME_POS)
	private String partsOfSpeech;

	@DatabaseField(columnName = FIELD_NAME_MEANINGS)
	private String meanings;

	@DatabaseField(columnName = FIELD_NAME_SENTENCES, width = 4096)
	private String sentences;

	public Word() {
		// all persisted classes must define a no-arg constructor with at least
		// package visibility
	}

	public Word(String spelling) {
		this.spelling = spelling;
	}

	public Word(int frequency, String spelling) {
		this.frequency = frequency;
		this.spelling = spelling;
	}

	public Word(int frequency, String spelling, String level) {
		this.frequency = frequency;
		this.spelling = spelling;
		this.level = level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getSpelling() {
		return spelling;
	}

	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

	public String getPhoneticDJ() {
		return phoneticDJ;
	}

	public void setPhoneticDJ(String phoneticDJ) {
		this.phoneticDJ = phoneticDJ;
	}

	public String getPhoneticKK() {
		return phoneticKK;
	}

	public void setPhoneticKK(String phoneticKK) {
		this.phoneticKK = phoneticKK;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPartsOfSpeech() {
		return partsOfSpeech;
	}

	public void setPartsOfSpeech(String partsOfSpeech) {
		this.partsOfSpeech = partsOfSpeech;
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
		String key = spelling;
		if (partsOfSpeech != null) {
			key = key + partsOfSpeech;
		}
		return key != null ? key.hashCode() : 0;
	}

	@Override
	public boolean equals(Object other) {
		// if (this == other) return true;
		// if (!(o instanceof Word)) return false;
		if (other == null || other.getClass() != getClass()) {
			return false;
		}
		Word oWord = (Word) other;
		if (spelling.equals(oWord.spelling)) {
			if (StringUtils.isBlank(partsOfSpeech) && StringUtils.isBlank(oWord.partsOfSpeech)) {
				return true;
			} else {
				if (StringUtils.equals(partsOfSpeech, oWord.partsOfSpeech)) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "id=" + id + ",frequency=" + frequency + ",spelling" + spelling + ", partsOfSpeech=" + partsOfSpeech;
	}

	// public String toString(int i) {
	// return "id: " + id + "\n"
	// + "name: " + name + "\n"
	// + "address: " + address + "\n"
	// + "gender: " + (gender ? "Male" : "Female")
	// + "\n\n";
	// }

	@Override
	public int compareTo(Object o) {
		if (this == o) {
			return 0;
		}
		if (this.spelling == null) {
			return -1;
		}
		if (o == null) {
			return 1;
		}
		if (!(o instanceof Word)) {
			return 1;
		}
		String t = ((Word) o).getSpelling();
		if (t == null) {
			return 1;
		}
		return this.spelling.compareToIgnoreCase(t);
	}

	// 先按照frequency排序，若frequency相等，再按照word排序
	public static final Comparator<Word> m_byFreqAndLevel = (lw, rw) -> {
		// return lw.getFrequency() - rw.getFrequency();
		if (lw.getFrequency() == rw.getFrequency())
			return lw.getSpelling().compareTo(rw.getSpelling());
		else
			return lw.getFrequency() - rw.getFrequency();
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

}
