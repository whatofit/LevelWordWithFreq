/**
 *
 */
package com.level.model;

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
public class Word implements Comparable<Word> {
    // for QueryBuilder to be able to find the fields
    public static final String FIELD_NAME_FREQUENCY = "frequency";
    public static final String FIELD_NAME_FREQ_WRITTEN = "freqWritten";
    public static final String FIELD_NAME_FREQ_SPOKEN = "freqSpoken";
    public static final String FIELD_NAME_COUNT_IN_ARTICLE = "countInArticle";// freqArticle
    public static final String FIELD_NAME_SPELLING = "spelling";// 单词拼写
    public static final String FIELD_NAME_LEMMA = "lemma";// 主旨
    public static final String FIELD_NAME_STEM = "stem";// 词干
    public static final String FIELD_NAME_DJ = "phoneticDJ";// 英音音标
    public static final String FIELD_NAME_KK = "phoneticKK";// 美音音标
    public static final String FIELD_NAME_LEVEL = "level";
    public static final String FIELD_NAME_POS = "partsOfSpeech";// 词性
    public static final String FIELD_NAME_SIMPLIFICATION = "simplification";// 精简的词义
    public static final String FIELD_NAME_MEANINGS = "meanings";
    public static final String FIELD_NAME_SENTENCES = "sentences";// exampleSentences
    public static final String FIELD_NAME_DETAIL = "detail";// word文件的所有字段,用json格式存储

    public static final String FIELD_NAME_SENIOR_ENTRANCE_EXAM = "中考";
    public static final String FIELD_NAME_COLLEGE_ENTRANCE_EXAM = "高考";
    public static final String FIELD_NAME_CET4 = "四级";
    public static final String FIELD_NAME_CET6 = "六级";
    public static final String FIELD_NAME_GRADUATE_ENTRANCE_EXAM = "考研";
    public static final String FIELD_NAME_IELTS = "雅思";
    public static final String FIELD_NAME_TOEFL = "托福";

    public static final String FIELD_NAME_PRIMARY_SCHOOL = "小学";
    public static final String FIELD_NAME_JUNIOR_SCHOOL = "初中";
    public static final String FIELD_NAME_SENIOR_SCHOOL = "高中";
    public static final String FIELD_NAME_UNIVERSITY = "大学";
    public static final String FIELD_NAME_COLLEGE_REQUIREMENTS = "大学英语课程教学要求";
    public static final String FIELD_NAME_NEW_CONCEPT_ENGLISH = "新概念";
    public static final String FIELD_NAME_TEM4 = "专四";
    public static final String FIELD_NAME_TEM8 = "专八";
    public static final String FIELD_NAME_BEC = "商务英语考试";
    public static final String FIELD_NAME_MBA = "MBA";
    public static final String FIELD_NAME_SAT = "SAT";
    public static final String FIELD_NAME_GRE = "GRE";
    public static final String FIELD_NAME_GMAT = "GMAT";
    public static final String FIELD_NAME_TOEIC = "TOEIC";
    public static final String FIELD_NAME_CATTI = "CATTI";
    public static final String FIELD_NAME_MAJOR_COMPUTER = "计算机";
    public static final String FIELD_NAME_MAJOR_CONSTRUCTION = "建筑";

    // @DatabaseField(columnName = "ID", id = true, generatedId = true)
    // private String ID;

    @DatabaseField(generatedId = true)
    private int id;

    // @DatabaseField(columnName = FIELD_NAME_FREQUENCY)
    private String frequency;

    @DatabaseField(columnName = FIELD_NAME_FREQ_WRITTEN)
    private String freqWritten;

    // @DatabaseField(columnName = FIELD_NAME_FREQ_SPOKEN)
    private String freqSpoken;

    // @DatabaseField(columnName = FIELD_NAME_COUNT_IN_ARTICLE)
    private int countInArticle;// freqArticle

    @DatabaseField(columnName = FIELD_NAME_SPELLING, canBeNull = false)
    private String spelling;

    // @DatabaseField(columnName = FIELD_NAME_LEMMA)
    private String lemma;

    // @DatabaseField(columnName = FIELD_NAME_STEM)
    private String stem;

    @DatabaseField(columnName = FIELD_NAME_DJ)
    private String phoneticDJ;

    @DatabaseField(columnName = FIELD_NAME_KK)
    private String phoneticKK;

    @DatabaseField(columnName = FIELD_NAME_LEVEL)
    private String level;

    // @DatabaseField(columnName = FIELD_NAME_POS)
    private String partsOfSpeech;

    @DatabaseField(columnName = FIELD_NAME_SIMPLIFICATION)
    private String simplification;

    @DatabaseField(columnName = FIELD_NAME_MEANINGS)
    private String meanings;

    // @DatabaseField(columnName = FIELD_NAME_SENTENCES, width = 4096)
    private String sentences;

    @DatabaseField(columnName = FIELD_NAME_DETAIL)
    private String detail;

    // 中考 / 高考 / CET4 / CET6 / 考研 / IELTS / TOEFL

    // 初中学业水平考试（高中段学校招生考试/高中入学考试）(senior high school entrance examination)
    // @DatabaseField(columnName = FIELD_NAME_SENIOR_ENTRANCE_EXAM)
    private String seniorEntranceExam;// 中考

    // 普通高等学校招生全国统一考试(The National College Entrance Examination)
    // @DatabaseField(columnName = FIELD_NAME_COLLEGE_ENTRANCE_EXAM)
    private String collegeEntranceExam; // 高考

    // @DatabaseField(columnName = FIELD_NAME_CET4)
    private String CET4;// 四级/大学英语四级考试(College English Test-4)

    // @DatabaseField(columnName = FIELD_NAME_CET6)
    private String CET6;// 六级/大学英语六级考试(College English Test-6)

    // 全国硕士研究生统一招生考试/硕士研究生入学考试(Unified national graduate entrance examination)
    // @DatabaseField(columnName = FIELD_NAME_GRADUATE_ENTRANCE_EXAM)
    private String graduateEntranceExam;// 考研

    // 全称是国际英语语言测试系统(International English Language Testing System)
    // @DatabaseField(columnName = FIELD_NAME_IELTS)
    private String IELTS;// 雅思

    // 全名为“检定非英语为母语者的英语能力考试”(The Test of English as a Foreign Language)
    // @DatabaseField(columnName = FIELD_NAME_TOEFL)
    private String TOEFL;// 托福

    // @DatabaseField(columnName = FIELD_NAME_PRIMARY_SCHOOL)
    private String primarySchool; // 小学 (primary school)或(elementary
                                  // school);//5级

    // @DatabaseField(columnName = FIELD_NAME_JUNIOR_SCHOOL)
    private String juniorSchool; // 初中(Junior high school)//4级

    // @DatabaseField(columnName = FIELD_NAME_SENIOR_SCHOOL)
    private String seniorSchool; // 高中(Senior high school)//3级

    // @DatabaseField(columnName = FIELD_NAME_UNIVERSITY)
    private String university; // 大学university(综合性的大学)或college(学院)//4级

    // @DatabaseField(columnName = FIELD_NAME_COLLEGE_REQUIREMENTS)
    private String collegeRequirements; // 大学英语课程教学要求(CollegeEnglishCurriculumRequirements)

    // @DatabaseField(columnName = FIELD_NAME_NEW_CONCEPT_ENGLISH)
    private String newConceptEnglish;// 新概念英语(New Concept English)//4级

    // @DatabaseField(columnName = FIELD_NAME_TEM4)
    private String TEM4;// 专四/英语专业四级考试(Test for English Majors-Band 4)

    // @DatabaseField(columnName = FIELD_NAME_TEM8)
    private String TEM8;// 专八/英语专业八级考试(Test for English Majors-Band 8)

    // @DatabaseField(columnName = FIELD_NAME_BEC)
    private String BEC;// 商务英语考试(Business English Certificate)

    // @DatabaseField(columnName = FIELD_NAME_MBA)
    private String MBA;// 工商管理学硕士(Master of Business Administration)

    // @DatabaseField(columnName = FIELD_NAME_SAT)
    private String SAT;// 学术能力评估测试(Scholastic Assessment Test)

    // @DatabaseField(columnName = FIELD_NAME_GRE)
    private String GRE;// 美国研究生入学考试(Graduate Record Examination)

    // @DatabaseField(columnName = FIELD_NAME_GMAT)
    private String GMAT;// 研究生管理科学入学考试(Graduate Management Admission Test)

    // 托业/国际交流英语考试(Test of English for International Communication)
    // @DatabaseField(columnName = FIELD_NAME_TOEIC)
    private String TOEIC;

    // 翻译专业资格（水平）考试”(China Accreditation Test for Translators and Interpreters)
    // @DatabaseField(columnName = FIELD_NAME_CATTI)
    private String CATTI;

    // @DatabaseField(columnName = FIELD_NAME_MAJOR_COMPUTER)
    private String majorComputer;// 专业英文词汇(计算机)

    // @DatabaseField(columnName = FIELD_NAME_MAJOR_CONSTRUCTION)
    private String majorConstruction;// 专业英文词汇 (建筑)

    public Word() {
        // all persisted classes must define a no-arg constructor with at least
        // package visibility
    }

    public Word(String spelling) {
        this.spelling = spelling;
    }

    public Word(String frequency, String spelling) {
        this.frequency = frequency;
        this.spelling = spelling;
    }

    public Word(String spelling, int level) {
        this.spelling = spelling;
        this.level = level + "";
    }

    public Word(String frequency, String spelling, String level) {
        this.frequency = frequency;
        this.spelling = spelling;
        this.level = level;
    }

    public Word(String frequency, String spelling, String level, String meanings) {
        this.frequency = frequency;
        this.spelling = spelling;
        this.level = level;
        this.meanings = meanings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrequency() {
        return frequency == null ? "" : frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFreqWritten() {
        return freqWritten;
    }

    public void setFreqWritten(String freqWritten) {
        this.freqWritten = freqWritten;
    }

    public String getFreqSpoken() {
        return freqSpoken;
    }

    public void setFreqSpoken(String freqSpoken) {
        this.freqSpoken = freqSpoken;
    }

    public int getCountInArticle() {
        return countInArticle;
    }

    public void setCountInArticle(int countInArticle) {
        this.countInArticle = countInArticle;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
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
        return level == null ? "" : level;
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

    public String getSimplification() {
        return simplification;
    }

    public void setSimplification(String simplification) {
        this.simplification = simplification;
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

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSeniorEntranceExam() {
        return seniorEntranceExam;
    }

    public void setSeniorEntranceExam(String seniorEntranceExam) {
        this.seniorEntranceExam = seniorEntranceExam;
    }

    public String getCollegeEntranceExam() {
        return collegeEntranceExam;
    }

    public void setCollegeEntranceExam(String collegeEntranceExam) {
        this.collegeEntranceExam = collegeEntranceExam;
    }

    public String getCET4() {
        return CET4;
    }

    public void setCET4(String cet4) {
        this.CET4 = cet4;
    }

    public String getCET6() {
        return CET6;
    }

    public void setCET6(String cet6) {
        this.CET6 = cet6;
    }

    public String getGraduateEntranceExam() {
        return graduateEntranceExam;
    }

    public void setGraduateEntranceExam(String graduateEntranceExam) {
        this.graduateEntranceExam = graduateEntranceExam;
    }

    public String getIELTS() {
        return IELTS;
    }

    public void setIELTS(String IELTS) {
        this.IELTS = IELTS;
    }

    public String getTOEFL() {
        return TOEFL;
    }

    public void setTOEFL(String TOEFL) {
        this.TOEFL = TOEFL;
    }

    public String getPrimarySchool() {
        return primarySchool;
    }

    public void setPrimarySchool(String primarySchool) {
        this.primarySchool = primarySchool;
    }

    public String getJuniorSchool() {
        return juniorSchool;
    }

    public void setJuniorSchool(String juniorSchool) {
        this.juniorSchool = juniorSchool;
    }

    public String getSeniorSchool() {
        return seniorSchool;
    }

    public void setSeniorSchool(String seniorSchool) {
        this.seniorSchool = seniorSchool;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCollegeRequirements() {
        return collegeRequirements;
    }

    public void setCollegeRequirements(String collegeRequirements) {
        this.collegeRequirements = collegeRequirements;
    }

    public String getNewConceptEnglish() {
        return newConceptEnglish;
    }

    public void setNewConceptEnglish(String newConceptEnglish) {
        this.newConceptEnglish = newConceptEnglish;
    }

    public String getTEM4() {
        return TEM4;
    }

    public void setTEM4(String TEM4) {
        this.TEM4 = TEM4;
    }

    public String getTEM8() {
        return TEM8;
    }

    public void setTEM8(String TEM8) {
        this.TEM8 = TEM8;
    }

    public String getBEC() {
        return BEC;
    }

    public void setBEC(String BEC) {
        this.BEC = BEC;
    }

    public String getMBA() {
        return MBA;
    }

    public void setMBA(String MBA) {
        this.MBA = MBA;
    }

    public String getSAT() {
        return SAT;
    }

    public void setSAT(String SAT) {
        this.SAT = SAT;
    }

    public String getGRE() {
        return GRE;
    }

    public void setGRE(String GRE) {
        this.GRE = GRE;
    }

    public String getGMAT() {
        return GMAT;
    }

    public void setGMAT(String GMAT) {
        this.GMAT = GMAT;
    }

    public String getTOEIC() {
        return TOEIC;
    }

    public void setTOEIC(String TOEIC) {
        this.TOEIC = TOEIC;
    }

    public String getCATTI() {
        return CATTI;
    }

    public void setCATTI(String CATTI) {
        this.CATTI = CATTI;
    }

    public String getMajorComputer() {
        return majorComputer;
    }

    public void setMajorComputer(String majorComputer) {
        this.majorComputer = majorComputer;
    }

    public String getMajorConstruction() {
        return majorConstruction;
    }

    public void setMajorConstruction(String majorConstruction) {
        this.majorConstruction = majorConstruction;
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
        return "id=" + id + ", frequency=" + (frequency == null ? "" : frequency) + ", spelling=" + spelling
                + ", level=" + (level == null ? "" : level) + ", partsOfSpeech="
                + (partsOfSpeech == null ? "" : partsOfSpeech);
    }

    public String toNoFreqLevel() {
        // StringBuffer sb = new StringBuffer();
        // if (id != 0) {
        // sb.append(id + "\t");
        // }
        // if (frequency != null) {
        // sb.append(frequency + "\t");
        // }
        return (id == 0 ? "" : id) + "\t" + (frequency == null ? "" : frequency) + "\t" + spelling + "\t"
                + (level == null ? "" : level);
    }

    // public String toString(int i) {
    // return "id: " + id + "\n"
    // + "name: " + name + "\n"
    // + "address: " + address + "\n"
    // + "gender: " + (gender ? "Male" : "Female")
    // + "\n\n";
    // }

    @Override
    public int compareTo(Word o) {
        if (this == o) {
            return 0;
        }
        if (this.spelling == null) {
            return -1;
        }
        if (o == null) {
            return 1;
        }
        // if (!(o instanceof Word)) {
        // return 1;
        // }
        // String t = ((Word) o).getSpelling();
        // if (t == null) {
        // return 1;
        // }
        String t = o.getSpelling();
        return this.spelling.compareToIgnoreCase(t);
    }

    // 返回正数，零，负数各代表大于，等于，小于。
    // 先按照frequency排序，若frequency相等，再按照word排序
    public static final Comparator<Word> m_byFreqAndLevel = (lw, rw) -> {
        if (StringUtils.isBlank(lw.getFrequency())) {
            if (StringUtils.isBlank(rw.getFrequency())) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (StringUtils.isBlank(rw.getFrequency())) {
                return -1;
            } else {
                // return lw.getFrequency().compareTo(rw.getFrequency());
                int freqLw = 0;
                int freqRw = 0;
                try {
                    freqLw = Integer.parseInt(lw.getFrequency());
                } catch (Exception e) {
                    return 0;
                }
                try {
                    freqRw = Integer.parseInt(rw.getFrequency());
                } catch (Exception e) {
                    return 1;
                }
                return freqLw - freqRw;
            }
        }
        // return lw.getFrequency() - rw.getFrequency();
        // if (lw.getFrequency() == rw.getFrequency())
        // return lw.getSpelling().compareTo(rw.getSpelling());
        // else
        // return lw.getFrequency() - rw.getFrequency();
    };

    /**
     * @param args
     */
    public static void main(String[] args) {
    }

}
