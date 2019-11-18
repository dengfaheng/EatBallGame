package com.hust.game;

public class PlayerCircle extends Circle{
    private int Max;
    public PlayerCircle(int X,int Y,int R,int id,String color,GUI gui,int Maxsize){
        super(X,Y,R,id,color,gui);
        Max = Maxsize;
    }
    public void resize(int plusSize){
        if(radius < Max){
            radius += plusSize;
            draw();
        }
    }
    public void move(){
//        clearcircle
        x = gui.mouseX;
        y = gui.mouseY;
        draw();
    }
    public int getMax(){
        return Max;
    }
}
