package com.test.testbuyscheduler.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author JackLei
 * @Title: SqlSessionConfig
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2520:30
 **/
@Configuration
public class SqlSessionConfig {
    @Autowired
    private DataSource stockDatasource;

    @Bean(name = "stockSqlSessionFactory")
    public SqlSessionFactory stockSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
        factoryBean.setDataSource(stockDatasource);
        return factoryBean.getObject();
    }
    public SqlSessionTemplate stockSqlSessionTemplate() throws Exception {
        SqlSessionTemplate template=new SqlSessionTemplate(stockSqlSessionFactory());
        return template;
    }

}
