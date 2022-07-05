package com.example.demo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private PerspectiveCamera camera;
    private final double cameraModifier = 50.0;
    private final double cameraQuantity = 10.0;
    private final double sceneWidth = 600;
    private final double sceneHeight = 600;
    private double mouseXold = 0;
    private double mouseYold = 0;
    private final double cameraYlimit = 15;
    private final double rotateModifier = 25;


    @Override
    public void start(Stage stage) throws IOException {

        Line line = new Line(1000,1000,1000, 500);


        Camera camera = new PerspectiveCamera(true);
        camera.setFarClip(5000);
        camera.setTranslateZ(-1000);

        Label label1 = new Label("Перемещение по координате X:");
        Slider slider1 = new Slider();
        Label infoLabel = new Label("-");

        slider1.setMin(-100);
        slider1.setMax(100);
        slider1.setValue(0);

        slider1.setShowTickLabels(true);
        slider1.setShowTickMarks(true);

        slider1.setBlockIncrement(10);

        slider1.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                infoLabel.setText("New value:" + newValue);
            }
        });

        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);
        root.getChildren().addAll(label1, slider1, infoLabel);

        stage.setTitle("JavaFX Slider");
        Scene scene1 = new Scene(root, 350, 200);
        stage.setScene(scene1);
        stage.show();

        Box box = new Box(100,100,100);
        PhongMaterial blackMaterial = new PhongMaterial();
        blackMaterial.setDiffuseColor(Color.GREY);
        blackMaterial.setSpecularColor(Color.BLACK);
        box.setMaterial(blackMaterial);

        box.setRotationAxis(Rotate.X_AXIS);
        box.setRotate(45);

        Group group = new Group();
        group.getChildren().addAll(box);
        group.getChildren().addAll(line);

        Scene scene = new Scene(group, 1000, 700);
        scene.setCamera(camera);

        scene.setOnMouseClicked(event -> {
            Node picked = event.getPickResult().getIntersectedNode();
            if (null != picked) {
                double scalar = 2;
                if(picked.getScaleX() > 1)
                    scalar = 1;
                picked.setScaleX(scalar);
                picked.setScaleY(scalar);
                picked.setScaleZ(scalar);
            }
        });

        scene.setOnKeyPressed(event -> {
            double change = cameraQuantity;
            if (event.isShiftDown()) {
                change = cameraModifier;
            }
            KeyCode keycode = event.getCode();
            if (keycode == KeyCode.W) {
                camera.setTranslateZ(camera.getTranslateZ() + change);
            }
            if (keycode == KeyCode.S) {
                camera.setTranslateZ(camera.getTranslateZ() - change);
            }
            if (keycode == KeyCode.A) {
                camera.setTranslateX(camera.getTranslateX() - change);
            }
            if (keycode == KeyCode.D) {
                camera.setTranslateX(camera.getTranslateX() + change);
            }
        });
        Rotate xRotate = new Rotate(0,0,0,0, Rotate.X_AXIS);
        Rotate yRotate = new Rotate(0,0,0,0, Rotate.Y_AXIS);
        camera.getTransforms().addAll(xRotate,yRotate);
        scene.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED ||
                    event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                double mouseXnew = event.getSceneX();
                double mouseYnew = event.getSceneY();
                if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    double pitchRotate = xRotate.getAngle() + (mouseYnew - mouseYold) / rotateModifier;
                    pitchRotate = pitchRotate > cameraYlimit ? cameraYlimit : pitchRotate;
                    pitchRotate = pitchRotate < -cameraYlimit ? -cameraYlimit : pitchRotate;
                    xRotate.setAngle(pitchRotate);
                    double yawRotate = yRotate.getAngle() - (mouseXnew - mouseXold) / rotateModifier;
                    yRotate.setAngle(yawRotate);
                }
                mouseXold = mouseXnew;
                mouseYold = mouseYnew;
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}