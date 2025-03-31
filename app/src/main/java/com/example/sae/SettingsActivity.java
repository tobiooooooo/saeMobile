package com.example.sae;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> finish());


        LinearLayout langueParam = findViewById(R.id.langue_param);
        langueParam.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, LangueActivity.class);
            startActivity(intent);
        });
        LinearLayout monCompteParam = findViewById(R.id.moncompte_param);
        monCompteParam.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class); // remplace par le vrai nom de ta classe
            startActivity(intent);
        });
        LinearLayout donneesParam = findViewById(R.id.donnees_param);
        donneesParam.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, DonneesConfidentialiteActivity.class);
            startActivity(intent);
        });
        LinearLayout assistanceParam = findViewById(R.id.assistance_param);
        assistanceParam.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, activity_aide.class);
            startActivity(intent);
        });
        LinearLayout apparenceParam = findViewById(R.id.apparence_param);
        apparenceParam.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, ApparenceActivity.class);
            startActivity(intent);
        });
    }
}
