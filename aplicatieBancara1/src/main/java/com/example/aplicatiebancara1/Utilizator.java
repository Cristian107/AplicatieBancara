package com.example.aplicatiebancara1;

public class Utilizator {
    private String idUtilizator;
    private String parola;
    private String nume;
    private String prenume;
    private double sold;
    private String pin;  // PIN unic adăugat conform cerințelor

    // Constructor complet
    public Utilizator(String idUtilizator, String parola, String nume, String prenume, double sold, String pin) {
        this.idUtilizator = idUtilizator;
        this.parola = parola;
        this.nume = nume;
        this.prenume = prenume;
        this.sold = sold;
        this.pin = pin;
    }

    // Constructor fără ID (pentru crearea unui utilizator nou)
    public Utilizator(String parola, String nume, String prenume, double sold, String pin) {
        this.parola = parola;
        this.nume = nume;
        this.prenume = prenume;
        this.sold = sold;
        this.pin = pin;
    }

    // Getteri și setteri
    public String getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(String idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "idUtilizator='" + idUtilizator + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", sold=" + sold +
                '}';
    }
}