package cn.pa.jsoup.Manager;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WenduCookieSpider implements PageProcessor {
    private static Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
            .setDomain("www.wendu.com").addHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                            "Chrome/75.0.3770.142 Safari/537.36")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                    "q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
            .addHeader("Accept-Encoding", "gzip, deflate, sdch")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8");

    private static List<String> toList;
    private static HashMap<String,String> hashMap;

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, String> hashMap) {
        WenduCookieSpider.hashMap = hashMap;
    }

    public  List<String> getToList() {
        return toList;
    }

    public void setToList(List<String> toList) {
        WenduCookieSpider.toList = toList;
    }

    @Override
    public void process(Page page) {

        Document doc = page.getHtml().getDocument();
        Elements elements = doc.getElementsByClass("fl date-load-fl");
        String url = "";
        String fileName = "";
        for(Element element : elements){
            fileName = element.getElementsByTag("a").first().text();
            url = element.nextElementSibling().attr("href");
            hashMap.put(url,fileName);
            toList.add(url);
        }
        System.out.println(toList.toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

}
