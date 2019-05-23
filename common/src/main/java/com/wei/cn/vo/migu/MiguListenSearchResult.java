package com.wei.cn.vo.migu;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("咪咕听书搜索内容返回结果")
public class MiguListenSearchResult {


    public List<MiguListenContent> contentList;
    public List<String> radioList;
    public List<String> newsList;
    public List<String> restaurantList;
    public List<String> videoList;
    public List<String> albumInfoList;
    public List<String> musicInfoList;
    public List<String> musicRadioList;
}
