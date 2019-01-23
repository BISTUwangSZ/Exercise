package cn.com.exercise.excel;

import cn.com.exercise.util.Excelutils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

@Service
public class ExcelService {
    static Log logger = LogFactory.getLog(ExcelService.class);

    @Autowired
    private ExcelMapper excelMapper;

    public Object getList(ExcelCondition condition){
        return excelMapper.getList(condition);
    }


    public HashMap<String,String> getExcel(ExcelCondition po,HttpServletResponse res){
        HashMap<String, String> result = new HashMap<>();
        //表头名称，导出数据列
        String header[] = new String[]{"ID#id", "第一列#excel1", "第二列#excel2","第三列#excel3","状态#status"};
        try {
            //请求响应 、文件名称、表头及对象属性名、导出数据
            Excelutils.export(res, "宽带赠送记录", header, excelMapper.getList(po));
            result.put("msg","success");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg","false");
            return result;
        }
    }




}
