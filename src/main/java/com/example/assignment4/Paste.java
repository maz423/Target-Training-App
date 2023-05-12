package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class Paste implements Command{

    List<Blob> myBlob;

    BlobModel model;

    InteractionModel iModel;

    public Paste( List<Blob> b, BlobModel m, InteractionModel im){
        myBlob = new ArrayList<>();
        b.forEach(blob -> {
            myBlob.add(blob.duplicateBlob());
        });

        model = m;
        iModel = im;


    }
    @Override
    public void doIt() {
      myBlob.forEach(b -> {

          model.add_Blob(b);


      });
        iModel.setSelectedList(myBlob);
    }

    @Override
    public void undo() {
        model.deleteBlob(myBlob);

    }
}
