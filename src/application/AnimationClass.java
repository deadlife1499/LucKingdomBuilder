package application;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.*;
import javafx.scene.transform.*;
import javafx.scene.shape.*;

import javax.swing.*;
import java.awt.*;
import java.beans.EventHandler;

public class AnimationClass {
    private static boolean active;
    public AnimationClass(){}
    public static void SettlementFadeScale(double Duration, javafx.scene.Node n, javafx.scene.image.Image settlement, double x1, double y1, double width1){
        active = true;
        ScaleTransition current = new ScaleTransition(javafx.util.Duration.seconds(Duration), n);
        FadeTransition whatever = new FadeTransition(javafx.util.Duration.seconds(Duration), n);
        GUI.get().setCancelButtonDisable(true);
        whatever.setFromValue(.25);
        whatever.setToValue(1);
        current.setFromX(1.4);
        current.setFromY(1.4);
        current.setToY(1);
        current.setToX(1);
        current.setInterpolator(Interpolator.LINEAR);
        whatever.setInterpolator(Interpolator.LINEAR);
        whatever.play();
        current.setOnFinished(e -> {
            Board.get().getSettlementObj().addSettlement(settlement, x1, y1, width1,42);
            n.setVisible(false);
            GUI.get().setCancelButtonDisable(false);
            active = false;
        });
        current.play();
    }
    public static boolean getActive(){
        return active;
    }
    public static void FadeScreen(Node node){
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.BLACK);
        rectangle.setHeight(1080);
        rectangle.setWidth(1920);
        rectangle.setX(0);
        rectangle.setY(0);
        ObjectHandler.get().add(rectangle);
        FadeTransition iHateBlacks = new FadeTransition(Duration.seconds(1), rectangle);
        iHateBlacks.setInterpolator(Interpolator.LINEAR);
        iHateBlacks.setFromValue(.9);
        iHateBlacks.setToValue(0);
        iHateBlacks.play();
        iHateBlacks.setOnFinished(e -> {
            rectangle.setDisable(true);
            ObjectHandler.get().remove(rectangle);
        });
    }
    public static void FadeScreenOut(Node start){
        FadeTransition iHateBlacks = new FadeTransition(Duration.seconds(1), start);
        //FadeTransition iHateWhites = new FadeTransition(Duration.seconds(1), end);
        iHateBlacks.setInterpolator(Interpolator.LINEAR);
        //iHateWhites.setInterpolator(Interpolator.LINEAR);
        iHateBlacks.setFromValue(1);
        //iHateWhites.setFromValue(0);
        iHateBlacks.setToValue(0);
        //iHateWhites.setToValue(1);
        iHateBlacks.play();
        //iHateWhites.play();
    }
    public static void FadeScreenIn(Node start){
        FadeTransition iHateBlacks = new FadeTransition(Duration.seconds(1), start);
        //FadeTransition iHateWhites = new FadeTransition(Duration.seconds(1), end);
        iHateBlacks.setInterpolator(Interpolator.LINEAR);
        //iHateWhites.setInterpolator(Interpolator.LINEAR);
        iHateBlacks.setFromValue(0);
        //iHateWhites.setFromValue(0);
        iHateBlacks.setToValue(1);
        //iHateWhites.setToValue(1);
        iHateBlacks.play();
        //iHateWhites.play();
    }
}
