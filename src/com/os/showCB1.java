package com.os;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.*;

class showCB1 extends JPanel{

    int[] b=Y.getArray();//////////////读取数组
    public void paint(Graphics g){

        for(int i=0;i<1024;i++){
            int xx=i%32,yy=i/32;
            if(b[i]==1){
                g.setColor(Color.YELLOW);
                g.fillRect(30+xx*5, 30+yy*5, 5, 5);
            }
            else if(b[i]==2){
                g.setColor(Color.GREEN);
                g.fillRect(30+xx*5, 30+yy*5, 5, 5);
            }
        }
        g.setColor(Color.black);
        for(int i=0;i<=32;i++)g.drawLine(30,30+5*i,190,30+5*i);
        for(int i=0;i<=32;i++)g.drawLine(30+5*i, 30, 30+5*i, 190);
    }
}

