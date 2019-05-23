package com.wei.cn.vo.migu;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("咪咕听书内容详情返回结果")
public class MiguListenContentDetailResult {


    public String contentId;
    public String contentName;
    public String subtitle;
    public String updatetime;
    public String publishDate;
    public String author;
    public String chargeMode;
    public String infofee;
    public String effectiveTime;
    public String shortrecommend;
    public String longrecommend;
    public String isfinish;
    public String isserial;
    public String sourcefrom;
    public String score;
    public String goals;
    public String audiences;
    public String mcpId;
    public String mcpName;
    public String infofeeChapterId;
    public String chapterCount;



    public List<MiguListenContentType> typeList;
    public List<MiguListenPic> picList;
    public List<MiguListenReader> readerList;
    public List<MiguListenChaper> chapterList;
    public String wordCount;
    public MiguListenContentRecord latesteRecord;


    /**
     * getContentChapterList 接口增加字段
     */
    public String newsList;
    public String weekList;
    public String musicList;





}
