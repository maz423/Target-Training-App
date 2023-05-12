package com.example.assignment4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class BlobView extends StackPane implements BlobModelListener, IModelListener {
    GraphicsContext gc;
    Canvas myCanvas;
    BlobModel model;
    InteractionModel iModel;

    public BlobView() {

        myCanvas = new Canvas(800,800);
        gc = myCanvas.getGraphicsContext2D();





        this.getChildren().add(myCanvas);
        this.setStyle("-fx-background-color: aqua");
    }

    private void draw() {
        gc.clearRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());
        model.getBlobs().forEach(b -> {
            gc.setFill(Color.BEIGE);
            iModel.getSelected().forEach(e -> {
                if (b == e ){
                gc.setFill(Color.TOMATO);
            }
            });

            gc.fillOval(b.x-b.r,b.y-b.r,b.r*2,b.r*2);
            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(b.num),b.x,b.y);
        });
        if(iModel.points.size() > 0){
            drawLasso();
        }
        drawRect();

    }

    public void drawLasso(){
        if (!iModel.pathComplete) {
            gc.setFill(Color.DARKGRAY);
            iModel.points.forEach(p -> gc.fillOval(p.getX()-3,p.getY()-3,6,6));
        } else {
            gc.setFill(Color.ORANGE);
            gc.beginPath();
            gc.moveTo(iModel.points.get(0).getX(),iModel.points.get(0).getY());
            iModel.points.forEach(p -> gc.lineTo(p.getX(),p.getY()));
            gc.closePath();

        }
    }

    public void drawRect(){
        gc.setStroke(Color.RED);
        gc.strokeRect(iModel.rectSelect.x,iModel.rectSelect.y,iModel.rectSelect.Width,iModel.rectSelect.Height);
    }

    public void setModel(BlobModel newModel) {
        model = newModel;
    }

    public void setIModel(InteractionModel newIModel) {
        iModel = newIModel;
    }

    @Override
    public void modelChanged() {
        draw();

    }

    @Override
    public void iModelChanged() {
        draw();
    }

    public void setController(BlobController controller) {
        myCanvas.setOnMousePressed(controller::handlePressed);
        myCanvas.setOnMouseDragged(controller::handleDragged);
        myCanvas.setOnMouseReleased(controller::handleReleased);


    }
}
