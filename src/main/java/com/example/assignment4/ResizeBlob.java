package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class ResizeBlob implements Command {

    List<Blob> MyBlob;
    BlobModel model;
    double dx;


    public ResizeBlob( List<Blob> b, BlobModel m, double ndx){

        MyBlob = new ArrayList<>();
        MyBlob.addAll(b);
        dx = ndx;
        model = m;

    }

    @Override
    public void doIt() {
        model.changeSize(MyBlob,dx);

    }

    @Override
    public void undo() {
     model.UnchangeSize(MyBlob,dx);
    }
}
