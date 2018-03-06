package com.example.pierre.tp_pplessy;

public class EcolePrimaire {
    private String nom;
    private String addresse;
    private String nbEleve;
    private String status;
    private String latitude;
    private String longitude;

    public EcolePrimaire(){};

    public EcolePrimaire(String nom, String addresse, String nbEleve, String status, String latitude, String longitude) {
        this.nom = nom;
        this.addresse = addresse;
        this.nbEleve = nbEleve;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getNbEleve() {
        return nbEleve;
    }

    public void setNbEleve(String nbEleve) {
        this.nbEleve = nbEleve;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
