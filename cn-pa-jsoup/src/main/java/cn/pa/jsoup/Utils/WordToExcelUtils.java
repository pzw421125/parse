package cn.pa.jsoup.Utils;



import cn.pa.jsoup.PoJo.Question;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordToExcelUtils {



    public static String getWordContentByName(String fileName){
        XWPFWordExtractor docx = null;
        try {
            docx = new XWPFWordExtractor(POIXMLDocument.openPackage(fileName));
        } catch (XmlException e) {
            e.printStackTrace();
        } catch (OpenXML4JException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //提取.docx正文文本
        String text = docx.getText();
        List<String> content = new ArrayList<String>();
        //分解题目内容
        System.out.println("解析DOCX格式的word文档！"+text);
        return text;
    }


    public static List<String> findFiles(String fileDir){
        List<String> fileNameList = new ArrayList<String>();
        //指定目录下的文件名列表
        File dir = new File(fileDir);
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isFile()){
                String fileName = file.getAbsolutePath();
                fileNameList.add(fileName);
            }
        }
        return fileNameList;
    }
    public static void main(String[] args) {
        //从文件夹读取文件
        while(true){
            System.out.println("请输入文件夹路径");
            Scanner scanner = new Scanner(System.in);
            String path = scanner.nextLine();
            List<String> fileNameList = findFiles(path);
            System.out.println(fileNameList.toString());
            for (int i = 0; i <fileNameList.size() ; i++) {
                String fileName = fileNameList.get(i);
                String wordContent = getWordContentByName(fileName);
                System.out.println(wordContent);
                List<Question> questionList = regexWordUtils.SeptoQuestion(wordContent,"【正确答案】");
                String exportFileName = fileName.replace(".docx",".xls");
                POIUtils.exportToExcel(questionList,"医考",exportFileName,"单选");
            }
        }
    }
}
