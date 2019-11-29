package cn.pa.jsoup;

import cn.pa.jsoup.Utils.HttpConnectionPoolUtil;
import org.apache.http.client.methods.HttpGet;

public class HQWX {

    public static void main(String[] args) {
        String url = "http://www.hqwx.com/zhibo/huodong/download?id=10099";
        HttpConnectionPoolUtil.getHttpClient(url);
        HttpGet httpget = new HttpGet(url);



    }
}
