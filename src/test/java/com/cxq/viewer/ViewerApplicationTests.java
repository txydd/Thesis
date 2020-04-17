package com.cxq.viewer;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URLDecoder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewerApplicationTests {
    @Test
    public void test() throws Exception{

        String fileName="20161344047汤小奕 实验一.doc";
        String path="C:\\Program Files\\Microsoft Office\\Office16\\WINWORD.EXE";
        Runtime.getRuntime().exec(path + " " + fileName);



    }


}
