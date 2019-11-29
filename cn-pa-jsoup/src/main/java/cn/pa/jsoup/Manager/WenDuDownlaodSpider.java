package cn.pa.jsoup.Manager;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WenDuDownlaodSpider implements PageProcessor {
    private static Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
            .setDomain("www.wendu.com").addHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                            "Chrome/75.0.3770.142 Safari/537.36")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                    "q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
            .addHeader("Accept-Encoding", "gzip, deflate, sdch")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8");

    private static List<String> downList;
    private static HashMap<String,String> hashMap;
    private static HashMap<String,String> fileMap;
    public HashMap<String,String> getFileMap(){
        return fileMap;
    }

    public void setFileMap(HashMap<String, String> fileMap) {
        WenDuDownlaodSpider.fileMap = fileMap;
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public  void setHashMap(HashMap<String, String> hashMap) {
        WenDuDownlaodSpider.hashMap = hashMap;
    }

    public  List<String> getDownList() {
        return downList;
    }

    public  void setDownList(List<String> downList) {
        WenDuDownlaodSpider.downList = downList;
    }

    @Override
    public void process(Page page) {
        String downloadUrl = page.getHtml().getDocument()
                .getElementsByClass("article-download").attr("href");
        String url = page.getUrl().toString();
        String fileName = hashMap.get(url);
        downList.add(downloadUrl);
        fileMap.put(downloadUrl,fileName);
        System.out.println("downloadList:"+downList.toString());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
