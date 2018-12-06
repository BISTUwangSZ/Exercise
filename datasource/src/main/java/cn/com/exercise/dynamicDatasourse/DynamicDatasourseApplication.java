package cn.com.exercise.dynamicDatasourse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,JdbcTemplateAutoConfiguration.class})
@MapperScan(basePackages = "cn.com.exercise.dynamicDatasourse.module.mapper",
        sqlSessionTemplateRef = "jdbcTemplate")
public class DynamicDatasourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicDatasourseApplication.class, args);
	}
}
