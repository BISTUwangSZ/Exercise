package cn.com.exercise.move.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public Integer makeTop(UserCondition condition){
        Integer seq = condition.getSeq();
        if(seq>userDao.getMaxSeq() || seq<userDao.getMinSeq()){
            return 1;
        }
        Integer makeTop=0;
        Integer updateRes = userDao.updateOthersSeq(condition);
        if (updateRes == (seq-1)){
            condition.setSeq(1);
            makeTop = userDao.makeTop(condition);
        }
        if (makeTop==1){
            return 0;
        }
        else {
            return -1;
        }
    }

    public List getUser(){
        return userDao.getUser();
    }

    public UserCondition getUserById(Integer id){
        UserCondition condition = new UserCondition();
        condition.setId(id);
        return userDao.getUserById(condition);
    }

    public Integer moveUp(UserCondition condition){
        if(condition.getSeq()>userDao.getMaxSeq()){
            return 1;
        }
        UserCondition last = userDao.getLastSeq(condition);
        Integer temp = condition.getSeq();
        condition.setSeq(last.getSeq());
        last.setSeq(temp);
        Integer p = userDao.update(condition);
        Integer l = userDao.update(last);
        if(p>0 && l >0){
            return 0;
        }else {
            return -1;
        }
    }

    public Integer moveDown(UserCondition condition){
        if(condition.getSeq()>userDao.getMaxSeq()){
            return 1;
        }
        UserCondition next = userDao.getNextSeq(condition);
        Integer temp = condition.getSeq();
        condition.setSeq(next.getSeq());
        next.setSeq(temp);
        Integer p = userDao.update(condition);
        Integer l = userDao.update(next);
        if(p>0 && l >0){
            return 0;
        }else {
            return -1;
        }
    }
}
