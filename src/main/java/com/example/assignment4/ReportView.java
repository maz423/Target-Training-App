package com.example.assignment4;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ReportView extends StackPane {

    List<Double> x;

    List<Long> y;

    public ReportView(List<TrialRecord> T) {
       x = new ArrayList<>();
       y = new ArrayList<>();

       T.forEach( t->{
           x.add(t.ID);
           y.add(t.time);

       });

//        x.add(1.5);
//        x.add(2.5);
//        x.add(4.5);
//        x.add(5.5);
//        x.add(8.5);
//
//        y.add((long)0.5);
//        y.add((long)1.5);
//        y.add((long)3.5);
//        y.add((long)7.5);
//        y.add((long)8.5);


        //Defining the axes
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("ID");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("MT");

        //Creating the Scatter chart
        ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);

        Group root = new Group(scatterChart);

        //Prepare XYChart.Series objects by setting data
        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < x.size(); i++) {
            series.getData().add(new XYChart.Data(x.get(i), y.get(i)));

        }

        //Setting the data to scatter chart
        scatterChart.getData().addAll(series);

        this.getChildren().add(root);




    }




}

