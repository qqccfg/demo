package com.test.tyreadmin.common.security;

import com.test.tyreadmin.entity.Admin;
import com.test.tyreadmin.repository.AdminMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Date: 2019/11/2 17:11
 * @Author: JackLei
 * @Description: 验证（登陆）
 * @Version:
 */
public class MyRealm extends AuthenticatingRealm {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        String username = userToken.getUsername();
        Admin admin = adminMapper.selectByName(username);

        if (admin!=null){
            SecurityUtils.getSubject().getSession().setAttribute("userInfo",admin);
            return new SimpleAuthenticationInfo(username, admin.getAdminPassword(), getName());
        }else {
            return null;
        }
    }
}
