package cn.com.exercise.batch.object;

import cn.com.exercise.batch.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ObjectService{

    @Autowired
    private ObjectMapper objectMapper;

    public Integer batchAdd(List<UserEntity> userEntity){
        return objectMapper.batchAdd(userEntity);
    }

    public Integer batchUpdateOneVariable(UserEntity user, List idList){
        return objectMapper.batchUpdateOneVariable(user,idList);
    }

    public Integer batchDelete(List<Integer> idList){
        return objectMapper.batchDelete(idList);
    }
    public Object batchSelect(UserEntity userEntity,List<String> nameList){
        return objectMapper.batchSelect(userEntity,nameList);
    }

}
