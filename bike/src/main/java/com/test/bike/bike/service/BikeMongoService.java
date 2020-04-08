package com.test.bike.bike.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.session.ClientSession;
import com.test.bike.bike.entity.BikeLocation;
import com.test.bike.bike.entity.Point;
import com.test.bike.common.exception.TestBikeException;
import com.test.bike.record.entity.RideContrail;
import lombok.extern.slf4j.Slf4j;
import org.bson.*;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author JackLei
 * @Date: Created in 2018/5/3 17:59
 * @Description
 */
@Slf4j
@Component
public class BikeMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
    *@Author JackLei
    *@Date: 2018/5/3 18:57
    *@Description: 查找某坐标附近的单车坐标点
    */
    public List<BikeLocation> findNearbyBike(String collection, String locationField, Point center,
                                             long minDistance, long maxDistance, DBObject query, DBObject fields, int limit) throws TestBikeException {
        List<BikeLocation> bikeLocationList=new ArrayList<>();
        try {

            MongoCollection<Document> mongoCollection=mongoTemplate.getCollection(collection);
            BsonDocument bson=new BsonDocument();
            List<BsonValue> list=new ArrayList<>();
            list.add(new BsonDouble(center.getLongitude()));
            list.add(new BsonDouble(center.getLatitude()));
            bson.put("location",new BsonDocument("$nearSphere",
                    new BsonDocument("$geometry",
                            new BsonDocument("type",new BsonString("Point"))
                                    .append("coordinates",new BsonArray(list))
                                    ).append("$minDistance",new BsonDouble(minDistance))
                            .append("$maxDistance",new BsonDouble(maxDistance))));

            bson.put("status",new BsonInt64(1));
            FindIterable<Document> findIterable =mongoCollection.find(bson).limit(limit);
            Double[] doubles=new Double[2];
            for (Document document : findIterable){
                BikeLocation location=new BikeLocation();
                location.setId(document.getObjectId("_id").toString());
                location.setBikeNumber(document.getInteger("bike_number"));
                ArrayList bsonArray=(ArrayList)((Document)document.get("location")).get("coordinates");
                doubles[0]=(Double) bsonArray.get(0);
                doubles[1]=(Double)bsonArray.get(1);
                location.setCoordinates(doubles);
                location.setStatus(document.getInteger("status"));
                bikeLocationList.add(location);
            }
        }catch (Exception e){
            log.error("fial to BikeMongoService findNearbyBike");
            throw new TestBikeException("查找附近单车坐标失败");
        }

        return bikeLocationList;
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/5 10:37
    *@Description: 查询某坐标附近的单车和距离
    */
    public List<BikeLocation> findNearbyBikeAndDistance(String collection,Point point,long maxDistance,int limt) throws TestBikeException {
        List<BikeLocation> bikeLocationList=new ArrayList<>();
        try {
            MongoCollection mongoCollection=mongoTemplate.getCollection(collection);
            List<BsonValue> list=new ArrayList<>();
            list.add(new BsonDouble(point.getLongitude()));
            list.add(new BsonDouble(point.getLatitude()));
            List<Bson> bsonList=new ArrayList<>();
            BsonDocument bsonDocument=new BsonDocument();
            bsonDocument.put("$geoNear",new BsonDocument("near",
                    new BsonDocument("type",new BsonString("Point"))
                            .append("coordinates",new BsonArray(list)))
                    .append("distanceField",new BsonString("dist.calculated"))
                    .append("maxDistance",new BsonInt64(maxDistance))
                    .append("includeLocs",new BsonString("dist.location"))
                    .append("num",new BsonInt64(limt))
                    .append("spherical",new  BsonBoolean(true))
                    .append("query",new BsonDocument("status",new BsonInt64(1))));


            bsonList.add(bsonDocument);
            AggregateIterable<Document> aggregate=mongoCollection.aggregate(bsonList);
            Double[] doubles=new Double[2];
            for (Document document:aggregate){
                BikeLocation location=new BikeLocation();
                location.setId(document.getObjectId("_id").toString());
                location.setBikeNumber(document.getInteger("bike_number"));
                ArrayList bsonArray=(ArrayList)((Document)document.get("location")).get("coordinates");
                doubles[0]=(Double) bsonArray.get(0);
                doubles[1]=(Double)bsonArray.get(1);
                location.setCoordinates(doubles);
                location.setStatus(document.getInteger("status"));
                location.setType(document.getInteger("type"));
                location.setDistance(((Document)document.get("dist")).getDouble("calculated"));
                bikeLocationList.add(location);
            }

        }catch (Exception e){
            log.error("fail to BikeMongoService findNearbyBikeAndDistance",e);
            throw new TestBikeException("查找附近单车失败");
        }
        return bikeLocationList;
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/8 23:58
    *@Description: 查询骑行轨迹
    */
    public RideContrail rideContrail(String recordNo) throws TestBikeException{
        RideContrail contrail=new RideContrail();
        try{
            BsonDocument bsonDocument=new BsonDocument();
            bsonDocument.put("record_no",new BsonString(recordNo));
            FindIterable<Document> findIterable=mongoTemplate.getCollection("ride_contrail").find(bsonDocument);
            Document document=findIterable.first();
            contrail.setBikeNo(document.getLong("bike_num"));
            contrail.setRideRecordNo(recordNo);
            contrail.setContrail((List<Point>) document.get("contrail"));
        }catch (Exception e){
            log.error("fail to BikeMongooService rideContrail");
            throw new TestBikeException("查询骑行轨迹失败");
        }
        return contrail;
    }
}
