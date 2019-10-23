package com.example.ptut_s3;

import java.io.Serializable;

public class Etudiant implements Serializable {
    private int creditDep;
    private String id;
    private String nom;
    private String prenom;

    public Etudiant(int creditDep, String id, String nom, String prenom) {
        this.creditDep = creditDep;
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Etudiant() { }

    public int getCreditDep() {
        return creditDep;
    }

    public void setCreditDep(int creditDep) {
        this.creditDep = creditDep;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
