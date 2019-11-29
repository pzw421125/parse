package cn.pa.jsoup.PoJo;

public class Option {
    //选项
    private String option;
    //选项内容
    private String optionContent;
    //选项正确
    private Boolean optionFlag;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public Boolean getOptionFlag() {
        return optionFlag;
    }

    public void setOptionFlag(Boolean optionFlag) {
        this.optionFlag = optionFlag;
    }

    @Override
    public String toString() {
        return "Option{" +
                "option='" + option + '\'' +
                ", optionContent='" + optionContent + '\'' +
                ", optionFlag=" + optionFlag +
                '}';
    }
}
