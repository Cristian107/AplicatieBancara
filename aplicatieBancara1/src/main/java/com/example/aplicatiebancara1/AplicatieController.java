package com.example.aplicatiebancara1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class AplicatieController {
    @FXML
    private Button button; // Buton Log In
    @FXML
    private Button button1; // Buton Create Account (deschide panel_aut)
    @FXML
    private Button createAccountButton; // Buton Create Account din panel_aut
    @FXML
    private Button CLose; // Buton Close
    @FXML
    private Pane panel_aut;
    @FXML
    private TextField username; // Username pentru Log In
    @FXML
    private TextField password; // Parola pentru Log In
    @FXML
    private TextField usernameCreate; // Username pentru Create Account
    @FXML
    private TextField passwordCreate; // Parola pentru Create Account
    @FXML
    private TextField nume; // Nume
    @FXML
    private TextField prenume; // Prenume
    @FXML
    private TextField pin; // PIN
    @FXML
    private Label errorLabel; // Label pentru mesaje de eroare/succes

    private String actiune;

    public void initialize() {
        panel_aut.setTranslateY(516);
        panel_aut.setVisible(false);
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }
    }

    @FXML
    protected void onLogInCLick() {
        String idUtilizator = username.getText();
        String parola = password.getText();

        if (idUtilizator.isEmpty() || parola.isEmpty()) {
            showError("Te rugăm să completezi ambele câmpuri!");
            return;
        }

        Utilizator utilizator = UtilizatorDAO.login(idUtilizator, parola);
        if (utilizator != null) {
            HelloApplication.setUtilizatorAutentificat(utilizator);
            Stage stage = (Stage) button.getScene().getWindow();
            SceneSwitcher.switchToScene(stage, "Menu.fxml", 500, 500);
        } else {
            showError("Username sau parolă incorecte!");
        }
    }

    @FXML
    protected void onCreateAccountClick() {
        String idUtilizator = usernameCreate.getText();
        String parola = passwordCreate.getText();
        String numeText = nume.getText();
        String prenumeText = prenume.getText();
        String pinText = pin.getText();

        // Validare câmpuri
        if (idUtilizator.isEmpty() || parola.isEmpty() || numeText.isEmpty() || prenumeText.isEmpty() || pinText.isEmpty()) {
            showError("Te rugăm să completezi toate câmpurile!");
            return;
        }

        // Validare PIN (4 cifre numerice)
        if (!pinText.matches("\\d{4}")) {
            showError("PIN-ul trebuie să fie un număr de exact 4 cifre!");
            return;
        }

        // Verificăm dacă utilizatorul există deja
        if (UtilizatorDAO.existaUtilizator(idUtilizator)) {
            showError("Utilizatorul există deja!");
            return;
        }

        // Creăm un utilizator nou (sold inițial 0.0)
        Utilizator utilizator = new Utilizator(parola, numeText, prenumeText, 0.0, pinText);
        utilizator.setIdUtilizator(idUtilizator);

        // Inserăm utilizatorul în baza de date
        if (UtilizatorDAO.adaugaUtilizator(utilizator)) {
            showError("Cont creat cu succes! Te poți autentifica.");
            // Curățăm câmpurile
            usernameCreate.clear();
            passwordCreate.clear();
            nume.clear();
            prenume.clear();
            pin.clear();
            // Ascundem panoul
            ShowHide.hidePane(panel_aut);
        } else {
            showError("Eroare la crearea contului. Încearcă din nou.");
        }
    }

    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        } else {
            System.err.println(message);
        }
    }

    @FXML
    protected void onAutentificareCLick(ActionEvent event) {
        if (!panel_aut.isVisible()) {
            ShowHide.showPane(panel_aut);
        }

        if (panel_aut.isVisible() && !Objects.equals(actiune, ((Button) event.getSource()).getText())) {
            panel_aut.setTranslateY(516);
            ShowHide.showPane(panel_aut);
        }

        actiune = ((Button) event.getSource()).getText();
        System.out.println(actiune);
    }

    @FXML
    private void onClickClose() {
        ShowHide.hidePane(panel_aut);
        errorLabel.setVisible(false); // Ascundem mesajul de eroare la închidere
    }
}