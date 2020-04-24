package com.cxq.viewer.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Date;

@Slf4j
public class PDFUtil
{
    private static final int wdFormatPDF = 17;
    private static final int xlTypePDF = 0;
    private static final int ppSaveAsPDF = 32;
    public static final int EXCEL_HTML = 44;

    public static String convert2PDF(String fileName,String pdfName)
    {
        String inputFile = "d:/upload/" + fileName;
        System.out.println(inputFile);
        String kind = getFileSuffix(inputFile);
        String pdfFile = "d:/upload/" + pdfName + ".pdf";
        System.out.println(pdfFile);
        File file = new File(inputFile);
        if (!file.exists()) {
            return "-2";//文件不存在
        }
        if (kind.equals("pdf")) {
            if (copyFile(inputFile, pdfFile)){
                return "-1"; //复制失败
            }
            return "-3";//原文件就是PDF文件
        }
        if (kind.equals("doc") || kind.equals("docx") || kind.equals("txt")) {
            return String.valueOf(word2PDF(inputFile, pdfFile));
        } else if (kind.equals("ppt") || kind.equals("pptx")) {
            return String.valueOf(ppt2PDF(inputFile, pdfFile));
        } else if (kind.equals("xls") || kind.equals("xlsx")) {
            return String.valueOf(Ex2PDF(inputFile, pdfFile));
        } else {
            return "-4";
        }
    }

    public static String convert3PDF(String originalName)
    {
        String inputFile = "d:/upload/"+originalName;
        System.out.println(inputFile);
        String suffixName=originalName.substring(0,originalName.lastIndexOf("."));

        String kind = originalName.substring(originalName.lastIndexOf(".")+1);
        String pdfFile = "d:/upload/"+suffixName+".pdf";
        System.out.println(pdfFile);
        File file = new File(inputFile);
        if (!file.exists()) {
            return "-2";//文件不存在
        }
        if (kind.equals("pdf")) {
            if (copyFile(inputFile, pdfFile)){
                return "-1"; //复制失败
            }
            return "-3";//原文件就是PDF文件
        }
        if (kind.equals("doc") || kind.equals("docx") || kind.equals("txt")) {
            return String.valueOf(word2PDF(inputFile, pdfFile));
        } else if (kind.equals("ppt") || kind.equals("pptx")) {
            return String.valueOf(ppt2PDF(inputFile, pdfFile));
        } else if (kind.equals("xls") || kind.equals("xlsx")) {
            return String.valueOf(Ex2PDF(inputFile, pdfFile));
        } else if(kind.equals("jpg")||kind.equals("png")){
            return imgToPdf(inputFile,pdfFile);
        }
        else {
            return "-4";
        }
    }


    private static int word2PDF(String inputFile, String pdfFile)
    {
        try {
            // 打开Word应用程序
            ActiveXComponent app = new ActiveXComponent("Word.Application");
            System.out.println("开始转化Word为PDF...");
            long date = new Date().getTime();
            // 设置Word不可见
            app.setProperty("Visible", new Variant(false));
            // 禁用宏
            app.setProperty("AutomationSecurity", new Variant(3));
            // 获得Word中所有打开的文档，返回documents对象
            Dispatch docs = app.getProperty("Documents").toDispatch();
            // 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
            Dispatch doc = Dispatch.call(docs, "Open", inputFile, false, true).toDispatch();
            File tofile = new File(pdfFile);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.call(doc, "ExportAsFixedFormat", pdfFile, wdFormatPDF);// word保存为pdf格式宏，值为17
            System.out.println(doc);
            // 关闭文档
            long date2 = new Date().getTime();
            int time = (int) ((date2 - date) / 1000);

            Dispatch.call(doc, "Close", false);
            // 关闭Word应用程序
            app.invoke("Quit", 0);
            return time;
        } catch (Exception e) {
            log.error(e.getMessage());

            return -1;
        }

    }

    private static int Ex2PDF(String inputFile, String pdfFile)
    {
        try {

            ComThread.InitSTA(true);
            ActiveXComponent ax = new ActiveXComponent("Excel.Application");
            System.out.println("开始转化Excel为PDF...");
            long date = new Date().getTime();
            ax.setProperty("Visible", false);
            ax.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
            Dispatch excels = ax.getProperty("Workbooks").toDispatch();

            Dispatch excel = Dispatch.invoke(excels, "Open", Dispatch.Method, new Object[]{
                    inputFile, new Variant(false), new Variant(false)}, new int[9]).toDispatch();
            File toFile = new File(pdfFile);
            if (toFile.exists()) {
                toFile.delete();
            }
            Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method, new Object[]{
                    new Variant(0), // PDF格式=0
                    pdfFile, new Variant(xlTypePDF) // 0=标准 (生成的PDF图片不会变模糊) 1=最小文件
                    // (生成的PDF图片糊的一塌糊涂)
            }, new int[1]);
            long date2 = new Date().getTime();
            int time = (int) ((date2 - date) / 1000);
            Dispatch.call(excel, "Close", new Variant(false));

            if (ax != null) {
                ax.invoke("Quit", new Variant[]{});
                ax = null;
            }
            ComThread.Release();
            return time;
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }


    private static int ppt2PDF(String inputFile, String pdfFile)
    {
        try {
            ComThread.InitSTA(true);
            ActiveXComponent app = new ActiveXComponent("PowerPoint.Application");
//            app.setProperty("Visible", false);
            System.out.println("开始转化PPT为PDF...");
            long date = new Date().getTime();
            Dispatch ppts = app.getProperty("Presentations").toDispatch();
            Dispatch ppt = Dispatch.call(ppts, "Open", inputFile, true, // ReadOnly
                    //    false, // Untitled指定文件是否有标题
                    false// WithWindow指定文件是否可见
            ).toDispatch();
            File toFile = new File(pdfFile);
            if (toFile.exists()) {
                toFile.delete();
            }
            Dispatch.invoke(ppt, "SaveAs", Dispatch.Method, new Object[]{pdfFile,
                    new Variant(ppSaveAsPDF)}, new int[1]);
            System.out.println("PPT");
            Dispatch.call(ppt, "Close");
            long date2 = new Date().getTime();
            int time = (int) ((date2 - date) / 1000);
            app.invoke("Quit");
            return time;
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    public static int excelToHTML(String xlsfile, String htmlfile)
    {
        ActiveXComponent app = new ActiveXComponent("Excel.Application"); // 启动Excel
        try {
            long date = new Date().getTime();
            app.setProperty("Visible", new Variant(false));
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.invoke(excels, "Open", Dispatch.Method, new Object[]{xlsfile,
                    new Variant(false), new Variant(true)}, new int[1]).toDispatch();
//				Dispatch sheet = Dispatch.invoke(excel, "sheet(0)", arg2, arg3, arg4)
            Dispatch.invoke(excel, "SaveAs", Dispatch.Method, new Object[]{htmlfile,
                    new Variant(EXCEL_HTML)}, new int[1]);
            Variant f = new Variant(false);
            Dispatch.call(excel, "Close", f);
            long date2 = new Date().getTime();
            int time = (int) ((date2 - date) / 1000);
            app.invoke("Quit");
            return time;
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        } finally {
            app.invoke("Quit", new Variant[]{});
        }
    }


    public static String getFileSuffix(String fileName)
    {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
    }

    public static String getFileName(String filePath)
    {
        return filePath.substring(0, filePath.lastIndexOf('.'));
    }

    public static void delFile(String filename)
    {
        File file=new File("d:/upload/"+filename);
        if(file.exists()&&file.isFile())
        file.delete();
    }

    public static boolean copyFile(String inPath, String outPath)
    {
        try{
            log.info("正在进行pdf复制操作------------...");
            Files.copy(new File(inPath).toPath(), new File(outPath).toPath());
            log.info(" pdf复制操作已完成------------...");
            return true;
        }catch (Exception e){
            log.error(" pdf复制操作失败-------------...", e);
            return false;
        }
    }

    public static String imgToPdf(String imgFilePath, String pdfFilePath) {
        try {
            File file = new File(imgFilePath);
            if (file.exists()) {
                Document document = new Document();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(pdfFilePath);
                    PdfWriter.getInstance(document, fos);

// 添加PDF文档的某些信息，比如作者，主题等等
                    document.addAuthor("arui");
                    document.addSubject("test pdf.");
// 设置文档的大小
                    document.setPageSize(PageSize.A4);
// 打开文档
                    document.open();
// 写入一段文字
//document.add(new Paragraph("JUST TEST ..."));
// 读取一个图片
                    Image image = Image.getInstance(imgFilePath);
                    float imageHeight = image.getScaledHeight();
                    float imageWidth = image.getScaledWidth();
                    int i = 0;
                    while (imageHeight > 500 || imageWidth > 500) {
                        image.scalePercent(100 - i);
                        i++;
                        imageHeight = image.getScaledHeight();
                        imageWidth = image.getScaledWidth();
                        System.out.println("imageHeight->" + imageHeight);
                        System.out.println("imageWidth->" + imageWidth);
                    }

                    image.setAlignment(Image.ALIGN_CENTER);
//     //设置图片的绝对位置
// image.setAbsolutePosition(0, 0);
// image.scaleAbsolute(500, 400);
// 插入一个图片
                    document.add(image);
                } catch (Exception de) {
                    System.out.println(de.getMessage());
                }
                document.close();
                fos.flush();
                fos.close();
                return "1";
            } else {
                return "0";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "1";
    }

}
