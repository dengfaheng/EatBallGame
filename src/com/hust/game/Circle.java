package com.hust.game;


import java.awt.*;
import java.util.Random;

public class Circle {
    protected int radius,x,y,dx,dy;
    private int id;
    protected GUI gui;
    String color;
    public Circle(int X,int Y,int R,int id,String color,GUI gui,int dx,int dy){
        x = X;
        y = Y;
        radius = R;
        Random random = new Random();
        this.dx = random.nextBoolean() ? dx : -dx;
        this.dy = random.nextBoolean() ? dy : -dy;
        this.gui = gui;
        this.id = id;
        this.color = color;
        draw();
    }
    public Circle(int X,int Y,int R,int id,String color,GUI gui){
        x = X;
        y = Y;
        radius = R;
        this.gui = gui;
        this.id = id;
        this.color = color;
    }
    public void draw(){
        gui.updateCircle(this);
    }
    public void move(){
//        clearcircle()
        x += dx;
        y += dy;
        if(x + radius > gui.graphWidth || x - radius < 0){
            dx = -dx;
        }
        if(y + radius > gui.graphHeight || y - radius < GUI.PROGRESSWIDTH){
            dy = -dy;
        }
        draw();
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getR(){
        return radius;
    }
    public int getD(){
        return radius << 1;
    }
    public int getID(){
        return id;
    }

}