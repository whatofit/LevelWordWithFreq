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
public class Word implements Comparable<Word> {
    // for QueryBuilder to be able to find the fields
    public static final String FIELD_NAME_FREQUENCY = "frequency";
    public static final String FIELD_NAME_FREQ_WRITTEN = "freqWritten";
    public static final String FIELD_NAME_FREQ_SPOKEN = "freqSpoken";
    public static final String FIELD_NAME_SPELLING = "spelling";
    public static final String FIELD_NAME_DJ = "phoneticDJ";
    public static final String FIELD_NAME_KK = "phoneticKK";
    public static final String FIELD_NAME_LEVEL = "level";
    public static final String FIELD_NAME_POS = "partsOfSpeech";
    public static final String FIELD_NAME_MEANINGS = "meanings";
    public static final String FIELD_NAME_SENTENCES = "sentences";// exampleSentences

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

    //@DatabaseField(columnName = "ID", id = true, generatedId = true)
    //private String ID;

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_NAME_FREQUENCY)
    private int frequency;

    @DatabaseField(columnName = FIELD_NAME_FREQ_WRITTEN)
    private int freqWritten;

    @DatabaseField(columnName = FIELD_NAME_FREQ_SPOKEN)
    private int freqSpoken;

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

    // 中考 / 高考 / CET4 / CET6 / 考研 / IELTS / TOEFL

    // 初中学业水平考试（高中段学校招生考试/高中入学考试）(senior high school entrance examination)
    @DatabaseField(columnName = FIELD_NAME_SENIOR_ENTRANCE_EXAM)
    private int seniorEntranceExam;// 中考

    // 普通高等学校招生全国统一考试(The National College Entrance Examination)
    @DatabaseField(columnName = FIELD_NAME_COLLEGE_ENTRANCE_EXAM)
    private int collegeEntranceExam; // 高考

    @DatabaseField(columnName = FIELD_NAME_CET4)
    private int CET4;// 四级/大学英语四级考试(College English Test-4)

    @DatabaseField(columnName = FIELD_NAME_CET6)
    private int CET6;// 六级/大学英语六级考试(College English Test-6)

    // 全国硕士研究生统一招生考试/硕士研究生入学考试(Unified national graduate entrance examination)
    @DatabaseField(columnName = FIELD_NAME_GRADUATE_ENTRANCE_EXAM)
    private int graduateEntranceExam;// 考研

    // 全称是国际英语语言测试系统(International English Language Testing System)
    @DatabaseField(columnName = FIELD_NAME_IELTS)
    private int IELTS;// 雅思

    // 全名为“检定非英语为母语者的英语能力考试”(The Test of English as a Foreign Language)
    @DatabaseField(columnName = FIELD_NAME_TOEFL)
    private int TOEFL;// 托福

    @DatabaseField(columnName = FIELD_NAME_PRIMARY_SCHOOL)
    private int primarySchool; // 小学 (primary school)或(elementary school);//5级

    @DatabaseField(columnName = FIELD_NAME_JUNIOR_SCHOOL)
    private int juniorSchool; // 初中(Junior high school)//4级

    @DatabaseField(columnName = FIELD_NAME_SENIOR_SCHOOL)
    private int seniorSchool; // 高中(Senior high school)//3级

    @DatabaseField(columnName = FIELD_NAME_UNIVERSITY)
    private int university; // 大学university(综合性的大学)或college(学院)//4级

    @DatabaseField(columnName = FIELD_NAME_COLLEGE_REQUIREMENTS)
    private int collegeRequirements; // 大学英语课程教学要求(CollegeEnglishCurriculumRequirements)

    @DatabaseField(columnName = FIELD_NAME_NEW_CONCEPT_ENGLISH)
    private int newConceptEnglish;// 新概念英语(New Concept English)//4级

    @DatabaseField(columnName = FIELD_NAME_TEM4)
    private int TEM4;// 专四/英语专业四级考试(Test for English Majors-Band 4)

    @DatabaseField(columnName = FIELD_NAME_TEM8)
    private int TEM8;// 专八/英语专业八级考试(Test for English Majors-Band 8)

    @DatabaseField(columnName = FIELD_NAME_BEC)
    private int BEC;// 商务英语考试(Business English Certificate)

    @DatabaseField(columnName = FIELD_NAME_MBA)
    private int MBA;// 工商管理学硕士(Master of Business Administration)

    @DatabaseField(columnName = FIELD_NAME_SAT)
    private int SAT;// 学术能力评估测试(Scholastic Assessment Test)

    @DatabaseField(columnName = FIELD_NAME_GRE)
    private int GRE;// 美国研究生入学考试(Graduate Record Examination)

    @DatabaseField(columnName = FIELD_NAME_GMAT)
    private int GMAT;// 研究生管理科学入学考试(Graduate Management Admission Test)

    // 托业/国际交流英语考试(Test of English for International Communication)
    @DatabaseField(columnName = FIELD_NAME_TOEIC)
    private int TOEIC;

    // 翻译专业资格（水平）考试”(China Accreditation Test for Translators and Interpreters)
    @DatabaseField(columnName = FIELD_NAME_CATTI)
    private int CATTI;

    @DatabaseField(columnName = FIELD_NAME_MAJOR_COMPUTER)
    private int majorComputer;// 专业英文词汇(计算机)

    @DatabaseField(columnName = FIELD_NAME_MAJOR_CONSTRUCTION)
    private int majorConstruction;// 专业英文词汇 (建筑)

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

    public Word(String spelling, String level) {
        this.frequency = 0;
        this.spelling = spelling;
        this.level = level;
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

    public int getFreqWritten() {
        return freqWritten;
    }

    public void setFreqWritten(int freqWritten) {
        this.freqWritten = freqWritten;
    }

    public int getFreqSpoken() {
        return freqSpoken;
    }

    public void setFreqSpoken(int freqSpoken) {
        this.freqSpoken = freqSpoken;
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

    public int getSeniorEntranceExam() {
        return seniorEntranceExam;
    }

    public void setSeniorEntranceExam(int seniorEntranceExam) {
        this.seniorEntranceExam = seniorEntranceExam;
    }

    public int getCollegeEntranceExam() {
        return collegeEntranceExam;
    }

    public void setCollegeEntranceExam(int collegeEntranceExam) {
        this.collegeEntranceExam = collegeEntranceExam;
    }

    public int getCET4() {
        return CET4;
    }

    public void setCET4(int cet4) {
        this.CET4 = cet4;
    }

    public int getCET6() {
        return CET6;
    }

    public void setCET6(int cet6) {
        this.CET6 = cet6;
    }

    public int getGraduateEntranceExam() {
        return graduateEntranceExam;
    }

    public void setGraduateEntranceExam(int graduateEntranceExam) {
        this.graduateEntranceExam = graduateEntranceExam;
    }

    public int getIELTS() {
        return IELTS;
    }

    public void setIELTS(int IELTS) {
        this.IELTS = IELTS;
    }

    public int getTOEFL() {
        return TOEFL;
    }

    public void setTOEFL(int TOEFL) {
        this.TOEFL = TOEFL;
    }

    public int getPrimarySchool() {
        return primarySchool;
    }

    public void setPrimarySchool(int primarySchool) {
        this.primarySchool = primarySchool;
    }

    public int getJuniorSchool() {
        return juniorSchool;
    }

    public void setJuniorSchool(int juniorSchool) {
        this.juniorSchool = juniorSchool;
    }

    public int getSeniorSchool() {
        return seniorSchool;
    }

    public void setSeniorSchool(int seniorSchool) {
        this.seniorSchool = seniorSchool;
    }

    public int getUniversity() {
        return university;
    }

    public void setUniversity(int university) {
        this.university = university;
    }

    public int getCollegeRequirements() {
        return collegeRequirements;
    }

    public void setCollegeRequirements(int collegeRequirements) {
        this.collegeRequirements = collegeRequirements;
    }

    public int getNewConceptEnglish() {
        return newConceptEnglish;
    }

    public void setNewConceptEnglish(int newConceptEnglish) {
        this.newConceptEnglish = newConceptEnglish;
    }

    public int getTEM4() {
        return TEM4;
    }

    public void setTEM4(int TEM4) {
        this.TEM4 = TEM4;
    }

    public int getTEM8() {
        return TEM8;
    }

    public void setTEM8(int TEM8) {
        this.TEM8 = TEM8;
    }

    public int getBEC() {
        return BEC;
    }

    public void setBEC(int BEC) {
        this.BEC = BEC;
    }

    public int getMBA() {
        return MBA;
    }

    public void setMBA(int MBA) {
        this.MBA = MBA;
    }

    public int getSAT() {
        return SAT;
    }

    public void setSAT(int SAT) {
        this.SAT = SAT;
    }

    public int getGRE() {
        return GRE;
    }

    public void setGRE(int GRE) {
        this.GRE = GRE;
    }

    public int getGMAT() {
        return GMAT;
    }

    public void setGMAT(int GMAT) {
        this.GMAT = GMAT;
    }

    public int getTOEIC() {
        return TOEIC;
    }

    public void setTOEIC(int TOEIC) {
        this.TOEIC = TOEIC;
    }

    public int getCATTI() {
        return CATTI;
    }

    public void setCATTI(int CATTI) {
        this.CATTI = CATTI;
    }

    public int getMajorComputer() {
        return majorComputer;
    }

    public void setMajorComputer(int majorComputer) {
        this.majorComputer = majorComputer;
    }

    public int getMajorConstruction() {
        return majorConstruction;
    }

    public void setMajorConstruction(int majorConstruction) {
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
