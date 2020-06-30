package com.it.common;

import org.apache.log4j.Logger;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName CommonUtils
 * @Description TODO
 * @Date 2019-12-06 16:12:32
 **/
public class CommonUtils {

    private static Logger LOGGER = Logger.getLogger(CommonUtils.class);

    /**
     * 获取项目类路径
     * @author 18576756475
     * @MethodName getClassPath
     * @return
     */
    public static String getClassPath() {
        String projectPath = "";
        try {
            projectPath = ResourceUtils.getURL("classpath:").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return projectPath;
    }

    /**
     * 执行cmd
     * @author 18576756475
     * @MethodName executeCmd
     * @return
     */
    public static String executeCmd(String command) {
        LOGGER.info("execute command is :" + command);
        StringBuilder builder = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    process.getInputStream(), "UTF-8"));
            String line;
            builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                LOGGER.info("line :" + br.readLine());
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String s = executeCmd("where python");
        System.out.println(s);
    }
}
