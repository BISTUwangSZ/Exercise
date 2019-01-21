package cn.com.exercise.excel;

import java.io.File;
import java.util.List;

public class OperateExcel {
    private List readExcel(String path){
        File file = new File(path);
        
        return null;
    }

    private Integer writeExcel(List list){
        return null;
    }
    public static void main(String [] args){
        String filePath="";
        OperateExcel operateExcel = new OperateExcel();
        List list = operateExcel.readExcel(filePath);
        Integer i = operateExcel.writeExcel(list);
        System.out.println(i);
    }
}
