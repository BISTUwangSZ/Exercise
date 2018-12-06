package cn.com.exercise.dynamicDatasourse.module.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class DynamicCondition implements Serializable{
    private Integer id;
    private String  name;
    private Integer age;
    private String gender;
    private Integer seq;
}
