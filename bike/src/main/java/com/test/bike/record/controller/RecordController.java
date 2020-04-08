package com.test.bike.record.controller;

import com.test.bike.bike.service.BikeMongoService;
import com.test.bike.common.constants.Constants;
import com.test.bike.common.exception.TestBikeException;
import com.test.bike.common.resp.ApiResult;
import com.test.bike.common.rest.BaseController;
import com.test.bike.record.entity.Record;
import com.test.bike.record.entity.RideContrail;
import com.test.bike.record.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/8 13:55
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("record")
public class RecordController extends BaseController{

    @Autowired
    private RecordService recordService;

    @Autowired
    private BikeMongoService bikeMongoService;


    /**
     *@Author JackLei
     *@Date: 2018/5/8 13:43
     *@Description: 查询骑行历史记录
     */
    @RequestMapping("/list/{id}")
    public ApiResult radeHisoryRecord(@PathVariable("id") Long id){
        ApiResult<List<Record>> resp=new ApiResult<>();
        try{
            List<Record> result=recordService.radeHisoryRecord(getCurrentUser().getUserId(),id);
            resp.setDate(result);
            resp.setMessage("查询成功");
        }catch (TestBikeException e){
            resp.setMessage(e.getMessage());
            resp.setCode(e.getCode());
        }
        catch (Exception e){
            log.error("fail to BikeController radeHisoryRecord");
            resp.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            resp.setMessage("内部错误");
        }
        return resp;
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/8 23:35
    *@Description: 查询骑行轨迹
    */
    @RequestMapping("/rideContrail/{recordNo}")
    public ApiResult rideContrail(@PathVariable("recordNo") String recordNo){
        ApiResult<RideContrail> resp=new ApiResult<>();
        try {
            RideContrail contrail=bikeMongoService.rideContrail(recordNo);
            resp.setDate(contrail);
            resp.setMessage("查询成功");
        }catch (TestBikeException e){
            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());
        }
        catch (Exception e){
            log.error("fail to RecordConotroller rideContrail",e);
            resp.setMessage("内部错误");
            resp.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return resp;

    }

}
