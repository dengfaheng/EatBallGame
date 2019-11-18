package com.hust.game;

import javax.swing.*;

import java.awt.Color;
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
        gui.back.setVisible(false);
        gui.score.setVisible(true);
        gui.start.setVisible(true);
        gui.scoreLabel.setVisible(false);
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
    public static Random random;
    public static String[] mys = {"#00FF00", "#0000FF", "#00FFFF", "#FFFF00", "#FF00FF"};
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

        System.out.println("鐜板湪鏄湪playView");
//        GUI.paintPanel oriView = (GUI.paintPanel) gui.jf.getContentPane();

        final PlayerCircle[] player = {new PlayerCircle(500, 500, ORIGNALR, CIRCLECOUNT, "#000000", gui, MAX)};
        final Circle[] enemies = new Circle[CIRCLECOUNT];
        score = 0;
        gameplaying = true;
        random = new Random();

        for (int i = 0; i < CIRCLECOUNT; i++) {
            if (i < CIRCLECOUNT / 4 * 3) {
                int enermyR = random.nextInt(player[0].getR()) + MIN;
                do {
                    enemies[i] = new Circle(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                            - enermyR * 2) + enermyR, enermyR, i, MyUtils.getRandomColor(random),
                            gui, random.nextInt(10) + 1, random.nextInt(10) + 1);
                    System.out.println("boom ID = " + i);
                } while (boom(enemies[i], player[0]));
                if (i == CIRCLECOUNT / 2) {
                    do {
                        enemies[i] = new Circle(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                                - enermyR * 2) + enermyR, enermyR, i, "#FF00FF",
                                gui, random.nextInt(10) + 1, random.nextInt(10) + 1);
                        System.out.println("boom ID = " + i);
                    } while (boom(enemies[i], player[0]));
                }
            } else {
                int enermyR = random.nextInt(MAX - player[0].getR()) + player[0].getR();

                do {
                    enemies[i] = new Circle(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                            - enermyR * 2) + enermyR, enermyR, i,  MyUtils.getRandomColor(random),
                            gui, random.nextInt(3) + 1, random.nextInt(3) + 1);
                } while (boom(enemies[i], player[0]));
            }

        }
        gui.jf.getContentPane().repaint();

        System.out.println("start and score has already unvisible");

        class playerMovingCircle implements Runnable {
            @Override
            public synchronized void run() {
                System.out.println("player moving");
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
					// TODO Auto-generated catch block
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
                                        - enermyR * 2) + enermyR, enermyR, i, MyUtils.getRandomColor(random),
                                        gui, random.nextInt(3) + 1, random.nextInt(3) + 1);
                            } while (boom(enemies[i], player[0]));
                            if (i == CIRCLECOUNT / 2) {
                                do {
                                    enemies[i] = new Circle(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                                            - enermyR * 2) + enermyR, enermyR, i, "#FF00FF",
                                            gui, random.nextInt(10) + 1, random.nextInt(10) + 1);
                                    System.out.println("boom ID = " + i);
                                } while (boom(enemies[i], player[0]));
                            }
                        }
                        enemies[i].move();
                    }
                    gui.printAllEnemiesCircles(); //鏇存柊鎵�鏈� 
                    try {
                        Thread.sleep(100);
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
                                if (player[0].getR() > enemies[i].getR()) {
                                    if (i != CIRCLECOUNT / 2) {
                                        player[0].resize(1);
                                        score++;
                                    } else {
                                        player[0].resize(-1 * (player[0].getR() - ORIGNALR));
                                    }
                                    gui.scoreLabel.setText("score = " + score);
                                    enemies[i] = null;
                                } else {
                                    gameplaying = false;
                                }

                                break;
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

                backMain(gui);
                System.out.println(score);
            }
        }

        playerMovingCircle pmc = new playerMovingCircle();
        Thread playerMC = new Thread(pmc);
        enemyMoving em = new enemyMoving();
        Thread eM = new Thread(em);
        countScore cs = new countScore();
        Thread cS = new Thread(cs);
        System.out.println("杩涚▼瀹氫箟瀹屾瘯");
        playerMC.start();
        eM.start();
        cS.start();
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
        gui.jf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        gui.jf.setBackground(Color.white);


        gui.start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.start.setVisible(false);
                gui.score.setVisible(false);
                gui.scoreLabel.setBounds(50,25,100,10);
                gui.scoreLabel.setVisible(true);
                score = 0;
                gui.scoreLabel.setText("score = " + score);
                gui.clear();
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

