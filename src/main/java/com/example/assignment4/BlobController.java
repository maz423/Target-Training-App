package com.example.assignment4;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.security.Key;
import java.util.List;


import static javafx.scene.input.KeyCode.*;

public class BlobController {
    BlobModel model;
    InteractionModel iModel;
    double prevX,prevY;
    double dX,dY;

    public void handlePressedTarget(MouseEvent mouseEvent) {
    }


    enum State {READY,PREPARE_CREATE, DRAGGING}
    enum Keys {Ctrl,Shift, None}
    State currentState = State.READY;
    Keys currentKey = Keys.None;

    public BlobController() {

    }

    public void setModel(BlobModel newModel) {
        model = newModel;
    }

    public void setIModel(InteractionModel newIModel) {
        iModel = newIModel;
    }

    public void handlePressed(MouseEvent event) {
        switch (currentState) {
            case READY -> {
                if (model.hitBlob(event.getX(),event.getY())) { //if clicked on blob
                    Blob b = model.whichHit(event.getX(),event.getY());
                    if(currentKey == Keys.Ctrl){
                        iModel.setMultipleSelect(b);
                    }
                    else if (currentKey != Keys.Shift){
                        if(!isSelectedGroup(b)){
                            iModel.setSelected(b);
                        }
                    }
                    prevX = event.getX();
                    prevY = event.getY();
                    currentState = State.DRAGGING;
                } else { //clicked on background

                    prevX = event.getX();
                    prevY = event.getY();
                    currentState = State.PREPARE_CREATE;
                    if(currentKey == Keys.Ctrl){
                        iModel.clearRect();
                        iModel.createRect(event.getX(),event.getY(),0,0);
                        iModel.addPtsInit(event.getX(),event.getY());
                    }
                    else{

                        iModel.wipe();

                    }

                }
            }
        }
    }

    private boolean isSelectedGroup(Blob b) {
        boolean found = false;
        List<Blob> s = iModel.getSelected();
        for(int i = 0; i< s.size();i++){
            if(b == s.get(i)){
            found = true;}

        }
        return found;
    }

    public void handleDragged(MouseEvent event) {

        switch (currentState) {
            case PREPARE_CREATE -> {

                if(currentKey == Keys.Ctrl){
                    dX = event.getX() - prevX;
                    dY = event.getY() - prevY;
                    prevX = event.getX();
                    prevY = event.getY();

                    iModel.addPtsOnDrag(event.getX(),event.getY());
                    iModel.extendRect(dX,dY);
                }
//                currentState = State.READY;

            }
            case DRAGGING -> {
                if(currentKey == Keys.Shift){
                    dX = event.getX() - prevX;
                    dY = event.getY() - prevY;
                    prevX = event.getX();
                    prevY = event.getY();
                    Command cmd = new ResizeBlob(iModel.getSelected(),model,dX);
                    model.AddToUndo(cmd);

//                    model.changeSize(iModel.getSelected(), dX);
                }
                else{
                dX = event.getX() - prevX;
                dY = event.getY() - prevY;
                prevX = event.getX();
                prevY = event.getY();
                Command cmd = new MoveBlob(model,iModel.getSelected(),dX,dY);
                model.AddToUndo(cmd);

//                model.moveBlob(iModel.getSelected(), dX,dY);
                }
            }
        }
    }

    public void handleReleased(MouseEvent event) {
        switch (currentState) {
            case PREPARE_CREATE -> {

                if(currentKey == Keys.Shift){
                    AddBlob command = new AddBlob(model,event.getX(), event.getY());
                    model.AddToUndo(command);
                    iModel.setSelected(command.myBlob);

//                    model.addBlob(event.getX(),event.getY());
                currentState = State.READY;}
                else if(currentKey == Keys.Ctrl){
                    iModel.pathCompleted();
                    currentState = State.READY;
                }
                else{
                    iModel.ResetLassoRect();
                    currentState = State.READY;}
            }
            case DRAGGING -> {
//                iModel.unselect();
                currentState = State.READY;
            }
        }
    }


    public void handleKeyPress(KeyEvent keyEvent) {

      if(keyEvent.isControlDown() && keyEvent.getCode() == Z){
          model.AddToRedo();
      }
      if(keyEvent.isControlDown() && keyEvent.getCode() == R){
          model.addToUndoAfterRedo();
        }

        if(keyEvent.isControlDown() && keyEvent.getCode() == C){
            Command cmd = new Copy(iModel.getSelected(),model);
            model.AddToUndo(cmd);
        }

        if(keyEvent.isControlDown() && keyEvent.getCode() == X){
            Command cmd = new Cut(iModel.getSelected(),model, iModel);
            model.AddToUndo(cmd);
        }

        if(keyEvent.isControlDown() && keyEvent.getCode() == V){
            Command cmd = new Paste(model.getMyClipboard(),model,iModel);
            model.AddToUndo(cmd);
        }

        if(keyEvent.isControlDown() && keyEvent.getCode() == T){
           iModel.setAppMode(InteractionModel.mode.TEST);

        }
        if(keyEvent.isControlDown() && keyEvent.getCode() == E){
            iModel.setAppMode(InteractionModel.mode.EDIT);
            model.clearTraining();
        }



        switch(keyEvent.getCode()) {

            case SHIFT -> currentKey = Keys.Shift;
            case CONTROL -> currentKey = Keys.Ctrl;


            case DELETE -> handleDelete();
        }
    }

    private void handleDelete() {
        Command delete = new DeleteBlob(model,iModel.getSelected(),iModel);
        model.AddToUndo(delete);
    }

    public void handleKeyRelease(KeyEvent key) {
        currentKey = Keys.None;

    }


}
