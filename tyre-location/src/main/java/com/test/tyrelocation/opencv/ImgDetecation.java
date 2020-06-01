package com.test.tyrelocation.opencv;

import com.alibaba.druid.util.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.constant.Constants;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.tool.BaiDuHttpUtil;
import com.test.tyrelocation.common.tool.HttpUtils;
import com.test.tyrelocation.entity.Image;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.util.Base64Utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.*;


/**
 * @Date: 2019/9/13 8:57
 * @Author: JackLei
 * @Description: 图像轮胎定位
 * @Version:
 */
public class ImgDetecation {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public static void main(String[] args) {
        System.out.println(getToken());
    }
    public static  void doDetection(String dir, String suffix){
        ImgDetecation imgDetecation = new ImgDetecation();
        Mat img = Imgcodecs.imread(dir);
        Mat dst = new Mat();
        Mat gray =new Mat();
        double height = img.size().height;
        double width = img.size().width;
        double limit = width/600;
        height = (int)height/limit;
        width = (int)width/limit;
        Size newSize = new Size(width, height);
        Imgproc.resize(img, dst, newSize);
        Imgproc.cvtColor(dst, gray, Imgproc.COLOR_RGB2GRAY);
        //CV_8UC1
        Mat dst2 = imgDetecation.handle(gray);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(dst2, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
        Mat ret = imgDetecation.detection(dst, contours, dst2);
        Imgcodecs.imwrite(dir+"."+suffix,ret);
    }

    public static  String doDetection(ByteBuffer buffer, Image image){
        ImgDetecation imgDetecation = new ImgDetecation();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat img = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC3,buffer);
        Mat dst = new Mat();
        Mat gray =new Mat();
        double height = img.size().height;
        double width = img.size().width;
        double limit = width/600;
        height = (int)height/limit;
        width = (int)width/limit;
        Size newSize = new Size(width, height);
        Imgproc.resize(img, dst, newSize);
        Imgproc.cvtColor(dst, gray, Imgproc.COLOR_RGB2GRAY);
        //CV_8UC1
        Mat dst2 = imgDetecation.handle(gray);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(dst2, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
        Mat ret = imgDetecation.detection(dst, contours, dst2);
        int bufferSize = ret.channels() * ret.cols() * ret.rows();
        byte[] arrayBuffer = new byte[bufferSize];
        ret.get(0, 0, arrayBuffer);
        return Base64Utils.encodeToString(arrayBuffer);
    }

    private static Double darknessVal(Mat image){
        double sum = 0;
        double height = image.size().height;
        double width = image.size().width;
        double maxVal = height*width*255;
        for (int h=0; h<height; h++ ){
            for (int w=0; w<width; w++){
                sum += image.get(h, w)[0];
             }
        }
        return sum/maxVal;
    }

    private static Mat handle(Mat image){
        double val = darknessVal(image);
        val = 1-val*val;
        MatOfDouble matVal = new MatOfDouble(val);
        Mat gray = image.mul(matVal);
        Mat dst = new Mat();
        Imgproc.threshold(gray, dst, 65, 255, Imgproc.THRESH_BINARY);
        Mat kernel = Mat.ones(4, 4, CvType.CV_32F);
        Mat ret = new Mat();
        Imgproc.morphologyEx(dst, ret, Imgproc.MORPH_CLOSE, kernel);
        return ret;
    }

    private Mat detection(Mat image, List<MatOfPoint> contours, Mat dst){
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGB2GRAY);
        double sunArea = gray.size().area();
        List<double[]> contain = new ArrayList<>();
        for (MatOfPoint matOfPoint : contours){
            Rect rect = Imgproc.boundingRect(matOfPoint);
            boolean onOff = areaFilter(matOfPoint);
            if (onOff){
                onOff = squareFilter(matOfPoint);
                if (onOff){
                    onOff = centerPointFilter(matOfPoint, dst);
                    if (onOff){
                        onOff = sizeFilter(matOfPoint, sunArea);
                        if (onOff){
                            contain.add(new double[]{rect.x, rect.y, rect.width, rect.height});
                        }
                    }
                }
            }
        }

        List<double[]> resultList = resultFilter(contain);
        for (double[] result:resultList){
            double x = result[0];
            double y = result[1];
            double w = result[2];
            double h = result[3];
            int excursion = (int) h/3-3;
            Point point1 = new Point((x-excursion), (y-excursion));
            Point point2 = new Point((x+w+excursion), (y + h+excursion));
            Scalar color = new Scalar(0, 255, 0);
            Imgproc.rectangle(image, point1, point2, color, 2);
        }
        return image;
    }
    public static List<double[]> detectionApi(String dir){
        Mat img = Imgcodecs.imread(dir);
        Mat gray =new Mat();

        Imgproc.cvtColor(img, gray, Imgproc.COLOR_RGB2GRAY);
        //CV_8UC1
        Mat dst2 = handle(gray);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(dst2, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);

        Mat gray2 = new Mat();
        Imgproc.cvtColor(img, gray2, Imgproc.COLOR_RGB2GRAY);
        double sunArea = gray2.size().area();
        List<double[]> contain = new ArrayList<>();
        for (MatOfPoint matOfPoint : contours){
            Rect rect = Imgproc.boundingRect(matOfPoint);
            boolean onOff = areaFilter(matOfPoint);
            if (onOff){
                onOff = squareFilter(matOfPoint);
                if (onOff){
                    onOff = centerPointFilter(matOfPoint, dst2);
                    if (onOff){
                        onOff = sizeFilter(matOfPoint, sunArea);
                        if (onOff){
                            contain.add(new double[]{rect.x, rect.y, rect.width, rect.height});
                        }
                    }
                }
            }
        }
        List<double[]> resultList = resultFilter(contain);
        return resultList;
    }

    private static boolean areaFilter(MatOfPoint point){
        double area = Imgproc.contourArea(point);
        Rect rect = Imgproc.boundingRect(point);
        double decide = 0;
        if (area>0){
            decide = rect.width*rect.height/area;
        }
        if (decide>1.2 && decide<1.8){
            return true;
        }else {
            return false;
        }
    }

    private static boolean  squareFilter(MatOfPoint point){
        Rect rect = Imgproc.boundingRect(point);
        if (Math.abs(rect.width - rect.height) <= 8){
            return true;
        }else{
            return false;
        }
    }

    private static boolean centerPointFilter(MatOfPoint matPoint, Mat dst){
        Point point = new Point();
        float[] radius = new float[1];
        MatOfPoint2f  matOfPoint2f = new MatOfPoint2f(matPoint.toArray());
        Imgproc.minEnclosingCircle(matOfPoint2f, point, radius);
        int x = (int)point.x;
        int y = (int)point.y;
        if(dst.get(y, x)[0] == 255){
            return true;
        }else {
            return false;
        }
    }
    private static boolean sizeFilter(MatOfPoint point, double area){
        Rect rect = Imgproc.boundingRect(point);
        double w1 = rect.width/area;
        double h1 = rect.height/area;
        if (w1>0.00005 && h1>0.00005){
            return true;
        }else {
            return false;
        }
    }
    private static List<double[]> resultFilter(List<double[]> list){
        List<double[]> resultList = new ArrayList<>();
        int length = list.size();
        double sum = 0;
        for (int i=0; i<length; i++){
            double w = list.get(i)[2];
            double h = list.get(i)[3];
            sum = sum + w*h;
        }
        double threshold = (sum/length) -Math.abs(30-(length-1)*7)*131-(sum/(600 * 400)) * 7860+500;
        for (int i=0; i<length; i++){
            double w = list.get(i)[2];
            double h = list.get(i)[3];
            if (threshold <= (w*h)){
                list.get(i)[0]=list.get(i)[0]-15;
                list.get(i)[1]=list.get(i)[1]-15;
                list.get(i)[2]=list.get(i)[2]+list.get(i)[0]+30;
                list.get(i)[3]=list.get(i)[3]+list.get(i)[1]+30;
                resultList.add(list.get(i));
            }
        }
        return resultList;
    }
    private static String getToken(){
        String getAccessTokenUrl = Constants.BAIDU_AUTH_HOST
                + "?grant_type=client_credentials"
                + "&client_id=" + Constants.BAIDU_CLIENT_ID
                + "&client_secret=" + Constants.BAIDU_CLIENT_SECRET;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            JSONObject jsonObject = JSON.parseObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }


    public static List<List<Integer>> detectionApiByBaiDu(String data){
        List<List<Integer>> resultData = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("image", data);
        map.put("top_num", "5");
        JSONObject jsonObject = new JSONObject(map);
        String param = jsonObject.toJSONString();
        String accessToken = getToken();
        String result = null;
        try {
            result = BaiDuHttpUtil.post(Constants.BAIDU_TYRE_DETECTION_URL, accessToken, "application/json", param);
        } catch (Exception e) {
            throw new TyreException(ExceptionEnum.UNKNOWN);
        }
        JSONObject jsonObject1 = JSON.parseObject(result);
        if (result.indexOf("error_code")>-1){
            return Collections.emptyList();
        }
        result = jsonObject1.getString("results");
        JSONArray jsonArray = jsonObject1.getJSONArray("results");
        for (int i=0; i<jsonArray.size(); i++){
            List<Integer> tempList = new ArrayList<>();
            String temp = ((JSONObject)jsonArray.get(i)).getString("location");
            JSONObject json = JSON.parseObject(temp);
            int top = json.getInteger("top");
            int left = json.getInteger("left");
            int width = json.getInteger("width");
            int height = json.getInteger("height");
            tempList.add(left);
            tempList.add(top);
            tempList.add(width+left);
            tempList.add(height+top);
            resultData.add(tempList);
        }
        return resultData;
    }


}
