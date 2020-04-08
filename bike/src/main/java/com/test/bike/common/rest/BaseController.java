package com.test.bike.common.rest;

import com.test.bike.cache.CommonCacheUtil;
import com.test.bike.common.constants.Constants;
import com.test.bike.common.exception.TestBikeException;
import com.test.bike.user.entity.UserElement;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/28 23:35
 * @Description
 */
@Slf4j
public class BaseController {
    @Autowired
    private CommonCacheUtil commonCacheUtil;
    /**
    *@Author JackLei
    *@Date: 2018/4/30 21:52
    *@Description: 后端获取uerElement
    */
    protected UserElement getCurrentUser() throws TestBikeException {
        UserElement ue=null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        try{
            String token=request.getHeader(Constants.REQUEST_TOKEN_KEY);
            if (!StringUtils.isBlank(token)){
                ue=commonCacheUtil.getUserByToken(token);
            }
        }catch (Exception e){
            log.error("fial to get current user",e);
            throw e;
        }
        return ue;
    }
    /**
    *@Author JackLei
    *@Date: 2018/4/30 21:54
    *@Description: 获取ip
     * @param request
    */
    protected String getIpFormRequest(HttpServletRequest request){
        String ip=request.getHeader("x-forwarded-for");
        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;

    }

}
