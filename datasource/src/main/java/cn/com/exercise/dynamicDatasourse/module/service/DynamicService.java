package cn.com.exercise.dynamicDatasourse.module.service;

import cn.com.exercise.dynamicDatasourse.module.condition.DynamicCondition;
import cn.com.exercise.dynamicDatasourse.module.dao.DynamicDao;
import cn.com.exercise.dynamicDatasourse.module.mapper.DynamicMapper;
import cn.com.exercise.dynamicDatasourse.util.DBHelper;
import cn.com.exercise.dynamicDatasourse.util.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicService {
    @Autowired
    DynamicDao dynamicDao;

    public List<DynamicCondition> getListFromSource1(){
        return dynamicDao.getListFromSource1();
    }

    @DS(DBHelper.DB_TYPE_R)
    public List<DynamicCondition> getListFromSource2(){
        return dynamicDao.getListFromSource2();
    }
}
