package com.test.testbuyuser.user.service;


import com.test.testbuyuser.common.constants.Constants;
import com.test.testbuyuser.common.exception.TestBuyException;
import com.test.testbuyuser.user.dao.UserMapper;
import com.test.testbuyuser.user.entity.User;
import com.test.testbuyuser.user.entity.UserElement;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author JackLei
 * @Date: Created in 2018/6/13 11:45
 * @Description
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CuratorFramework zkClient;

    /**
    *@Author JackLei
    *@Date: 2018/6/13 12:18
    *@Description: 用户登录
    */
    @Override
    public UserElement login(User user) {
        UserElement ue=null;
        User resultUser=userMapper.selectByemail(user.getEmail());
        if (resultUser==null){
            throw new TestBuyException("用户不存在");
        }
        boolean sign=passwordEncoder.matches(user.getPassword(),resultUser.getPassword());
        if (!sign){
            throw new TestBuyException("密码错误");
        }
        ue=new UserElement();
        ue.setUserId(resultUser.getId());
        ue.setUuId(resultUser.getUuid());
        ue.setEmail(resultUser.getEmail());
        ue.setNackname(resultUser.getNackname());
        ue.setStatus(resultUser.getStatus());
        return ue;
    }
    /**
    *@Author JackLei
    *@Date: 2018/6/13 13:23
    *@Description: 用户注册  由于分表原因 采用了ZK分布式锁保证查重正确
    */
    @Override
    public void registry(User user) throws Exception {
        InterProcessMutex lock=null;
        try {
            lock = new InterProcessMutex(zkClient, Constants.USER_REGISTER_DISTRIBUTE_LOCK_PATH);
            boolean retry=true;
            do {
                if (lock.acquire(3000, TimeUnit.MILLISECONDS)){
                    User resultUser=userMapper.selectByemail(user.getEmail());
                    if (resultUser!=null){
                        throw new TestBuyException("用户邮箱重复");
                    }
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setNackname("Test购用户"+user.getEmail());
                    userMapper.insertSelective(user);
                }
                retry=false;
            }while (retry);
        }catch (Exception e){
            log.error("用户注册异常",e);
            throw e;
        }finally {
           if (lock!=null){
               try {
                   lock.release();
                   log.info(user.getEmail()+Thread.currentThread().getName()+"释放");
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
        }

    }
}
