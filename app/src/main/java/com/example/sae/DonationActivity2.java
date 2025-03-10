package com.example.sae;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DonationActivity2 extends AppCompatActivity {

    private EditText etNomCarte, etNumeroCarte, etCryptogramme;
    private Button btnNext;
    private TextView montantTextView, tvDonType;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donationa_acivity2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView logoImageView = findViewById(R.id.imageView);
        logoImageView.setOnClickListener(v -> {
            Intent intent = new Intent(DonationActivity2.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        montantTextView = findViewById(R.id.tv_montant);
        etNomCarte = findViewById(R.id.et_nom_carte);
        etNumeroCarte = findViewById(R.id.et_numero_carte);
        etCryptogramme = findViewById(R.id.et_cryptogramme);
        btnNext = findViewById(R.id.btn_next);
        TextView tvAssociationRecap = findViewById(R.id.tv_association_recap);
        tvDonType = findViewById(R.id.tv_don_type); // ✅ Ajouter ici

        Intent intent = getIntent();
        if (intent.hasExtra("montant")) {
            String montant = intent.getStringExtra("montant");
            montantTextView.setText("Montant : " + montant + "€");
        }

        if (intent.hasExtra("association")) {
            String association = intent.getStringExtra("association");
            tvAssociationRecap.setText("Association : " + association);
        }

        // ✅ Récupérer et afficher le type de don
        if (intent.hasExtra("don_type")) {
            String donType = intent.getStringExtra("don_type");
            tvDonType.setText("Type de don : " + donType);
        }

        btnNext.setOnClickListener(v -> {
            if (validateInputs()) {
                Toast.makeText(DonationActivity2.this, "Paiement validé !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs() {
        if (etNomCarte.getText().toString().trim().isEmpty()) {
            etNomCarte.setError("Veuillez entrer le nom sur la carte");
            return false;
        }
        if (etNumeroCarte.getText().toString().trim().length() != 16) {
            etNumeroCarte.setError("Le numéro de carte doit contenir 16 chiffres");
            return false;
        }
        if (etCryptogramme.getText().toString().trim().length() != 3) {
            etCryptogramme.setError("Le cryptogramme doit être de 3 chiffres");
            return false;
        }
        return true;
    }
}
