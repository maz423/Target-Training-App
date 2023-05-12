package com.example.assignment4;

public class RectSelect {

    double x;
    double y;

    double Tdx = 0;
    double Tdy = 0;



    double Height;

    double Width;
    public RectSelect(double nx,double ny,double w,double h){
        x = nx;
        y = ny;
        Width = w;
        Height = h;
    }


    public boolean contains(double cx, double cy) {


        double nx = x ;
        double  ny = y;

        double left = nx;
        double right = nx + Width;
        double top = ny;
        double bottom =  ny + Height;


        if(cx > left && cx < right && cy > top && cy < bottom ){
            return true;
        }
        else{
            return false;
        }

    }

}
