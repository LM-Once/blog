package com.it.blog.com.it.blog.jframe;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JSlider;

/**
 * 滑块组件
 * @author 18576756475
 *
 */
public class JSliderDemo{
	
    public static void main(String[] agrs)
    {
        JFrame frame=new JFrame("滑块组件示例");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane=frame.getContentPane();
        JSlider slider=new JSlider(0,100);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintLabels(true);   
        slider.setPaintTicks(true);
        contentPane.add(slider);
        frame.setVisible(true);       
    }
}