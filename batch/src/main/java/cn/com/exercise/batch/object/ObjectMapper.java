package cn.com.exercise.batch.object;

import cn.com.exercise.batch.entity.UserEntity;
import cn.com.exercise.batch.entity.UserEntity2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ObjectMapper {
    Integer batchAdd(List<UserEntity> userEntity);
    Integer batchDelete(@Param("idList") List<Integer> idList);

    Integer batchUpdateOneVariable(@Param("user") UserEntity user,@Param("idList") List idList);
    List<UserEntity> batchSelect(@Param("userEntity") UserEntity userEntity,@Param("nameList")List<String> nameList);

    List<UserEntity> batchSelect2(UserEntity2 userEntity2);
    List<UserEntity> batchSelect3(Integer[] idArray);
    List<UserEntity> batchSelect4(Map<String,Object> myMap);
}
