package com.test.bike.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/29 21:31
 * @Description 对符合要求的合法token授权
 */
public class RestAuthenticationProvider implements AuthenticationProvider{
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof PreAuthenticatedAuthenticationToken){
            PreAuthenticatedAuthenticationToken preToken= (PreAuthenticatedAuthenticationToken) authentication;
            RestAuthenticationToken sysAuth = (RestAuthenticationToken) preToken.getPrincipal();
            if (sysAuth.getAuthorities() != null && sysAuth.getAuthorities().size() > 0) {
                GrantedAuthority gauth = sysAuth.getAuthorities().iterator().next();
                if ("BIKE_CLIENT".equals(gauth.getAuthority())) {
                    return sysAuth;
                }else if ("ROLE_SOMEONE".equals(gauth.getAuthority())) {
                    return sysAuth;
                }
            }

        }else if(authentication instanceof RestAuthenticationToken){
            RestAuthenticationToken sysAuth = (RestAuthenticationToken) authentication;
            if (sysAuth.getAuthorities() != null && sysAuth.getAuthorities().size() > 0) {
                GrantedAuthority gauth = sysAuth.getAuthorities().iterator().next();
                if ("BIKE_CLIENT".equals(gauth.getAuthority())) {
                    return sysAuth;
                }else if ("ROLE_SOMEONE".equals(gauth.getAuthority())) {
                    return sysAuth;
                }
            }

        }
        throw new BadCredentialsException("unknown.error");
    }
    /**
    *@Author JackLei
    *@Date: 2018/4/29 21:45
    *@Description: 校验授权token是不是合法 符合要求
    */
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication)||RestAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
