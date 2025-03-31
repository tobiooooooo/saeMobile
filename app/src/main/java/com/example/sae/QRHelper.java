package com.example.sae;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.io.IOException;

public class QRHelper {

    // Lance le scanner QR depuis une activité
    public static void startQRScanner(Activity activity) {
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setPrompt("Scannez un QR Code");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(com.journeyapps.barcodescanner.CaptureActivity.class);
        integrator.initiateScan();
    }

    // À appeler dans onActivityResult()
    public static boolean handleQRResult(Activity activity, int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            String scannedRaw = result.getContents().trim();

            // 🔎 Supprimer "Association :" s’il est présent
            String scannedName = scannedRaw.replaceFirst("(?i)^association\\s*:\\s*", "").trim();
            String cleanedName = clean(scannedName);

            if (isKnownAssociation(activity, cleanedName)) {
                Intent intent = new Intent(activity, DonationActivity.class);
                intent.putExtra("association", cleanedName);  // 💡 on transmet uniquement "ADMD"
                activity.startActivity(intent);
            } else {
                Toast.makeText(activity, "Association inconnue : " + scannedName, Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return false;
    }


    private static boolean isKnownAssociation(Activity activity, String scannedName) {
        scannedName = clean(scannedName);

        try {
            AssetManager assetManager = activity.getAssets();
            InputStream is = assetManager.open("associations.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Association>>() {}.getType();
            List<Association> associations = gson.fromJson(json, listType);

            for (Association asso : associations) {
                String nomNettoye = clean(asso.getNom());
                if (nomNettoye.equalsIgnoreCase(scannedName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    private static String clean(String input) {
        if (input == null) return "";
        return input
                .trim()
                .replaceAll("[,;:]$", "") // supprime la virgule/point-virgule/2-points finale
                .replaceAll("\\s+", " "); // remplace les multiples espaces par un seul
    }


}
