package com.example.assignment4;

import javafx.scene.input.MouseEvent;

import java.util.Timer;



public class TargetController {
    BlobModel model;
    InteractionModel iModel;

    Blob b = new Blob(0,0,0);

    int Trial = 0;


    public TargetController(){

    }

    public void setModel(BlobModel newModel) {
        model = newModel;
    }

    public void setIModel(InteractionModel newIModel) {
        iModel = newIModel;
    }

    public void handlePressed(MouseEvent event) {

       b = model.getBlobs().get(model.index);

        if (b.contains(event.getX(),event.getY())) { //on blob
            model.startTime();

            model.incrIndex();

            if(model.index == model.getBlobs().size()){
                model.calcID();
                iModel.setAppMode(InteractionModel.mode.REPORT);
                model.clearTraining();


            }



        }





        }

    public void handleReleased(MouseEvent event) {

    }
}
