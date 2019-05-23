package com.wei.cn.vo.ximalaya;

public class XimaPlay {

    private String type;
    private String playBehavior;
    private AudioItem audioItem;


    class AudioItem{
        private XimaStream stream;
        private XimaMetadata metadata;
    }

}
