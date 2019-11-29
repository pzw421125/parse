package cn.pa.jsoup;


import cn.pa.jsoup.PoJo.Option;
import cn.pa.jsoup.PoJo.Question;
import org.apache.poi.hssf.usermodel.*;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import static org.junit.Assert.*;


public class JsoupDemoTest {
    @Test
    public void  testJsoup01() throws IOException {

        File pageFile = new File("D:\\test\\aa.html");
//        File pageFile = new File("D:/age/"+path+".html");
        Document doc = Jsoup.parse(pageFile,"utf-8");
        Element titleEle = doc.getElementsByClass("h2").first();
        String examName = titleEle.text();
//        Elements elements = doc.getElementsByClass("subject-con  bor clearfix m-question disabled");
//        String right =  elements.first().getElementsByClass("right").text();
//        elements.first().getElementsByClass("so-text").text();
//        elements.first().getElementsByClass("wenzi").text();
//        String s1 = elements.get(1).getElementsByClass("sub-dotitle").text();
//        System.out.println(s1+"s1");
//        List<String> question = new ArrayList<String>();
//        System.out.println("question"+elements.size());
        Elements elements = doc.getElementsByClass("sub-dotitle");
        String right =  elements.first().nextElementSibling().toString();
        String option0 = elements.get(0).nextElementSibling()
                .getElementsByTag("dd").attr("data-value");
        Elements e1 =  elements.get(0).nextElementSibling().nextElementSibling()
                .getElementsByClass("right");

        String so = elements.first().getElementsByClass("so-text").text();
        String wenzi = elements.first().getElementsByClass("wenzi").text();
        String s1 = elements.get(1).text();
        String examId = elements.get(0).attr("data-examid");
        //System.out.println("right"+right);
        System.out.println("option"+option0);
        //System.out.println("so-text"+so);
        //System.out.println("wenzi"+wenzi);
        //System.out.println(s1+"s1");

        List<Question> questionList = new ArrayList<Question>();
        System.out.println("question"+elements.size());

        Elements questionElement ;
        for (int i = 0; i <elements.size() ; i++) {
            //questionElement =elements.get(0).nextElementSibling().getElementsByTag("dd");
            questionElement =elements.get(i).nextElementSibling().getElementsByTag("dd");

            List<Option> options = new ArrayList<Option>();
            int rightNum = 0;
            for (int j = 0; j <questionElement.size() ; j++) {
                String ss = questionElement.get(j).attr("data-value");

                Option option = new Option();
                option.setOption(ss);
                option.setOptionContent(questionElement.get(j).text());
                if(!questionElement.get(j).getElementsByClass(
                        "m-question-option  right-this").isEmpty()){
                    option.setOptionFlag(true);
                    rightNum ++;
                }else if(!questionElement.get(j).getElementsByClass(
                        "m-question-option cho-this right-this").isEmpty()){
                    option.setOptionFlag(true);
                    rightNum --;

                }
                else {
                    option.setOptionFlag(false);
                }

            }
            Question question =new Question() ;

            //设置问题题目
            String ques = elements.get(i).text();
            ques = ques.substring(ques.lastIndexOf("]")+1,ques.length()).trim();
            question.setQuestionItem(ques);
            //设置问题选项
            question.setOptions(options);
            //问题数量
            question.setOptionNum(options.size());
            //设置考试问题
            question.setExamItem(examName);
            String examid = doc.getElementsByAttribute("data-examid").first().attr("data-examid");
            //设置考试ID
            question.setQuestionId(i+1);
            question.setExamId(Integer.parseInt(examid));
            String answer= doc.getElementsByClass("right").get(i).
                    getElementsByTag("em").text();
            //设置问题答案ri
            question.setAnswer(answer);
            //设置问题类型
            String quetionType = "";

            if(rightNum<0){
                quetionType="p";
            }else if(rightNum==1){
                quetionType="s";
            }else{
                quetionType="m";
            }

            question.setQuestionType(quetionType);
            String desc = doc.getElementsByClass("wenzi").get(i).text();
            question.setQuestionDesc(desc);
            questionList.add(question);
        }
        //System.out.println(question.toString());
        System.out.println("试题"+questionList.get(1).toString());

        String filePath="D:/test/2018zqzt.xls";
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("单选");
        Row row = sheet.createRow(0);// 创建行,从0开始
        Cell cell = row.createCell(0);// 创建行的单元格,也是从0开始
        cell.setCellValue("证券考试");
        CellStyle cs=workbook.createCellStyle();
        cs.setAlignment(HorizontalAlignment.LEFT);
        cs.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setCellStyle(cs);
        sheet.setColumnWidth(0,75*256);
        sheet.setColumnWidth(1,55*256);
        sheet.setColumnWidth(3,80*256);

        CellStyle cellStyleRed = workbook.createCellStyle();
        cellStyleRed.setFillPattern(SOLID_FOREGROUND);
        cellStyleRed.setFillForegroundColor(IndexedColors.RED.getIndex());

        CellStyle cellStyleGreen = workbook.createCellStyle();
        cellStyleGreen.setFillPattern(SOLID_FOREGROUND);
        cellStyleGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        cs.setWrapText(true);
        Row row1 = sheet.createRow(1);
        Cell cell10 = row1.createCell(1);

        cell10.setCellValue(questionList.get(0).getExamItem());
        //题目编号
        Cell cell20 = row.createCell(0);
        //cell20.setCellValue("题目编号");
        Cell cell21 = row.createCell(0);
        cell21.setCellValue("考试题目");

        Cell cell22 = row.createCell(1);
        cell22.setCellValue("答案选项");
        Cell cell23 = row.createCell(2);
        cell23.setCellValue("是否正确");
        Cell cell24 = row.createCell(3);
        cell24.setCellValue("题目解析");

        int rowNum = 1;
        for (int i = 0; i <questionList.size() ; i++) {
            Row rown = sheet.createRow(rowNum++);

            int  questionId = questionList.get(i).getQuestionId();
            rown.createCell(0).setCellValue(questionId);
            String questionItem = questionList.get(i).getQuestionItem();
            rown.createCell(0).setCellValue(questionItem);
            int questionNum = questionList.get(i).getOptionNum();
            String questype = questionList.get(i).getQuestionType();
//            if(questype=="m"){
////                rown.setRowStyle(cellStyleRed);
////            }
            for (int j = 0; j <questionNum ; j++) {

                if(j==0){
                    String answeOption = questionList.get(i).getOptions().get(j).getOptionContent();
                    answeOption = answeOption.substring(answeOption.indexOf(".")+1);
                    rown.createCell(1).setCellValue(answeOption);
                    Boolean isY = questionList.get(i).getOptions().get(j).getOptionFlag();
                    rown.createCell(2).setCellValue(isY ? "Y" : "N");
                }else{
                    Row rowj = sheet.createRow(rowNum++);
                    String answeOption = questionList.get(i).getOptions().get(j).getOptionContent();
                    answeOption = answeOption.substring(answeOption.indexOf(".")+1);
                    rowj.createCell(1).setCellValue(answeOption);
                    Boolean isY = questionList.get(i).getOptions().get(j).getOptionFlag();
                    rowj.createCell(2).setCellValue(isY ? "Y" : "N");
                }
                String questionDesc = questionList.get(i).getQuestionDesc();
                rown.createCell(3).setCellValue(questionDesc);
            }

        }

        workbook.write(new FileOutputStream(new File(filePath)));
        System.out.println("文件写入成功");
        workbook.close();

    }

    @Test
    public void testPOI() throws IOException {
        String filePath="E:/poi/2018qzt.xls";
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("单选");

        Row row = sheet.createRow(0);// 创建行,从0开始
        Cell cell = row.createCell(0);// 创建行的单元格,也是从0开始
        cell.setCellValue("证券考试");
        //设置样式：

        sheet.setColumnWidth(1,75*256);
        sheet.setColumnWidth(2,75*256);
        sheet.setColumnWidth(4,80*256);
        CellStyle cs=workbook.createCellStyle();
        cs.setWrapText(true);
        cs.setAlignment(HorizontalAlignment.LEFT);
        cs.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setCellStyle(cs);
        Row row1 = sheet.createRow(1);
        Cell cell10 = row1.createCell(1);


        CellStyle cellStyleRed = workbook.createCellStyle();
        cellStyleRed.setFillPattern(SOLID_FOREGROUND);
        cellStyleRed.setFillForegroundColor(IndexedColors.RED.getIndex());

        CellStyle cellStyleGreen = workbook.createCellStyle();
        cellStyleGreen.setFillPattern(SOLID_FOREGROUND);
        cellStyleGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());

        cell10.setCellValue("证券考试");
        //题目编号
        Cell cell20 = row.createCell(0);
        cell20.setCellValue("题目编号");
        Cell cell21 = row.createCell(1);
        cell21.setCellValue("考试题目");
        cell21.setCellStyle(cellStyleGreen);
        Cell cell22 = row.createCell(2);
        cell21.setCellValue("答案选项");
        Cell cell23 = row.createCell(3);
        cell23.setCellValue("是否正确");
        Cell cell24 = row.createCell(4);
        cell24.setCellValue("题目解析");


        workbook.write(new FileOutputStream(new File(filePath)));
        System.out.println("文件写入成功");
        workbook.close();

    }
    @Test
    public void stringTest(){
        String  questionItem = "2 [单选题 长期国债的偿还期一般为(　　)。";
        questionItem = questionItem.substring(questionItem.lastIndexOf("]")+1,questionItem.length()).trim();

        String answer = "A.深圳证券交易所";
        answer = answer.substring(answer.indexOf(".")+1);
        System.out.println(answer);
        System.out.println(questionItem);
    }


}
