package com.test.bike.security;

import com.alibaba.fastjson.JSON;
import com.test.bike.common.constants.Constants;
import com.test.bike.common.resp.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/29 19:38
 * @Description 将用户定向到认证入口，收集认证信息
 */
@Slf4j
public class RestauthenticationEntryPoint implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ApiResult<String> resp=new ApiResult<>();
        if(request.getAttribute("header-error")!=null){
            if ("400".equals(request.getAttribute("header-error")+"")){
                resp.setCode(408);
                resp.setMessage("请升级app版本");
            }else {
                resp.setCode(401);
                resp.setMessage("请您登录");
            }
        }
        try {
            //设置跨域请求 请求结果json刷到响应里
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEADER");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, user-token, Content-Type, Accept, version, type, platform");
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JSON.toJSONString(resp));
            response.flushBuffer();
        }catch (Exception er){
            log.error("Fail to send 401 response {}", er.getMessage());
        }
    }
}
