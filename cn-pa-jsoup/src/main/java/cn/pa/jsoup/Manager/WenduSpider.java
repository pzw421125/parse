package cn.pa.jsoup.Manager;

import cn.pa.jsoup.Utils.DownloadFileUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.SimplePageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WenduSpider implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    private static int count =0;
    private static String URL_GET = "https://www.wendu.com/download/yixue/";
    private static String filePath = "D:\\test\\htmls\\";
    private static HashMap<String,String> urlHashMap = new HashMap<>();
    private static List<String> downloadUrls = new ArrayList<>();
    @Override
    public void process(Page page) {
        Document doc = page.getHtml().getDocument();
        Elements elements = doc.getElementsByClass("fl date-load-fl");

        for (int i = 0; i <elements.size() ; i++) {
            Element element = elements.get(i);
            String examName = element.getElementsByTag("a").first().text();
            //获取url
            String downloadUrl = element.nextElementSibling().getElementsByClass("fr date-load-btn users")
                    .attr("href");
            downloadUrls.add(downloadUrl);
            urlHashMap.put(downloadUrl,examName);
            page.putField(downloadUrl,examName);
            count++;
        }
        System.out.println("count::"+count);
        System.out.println("downloadUrls::"+downloadUrls.toString());

    }

    @Override
    public Site getSite() {
        site.addHeader("Upgrade-Insecure-Requests","1");
        site.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
        site.getCookies();
        return site;
    }

    public static void main(String[] args) {

        System.out.println("开始爬取...");
        List<String> urlLists = new ArrayList<>();
        String targetLinkUrl = URL_GET;
                urlLists.add(URL_GET);
        System.out.println("targetLinkUrl:" + targetLinkUrl);
        for (int i = 2; i <=50 ; i++) {
            targetLinkUrl = URL_GET + "index_" + i + ".shtml";
            System.out.println("targetLinkUrl:" + targetLinkUrl);
            urlLists.add(targetLinkUrl);

        }
        Spider.create(new WenduSpider()).thread(5)
                .startUrls(urlLists).run();

        System.out.println("downloadUrls.size():"+ downloadUrls.size());
        System.out.println("urlHashMap.size()"+urlHashMap.size());

        DownloadFileUtils.downloadFileByUrl(downloadUrls,urlHashMap,filePath);
    }
}
