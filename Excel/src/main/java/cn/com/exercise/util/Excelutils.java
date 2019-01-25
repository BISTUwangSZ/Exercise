package cn.com.exercise.util;

import cn.com.exercise.excel.ExcelCondition;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Excelutils {

    public static void export(HttpServletResponse response, String fileName, String[] title, List<ExcelCondition> dataList) throws Exception{
        // 设置请求
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition",
                "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));
        // 创建一个Workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 在Workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(fileName);
        // 自动设置宽度
        sheet.autoSizeColumn(0);

        // 设置标题样式
        HSSFCellStyle titleStyle = wb.createCellStyle();
        // 设置单元格边框样式，细边线
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 设置单元格对齐方式
        // 水平居中
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 垂直居中
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置字体样式
        Font titleFont = wb.createFont();
        // 字体高度
        titleFont.setFontHeightInPoints((short) 15);
        // 字体样式
        titleFont.setFontName("黑体");
        titleStyle.setFont(titleFont);

        // 数据样式 因为标题和数据样式不同 需要分开设置 不然会覆盖
        HSSFCellStyle dataStyle = wb.createCellStyle();
        // 设置数据边框
        dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 设置居中样式
        dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置数据字体
        Font dataFont = wb.createFont();
        dataFont.setFontHeightInPoints((short) 12);
        dataFont.setFontName("宋体");
        dataStyle.setFont(dataFont);

        // 遍历集合数据，产生数据行
        dataList.iterator();
        //行数量
        int index = 0;
        //标题行
        HSSFRow row = sheet.createRow(index++);
        for (int i = 0; i < title.length; i++) {
            row.createCell(i).setCellValue(title[i]);
        }
        //数据行
        for (ExcelCondition rv : dataList) {
            /**
             *{"ID#id", "Excel1#excel1", "Excel2#excel2","Excel3#excel3","status#status"};
             * */
            row = sheet.createRow(index);
            row.createCell(0).setCellValue(rv.getId());
            row.createCell(1).setCellValue(rv.getExcel1());
            row.createCell(2).setCellValue(rv.getExcel2());
            row.createCell(3).setCellValue(rv.getExcel3());
            row.createCell(4).setCellValue(rv.getStatus());
            index++;
        }
        // 打开流
        OutputStream outputStream = response.getOutputStream();
        // HSSFWorkbook写入流
        wb.write(outputStream);
        // 刷新流
        outputStream.flush();
        // 关闭流
//        outputStream.close();
    }
}
