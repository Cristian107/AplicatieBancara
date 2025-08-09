package com.example.aplicatiebancara1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static com.example.aplicatiebancara1.ShowHide.hideVBox;
import static com.example.aplicatiebancara1.ShowHide.showVBox;

public class MenuController {
    @FXML
    private Button logOut;
    @FXML
    private Button menu;
    @FXML
    private VBox vBox;
    @FXML
    private Pane interfata;  // For Trimite
    @FXML
    private Pane interfata1; // For Retragere
    @FXML
    private Pane interfata2; // For Depunere
    @FXML
    private Text balanta;
    @FXML
    private Button hello;
    @FXML
    private TextField amountTrimite;
    @FXML
    private TextField toUserTrimite;
    @FXML
    private TextField amountRetragere;
    @FXML
    private TextField pinRetragere;
    @FXML
    private TextField amountDepunere;
    @FXML
    private TextField pinDepunere;
    // Referințe la cele 4 elemente Text pentru tranzacții
    @FXML
    private Text text1;
    @FXML
    private Text text2;
    @FXML
    private Text text3;
    @FXML
    private Text text4;

    private String actiune;

    public void initialize() {
        vBox.setTranslateX(386);
        logOut.setTranslateX(386);
        interfata.setTranslateY(516);
        interfata1.setTranslateY(516);
        interfata2.setTranslateY(516);
        vBox.setVisible(false);
        logOut.setVisible(false);
        interfata.setVisible(false);
        interfata1.setVisible(false);
        interfata2.setVisible(false);

        Utilizator utilizator = HelloApplication.getUtilizatorAutentificat();
        if (utilizator != null) {
            balanta.setText(String.format("%.2f RON", utilizator.getSold()));
            hello.setText("Bună, " + utilizator.getPrenume() + " " + utilizator.getNume() + "!");
            updateTranzactii(); // Inițializăm tranzacțiile la încărcarea meniului
        } else {
            showErrorPopup("Utilizator neautentificat");
            balanta.setText("0.00 RON");
            hello.setText("Bună, barosane!");
        }
    }

    // Metodă pentru actualizarea tranzacțiilor în elementele Text
    private void updateTranzactii() {
        Utilizator utilizator = HelloApplication.getUtilizatorAutentificat();
        if (utilizator == null) return;

        List<Tranzactie> tranzactii = TranzactieDAO.getIstoricTranzactii(utilizator.getIdUtilizator());
        Text[] textElements = {text1, text2, text3, text4};

        // Curățăm elementele Text
        for (Text textElement : textElements) {
            if (textElement != null) {
                textElement.setText("");
            }
        }

        // Afișăm ultimele 4 tranzacții
        for (int i = 0; i < Math.min(tranzactii.size(), 4); i++) {
            Tranzactie tranzactie = tranzactii.get(i);
            String text;
            double valoare = tranzactie.getValoareTranzactie();
            String semn = valoare >= 0 ? "+" : "-";

            if (!tranzactie.getIdUtilizator().equals(tranzactie.getIdDestinatar())) {
                // Tranzacție de tip „Trimite” sau „Primit”
                String numeDestinatar = UtilizatorDAO.getNumeComplet(tranzactie.getIdDestinatar());
                text = String.format("%s%.2f RON către %s", semn, Math.abs(valoare), numeDestinatar);
            } else {
                // Tranzacție de tip „Depunere” sau „Retragere”
                text = String.format("%s%.2f RON", semn, Math.abs(valoare));
            }

            if (textElements[i] != null) {
                textElements[i].setText(text);
            }
        }
    }

    @FXML
    public void toggleVBox() {
        if (vBox.isVisible()) {
            hideVBox(vBox, logOut);
        } else {
            if (interfata.isVisible())
                ShowHide.showPane(interfata);
            if (interfata1.isVisible())
                ShowHide.showPane(interfata1);
            if (interfata2.isVisible())
                ShowHide.showPane(interfata2);
            showVBox(vBox, logOut);
        }
    }

    private void hideAllPanes() {
        if (interfata.isVisible()) {
            interfata.setTranslateY(516);
            ShowHide.hidePane(interfata);
        }
        if (interfata1.isVisible()) {
            interfata1.setTranslateY(516);
            ShowHide.hidePane(interfata1);
        }
        if (interfata2.isVisible()) {
            interfata2.setTranslateY(516);
            ShowHide.hidePane(interfata2);
        }
    }

    @FXML
    private void onTrimiteClick(ActionEvent event) {
        if (vBox.isVisible())
            hideVBox(vBox, logOut);

        if (interfata1.isVisible()) {
            interfata1.setTranslateY(516);
            ShowHide.hidePane(interfata1);
        }
        if (interfata2.isVisible()) {
            interfata2.setTranslateY(516);
            ShowHide.hidePane(interfata2);
        }

        if (!interfata.isVisible())
            ShowHide.showPane(interfata);

        if (interfata.isVisible() && !Objects.equals(actiune, ((Button) event.getSource()).getText())) {
            interfata.setTranslateY(516);
            ShowHide.showPane(interfata);
        }

        actiune = ((Button) event.getSource()).getText();
        System.out.println(actiune);
    }

    @FXML
    private void onDepunereClick(ActionEvent event) {
        if (vBox.isVisible())
            hideVBox(vBox, logOut);

        if (interfata.isVisible()) {
            interfata.setTranslateY(516);
            ShowHide.hidePane(interfata);
        }
        if (interfata1.isVisible()) {
            interfata1.setTranslateY(516);
            ShowHide.hidePane(interfata1);
        }

        if (!interfata2.isVisible())
            ShowHide.showPane(interfata2);

        if (interfata2.isVisible() && !Objects.equals(actiune, ((Button) event.getSource()).getText())) {
            interfata2.setTranslateY(516);
            ShowHide.showPane(interfata2);
        }

        actiune = ((Button) event.getSource()).getText();
        System.out.println(actiune);
    }

    @FXML
    private void onRetragereClick(ActionEvent event) {
        if (vBox.isVisible())
            hideVBox(vBox, logOut);

        if (interfata.isVisible()) {
            interfata.setTranslateY(516);
            ShowHide.hidePane(interfata);
        }
        if (interfata2.isVisible()) {
            interfata2.setTranslateY(516);
            ShowHide.hidePane(interfata2);
        }

        if (!interfata1.isVisible())
            ShowHide.showPane(interfata1);

        if (interfata1.isVisible() && !Objects.equals(actiune, ((Button) event.getSource()).getText())) {
            interfata1.setTranslateY(516);
            ShowHide.showPane(interfata1);
        }

        actiune = ((Button) event.getSource()).getText();
        System.out.println(actiune);
    }

    @FXML
    private void onConfirmTrimite() {
        Utilizator utilizator = HelloApplication.getUtilizatorAutentificat();
        if (utilizator == null) {
            showErrorPopup("Utilizator neautentificat");
            return;
        }

        try {
            double amount = Double.parseDouble(amountTrimite.getText());
            String toUserId = toUserTrimite.getText();

            if (amount <= 0) {
                showErrorPopup("Suma trebuie să fie pozitivă");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UTILIZATORI WHERE ID_UTILIZATOR = ?");
                stmt.setString(1, toUserId);
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) {
                    showErrorPopup("Utilizatorul destinatar nu există");
                    return;
                }

                String destinatarNume = rs.getString("NUME");
                String destinatarPrenume = rs.getString("PRENUME");
                double recipientBalance = rs.getDouble("SOLD");

                if (utilizator.getSold() < amount) {
                    showErrorPopup("Sold insuficient");
                    return;
                }

                double newSenderBalance = utilizator.getSold() - amount;
                stmt = conn.prepareStatement("UPDATE UTILIZATORI SET SOLD = ? WHERE ID_UTILIZATOR = ?");
                stmt.setDouble(1, newSenderBalance);
                stmt.setString(2, utilizator.getIdUtilizator());
                stmt.executeUpdate();

                double newRecipientBalance = recipientBalance + amount;
                stmt = conn.prepareStatement("UPDATE UTILIZATORI SET SOLD = ? WHERE ID_UTILIZATOR = ?");
                stmt.setDouble(1, newRecipientBalance);
                stmt.setString(2, toUserId);
                stmt.executeUpdate();

                stmt = conn.prepareStatement("INSERT INTO TRANZACTII (ID_UTILIZATOR, ID_DESTINATAR, VALOARE_TRANZACTIE, DETALII_TRANZACTIE) VALUES (?, ?, ?, ?)");
                stmt.setString(1, utilizator.getIdUtilizator());
                stmt.setString(2, toUserId);
                stmt.setDouble(3, -amount);
                stmt.setString(4, "Trimite către " + toUserId);
                stmt.executeUpdate();

                stmt = conn.prepareStatement("INSERT INTO TRANZACTII (ID_UTILIZATOR, ID_DESTINATAR, VALOARE_TRANZACTIE, DETALII_TRANZACTIE) VALUES (?, ?, ?, ?)");
                stmt.setString(1, toUserId);
                stmt.setString(2, utilizator.getIdUtilizator());
                stmt.setDouble(3, amount);
                stmt.setString(4, "Primit de la " + utilizator.getIdUtilizator());
                stmt.executeUpdate();

                utilizator.setSold(newSenderBalance);
                balanta.setText(String.format("%.2f RON", newSenderBalance));
                ShowHide.hidePane(interfata);

                // Actualizăm tranzacțiile după transfer
                updateTranzactii();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Transfer efectuat");
                alert.setHeaderText(null);
                alert.setContentText(String.format("S-au trimis %.2f RON către %s %s (%s)",
                        amount, destinatarPrenume, destinatarNume, toUserId));
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            showErrorPopup("Suma invalidă");
        } catch (SQLException e) {
            showErrorPopup("Eroare bază de date: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onConfirmRetragere() {
        Utilizator utilizator = HelloApplication.getUtilizatorAutentificat();
        if (utilizator == null) {
            showErrorPopup("Utilizator neautentificat");
            return;
        }

        try {
            double amount = Double.parseDouble(amountRetragere.getText());
            String pinText = pinRetragere.getText();

            if (amount <= 0) {
                showErrorPopup("Suma trebuie să fie pozitivă");
                return;
            }

            int pin;
            try {
                pin = Integer.parseInt(pinText);
            } catch (NumberFormatException e) {
                showErrorPopup("PIN-ul trebuie să fie numeric");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("SELECT PIN FROM UTILIZATORI WHERE ID_UTILIZATOR = ?");
                stmt.setString(1, utilizator.getIdUtilizator());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int pinFromDb = rs.getInt("PIN");
                    if (pinFromDb != pin) {
                        showErrorPopup("PIN incorect");
                        return;
                    }
                } else {
                    showErrorPopup("Utilizatorul nu există în bază");
                    return;
                }

                if (utilizator.getSold() < amount) {
                    showErrorPopup("Sold insuficient");
                    return;
                }

                double newBalance = utilizator.getSold() - amount;
                stmt = conn.prepareStatement("UPDATE UTILIZATORI SET SOLD = ? WHERE ID_UTILIZATOR = ?");
                stmt.setDouble(1, newBalance);
                stmt.setString(2, utilizator.getIdUtilizator());
                stmt.executeUpdate();

                stmt = conn.prepareStatement("INSERT INTO TRANZACTII (ID_UTILIZATOR, ID_DESTINATAR, VALOARE_TRANZACTIE, DETALII_TRANZACTIE) VALUES (?, ?, ?, ?)");
                stmt.setString(1, utilizator.getIdUtilizator());
                stmt.setString(2, utilizator.getIdUtilizator());
                stmt.setDouble(3, -amount);
                stmt.setString(4, "Retragere");
                stmt.executeUpdate();

                utilizator.setSold(newBalance);
                balanta.setText(String.format("%.2f RON", newBalance));
                ShowHide.hidePane(interfata1);

                // Actualizăm tranzacțiile după retragere
                updateTranzactii();
            }
        } catch (NumberFormatException e) {
            showErrorPopup("Suma invalidă");
        } catch (SQLException e) {
            showErrorPopup("Eroare bază de date: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onConfirmDepunere() {
        Utilizator utilizator = HelloApplication.getUtilizatorAutentificat();
        if (utilizator == null) {
            showErrorPopup("Utilizator neautentificat");
            return;
        }

        try {
            double amount = Double.parseDouble(amountDepunere.getText());
            String pinText = pinDepunere.getText();

            if (amount <= 0) {
                showErrorPopup("Suma trebuie să fie pozitivă");
                return;
            }

            int pin;
            try {
                pin = Integer.parseInt(pinText);
            } catch (NumberFormatException e) {
                showErrorPopup("PIN-ul trebuie să fie numeric");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("SELECT PIN FROM UTILIZATORI WHERE ID_UTILIZATOR = ?");
                stmt.setString(1, utilizator.getIdUtilizator());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int pinFromDb = rs.getInt("PIN");
                    if (pinFromDb != pin) {
                        showErrorPopup("PIN incorect");
                        return;
                    }
                } else {
                    showErrorPopup("Utilizatorul nu există în bază");
                    return;
                }

                double newBalance = utilizator.getSold() + amount;
                stmt = conn.prepareStatement("UPDATE UTILIZATORI SET SOLD = ? WHERE ID_UTILIZATOR = ?");
                stmt.setDouble(1, newBalance);
                stmt.setString(2, utilizator.getIdUtilizator());
                stmt.executeUpdate();

                stmt = conn.prepareStatement("INSERT INTO TRANZACTII (ID_UTILIZATOR, ID_DESTINATAR, VALOARE_TRANZACTIE, DETALII_TRANZACTIE) VALUES (?, ?, ?, ?)");
                stmt.setString(1, utilizator.getIdUtilizator());
                stmt.setString(2, utilizator.getIdUtilizator());
                stmt.setDouble(3, amount);
                stmt.setString(4, "Depunere");
                stmt.executeUpdate();

                utilizator.setSold(newBalance);
                balanta.setText(String.format("%.2f RON", newBalance));
                ShowHide.hidePane(interfata2);

                // Actualizăm tranzacțiile după depunere
                updateTranzactii();
            }
        } catch (NumberFormatException e) {
            showErrorPopup("Suma invalidă");
        } catch (SQLException e) {
            showErrorPopup("Eroare bază de date: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onTrimiteCLose() {
        ShowHide.hidePane(interfata);
    }

    @FXML
    private void onRetragereCLose() {
        ShowHide.hidePane(interfata1);
    }

    @FXML
    private void onDepunereCLose() {
        ShowHide.hidePane(interfata2);
    }

    @FXML
    private void onLogOutClick() {
        HelloApplication.setUtilizatorAutentificat(null);
        Stage stage = (Stage) logOut.getScene().getWindow();
        SceneSwitcher.switchToScene(stage, "interfataPrimara.fxml", 500, 500);
    }

    private void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}