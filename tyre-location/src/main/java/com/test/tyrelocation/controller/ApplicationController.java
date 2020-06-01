package com.test.tyrelocation.controller;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.emum.ResponseEnum;
import com.test.tyrelocation.common.response.ResponseRet;
import com.test.tyrelocation.common.tool.ConversionUtils;
import com.test.tyrelocation.entity.Api;
import com.test.tyrelocation.entity.Application;
import com.test.tyrelocation.entity.User;
import com.test.tyrelocation.service.ApiLimitNumService;
import com.test.tyrelocation.service.ApiService;
import com.test.tyrelocation.service.ApplicationService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2019/10/8 14:04
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Controller
@RequestMapping("/application")
public class ApplicationController extends BaseController{

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private ApiLimitNumService apiLimitNumService;

    @RequestMapping("/create")
    @ResponseBody
    public ResponseRet create(@Valid @RequestBody Application application){
        applicationService.create(application, getUserId());
        return new ResponseRet(ResponseEnum.OK);
    }

    @RequestMapping("/gocreate")
    public String goCreate(Model model){
        List<Api> apis = apiService.queryAll();
        model.addAttribute("apis", apis);
        return "/application/create";
    }
    @RequestMapping("/goupdate")
    public String goUpdate(@RequestParam("appId")Long appId, Model model){
        checkId(appId);
        Application app =applicationService.queryByApplicationId(getUserId(),appId);
        List<Api> apis = apiService.queryAll();
        List<Long> apiIds = app.getApis().stream().map(a->a.getId()).collect(Collectors.toList());
        model.addAttribute("appl", app);
        model.addAttribute("apis", apis);
        model.addAttribute("apiIds", apiIds);
        return "/application/update";
    }

    @RequestMapping("/query/all")
    public String queryAll(Model model){
        List<Application> applications = applicationService.queryAllByUserId(getUserId());
        model.addAttribute("apps", applications);
        return "/application/applicationList";

    }

    @RequestMapping("/query/detail")
    public String query(@RequestParam("applicationId") Long applicationId, Model model){
        checkId(applicationId);
        Application application = applicationService.queryByApplicationId(getUserId(), applicationId);
        List apiTypes = apiTypeHandle(application.getApis());
        List<Long> apiIds = application.getApis().stream().map(api -> api.getId()).collect(Collectors.toList());
        List<Integer> apiLimitNums = apiLimitNumService.queryByApiIds(apiIds, getUserId());
        model.addAttribute("app", application);
        model.addAttribute("apiTypes", apiTypes);
        model.addAttribute("apiLimitNums", apiLimitNums);
        return "/application/detail";
    }

    @RequestMapping("/detect")
    public String detect(@RequestParam("applicationId") Long applicationId, Model model){
        checkId(applicationId);
        applicationService.deleteById(getUserId(), applicationId);
        List<Application> applications = applicationService.queryAllByUserId(getUserId());
        model.addAttribute("apps", applications);
        return "/application/applicationList";
    }

    @RequestMapping("/update")
    public ResponseRet update(@Valid @RequestBody Application application, Model model){
        applicationService.updateByPrimaryKeySelective(getUserId(), application);
        Application app =applicationService.queryByApplicationId(getUserId(), application.getId());
        model.addAttribute("appl", app);
        return new ResponseRet(ResponseEnum.OK);
    }

    private void checkId(Long id){
        if (id==null || id<0){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
    }
    private List<String> apiTypeHandle(List<Api> apis){
        return apis.stream().map(Api::getApiType)
                .map(ConversionUtils::apiTypeToString)
                .collect(Collectors.toList());
    }
}
