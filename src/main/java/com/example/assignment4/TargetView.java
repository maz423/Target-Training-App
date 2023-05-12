package com.example.assignment4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class TargetView extends StackPane implements BlobModelListener, IModelListener {

    GraphicsContext gc;
    Canvas myCanvas;
    BlobModel model;
    InteractionModel iModel;

    public TargetView(){
        myCanvas = new Canvas(800,800);
        gc = myCanvas.getGraphicsContext2D();


        this.setStyle("-fx-background-color: aqua");
        this.getChildren().add(myCanvas);

    }

    private void draw() {
        gc.clearRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());
        gc.setFill(Color.BEIGE);

        if(model.index < model.getBlobs().size()){
        Blob b = model.getBlobs().get(model.index);
        gc.fillOval(b.x-b.r,b.y-b.r,b.r*2,b.r*2);}


    }
    @Override
    public void modelChanged() {
    if(model.getBlobs() != null)   {
    draw();}
    }

    @Override
    public void iModelChanged() {

    }

    public void setController(TargetController controller) {
        myCanvas.setOnMousePressed(controller::handlePressed);
        myCanvas.setOnMouseReleased(controller::handleReleased);



    }

    public void setModel(BlobModel m) {
        model = m;
    }
    public void setiModel(InteractionModel im) {
        iModel = im;
    }
}
