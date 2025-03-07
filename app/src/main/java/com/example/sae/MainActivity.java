package com.example.sae;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView logoImageView = findViewById(R.id.imageView); // ID du logo

        logoImageView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Ferme la page actuelle
        });


        Button discoverBtn = (Button) findViewById(R.id.discover_btn);
        Button logInBtn = (Button) findViewById(R.id.log_in_btn);
        Button scanQRButton = findViewById(R.id.btn_qr_code);
         DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        ImageButton menuButton = findViewById(R.id.menu_button);
        LinearLayout loginRedirectLayout = findViewById(R.id.login_redirect_layout);
        Button loginRedirectButton = findViewById(R.id.btn_login_redirect);

        navigationView.setNavigationItemSelectedListener(this);

        Button logoutButton = findViewById(R.id.btn_logout);

        logoutButton.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", false); // ✅ Déconnecte l'utilisateur
            editor.apply();

            Toast.makeText(MainActivity.this, "Déconnecté avec succès", Toast.LENGTH_SHORT).show();
        });


        discoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, selection_assos_activity.class);
                startActivity(intent);
            }
        });


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginAdmin.class);
                startActivity(intent);
            }
        });

        // Redirection vers LoginActivity lorsque l'on appuie sur le bouton
        loginRedirectButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });


        scanQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanQRActivity.class);
                startActivity(intent);
            }
        });





    }
    //fonction qui gère les boutons du menu burger la page settings en commentaire car pas encore faite
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
            }

            // Fermer le menu après un clic
            DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
    }
}