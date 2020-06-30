package com.it.utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @version V1.0
 * @ClassName FileUtils
 * @Description 文件工具类
 * @Date 2019-12-05 16:40:12
 **/
public class FileUtils {

    /**
     * 打开文件的位置
     * @param filePath
     */
    public void openFileDir(String filePath){
        try {
            Desktop.getDesktop().open(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Date: 2019/12/05
     * @Param: [dir 文件夹路径]
     * @return: [文件列表]
     * @Description: 遍历文件夹下所有文件(包括子文件夹)
     */
    public static List<File> getAllFiles(String dir){

        List< File >files=new ArrayList();

        File file=new File(dir);
        if (file.exists()&&file.isDirectory()) {
            longErgodic(file,files);
        }
        return files;
    }

    /**
     * @Date: 2019/12/06
     * @Param: [path 文件夹路径]
     * @return: [文件列表]
     * @Description: 遍历文件夹下所有文件(不包括子文件夹)
     */
    public static List<File> getDirFiles(String path){
        File file = new File(path);
        List<File> fileList = new ArrayList<>();
        if (file.isDirectory()){

            File[] fileListArray = file.listFiles();
            if (null != fileListArray && fileListArray.length != 0){
                for(File useFile: fileListArray){
                    if (useFile.isFile()){
                        System.out.println(useFile.getName());
                        fileList.add(useFile);
                    }
                }
            }
        }
        return fileList;
    }

    private static void longErgodic(File file, List files) {

        File[] fillArr=file.listFiles();
        if (fillArr==null) {
            return;
        }
        for (File useFile : fillArr) {
            if (useFile.isFile()){
                files.add(useFile);
            }
            longErgodic(useFile, files);
        }
    }
    public static void main(String[] args) {
        List<File> allFiles = FileUtils.getAllFiles("E:\\oppo-workfile");
        System.out.println(allFiles);
    }
}
