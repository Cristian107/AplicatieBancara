package com.example.aplicatiebancara1;

public class Tranzactie {
    private int idTranzactie;         // Generat automat prin secvență și trigger
    private String idUtilizator;      // Expeditorul (referință la utilizatori)
    private String idDestinatar;      // Destinatarul (referință la utilizatori)
    private double valoareTranzactie; // Suma transferată
    private String detaliiTranzactie; // Descrierea tranzacției

    // Constructor complet
    public Tranzactie(int idTranzactie, String idUtilizator, String idDestinatar,
                      double valoareTranzactie, String detaliiTranzactie) {
        this.idTranzactie = idTranzactie;
        this.idUtilizator = idUtilizator;
        this.idDestinatar = idDestinatar;
        this.valoareTranzactie = valoareTranzactie;
        this.detaliiTranzactie = detaliiTranzactie;
    }

    // Constructor fără ID (pentru inserare nouă - ID-ul va fi generat de trigger)
    public Tranzactie(String idUtilizator, String idDestinatar,
                      double valoareTranzactie, String detaliiTranzactie) {
        this.idUtilizator = idUtilizator;
        this.idDestinatar = idDestinatar;
        this.valoareTranzactie = valoareTranzactie;
        this.detaliiTranzactie = detaliiTranzactie;
    }

    // Getteri și setteri
    public int getIdTranzactie() {
        return idTranzactie;
    }

    public void setIdTranzactie(int idTranzactie) {
        this.idTranzactie = idTranzactie;
    }

    public String getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(String idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public String getIdDestinatar() {
        return idDestinatar;
    }

    public void setIdDestinatar(String idDestinatar) {
        this.idDestinatar = idDestinatar;
    }

    public double getValoareTranzactie() {
        return valoareTranzactie;
    }

    public void setValoareTranzactie(double valoareTranzactie) {
        this.valoareTranzactie = valoareTranzactie;
    }

    public String getDetaliiTranzactie() {
        return detaliiTranzactie;
    }

    public void setDetaliiTranzactie(String detaliiTranzactie) {
        this.detaliiTranzactie = detaliiTranzactie;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "idTranzactie=" + idTranzactie +
                ", idUtilizator='" + idUtilizator + '\'' +
                ", idDestinatar='" + idDestinatar + '\'' +
                ", valoareTranzactie=" + valoareTranzactie +
                ", detaliiTranzactie='" + detaliiTranzactie + '\'' +
                '}';
    }
}