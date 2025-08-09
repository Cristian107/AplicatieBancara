package com.example.aplicatiebancara1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {
        public static void switchToScene(Stage stage, String fxmlFile, double width, double height) {
            try {
                FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlFile));
                AnchorPane newRoot = loader.load();
                Scene newScene = new Scene(newRoot, width, height);
                stage.setScene(newScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

