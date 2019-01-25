package cn.com.exercise.excel;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface ExcelMapper {
    public Integer upload(ArrayList<ExcelCondition> list);
    public ArrayList<ExcelCondition> getList(ExcelCondition excel);
}
