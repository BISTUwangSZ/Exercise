package cn.com.exercise.move.module;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    Integer updateOthersSeq(UserCondition condition);
    Integer makeTop(UserCondition condition);
    List<UserCondition> getUser();
    UserCondition getUserById(UserCondition condition);
    UserCondition getLastSeq(UserCondition condition);
    UserCondition getNextSeq(UserCondition condition);
    Integer update(UserCondition condition);
    Integer getMaxSeq();
    Integer getMinSeq();
}
