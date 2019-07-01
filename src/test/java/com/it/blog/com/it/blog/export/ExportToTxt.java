package com.it.blog.com.it.blog.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;




public class ExportToTxt {
	
	private static final String filePath = "F:\\test.txt";
	
	private static final String writeContent = "防止文件建立或读取失败，"
			+ "用catch捕捉错误并打印，也可以throw防止文件建立或读取失败，用"
			+ "catch捕捉错误并打印，也可以throw  防止文件建立或读取失败，"
			+ "用catch捕捉错误并打印，也可以throw防止文件建立或读取失败，"
			+ "用catch捕捉错误并打印，也可以throw  "
			+ "防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw    ";
	
	public static void main(String[] args) {
		
		boolean flag = exportTxt(filePath, writeContent);
		System.out.println(flag);
	}
	
	
	public static void exportListToTxt() {
		List<String> list = new ArrayList<String>();
		list.add("Hello,World!");  
		list.add("Hello,World!");  
		list.add("Hello,World!");  
		list.add("Hello,World!");  
		boolean isSuccess =false;
		try {
            File file = new File("D:\\123.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = null;
          	
          	out = new FileOutputStream(file);
			isSuccess= exportTxtByOS(out, list);
        } catch (Exception e) {
            System.out.println("文件创建失败！" + e);
        }
		System.out.println(isSuccess);
	}
 
	/**
	   *         导出
	 * @param out
	   *         输出流
	 * @param dataList
	   *         数据
	 * @return
	 */
	public static boolean exportTxtByOS(OutputStream out, List<String> dataList) {
		boolean isSucess = false;
 
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			osw = new OutputStreamWriter(out);
			bw = new BufferedWriter(osw);
			// 循环数据
			for (int i = 0; i < dataList.size(); i++) {
				bw.append(dataList.get(i)).append("\r\n");
			}
			
			isSucess = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSucess = false;
 
		} finally {
			if (bw != null) {
				try {
					bw.close();
					bw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return isSucess;
	}
	
	public static boolean exportTxt(String filePath,String writeContent) {
		String str="我是一颗自由小星星我是一"
				+ "颗自由小星星我是一颗自由小星星;"
				+ "我是一颗自由小星星我是一颗自由小"
				+ "星星我是一颗自由小星星我是一颗自"
				+ "由小星星我是一颗自由小星星我是一"
				+ "颗自由小星星我是一颗自由小星星我"
				+ "是一颗自由小星星我是一颗自由小星"
				+ "星我是一颗自由小星星";
		try {
        	
            byte[] b = writeContent.getBytes();
            File newfile = new File(filePath);
            if (newfile.exists()) {
            	System.out.println("文件已存在");
            	return false;
            }
            newfile.createNewFile();
            FileOutputStream  fos = new FileOutputStream(newfile);
			fos.write(b);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return true;
	}
}
