package com.escuela.javaescuelasena.dao;

import com.escuela.javaescuelasena.model.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudiantesDAO {

    public void guardar(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (nombre, edad, carrera, ciudad, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setInt(2, estudiante.getEdad());
            stmt.setString(3, estudiante.getCarrera());
            stmt.setString(4, estudiante.getCiudad());
            stmt.setString(5, estudiante.getEstado());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Estudiante> obtenerTodos() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                estudiantes.add(new Estudiante(
                        rs.getInt("id_estudiante"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("carrera"),
                        rs.getString("ciudad"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estudiantes;
    }

    public void modificar(Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET nombre=?, edad=?, carrera=?, ciudad=?, estado=?   WHERE id_estudiante=? ";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setInt(2, estudiante.getEdad());
            stmt.setString(3, estudiante.getCarrera());
            stmt.setString(4, estudiante.getCiudad());
            stmt.setString(5, estudiante.getEstado());
            stmt.setInt(6, estudiante.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(Estudiante estudiante) {
        String sql = "DELETE FROM estudiantes WHERE id_estudiante=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, estudiante.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No se encontró el estudiante con ID " + estudiante.getId());
            } else {
                System.out.println("Estudiante eliminado exitosamente.");
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Estudiante buscarPorId(int id) {
        String sql = "SELECT * FROM estudiantes WHERE id_estudiante=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Estudiante(
                        rs.getInt("id_estudiante"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("carrera"),
                        rs.getString("ciudad"),
                        rs.getString("estado")
                );
            } else {
                System.out.println("No se encontró el estudiante con ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}