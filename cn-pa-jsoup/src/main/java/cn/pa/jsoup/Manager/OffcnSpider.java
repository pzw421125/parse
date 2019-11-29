package cn.pa.jsoup.Manager;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.*;
import java.util.*;

public class OffcnSpider implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    private static int count =0;
    private static String URL_GET = "http://www.cyikao.com/lcyishi/zhenti/";
    private static String filePath = "D:\\test\\htmls\\";
    private static HashMap<String,String> urlHashMap = new HashMap<>();

    @Override
    public void process(Page page) {

        String pa= page.getHtml().links().toString();
        System.out.println(pa);
//        Document doc = page.getHtml().getDocument();
//        HashMap<String,String> urlHashMap = new HashMap<>();
//        //处理试题，存储到Map集合
//        Elements elements= doc.getElementsByClass("lby19mlist_p");
//        for (int i = 0; i <elements.size() ; i++) {
//                String key = elements.get(i).attr("href");
//                String value = elements.get(i).attr("title");
//                urlHashMap.put(key,value);
//                System.out.println("key:"+key+" value:"+value);
//        }

        Document doc = page.getHtml().getDocument();
        //处理跳转连接

        //处理试题，存储到Map集合
        Elements elements= doc.getElementsByClass("lby19mlist_p");
        for (int j = 0; j <elements.size() ; j++) {
                String key = elements.get(j).attr("href");
                String value = elements.get(j).attr("title");
                urlHashMap.put(key,value);
                System.out.println("key:"+key+" value:"+value);
                StringBuffer sb = new StringBuffer();
                String fileName =sb.append(filePath)
                        .append(j)
                        .append(".txt").toString();
                page.putField(key,value);
        }
        System.out.println("urlMaps"+urlHashMap.size());

    }




    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {
        long startTime, endTime;
        System.out.println("开始爬取...");

        startTime = System.currentTimeMillis();
        List<String> urlLists = new ArrayList<>();
        urlLists.add(URL_GET+".html");
        for (int i = 2; i <=150 ; i++) {
            String targetLinkUrl = URL_GET + "page" + i + ".html";
            System.out.println("targetLinkUrl:" + targetLinkUrl);
            urlLists.add(URL_GET + "page" + i + ".html");
        }

        Spider.create(new OffcnSpider()).startUrls(urlLists).
                thread(5).addPipeline(
                        new MyPipeline("D:\\page\\ykzt")
        ).runAsync();
        ;
//        Spider.create(new OffcnSpider()).addUrl("http://www.cyikao.com/yaoshi/zhenti/").thread(5).run();
        System.out.println("size==="+urlHashMap.size());
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+count+"条记录");


    }


}
