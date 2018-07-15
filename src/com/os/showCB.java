package com.os;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class showCB extends JFrame{
    private JTextArea jta=new JTextArea();
    int count=0;
    private showCB1 cb=new showCB1();
    public showCB(){
        this.setTitle("磁盘快分配情况");

        int[]d=Y.getArray();
        for(int i=0;i<1024;i++)
            if(d[i]!=0)
                count++;
        jta.append("磁盘总数为：1024"+"\r\n");
        jta.append("磁盘占用为："+count+"\r\n");
        jta.append("磁盘剩余为："+(1024-count));
        this.add(cb,BorderLayout.CENTER);
        this.add(jta,BorderLayout.EAST);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)screensize.getWidth(),(int)screensize.getHeight());
        this.setLocationRelativeTo(null);
        this.setSize(400, 300);
        this.setVisible(true);
    }

    public static void main(String[]arg){
        new showCB();
    }


}
