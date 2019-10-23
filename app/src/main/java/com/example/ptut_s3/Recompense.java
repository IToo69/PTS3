package com.example.ptut_s3;

public class Recompense {
    private int prix;
    private String nom;

    public Recompense(int prix, String nom) {
        this.prix = prix;
        this.nom = nom;
    }

    public Recompense(){}

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
