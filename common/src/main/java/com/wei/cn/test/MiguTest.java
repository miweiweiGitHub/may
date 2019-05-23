package com.wei.cn.test;

public class MiguTest {

    public static final String XIMALAYA_ALBUMS_ID_TRACKS = "https://api.ximalaya.com/smart-os-gateway/smart-os-openapi/albums/";

    public static void main(String[] args) {

        String ss = XIMALAYA_ALBUMS_ID_TRACKS+String.valueOf(123)+"/tacks";
        String s = "http://127.0.0.1:14340/ximalaya/text/query?deviceId=110003801000625&productId=N7N8DesO&text=hello&publicParams={\n" +
                "\"osClientId\":\"os.client.000001\",\n" +
                "\"deviceType\":1,\n" +
                "\"deviceId\":\"dajkjfaSASFAsca\",\n" +
                "\"lat\":\"12.3\",\n" +
                "\"lng\":\"32\",\n" +
                "\"appVersion\":\"1.2\",\n" +
                "\"sysVersion\":\"8.0\", \n" +
                "\"sysType\":1,\n" +
                "\"productId\":\"N_PROD1_DEMO\",\n" +
                "\"dt\":23123123123\n" +
                "}";

        System.out.println(s.substring(111));


    }


}
