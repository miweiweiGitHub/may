package com.wei.cn;

import com.wei.cn.configuration.ConfigInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.ConfigServerApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import javax.annotation.Resource;

@SpringBootApplication
@EnableConfigServer
public class ConfigApplication {


	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);

    }
}
