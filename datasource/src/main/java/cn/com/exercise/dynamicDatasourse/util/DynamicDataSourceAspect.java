package cn.com.exercise.dynamicDatasourse.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class.getName());

    @Around("execution(* cn.com.exercise.dynamicDatasourse.module..service.*.*(..))")
    public Object switchDS(ProceedingJoinPoint point) throws Throwable {
        Class<?> className = point.getTarget().getClass();
        String dataSource = DBHelper.DB_TYPE_RW;
        if (className.isAnnotationPresent(DS.class)) {
            DS ds = className.getAnnotation(DS.class);
            dataSource = ds.value();
        }else{
            // 得到访问的方法对象
            String methodName = point.getSignature().getName();
            Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        }
        // 切换数据源
        DBHelper dbHelper = new DBHelper();
        dbHelper.setDBType(dataSource);
        logger.info("当前数据源"+dataSource);
        try {
            return point.proceed();
        } finally {
            dbHelper.clearDBType();
        }
    }
}
