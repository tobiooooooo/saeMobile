package com.example.sae;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class TableauDeBordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tableau_de_bord);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnStatsDons = (Button) findViewById(R.id.btn_stats_dons);
        btnStatsDons.setOnClickListener(v -> {
            Intent intent = new Intent(TableauDeBordActivity.this, StatsDons.class);
            startActivity(intent);
        });
        loadTransactionsFromJson1();
        loadTransactionsFromJson2();
    }

    private void loadTransactionsFromJson1() {
        TextView textViewTotal = findViewById(R.id.total_dons);
        TextView textViewTotalMois = findViewById(R.id.dons_recus_ce_mois_ci);
        TextView textViewNbrTotal = findViewById(R.id.nombre_total_de_dons);

        double sommeDonsTotal = 0.;
        double sommeDonsMois = 0.;
        int cptDons = 0;

        String jsonString = JsonReader.loadJSONFromRaw(this, R.raw.dons);
        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Transaction t = Transaction.fromJson(jsonObject);
                    sommeDonsTotal += t.getMontant();
                    if (t.getDateAsString().contains("/03/")) {
                        sommeDonsMois += t.getMontant();
                    }
                    ++cptDons;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        textViewTotal.setText(sommeDonsTotal + "€");
        textViewTotalMois.setText(sommeDonsMois + "€");
        textViewNbrTotal.setText(cptDons + "");
    }

    private void loadTransactionsFromJson2() {
        TextView textViewDernierDonateur = findViewById(R.id.dernier_donateur);
        TextView textViewDernierMontant = findViewById(R.id.montant_du_dernier_don);
        TextView textViewDerniereDate = findViewById(R.id.date_dernier_don);
        TextView textViewDernierType = findViewById(R.id.type_du_dernier_don);
        String jsonString = JsonReader.loadJSONFromRaw(this, R.raw.dons);
        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                Transaction t = Transaction.fromJson(jsonObject);
                textViewDernierDonateur.setText(t.getNom());
                textViewDernierMontant.setText(t.getMontant() + "€");
                textViewDerniereDate.setText(t.getDateAsString());
                textViewDernierType.setText(t.isRecurrent() ? "Récurrent" : "Unique");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}