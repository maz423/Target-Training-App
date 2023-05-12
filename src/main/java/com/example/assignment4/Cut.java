package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class Cut implements Command{

    List<Blob> myBlob;

    BlobModel model;

    List<Blob> temp;

    InteractionModel iModel;

    public Cut(List<Blob> b, BlobModel m, InteractionModel im){
        temp = b;
        myBlob = new ArrayList<>();
        b.forEach(blob -> {
            myBlob.add(blob.duplicateBlob());
        });

        model = m;
        iModel = im;
    }
    @Override
    public void doIt() {
        model.cutBlobs(myBlob,temp);
    }

    @Override
    public void undo() {

        for(int i = 0; i < myBlob.size(); i++){
            model.add_Blob(myBlob.get(i));



        }
        iModel.setSelectedList(myBlob);
        model.clearCopy();

//        myBlob.forEach(b ->{
//            model.add_Blob(b);
//            model.clearCopy();
//        });
    }
}

