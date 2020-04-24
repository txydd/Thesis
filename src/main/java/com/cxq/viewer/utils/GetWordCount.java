package com.cxq.viewer.utils;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GetWordCount {
    public static int wordCount(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            //获取文件后缀名
            String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            //定义word内容
            String content = "";
            switch (suffix) {
                case "doc":
                    WordExtractor wordExtractor = new WordExtractor(fis);
                    content = wordExtractor.getText();
                    break;
                case "docx":
                    XWPFDocument document = new XWPFDocument(fis);
                    XWPFWordExtractor extractor = new XWPFWordExtractor(document);
                    content = extractor.getText();
                    break;
                default:
                    break;
            }
            fis.close();
            //中文单词
            String cnWords = content.replaceAll("[^(\\u4e00-\\u9fa5，。《》？；’‘：“”【】、）（……￥！·)]", "");
            int cnWordsCount = cnWords.length();
            //非中文单词
            String noCnWords = content.replaceAll("[^(a-zA-Z0-9`\\-=\';.,/~!@#$%^&*()_+|}{\":><?\\[\\])]", " ");
            int noCnWordsCount = 0;
            String[] ss = noCnWords.split(" ");
            for (String s : ss) {
                if (s.trim().length() != 0) {
                    noCnWordsCount++;
                }
            }
            //中文和非中文单词合计
            return cnWordsCount + noCnWordsCount;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;

    }
}
