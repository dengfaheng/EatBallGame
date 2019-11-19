package com.hust.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class GUI {

    public final int graphWidth;
    public final int graphHeight;
    public final int STARTX = 650;
    public final int STARTY = 450;
    public final int buttonwidth = 100;
    public final int buttonheight = 60;
    public final int EXITX = 650;
    public final int EXITY = 600;
    public static final int PROGRESSWIDTH = 40;
    public static final int BOTTOM = 70;

    public int mouseX;
    public int mouseY;
    public Circle[] willPaint = new Circle[Game.CIRCLECOUNT+1];
    public paintPanel conn = new paintPanel(willPaint,"paintPanel");
    public JFrame jf;
    public JButton start;
    public JButton score;
    public JLabel currentScoreLabel;
    public JLabel maxScoreLabel;
    public JLabel gameLevelLabel;
    public JButton back;
    public JLabel[] noi = new JLabel[10];
    public ProgressUI jProBar;
    public GUI(){
        jf = new JFrame("Big ball eat Small ball");
        Toolkit kit = Toolkit.getDefaultToolkit();
        graphWidth = kit.getScreenSize().width;
        graphHeight = kit.getScreenSize().height-BOTTOM;
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        
        jf.setLayout(null);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        conn.setLayout(null);
        start = new JButton("start");
        start.setBounds(STARTX,STARTY,buttonwidth,buttonheight);
        score = new JButton("score");
        score.setBounds(EXITX,EXITY,buttonwidth,buttonheight);
        back = new JButton("Back");
        for(int i = 0; i < noi.length; i++){
            noi[i] = new JLabel();
            noi[i].setBounds(graphWidth / 2 - 50, 300 + i * 40, 100, 30);
            noi[i].setVisible(false);
            conn.add(noi[i]);
        }
        back.setBounds(graphWidth / 2 - 50, 300 + 10 * 40, 100, 50);
        back.setVisible(false);
        currentScoreLabel = new JLabel();
        currentScoreLabel.setVisible(false);
        currentScoreLabel.setBounds(10, 40, 100, 20);
        
        maxScoreLabel = new JLabel();
        maxScoreLabel.setVisible(false);
        maxScoreLabel.setBounds(10, 60, 100, 20);
        
        gameLevelLabel = new JLabel();
        gameLevelLabel.setVisible(false);
        gameLevelLabel.setBounds(10, 80, 100, 20);
        
        conn.add(back);
        conn.add(start);
        conn.add(score);
        conn.add(currentScoreLabel);
        conn.add(maxScoreLabel);
        conn.add(gameLevelLabel);
        
        jProBar = new ProgressUI();
        jProBar.getjProgressBar().setSize(graphWidth, PROGRESSWIDTH);
        jProBar.getjProgressBar().setLocation(0, 0);
        jProBar.getjProgressBar().setVisible(false);
        conn.add(jProBar.getjProgressBar());
        
        jf.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY() - 20;
            }
        });
        jf.setContentPane(conn);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    

    public void updateCircle(Circle c){
        if(c != null){
        	if(c.getID() == Game.CIRCLECOUNT) {
        		willPaint[c.getID()] = c;
        		jf.getContentPane().repaint();
        	}else {
        		willPaint[c.getID()] = c;
        	}
        }
    }
    
    public void printAllEnemiesCircles() {
    	jf.getContentPane().repaint();
    }

    public void clear(){
        for(int i = 0; i < willPaint.length; i++){
            willPaint[i] = null;
        }
        jf.getContentPane().repaint();
    }
    
    
    

}

