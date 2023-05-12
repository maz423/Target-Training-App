package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class DeleteBlob implements Command{
    List<Blob> myBlob;
    BlobModel model;

    List<Blob> TempBlob;

    InteractionModel iModel;

    public DeleteBlob(BlobModel m, List<Blob> b, InteractionModel im){
        TempBlob = b;
        myBlob = new ArrayList<>();
        b.forEach(blob -> {
            myBlob.add(blob.duplicateBlob());
        });

        model = m;
        iModel = im;

    }

    @Override
    public void doIt() {
        model.CommandDeletion(TempBlob);
    }

    @Override
    public void undo() {
        myBlob.forEach(b -> {
            model.add_Blob(b);
        });
        iModel.setSelectedList(myBlob);


    }
    public void duplicate(List<Blob> myBlob){


    }
}
