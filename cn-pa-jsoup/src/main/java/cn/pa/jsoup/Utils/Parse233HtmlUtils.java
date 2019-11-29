package cn.pa.jsoup.Utils;

import cn.pa.jsoup.PoJo.Option;
import cn.pa.jsoup.PoJo.Question;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parse233HtmlUtils {


    public  static List<Question> parse(String filePath) {
        File pageFile = new File(filePath);
//        File pageFile = new File("D:/age/"+path+".html");
        Document doc = null;
        try {
            doc = Jsoup.parse(pageFile,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取考试类型
        Element titleEle = doc.getElementsByClass("h2").first();

//        String examName = titleEle.text();

        Elements elements = doc.getElementsByClass("sub-dotitle");

        List<Question> questionList = new ArrayList<Question>();
        System.out.println("question"+elements.size());

        Elements questionElement ;
        for (int i = 0; i <elements.size() ; i++) {
            questionElement =elements.get(0).nextElementSibling().getElementsByTag("dd");
            try {
                questionElement =elements.get(i).nextElementSibling().getElementsByTag("dd");
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<Option> options = new ArrayList<Option>();
            int rightNum = 0;
            for (int j = 0; j <questionElement.size() ; j++) {
                String ss = questionElement.get(j).attr("data-value");

                Option option = new Option();
                option.setOption(ss);
                option.setOptionContent(questionElement.get(j).text());
                if(!questionElement.get(j).getElementsByClass(
                        "m-question-option  right-this").isEmpty()){
                    option.setOptionFlag(true);
                    rightNum ++;
                }else if(!questionElement.get(j).getElementsByClass(
                        "m-question-option cho-this right-this").isEmpty()){
                    option.setOptionFlag(true);
                    rightNum --;

                }
                else {
                    option.setOptionFlag(false);
                }
                options.add(option);
            }
            Question question =new Question() ;
            //设置问题题目
            question.setQuestionItem(elements.get(i).text());
            //设置问题选项
            question.setOptions(options);
            //问题数量
            question.setOptionNum(options.size());
            //设置考试问题
            String ques = elements.get(i).text();
            ques = ques.substring(ques.lastIndexOf("]")+1,ques.length()).trim();
            question.setQuestionItem(ques);
            //设置考试ID
            String examid = doc.getElementsByAttribute("data-examid").first().attr("data-examid");
            question.setQuestionId(i+1);
            question.setExamId(Integer.parseInt(examid));
            String answer= doc.getElementsByClass("right").get(i).
                    getElementsByTag("em").text();
            //设置问题答案ri
            question.setAnswer(answer);
            //设置问题类型
            String quetionType = "";
            if(rightNum<0){
                quetionType="p";
            }else if(rightNum==1){
                quetionType="s";
            }else{
                quetionType="m";
            }

            //设置答案选项集合

//            question.setAnswerOptions(options.get(rightNum));
            //设置问题类型
            question.setQuestionType(quetionType);
            //设置问题解析

            try {
                String desc = doc.getElementsByClass("wenzi").get(i).text();
                question.setQuestionDesc(desc);
                questionList.add(question);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //System.out.println(question.toString());

        return questionList;
    }

    public static List<String> findFiles(String fileDir){
        List<String> fileNameList = new ArrayList<String>();
        //指定目录下的文件名列表
        File dir = new File(fileDir);
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isFile()){
                fileNameList.add(file.getName());
            }
        }
        return fileNameList;
    }

    public static void fileTo(String path){

        List<String> fileNames =  findFiles(path);
        System.out.println(fileNames.size());
        System.out.println(fileNames.toString());
        for (int i = 0; i <fileNames.size() ; i++) {
            String examType = fileNames.get(i);
            String filePath = path+"/"+examType;
            System.out.println(filePath);
            List<Question> questionList = parse(filePath);
            System.out.println(questionList.toString());
            //输出Excel文档String filePath =  "E:/poi/"+file.getName().substring(0,file.getName().indexOf("-"))+".xls";
            String dir = "D:/excel/"+path.substring(7,path.length());

            String exportExcel = "D:/excel/"+examType.substring(0,examType.lastIndexOf("."))+".xls";
            System.out.println(exportExcel);
            POIUtils.exportToExcel(questionList,fileNames.get(i),exportExcel,"单选");
        }
        System.out.println("解析完毕");
    }

    public static void main(String[] args) {

       // fileTo("D:\\page\\CJKJZC\\CJKJSW");
        //fileTo("D:\\page\\RLZYGLS\\EJLLZS");
        //D:\page\RLZYGLS\SJLLZS

        while(true){
            Scanner scanner= new Scanner(System.in);


            System.out.println("请输入文件路径");
            String path = scanner.nextLine();
            System.out.println("输入的文件路径为"+path);
            fileTo(path);
        }



    }

}
