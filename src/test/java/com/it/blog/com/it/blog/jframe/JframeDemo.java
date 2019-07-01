package com.it.blog.com.it.blog.jframe;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class JframeDemo extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JframeDemo() {
		setTitle("窗口");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel jl = new JLabel("创建的窗口");
		Container contentPane = getContentPane();
		contentPane.add(jl);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		JframeDemo jf = new JframeDemo();
	}

}
