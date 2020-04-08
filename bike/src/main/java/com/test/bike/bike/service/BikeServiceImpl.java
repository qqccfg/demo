package com.test.bike.bike.service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.test.bike.bike.dao.BikeMapper;
import com.test.bike.bike.entity.Bike;
import com.test.bike.bike.entity.BikeLocation;
import com.test.bike.bike.entity.GenerateBakeNumber;
import com.test.bike.common.exception.TestBikeException;


import com.test.bike.common.utils.BaiduPushUtils;
import com.test.bike.common.utils.DateUtils;
import com.test.bike.common.utils.RandomVercode;
import com.test.bike.fee.dao.FeeMapper;
import com.test.bike.fee.entity.Fee;
import com.test.bike.record.dao.RecordMapper;
import com.test.bike.record.entity.Record;
import com.test.bike.user.dao.UserMapper;
import com.test.bike.user.entity.User;
import com.test.bike.user.entity.UserElement;
import com.test.bike.wallet.dao.WalletMapper;
import com.test.bike.wallet.entity.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.bson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/2 22:48
 * @Description
 */
@Slf4j
@Service
public class BikeServiceImpl implements BikeService{

    private static final Byte NOT_VERYFY =1 ;

    private static final Object BIKE_UNLOCK = 2; //单车解锁

    public static final Byte BIKE_LOCK=1;


    @Autowired
    private BikeMapper bikeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FeeMapper feeMapper;

    /**
    *@Author JackLei
    *@Date: 2018/5/7 19:41
    *@Description: 生成单车
    */
    @Override
    public void generateBike() throws TestBikeException {
        //生成单车编号
        try {
            GenerateBakeNumber bakeNumber=new GenerateBakeNumber();
            bakeNumber.setWhatValue("1");
            bikeMapper.generateBikeNum(bakeNumber);
            Bike bike=new Bike();
            bike.setNumber(bakeNumber.getGenNumber());
            bike.setType((byte) 1);
            bikeMapper.insertSelective(bike);
        }catch (Exception e){
            log.error("fail to BikeServiceImpl generateBike");
            throw new TestBikeException("生成单车失败");
        }


    }
    /**
    *@Author JackLei
    *@Date: 2018/5/7 19:41
    *@Description: 单车开锁
    */
    @Override
    @Transactional
    public void unlockBike(UserElement currentUser, Long bikeNumber) throws TestBikeException {
        try{
            //是否实名验证
            User user=userMapper.selectByPrimaryKey(currentUser.getUserId());
            if (user.getVerifyFlag()==NOT_VERYFY){
                throw new TestBikeException("没有实名验证");
            }
            //账户是否有钱
            Wallet wallet=walletMapper.selectByUserId(currentUser.getUserId());
            if (wallet.getRemainSum().compareTo(new BigDecimal(1))<0){
                throw new TestBikeException("余额不足");
            }
            //是否有没完成的订单
            Record record=recordMapper.selectByUserId(currentUser.getUserId());
            if (record!=null){
                throw new TestBikeException("有单车在骑行中");
            }
            //推送单车进行解锁
            JSONObject notification = new JSONObject();
            notification.put("unlock", "unlock");
//            BaiduPushUtils.pushMsgToSingleDevice(currentUser,"{\"title\":\"TEST\",\"description\":\"Hello Baidu push!\"}");
            //推送如果可靠性比较差 可以采用单车端开锁后 主动ACK服务器 再修改相关状态的方式
            //mongodb 改变单车的状态
            Query query=new Query(Criteria.where("bike_number").is(bikeNumber));
            Update update = Update.update("status", BIKE_UNLOCK);
            mongoTemplate.upsert(query,update,"bike-position");
            //创建订单
            Record record1=new Record();
            record1.setBikeNum(bikeNumber);
            record1.setStartTime(new Date());
            String recordNo=System.currentTimeMillis()+ RandomVercode.getRecordValue();
            record1.setRecordNo(recordNo);
            record1.setUserId(currentUser.getUserId());
            recordMapper.insertSelective(record1);
        }catch (Exception e){
            log.error("fail to BikeServiceImpl unlockBike",e);
        }
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/7 19:55
    *@Description: 锁车
    */
    @Override
    @Transactional
    public void lockBike(BikeLocation bikeLocation) throws TestBikeException{
        try {
            //结束订单，计算时间存订单
            Record record=recordMapper.selectByBikeNumber(bikeLocation.getBikeNumber());
            if (record==null){
                throw new TestBikeException("骑行记录不存在");
            }
            Long userId=record.getUserId();
            Bike bike=bikeMapper.selectByBikeNumber(bikeLocation.getBikeNumber());
            if (bike==null){
                throw new TestBikeException("单车不存在");
            }
            Fee fee=feeMapper.selectByType(bike.getType());
            if (fee==null){
                new TestBikeException("计费系统异常");
            }
            Long rideTime=DateUtils.getMinuts(record.getStartTime(),new Date());
            record.setEndTime(new Date());
            record.setRideTime(rideTime.intValue());
            record.setStatus((byte) 2);
            BigDecimal cost = BigDecimal.ZERO;
            int minUnit =fee.getMinUnit();
            int intRideTime=rideTime.intValue();
            //不足单位时间
            if(intRideTime/minUnit==0){
                cost=fee.getFee();
            //干好整除
            }else if (intRideTime%minUnit==0){
                cost=fee.getFee().multiply(new BigDecimal(intRideTime/minUnit));
            //时间多一点
            }else if(intRideTime%minUnit!=0){
                cost=fee.getFee().multiply(new BigDecimal((intRideTime/minUnit)+1));
            }
            record.setRideCost(cost);
            recordMapper.updateByPrimaryKeySelective(record);
            //钱包扣费
            Wallet wallet=walletMapper.selectByUserId(userId);
            if (wallet==null){
                new TestBikeException("钱包扣费失败");
            }
            wallet.setRemainSum(wallet.getRemainSum().subtract(cost));
            walletMapper.updateByPrimaryKeySelective(wallet);
            //修改Mongodb中的东西
            Query query=Query.query(Criteria.where("bike_number").is(bikeLocation.getBikeNumber()));
            Update update=Update.update("status",1).set("location.coordinates",bikeLocation.getCoordinates());
            mongoTemplate.upsert(query,update,"bike-position");

        }catch (Exception e){
            log.error("fail to Bikeservice lockBike");
        }
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/8 15:44
    *@Description: 上传坐标
    */
    @Override
    public void uploadPoint(BikeLocation bikeLocation) throws TestBikeException{
        try{
            //查询没有完成的订单
            Record record=recordMapper.selectByBikeNumber(bikeLocation.getBikeNumber());
            if (record==null){
                throw new TestBikeException("没有定单");
            }
            //mongodb中是否插入了数据
            FindIterable<Document> findIterable=mongoTemplate.getCollection("ride_contrail").find(new BsonDocument("record_no",new BsonString(record.getRecordNo())));
            if (findIterable.first()==null){
                List<BasicDBObject> list = new ArrayList();
                BasicDBObject temp = new BasicDBObject("loc",bikeLocation.getCoordinates());
                list.add(temp);
                BasicDBObject insertObj = new BasicDBObject("record_no",record.getRecordNo())
                        .append("bike_num",record.getBikeNum())
                        .append("contrail",list);

                mongoTemplate.insert(insertObj,"ride_contrail");
            }else {
                Query query=Query.query(Criteria.where("record_no").is(record.getRecordNo()));
                Update update=new Update().push("contrail",new BasicDBObject("loc",bikeLocation.getCoordinates()));
                mongoTemplate.upsert(query,update,"ride_contrail");
            }
        }catch (Exception e){
            log.error("fail to BikeController uploadPoint",e);
        }
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/9 18:06
    *@Description: 用户查询单车状态
    */
    @Override
    public Byte userQueryBikeStatus(Long number) {
        Byte result=null;
        try{
            Bike bike=bikeMapper.selectByBikeNumber(Math.toIntExact(number));
            result=bike.getEnable();
        }catch (Exception e){
            log.error("fail to BikeService userQueryBikeStatus",e);
            throw e;
        }
        return result;
    }
}
