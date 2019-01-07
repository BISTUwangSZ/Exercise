package cn.com.exercise.batch.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserEntity implements Serializable{
    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private String psw;
    private Integer seq;
}
