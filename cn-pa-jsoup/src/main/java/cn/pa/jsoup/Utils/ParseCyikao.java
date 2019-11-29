package cn.pa.jsoup.Utils;

import cn.pa.jsoup.PoJo.Exam;
import cn.pa.jsoup.PoJo.Question;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ParseCyikao {
    public static void main(String[] args) {
        String filePath=  "D:/page/yk";
        HashSet<Exam> examList = FileUtils.getExam(filePath);
        HashSet<Exam> examHashSet = new HashSet<>();
        Iterator it = examHashSet.iterator();
        Exam exam ;
        while (it.hasNext()){
           exam =(Exam) it.next();
           String url = exam.getSiteName();

        }
    }

    private static void parseUrl(String url,HashSet<Exam> examHashSet) {
        Document doc = Jsoup.parse(url);
        Element element = doc.getElementsByClass("zglh_cont").first();

    }

    public static List<Question> parse(String filePath ){
        List<Question> questionList = new ArrayList<>();
        return  questionList;
    }

}
