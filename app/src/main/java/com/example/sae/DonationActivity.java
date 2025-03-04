package com.example.sae;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DonationActivity extends AppCompatActivity {

    private EditText editNom, editPrenom, editMontant;
    private CheckBox donRecurrent;
    private Button nextButton;

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

        ImageView logoImageView = findViewById(R.id.imageView);
        logoImageView.setOnClickListener(v -> {
            Intent intent = new Intent(DonationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        TextView tvAssociation = findViewById(R.id.tv_association);
        editNom = findViewById(R.id.input_nom);
        editPrenom = findViewById(R.id.input_prenom);
        editMontant = findViewById(R.id.input_montant_don);
        donRecurrent = findViewById(R.id.checkbox_don_recurrent);
        nextButton = findViewById(R.id.btn_next);

        nextButton.setEnabled(false);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        donRecurrent.setOnClickListener(v -> {
            if (!isLoggedIn) {
                Intent intent = new Intent(DonationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        String association = getIntent().getStringExtra("association");
        if (association != null) {
            tvAssociation.setText("Association : " + association);
        }

        View.OnFocusChangeListener inputWatcher = (v, hasFocus) -> validateInputs();
        editNom.setOnFocusChangeListener(inputWatcher);
        editPrenom.setOnFocusChangeListener(inputWatcher);
        editMontant.setOnFocusChangeListener(inputWatcher);

        nextButton.setOnClickListener(v -> {
            if (validateInputs()) {
                Intent intent = new Intent(DonationActivity.this, DonationActivity2.class);
                intent.putExtra("association", association);
                intent.putExtra("montant", editMontant.getText().toString());

                // ✅ Transmettre le type de don (Récurrent ou Unique)
                boolean isRecurrent = donRecurrent.isChecked();
                intent.putExtra("don_type", isRecurrent ? "Récurrent" : "Unique");

                startActivity(intent);
            }
        });
    }

    private boolean validateInputs() {
        String montantStr = editMontant.getText().toString().trim();

        if (montantStr.isEmpty()) {
            editMontant.setError("Veuillez entrer un montant");
            return false;
        }

        nextButton.setEnabled(true);
        return true;
    }
}
