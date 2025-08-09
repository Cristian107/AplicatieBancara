package com.example.aplicatiebancara1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TranzactieDAO {

    // Metodă pentru efectuarea unei tranzacții (transfer între conturi)
    public static boolean efectueazaTranzactie(String idExpeditor, String idDestinatar, double suma, String detalii) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Dezactivează auto-commit pentru a gestiona tranzacția manual

            // 1. Verifică dacă există suficienți bani în contul expeditorului
            double soldExpeditor = UtilizatorDAO.getSold(idExpeditor);
            if (soldExpeditor < suma) {
                return false; // Fonduri insuficiente
            }

            // 2. Scade suma din contul expeditorului
            if (!UtilizatorDAO.actualizareSold(idExpeditor, soldExpeditor - suma)) {
                conn.rollback();
                return false;
            }

            // 3. Adaugă suma în contul destinatarului
            double soldDestinatar = UtilizatorDAO.getSold(idDestinatar);
            if (!UtilizatorDAO.actualizareSold(idDestinatar, soldDestinatar + suma)) {
                conn.rollback();
                return false;
            }

            // 4. Înregistrează tranzacția în istoricul tranzacțiilor
            String query = "INSERT INTO tranzactii (id_utilizator, id_destinatar, valoare_tranzactie, detalii_tranzactie) " +
                    "VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, idExpeditor);
                stmt.setString(2, idDestinatar);
                stmt.setDouble(3, suma);
                stmt.setString(4, detalii);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit(); // Confirmă tranzacția
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Anulează tranzacția în caz de eroare
                }
            } catch (SQLException ex) {
                System.err.println("Eroare la rollback: " + ex.getMessage());
            }
            System.err.println("Eroare la efectuarea tranzacției: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Resetează auto-commit
                }
            } catch (SQLException e) {
                System.err.println("Eroare la resetarea auto-commit: " + e.getMessage());
            }
        }
    }

    // Metodă pentru obținerea istoricului tranzacțiilor unui utilizator
    public static List<Tranzactie> getIstoricTranzactii(String idUtilizator) {
        List<Tranzactie> tranzactii = new ArrayList<>();
        String query = "SELECT * FROM tranzactii WHERE id_utilizator = ? OR id_destinatar = ? ORDER BY id_tranzactie DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idUtilizator);
            stmt.setString(2, idUtilizator);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Tranzactie tranzactie = new Tranzactie(
                            rs.getInt("id_tranzactie"),
                            rs.getString("id_utilizator"),
                            rs.getString("id_destinatar"),
                            rs.getDouble("valoare_tranzactie"),
                            rs.getString("detalii_tranzactie")
                    );
                    tranzactii.add(tranzactie);
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare la obținerea istoricului tranzacțiilor: " + e.getMessage());
            e.printStackTrace();
        }

        return tranzactii;
    }

    // Metodă pentru obținerea detaliilor unei tranzacții specifice
    public static Tranzactie getTranzactie(int idTranzactie) {
        String query = "SELECT * FROM tranzactii WHERE id_tranzactie = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idTranzactie);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Tranzactie(
                            rs.getInt("id_tranzactie"),
                            rs.getString("id_utilizator"),
                            rs.getString("id_destinatar"),
                            rs.getDouble("valoare_tranzactie"),
                            rs.getString("detalii_tranzactie")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare la obținerea detaliilor tranzacției: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}