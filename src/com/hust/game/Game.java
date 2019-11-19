package com.hust.game;

import javax.swing.*;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    	gui.jf.getContentPane().setBackground(Color.WHITE);
        gui.back.setVisible(false);
        gui.score.setVisible(true);
        gui.start.setVisible(true);
        gui.currentScoreLabel.setVisible(false);
        gui.jProBar.getjProgressBar().setVisible(false);
        gui.jf.getContentPane().repaint();
        gui.clear();

    }
    public static final int ORIGNALR = 15;
    public static final int CIRCLECOUNT = 50;
    public static final int MAX = 100;
    public static final int MIN = 10;
    public static volatile int score = 0;
    public static volatile boolean gameplaying;
    public static volatile boolean lookingscore;
    
    public static int enemyMovingSpeed = 100;
    
    public static Random random;
    //public static String[] mys = {"#00FF00", "#0000FF", "#00FFFF", "#FFFF00", "#FF00FF"};
    public static int cnt;
    class PLAY {
        public int cnt;
        public int score;

        public PLAY(int cnt, int score) {
            this.cnt = cnt;
            this.score = score;
        }
    }

    public static ArrayList<PLAY> s_i;

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
                    System.out.println("g px = "+player[0].getX() +" py = "+player[0].getY());
                }else {
                	do {
                        enemies[i] = new Circle(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                                - enermyR * 2-GUI.BOTTOM) + enermyR+GUI.PROGRESSWIDTH, enermyR, i, MyUtils.getRandomColor(random),
                                gui, random.nextInt(10) + 1, random.nextInt(10) + 1);
                        System.out.println("boom ID = " + i);
                    } while (boom(enemies[i], player[0]));
                	System.out.println("g px = "+player[0].getX() +" py = "+player[0].getY());
                }
            } else {
                int enermyR = random.nextInt(MAX - player[0].getR()) + player[0].getR();
                do {
                    enemies[i] = new Circle(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                            - enermyR * 2-GUI.BOTTOM) + enermyR+GUI.PROGRESSWIDTH, enermyR, i,  MyUtils.getRandomColor(random),
                            gui, random.nextInt(3) + 1, random.nextInt(3) + 1);
                } while (boom(enemies[i], player[0]));
                System.out.println("g px = "+player[0].getX() +" py = "+player[0].getY());
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
                                        if(score != 0 && score % 10 == 0 && enemyMovingSpeed > 20) {
                                        	enemyMovingSpeed -= 20;
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
                cnt++;
                if (s_i.size() > 10) {
                    if (score > s_i.get(9).score) {
                        s_i.set(9, new PLAY(cnt, score));
                    }
                } else {
                    s_i.add(new PLAY(cnt, score));
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

    public void printScores(GUI gui) throws IOException, InterruptedException {
        s_i.sort(new Comparator<PLAY>() {
            @Override
            public int compare(PLAY o1, PLAY o2) {
                if (o1.score > o2.score) {
                    return -1;
                } else if (o1.score == o2.score) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        for (int i = 0; i < s_i.size(); i++) {
            gui.noi[i].setText(s_i.get(i).cnt + " " + s_i.get(i).score);
            gui.noi[i].setVisible(true);
        }

        gui.back.setVisible(true);
        gui.back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < gui.noi.length; i++){
                    if(gui.noi[i] != null){
                        gui.noi[i].setVisible(false);
                    }
                }
                gui.back.setVisible(false);
                backMain(gui);
                lookingscore = false;
            }
        });
    }


    public static void main(String[] args) {
        final Game mian = new Game();
        final GUI gui = new GUI();
        System.out.println("gui鍒涘缓瀹屾瘯");
        s_i = new ArrayList<PLAY>();
        gameplaying = false;

        gui.jf.getContentPane().repaint();
        //gui.jf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        gui.jf.setBackground(Color.gray);


        gui.start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.start.setVisible(false);
                gui.score.setVisible(false);
                gui.currentScoreLabel.setVisible(true);
                gui.maxScoreLabel.setVisible(true);
                gui.gameLevelLabel.setVisible(true);
                score = 0;
                enemyMovingSpeed = 100;
                gui.currentScoreLabel.setText("当前成绩：" + score);
                gui.maxScoreLabel.setText(    "历史最高：" + 100);
                gui.gameLevelLabel.setText(   "难度等级：" + (100-enemyMovingSpeed));
                gui.clear();
                gui.jProBar.getjProgressBar().setValue(100);
                gui.jProBar.getjProgressBar().setVisible(true);
                
                try {
                    mian.startGame(gui);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }


            }
        });

        gui.score.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.start.setVisible(false);
                gui.score.setVisible(false);
                lookingscore = true;
                try {
                    mian.printScores(gui);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        System.out.println("涓荤嚎绋嬮兘宸茬粡鍒拌繖鍎夸簡");
    }

}

