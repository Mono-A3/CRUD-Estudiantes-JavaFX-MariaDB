package com.escuela.javaescuelasena;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class EstudiantesController {
    @FXML
    private TableView<?> tblEstudiantes;

    @FXML
    public void initialize() {
        if (tblEstudiantes == null) {
            System.err.println("⚠ ERROR: tblEstudiantes es NULL. Revisa el FXML.");
        } else {
            tblEstudiantes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
    }
}