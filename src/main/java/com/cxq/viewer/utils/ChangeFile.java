package com.cxq.viewer.utils;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.documents.*;
import com.spire.doc.fields.TextRange;


import java.awt.*;
import java.util.List;
import java.util.Map;

public class ChangeFile {
    public static void changeRed(String fileName,List<String> list) {



        //加载Word文档
        Document document = new Document("D:\\upload\\"+fileName);

        //查找所有文本
        for(int i=0;i<list.size();i++) {
            String s=list.get(i);
            if(!s.equals("")) {
                TextSelection[] textSelections = document.findAllString(list.get(i), false, false);

                //设置高亮颜色
                for (TextSelection selection : textSelections) {
                    selection.getAsOneRange().getCharacterFormat().setTextColor(Color.red);

                }
            }
        }

        //保存文档
        document.saveToFile("D:\\check\\"+fileName, FileFormat.Docx_2013);

    }


}
