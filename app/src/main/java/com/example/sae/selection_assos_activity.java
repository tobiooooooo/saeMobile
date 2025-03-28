package com.example.sae;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.util.List;

public class selection_assos_activity extends AppCompatActivity implements OnAssociationClickListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private AssociationAdapter adapter;
    private List<Association> associations;

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


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        associations = loadAssociationsFromJson();
        adapter = new AssociationAdapter(associations, this, this);
        recyclerView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // on s’en fout du "submit", on veut live filter
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText); // 🔥 voilà ce qu’il manquait
                return true;
            }
        });

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));


        Button logInBtn = (Button) findViewById(R.id.log_in_btn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(selection_assos_activity.this, LoginAdmin.class);
                startActivity(intent);
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
        } else if (id == R.id.nav_donation) {
            startActivity(new Intent(this, DonationActivity.class));
        } else if (id == R.id.nav_associations) {
            startActivity(new Intent(this, selection_assos_activity.class));
        } else if (id == R.id.nav_qr) {
            startActivity(new Intent(this, ScanQRActivity.class));
//            } else if (id == R.id.nav_settings) {
//                startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_aideFAQ) {
            startActivity(new Intent(this, activity_aide.class));
        }else if (id == R.id.nav_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }

        // Fermer le menu après un clic
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.END);

        return true;
    }

}
