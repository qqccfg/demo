package com.test.bike.security;

import com.test.bike.cache.CommonCacheUtil;
import com.test.bike.common.constants.Constants;
import com.test.bike.user.entity.UserElement;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.AntPathMatcher;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/29 19:45
 * @Description
 * 自定义登陆验证拦截器
 * 用户登陆，会被AuthenticationProcessingFilter拦截，
 * 调用AuthenticationManager的实现，
 * 而AuthenticationManager会调用ProviderManager来获取用户验证信息
 * （不同的Provider调用的服务不同，这些信息可以是在数据库上，可以是在LDAP服务器上 可以是xml配置文件上等），
 * 如果验证通过后会将用户的权限信息封装一个User放到spring的全局缓存SecurityContextHolder中，以备后面访问资源时使用。
 *
 * 这里需要一个构造方法来进行初始化，原因好像是过滤器优先级比较大
 */
@Slf4j
public class RestPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {


    private List<String> noneSecurityList;

    private CommonCacheUtil commonCacheUtil;

    /**
     * spring的路径匹配器
     */
    private AntPathMatcher matcher = new AntPathMatcher();

    public RestPreAuthenticatedProcessingFilter(List<String> noneSecurityList, CommonCacheUtil commonCacheUtil) {
        this.noneSecurityList = noneSecurityList;
        this.commonCacheUtil = commonCacheUtil;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        GrantedAuthority[] authorities=new GrantedAuthority[1];
        try {
            //判断是否是在免验证的url的路径
            if (isNoneSecurity(request.getRequestURI().toString())||"OPTIONS".equals(request.getMethod())){
                GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_SOMEONE");
                authorities[0]=authority;
                return new RestAuthenticationToken(Arrays.asList(authorities));
            }
            //判断token和版本
            String token=request.getHeader(Constants.REQUEST_TOKEN_KEY);
            String version=request.getHeader(Constants.REQUEST_VERSION_KEY);
            if (version == null) {
                request.setAttribute("header-error", 400);
            }
            if (request.getAttribute("header-error")==null){
                if (token!=null&&!token.trim().isEmpty()){
                    UserElement ue=commonCacheUtil.getUserByToken(token);
                    if(ue instanceof UserElement){
                        //检查到token说明用户已经登录 授权给用户BIKE_CLIENT角色 允许访问
                        GrantedAuthority grantedAuthority=new SimpleGrantedAuthority("BIKE_CLIENT");
                        authorities[0]=grantedAuthority;
                        RestAuthenticationToken restToken=new RestAuthenticationToken(Arrays.asList(authorities));
                        restToken.setUser(ue);
                        return restToken;
                    }else {
                        request.setAttribute("header-error", 401);
                    }
                }else {
                    log.warn("token 不存在");
                    request.setAttribute("header-error", 401);
                }
            }
        }catch (Exception e){
            log.error("Fail to authenticate user", e);
        }
        //请求头错误，给个逻辑过去处理
        if(request.getAttribute("header-error")!=null){
            GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_NONE");
            authorities[0]=authority;
        }
        RestAuthenticationToken reToken=new RestAuthenticationToken(Arrays.asList(authorities));

        return reToken;
    }



    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }

    /**
     * 校验是否是无需权限的url
     * @param s
     * @return
     */
    private boolean isNoneSecurity(String s) {
        boolean result=false;
        if (!StringUtils.isBlank(s)){
            for (String list:noneSecurityList){
                //匹配
                if (matcher.match(list,s)){
                    result=true;
                    break;
                }
            }
        }
        return result;
    }
}
