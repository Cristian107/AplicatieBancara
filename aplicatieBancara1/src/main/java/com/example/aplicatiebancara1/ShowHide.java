package com.example.aplicatiebancara1;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ShowHide {


    public static void showPane(Pane p){
        p.setVisible(true);
        TranslateTransition transitionP = new TranslateTransition(Duration.millis(300), p);
        transitionP.setToY(0);
        transitionP.play();
    }
    public static void hidePane(Pane p){
        TranslateTransition transitionP = new TranslateTransition(Duration.millis(300), p);
        transitionP.setToY(516);
        transitionP.setOnFinished(e->p.setVisible(false));
        transitionP.play();
    }

    //vBox si logOut button
    public static void showVBox(VBox vBox,Button logOut) {
        // Setează VBox-ul ca vizibil
        vBox.setVisible(true);
        logOut.setVisible(true);
        // Creează animația pentru a aduce VBox-ul din dreapta
        TranslateTransition transitionVBox = new TranslateTransition(Duration.millis(300), vBox);
        TranslateTransition transitionLogOut = new TranslateTransition(Duration.millis(300), logOut);
        transitionVBox.setToX(0); // Muta VBox-ul la poziția sa normală
        transitionLogOut.setToX(0);
        transitionLogOut.play();
        transitionVBox.play(); // Rulează animația
    }
    public static void hideVBox(VBox vBox, Button logOut) {
        // Creează animația pentru a muta VBox-ul în afacerea dreaptă
        TranslateTransition transitionVBox = new TranslateTransition(Duration.millis(300), vBox);
        TranslateTransition transitionLogOut = new TranslateTransition(Duration.millis(300), logOut);
        transitionVBox.setToX(386); // Muta VBox-ul la poziția sa normală
        transitionLogOut.setToX(386);
        transitionVBox.setOnFinished(e -> vBox.setVisible(false));
        transitionLogOut.setOnFinished(e -> logOut.setVisible(false));
        transitionVBox.play(); // Rulează animația
        transitionLogOut.play();
    }
}
