package cn.pa.jsoup.Utils;

import cn.pa.jsoup.PoJo.Exam;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class FileUtils {

    public static HashSet<Exam> getExam(String filePath){

            //读取网页jason数据
            File[] files = new File(filePath).listFiles();
            FileInputStream ifs = null;
            InputStreamReader in = null;
            BufferedReader bufferedReader = null;
            HashSet examList = new HashSet<>();
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
                        int begin =  line.indexOf("=");
                        int second = line.lastIndexOf("=");
                        String url = line.substring(line.indexOf("=")+1,line.indexOf("$"));
                        String item = line.substring(line.lastIndexOf("=")+1,line.length());
                        exam.setExameName(item);
                        exam.setSiteName(url);
                        String examType = url.substring(url.indexOf("/")+2,url.lastIndexOf("/"));
                        String examId = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
                        exam.setExamType(examType);
                        exam.setExamId(Integer.parseInt(examId));
                        examList.add(exam);
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
            return examList;
        }

    public static void main(String[] args) {
        String filePath=  "D:/page/yk";
        System.out.println(getExam(filePath));


    }


}
