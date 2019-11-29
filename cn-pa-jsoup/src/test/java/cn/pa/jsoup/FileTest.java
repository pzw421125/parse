package cn.pa.jsoup;

import cn.pa.jsoup.PoJo.Exam;
import cn.pa.jsoup.PoJo.Question;
import cn.pa.jsoup.Utils.FileUtils;
import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FileTest {
    @Test
    public void fileTest(){
        String fileDir = "D:/page";
        File dir = new File(fileDir);
        File[] files = dir.listFiles();
        List<String> fileNames = new ArrayList<>();
        for (File file : files) {
            if(file.isFile()){
                fileNames.add(file.getName());
                System.out.println("文件名"+file.getName());
            }else if(file.isDirectory()){

            }
        }

        for(String fileName : fileNames){
            File file = new File("D:/page/"+fileName);
            try {
                Document document = Jsoup.parse(file,"utf-8");
                String s = document.getElementsByClass("h2").first().text();
                System.out.println(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @Test
    public void createFile() throws FileNotFoundException {
        String fileDir = "D:/page";
        File dir = new File(fileDir);
        File[] files = dir.listFiles();
        List<String> fileNames = new ArrayList<>();
        for (File file : files) {
            if(file.isFile()){
                fileNames.add(file.getName());
                System.out.println("文件名"+file.getName());

                String filePath =  "E:/poi/"+file.getName().substring(0,file.getName().indexOf("-"))+".xls";
                Workbook workbook = new HSSFWorkbook();
                Sheet sheet = workbook.createSheet("test");
                Row row = sheet.createRow(0);// 创建行,从0开始
                Cell cell = row.createCell(0);
                try {
                    workbook.write(new FileOutputStream(new File(filePath)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("文件写入成功");
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(file.isDirectory()){

            }
        }
    }


    @Test
    public void Test() {
        String filePath = "D:/page/ykzt";

        //读取网页jason数据
        File[] files = new File(filePath).listFiles();
        FileInputStream ifs = null;
        InputStreamReader in = null;
        BufferedReader bufferedReader = null;
        List<Exam> examList = new ArrayList<>();
        Exam exam = new Exam();
        String line = null;
        int num = 0;
        for (int i = 0; i < files.length; i++) {
            try {
                ifs = new FileInputStream(files[i]);
                in = new InputStreamReader(ifs, "UTF-8");
                bufferedReader = new BufferedReader(in);

               while(bufferedReader.readLine()!=null){

                   line = bufferedReader.readLine();

                   if(line.contains("$")){
                       String url = line.substring(line.indexOf("=")+1,line.indexOf("$"));
                       String item = line.substring(line.lastIndexOf("=")+1,line.length());
                       exam.setExameName(item);
                       exam.setSiteName(url);
                       String examType = url.substring(url.indexOf("//")+2,url.lastIndexOf("/"));
                       String examId = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
                       exam.setExamType(examType);
                       exam.setExamId(Integer.parseInt(examId));
                       examList.add(exam);
                   }
                   num++;
               }
                System.out.println(num);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    if (ifs != null) {
                        ifs.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Test
    public void parse() throws UnsupportedEncodingException {
       File file = new File("D:/excel/1/2");
       file.mkdirs();



    }


}