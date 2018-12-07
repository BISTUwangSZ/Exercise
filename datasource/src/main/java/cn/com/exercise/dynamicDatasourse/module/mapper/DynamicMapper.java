package cn.com.exercise.dynamicDatasourse.module.mapper;

import cn.com.exercise.dynamicDatasourse.module.condition.DynamicCondition;
import cn.com.exercise.dynamicDatasourse.util.DBHelper;
import cn.com.exercise.dynamicDatasourse.util.DS;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DynamicMapper {
    List<DynamicCondition> getListFromSource1();

    @DS(DBHelper.DB_TYPE_R)
    List<DynamicCondition> getListFromSource2();
}
