package cn.com.exercise.excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("excel")
public class ExcelController {
    private String uploadExcelPath = "d:/";

    @Autowired
    private ExcelService excelService;

    @RequestMapping(value = "/toPage")
    public Object toPage() {
        return "index";
    }

    @RequestMapping(value = "/getList")
    @ResponseBody
    public Object getList(ExcelCondition condition) {
        Object result = this.excelService.getList(condition);
        System.out.println(result);
        return result;
    }


    @RequestMapping(value = "/downloadExcel")
    @ResponseBody
    public HashMap<String, String> downLoadExcel(String strPo,HttpServletResponse response) {
        HashMap<String, String> result = null;
        try{
            ExcelCondition condition = JSON.parseObject(strPo,ExcelCondition.class);
            return excelService.getExcel(condition,response);
        }catch (Exception e){
            e.printStackTrace();
            result.put("msg","false");
            return result;
        }
    }


    /**
     * 保存上传文件副本的同时导入
     * @param file
     * @return
     */
    /*@RequestMapping(value = "/uploadExcel")
    @ResponseBody
    public Object uploadExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
        Integer result = null;
        try{
            result = excelService.uploadExcel(file,uploadExcelPath);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }*/


    @RequestMapping(value = "/uploadExcel")
    @ResponseBody
    public Object uploadExcel2(@RequestParam(value = "file", required = false) MultipartFile file) {
        //仅导入，不保存副本
        Integer result = null;
        try{
            result = excelService.uploadExcel2(file);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
