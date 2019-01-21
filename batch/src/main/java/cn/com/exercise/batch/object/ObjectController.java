package cn.com.exercise.batch.object;

import cn.com.exercise.batch.entity.UserEntity;
import cn.com.exercise.batch.entity.UserEntity2;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("object")
public class ObjectController {

    @Autowired
    private ObjectService objectService;

    /**
     * 入参为List<?>的批量插入
     * @param userEntity    待插入的信息
     * @return              插入的数据数量
     */
    @RequestMapping(value = "batchAdd")
    @ResponseBody
    public Integer batchAdd(String userEntity){
        List<UserEntity> list = JSON.parseArray(userEntity,UserEntity.class);
        return objectService.batchAdd(list);
    }

    /**
     * 两个入参的批量更新
     * @param getStr    待更新的id
     * @param getUser   待更新的其他信息
     * @return         更新的数据数量
     */
    @RequestMapping(value = "batchUpdateOneVariable")
    @ResponseBody
    public Integer batchUpdateOneVariable(String getStr,String getUser){
        List idList = JSON.parseObject(getStr,List.class);
        UserEntity user = JSON.parseObject(getUser,UserEntity.class);
        return objectService.batchUpdateOneVariable(user,idList);
    }

    /**
     * 入参为List,使用@Param的批量删除
     * @param idStr    待删除的id
     * @return        删除的数据数量
     */
    @RequestMapping(value = "batchDelete")
    @ResponseBody
    public Integer batchDelete(String idStr){
        List idList = JSON.parseObject(idStr,List.class);
        return objectService.batchDelete(idList);
    }

    /**
     * 两个入参的批量查询
     * @param list    待查询的id
     * @param entity  其他查询条件
     * @return        批量查询的List结果集
     */
    @RequestMapping(value = "batchSelect")
    @ResponseBody
    public Object batchSelect(String list, String entity){
        List nameList = JSON.parseObject(list,List.class);
        UserEntity user = JSON.parseObject(entity,UserEntity.class);
        return objectService.batchSelect(user,nameList);
    }


    /**
     * 入参为对象的某个字段的批量查询
     * @param entity 从Test传入的JSON格式的id
     * @return       批量查询的List结果集
     */
    @RequestMapping(value = "batchSelect2")
    @ResponseBody
    public Object batchSelect2(String entity){
        UserEntity2 user = JSON.parseObject(entity,UserEntity2.class);
        return objectService.batchSelect2(user);
    }

    /**
     * 入参为数组的查询
     * @param getStr 从Test传入的JSON格式的id
     * @return       批量查询的List结果集
     */
    @RequestMapping(value = "batchSelect3")
    @ResponseBody
    public Object batchSelect3(String getStr){
        Integer[] idArray = JSON.parseObject(getStr,Integer[].class);
        return objectService.batchSelect3(idArray);
    }

    /**
     * 入参为Map的查询
     * @param myMap 从Test传入的JSON格式的id
     * @return       批量查询的List结果集
     */
    @RequestMapping(value = "batchSelect4")
    @ResponseBody
    public Object batchSelect4(String myMap){
        Map<String,Object> map = JSON.parseObject(myMap,Map.class);
        return objectService.batchSelect4(map);
    }

}
