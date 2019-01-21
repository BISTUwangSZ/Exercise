package cn.com.exercise.batch.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserEntity2 {
    private List<Integer> ids;
    private String name;
    private String gender;
    private Integer age;
    private String psw;
    private Integer seq;
}
