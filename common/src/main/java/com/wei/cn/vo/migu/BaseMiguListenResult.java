package com.wei.cn.vo.migu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("咪咕听书返回结果基础模型")
public class BaseMiguListenResult {

    @ApiModelProperty("流水号")
    public String requestSeq;
    @ApiModelProperty("请求类型")
    public String requestType;

    @ApiModelProperty("应答结果码")
    public String resultCode;
    @ApiModelProperty("应答结果信息")
    public String resultMsg;

    @ApiModelProperty("分页总数")
    public String pageTotal;
    @ApiModelProperty("每页显示的条数")
    public String pageSize;
    @ApiModelProperty("当前分页数")
    public String pageNum;
    @ApiModelProperty("排序方式")
    public String order;
    @ApiModelProperty("搜索结果总数")
    public String totalElements;




}
