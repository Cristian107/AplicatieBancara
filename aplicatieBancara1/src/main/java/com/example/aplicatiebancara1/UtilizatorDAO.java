package com.example.aplicatiebancara1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilizatorDAO {

    // Metodă pentru autentificarea utilizatorului
    public static Utilizator login(String idUtilizator, String parola) {
        String query = "SELECT * FROM utilizatori WHERE id_utilizator = ? AND parola = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idUtilizator);
            stmt.setString(2, parola);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilizator(
                            rs.getString("id_utilizator"),
                            rs.getString("parola"),
                            rs.getString("nume"),
                            rs.getString("prenume"),
                            rs.getDouble("sold"),
                            rs.getString("PIN")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare la autentificarea utilizatorului: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Metodă pentru verificarea existenței unui utilizator după ID
    public static boolean existaUtilizator(String idUtilizator) {
        String query = "SELECT COUNT(*) FROM utilizatori WHERE id_utilizator = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idUtilizator);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare la verificarea existenței utilizatorului: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Metodă pentru actualizarea soldului unui utilizator
    public static boolean actualizareSold(String idUtilizator, double soldNou) {
        String query = "UPDATE utilizatori SET sold = ? WHERE id_utilizator = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, soldNou);
            stmt.setString(2, idUtilizator);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Eroare la actualizarea soldului: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Metodă pentru adăugarea unui utilizator nou
    public static boolean adaugaUtilizator(Utilizator utilizator) {
        String query = "INSERT INTO utilizatori (id_utilizator, parola, nume, prenume, sold, PIN) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, utilizator.getIdUtilizator());
            stmt.setString(2, utilizator.getParola());
            stmt.setString(3, utilizator.getNume());
            stmt.setString(4, utilizator.getPrenume());
            stmt.setDouble(5, utilizator.getSold());
            stmt.setString(6, utilizator.getPin());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Eroare la adăugarea utilizatorului: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Metodă pentru verificarea PIN-ului unui utilizator
    public static boolean verificaPin(String idUtilizator, String pin) {
        String query = "SELECT COUNT(*) FROM utilizatori WHERE id_utilizator = ? AND PIN = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idUtilizator);
            stmt.setString(2, pin);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare la verificarea PIN-ului: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Metodă pentru a obține sold-ul curent al unui utilizator
    public static double getSold(String idUtilizator) {
        String query = "SELECT sold FROM utilizatori WHERE id_utilizator = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idUtilizator);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("sold");
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare la obținerea soldului: " + e.getMessage());
            e.printStackTrace();
        }

        return -1;
    }

    // Metodă pentru a obține numele complet al unui utilizator
    public static String getNumeComplet(String idUtilizator) {
        String query = "SELECT nume, prenume FROM utilizatori WHERE id_utilizator = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idUtilizator);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("prenume") + " " + rs.getString("nume");
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare la obținerea numelui complet: " + e.getMessage());
            e.printStackTrace();
        }
        return idUtilizator; // Fallback în caz de eroare
    }
}