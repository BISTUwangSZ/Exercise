package cn.com.exercise.batch.object;

import cn.com.exercise.batch.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ObjectMapper {
    Integer batchAdd(List<UserEntity> userEntity);
    Integer batchUpdateOneVariable(@Param("user") UserEntity user,@Param("idList") List idList);
    Integer batchDelete(@Param("idList") List<Integer> idList);
    List<UserEntity> batchSelect(@Param("userEntity") UserEntity userEntity,@Param("nameList")List<String> nameList);
}
