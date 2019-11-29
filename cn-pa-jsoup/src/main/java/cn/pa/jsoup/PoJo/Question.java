package cn.pa.jsoup.PoJo;

import java.util.List;
/* 问题实体类
 *
 */
public class Question {
    //问题ID
    private Integer questionId;
    //问题题目
    private String questionItem;
    //选项
    private List<Option> options;
    //答案
    private String answer;
    //答案选项
    private List<Option> answerOptions;
    //选项数量
    private Integer optionNum;
    //问题类型
    private String questionType;
    //问题解析
    private String questionDesc;
    //试题Id
    private Integer examId;
    //试题标题
    private String examItem;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestionItem() {
        return questionItem;
    }

    public void setQuestionItem(String questionItem) {
        this.questionItem = questionItem;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<Option> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<Option> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public Integer getOptionNum() {
        return optionNum;
    }

    public void setOptionNum(Integer optionNum) {
        this.optionNum = optionNum;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getExamItem() {
        return examItem;
    }

    public void setExamItem(String examItem) {
        this.examItem = examItem;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", questionItem='" + questionItem + '\'' +
                ", options=" + options +
                ", answerOptions=" + answerOptions +
                ", optionNum=" + optionNum +
                ", questionType='" + questionType + '\'' +
                ", questionDesc='" + questionDesc + '\'' +
                ", examId=" + examId +
                ", examItem='" + examItem + '\'' +
                '}';
    }
}
