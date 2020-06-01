package com.test.tyreadmin.controller;

import com.test.tyreadmin.common.emum.ResponseEnum;
import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.common.response.ResponseRet;
import com.test.tyreadmin.entity.Api;
import com.test.tyreadmin.service.APiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date: 2020/4/25 11:33
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@RequestMapping("api")
@Controller
public class ApiController {

    @Autowired
    private APiService aPiService;

    @RequestMapping("/index")
    public String apiList(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
        PageQueryBean pageQueryBean = new PageQueryBean();
        pageQueryBean.setCurrentPage(page);
        PageQueryBean result = aPiService.getApis(pageQueryBean);
        model.addAttribute("result", result);
        return "/api/index";
    }

    @RequestMapping("/info")
    public String apiInfo(Long id, Model model){
        Api api = aPiService.getApi(id);
        model.addAttribute("api", api);
        return "/api/info";
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseRet updateApi(@RequestBody Api api){
        aPiService.updateApi(api);
        return new ResponseRet(ResponseEnum.OK);
    }

}
