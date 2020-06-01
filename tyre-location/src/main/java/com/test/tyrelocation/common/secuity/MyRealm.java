package com.test.tyrelocation.common.secuity;

import com.test.tyrelocation.entity.User;
import com.test.tyrelocation.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Date: 2019/11/2 17:11
 * @Author: JackLei
 * @Description: 验证（登陆）
 * @Version:
 */
public class MyRealm extends AuthenticatingRealm{

    @Autowired
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        String username = userToken.getUsername();
        User user = userService.queryByUsernameOrMobile(username);
        if (user!=null){
            SecurityUtils.getSubject().getSession().setAttribute("userInfo",user);
            return new  SimpleAuthenticationInfo(username, user.getPassword(), getName());
        }else {
            return null;
        }
    }
}
