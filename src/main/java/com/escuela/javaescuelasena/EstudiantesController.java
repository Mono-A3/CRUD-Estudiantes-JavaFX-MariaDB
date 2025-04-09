package com.escuela.javaescuelasena;

import com.escuela.javaescuelasena.dao.EstudiantesDAO;
import com.escuela.javaescuelasena.model.Estudiante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

public class EstudiantesController {
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtCarrera;
    @FXML
    private TextField txtCiudad;
    @FXML
    private ComboBox<String> cbEstado;

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

        ObservableList<String> estados = FXCollections.observableArrayList("Activo", "Inactivo");
        cbEstado.setItems(estados);


        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<>("carrera"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tblEstudiantes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cargarEstudiantes();

        tblEstudiantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                llenarCampos(newSelection);
            }
        });

        colEstado.setCellFactory(column -> new TableCell<Estudiante, String>() {
            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estado);
                    if (estado.equalsIgnoreCase("Activo")) {
                        setStyle("-fx-text-fill: #2E8B57; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #FF6347; -fx-font-weight: bold;");
                    }
                }
            }
        });
    }

    private void cargarEstudiantes() {
        List<Estudiante> estudiantes = estudiantesDAO.obtenerTodos();
        tblEstudiantes.getItems().setAll(estudiantes);
        tblEstudiantes.refresh();
    }

    @FXML
    private void agregarEstudiante() {
        String nombre = txtNombre.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        String carrera = txtCarrera.getText();
        String ciudad = txtCiudad.getText();
        String estado = cbEstado.getValue();

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

        String nombre = txtNombre.getText().trim();
        String edadTexto = txtEdad.getText().trim();
        String carrera = txtCarrera.getText().trim();
        String ciudad = txtCiudad.getText().trim();
        String estado = cbEstado.getValue();

        if (nombre.isEmpty() || edadTexto.isEmpty() || carrera.isEmpty() || ciudad.isEmpty() || estado.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos deben estar llenos");
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadTexto);
            if (edad < 0) {
                mostrarAlerta("Error", "La edad debe ser un número positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La edad debe ser un número válido");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación de modificación");
        confirmacion.setHeaderText("¿Estás seguro de que quieres modificar este estudiante?");
        confirmacion.setContentText("Este cambio no se puede deshacer.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setEdad(Integer.parseInt(txtEdad.getText()));
            seleccionado.setCarrera(txtCarrera.getText());
            seleccionado.setCiudad(txtCiudad.getText());
            seleccionado.setEstado(cbEstado.getValue());

            estudiantesDAO.modificar(seleccionado);
            cargarEstudiantes();
            limpiarCampos();

            mostrarAlerta("Exito", "Estudiante actualizado correctamente.");
        }
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

    @FXML
    private void buscarEstudiante() {
        String idInput = txtId.getText().trim();
        String nombreInput = txtNombre.getText().trim();

        if (idInput.isEmpty() && nombreInput.isEmpty()) {
            mostrarAlerta("Error", "Ingrese un ID o un nombre para buscar.");
            return;
        }

        List<Estudiante> estudiantes = estudiantesDAO.obtenerTodos();

        // 🔎 Buscar por ID si se ingresó un número válido
        if (!idInput.isEmpty() && idInput.matches("\\d+")) {
            int idBuscado = Integer.parseInt(idInput);
            Optional<Estudiante> resultado = estudiantes.stream()
                    .filter(est -> est.getId() == idBuscado)
                    .findFirst();

            if (resultado.isPresent()) {
                Estudiante estudianteEncontrado = resultado.get();
                tblEstudiantes.setItems(FXCollections.observableArrayList(estudianteEncontrado));

                // ✅ Llenar los campos porque es un único estudiante
                txtId.setText(String.valueOf(estudianteEncontrado.getId()));
                txtNombre.setText(estudianteEncontrado.getNombre());
                txtEdad.setText(String.valueOf(estudianteEncontrado.getEdad()));
                txtCarrera.setText(estudianteEncontrado.getCarrera());
                txtCiudad.setText(estudianteEncontrado.getCiudad());
                cbEstado.setValue(estudianteEncontrado.getEstado());
            } else {
                mostrarAlerta("Error", "Estudiante no encontrado.");
                tblEstudiantes.getItems().clear();
            }
            return;
        }

        if (!nombreInput.isEmpty()) {
            String nombreBuscado = nombreInput.toLowerCase();
            List<Estudiante> coincidencias = estudiantes.stream()
                    .filter(est -> est.getNombre().toLowerCase().contains(nombreBuscado))
                    .toList();

            if (!coincidencias.isEmpty()) {
                tblEstudiantes.setItems(FXCollections.observableArrayList(coincidencias));

                //--- Cambio clave: No limpiamos los campos si hay múltiples resultados ---
                if (coincidencias.size() == 1) {  // Si es solo uno, llenamos los campos
                    Estudiante estudianteUnico = coincidencias.get(0);
                    llenarCampos(estudianteUnico);
                }
                // (Si hay varios, no hacemos nada: los campos quedan como estaban)
            } else {
                mostrarAlerta("Error", "Estudiante no encontrado.");
                tblEstudiantes.getItems().clear();
            }
        }
    }

    @FXML
    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtEdad.clear();
        txtCarrera.clear();
        txtCiudad.clear();
        cbEstado.setValue(null);

        cargarEstudiantes();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void llenarCampos(Estudiante estudiante) {
        txtNombre.setText(estudiante.getNombre());
        txtEdad.setText(String.valueOf(estudiante.getEdad()));
        txtCarrera.setText(estudiante.getCarrera());
        txtCiudad.setText(estudiante.getCiudad());
        cbEstado.setValue(estudiante.getEstado());
    }
}