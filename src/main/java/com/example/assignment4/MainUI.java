package com.example.assignment4;

import javafx.scene.layout.StackPane;

import java.util.List;

public class MainUI extends StackPane implements AppModeListener {

    InteractionModel iModel;
    TargetView Training;
    BlobView view;

    BlobModel model;

    ReportView Report;

    public MainUI() {

         model = new BlobModel();
        Training = new TargetView();
        BlobController controller = new BlobController();
        view = new BlobView();
        iModel = new InteractionModel();
        TargetController tc = new TargetController();


        controller.setModel(model);
        view.setModel(model);
        controller.setIModel(iModel);
        view.setIModel(iModel);
        model.addSubscriber(view);
        iModel.addSubscriber(view);

        view.setController(controller);

        model.setIModel(iModel);
        iModel.setModel(model);

        iModel.setUI(this);

        Training.setController(tc);
        model.addSubscriber(Training);
        Training.setModel(model);
        Training.setiModel(iModel);
        tc.setModel(model);
        tc.setIModel(iModel);




        this.getChildren().add(view);
        this.setOnKeyPressed(controller::handleKeyPress);
        this.setOnKeyReleased(controller::handleKeyRelease);

    }

    @Override
    public void viewChanged() {
       if(iModel.currentMode == InteractionModel.mode.TEST) {
          this.getChildren().clear();
          this.getChildren().add(Training);
          this.getChildren().get(0).requestFocus();


       }
       else if(iModel.currentMode == InteractionModel.mode.EDIT){
           this.getChildren().clear();
           this.getChildren().add(view);
           this.getChildren().get(0).requestFocus();
       }

       else if(iModel.currentMode == InteractionModel.mode.REPORT){
           this.getChildren().clear();

           Report = new ReportView(model.trails);
           this.getChildren().add(Report);
           this.getChildren().get(0).requestFocus();

//           model.trails.forEach(t->{
//               System.out.println("time :" + t.time);
//               System.out.println("ID :" + t.ID);
//           });


       }
    }
}
