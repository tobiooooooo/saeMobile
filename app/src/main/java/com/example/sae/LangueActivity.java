package com.example.sae;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LangueActivity extends AppCompatActivity {

    private void changerLangue(String codeLangue) {
        Locale locale = new Locale(codeLangue);
        Locale.setDefault(locale);

        Resources res = getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());


        Intent refresh = new Intent(this, MainActivity.class);
        refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(refresh);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langue);

        Button btnFr = findViewById(R.id.btn_fr);
        Button btnEn = findViewById(R.id.btn_en);
        ImageButton btnBack = findViewById(R.id.btn_back_langue);

        btnFr.setOnClickListener(v -> changerLangue("fr"));
        btnEn.setOnClickListener(v -> changerLangue("en"));
        btnBack.setOnClickListener(v -> finish());
    }
}
