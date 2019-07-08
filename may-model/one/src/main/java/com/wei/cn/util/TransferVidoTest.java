package com.wei.cn.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lw on 2019/7/8 14:22
 */
@Slf4j
public class TransferVidoTest {


    private final static String PATH = "F:\\2019\\vido\\Nginx";

    public static void main(String[] args) {
        File file = new File(PATH);
        func(file);
       /* if (process()) {        //执行转码任务
            System.out.println("ok");
        }*/
    }

    private static void func(File file){
        File[] fs = file.listFiles();
        for(File f:fs){
            if(f.isDirectory())	//若是目录，则递归打印该目录下的文件
                func(f);
            if(f.isFile() && f.getName().endsWith("wmv")){
                //若是文件，直接打印
                boolean delete = f.delete();
                log.info("func  file:{},isOk:{}",f.getPath(),delete);
            }
        }
    }



    private static boolean process() {
        // 判断视频的类型
        int type = checkContentType();
        boolean status = false;
        //如果是ffmpeg可以转换的类型直接转码，否则先用mencoder转码成AVI
        if (type == 0) {
            System.out.println("直接将文件转为flv文件");
            //status = processFLV(PATH);// 直接将文件转为flv文件
        } else if (type == 1) {
            String avifilepath = processAVI(type);
            if (avifilepath == null)
                return false;// avi文件没有得到
            //status = processFLV(avifilepath);// 将avi转为flv
        }
        return status;
    }

    private static int checkContentType() {
        String type = PATH.substring(PATH.lastIndexOf(".") + 1, PATH.length())
                .toLowerCase();
        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mpg")) {
            return 0;
        } else if (type.equals("wmv")) {
            return 0;
        } else if (type.equals("3gp")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("asf")) {
            return 0;
        } else if (type.equals("asx")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        }
        // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        else if (type.equals("wmv9")) {
            return 1;
        } else if (type.equals("rm")) {
            return 1;
        } else if (type.equals("rmvb")) {
            return 1;
        }
        return 9;
    }


    // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
    private static String processAVI(int type) {
        List<String> commend = new ArrayList<String>();
        commend.add("D:\\ffmpeg\\mencoder");
        commend.add(PATH);
        commend.add("-oac");
        commend.add("lavc");
        commend.add("-lavcopts");
        commend.add("acodec=mp3:abitrate=64");
        commend.add("-ovc");
        commend.add("xvid");
        commend.add("-xvidencopts");
        commend.add("bitrate=600");
        commend.add("-of");
        commend.add("avi");
        commend.add("-o");
        commend.add("【存放转码后视频的路径，记住一定是.avi后缀的文件名】");
        try {
            //调用线程命令启动转码
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.start();
            return "【存放转码后视频的路径，记住一定是.avi后缀的文件名】";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
    private static boolean processWMVtoAVI(File file) {

        // 文件命名
//        Calendar c = Calendar.getInstance();
//        String savename = String.valueOf(c.getTimeInMillis())+ Math.round(Math.random() * 100000);

        String replace = file.getPath().replace("wmv", "avi");

        List<String> commend = new ArrayList<String>();
        commend.add("C:\\lwsofteware\\FFmpeg\\ffmpeg-win64-static\\bin\\ffmpeg");
        commend.add("-i");
        commend.add(file.getPath());
        commend.add("-c");
        commend.add("copy");
        commend.add("-map");
        commend.add("0");
        commend.add(replace);

        try {
//            Runtime runtime = Runtime.getRuntime();
//            Process proce = null;
//            //视频截图命令，封面图。  8是代表第8秒的时候截图
//            String cmd = "    ffmpeg  -i  "
//                    + file.getPath()
//                    + "  -c   copy -map 0 "
//                    + replace;
//
//            proce = runtime.exec(cmd);

            //调用线程命令进行转码
            ProcessBuilder builder = new ProcessBuilder(commend);
            builder.command(commend);
            Process process = builder.start();
            log.info("do processWMVtoAVI  newFile:{}",replace);


            // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            String line = "";
            while ((line = br.readLine()) != null) {

            }
            if (br != null) {
                br.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (errorStream != null) {
                errorStream.close();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
