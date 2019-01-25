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

    /**
     * 导入Excel的同时，备份文件
     * @param file
     * @param uploadPath
     * @return
     */
    public Integer uploadExcel(MultipartFile file,String uploadPath){
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.indexOf("."),fileName.length());
        String name = fileName.substring(0,fileName.indexOf("."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss",Locale.CHINA);
        Date currentDate = new Date();
        String date = sdf.format(currentDate);
        String excelPath = uploadPath + name + "_" + date + type;

        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists() && !uploadFile.isDirectory()){
            uploadFile.mkdirs();
        }
        FileOutputStream out = null;
        InputStream in = null;
        byte[] buffer = new byte[2014];
        try {
            out = new FileOutputStream(excelPath);
            in = file.getInputStream();
            int len = buffer.length;
            while ((len = in.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
        Integer result = this.excelHandleWorker(excelPath);
        return result;
    }

    private Integer excelHandleWorker(String excelPath) {
        Integer result = 0;
        InputStream inputStream = null;
        ArrayList<ExcelCondition> conditions = new ArrayList<>();
        try {
            inputStream = new FileInputStream(excelPath);
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet readsheet = wb.getSheetAt(0);
            int rowLen = readsheet.getLastRowNum();
            System.out.println("行数："+(rowLen+1));
            for (int i = 0; i <= rowLen; i++) {
                Row row = readsheet.getRow(i);
                ExcelCondition condition = new ExcelCondition();
                // 当前行为空
                if (row == null) {
                    continue;
                }
                Cell excel1Cell = row.getCell(0);
                if (excel1Cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                    String temp = String.valueOf(excel1Cell.getNumericCellValue());
                    condition.setExcel1(temp);
                } else {
                    condition.setExcel1(excel1Cell.getStringCellValue());
                }

                Cell excel2Cell = row.getCell(1);
                if (excel2Cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                    String temp = String.valueOf(excel2Cell.getNumericCellValue());
                    condition.setExcel2(temp);
                } else {
                    condition.setExcel2(excel2Cell.getStringCellValue());
                }

                Cell excel3Cell = row.getCell(2);
                if (excel3Cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                    String temp = String.valueOf(excel3Cell.getNumericCellValue());
                    condition.setExcel3(temp);
                } else {
                    condition.setExcel3(excel3Cell.getStringCellValue());
                }

                Cell status = row.getCell(3);
                if (status.getCellType()==Cell.CELL_TYPE_NUMERIC){
                    condition.setStatus((byte)status.getNumericCellValue());
                } else {
                    return -1;
                }

                conditions.add(condition);
            }
            result = excelMapper.upload(conditions);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }


    /**
     *导入文件的同时不备份文件
     * @param file
     * @return -1数据类型错误；0导入成功
     */
    public Integer uploadExcel2(MultipartFile file){
        Integer result=null;
        InputStream in = null;
        ArrayList<ExcelCondition> conditions = new ArrayList<>();
        try {
            in = file.getInputStream();
            Workbook wb = WorkbookFactory.create(in);
            Sheet readsheet = wb.getSheetAt(0);
            int rowLen = readsheet.getLastRowNum();
            System.out.println("行数：" + (rowLen+1));
            for (int i = 0; i <= rowLen; i++) {
                Row row = readsheet.getRow(i);
                ExcelCondition condition = new ExcelCondition();
                // 当前行为空
                if (row == null) {
                    continue;
                }
                Cell excel1Cell = row.getCell(0);
                if (excel1Cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                    String temp = String.valueOf(excel1Cell.getNumericCellValue());
                    condition.setExcel1(temp);
                } else {
                    condition.setExcel1(excel1Cell.getStringCellValue());
                }

                Cell excel2Cell = row.getCell(1);
                if (excel2Cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                    String temp = String.valueOf(excel2Cell.getNumericCellValue());
                    condition.setExcel2(temp);
                } else {
                    condition.setExcel2(excel2Cell.getStringCellValue());
                }

                Cell excel3Cell = row.getCell(2);
                if (excel3Cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                    String temp = String.valueOf(excel3Cell.getNumericCellValue());
                    condition.setExcel3(temp);
                } else {
                    condition.setExcel3(excel3Cell.getStringCellValue());
                }

                Cell status = row.getCell(3);
                if (status.getCellType()==Cell.CELL_TYPE_NUMERIC){
                    condition.setStatus((byte)status.getNumericCellValue());
                } else {
                    return -1;
                }

                conditions.add(condition);
            }
            result = excelMapper.upload(conditions);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
