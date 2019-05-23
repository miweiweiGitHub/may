package com.wei.cn.util.http;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


/**
 * Http工具类
 */
@Slf4j
public class RestUtil {


    public static final int HTTP_SUCCESS_CODE = 200;

    public static String sendPostHttpRequest(String url, String data){

        HttpHeaders restHeaders = new HttpHeaders();
        restHeaders.add("Content-Type","application/json;charset=utf-8");
        HttpEntity<String> request = new HttpEntity<String>(data, restHeaders);
        long start = System.currentTimeMillis();
        log.info("[请求migu服务]: url:{},request:{}",url,data);
        ResponseEntity<String> result = RestClient.getClient().postForEntity(url, request, String.class);
        long end = System.currentTimeMillis();
        log.info("[请求migu服务]: 耗时:{}ms, http code:{},body:{}",end-start,result.getStatusCode(),result.getBody());
        if(result.getStatusCode().value() == HTTP_SUCCESS_CODE){
            return result.getBody();
        }

        return null;
    }

}