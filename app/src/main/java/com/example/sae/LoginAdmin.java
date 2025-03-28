package com.example.sae;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.List;

public class LoginAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    List<String> admin = List.of("greg","theo","lucas","ethan","ali-shan");
    List<String> adminpsw = List.of("gregmdp","theomdp","lucasmdp","ethanmdp","ali-shanmdp");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button connectButton = findViewById(R.id.button_connection);
        EditText emailEditText = findViewById(R.id.editTextTextEmailAddress);
        EditText pswEditText = findViewById(R.id.editTextTextPassword);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String psw = pswEditText.getText().toString();
                Integer connection = 0;
                for(int i=0;i<admin.size();i++){
                    if(email.equals(admin.get(i))&&(psw.equals(adminpsw.get(i)))){
                        Toast.makeText(getApplicationContext(),"connection réussi", Toast.LENGTH_LONG).show();
                        connection=1;
                    }
                }
                if (connection==0){
                    Toast.makeText(getApplicationContext(),"connection failed",Toast.LENGTH_LONG).show();
                }
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
                Intent intent = new Intent(LoginAdmin.this, LoginAdmin.class);
                startActivity(intent);
            }
        });
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