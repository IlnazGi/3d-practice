package com.example.demo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {


    private double statusX;
    private double statusY;
    private double statusZ;


    @Override
    public void start(Stage stage) throws IOException {


        Box box = new Box(100,100,100);
        PhongMaterial blackMaterial = new PhongMaterial();
        blackMaterial.setDiffuseColor(Color.GREY);
        blackMaterial.setSpecularColor(Color.BLACK);
        box.setMaterial(blackMaterial);

        //Setting the slider for the horizontal translation
        Slider slider1 = new Slider(0, 200, 0);
        slider1.setOrientation(Orientation.HORIZONTAL);
        slider1.setShowTickLabels(true);
        slider1.setShowTickMarks(true);
        slider1.setMajorTickUnit(150);
        slider1.setBlockIncrement(150);
        //Creating the translation transformation
        Translate translate = new Translate();
        //Linking the transformation to the slider
        slider1.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<?extends Number> observable, Number oldValue, Number newValue){
                translate.setX((double) newValue);
                statusX = (double) newValue;
            }
        });

        box.setRotationAxis(Rotate.X_AXIS);
        box.setRotate(45);

        //Setting the slider for the vertical translation
        Slider slider2 = new Slider(0, 200, 0);
        slider2.setOrientation(Orientation.HORIZONTAL);
        slider2.setShowTickLabels(true);
        slider2.setShowTickMarks(true);
        slider2.setMajorTickUnit(50);
        slider2.setBlockIncrement(50);
        //Creating the translation transformation
        slider2.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                translate.setY((double) newValue);
                statusY = (double) newValue;
            }
        });


        Slider slider3 = new Slider(0, 200, 0);
        slider3.setOrientation(Orientation.HORIZONTAL);
        slider3.setShowTickLabels(true);
        slider3.setShowTickMarks(true);
        slider3.setMajorTickUnit(50);
        slider3.setBlockIncrement(50);
        //Creating the translation transformation
        slider3.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){

                translate.setZ((double) newValue);
                statusZ = (double) newValue;
            }
        });

        Slider slider4 = new Slider(0, 360, 0);
        slider4.setOrientation(Orientation.HORIZONTAL);
        slider4.setShowTickLabels(true);
        slider4.setShowTickMarks(true);
        slider4.setMajorTickUnit(90);
        slider4.setBlockIncrement(10);
        slider4.setOrientation(Orientation.VERTICAL);
        slider4.setLayoutX(2);
        slider4.setLayoutY(195);

        Rotate rotate = new Rotate();
        //Setting pivot points for the rotation
        rotate.setPivotX(300);
        rotate.setPivotY(100);
        //Adding the transformation to rectangle
        box.getTransforms().addAll(rotate);
        //Linking the transformation to the slider
        slider4.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                //Setting the angle for the rotation
                rotate.setAngle((double) newValue);
            }
        });
        //Adding the transformation to the circle
        box.getTransforms().add(rotate);

        //Creating the translation transformation
        box.getTransforms().add(translate);

        Label sign = new Label("X = " + toString() + statusX + "    Y = " + toString() + statusY + "    Z = " +
                toString() + statusZ);


        BorderPane pane = new BorderPane();
        pane.getChildren().add(sign);
        pane.setRight(new VBox(new Label("Движение по X"), slider1, new VBox(new Label("Движение по Y"), slider2),
                new VBox (new Label("Движение по Z"), slider3), new VBox(new Label("Повернуть"), slider4)));
        pane.setCenter(box);
        pane.setBottom(sign);
        Scene scene = new Scene(pane, 595, 300);
        stage.setTitle("Translate Example");


        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }


}