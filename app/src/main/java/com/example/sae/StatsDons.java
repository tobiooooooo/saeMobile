package com.example.sae;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StatsDons extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stats_dons);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.tableau_dons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Charger les transactions
        transactions = loadTransactionsFromJson();
        adapter = new TransactionAdapter(transactions);
        recyclerView.setAdapter(adapter);
    }

    private List<Transaction> loadTransactionsFromJson() {
        TextView textViewTotal = findViewById(R.id.total_dons);
        double sommeDons = 0.;
        List<Transaction> transactionList = new ArrayList<>();
        String jsonString = JsonReader.loadJSONFromRaw(this, R.raw.dons);

        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    transactionList.add(Transaction.fromJson(jsonObject));
                    sommeDons += Transaction.fromJson(jsonObject).getMontant();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        textViewTotal.setText(sommeDons + "€");
        return transactionList;
    }
}