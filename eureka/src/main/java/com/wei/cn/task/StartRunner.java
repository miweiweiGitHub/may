package com.wei.cn.task;

import com.wei.cn.configuration.ConfigInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author kuangshumin
 * @Date 2019/4/29
 */
@Component
@Slf4j
public class StartRunner implements CommandLineRunner {

    @Resource
    private ConfigInfo configInfo;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(configInfo.getUploadPath());
    }
}
