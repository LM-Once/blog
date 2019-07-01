package com.it.blog.com.it.blog.jframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class ToolBarDemo extends JPanel implements ActionListener
{
    protected JTextArea textArea;
    protected String newline="\n";
    static final private String OPEN="OPEN";
    static final private String SAVE="SAVE";
    static final private String NEW="NEW";   
    //事件监听器部分的代码省略，请查阅源文件
    protected void displayResult(String actionDescription)
    {
        textArea.append(actionDescription+newline);
    }
    public static void main(String[] args)
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        //定义窗体
        JFrame frame=new JFrame("工具栏");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //定义面板
        ToolBarDemo newContentPane=new ToolBarDemo();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        //显示窗体
        frame.pack();
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // TODO 自动生成的方法存根
    }
    
    public ToolBarDemo()
    {
        super(new BorderLayout());
        //创建工具栏
        JToolBar toolBar=new JToolBar();
        addButtons(toolBar);
        //创建一个文本域，用来输出一些信息
        textArea=new JTextArea(15, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane=new JScrollPane(textArea);
        //把组件添加到面板中
        setPreferredSize(new Dimension(450, 110));
        add(toolBar,BorderLayout.PAGE_START);
        add(scrollPane,BorderLayout.CENTER);
    }
    
    protected void addButtons(JToolBar toolBar)
    {
        JButton button=null;
        button=makeNavigationButton("new1",NEW,"新建一个文件","新建");
        toolBar.add(button);
        button=makeNavigationButton("open1",OPEN,"打开一个文件","打开");
        toolBar.add(button);
        button=makeNavigationButton("save1",SAVE,"保存当前文件","保存");
        toolBar.add(button);
    }
    
    protected JButton makeNavigationButton(String imageName,String actionCommand,String toolTipText,String altText)
    {
        //搜索图片
        String imgLocation=imageName+".jpg";
        URL imageURL=ToolBarDemo.class.getResource(imgLocation);
        //初始化工具按钮
        JButton button=new JButton();
        //设置按钮的命令
        button.setActionCommand(actionCommand);
        //设置提示信息
        button.setToolTipText(toolTipText);
        button.addActionListener(this);
        if(imageURL!=null)
        {
            //找到图像
            button.setIcon(new ImageIcon(imageURL));
        }
        else
        {
            //没有图像
            button.setText(altText);
            System.err.println("Resource not found: "+imgLocation);
        }
        return button;
    }
}
