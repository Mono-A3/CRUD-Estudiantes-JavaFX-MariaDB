package com.escuela.javaescuelasena;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/escuela/javaescuelasena/estudiantes-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Estudiantes");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("favicon.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        Platform.runLater(() -> stage.centerOnScreen());
    }

    public static void main(String[] args) {
        launch();
    }
}