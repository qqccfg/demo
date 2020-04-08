package com.test.bike.record.entity;


import com.test.bike.bike.entity.Point;
import lombok.Data;

import java.util.List;

/**
 * Created by JackWangon[www.coder520.com] 2017/8/23.
 * 骑行轨迹
 */
@Data
public class RideContrail {

    private String rideRecordNo;

    private Long bikeNo;

    private List<Point> contrail;

}
