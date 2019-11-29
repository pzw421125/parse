package cn.pa.jsoup.Utils;

import cn.pa.jsoup.Manager.WenDuDownlaodSpider;
import cn.pa.jsoup.Manager.WenduCookieSpider;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import sun.security.provider.ConfigFile;
import us.codecraft.webmagic.Spider;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class ParseWenduMain {


    public static void main(String[] args) throws IOException {
//        String urlStr = "https://www.wendu.com/index.php?m=content&c=index&a=lists&catid=302&siteid=1";
//        String savePath = "D:/test/yszg/";
//
//        String urlStr = "https://www.wendu.com/index.php?m=content&c=index&a=lists&catid=303&siteid=1";
//        String savePath = "D:/test/zyys/";
//        String urlStr = "https://www.wendu.com/index.php?m=content&c=index&a=lists&catid=304&siteid=1";
//        String savePath = "D:/test/hszy/";
        String urlStr = "https://www.wendu.com/index.php?m=content&c=index&a=lists&catid=305&siteid=1";
        String savePath = "D:/test/wszy/";
        List<String> urlList = new ArrayList<>();
        urlList.add(urlStr);
        for (int i = 1; i <=48 ; i++) {
            urlList.add(urlStr+"&page="+i);
        }
        //获取跳板链接
        WenduCookieSpider wenduCookieSpider = new WenduCookieSpider();
        List<String> toList = new ArrayList<>();

        wenduCookieSpider.setToList(toList);
        //设置hashmap
        HashMap<String,String> hashMap = new HashMap<>();
        wenduCookieSpider.setHashMap(hashMap);
        Spider.create(wenduCookieSpider).startUrls(urlList).thread(5).run();

        System.out.println("跳板链接："+wenduCookieSpider.getToList().toString());
        System.out.println(wenduCookieSpider.getToList().size());
        //获取下载链接
        WenDuDownlaodSpider wenDuDownlaodSpider = new WenDuDownlaodSpider();

        //设置文件名map
        List<String> downLoadList = new ArrayList<>();
        HashMap<String,String> fileNameMap = new HashMap<>();

        wenDuDownlaodSpider.setDownList(downLoadList);
        wenDuDownlaodSpider.setHashMap(hashMap);
        wenDuDownlaodSpider.setFileMap(fileNameMap);
        Spider.create(wenDuDownlaodSpider).startUrls(toList).thread(5).run();

        System.out.println("下载链接"+downLoadList);
        System.out.println(downLoadList.size());

        //下载文件
        DownloadFileUtils.downloadFileByUrl(downLoadList,fileNameMap,savePath);

    }
}
