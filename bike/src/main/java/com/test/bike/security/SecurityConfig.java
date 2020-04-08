package com.test.bike.security;

import com.test.bike.cache.CommonCacheUtil;
import com.test.bike.common.constants.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/29 18:08
 * @Description spring security 的配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private Parameters parameters;

    @Autowired
    private CommonCacheUtil commonCacheUtil;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new RestAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略options方法的请求
        web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**")
        //放过swagger
        .antMatchers(parameters.getListSwagger().toArray(new String[parameters.getListSwagger().size()]));

    }

    private RestPreAuthenticatedProcessingFilter getRestPreAuthenticatedProcessingFilter() throws Exception {
        RestPreAuthenticatedProcessingFilter restPreAuthenticatedProcessingFilter=new RestPreAuthenticatedProcessingFilter(parameters.getNoneSecurityPath(),commonCacheUtil);
        restPreAuthenticatedProcessingFilter.setAuthenticationManager(this.authenticationManagerBean());
        return restPreAuthenticatedProcessingFilter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                //符合条件的路径放过验证
                .antMatchers(parameters.getNoneSecurityPath().toArray(new String[parameters.getNoneSecurityPath().size()])).permitAll()
                //其他的全部需要授权
                .anyRequest().authenticated()
                //设置统一的信息返回处理(自定义一个类来处理)
                .and().httpBasic().authenticationEntryPoint(new RestauthenticationEntryPoint())
                //设置无状态请求，不用session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //添加自定义登录验证过滤器
                .and().addFilter(getRestPreAuthenticatedProcessingFilter());

    }
}
