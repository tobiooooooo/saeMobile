package com.example.sae;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.journeyapps.barcodescanner.CaptureActivity;

public class ScanQRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scan_qr);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button logInBtn = (Button) findViewById(R.id.log_in_btnn);


        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanQRActivity.this, Login.class);
                startActivity(intent);
            }
        });


        Button scanQRButton;
        scanQRButton = findViewById(R.id.scan_qr_button);
        scanQRButton.setOnClickListener(v -> startQRScanner());

    }


    private void startQRScanner() {

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scannez un QR Code");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            // Redirection après scan réussi
            Intent intent = new Intent(this, DonationActivity.class);
            intent.putExtra("qrData", result.getContents());
            startActivity(intent);
        }
    }


}
