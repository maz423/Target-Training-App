package com.example.assignment4;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class InteractionModel {
    List<IModelListener> subscribers;
    List<Blob> selected;

    List<Point2D> points; // points for the user path

    PixelReader reader; // for checking the offscreen bitmap's colours

    RectSelect rectSelect;

    Timer timer;




    enum mode {EDIT, TEST, REPORT};

    mode currentMode;

    MainUI UI;

    List<TrialRecord> Trials;



    private BlobModel model;

    public boolean pathComplete;

    public InteractionModel() {
        subscribers = new ArrayList<>();
        selected = new ArrayList<>();
        points = new ArrayList<>();
        rectSelect = new RectSelect(0,0,0,0);
        Trials = new ArrayList<>();
        timer = new Timer();
    }

    public void addSubscriber(IModelListener sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.iModelChanged());

    }

    private void notifyUI(){
        UI.viewChanged();
    }

    public void setSelected(Blob b) {
        selected.clear();
        selected.add(b);
        notifySubscribers();
    }

    public void setSelectedList(List<Blob> l) {
        selected.clear();
        selected.addAll(l);
        notifySubscribers();


    }

    public void unselect() {
        selected.clear();
    }

    public List<Blob> getSelected() {

        return selected;
    }

    public void setMultipleSelect(Blob b) {
        boolean found = false;
        int index = 0;
        for(int i = 0; i < selected.size(); i++){
            if(b == selected.get(i)){
             found = true;
             index = i;
            }
        }
        if(found == true){
            selected.remove(index);
        }
        else{
            selected.add(b);
        }

        notifySubscribers();
    }

    public void setModel(BlobModel m){
        model = m;
    }

    public void addPtsInit(double x , double y){

        points.clear();
        pathComplete = false;
        points.add(new Point2D(x, y));
        notifySubscribers();

    }

    public void addPtsOnDrag(double x, double y) {
        points.add(new Point2D(x, y));
        notifySubscribers();
    }

    public void pathCompleted() {
        pathComplete = true;
        List<Blob> lasso = OffscreenCanvas();
        List<Blob> rect = updateRectSelection();
        if(lasso.size() > rect.size()){
            selected.addAll(lasso);
        }
        else if (lasso.size() < rect.size()){
            selected.addAll(rect);
        }
        else {
            selected.addAll(lasso);
        }
        notifySubscribers();
    }

    public void wipe() {
        selected.clear();
        points.clear();
        clearRect();


        pathComplete = false;
        notifySubscribers();
    }

    public List<Blob> OffscreenCanvas(){
        Canvas checkCanvas = new Canvas(800, 800);
        GraphicsContext checkGC = checkCanvas.getGraphicsContext2D();
        checkGC.setFill(Color.RED);
        checkGC.beginPath();
        checkGC.moveTo(points.get(0).getX(),points.get(0).getY());
        points.forEach(p -> checkGC.lineTo(p.getX(),p.getY()));
        checkGC.closePath();
        checkGC.fill();
        WritableImage buffer = checkCanvas.snapshot(null, null);
        reader = buffer.getPixelReader();
        List<Blob> blobs = model.getBlobs();
        List<Blob> lasso = new ArrayList<>();

        selected.clear();
        blobs.forEach(b -> {
          if(reader.getColor((int) (b.x-b.r), (int) (b.y-b.r)).equals(Color.RED)){

              lasso.add(b);

          }
        });
       return lasso;

    }

    public void createRect(double x, double y, int w, int h) {
        rectSelect.x = x;
        rectSelect.y = y;
        rectSelect.Width = w;
        rectSelect.Height = h;
        rectSelect.Tdx = 0;
        rectSelect.Tdy = 0;
        notifySubscribers();
    }

    public void extendRect(double dX, double dY) {
        rectSelect.Tdx += dX;
        rectSelect.Tdy += dY;

        if(rectSelect.Tdx >= 0){
            rectSelect.Width += dX;
        }
        if(rectSelect.Tdy >= 0){
            rectSelect.Height +=dY;
        }
        if(rectSelect.Tdx <= 0){
            rectSelect.x += dX;
            rectSelect.Width -= dX;

        }
        if(rectSelect.Tdy <= 0){
            rectSelect.y += dY;
            rectSelect.Height -= dY;

        }

        notifySubscribers();
    }

    public List<Blob> updateRectSelection(){
       List<Blob> blobs = model.getBlobs();
       List<Blob> rect = new ArrayList<>();
       blobs.forEach(b -> {
           if(rectSelect.contains(b.x-b.r,b.y-b.r)){
               rect.add(b);

           }
       });

       clearRect();

       return rect;

    }

    public void clearRect(){
        rectSelect.x = 0;
        rectSelect.y = 0;
        rectSelect.Height = 0;
        rectSelect.Width = 0;
    }

    public void setUI(MainUI main){
        UI = main;
    }

    public void setAppMode(mode m){
        currentMode = m;
        notifyUI();

    }

    public void addTrail(int index, long time) {
        TrialRecord t = new TrialRecord(index,time);
        Trials.add(t);
    }

    public void ResetLassoRect(){
        points.clear();
        clearRect();
        notifySubscribers();

    }






}

