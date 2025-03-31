package com.example.sae;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class DonneesConfidentialiteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donnes);

        ImageButton backBtn = findViewById(R.id.btn_back_donnees);
        backBtn.setOnClickListener(v -> finish());
    }
}
