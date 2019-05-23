package com.wei.cn.vo.migu;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("咪咕听书分类列表返回结果")
public class MiguListenTypeListResult {

    public List<String> radioAreaList;
    public List<String> radioTypeList;
    public List<String> newsTypeList;
    public List<MiguListenType> typeList;


}
