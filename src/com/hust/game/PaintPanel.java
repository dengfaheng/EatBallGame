package com.hust.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PaintPanel extends JPanel{
        String name;
        private Circle[] willPaint;
        public PaintPanel(Circle[] willPaint,String name){
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
            	int maxBoomsNr = 0;
            	int maxLifesNr = 0;
                if(nowPainting != null){
//                    System.out.println(nowPainting.color + " " + nowPainting.getID());
                	if(nowPainting.color.equals("#EED5D2") && (maxBoomsNr <= 3)) {
                        Image image=Toolkit.getDefaultToolkit().getImage("./res/boom.png");
                        nowPainting.radius = 16;
                        g.drawImage(image, nowPainting.getX(), nowPainting.getY(),  this);//设定位置 
                        maxBoomsNr++;
                        continue;
                	}else if(nowPainting.color.equals("#6A5ACD") && (maxLifesNr <= 3)) {
                		Image image=Toolkit.getDefaultToolkit().getImage("./res/life.png");
                		nowPainting.radius = 16;
                		g.drawImage(image, nowPainting.getX(), nowPainting.getY(),  this);//设定位置 
                        maxLifesNr++;
                        continue;
                	}
                    g.setColor(Color.decode(nowPainting.color));
                    g.fillOval(nowPainting.getX() - nowPainting.getR(),nowPainting.getY() - nowPainting.getR(),
                            nowPainting.getD(),nowPainting.getD());
                }
            }
        }
    }