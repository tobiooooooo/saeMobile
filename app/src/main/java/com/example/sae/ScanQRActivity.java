package com.example.sae;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.CaptureActivity;

public class ScanQRActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String selectedAssociation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        // ✅ Affichage du nom de l'association
        TextView assoNameTextView = findViewById(R.id.tv_asso_name);
        selectedAssociation = getIntent().getStringExtra("association");
        if (selectedAssociation != null) {
            assoNameTextView.setText("Association : " + selectedAssociation);
        }

        // ✅ Lancer le scan QR
//        Button scanQRButton = findViewById(R.id.scan_qr_button);
//        scanQRButton.setOnClickListener(v -> startQRScanner());

        Button scanQRButton = findViewById(R.id.scan_qr_button_retour);
        scanQRButton.setOnClickListener(v -> finish());



        // ✅ Retour accueil via logo
        ImageView logoImageView = findViewById(R.id.imageView);
        logoImageView.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        // ✅ Menu drawer (navigation latérale)
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));

        // ✅ Accès aux paramètres
        ImageButton settingsIcon = findViewById(R.id.settings_icon);
        settingsIcon.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        ImageView qrCodeImage = findViewById(R.id.qr_code_image);
        try {
            String content = assoNameTextView.getText().toString();
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            qrCodeImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    private void startQRScanner() {
//        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setPrompt("Scannez un QR Code");
//        integrator.setBeepEnabled(true);
//        integrator.setOrientationLocked(false);
//        integrator.setCaptureActivity(CaptureActivity.class);
//        integrator.initiateScan();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!QRHelper.handleQRResult(this, requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
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
}
