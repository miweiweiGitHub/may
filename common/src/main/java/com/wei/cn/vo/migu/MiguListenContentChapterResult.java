package com.wei.cn.vo.migu;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("咪咕听书内容章节返回结果")
public class MiguListenContentChapterResult {


    public String chapterId;
    public String chapterName;
    public String subtitle;


    public List<MiguListenChaper> chapterList;
    public List<String> newsList;
    public List<String> weekList;
    public List<String> musicList;


}
