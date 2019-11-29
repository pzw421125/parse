package cn.pa.jsoup.PoJo;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Exam {
    private int examId;
    private String examType;
    private String exameName;
    private String siteName;
    private List<Question> questions;
    private String content;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getExameName() {
        return exameName;
    }

    public void setExameName(String exameName) {
        this.exameName = exameName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId=" + examId +
                ", examType='" + examType + '\'' +
                ", exameName='" + exameName + '\'' +
                ", siteName='" + siteName + '\'' +
                ", questions=" + questions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return examId == exam.examId &&
                siteName.equals(exam.siteName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId, siteName);
    }
}
