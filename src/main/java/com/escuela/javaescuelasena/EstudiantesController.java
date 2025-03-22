package com.escuela.javaescuelasena;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EstudiantesController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}