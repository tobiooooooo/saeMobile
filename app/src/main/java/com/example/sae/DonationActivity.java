package com.example.sae;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DonationActivity extends AppCompatActivity {

    private EditText editNom, editPrenom, editMail, editMontant;

    private CheckBox donRecurrent;
    private Button nextButton;

    private boolean isDonRecurrentChecked = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donation_acivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageView logoImageView = findViewById(R.id.imageView); // ID du logo

        logoImageView.setOnClickListener(v -> {
            Intent intent = new Intent(DonationActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Ferme la page actuelle
        });


        // Initialisation des champs
        TextView tvAssociation = findViewById(R.id.tv_association);
        editNom = findViewById(R.id.input_nom);
        editPrenom = findViewById(R.id.input_prenom);
        editMail = findViewById(R.id.input_email);
        editMontant = findViewById(R.id.input_montant_don);

        donRecurrent = findViewById(R.id.checkbox_don_recurrent);
        nextButton = findViewById(R.id.btn_next);

        // Désactiver le bouton au début
        nextButton.setEnabled(false);


        // Vérifier si on revient de la connexion/inscription
        isDonRecurrentChecked = getIntent().getBooleanExtra("don_recurrent", false);
        donRecurrent.setChecked(isDonRecurrentChecked);


        // Gestion du clic sur "Don récurrent"
        donRecurrent.setOnClickListener(v -> {
            if (!isDonRecurrentChecked) {
                Intent intent = new Intent(DonationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        // Récupérer l'association sélectionnée
        String association = getIntent().getStringExtra("association");
        if (association != null) {
            tvAssociation.setText("Association : " + association);
        }

        // Ajouter un listener pour vérifier les champs
        View.OnFocusChangeListener inputWatcher = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                validateInputs();
            }
        };

        editNom.setOnFocusChangeListener(inputWatcher);
        editPrenom.setOnFocusChangeListener(inputWatcher);
        editMail.setOnFocusChangeListener(inputWatcher);
        editMontant.setOnFocusChangeListener(inputWatcher);

        // Bouton "Next"
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    // Modifier l'intent pour transmettre le montant
                    Intent intent = new Intent(DonationActivity.this, DonationActivity2.class);
                    intent.putExtra("association", association);
                    intent.putExtra("montant", editMontant.getText().toString()); // Passer le montant
                    intent.putExtra("don_recurrent", donRecurrent.isChecked());
                    startActivity(intent);
                }
            }
        });
    }

    // Validation des champs
    private boolean validateInputs() {
        String nom = editNom.getText().toString().trim();
        String prenom = editPrenom.getText().toString().trim();
        String email = editMail.getText().toString().trim();
        String montantStr = editMontant.getText().toString().trim();

        if (nom.isEmpty()) {
            editNom.setError("Veuillez entrer votre nom");
            return false;
        }

        if (prenom.isEmpty()) {
            editPrenom.setError("Veuillez entrer votre prénom");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editMail.setError("Veuillez entrer une adresse e-mail valide");
            return false;
        }

        if (montantStr.isEmpty()) {
            editMontant.setError("Veuillez entrer un montant");
            return false;
        }

        try {
            double montant = Double.parseDouble(montantStr);
            if (montant <= 0) {
                editMontant.setError("Le montant doit être supérieur à 0");
                return false;
            }
        } catch (NumberFormatException e) {
            editMontant.setError("Veuillez entrer un montant valide");
            return false;
        }

        // Activer le bouton si tout est correct
        nextButton.setEnabled(true);
        return true;
    }
}
