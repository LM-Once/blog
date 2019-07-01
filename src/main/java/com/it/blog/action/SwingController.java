package com.it.blog.action;

import javax.swing.*;


public class SwingController {

    public static void main(String[] args){

        SimpleFrame frame = new SimpleFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

    }
}


class SimpleFrame extends  JFrame {

    public static final int DEFAULT_WIDTH = 500;

    public static  final  int DEFAULT_HEIGHT = 700;

    public SimpleFrame(){
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}
