package com.cxq.viewer.controller;

import com.cxq.viewer.domain.*;
import com.cxq.viewer.services.EntropyService;
import com.cxq.viewer.services.FileService;
import com.cxq.viewer.services.RecordService;
import com.cxq.viewer.services.UserService;
import com.cxq.viewer.utils.GUIDUtil;
import com.cxq.viewer.utils.GetWordCount;
import com.cxq.viewer.utils.IndexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private EntropyService entropyService;

    @GetMapping("/fileImport")
    public String index(Model model){
        model.addAttribute("userName","admin");
        model.addAttribute("mclass","fileImport");
        return "fileImport.html";
    }

    @PostMapping("/admin/upload")
    @ResponseBody
    public String SingeUpload(@RequestParam("fileName") String fileName, @RequestParam("keyWord") String keyWord, @RequestParam("file") MultipartFile file) throws Exception
    {



        //InputStream in = null;
        //OutputStream out = null;

        try {
            byte[] files=file.getBytes();

            // in = file.getInputStream();

            File targetFile = new File("d:/file" );
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            FileOutputStream out = new FileOutputStream( "d:/file/"+ fileName);
            out.write(files);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        String s= GUIDUtil.generateGUID();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Files files=new Files();
       File file1=new File("d:/file/"+ fileName);
       int t= GetWordCount.wordCount(file1);
       files.setNumCount(t);
       files.setKeyWord(keyWord);
       files.setId(s);
       files.setCreatetime(df.format(new Date()));
       files.setName(fileName);
        fileService.addFile(files);
        IndexManager.addDocument("d:/file",
                "d:/file/"+fileName);
        return "上传成功";

    }

    @GetMapping("/fileManage")
    public String fileManage(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum,@RequestParam(value="searchName",required=false) String searchName, Model model){
        if(searchName!=null&&searchName.equals("")){
            searchName=null;
        }
        Integer page = Integer.parseInt(pageNum);

        List<Files> list=fileService.getFile(searchName,page);
        int totalPage=fileService.getTotalPage(searchName);
        model.addAttribute("fileList",list);
        model.addAttribute("searchName",searchName);
        model.addAttribute("currPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("mclass","fileManage");
        model.addAttribute("userName","admin");
        return "fileManage.html";
    }

    @PostMapping("/openFile2")
    @ResponseBody
    public String openFile(@RequestParam("fileName") String fileName){
        Runtime runtime = Runtime.getRuntime();
        System.out.println("打开的文件名是----------------------"+fileName);

        String cmd = "cmd /c start D:\\file\\"+fileName;//要打开的文件路径。
        try {
            runtime.exec(cmd);
        } catch (Exception e) {
            System.out.println("Error exec!");
        }
        return "打开成功";
    }

    @PostMapping("/deleteFile")
    @ResponseBody
    public String deleteFile(@RequestParam("id") String id){
       int t=fileService.deleteFile(id);
        return "删除成功";
    }

    @GetMapping("/userManage")
    public String userManage(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum,@RequestParam(value="searchName",required=false) String searchName, Model model){
        if(searchName!=null&&searchName.equals("")){
            searchName=null;
        }
        Integer page=Integer.parseInt(pageNum);
        List<Users> list=userService.getUser(searchName,page);
        int totalPage=userService.getTotalPage(searchName);
        model.addAttribute("userList",list);
        model.addAttribute("searchName",searchName);
        model.addAttribute("currPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("mclass","userManage");
        model.addAttribute("userName","admin");
        return "userManage.html";

    }

    @PostMapping("/changeStatus")
    @ResponseBody
    public String changeStatus(@RequestParam("name") String name,@RequestParam("status") String status){
        Users users=userService.getUserByName(name);
        users.setStatus(status);
        userService.updateUser(users);
        return "修改成功";
    }

    @GetMapping("/record")
    public String record(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum,@RequestParam(value="searchName",required=false) String searchName,@RequestParam(value="searchUser",required=false) String searchUser,@RequestParam(value="chanceMin",required=false) String chanceMin,@RequestParam(value="chanceMax",required=false) String chanceMax,@RequestParam(value="startTime",required=false) String startTime,@RequestParam(value="endTime",required=false) String endTime,Model model){
        if(searchName!=null&&searchName.equals("")){
            searchName=null;
        }
        if(searchUser!=null&&searchUser.equals("")){
            searchUser=null;
        }
        if(chanceMin==null){
            chanceMin="";
        }
        if(chanceMax==null){
            chanceMax="";
        }
        Integer page=Integer.parseInt(pageNum);
        List<Record> list=recordService.getRecord1(searchName,searchUser,chanceMin,chanceMax,startTime,endTime,null,page);
        int totalPage=recordService.getTotalPage1(searchName,searchUser,chanceMin,chanceMax,startTime,endTime,null);
        model.addAttribute("recordList",list);
        model.addAttribute("searchName",searchName);
        model.addAttribute("searchUser",searchUser);
        model.addAttribute("chanceMin",chanceMin);
        model.addAttribute("chanceMax",chanceMax);
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
        model.addAttribute("currPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("mclass","record");
        model.addAttribute("userName","admin");
        return "record.html";

    }

    @GetMapping("/successKey")
    public String successKey(Model model){
        Entropy entropy=entropyService.getEntropy("test");
        String edata=entropy.getEdata();
        model.addAttribute("key",edata);
        model.addAttribute("mclass","successKey");

        model.addAttribute("userName","admin");
        return "successKey.html";

    }

    @PostMapping("/changeEdata")
    @ResponseBody
    public String changeEdata(@RequestParam("edata") String edata){
        Entropy entropy=entropyService.getEntropy("test");
        entropy.setEdata(edata);
        entropyService.updateEntropy(entropy);
        return "修改成功";
    }


}
