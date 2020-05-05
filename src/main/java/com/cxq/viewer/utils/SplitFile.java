package com.cxq.viewer.utils;

import com.hankcs.hanlp.corpus.document.sentence.Sentence;

import java.util.ArrayList;
import java.util.List;

public class SplitFile {
    private String text = "";
    private int startOffset = 0;
    private int endOffset = 0;
    public SplitFile(String text) {
        this.text = text;
    }
    public String next()   {

        boolean start = true;
        for (int i = startOffset; i < text.length(); i++) {
            int type = Character.getType(text.charAt(i));
            switch (type) {
                case Character.UPPERCASE_LETTER:
                case Character.LOWERCASE_LETTER:
                case Character.TITLECASE_LETTER:
                case Character.MODIFIER_LETTER:
                case Character.OTHER_LETTER:
/*
* 1. 0x3041-0x30f6 -> ぁ-ヶ //日文(平|片)假名
* 2. 0x3105-0x3129 -> ㄅ-ㄩ
* //注意符号
*/
                    if (start) {
                        startOffset = i;
                        start = false;
                    }
                    break;
                case Character.LETTER_NUMBER:
// ⅠⅡⅢ 单分


                case Character.OTHER_NUMBER:
// ①⑩㈠㈩⒈⒑⒒⒛⑴⑽⑾⒇ 连着用


                default:
// 其它认为无效字符
                    if (!start) {
                        endOffset = i;
                        i = text.length() ;
                    }
            }// switch
        }
        String word = "";
        if (startOffset < text.length()) {
            if(endOffset<=startOffset) {
                endOffset=text.length()-1;
            }

            word = text.substring(startOffset, endOffset);
            startOffset = endOffset + 1;


        }


        return word;
    }

    public static List<String> splitFile(String path,String word,double chance){
        List<String> alist=new ArrayList<String>();
        try{
        String content = GetWorld.JudgingFileType(path);
        SplitFile sen = new SplitFile(content);
        String w = "";
        List<String> list=new ArrayList<String>();
        while (!(w = sen.next()).isEmpty()) {
            if(w.contains(word)){
                list.add(w);
            }
        }
        int t=(int)(list.size()*chance);

        for(int i=0;i<t;i++){
            alist.add(list.get(i));

        }
        } catch(Exception e){
            e.printStackTrace();
        }
        return alist;
    }


    public static void main(String[] args) throws Exception   {
       /* String content = GetWorld.JudgingFileType("D:\\check\\汤小奕问卷调查.docx");
        SplitFile sen = new SplitFile(content);
        String w = "";
        List<String> list=new ArrayList<String>();
        while (!(w = sen.next()).isEmpty()) {
            if(w.contains("就业")){
                list.add(w);
            }
        }
        int t=(int)(list.size()*0.3);
        for(int i=0;i<t;i++){
            System.out.println(list.get(i));
        }*/
        List<String> list1=SplitFile.splitFile("D:\\file\\汤小奕问卷调查.doc","就业",0.4);
        ChangeFile.changeRed("汤小奕问卷调查.doc",list1);


    }
}
