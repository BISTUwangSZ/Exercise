package cn.com.exercise.dynamicDatasourse.module.mapper;

import cn.com.exercise.dynamicDatasourse.module.condition.DynamicCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DynamicMapper {
    List<DynamicCondition> getListFromSource1();
    List<DynamicCondition> getListFromSource2();
}
