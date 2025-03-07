package com.example.sae;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class activity_aide extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private HelpAdapter helpAdapter;
    private List<String> listHeaders;
    private HashMap<String,List<String[]>> listChildren;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aide);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        expandableListView = findViewById(R.id.expListView);

        prepareData();

        HelpAdapter helpAdapter = new HelpAdapter(this, listHeaders, listChildren);
        expandableListView.setAdapter(helpAdapter);

    }

    private void prepareData()
    {
        listHeaders = new ArrayList<>();
        listChildren = new HashMap<>();

        listHeaders.add("Données personnelles");

        List<String[]> questionsReponsesDonnees = new ArrayList<>();
        questionsReponsesDonnees.add(new String[]{"Où vont mes données ?", "Vos données sont stockées sur nos serveurs sécurisés."});
        questionsReponsesDonnees.add(new String[]{"Mes données sont-elles vendues ?", "Non, nous ne vendons jamais vos données."});
        questionsReponsesDonnees.add(new String[]{"Comment puis-je supprimer mes données ?", "Rendez-vous dans les paramètres et sélectionnez 'Supprimer mon compte'."});

        // 🏆 Catégorie : Sécurité
        listHeaders.add("Sécurité");

        List<String[]> questionsReponsesSecurite = new ArrayList<>();
        questionsReponsesSecurite.add(new String[]{"Comment activer la double authentification ?", "Allez dans les paramètres > Sécurité > 2FA et suivez les instructions."});
        questionsReponsesSecurite.add(new String[]{"Que faire en cas de vol de mon compte ?", "Contactez notre support immédiatement pour sécuriser votre compte."});

        // 🔥 Associer les questions à leurs catégories
        listChildren.put(listHeaders.get(0), questionsReponsesDonnees);
        listChildren.put(listHeaders.get(1), questionsReponsesSecurite);
    }
}