package com.wei.cn.util.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TempHttpUtil {


    private static PoolingHttpClientConnectionManager cm;
    private static String EMPTY_STR = "";
    private static String UTF_8 = "UTF-8";

    static RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();

    private static void init() {
        if (cm == null) {
            cm = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);            // 整个连接池最大连接数
            cm.setMaxTotal(1000);            // 每路由最大连接数，默认值是2
            cm.setDefaultMaxPerRoute(50);
        }
    }

    /**
     * 通过连接池获取HttpClient     *     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        return HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(defaultRequestConfig)
                .setConnectionManagerShared(true)
                .build();
    }

    /**
     * @param url * @return
     */
    public static String httpGetRequest(String url) {
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url, String path, Map<String, Object> params){
        HttpGet httpGet = null;
        try {

            URIBuilder ub = new URIBuilder(url+path);
            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            ub.setParameters(pairs);
            httpGet = new HttpGet(ub.build());
            httpGet.addHeader("Accept","text/plain, application/json, application/*+json, */*");
            httpGet.addHeader("Connection","Keep-Alive");
            httpGet.addHeader("SessionId","1");
            httpGet.addHeader("Content-Type","application/json;charset=UTF-8");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        log.info("get ximalaya url :{}",httpGet.getRequestLine().getUri());
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params) throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);
        HttpGet httpGet = new HttpGet(ub.build());
        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        return getResult(httpGet);
    }

    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }
        return pairs;
    }

    /**
     * 处理Http请求
     */
    private static String getResult(HttpRequestBase request) {
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = getHttpClient();
        try {
            long start = System.currentTimeMillis();
            CloseableHttpResponse response = httpClient.execute(request);
            long end = System.currentTimeMillis();
            log.info("[HTTP]-[GET]-[请求ximalay服务]: 耗时:{}ms ",end-start);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity);
                log.info("[HTTP]-[GET]-[result] - code:{},entity:{}",response.getStatusLine().getStatusCode(),result);
                response.close();
                return result;
            }
        } catch (IOException e) {
            log.info("[HTTP]-[GET]-IOException  error:{} ",e);
            e.printStackTrace();
            return  "request ximalaya plat IOException ";
        }
        return EMPTY_STR;
    }

}
