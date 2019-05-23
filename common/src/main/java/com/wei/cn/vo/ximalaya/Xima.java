package com.wei.cn.vo.ximalaya;

public class Xima {

    public  ResSoundData resSoundData;

    class  ResSoundData{
        // 资源类型 1 内容资源 2 电台资源
        private Integer   contentType;
        // contentType 为2 时存在，电台id
        private Integer   radioId;
        // contentType 为2 时存在，⼴播电台时间表id
        private long   scheduleId;
        // contentType 为1 时存在，专辑id
        private long   albumId;
        // contentType 为1 时存在，声⾳id
        private long   trackId;
    }

}
