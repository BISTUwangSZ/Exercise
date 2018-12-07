package cn.com.exercise.dynamicDatasourse.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(0)
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class.getName());

    @Autowired
    DBHelper dbHelper;

    /**
     * 在Mapper层添加注解，实现切换数据源
     */
    @Pointcut("execution(* cn.com.exercise.dynamicDatasourse.module..mapper.*.*(..))")
    public void dataSourcePointCut(){
    }

    @Before("dataSourcePointCut()")
    public void before(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        String method = joinPoint.getSignature().getName();
        Class<?>[] clazz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = clazz[0].getMethod(method, parameterTypes);
            //如果方法上存在切换数据源的注解，则根据注解内容进行数据源切换
            if (m != null && m.isAnnotationPresent(DS.class)) {
                DS data = m.getAnnotation(DS.class);
                String dataSourceName = data.value();
                dbHelper.setDBType(dataSourceName);
                logger.debug("current thread " + Thread.currentThread().getName() + " add " + dataSourceName + " to ThreadLocal");
            } else {
                logger.debug("switch datasource fail,use default");
            }
        } catch (Exception e) {
            logger.error("current thread " + Thread.currentThread().getName() + " add data to ThreadLocal error", e);
        }
    }

    @After("dataSourcePointCut()")
    public void after(JoinPoint joinPoint){
        dbHelper.clearDBType();
    }
}
