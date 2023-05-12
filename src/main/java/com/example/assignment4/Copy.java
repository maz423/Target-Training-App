package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class Copy implements Command{

    List<Blob> myBlob;

    BlobModel model;

    public Copy(List<Blob> b, BlobModel m ){
        myBlob = new ArrayList<>();
        b.forEach(blob -> {
            myBlob.add(blob.duplicateBlob());
        });

        model = m;
    }
    @Override
    public void doIt() {
      model.copyBlobs(myBlob);
    }

    @Override
    public void undo() {
      model.clearCopy();
    }
}
