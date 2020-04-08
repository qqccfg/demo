package com.test.bike.security;

import com.test.bike.user.entity.UserElement;
import lombok.Data;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/29 20:49
 * @Description 自定义的token
 */
@Data
public class RestAuthenticationToken extends AbstractAuthenticationToken {

    private UserElement user;

    public RestAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
