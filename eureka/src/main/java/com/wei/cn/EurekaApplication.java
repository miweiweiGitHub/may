package com.wei.cn;

import com.wei.cn.configuration.ConfigInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);

		ConfigInfo configInfo = new ConfigInfo();
		System.out.println(configInfo.getUploadPath());
	}
}