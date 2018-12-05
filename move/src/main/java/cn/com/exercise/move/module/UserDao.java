package cn.com.exercise.move.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends BaseDao{

    @Autowired
    private UserMapper mapper;

    public Integer updateOthersSeq(UserCondition condition){
        Integer i = mapper.updateOthersSeq(condition);
        return i;
    }

    public Integer makeTop(UserCondition condition){
        return mapper.makeTop(condition);
    }

    public List<UserCondition> getUser(){
        return mapper.getUser();
    }

    public UserCondition getUserById(UserCondition condition){
        return mapper.getUserById(condition);
    }

    public UserCondition getLastSeq(UserCondition condition){
        return mapper.getLastSeq(condition);
    }

    public UserCondition getNextSeq(UserCondition condition){
        return mapper.getNextSeq(condition);
    }

    public Integer update(UserCondition condition){
        return mapper.update(condition);
    }
    public Integer getMaxSeq(){
        return mapper.getMaxSeq();
    }
    public Integer getMinSeq(){
        return mapper.getMinSeq();
    }
}
