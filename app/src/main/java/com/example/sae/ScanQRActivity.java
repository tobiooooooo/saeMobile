package com.example.sae;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

public class ScanQRActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String selectedAssociation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scan_qr);

        // ✅ Affichage du nom de l'association dans le header
        TextView assoNameTextView = findViewById(R.id.tv_asso_name);
        selectedAssociation = getIntent().getStringExtra("association");
        if (selectedAssociation != null) {
            assoNameTextView.setText("Association : " + selectedAssociation);
        }

        // ✅ Bouton scan QR
        Button scanQRButton = findViewById(R.id.scan_qr_button);
        scanQRButton.setOnClickListener(v -> startQRScanner());

        // ✅ Bouton logo => retourne à l'accueil
        ImageView logoImageView = findViewById(R.id.imageView);
        logoImageView.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        // ✅ Menu drawer
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));

        // ✅ Icône des paramètres
        ImageButton settingsIcon = findViewById(R.id.settings_icon);
        settingsIcon.setOnClickListener(v -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });
    }

    private void startQRScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scannez un QR Code");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.initiateScan();
    }

    // ✅ Résultat du scan
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            Intent intent = new Intent(this, DonationActivity.class);
            intent.putExtra("qrData", result.getContents()); // données du QR
            intent.putExtra("association", selectedAssociation); // on garde aussi le nom
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_associations) {
            startActivity(new Intent(this, selection_assos_activity.class));
        } else if (id == R.id.nav_aideFAQ) {
            startActivity(new Intent(this, activity_aide.class));
        } else if (id == R.id.nav_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }
}
