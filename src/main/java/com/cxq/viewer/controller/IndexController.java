package com.cxq.viewer.controller;

import com.cxq.viewer.domain.Thesis;
import com.cxq.viewer.domain.Users;
import com.cxq.viewer.services.ThesisService;
import com.cxq.viewer.services.UserService;
import com.cxq.viewer.utils.AuthenticationUtil;
import com.cxq.viewer.utils.GUIDUtil;
import com.cxq.viewer.utils.RequestUtil;
import com.cxq.viewer.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private ThesisService thesisService;
    @GetMapping("/")
    public String start(){
        return "redirect:/index";

    }

    @RequestMapping(value = "/login",method = { RequestMethod.GET })
    public String dologin()
    {

        System.out.println("run in login");
        return "redirect:/user/login";
    }


    @GetMapping("/user/login")
    public String login(){
        return "login.html";

    }

    @GetMapping("/user/create")
    public String create(){
        return "create.html";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestParam("username")String username, @RequestParam("password")String password){
        Users user=userService.getUserByName(username);
        if(user!=null){
            return "该用户已存在";
        }
        Users user1=new Users();
        user1.setId(GUIDUtil.generateGUID());
        user1.setName(username);
        user1.setPassword(password);
        user1.setStatus("1");
        userService.addUser(user1);
        return  "注册成功";
    }


    @GetMapping("/user/exit")
    public String exit(){
        return "login.html";

    }

    @GetMapping("/import")
    public String Import(Model model){
        Users currUser = UserUtil.getCurrUser();
        String userName=currUser.getName();
        model.addAttribute("userName",userName);
        model.addAttribute("mclass","import");
        return "import.html";

    }


    @GetMapping("/index")
    public String index()
    {
        String role = AuthenticationUtil.getUserRole();
        System.out.println("角色为"+role);
        if("[ROLE_ADMIN]".equals(role)){
            System.out.println("到admin界面了");
            return "redirect:/fileImport";
        }else{
            System.out.println("到index了");

            return "redirect:/import";

        }
    }



    @PostMapping("/prelogin")
    @ResponseBody
    public String adminPrelogin(@RequestParam("username")String username, @RequestParam("password")String password){
        System.out.println("-------------------------------------"+username);
        return  userService.preLogin(username,password);
    }

    @PostMapping("/user/upload")
    @ResponseBody
    public String SingeUpload(@RequestParam("fileName") String fileName,@RequestParam("owner") String owner,@RequestParam("file") MultipartFile file)
    {

        System.out.println(file.getSize());

        //InputStream in = null;
        //OutputStream out = null;

        try {
            byte[] files=file.getBytes();

           // in = file.getInputStream();

            File targetFile = new File("d:/upload" );
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            FileOutputStream out = new FileOutputStream( "d:/upload/"+ fileName);
            out.write(files);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        String s=GUIDUtil.generateGUID();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Thesis thesis=new Thesis();
        thesis.setId(s);
        thesis.setCreatetime(df.format(new Date()));
        thesis.setStatus("1");
        thesis.setOwner(owner);
        thesis.setName(fileName);
        thesisService.addThesis(thesis);
        return "上传成功";

    }

    @GetMapping("/read")
    public String read(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum,Model model){
        Integer page = Integer.parseInt(pageNum);
        Users currUser = UserUtil.getCurrUser();
        String userName=currUser.getName();

        List<Thesis> list=thesisService.getThesis(null,userName,"1",page);
        int totalPage=thesisService.getTotalPage(null,userName,"1");
        model.addAttribute("importList",list);
        model.addAttribute("currPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("userName",userName);
        model.addAttribute("mclass","read");
        return "read.html";
    }

    @PostMapping("/openFile")
    @ResponseBody
    public String openFile(@RequestParam("fileName") String fileName){
        Runtime runtime = Runtime.getRuntime();

        String cmd = "cmd /c start D:\\upload\\"+fileName;//要打开的文件路径。
        try {
            runtime.exec(cmd);
        } catch (Exception e) {
            System.out.println("Error exec!");
        }
        return "打开成功";
    }

    @GetMapping("/check")
    public String check(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum,Model model){
        Integer page = Integer.parseInt(pageNum);

        Users currUser = UserUtil.getCurrUser();
        String userName=currUser.getName();
        List<Thesis> list=thesisService.getThesis(null,userName,"1",page);
        int totalPage=thesisService.getTotalPage(null,userName,"1");
        model.addAttribute("importList",list);
        model.addAttribute("currPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("userName",userName);
        model.addAttribute("mclass","check");
        return "check.html";
    }

    @GetMapping("/modify")
    public String modify(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum,Model model){
        Integer page = Integer.parseInt(pageNum);

        Users currUser = UserUtil.getCurrUser();
        String userName=currUser.getName();
        List<Thesis> list=thesisService.getThesis(null,userName,"2",page);
        int totalPage=thesisService.getTotalPage(null,userName,"2");
        model.addAttribute("importList",list);
        model.addAttribute("currPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("userName",userName);
        model.addAttribute("mclass","modify");
        return "modify.html";
    }

    @PostMapping("/openFile1")
    @ResponseBody
    public String openFile1(@RequestParam("fileName") String fileName){
        Runtime runtime = Runtime.getRuntime();

        String cmd = "cmd /c start D:\\check\\"+fileName;//要打开的文件路径。
        try {
            runtime.exec(cmd);
        } catch (Exception e) {
            System.out.println("Error exec!");
        }
        return "打开成功";
    }

    @GetMapping("/export")
    public String export(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum,Model model){
        Integer page = Integer.parseInt(pageNum);

        Users currUser = UserUtil.getCurrUser();
        String userName=currUser.getName();
        List<Thesis> list=thesisService.getThesis(null,userName,"2",page);
        int totalPage=thesisService.getTotalPage(null,userName,"2");
        model.addAttribute("importList",list);
        model.addAttribute("currPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("userName",userName);
        model.addAttribute("mclass","export");
        return "export.html";
    }

    @GetMapping("download")
    @ResponseBody
    public String downloadFile(@RequestParam("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) {
            File file = new File("d:/check/" + fileName);

                response.setContentType("application/force-download");// 设置强制下载不打开
                try {
                    response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
                } catch (IOException e) {
                    e.printStackTrace();
                }// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;

                try {

                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    os.flush();
                    os.close();
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }


        return "下载失败";
    }

    @PostMapping("/deleteThesis")
    @ResponseBody
    public String deleteThesis(@RequestParam("id") String id){
        int t=thesisService.deleteThesis(id);
        return "删除成功";
    }



}
