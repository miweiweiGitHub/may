package com.wei.cn.util.http;


import org.apache.http.Consts;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.CodingErrorAction;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * 互联网工具类
 */
public class SunniwellHttpProxy {

	private static final Logger log = LoggerFactory.getLogger(SunniwellHttpProxy.class);
    private static CloseableHttpClient httpclient = null;

	static RequestConfig defaultRequestConfig = RequestConfig.custom()
			.setSocketTimeout(5000)
			.setConnectTimeout(5000)
			.setConnectionRequestTimeout(5000)
			.build();

    static {
        try {
            PoolingHttpClientConnectionManager pollingConnectionManager =
                    new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
            //最大连接数
            pollingConnectionManager.setMaxTotal(1000);
            //单路由的并发数
            pollingConnectionManager.setDefaultMaxPerRoute(1000);

            MessageConstraints messageConstraints = MessageConstraints.custom().build();
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                                                .setMalformedInputAction(CodingErrorAction.IGNORE)
                                                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                                                .setCharset(Consts.UTF_8)
                                                .setMessageConstraints(messageConstraints)
                                                .build();
            pollingConnectionManager.setDefaultConnectionConfig(connectionConfig);

            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            pollingConnectionManager.setDefaultSocketConfig(socketConfig);


            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setConnectionManager(pollingConnectionManager);
            // 重试次数2次，并开启
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(1, true));
            // 保持长连接配置，需要在头添加Keep-Alive
            httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
            httpClientBuilder.setDefaultRequestConfig(defaultRequestConfig);
            //逐出超时连接
            httpClientBuilder.evictExpiredConnections();
            //逐出空闲连接
            httpClientBuilder.evictIdleConnections(30, TimeUnit.SECONDS);

            httpclient = httpClientBuilder.build();

         }catch (Exception e){
            log.error("some error is init, please check it.", e);
        }

    }



	public static String sendGetHttpRequest(String url, String path, Map<String, Object> params)  {

		//CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = null;
        try {
            URIBuilder uri = new URIBuilder(url+path);
            if (params != null && !params.isEmpty()) {
                for (Entry<String, Object> entry : params.entrySet()) {
                    uri.addParameter(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
                }
            }
            httpGet = new HttpGet(uri.build());
            httpGet.setConfig(defaultRequestConfig);
            httpGet.addHeader("Accept","text/plain, application/json, application/*+json, */*");
            httpGet.addHeader("Connection","Keep-Alive");
            httpGet.addHeader("SessionId","1");
            httpGet.addHeader("Content-Type","application/json;charset=UTF-8");

			log.info("get url :{}",httpGet.getRequestLine().getUri());
            long start = System.currentTimeMillis();
			CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
            long end = System.currentTimeMillis();
            String entity = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
            log.info("[HTTP]-[GET]-[请求ximalay服务]: 耗时:{}ms - [code:{},entity:{}]",end-start,httpResponse.getStatusLine().getStatusCode(),entity);
            httpResponse.close();
            return entity;

        } catch (URISyntaxException e) {
            log.error("URISyntaxException e:{}",e);
            e.printStackTrace();
        } catch (ClientProtocolException e) {
			log.error("ClientProtocolException e:{}",e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("IOException e:{}",e);
			e.printStackTrace();
		} finally {
            if(null!=httpGet){
                httpGet.reset();
            }
		}
		return null;
	}




	public static String sendPostHttpRequest(String url, String data,String headerName,String headerValue) {
		log.info("[HTTP]-[POST] " + url);
		String bodyAsString = null;
		//CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		HttpPost httppost = new HttpPost(url);
        try {
			StringEntity myEntity = new StringEntity(data, Consts.UTF_8);
			httppost.setEntity(myEntity);
			httppost.setHeader(headerName,headerValue);
			long start = System.currentTimeMillis();
			CloseableHttpResponse httpResponse = httpclient.execute(httppost);
			bodyAsString = EntityUtils.toString(httpResponse.getEntity());
			long end = System.currentTimeMillis();
			log.info("[请求migu服务]: 耗时:{}ms",end-start);
			log.info("[HTTP]-[POST]-[" + httpResponse.getStatusLine().getStatusCode() + "] " + bodyAsString);
		} catch (ClientProtocolException e) {
            log.error("ClientProtocolException e:{}",e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("IOException e:{}",e);
            e.printStackTrace();
        } finally {
			httppost.reset();
		}
		return bodyAsString;
	}



}
