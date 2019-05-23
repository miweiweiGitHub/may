package com.wei.cn.util;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 输入地名获取经纬度, 以及详细地址工具类
 */
@Slf4j
public class LngAndLatUtil {


    /**
     * 功能：传入地址，返回字符串经纬度和详细地址和邮政编码         *         * @param address         * @return
     */
    public static String getLngAndLatAndAddress(String address) {
        Map<String, Double> map = getLngAndLat(address);
        String info = "";
        if (map.size() != 0) {
            String addressInfo = getAddress(String.valueOf(map.get("lng")), String.valueOf(map.get("lat")));
            //String psotCode = getPostCode(addressInfo);
            info = map.get("lng") + "," + map.get("lat") + "," + "";
        }
        return info;
    }

    /**
     * 通过模糊地址获取经纬度         *         * @param address         * @return
     */
    public static Map<String, Double> getLngAndLat(String address) {
        Map<String, Double> map = new HashMap<String, Double>();
        String ak = "sATRYUYdhIAXtin4lHDsXasASGvRUZ5Q";
        String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak="+ak;
        //http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak
        String json = loadJSON(url);
        JSONObject obj = null;
        try {
            obj = JSONObject.parseObject(json);
            if (obj.get("status").toString().equals("0")) {
                double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
                double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
                map.put("lng", lng);
                map.put("lat", lat);
                System.out.println("经度：" + lng + "---纬度：" + lat);
            } else {
                System.out.println("未找到相匹配的经纬度！");
                Integer lng[] = {113, 114, 119, 115};
                Integer lat[] = {34, 38, 26, 28};
                //int random = CreateDataUtil.getGaussianRandom(0, 3);
                Random ra =new Random();
                int random = ra.nextInt(3);
                map.put("lng", lng[random].doubleValue());
                map.put("lat", lat[random].doubleValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
            Integer lng[] = {113, 114, 119, 115};
            Integer lat[] = {34, 38, 26, 28};
            //int random = CreateDataUtil.getGaussianRandom(0, 3);
            Random ra =new Random();
            int random = ra.nextInt(3);
            map.put("lng", lng[random].doubleValue());
            map.put("lat", lat[random].doubleValue());
        }
        return map;
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }

    /**
     * 功能:通过经纬度获取地址         *         * @param lng         * @param lat         * @return
     */
    public static String getAddress(String lng, String lat) {
        String add = getAdd(lng, lat);
        JSONObject jsonObject = JSONObject.parseObject(add);
        JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("addrList"));
        JSONObject j_2 = JSONObject.parseObject(jsonArray.get(0).toString());
        String allAdd = j_2.getString("admName");
        String address_road = "";
        if (j_2.containsKey("addr")) {
            address_road = j_2.getString("addr");
        }
        String addressInfo = allAdd.replaceAll(",", "") + address_road;
        return addressInfo;
    }

    public static String getAdd(String log, String lat) {
        //lat 小  log  大            //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l=" + lat + "," + log + "&type=010";
        String res = "";
        try {
            URL url = new URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line + "\n";
            }
            in.close();
        } catch (Exception e) {
            System.out.println("error in wapaction,and e is " + e.getMessage());
        }
        return res;
    }

    /**
     * 通过地名获取邮政编码         *         * @param address         * @return
     */
    public static String getPostCode(String address) {
        String postCode = "";
        String urlString = "http://cpdc.chinapost.com.cn/web/index.php?m=postsearch&c=index&a=ajax_addr&searchkey=" + address;
        String json = loadJSON(urlString);
        JSONObject obj = JSONObject.parseObject(json);
        if ((Integer) obj.get("now_num") > 0) {
            List list = (List) obj.get("rs");
            for (Object result : (List) obj.get("rs")) {
                JSONObject fromObject = JSONObject.parseObject(result.toString());
                if (Integer.valueOf(fromObject.get("POSTCODE").toString()) != -1) {
                    postCode = fromObject.get("POSTCODE").toString();
                    break;
                }
            }
        } else {
            System.out.println("未找到相匹配的邮编地址！");
        }
        if (StringUtils.isEmpty(postCode)) {
            postCode = "000000";
        }
        return postCode;
    }


    public static void main(String[] args) {
        String lngAndLatAndAddress = getLngAndLatAndAddress("北京市海淀区上地十街10号");
        log.info("lngAndLatAndAddress : {}",lngAndLatAndAddress);

    }

}
