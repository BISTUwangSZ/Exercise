package cn.com.exercise.move.module;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@EqualsAndHashCode(callSuper = false)
@Data
public class UserCondition implements Serializable{
    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private String psw;
    private Integer seq;
}
