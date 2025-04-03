package com.escuela.javaescuelasena;

import com.escuela.javaescuelasena.dao.EstudiantesDAO;
import com.escuela.javaescuelasena.model.Estudiante;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

public class EstudiantesController {
    @FXML
    private TableView<Estudiante> tblEstudiantes;

    @FXML
    private TableColumn<Estudiante, Integer> colId;
    @FXML
    private TableColumn<Estudiante, String> colNombre;
    @FXML
    private TableColumn<Estudiante, Integer> colEdad;
    @FXML
    private TableColumn<Estudiante, String> colCarrera;
    @FXML
    private TableColumn<Estudiante, String> colCiudad;
    @FXML
    private TableColumn<Estudiante, String> colEstado;

    private final EstudiantesDAO estudiantesDAO = new EstudiantesDAO();

    @FXML
    public void initialize() {
        if (tblEstudiantes == null) {
            System.err.println("⚠ ERROR: tblEstudiantes es NULL. Revisa el FXML.");
            return;
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<>("carrera"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tblEstudiantes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cargarEstudiantes();
    }

    private void cargarEstudiantes() {
        List<Estudiante> estudiantes = estudiantesDAO.obtenerTodos();
        tblEstudiantes.getItems().setAll(estudiantes);
    }

    @FXML
    private void agregarEstudiante() {
        String nombre = txtNombre.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        String carrera = txtCarrera.getText();
        String ciudad = txtCiudad.getText();
        String estado = txtEstado.getText();

        if (nombre.isEmpty() || carrera.isEmpty() || ciudad.isEmpty() || estado.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos deben estar llenos");
            return;
        }

        Estudiante nuevo = new Estudiante(0, nombre, edad, carrera, ciudad, estado);

        estudiantesDAO.guardar(nuevo);
        cargarEstudiantes();
        limpiarCampos();
    }

    @FXML
    private void modificarEstudiante() {
        Estudiante seleccionado = tblEstudiantes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Error", "Selecciona un estudiante de la tabla.");
            return;
        }

        seleccionado.setNombre(txtNombre.getText());
        seleccionado.setEdad(Integer.parseInt(txtEdad.getText()));
        seleccionado.setCarrera(txtCarrera.getText());
        seleccionado.setCiudad(txtCiudad.getText());
        seleccionado.setEstado(txtEstado.getText());

        estudiantesDAO.modificar(seleccionado);
        cargarEstudiantes();
        limpiarCampos();
    }

    @FXML
    private void eliminarEstudiante() {
        Estudiante seleccionado = tblEstudiantes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Error", "Selecciona un estudiante apra eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que quieres eliminar a " + seleccionado.getNombre() + "?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.YES) {
            estudiantesDAO.eliminar(seleccionado);
            cargarEstudiantes();
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtEdad.clear();
        txtCarrera.clear();
        txtCiudad.clear();
        txtEstado.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}