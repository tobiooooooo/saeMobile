package com.example.sae;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class selection_assos_activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AssociationAdapter adapter;
    private List<Association> associations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selection_assos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        associations = loadAssociationsFromJson();

        // 🔹 Associer l'Adapter avec le RecyclerView
        adapter = new AssociationAdapter(associations,this);
        recyclerView.setAdapter(adapter);

        androidx.appcompat.widget.SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // Pas besoin de gérer la soumission du texte
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText); // Appelle la méthode de filtrage
                return true;
            }
        });

    }



    private List<Association> loadAssociationsFromJson()
    {
        try {
            //on ouvre le JSON
            InputStream is = getAssets().open("associations.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer,"UTF-8");

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Association>>(){}.getType();
            return gson.fromJson(json, listType);

        }catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}