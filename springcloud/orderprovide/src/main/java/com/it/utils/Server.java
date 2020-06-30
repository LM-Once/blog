package com.it.utils;

import java.io.*;

public class Server implements Runnable {

    private Integer port = 9000;

    private String ipAddress = "127.0.0.1";

    @Override
    public void run() {
        File pyFile = new File("");
        try {
            FileInputStream fis = new FileInputStream(pyFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void file (File file, String content){
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);

            byte[] contentInBytes = content.getBytes();
            fos.write(contentInBytes);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().file(new File("F:/1111.py"),
                "#!/usr/bin/env python\n" +
                        "# coding: utf-8\n" +
                        "\n" +
                        "# In[1]:\n" +
                        "\n" +
                        "\n" +
                        "# -*- coding: utf-8 -*-\n" +
                        "\n" +
                        "\"\"\"\n" +
                        " * @ 项目名称 : \n" +
                        " * @ 用例 ID:   \n" +
                        " * @ 编写人 :   \n" +
                        " * @ 创建时间 : \n" +
                        " * @ 修改时间 :\n" +
                        " * @ 修改说明 :\n" +
                        " * @ 脚本描述 : \n" +
                        " * @ 前置条件 :\n" +
                        " *      1. set_up\n" +
                        " * @ 测试步骤 :\n" +
                        " *      1. start_run\n" +
                        " * @ 后置操作 :\n" +
                        " *      1. tear_down\n" +
                        " * @ 预期结果 : 无\n" +
                        " * @ 拓扑依赖 : 测试机*1\n" +
                        " * @ 依赖库 : \n" +
                        "\"\"\"\n" +
                        "\n" +
                        "\n" +
                        "from oppotool import OppoPythonExeutor\n" +
                        "from oppotool import OppoMobile\n" +
                        "import time\n" +
                        "\n" +
                        "class Case(OppoPythonExeutor):\n" +
                        "\n" +
                        "    def set_up(self):\n" +
                        "        pass\n" +
                        "\n" +
                        "    def start_run(self):\n" +
                        "        pass\n" +
                        "        \n" +
                        "\n" +
                        "    def return_result(self):\n" +
                        "        pass\n" +
                        "\n" +
                        "    def tear_down(self):\n" +
                        "        pass\n" +
                        "\n" +
                        "\n" +
                        "if __name__ == '__main__':\n" +
                        "    executor = Case()\n" +
                        "    executor.run()\n" +
                        "\n");
    }
}
