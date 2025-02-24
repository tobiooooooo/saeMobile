package com.example.sae;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DonationActivity extends AppCompatActivity {

    private EditText editNom, editPrenom, editMail, editMontant;
    private Spinner associationSpinner;
    private CheckBox donRecurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_acivity);

        // Initialiser les champs Contact
        editNom = findViewById(R.id.input_nom);
        editPrenom = findViewById(R.id.input_prenom);
        editMail = findViewById(R.id.input_email);
        // Initialiser les champs Donation
        associationSpinner = findViewById(R.id.association_spinner);
        editMontant = findViewById(R.id.input_montant_don);
        donRecurrent = findViewById(R.id.checkbox_don_recurrent);

        // Bouton "Next"
        Button nextButton = findViewById(R.id.btn_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    // Rediriger si tout est rempli
                    Intent intent = new Intent(DonationActivity.this, DonationActivity2.class);
                    startActivity(intent);
                } else {
                    // Message d'erreur si des champs sont vides
                    Toast.makeText(DonationActivity.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Fonction de validation des champs
    private boolean validateInputs() {
        return !TextUtils.isEmpty(editNom.getText().toString()) &&
                !TextUtils.isEmpty(editPrenom.getText().toString()) &&
                !TextUtils.isEmpty(editMail.getText().toString()) &&
                !TextUtils.isEmpty(editMontant.getText().toString());
    }
}
