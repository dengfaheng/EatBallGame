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
    public final int STARTX = 180;
    public final int STARTY = 500;
    public final int buttonwidth = 100;
    public final int buttonheight = 60;
    public final int EXITX  = 330;
    public final int EXITY  = 500;
    public static final int PROGRESSWIDTH = 40;
    public static final int BOTTOM = 70;

    public int mouseX;
    public int mouseY;
    public Circle[] willPaint = new Circle[Game.CIRCLECOUNT+1];
    public PaintPanel conn = new PaintPanel(willPaint,"paintPanel");
    public JFrame jf;
    public JButton start;
    public JButton exit;
    public JLabel currentScoreLabel;
    public JLabel maxScoreLabel;
    public JLabel gameLevelLabel;
    public ProgressUI jProBar;
    public GUI(){
        jf = new JFrame("Big ball eat Small ball");
        Toolkit kit = Toolkit.getDefaultToolkit();
        graphWidth = kit.getScreenSize().width;
        graphHeight = kit.getScreenSize().height-BOTTOM;
        
        jf.setBounds(graphWidth/2-300, graphHeight/2-400, 600, 800);

        jf.setLayout(null);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        conn.setLayout(null);
        start = new JButton();
        start.setBounds(STARTX,STARTY,64,64);
        exit = new JButton();
        exit.setBounds(EXITX,EXITY,64,64);
        
        start.setIcon(new ImageIcon("./res/start.png"));
        start.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
        start.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
        start.setBorderPainted(false);//不打印边框  
        start.setBorder(null);//除去边框  
        start.setFocusPainted(false);//除去焦点的框  
        start.setContentAreaFilled(false);//除去默认的背景填充 
        
        
        exit.setIcon(new ImageIcon("./res/exit.png"));
        exit.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
        exit.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
        exit.setBorderPainted(false);//不打印边框  
        exit.setBorder(null);//除去边框  
        exit.setFocusPainted(false);//除去焦点的框  
        exit.setContentAreaFilled(false);//除去默认的背景填充 

        Font font=new Font("Monospaced",Font.BOLD,16);//设置字体格式和大小

        currentScoreLabel = new JLabel();
        currentScoreLabel.setVisible(false);
        currentScoreLabel.setFont(font);
        currentScoreLabel.setBounds(10, 40, 200, 20);
        
        maxScoreLabel = new JLabel();
        maxScoreLabel.setVisible(false);
        maxScoreLabel.setFont(font);
        maxScoreLabel.setBounds(10, 60, 200, 20);
        
        gameLevelLabel = new JLabel();
        gameLevelLabel.setVisible(false);
        gameLevelLabel.setFont(font);
        gameLevelLabel.setBounds(10, 80, 200, 20);
        
        conn.add(start);
        conn.add(exit);
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

