package com.hust.game;

import javax.swing.*;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public boolean boom(Circle a, Circle b) {
        return a.getR() + b.getR() >=
                Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
    public void backMain(GUI gui){
    	gui.jf.getContentPane().setBackground(initColor);
    	gui.jf.setBounds(gui.graphWidth/2-300, gui.graphHeight/2-400, 600, 800);
        gui.exit.setVisible(true);
        gui.start.setVisible(true);
        gui.eatYourBalls.setText("再试试看吧！");
        gui.eatYourBalls.setVisible(true);
        gui.eatYourBalls.setBounds(120, 120, 500, 100);
        gui.jProBar.getjProgressBar().setVisible(false);
        gui.clear();
        new Circle(240, 350, 80, Game.CIRCLECOUNT+1, "#00CED1",gui, 0, 0);
        new Circle(350, 380, 40, Game.CIRCLECOUNT+2, "#ADFF2F",gui, 0, 0);
        gui.jf.getContentPane().repaint();

    }
    public static final int ORIGNALR = 15;
    public static final int CIRCLECOUNT = 50;
    public static final int MAX = 100;
    public static final int MIN = 10;
    public static int historyScore = 0;
    public static volatile int score = 0;
    public static volatile boolean gameplaying;
    public static volatile boolean lookingscore;
    public static Color initColor;
    
    public static int enemyMovingSpeed = 100;
    
    public static Random random;
    class PLAY {
        public int cnt;
        public int score;

        public PLAY(int cnt, int score) {
            this.cnt = cnt;
            this.score = score;
        }
    }


    public synchronized void startGame(final GUI gui) throws InterruptedException {
        
    	final PlayerCircle[] player = {new PlayerCircle(gui.mouseX, gui.mouseY, ORIGNALR, CIRCLECOUNT, "#000000", gui, MAX)};
        final Circle[] enemies = new Circle[CIRCLECOUNT];
        score = 0;
        gameplaying = true;
        random = new Random();

        for (int i = 0; i < CIRCLECOUNT; i++) {
            if (i < CIRCLECOUNT / 4 * 3) {
                int enermyR = random.nextInt(player[0].getR()) + MIN;
                if (i == CIRCLECOUNT / 2) {
                    do {
                        enemies[i] = new Circle(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                                - enermyR * 2-GUI.BOTTOM) + enermyR+GUI.PROGRESSWIDTH, enermyR, i, "#FFFF00",
                                gui, random.nextInt(10) + 1, random.nextInt(10) + 1);
                        System.out.println("boom ID = " + i);
                    } while (boom(enemies[i], player[0]));
                }else {
                	do {
                        enemies[i] = new Circle(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                                - enermyR * 2-GUI.BOTTOM) + enermyR+GUI.PROGRESSWIDTH, enermyR, i, MyUtils.getRandomColor(random),
                                gui, random.nextInt(10) + 1, random.nextInt(10) + 1);
                        System.out.println("boom ID = " + i);
                    } while (boom(enemies[i], player[0]));
                }
            } else {
                int enermyR = random.nextInt(MAX - player[0].getR()) + player[0].getR();
                do {
                    enemies[i] = new Circle(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                            - enermyR * 2-GUI.BOTTOM) + enermyR+GUI.PROGRESSWIDTH, enermyR, i,  MyUtils.getRandomColor(random),
                            gui, random.nextInt(3) + 1, random.nextInt(3) + 1);
                } while (boom(enemies[i], player[0]));
            }

        }
        gui.jf.getContentPane().repaint();
        System.out.println("h px = "+player[0].getX() +" py = "+player[0].getY());
        System.out.println("start and score has already unvisible");

        class playerMovingCircle implements Runnable {
            @Override
            public synchronized void run() {
                System.out.println("player moving");
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                while (gameplaying && player[0] != null) {
                    player[0].move();
                }
                System.out.println("player done");
            }
        }

        class enemyMoving implements Runnable {
            public synchronized void run() {
                System.out.println("enemies moving");
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
                while (gameplaying && player[0] != null) {
                    for (int i = 0; i < enemies.length; i++) {
                        if (enemies[i] == null) {
                            int enermyR;
                            if (i < CIRCLECOUNT / 4 * 3) {
                                enermyR = random.nextInt(player[0].getR() - MIN) + MIN;
                            } else {
                                enermyR = random.nextInt(MAX - player[0].getR()) + player[0].getR();
                            }
                            do{
                                enemies[i] = new Circle(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                                        - enermyR * 2-GUI.BOTTOM) + enermyR+GUI.PROGRESSWIDTH, enermyR, i, MyUtils.getRandomColor(random),
                                        gui, random.nextInt(3) + 1, random.nextInt(3) + 1);
                            } while (boom(enemies[i], player[0]));
                            if (i == CIRCLECOUNT / 2) {
                                do {
                                    enemies[i] = new Circle(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                                            - enermyR * 2-GUI.BOTTOM) + enermyR+GUI.PROGRESSWIDTH, enermyR, i, "#FFFF00",
                                            gui, random.nextInt(10) + 1, random.nextInt(10) + 1);
                                } while (boom(enemies[i], player[0]));
                            }
                        }
                        enemies[i].move();
                    }
                    gui.printAllEnemiesCircles(); //鏇存柊鎵�鏈� 
                    try {
                        Thread.sleep(enemyMovingSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                }
                System.out.println("enemies done");
            }
        }

        class countScore implements Runnable {
            public synchronized void run() {
                System.out.println("counting score");
                while (gameplaying) {
                    for (int i = 0; i < enemies.length; i++) {
                        if (enemies[i] != null && player[0] != null) {
                            if (boom(enemies[i], player[0])) {
                            	//炸弹检测
                            	if(enemies[i].color.equals("#EED5D2")) {
                            		gameplaying = false;
                                	break;
                            	}
                            	//药 检测
                            	if(enemies[i].color.equals("#6A5ACD")) {
                            		gui.jProBar.addValue(5);
                            		enemies[i] = null;
                            		continue;
                            	}
                            	//普通球检测
                                if (player[0].getR() > enemies[i].getR()) {
                                    if (i != CIRCLECOUNT / 2) {
                                        player[0].resize(1);
                                        score++;
                                        gui.currentScoreLabel.setText("当前成绩：" + score);
                                        if(score != 0 && score % 10 == 0) {
                                        	if(enemyMovingSpeed > 40) {
                                        		enemyMovingSpeed -= 20;
                                        	}else if(enemyMovingSpeed > 20) {
                                        		enemyMovingSpeed -= 5;
                                        	}else if(enemyMovingSpeed > 1) {
                                        		enemyMovingSpeed -= 1;
                                        	}
                                        	gui.gameLevelLabel.setText("难度等级：" + (100- enemyMovingSpeed));
                                        }
                                        gui.jProBar.addValue(3);
                                    } else {
                                        player[0].resize(-1 * (player[0].getR() - ORIGNALR));
                                    }
                                    enemies[i] = null;
                                } else {
                                	gameplaying = false;
                                	break;
                                }
                            }
                        }
                    }
                }
                //娓告垙缁撴潫锛屾敹灏�
                for (int i = 0; i < enemies.length; i++) {
                    enemies[i] = null;
                }

                if(score > historyScore) {
                	historyScore = score;
                }
                
                player[0] = null;
                gui.jf.getContentPane().setBackground(Color.RED);
                //gui.jf.getContentPane().repaint();
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                backMain(gui);
                System.out.println(score);
            }
        }
        
        class progressUI implements Runnable {
            public synchronized void run() {
            	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            	while(gui.jProBar.getValue() > 0 && gameplaying) {
            		gui.jProBar.addValue(-1);
            		try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
            	}
            	gameplaying = false;
            }
        }

        playerMovingCircle pmc = new playerMovingCircle();
        Thread playerMC = new Thread(pmc);
        enemyMoving em = new enemyMoving();
        Thread eM = new Thread(em);
        countScore cs = new countScore();
        Thread cS = new Thread(cs);
        progressUI pUI = new progressUI();
        Thread tProgress = new Thread(pUI);
        System.out.println("杩涚▼瀹氫箟瀹屾瘯");
        playerMC.start();
        eM.start();
        cS.start();
        tProgress.start();
        System.out.println("涓荤嚎绋媟unning");
       
//        gui.jf.setContentPane(oriView);
    }




    public static void main(String[] args) {
        final Game mian = new Game();
        final GUI gui = new GUI();
        System.out.println("gui鍒涘缓瀹屾瘯");
        gameplaying = false;
        initColor = gui.jf.getContentPane().getBackground();
        
        historyScore = MyUtils.readRecordFromFile("./res/record.txt");
        
        new Circle(240, 350, 80, Game.CIRCLECOUNT+1, "#00CED1",gui, 0, 0);
        new Circle(350, 380, 40, Game.CIRCLECOUNT+2, "#ADFF2F",gui, 0, 0);

        gui.jf.getContentPane().repaint();
        

        gui.start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.start.setVisible(false);
                gui.exit.setVisible(false);
                gui.eatYourBalls.setVisible(false);
                gui.currentScoreLabel.setVisible(true);
                gui.maxScoreLabel.setVisible(true);
                gui.gameLevelLabel.setVisible(true);
                score = 0;
                enemyMovingSpeed = 100;
                gui.currentScoreLabel.setText("当前成绩：" + score);
                gui.maxScoreLabel.setText(    "历史最高：" + historyScore);
                gui.gameLevelLabel.setText(   "难度等级：" + (100-enemyMovingSpeed));
                gui.clear();
                gui.jProBar.getjProgressBar().setValue(100);
                gui.jProBar.getjProgressBar().setVisible(true);
                gui.jf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                try {
                    mian.startGame(gui);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        gui.jf.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		MyUtils.writeRecordInFile("./res/record.txt", historyScore);
             }

        });

        gui.exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	MyUtils.writeRecordInFile("./res/record.txt", historyScore);
                System.exit(0);
            }
        });
    }

}

