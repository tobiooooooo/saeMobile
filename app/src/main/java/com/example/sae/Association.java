package com.example.sae;

public class Association {
    private String nom;
    private String description;
    private String image;

    public Association(String nom, String description, String image) {
        this.nom = nom;
        this.description = description;
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
