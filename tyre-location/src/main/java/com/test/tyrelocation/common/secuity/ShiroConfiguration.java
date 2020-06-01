package com.test.tyrelocation.common.secuity;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Date: 2019/11/2 17:29
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        creator.setUsePrefix(true);
        return creator;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(defaultWebSecurityManager());
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/", "authc");
        filterMap.put("/operation/**", "authc");
        filterMap.put("/application/**", "authc");
        filterMap.put("/api/**", "authc");
        filterMap.put("/api/**", "authc");
        filterMap.put("/message/**", "authc");
        filterMap.put("/order/**", "authc");
        factoryBean.setFilterChainDefinitionMap(filterMap);
        factoryBean.setLoginUrl("/user/gologin");
        factoryBean.setSuccessUrl("/operation/index");
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        return securityManager;
    }
    @Bean
    public Realm realm(){
        MyRealm realm = new MyRealm();
        realm.setCredentialsMatcher(customCredentialsMatcher());
        return realm;
    }

    @Bean
    public CustomCredentialsMatcher customCredentialsMatcher(){
        return new CustomCredentialsMatcher();
    }

}
