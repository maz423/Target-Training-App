package com.example.assignment4;

public class Blob {
    double x,y;
    double r;

    int num = 0;

    public Blob(double nx, double ny , int n) {
        x = nx;
        y = ny;
        r = 50;
        num = n;
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public void unmove(double dx, double dy){
        x -= dx;
        y -= dy;
    }

    public boolean contains(double cx, double cy) {
        return dist(cx,cy,x,y) <= r;
    }

    public double dist(double x1,double y1,double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
    }

    public Blob duplicateBlob(){
        Blob dup = new Blob(this.x,this.y,this.num);
        dup.r = this.r;
        return dup;

    }
}
