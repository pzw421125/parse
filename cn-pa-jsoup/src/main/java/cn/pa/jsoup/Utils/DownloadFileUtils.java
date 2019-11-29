package cn.pa.jsoup.Utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadFileUtils {

    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
//    public static void downloadFile(String savePath,String urlStr,String fileName) {
//        URL url = null;
//        BufferedReader br=null;
//        BufferedWriter bw=null;
//        try {
//            url = new URL(urlStr);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(3 * 1000);
//            conn.setRequestProperty("Charset", "UTF-8");
//            conn.setRequestProperty("User-Agent",
//                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
//            InputStream inputStream = conn.getInputStream();
//            //获取字符流
//            br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
//
//            //文件保存位置
//            File saveDir = new File(savePath);
//            if (!saveDir.exists()) {
//                saveDir.mkdir();
//            }
//
//            File file = new File(saveDir + File.separator + fileName);
//            FileOutputStream fos = new FileOutputStream(file);
//            bw = new BufferedWriter(new OutputStreamWriter(fos));
//            String str = null;
//            if(conn.getResponseCode()==200) {
//                str = br.readLine();
//                System.out.println(str);
//                bw.write(str);
//                if (fos != null) {
//                    fos.close();
//                }
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        System.out.println("info:"+urlStr+" download success");
//    }
//
//
//    public static void downloadFileByUrl(List<String> urlList, HashMap<String,String> fileNames, String savePath)  {
//
//        String fileName ="";
//        for(String urlStr: urlList){
//
//            URL url = null;
//            try {
//                if(urlStr!=null || urlStr.equals("")){
//                    if(urlStr.startsWith("//")){
//                        urlStr = "https:"+urlStr;
//                    }
//                    url = new URL(urlStr);
//                }else {
//                    System.out.println("url为空");
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                System.out.println(urlStr);
//            }
//            FileOutputStream fos = null;
//            InputStream inputStream = null;
//            BufferedReader br;
//            BufferedWriter bw;
//            try{
//                HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
//                conn.setConnectTimeout(3*1000);
////              conn.setRequestProperty("Charset", "UTF-8");
//                conn.setRequestProperty("User-Agent",
//                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
//                //获取字符流
//                inputStream = conn.getInputStream();
//               // br = new BufferedReader(new InputStreamReader(inputStream));
//
//                //文件保存位置
//                File saveDir = new File(savePath);
//                if (!saveDir.exists()) {
//                    saveDir.mkdir();
//                }
//                //得到输入流
//                inputStream = conn.getInputStream();
//                //获取自己数组
//                byte[] getData = readInputStream(inputStream);
//
//                //文件保存位置
//                saveDir = new File(savePath);
//                if(!saveDir.exists()){
//                    saveDir.mkdir();
//                }
//                fileName = urlStr.substring(urlStr.lastIndexOf("/")+1);
//                File file = new File(saveDir+File.separator+fileName);
//                fos = new FileOutputStream(file);
//                fos.write(getData);
////                fileName = urlStr.substring(urlStr.lastIndexOf("/")+1);
////                File file = new File(saveDir + File.separator + fileName);
////                fos = new FileOutputStream(file);
////                bw = new BufferedWriter(new OutputStreamWriter(fos));
////                String str = null;
////
////                    str = br.readLine();
////                    System.out.println(str);
////                    while (str!=null){
////                        bw.write(str);
////                    }
////
////                    if (fos != null) {
////                        fos.close();
////                    }
////                    if (inputStream != null) {
////                        inputStream.close();
////                    }
//
//            }catch (Exception e) {
//                e.printStackTrace();
//            }finally {
//                if(fos!=null){
//                    try {
//                        fos.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(inputStream!=null){
//                    try {
//                        inputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            System.out.println("info:"+urlStr+" download success");
//        }
//    }

    public static void downloadFileByUrl(List<String> urlList, HashMap<String,String> fileNames, String savePath)  {

        String fileName ="";
        for(String urlStr: urlList){

            URL url = null;
            try {
                if(urlStr!=null || !urlStr.equals("")){
                    if(urlStr.startsWith("//")){
                        urlStr = "https:"+urlStr;
                    }
                    url = new URL(urlStr);
                }else {
                    System.out.println("url为空");
                }
            } catch (MalformedURLException e) {
                System.out.println("url:::"+urlStr);
                e.printStackTrace();

            }
            FileOutputStream fos = null;
            InputStream inputStream = null;
            BufferedReader br;
            BufferedWriter bw;
            try{
                HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(3*1000);
//              conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
                //获取字符流
                inputStream = conn.getInputStream();
                // br = new BufferedReader(new InputStreamReader(inputStream));

                //文件保存位置
                File saveDir = new File(savePath);
                if (!saveDir.exists()) {
                    saveDir.mkdir();
                }
                //得到输入流
                inputStream = conn.getInputStream();
                //获取自己数组
                byte[] getData = readInputStream(inputStream);

                //文件保存位置
                saveDir = new File(savePath);
                if(!saveDir.exists()){
                    saveDir.mkdir();
                }

                fileName = fileNames.get(urlStr)+urlStr.substring(urlStr.lastIndexOf("."));
                File file = new File(saveDir+File.separator+fileName);
                fos = new FileOutputStream(file);
                fos.write(getData);


            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(inputStream!=null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("info:"+urlStr+" download success");
        }
    }

}
