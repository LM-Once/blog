package com.it.blog.com.it.blog.jframe;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


/**
 * 焦点事件监听器
 * @author 18576756475
 *
 */
public class FocusListenerDemo extends JFrame
{
    JList list;
    JLabel label;
    JButton button1;
    JTextField txtfield1;
    public FocusListenerDemo()
    {
        setTitle("焦点事件监听器示例");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,400,200);
        JPanel contentPane=new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        contentPane.setLayout(new BorderLayout(0,0));
        setContentPane(contentPane);       
        label=new JLabel(" ");
        label.setFont(new Font("楷体",Font.BOLD,16));    //修改字体样式
        contentPane.add(label, BorderLayout.SOUTH);
        txtfield1=new JTextField();    //创建文本框
        txtfield1.setFont(new Font("黑体", Font.BOLD, 16));    //修改字体样式
        txtfield1.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent arg0)
            {
                // 获取焦点时执行此方法
                label.setText("文本框获得焦点，正在输入内容");
            }
            @Override
            public void focusLost(FocusEvent arg0)
            {
                // 失去焦点时执行此方法
                label.setText("文本框失去焦点，内容输入完成");
            }
        });
        contentPane.add(txtfield1);
    }
    public static void main(String[] args)
    {
        FocusListenerDemo frame=new FocusListenerDemo();
        frame.setVisible(true);
    }
}