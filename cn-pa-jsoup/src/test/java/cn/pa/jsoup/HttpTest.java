package cn.pa.jsoup;

import cn.pa.jsoup.Utils.HttpConnectionPoolUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HttpTest {
    @Test
    public void HttpTest(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<String> urls = new ArrayList<>();
        int urlId = 0;
        List<Integer> nums = new ArrayList<>();
        for (int i = 380000; i <383999 ; i++) {
            urlId= i;
            urls.add("https://wx.233.com/tiku/exam/item/"+urlId);
        }

        for (int i = 0; i <urls.size() ; i++) {
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        }

    }

    @Test
    public void HttpGet() {

    }
        @Test
    public void parse() throws IOException {
        String url = "http://www.hqwx.com/zhibo/huodong/download?id=2225";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept"
                ,"application/json");
        httpGet.setHeader("accept-encoding","gzip, deflate, br");
        httpGet.setHeader("accept-language","zh-CN,zh;q=0.9");
        List<String> list  = new ArrayList<>();
        try {
            HttpResponse httpResponse =  httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode()==200){
                HttpEntity entity = httpResponse.getEntity();
                Document doc =  Jsoup.parse(EntityUtils.toString(entity,"utf-8"));


                System.out.println(doc.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpClient.close();
        }

//        Elements element = doc.getElementsByClass("zglh_cont");
//        String examContent =  element.text();
//        System.out.println(examContent);
    }

    @Test
    public void test01(){
        System.out.println("a");
    }




}
