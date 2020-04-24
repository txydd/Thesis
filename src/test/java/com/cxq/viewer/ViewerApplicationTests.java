package com.cxq.viewer;


import com.cxq.viewer.domain.Entropy;
import com.cxq.viewer.services.EntropyService;
import com.cxq.viewer.utils.IndexManager;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URLDecoder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewerApplicationTests {
    @Autowired
    private EntropyService entropyService;
    @Test
    public void test() throws Exception{

        String fileName="20161344047汤小奕 实验一.doc";
        String path="C:\\Program Files\\Microsoft Office\\Office16\\WINWORD.EXE";
        Runtime.getRuntime().exec(path + " " + fileName);



    }

    @Test
    public void test1() throws Exception{


        FSDirectory directory = FSDirectory.open(new File("D:/file").toPath());


try{
        IndexReader indexReader = DirectoryReader.open(directory);
    indexReader.close();

    System.out.println(indexReader.numDocs());}
        catch(Exception e){
    e.printStackTrace();

        }


    }
    @Test
    public void test3() throws Exception{
       /* Query query = new TermQuery(new Term("name", "asd2"));
        excQuery(query);
        IndexManager.addDocument("D:/file", "E:/Spring/papers/target/papers-1.0-SNAPSHOT/upload/papers/42简历.docx");
        addDocument("D:/file"+"asd2.docx");*/
        Entropy entropy=entropyService.getEntropy("test");
        String s=entropy.getEdata();
        int t=Integer.parseInt(s.substring(0,s.length()-1));

        double edata= (double) t/100;
        System.out.println("------------"+edata);
    }


}
