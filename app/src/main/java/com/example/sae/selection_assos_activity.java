package com.example.sae;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class selection_assos_activity extends AppCompatActivity implements OnAssociationClickListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private AssociationAdapter adapter;
    private List<Association> associations;
    private Spinner spinnerCategories;
    private ArrayAdapter<String> spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selection_assos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView logoImageView = findViewById(R.id.imageView); // ID du logo

        logoImageView.setOnClickListener(v -> {
            Intent intent = new Intent(selection_assos_activity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Ferme la page actuelle
        });
        ImageButton settingsIcon = findViewById(R.id.settings_icon);

        settingsIcon.setOnClickListener(v -> {
            Intent intent = new Intent(selection_assos_activity.this, SettingsActivity.class);
            startActivity(intent);
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        associations = loadAssociationsFromJson();
        adapter = new AssociationAdapter(new ArrayList<>(associations), this, this);
        recyclerView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; //sert a r
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (spinnerCategories.getSelectedItemPosition() != 0) {
                    spinnerCategories.setSelection(0); // Remet sur "Toutes les catégories"
                }
                adapter.filter(newText);
                return true;
            }
        });


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));


        Button logInBtn = (Button) findViewById(R.id.log_in_btn);

//        logInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(selection_assos_activity.this, LoginAdmin.class);
//                startActivity(intent);
//            }
//        });

        spinnerCategories = findViewById(R.id.spinnerCategories);

        String[] categories = {
                getString(R.string.all_categories),
                getString(R.string.handicap),
                getString(R.string.mental_health),
                getString(R.string.chronic_rare_diseases),
                getString(R.string.social_support),
                getString(R.string.addictions),
                getString(R.string.cardio_donation),
                getString(R.string.cancer_support)
        };


        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(spinnerAdapter);

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categorieSelectionnee = categories[position];
                filterAssociationsByCategory(categorieSelectionnee);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Rien à faire ici
            }
        });

    }

    private List<Association> loadAssociationsFromJson() {
        try {
            InputStream is = getAssets().open("associations.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Association>>() {}.getType();
            return gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onAssociationClick(String associationName) {
        Intent intent = new Intent(this, DonationActivity.class);
        intent.putExtra("association", associationName);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_associations) {
            startActivity(new Intent(this, selection_assos_activity.class));
        } else if (id == R.id.nav_qr) {
            QRHelper.startQRScanner(this);
        }
        else if (id == R.id.nav_aideFAQ) {
            startActivity(new Intent(this, activity_aide.class));
        }else if (id == R.id.nav_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }

        // Fermer le menu après un clic
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.END);

        return true;
    }
    private void filterAssociationsByCategory(String categorie) {
        if (categorie.equals("Toutes les catégories")) {
            adapter.setAssociations(new ArrayList<>(associations));
            return;
        }

        List<String> associationsCategorie = getAssociationsForCategory(categorie);
        List<Association> filteredList = new ArrayList<>();

        for (Association asso : associations) {
            if (associationsCategorie.contains(asso.getNom())) {
                filteredList.add(asso);
            }
        }

        adapter.setAssociations(filteredList);
    }

    // Correspondance entre catégorie et associations
    private List<String> getAssociationsForCategory(String categorie) {
        if (categorie.equals(getString(R.string.all_categories))) {
            List<String> toutesLesAssos = new ArrayList<>();
            for (Association asso : associations) {
                toutesLesAssos.add(asso.getNom());
            }
            return toutesLesAssos;
        } else if (categorie.equals(getString(R.string.handicap))) {
            return Arrays.asList("A.M.I nationale", "ADEPA", "AFH", "AFM-Téléthon", "APAHJ",
                    "APF France handicap,", "ASBH", "EFAPPE Epilepsies", "FFSA", "UNAFTC", "Vaincre la Mucoviscidose,");
        } else if (categorie.equals(getString(R.string.mental_health))) {
            return Arrays.asList("Advocacy France", "ARGOS 2001", "FNAPSY", "France Dépression,", "Schizo-Oui,", "UNAFAM");
        } else if (categorie.equals(getString(R.string.chronic_rare_diseases))) {
            return Arrays.asList("AAAVAM", "Actions Traitements", "AFA Crohn RCH France", "AFDE", "AFDOC",
                    "AFGS", "AFPric", "AFRH", "AFS", "AFSA", "AFSEP", "AFVD", "AFVS", "AINP", "Alliance Maladies Rares,",
                    "AMADYS", "AMALYSTE", "ANDAR", "ARSLA", "ASF", "ASFC", "Autisme France,", "E3M", "EndoFrance,", "ENDOmind,",
                    "Fibromyalgie SOS,", "France Alzheimer,", "France Lyme,", "France Parkinson,", "Fédération française des Diabétiques,",
                    "Hypersupers TDAH France,", "Marfans,", "PRIARTEM", "Réseau D.E.S. France,", "SOS Hépatites", "Transhépate", "Épilepsie-France,");
        } else if (categorie.equals(getString(R.string.social_support))) {
            return Arrays.asList("ADMD", "AVIAM", "CADUS", "CLCV", "CNAFAL", "CNAFC", "CNAO",
                    "Familles de France,", "Familles Rurales,", "FNAR", "FNATH", "La CSF", "Le LIEN", "Le Planning Familial,",
                    "Les petits frères des pauvres,", "Réseau Environnement Santé,", "UFAL", "UFC-Que Choisir,", "UNAF", "VMEH");
        } else if (categorie.equals(getString(R.string.addictions))) {
            return Arrays.asList("Addictions Alcool Vie Libre,", "Alcool Ecoute Joie & Santé,", "Entraid’Addict",
                    "FNAS", "La Croix bleue");
        } else if (categorie.equals(getString(R.string.cardio_donation))) {
            return Arrays.asList("APODEC", "FFCM", "FFDSB", "FGCP", "France Rein,", "Renaloo");
        } else if (categorie.equals(getString(R.string.cancer_support))) {
            return Arrays.asList("La Ligue contre le cancer,", "Vivre comme avant", "JALMALV", "Association des Brûlés de France,");
        } else {
            return new ArrayList<>();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!QRHelper.handleQRResult(this, requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}