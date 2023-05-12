package com.example.assignment4;

public class AddBlob implements Command{
    Blob myBlob;
    BlobModel model;
    double x,y;

    public AddBlob(BlobModel b,double nx,double ny){
        x = nx;
        y = ny;
        model = b;
        myBlob = null;

    }

    @Override
    public void doIt() {
        if (myBlob == null) {
            myBlob = model.addBlob(x,y);
        } else {
            model.add_Blob(myBlob);
        }

    }

    @Override
    public void undo() {
           model.delete(myBlob);
    }

}
