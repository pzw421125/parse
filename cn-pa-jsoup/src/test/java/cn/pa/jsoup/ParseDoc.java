package cn.pa.jsoup;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseDoc {
    public static void main(String[] args) {
        XWPFWordExtractor docx = null;
        try {
            docx = new XWPFWordExtractor(POIXMLDocument.openPackage("D:\\txt\\冠状动脉粥样硬化性心脏病.docx"));
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
    }

}
