package com.wei.cn.vo.ximalaya;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 小雅os公共参数
 */
@Data
@ApiModel("小雅os公共参数")
public class XimalayaPublicParams {

    /**
     * 小雅os开放平台分配Id 必填
     */
    @ApiModelProperty(name = "小雅os开放平台分配Id ")
    private String osClientId;
    /**
     * 小雅os的用户令牌 必填
     */
    @ApiModelProperty(name = "小雅os的用户令牌 ")
    private String osAccessToken;
    /**
     * 请求来源 1 app端 2 设备端 必填
     */
    @ApiModelProperty(name = "请求来源")
    private int deviceType;
    /**
     * 设备产品类型 deviceType 2 必填
     */
    @ApiModelProperty(name = "设备产品类型")
    private String productId;
    /**
     * 设备sn deviceType 2 必填
     */
    @ApiModelProperty(name = "设备sn")
    private String sn;
    /**
     * app设备号  deviceType 1 必填
     */
    @ApiModelProperty(name = "app设备号")
    private String deviceId;
    /**
     * 纬度
     */
    @ApiModelProperty(name = "纬度")
    private String lat;
    /**
     * 经度
     */
    @ApiModelProperty(name = "经度")
    private String lng;
    /**
     * 手机系统类型 1 安卓 2 ios deviceType 1 必填
     */
    @ApiModelProperty(name = "手机系统类型")
    private int sysType;
    /**
     * 手机应用版本号 deviceType 1 必填
     */
    @ApiModelProperty(name = "手机应用版本号")
    private String appVersion;
    /**
     * 对应手机系统版本号 deviceType 1 必填
     */
    @ApiModelProperty(name = "对应手机系统版本号")
    private String sysVersion;

    /**
     * 对应的设备版本号  deviceType 2 必填
     */
    @ApiModelProperty(name = "对应的设备版本号")
    private String speakerVersion;
    /**
     * 对应设备rom版本号 deviceType 2 必填
     */
    @ApiModelProperty(name = "对应设备rom版本号")
    private String romVersion;
    /**
     * 毫秒 uinx时间戳
     */
    @ApiModelProperty(name = "uinx时间戳 ")
    private long dt;



}
