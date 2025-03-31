//package com.example.sae;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.GravityCompat;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import com.google.android.material.navigation.NavigationView;
//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;
//
//import java.util.List;
//
//public class LoginAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
//
//    EditText etEmail, etPassword;
//    Button btnLogin;
//    List<AdminUser> adminUsers;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login_admin);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        Button connectButton = findViewById(R.id.button_connection);
//        EditText emailEditText = findViewById(R.id.editTextTextEmailAddress);
//        EditText pswEditText = findViewById(R.id.editTextTextPassword);
//
//        loadUsers();
//
//        connectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                btnLogin.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        loginUser();
//                    }
//                });
//
//                //    Toast.makeText(getApplicationContext(),"connection failed",Toast.LENGTH_LONG).show();
//            }
//        });
//
//        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.navigation_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        ImageButton menuButton = findViewById(R.id.menu_button);
//        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));
//
//        ImageButton settingsIcon = findViewById(R.id.settings_icon);
//
//        settingsIcon.setOnClickListener(v -> {
//            Intent intent = new Intent(LoginAdmin.this, SettingsActivity.class);
//            startActivity(intent);
//        });
//
//        Button logInBtn = (Button) findViewById(R.id.log_in_btn);
//
//        logInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginAdmin.this, LoginAdmin.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.nav_home) {
//            startActivity(new Intent(this, MainActivity.class));
//        } else if (id == R.id.nav_associations) {
//            startActivity(new Intent(this, selection_assos_activity.class));
//        } else if (id == R.id.nav_qr) {
//            startActivity(new Intent(this, ScanQRActivity.class));
////            } else if (id == R.id.nav_settings) {
////                startActivity(new Intent(this, SettingsActivity.class));
//        } else if (id == R.id.nav_aideFAQ) {
//            startActivity(new Intent(this, activity_aide.class));
//        }else if (id == R.id.nav_register) {
//            startActivity(new Intent(this, RegisterActivity.class));
//        }
//
//
//        // Fermer le menu après un clic
//        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
//        drawerLayout.closeDrawer(GravityCompat.END);
//
//        return true;
//    }
//
//
//    private void loadUsers() {
//        try {
//            String json = JsonReader.loadJSONFromRaw(this, R.raw.users);
//            Gson gson = new Gson();
//            AdminUserList userList = gson.fromJson(json, AdminUserList.class);
//            adminUsers = userList.getAdminUsers();
//        } catch (JsonSyntaxException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Erreur de chargement des utilisateurs", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void loginUser() {
//        String email = etEmail.getText().toString().trim();
//        String password = etPassword.getText().toString().trim();
//
//        if (email.isEmpty() || password.isEmpty()) {
//            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        boolean isValid = false;
//        for (AdminUser user : adminUsers) {
//            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
//                isValid = true;
//                break;
//            }
//        }
//
//        if (isValid) {
//            Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
//        }
//    }
//}