package com.cxq.viewer.utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import org.apache.lucene.index.*;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import static com.cxq.viewer.utils.GetWorld.indexLibraryPath;


//论文查重
public class ThesisUtil {


    /**
     * 用余弦相似性计算文本相似度
     * @param searchTextTfIdfMap  查找文本的向量
     * @param allTfIdfMap   所有文本的向量
     * @return  计算出当前查询文本与所有文本的相似度
     */
    private static Map<String,Double> cosineSimilarity(Map<String, Float> searchTextTfIdfMap, HashMap<String, Map<String, Float>> allTfIdfMap)
    {
        //key是相似的文档名称，value是与当前文档的相似度
        Map<String,Double> similarityMap = new HashMap<String,Double>();
        //计算查找文本向量绝对值
        double searchValue = 0;
        for (Map.Entry<String, Float> entry : searchTextTfIdfMap.entrySet())
        {
            searchValue += entry.getValue() * entry.getValue();
        }

        for (Map.Entry<String, Map<String, Float>> docEntry : allTfIdfMap.entrySet())
        {
            //得到文档名
            String docName = docEntry.getKey();
            //得到tf-idf值
            Map<String, Float> docScoreMap = docEntry.getValue();
            double termValue = 0;
            double acrossValue = 0;
            for (Map.Entry<String, Float> termEntry : docScoreMap.entrySet())
            {
                //判断此文档是否有这个词
                if (searchTextTfIdfMap.containsKey(termEntry.getKey()))
                {
                    acrossValue += termEntry.getValue() * searchTextTfIdfMap.get(termEntry.getKey());
                }
                //计算各个文档的绝对值
                termValue += termEntry.getValue() * termEntry.getValue();
            }

            similarityMap.put(docName,acrossValue/(Math.sqrt(termValue) * Math.sqrt(searchValue)));
        }

        return similarityMap;
    }



    /**
     * 查重
     * @param path 需要查重的文件路径
     * @throws IOException
     * @throws Exception
     */
    public static double search(String path) throws IOException,Exception{
        File f = new File(path);
        String name = f.getName();
        //得到文档内容
        String content = GetWorld.JudgingFileType(path);
        //去除特殊符号
        content = content.replaceAll("[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]","");
        String filterStr = "`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ ";

        //使用HanLp分词
        List<Term> terms = HanLP.segment(content);
        List<String> list = terms.stream().map(a -> a.word).filter(s -> !filterStr.contains(s)).collect(Collectors.toList());
        //wordsFren存储每个词的词频
        Map<String,Integer> wordsFren = new HashMap<>();
        //计算词频
        for (String key:list) {
            if (wordsFren.containsKey(key)) {
                wordsFren.put(key,wordsFren.get(key)+1);
            }else {
                wordsFren.put(key,1);
            }
        }
        //提取关键词
        List<String> term = HanLP.extractKeyword(content,1000);

        //存放提取的关键词和它的词频
        Map<String ,Float> map = new HashMap<>();
        TFIDFSimilarity ts = new ClassicSimilarity();
        for (Map.Entry<String ,Integer> key:wordsFren.entrySet()) {
            if (term.contains(key.getKey())){
                map.put(key.getKey(),key.getValue()+0.0f);
            }
        }
        //调用getAllTfIdf获得所有文档的tf-idf值
        HashMap<String, Map<String, Float>> allTfIdfMap = getAllTfIdf(indexLibraryPath);
        //调用cosineSimilarity方法计算余弦相似度并返回此文档与各个文档的相似度
        Map<String ,Double> hashMap =  cosineSimilarity(map,allTfIdfMap);

        Float d1=0.00f,t1;
        List<Float> fl=new ArrayList<>();
        Float[] ff=new Float[5];
        List<String> word=new ArrayList<>();

        for (Map.Entry<String,Float> m : map.entrySet()) {
           fl.add(m.getValue());

        }
        for(int i=0;i<fl.size();i++){
           if(i<5){
               ff[i]=fl.get(i);
           }
           else{
               Arrays.sort(ff);
               if(fl.get(i)>ff[0]){
                   ff[0]=fl.get(i);
               }
           }
        }
        for(int i=0;i<5;i++){
            for (Map.Entry<String,Float> m : map.entrySet()) {
                if(m.getValue()==ff[i]){
                    word.add(m.getKey());
                    break;
                }
            }
        }
      

        Double d=0.00;
        for (Map.Entry<String, Double> m : hashMap.entrySet()) {
            if (m.getValue()>d){
                d=m.getValue();
            }
        }
        List<String> list1=SplitFile.splitFile(path,word,d);
        ChangeFile.changeRed(name,list1);
        return d;
    }

    /**
     * 计算出所有文档的tf-idf值
     * @param indexLibraryPath  指定索引库的位置
     * @return  所有文档的tf-idf值
     * @throws Exception
     */
    public static HashMap<String, Map<String, Float>> getAllTfIdf(String indexLibraryPath) throws Exception {
        //存储所有文档的tf-idf值
        HashMap<String, Map<String, Float>> scoreMap = new HashMap<String, Map<String, Float>>();
        //创建IndexReader对象
        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(new File(indexLibraryPath).toPath()));
        //循环根据文档数量次数
        for (int i = 0; i < indexReader.numDocs(); i++) {
            //存储当前文档的tf-idf值
            Map<String,Float> wordMap = new HashMap<String, Float>();
            //检索此文档和字段的术语向量，如果未对术语向量编制索引，则为null
            Terms termsFreq = indexReader.getTermVector(i, "content");
            //获得当前文档的名称
            String name = indexReader.document(i).get("name");
            //如果未对术语向量编制索引，就跳出本次循环
            if (termsFreq == null){
                continue;
            }
            //获取文档内容
            String content = indexReader.document(i).get("content");
            //提取关键词
            List<String> list = HanLP.extractKeyword(content,1000);
            //遍历所有术语的迭代器
            TermsEnum termsEnum = termsFreq.iterator();
            BytesRef thisTerm = null;
            while ((thisTerm = termsEnum.next()) != null) {
                //获得词
                String termText = thisTerm.utf8ToString();
                //获取当前的PostingsEnum
                PostingsEnum docsEnum = termsEnum.postings(null);
                Float tf = 0.0f ;
                Float idf = 0.0f;
                Float tf_idf = 0.0f;
                //算各个文档的tf-idf值
                while ((docsEnum.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
                    TFIDFSimilarity ts = new ClassicSimilarity();
                    if (list.contains(termText)) {
                        tf =docsEnum.freq()+0.0f;
                    }
                }
                wordMap.put(termText,tf);

            }
            scoreMap.put(name,wordMap);
        }
        return scoreMap;
    }
}
