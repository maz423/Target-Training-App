package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class MoveBlob implements Command{
    List<Blob> MyBlob;
    BlobModel model;


    double dx,dy;

    public MoveBlob(BlobModel m, List<Blob> b, double ndx, double ndy){

        MyBlob = new ArrayList<>();
        MyBlob.addAll(b);
        dx = ndx;
        dy = ndy;
        model = m;

    }

    @Override
    public void doIt() {
        model.moveBlob(MyBlob,dx,dy);


    }

    @Override
    public void undo() {
        model.UnMoveBlob(MyBlob,dx,dy);
    }
}
