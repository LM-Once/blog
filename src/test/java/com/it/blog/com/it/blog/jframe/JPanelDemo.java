package com.it.blog.com.it.blog.jframe;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelDemo {

	
	public static void main(String[] args) {
		
		JFrame jFrame = new JFrame("窗口");
		jFrame.setBounds(500, 500, 400, 400);
		
		JPanel jp=new JPanel();    //创建一个JPanel对象
		
        JLabel jl=new JLabel("这是放在JPanel上的标签");    //创建一个标签
        
        jp.setBackground(Color.white);    //设置背景色
        
        jp.add(jl);    //将标签添加到面板
        jFrame.add(jp);    //将面板添加到窗口
        
        JPanel jp2 = new JPanel();
        JLabel jl2 = new JLabel("第二个标签");
        jp.setBackground(Color.WHITE);
        jp.add(jl2);
        jFrame.add(jp2);
        
        jFrame.setVisible(true);    //设置窗口可见
	}
}
