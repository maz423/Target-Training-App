package com.example.assignment4;

import java.util.*;

public class BlobModel {
//    public boolean pathComplete;
    private List<BlobModelListener> subscribers;
    private List<Blob> blobs;

    private  InteractionModel iModel;

    private Stack<Command> Undo;
    private Stack<Command> Redo;

    private List<Blob> myClipboard;

    public int index = 0;

   List<Long> times;

   List<TrialRecord> trails;




//    List<Point2D> points; // points for the user path

    public BlobModel() {
        subscribers = new ArrayList<>();
        blobs = new ArrayList<>();
        Undo = new Stack<Command>();
        Redo = new Stack<Command>();
        myClipboard = new ArrayList<>();
        times = new ArrayList<>();
        trails = new ArrayList<>();


    }

    public Blob addBlob(double x, double y) {
        int n = blobs.size() + 1;
        Blob blob = new Blob(x,y,n);
        blobs.add(blob);
        notifySubscribers();
        return blob;

    }

    public void add_Blob(Blob b){
        blobs.add(b);
        notifySubscribers();
    }

    public void moveBlob(List<Blob> b, double dx, double dy) {
        b.forEach(e -> {
            e.move(dx,dy);
        });

        notifySubscribers();
    }

    public void UnMoveBlob(List<Blob> b, double dx, double dy){
        b.forEach(e -> {
            e.unmove(dx,dy);
        });

        notifySubscribers();
    }
    public void addSubscriber(BlobModelListener sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.modelChanged());
    }

    public List<Blob> getBlobs() {
        return blobs;
    }

    public boolean hitBlob(double x, double y) {
        for (Blob b : blobs) {
            if (b.contains(x,y)) return true;
        }
        return false;
    }

    public Blob whichHit(double x, double y) {
        for (Blob b : blobs) {
            if (b.contains(x,y)) return b;
        }
        return null;
    }

    public void changeSize(List<Blob> selected, double dX) {

        selected.forEach( s -> blobs.forEach(b -> {

            if(b == s ){
                if((b.r + dX) <= 5){
                    if(dX > 0){
                        b.r += dX;
                    }
                }
                else{
                    b.r += dX;
                }

            }

        }));

        notifySubscribers();
    }

    public void UnchangeSize(List<Blob> selected, double dX){
        selected.forEach( s -> blobs.forEach(b -> {

            if(b == s ){
                b.r -= dX;

            }

        }));

        notifySubscribers();

    }

    public void deleteBlob(List<Blob> selected) {
        System.out.println("delete");
       for(int s = 0; s< selected.size(); s++){
           for(int b = 0; b< blobs.size(); b++){
               if(blobs.get(b) == selected.get(s)){
                   blobs.remove(b);
               }
           }


       }
       selected.clear();
       notifySubscribers();

    }

//

    public void setIModel(InteractionModel im){
        iModel = im;
    }

    public void delete(Blob myBlob) {
        for(int b = 0 ; b< blobs.size(); b++){
            if (blobs.get(b) == myBlob){
                blobs.remove(b);
            }

        }

        notifySubscribers();
    }

    public void AddToUndo(Command command){
        if(Redo.empty()){
            Undo.push(command);
        }
        else{
            Redo.clear();
            Undo.push(command);
        }
        command.doIt();


    }


    public void AddToRedo(){
        if(!Undo.empty()){
           Command cmd = Undo.pop();
           Redo.push(cmd);
           cmd.undo();
        }

    }

    public void addToUndoAfterRedo(){
        if(!Redo.empty()){
            Command cmd = Redo.pop();
            Undo.push(cmd);
            cmd.doIt();

        }

    }

    public void CommandDeletion(List<Blob> myBlob) {
        myBlob.forEach( b -> {
            for(int i = 0; i<blobs.size();i++){
                if(b == blobs.get(i)){
                    blobs.remove(i);

                }
            }

        });
        iModel.selected.clear();
        notifySubscribers();
    }

    public void copyBlobs(List<Blob> b){
        myClipboard.clear();
        myClipboard = b;




    }

    public void cutBlobs(List<Blob> b , List<Blob> temp){
        myClipboard.clear();
        myClipboard = b;
        this.deleteBlob(temp);




    }



    public void clearCopy(){
        myClipboard.clear();




    }

    public void incrIndex() {
//        if(index < blobs.size() -1){
        index += 1;
        notifySubscribers();
//        }
    }

    public void startTime() {
        times.add(System.currentTimeMillis());
        if(times.size() == 2){
            trails.add(new TrialRecord(index-1,(times.get(1) - times.get(0))));


            times.remove(0);

        }
    }


    public void calcID(){
        int cur = 0;


        for(int i = 1; i< blobs.size(); i++){

            Blob current = blobs.get(cur);
            Blob next = blobs.get(i);
            double d = current.dist(current.x-current.r,current.y- current.r,next.x-next.r,next.y-next.r);
            double w = next.r*2;
            double ID = log2(2*d/w);
            trails.get(cur).ID = ID;
            cur += 1;





        }



    }
    public double log2(double n){
        return  Math.log(n)/Math.log(2);
    }

    public List<Blob> getMyClipboard(){
        return myClipboard;
    }



    public void clearTraining() {
        index = 0;
        times.clear();
        trails.clear();
        notifySubscribers();
    }





}
