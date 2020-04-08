package com.test.bike;

import com.test.bike.bike.entity.BikeLocation;
import com.test.bike.bike.entity.Point;
import com.test.bike.bike.service.BikeMongoService;
import com.test.bike.bike.service.BikeService;
import com.test.bike.common.exception.TestBikeException;
import com.test.bike.record.service.RecordService;
import com.test.bike.user.entity.UserElement;
import com.test.bike.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BikeApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BikeApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;
	@Autowired
	private UserService userService;

	@Autowired
	private BikeMongoService bikeMongoService;

	@Autowired
	private BikeService bikeService;

	@Autowired
	private RecordService recordService;

	@Test
	public void contextLoads() {
		String result=restTemplate.getForObject("/user/home",String.class);
		System.out.println(result);
	}
	@Test
	public void MongoDB() throws TestBikeException {

//		List<BikeLocation> list=bikeMongoService.findNearbyBike("bike-position","location",
//				new Point(109.448482, 24.330802),0,1,null,null,10);
		bikeMongoService.findNearbyBikeAndDistance("bike-position",new Point(109.448482, 24.330802),50,5);
		System.out.println("1111");

	}
	@Test
	public void luckBike() throws TestBikeException {
		UserElement ue=new UserElement();
		ue.setUserId(1);
		bikeService.unlockBike(ue, Long.valueOf(2018003));

//		BikeLocation bikeLocation=new BikeLocation();
//		bikeLocation.setBikeNumber(2018003);
//		Double[] d=new Double[2];
//		d[0]=109.446418;
//		d[1]=24.329937;
//		bikeLocation.setCoordinates(d);
//		bikeService.lockBike(bikeLocation);
	}
	@Test
	public void recordBike() throws TestBikeException {
		//recordService.radeHisoryRecord(1l,0l);

//		BikeLocation bikeLocation=new BikeLocation();
//		bikeLocation.setBikeNumber(2018003);
//		Double[] d=new Double[2];
//		d[0]=109.446410;
//		d[1]=24.329933;
//		bikeLocation.setCoordinates(d);
//		bikeService.uploadPoint(bikeLocation);

		bikeMongoService.rideContrail("1525833689727675899875");

	}




}
