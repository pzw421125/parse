package cn.pa.jsoup.Utils;

import cn.pa.jsoup.PoJo.Question;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class POIUtils2 {

    public Sheet createSheet(Workbook workbook,String questionType){
        Sheet sheet =workbook.createSheet(questionType);
        Row row = sheet.createRow(0);// 创建行,从0开始
        Cell cell = row.createCell(0);// 创建行的单元格,也是从0开始
        CellStyle cs=workbook.createCellStyle();
        cs.setAlignment(HorizontalAlignment.LEFT);
        cs.setVerticalAlignment(VerticalAlignment.TOP);

        sheet.setColumnWidth(0,75*256);
        sheet.setColumnWidth(1,60*256);
        sheet.setColumnWidth(3,80*256);
        cs.setWrapText(true);
        cell.setCellStyle(cs);
        Row row1 = sheet.createRow(1);
        Cell cell10 = row1.createCell(1);

        Cell cell21 = row.createCell(0);
        cell21.setCellValue("考试题目");

        Cell cell22 = row.createCell(1);
        cell22.setCellValue("答案选项");
        Cell cell23 = row.createCell(2);
        cell23.setCellValue("是否正确");
        Cell cell24 = row.createCell(3);
        cell24.setCellValue("题目解析");

        return sheet;
    }
    public static void exportToExcel(List<Question> questionList, String examType,
                                     String exportPath, String questionType) {
        String filePath=exportPath;
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(questionType);

        Row row = sheet.createRow(0);// 创建行,从0开始
        Cell cell = row.createCell(0);// 创建行的单元格,也是从0开始
        cell.setCellValue(examType);
        CellStyle cs=workbook.createCellStyle();
        cs.setAlignment(HorizontalAlignment.LEFT);
        cs.setVerticalAlignment(VerticalAlignment.TOP);

        sheet.setColumnWidth(0,75*256);
        sheet.setColumnWidth(1,60*256);
        sheet.setColumnWidth(3,80*256);
        cs.setWrapText(true);
        cell.setCellStyle(cs);
        Row row1 = sheet.createRow(1);
        Cell cell10 = row1.createCell(1);

        cell10.setCellValue(questionList.get(0).getExamItem());
        //题目编号
//        Cell cell20 = row.createCell(0);
//        cell20.setCellValue("题目编号");
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
//            int  questionId = questionList.get(i).getQuestionId();
//            rown.createCell(0).setCellValue(questionId);
            String questionItem = questionList.get(i).getQuestionItem();
            rown.createCell(0).setCellValue(questionItem);
            int questionNum = questionList.get(i).getOptionNum();
            for (int j = 0; j <questionNum ; j++) {

                if(j==0){
                    String answeOption = questionList.get(i).getOptions().get(j).getOptionContent();
                    //去掉前面的abcd
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

    }

}
