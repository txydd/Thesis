package com.cxq.viewer.utils;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class GetWorld {
    /**
     * 索引库位置
     */
    public static String indexLibraryPath = "D:/file/";
    /**
     * 文件位置
     */
    public static String readFilePath = "E:/Spring/papers/target/papers-1.0-SNAPSHOT/upload/papers";

    /**
     * 判断文件类型并获取文件内容
     * @param path
     * @return
     */
    public static String JudgingFileType(String path) throws Exception{
        String type =  path.substring(path.lastIndexOf(".")+1);
        String content ="";
        if (type.equals("docx")){
            InputStream is = new FileInputStream(path);
            XWPFDocument xdoc = new XWPFDocument(is);
            XWPFWordExtractor ex = new XWPFWordExtractor(xdoc);
            content = ex.getText();
            return content;
        }else if (type.equals("doc")){
            InputStream fis = new FileInputStream(path);
            HWPFDocument doc = new HWPFDocument(fis);
            content = doc.getDocumentText().toString();
            return content;
        }else if (type.equals("txt")){
            File f = new File(path);
            content = FileUtils.readFileToString(f,"utf-8");
            return content;
        }else {
            return "err";
        }
    }
}
