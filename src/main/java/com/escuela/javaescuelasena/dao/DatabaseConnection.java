package com.escuela.javaescuelasena.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/sena_estudiantes";
    private static final String USER = "root";
    private static final String PASSWORD = "1632";

    public static Connection getConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // 🔥 Cargar el driver manualmente
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos");
            return con;
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver de MariaDB no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
        }
        return null;

    }
}
