package com.wei.cn.util.http;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Map;


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
        log.info("[请求 服务]: url:{},request:{}",url,data);
        ResponseEntity<String> result = RestClient.getClient().postForEntity(url, request, String.class);
        long end = System.currentTimeMillis();
        log.info("[请求 服务]: 耗时:{}ms, http code:{},body:{}",end-start,result.getStatusCode(),result.getBody());
        if(result.getStatusCode().value() == HTTP_SUCCESS_CODE){
            return result.getBody();
        }

        return null;
    }


    public static String handlerBaseGet(String url, String path, Map<String, Object> params){

        try {

            URIBuilder uri = new URIBuilder(url+path);
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    uri.addParameter(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
                }
            }

            long start = System.currentTimeMillis();
            log.info("[请求 服务]: url:{}",uri.toString());
            ResponseEntity<String> entity = RestClient.getClient().getForEntity(uri.build(), String.class);
            long end = System.currentTimeMillis();
            log.info("[请求 服务]: 耗时:{}ms, http code:{}",end-start,entity.getStatusCode());
            if(entity.getStatusCode().value() != HTTP_SUCCESS_CODE){
                log.info("[请求 异常]: 耗时:{}ms, http code:{},result:{}",end-start,entity.getStatusCode(),entity.getBody());
            }
            return entity.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            return "http get error !!!";

        }

    }



}