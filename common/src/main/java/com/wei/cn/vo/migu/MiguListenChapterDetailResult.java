package com.wei.cn.vo.migu;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("咪咕听书章节详情返回结果")
public class MiguListenChapterDetailResult {


    public String chapterId;
    public String chapterName;
    public String updatetime;
    public String publishDate;
    public String catalogid;
    public String isfree;
    public String chapterDesc;
    public String prevChapterId;
    public String nextChapterId;
    public String chaptertime;
    public String chapterSize;
    public String chaptertype;
    public String chapterListenCount;
    public String chapterurl;

    public String contentId;
    public String contentName;
    public String longrecommend;
    public String mcpId;
    public String mcpName;
    public String contentPicUrl;
    public String chapterPicUrl;
    public String readerId;
    public String readerName;
    public String chapterTxturl;
    public String penName;
    public String payUrl;
    public List<String> commonNewsDetails;
    public List<String> productInfoList;
    public String chapterAudioUrl;
    public String chapterAudioUrlCount;
    public String cdnChapterUrl;
    public String cdnToken;
    public String musicInfo;
    public String videoInfo;

}
