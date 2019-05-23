package com.wei.cn.util.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author kuangshumin
 * @Date 2018/11/28
 */
@Lazy(false)
public class RestClient {

    private static RestTemplate restTemplate;

    static {

        // 长连接保持时长30秒
        PoolingHttpClientConnectionManager pollingConnectionManager =
                new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        //最大连接数
        pollingConnectionManager.setMaxTotal(1000);
        //单路由的并发数
        pollingConnectionManager.setDefaultMaxPerRoute(1000);
        //socket超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000)
                .build();

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(pollingConnectionManager);
        // 重试次数2次，并开启
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(1, true));
        // 保持长连接配置，需要在头添加Keep-Alive
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        //逐出超时连接
        httpClientBuilder.evictExpiredConnections();
        //逐出空闲连接
        httpClientBuilder.evictIdleConnections(30, TimeUnit.SECONDS);

        //HttpClient httpClient = httpClientBuilder.build();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        // httpClient连接底层配置clientHttpRequestFactory

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(closeableHttpClient);

        // 连接超时时长配置
        clientHttpRequestFactory.setConnectTimeout(5000);
        // 数据读取超时时长配置
        clientHttpRequestFactory.setReadTimeout(5000);
        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        clientHttpRequestFactory.setConnectionRequestTimeout(200);
        // 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
        clientHttpRequestFactory.setBufferRequestBody(false);
        restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
    }

    private RestClient() {
    }

    public static RestTemplate getClient() {
        return restTemplate;
    }

}


