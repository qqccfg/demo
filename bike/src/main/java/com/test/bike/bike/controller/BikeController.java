package com.test.bike.bike.controller;

import com.test.bike.bike.entity.Bike;
import com.test.bike.bike.entity.BikeLocation;
import com.test.bike.bike.entity.Point;
import com.test.bike.bike.service.BikeMongoService;
import com.test.bike.bike.service.BikeService;
import com.test.bike.common.constants.Constants;
import com.test.bike.common.exception.TestBikeException;
import com.test.bike.common.resp.ApiResult;
import com.test.bike.common.rest.BaseController;
import com.test.bike.record.entity.Record;
import com.test.bike.record.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/2 22:44
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("bike")
public class BikeController extends BaseController{
    @Autowired
    private BikeService bikeService;

    @Autowired
    private BikeMongoService bikeMongoService;

    @Autowired
    private RecordService recordService;

    /**
    *@Author JackLei
    *@Date: 2018/5/3 18:01
    *@Description: 生成单车
    */
    @RequestMapping("/generateBike")
    public ApiResult generateBike(){
        ApiResult<String> resp=new ApiResult<>();
        try {
            bikeService.generateBike();
            resp.setMessage("单车创建成功");
        }catch (TestBikeException e){
            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("fail to BikeCentroller generateBike",e);
            resp.setMessage("内部错误");
            resp.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return resp;
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/3 18:03
    *@Description: 查找附近的单车
    */
    @RequestMapping("/findNearbyBike")
    public ApiResult findNearbyBike(@RequestBody Point point){
        ApiResult<List<BikeLocation>> resp=new ApiResult<>();
        try {
            List<BikeLocation> result =bikeMongoService.findNearbyBikeAndDistance(Constants.MONGODB_COLLECTION,point,Constants.MONGODB_MAX,Constants.MONGODB_LIMT);
            resp.setDate(result);
            resp.setMessage("查询附近单车成功");
        }catch (Exception e){
            log.error("fail to bikeCentroller findNearbyBike");
            resp.setMessage("内部错误");
            resp.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return resp;
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/6 21:15
    *@Description: 单车开锁
    */
    @RequestMapping("/unlockBike")
    public ApiResult unlockBike(@RequestBody Bike bike){
        ApiResult<String> resp=new ApiResult<>();
        try{
            bikeService.unlockBike(getCurrentUser(),bike.getNumber());
            resp.setMessage("开锁成功");
        }catch (TestBikeException e){
            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());
        }
        catch (Exception e){
            log.error("fail to BikeCentroller unlockBike");
            resp.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            resp.setMessage("内部错误");
        }
        return resp;
    }

    /**
    *@Author JackLei
    *@Date: 2018/5/7 19:45
    *@Description: 锁单车
    */
    @RequestMapping("/lockBike")
    public ApiResult lockBike(@RequestBody BikeLocation bikeLocation){
        ApiResult<String> resp=new ApiResult<>();
        try {
            bikeService.lockBike(bikeLocation);
            resp.setMessage("锁车成功");
        }catch (TestBikeException e){
            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());
        } catch (Exception e){
            log.error("fail to BikeCentoller lockBike",e);
            resp.setMessage("内部错误");
            resp.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return resp;
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/8 14:19
    *@Description: 上传骑行时的坐标
    */
    public ApiResult uploadPoint(@RequestBody BikeLocation bikeLocation){
        ApiResult resp=new ApiResult();
        try{
            bikeService.uploadPoint(bikeLocation);
            resp.setMessage("上传成功");
        }catch (TestBikeException e){
            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("fail to BikeController uploadPoint",e);
            resp.setMessage("内部错误");
            resp.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return resp;
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/9 17:54
    *@Description: 用户端查询单车状态
    */
    @RequestMapping("/userQueryBikeStatus")
    public ApiResult userQueryBikeStatus(@RequestBody Bike bike){
        ApiResult<Byte> resp=new ApiResult();
        try {
           Byte result=bikeService.userQueryBikeStatus(bike.getNumber());
           resp.setDate(result);
           resp.setMessage("查询成功");
        }catch (Exception e){
            log.error("fail to BikeContriller userQueryBikeStatus",e);
            resp.setMessage("内部错误");
            resp.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return resp;
    }



}
