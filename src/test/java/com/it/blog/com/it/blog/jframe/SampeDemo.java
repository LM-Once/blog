package com.it.blog.com.it.blog.jframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * 星座选择器界面的实现
 * @author 18576756475
 *
 */
public class SampeDemo
{
    public static void main(String[] args)
    {
        new SampeDemo();    //调用构造方法
    }
    
    private JPanel panel=new JPanel();
    private JComboBox cmb=new JComboBox();    //创建JComboBox
    private JLabel label=new JLabel("添加新星座：");
    private JLabel showInfo=new JLabel();    //用于显示信息
    private JTextField jtf=new JTextField(16);    //用于输入信息
    private JButton buttonAdd=new JButton("新增");
    private JButton buttonDel=new JButton("删除");
    public SampeDemo()
    {
        JFrame frame=new JFrame("选择你的星座");
        cmb.addItem("--请选择--");    //向下拉列表中添加一项
        cmb.addItem("巨蟹座");
        cmb.addItem("狮子座");
        cmb.addItem("双鱼座");
        panel.add(cmb);
        panel.add(label);
        panel.add(jtf);
        panel.add(buttonAdd);
        panel.add(buttonDel);
        frame.add(panel);
        buttonAdd.addActionListener(new MyActionListener());    //“添加”按钮的事件
        buttonDel.addActionListener(new MyActionListener());    //“删除”按钮的事件
        cmb.addItemListener(new MyItemListener());    //下拉列表的事件
        frame.setBounds(300,200,600,200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
 // 监听选中事件
    class MyItemListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            String str=e.getItem().toString();
            panel.add(showInfo);
            showInfo.setText("您选择的星座是："+str);
        }
    }
    
 // 监听添加和删除按钮事件
    class MyActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String command=e.getActionCommand();
            //添加按钮处理
            if(command.equals("新增"))
            {
                if(jtf.getText().length()!=0)
                {
                    cmb.addItem(jtf.getText());    //添加项
                    panel.add(showInfo);
                    showInfo.setText("添加成功，新增了："+jtf.getText());
                }
                else
                {
                    panel.add(showInfo);
                    showInfo.setText("请输入要添加星座");
                }
            }
            //删除按钮处理
            if(command.equals("删除"))
            {
                if(cmb.getSelectedIndex()!=-1)
                {
                    //先获得要删除的项的值
                    String strDel=cmb.getSelectedItem().toString();
                    cmb.removeItem(strDel);    //删除项
                    panel.add(showInfo);
                    showInfo.setText("删除成功，删除了："+strDel);
                }
                else
                {
                    panel.add(showInfo);
                    showInfo.setText("请选择要删除的星座");
                }
            }
        }
    }
}
