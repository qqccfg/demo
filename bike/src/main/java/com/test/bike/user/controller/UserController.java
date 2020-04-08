package com.test.bike.user.controller;

import com.test.bike.common.constants.Constants;
import com.test.bike.common.exception.TestBikeException;
import com.test.bike.common.resp.ApiResult;
import com.test.bike.common.rest.BaseController;
import com.test.bike.user.dao.UserMapper;
import com.test.bike.user.entity.LoginInfo;
import com.test.bike.user.entity.User;
import com.test.bike.user.entity.UserElement;
import com.test.bike.user.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/23 22:54
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    /**
    *@Author JackLei
    *@Date: 2018/4/28 23:02
    *@Description: 登录
    */
    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    @ApiImplicitParam(name = "loginInfo",value = "加密的数据",required = true,dataType = "LoginInfo")
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult login(@RequestBody LoginInfo loginInfo) throws TestBikeException {
        //我主要的目的 就是获取一个 ApiResult（code[状态码],message[消息],data[数据]） 类进行返回
        //主要就是获取个token 传入data中
        //对logininfo.getDate的数据进行验证
        ApiResult<String> resp=new ApiResult<>();
        try {
            String data=loginInfo.getData();
            String key=loginInfo.getKey();
            if(data==null){
                throw new TestBikeException("非法请求");
            }
            String token=userService.loging(data,key);
            resp.setDate(token);
        }catch (TestBikeException e){
            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("fail to login(登录出错)",e);
            resp.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            resp.setMessage("内部错误");
        }
        if (resp.getCode()==Constants.RESP_STATUS_OK){
            resp.setMessage("succ");
        }else {
            resp.setMessage("fail");
        }

        return resp;
    }
    /**
    *@Author JackLei
    *@Date: 2018/4/28 23:03
    *@Description: 修改昵称
    */
    @ApiOperation(value = "修改昵称",notes = "用户修改昵称",httpMethod = "POST")
    @ApiImplicitParam(name = "user",value = "用户信息，包含昵称",required = true,dataType = "User")
    @RequestMapping("/modifNickname")
    public ApiResult modifNickname(@RequestBody User user ) throws TestBikeException {
        ApiResult resp=new ApiResult();
        try {
            UserElement ue=getCurrentUser();
            user.setId(ue.getUserId());
            userService.modifNickname(user);
            resp.setMessage("修改成功");
        }catch (TestBikeException e){
            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());
        }
        catch (Exception e){
            resp.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            resp.setMessage(e.getMessage());
            log.error("fail to modif nickname(修改昵称错误)",e);
        }
        return resp;
    }
    /**
    *@Author JackLei
    *@Date: 2018/4/30 21:13
    *@Description: 发送验证短息
    */
    @ApiOperation(value = "短息验证",notes = "根据用提供手机号码发送验证码",httpMethod = "POST")
    @ApiImplicitParam(name = "user",value = "用户信息，包括手机号码",required = true,dataType = "User")
    @RequestMapping("/sendVercode")
    public ApiResult sendVercode(@RequestBody User user,HttpServletRequest request){
        ApiResult<String> resp=new ApiResult<>();
        try {
            if(StringUtils.isBlank(user.getMobile())){
                throw new TestBikeException("手机号码不能为空");
            }
            userService.sendVercode(user.getMobile(),getIpFormRequest(request));
            resp.setMessage("发送成功");
        }catch (TestBikeException e){
            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());
        } catch (Exception e){
            resp.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            resp.setMessage("内部出错");
            log.error("fail to send vercode",e);
        }
        return resp;
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/1 18:54
    *@Description: 修改头像
    */
    @ApiOperation(value = "修改头像",notes = "用户上传头像 file",httpMethod = "POST")
    @RequestMapping("/modifHeadportrait")
    public ApiResult modifHeadportrait(HttpServletRequest request, @RequestParam(required = false)MultipartFile file){
        ApiResult<String> resp=new ApiResult<>();
        try{
            UserElement ue=getCurrentUser();
            userService.modifHeadportrait(ue.getUserId(),file);
            resp.setMessage("修改头像成功");
        }catch (TestBikeException e){
            resp.setMessage(e.getMessage());
            resp.setCode(e.getCode());
        }
        catch (Exception e){
            log.error("fail to modifHeadportrait",e);
            resp.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            resp.setMessage("内部错误");
        }
        return resp;

    }

}
