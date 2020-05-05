package com.cxq.viewer.utils;




import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

public class WordUtil {
    public static void changeWord(String name){
                 try{
                         String pdfFile = "D:\\upload\\"+name;
                         PDDocument doc = PDDocument.load(new File(pdfFile));
                         int pagenumber = doc.getNumberOfPages();
                         pdfFile = pdfFile.substring(0, pdfFile.lastIndexOf("."));
                         String fileName = pdfFile+".doc";
                         File file = new File(fileName);
                         if (!file.exists()){
                             file.createNewFile();
                             }
                         FileOutputStream fos = new FileOutputStream(fileName);
                         Writer writer = new OutputStreamWriter(fos, "UTF-8");
                         PDFTextStripper stripper = new PDFTextStripper();
                         stripper.setSortByPosition(true);// 排序
                         stripper.setStartPage(1);// 设置转换的开始页
                         stripper.setEndPage(pagenumber);// 设置转换的结束页
                         stripper.writeText(doc, writer);
                         writer.close();
                         doc.close();
                         System.out.println("pdf转换word成功！");
                     }
                 catch (IOException e){
                         e.printStackTrace();
                     }
             }

    public static void changeWord1(String name){
        try{
            String pdfFile = "D:\\file\\"+name;
            PDDocument doc = PDDocument.load(new File(pdfFile));
            int pagenumber = doc.getNumberOfPages();
            pdfFile = pdfFile.substring(0, pdfFile.lastIndexOf("."));
            String fileName = pdfFile+".doc";
            File file = new File(fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(fileName);
            Writer writer = new OutputStreamWriter(fos, "UTF-8");
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);// 排序
            stripper.setStartPage(1);// 设置转换的开始页
            stripper.setEndPage(pagenumber);// 设置转换的结束页
            stripper.writeText(doc, writer);
            writer.close();
            doc.close();
            System.out.println("pdf转换word成功！");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
