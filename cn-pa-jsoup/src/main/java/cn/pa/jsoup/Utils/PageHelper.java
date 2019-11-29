package cn.pa.jsoup.Utils;

import cn.pa.jsoup.PoJo.Question;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageHelper {

    public static List<Question> parsePage(String htmlPath ){
        File pageFile = new File(htmlPath);
        Document doc = null;
        Question question = null;
        List<Question> questionList = new ArrayList<>();
        try {
            doc = Jsoup.parse(pageFile,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取试题标题
        String title = doc.getElementsByClass("h2").first().text();
        question.setExamItem(title);


        return questionList;
    }




}
