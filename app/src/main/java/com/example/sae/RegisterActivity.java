package com.example.sae;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import android.util.Patterns;

import com.google.android.material.navigation.NavigationView;


public class RegisterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private EditText etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView logoImageView = findViewById(R.id.imageView); // ID du logo

        logoImageView.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Ferme la page actuelle
        });


        etEmail = findViewById(R.id.editTextTextEmailAddress);
        etPassword = findViewById(R.id.editTextTextPassword);
        etConfirmPassword = findViewById(R.id.editTextTextConfirmPassword);
        btnRegister = findViewById(R.id.button_connection);
        Button btnDeleteAccount = findViewById(R.id.button_delete_account);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        btnRegister.setOnClickListener(v -> {
            if (validateInputs()) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String hashedPassword = hashPassword(password);

                // Vérifier si l'utilisateur existe déjà
                if (prefs.contains(email)) {
                    Toast.makeText(RegisterActivity.this, "Cet email est déjà utilisé", Toast.LENGTH_SHORT).show();
                } else {
                    // Enregistrer l'utilisateur avec un mot de passe haché
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(email, hashedPassword);
                    editor.apply();

                    Toast.makeText(RegisterActivity.this, "Inscription réussie, connectez-vous", Toast.LENGTH_SHORT).show();

                    // Rediriger vers la connexion
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


        btnDeleteAccount.setOnClickListener(v -> {
            SharedPreferences preffs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            if (preffs.getAll().isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Aucun compte enregistré.", Toast.LENGTH_SHORT).show();
                return;
            }


            // Récupérer la liste des emails enregistrés
            String[] accountsArray = prefs.getAll().keySet().toArray(new String[0]);

            // Afficher une boîte de dialogue pour choisir quel compte supprimer
            new android.app.AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("Supprimer un compte")
                    .setItems(accountsArray, (dialog, which) -> {
                        String selectedEmail = accountsArray[which];

                        // Supprimer le compte sélectionné
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.remove(selectedEmail);
                        editor.apply();

                        Toast.makeText(RegisterActivity.this, "Compte supprimé : " + selectedEmail, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Annuler", null)
                    .show();
        });



        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));


        Button logInBtn = (Button) findViewById(R.id.log_in_btnn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginAdmin.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateInputs() {
        String email = etEmail.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Veuillez entrer votre email");
            return false;
        }
        if (!isValidEmail(email)) {
            etEmail.setError("Veuillez entrer un email valide");
            return false;
        }
        if (etPassword.getText().toString().trim().length() < 6) {
            etPassword.setError("Mot de passe trop court (min. 6 caractères)");
            return false;
        }
        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            etConfirmPassword.setError("Les mots de passe ne correspondent pas");
            return false;
        }
        return true;
    }


    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hachage du mot de passe", e);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
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
