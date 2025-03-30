package com.example.sae;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Gère le clic sur le bouton retour
        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> finish()); // Ferme l'activité et revient à la précédente
    }
}