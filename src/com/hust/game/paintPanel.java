package com.hust.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class paintPanel extends JPanel{
        String name;
        private Circle[] willPaint;
        public paintPanel(Circle[] willPaint,String name){
            this.willPaint = willPaint;
            this.name = name;
        }
        public String toString(){
            return name;
        }
        @Override
        public void paint(Graphics g){
            super.paint(g);
            for(Circle nowPainting : willPaint){
                if(nowPainting != null){
//                    System.out.println(nowPainting.color + " " + nowPainting.getID());
                	if(nowPainting.getID() == Game.CIRCLECOUNT /2) {
                        Image image=Toolkit.getDefaultToolkit().getImage("./res/boom.png");
                        g.drawImage(image, nowPainting.getX(), nowPainting.getY(),  this);//…Ë∂®Œª÷√ 
                        continue;
                	}
                    g.setColor(Color.decode(nowPainting.color));
                    g.fillOval(nowPainting.getX() - nowPainting.getR(),nowPainting.getY() - nowPainting.getR(),
                            nowPainting.getD(),nowPainting.getD());
                }
            }
        }
    }