package com.example.sae;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


        //données en "dur" créés tant qu'on a pas le JSON pour test
        associations = new ArrayList<>();
        associations.add(new Association("CADUS", "Conseil Aide & Défense des Usagers de la Santé", "cadus_logo"));
        associations.add(new Association("UAFLMV", "Union des associations françaises de laryngectomisés et mutilés de la voix", "uaflmv_logo"));
        associations.add(new Association("A.M.I", "Association nationale de défense des malades, invalides et handicapés", "ami_logo"));
        associations.add(new Association("Endo France", "Association de lutte contre l’endométriose", "endofrance_logo"));
        associations.add(new Association("UFC-Que Choisir", "UFC-Que Choisir", "ufc_quechoisir_logo"));

        // 🔹 Associer l'Adapter avec le RecyclerView
        adapter = new AssociationAdapter(associations,this);
        recyclerView.setAdapter(adapter);
    }
}