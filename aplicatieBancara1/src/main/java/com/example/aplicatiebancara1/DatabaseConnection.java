package com.example.aplicatiebancara1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Informații de conectare pentru Oracle
    private static final String URL = "jdbc:oracle:thin:@//193.226.34.57:1521/orclpdb.docker.internal";
    private static final String USER = "RAILEANUA_63";
    private static final String PASSWORD = "STUD";

    // Metoda pentru a obține o conexiune nouă
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexiune la baza de date Oracle stabilită cu succes!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Eroare la stabilirea conexiunii: " + e.getMessage());
            throw e;
        }
    }

    // Opțional: metodă pentru a închide o conexiune specifică (nu mai este necesară în mod normal, datorită try-with-resources)
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexiune la baza de date închisă.");
            } catch (SQLException e) {
                System.err.println("Eroare la închiderea conexiunii: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}