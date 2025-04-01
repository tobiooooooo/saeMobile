package com.example.sae;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Transaction {
    private String nom;
    private double montant;
    private Date date;
    private boolean recurrent;

    // Format de la date attendue (12/08/25 → dd/MM/yy)
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);

    public Transaction(String nom, double montant, String dateStr, boolean recurrent) {
        this.nom = nom;
        this.montant = montant;
        this.recurrent = recurrent;

        try {
            this.date = dateFormat.parse(dateStr); // Conversion String → Date
        } catch (ParseException e) {
            e.printStackTrace();
            this.date = null; // En cas d'erreur, éviter une valeur incorrecte
        }
    }

    public String getNom() { return nom; }
    public double getMontant() { return montant; }
    public Date getDate() { return date; } // Renvoie un objet Date
    public boolean isRecurrent() { return recurrent; }

    // Convertir un objet JSON en Transaction
    public static Transaction fromJson(JSONObject jsonObject) throws JSONException {
        return new Transaction(
                jsonObject.getString("nom"),
                jsonObject.getDouble("montant"),
                jsonObject.getString("date"),
                jsonObject.getBoolean("recurrent")
        );
    }

    public String getDateAsString() {
        return dateFormat.format(date);
    }
}
