package com.example.aplicatiebancara1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Utilizator utilizatorAutentificat; // Variabilă statică pentru utilizatorul autentificat

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("interfataPrimara.fxml"));
        AnchorPane root = fxmlLoader.load();

        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // Getter și setter pentru utilizatorul autentificat
    public static Utilizator getUtilizatorAutentificat() {
        return utilizatorAutentificat;
    }

    public static void setUtilizatorAutentificat(Utilizator utilizator) {
        utilizatorAutentificat = utilizator;
    }
}