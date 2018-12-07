package cn.com.exercise.dynamicDatasourse.util;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author wsz
 * 数据源配置
 */
@Configuration
@Component
@PropertySource(value = {"config/jdbc.properties","application.properties"})
public class DataSourceConfig extends LogFactory{

    @Value("${jdbc.driverClassName.db}")
    private String driverClassName;

    @Value("${jdbc.w.url}")
    private String wUrl;

    @Value("${jdbc.w.user}")
    private String wUserName;

    @Value("${jdbc.w.password}")
    private String wPassword;

    @Value("${jdbc.r.url}")
    private String rUrl;

    @Value("${jdbc.r.user}")
    private String rUserName;

    @Value("${jdbc.r.password}")
    private String rPassword;

    @Value("${druid.initialSize}")
    private Integer initialSize;

    @Value("${druid.minIdle}")
    private Integer minIdle;

    @Value("${druid.maxActive}")
    private Integer maxActive;

    @Value("${druid.maxWait}")
    private Integer maxWait;

    @Value("${druid.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;

    @Value("${druid.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${druid.validationQuery}")
    private String validationQuery;

    @Value("${druid.testWhileIdle}")
    private Boolean testWhileIdle;

    @Value("${druid.testOnBorrow}")
    private Boolean testOnBorrow;

    @Value("${druid.testOnReturn}")
    private Boolean testOnReturn;

    @Value("${druid.poolPreparedStatements}")
    private Boolean poolPreparedStatements;

    @Value("${druid.maxPoolPreparedStatementPerConnectionSize}")
    private Integer maxPoolPreparedStatementPerConnectionSize;

    @Value("${druid.filters}")
    private String filters;

    @Value("${mybatis.config-location}")
    private String sqlmapConfigPath;

    @Value("${mybatis.mapper-locations}")
    private String mapperLocation;

    @Value("${mybatis.type-aliases-package}")
    private String typeAlias;

    @Bean(name = "log-filter")
    public Slf4jLogFilter logFilter(){
        return new Slf4jLogFilter();
    }

    @Bean(name = DBHelper.DB_TYPE_RW)
    @Primary
    public DataSource master(){
        DruidDataSource source = new DruidDataSource();
        //基本属性 url、user、password
        source.setUrl(wUrl);
        source.setDriverClassName(driverClassName);
        source.setUsername(wUserName);
        source.setPassword(wPassword);
        //配置初始化大小、最小、最大
        source.setInitialSize(initialSize);
        source.setMinIdle(minIdle);
        source.setMaxActive(maxActive);
        //配置获取连接等待超时的时间
        source.setMaxWait(maxWait);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        source.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //配置一个连接在池中最小生存的时间，单位是毫秒
        source.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        source.setValidationQuery(validationQuery);
        source.setTestWhileIdle(testWhileIdle);
        source.setTestOnBorrow(testOnBorrow);
        source.setTestOnReturn(testOnReturn);
        //打开PSCache，并且指定每个连接上PSCache的大小  如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false
        source.setPoolPreparedStatements(poolPreparedStatements);
        source.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            //配置监控统计拦截的filters
            source.setFilters(filters);
            source.setProxyFilters(Arrays.asList(logFilter()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //超过时间限制是否回收
        source.setRemoveAbandoned(true);
        //超时时间；单位为秒。180秒=3分钟
        source.setRemoveAbandonedTimeout(180);
        //关闭abanded连接时输出错误日志
        source.setLogAbandoned(true);
        return source;
    }

    @Bean(name = DBHelper.DB_TYPE_R)
    public DataSource slave(){
        DruidDataSource source = new DruidDataSource();
        //基本属性 url、user、password
        source.setUrl(rUrl);
        source.setDriverClassName(driverClassName);
        source.setUsername(rUserName);
        source.setPassword(rPassword);
        //配置初始化大小、最小、最大
        source.setInitialSize(initialSize);
        source.setMinIdle(minIdle);
        source.setMaxActive(maxActive);
        //配置获取连接等待超时的时间
        source.setMaxWait(maxWait);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        source.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //配置一个连接在池中最小生存的时间，单位是毫秒
        source.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        source.setValidationQuery(validationQuery);
        source.setTestWhileIdle(testWhileIdle);
        source.setTestOnBorrow(testOnBorrow);
        source.setTestOnReturn(testOnReturn);
        //打开PSCache，并且指定每个连接上PSCache的大小  如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false
        source.setPoolPreparedStatements(poolPreparedStatements);
        source.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            //配置监控统计拦截的filters
            source.setFilters(filters);
            source.setProxyFilters(Arrays.asList(logFilter()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //超过时间限制是否回收
        source.setRemoveAbandoned(true);
        //超时时间；单位为秒。180秒=3分钟
        source.setRemoveAbandonedTimeout(180);
        //关闭abanded连接时输出错误日志
        source.setLogAbandoned(true);
        return source;
    }


    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource(){
        DynamicDataSource dynamicRoutingDataSource = new DynamicDataSource();
        //配置多数据源
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(DBHelper.DB_TYPE_RW, master());
        dataSourceMap.put(DBHelper.DB_TYPE_R, slave());
        // 将 master 和 slave 数据源作为指定的数据源
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        // 将 master 数据源作为默认指定的数据源
        dynamicRoutingDataSource.setDefaultTargetDataSource(master());
        return dynamicRoutingDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAlias);
        sqlSessionFactoryBean.setConfigLocation( new ClassPathResource(sqlmapConfigPath));
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+mapperLocation;
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packageSearchPath));
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return  sqlSessionFactoryBean;
    }

    @Bean(name = "jdbcTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager manager = new DataSourceTransactionManager(dynamicDataSource());
        return manager;
    }

    /**
     * 配置事务的传播特性
     */
    @Bean(name = "txAdvice")
    public TransactionInterceptor txAdvice(){
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(transactionManager());
        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("save*","PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("add*","PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("insert*","PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("update*","PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("delete*","PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("*","readOnly");
        interceptor.setTransactionAttributes(transactionAttributes);
        return interceptor;
    }

    @Bean(name = "txAdviceAdvisor")
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        String transactionExecution = "execution(* cn.com.hiveview.springboot.demoapi..service.*.*(..))";
        pointcut.setExpression(transactionExecution);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

}
