package com.example.sae;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class activity_aide extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ExpandableListView expandableListView;
    private HelpAdapter helpAdapter;
    private List<String> listHeaders;
    private HashMap<String,List<String[]>> listChildren;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aide);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        expandableListView = findViewById(R.id.expListView);

        prepareData();

        HelpAdapter helpAdapter = new HelpAdapter(this, listHeaders, listChildren);
        expandableListView.setAdapter(helpAdapter);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));


        Button logInBtn = (Button) findViewById(R.id.log_in_btn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_aide.this, LoginAdmin.class);
                startActivity(intent);
            }

        });
        ImageButton settingsIcon = findViewById(R.id.settings_icon);

        settingsIcon.setOnClickListener(v -> {
            Intent intent = new Intent(activity_aide.this, SettingsActivity.class);
            startActivity(intent);
        });

    }

    private void prepareData()
    {
        listHeaders = new ArrayList<>();
        listChildren = new HashMap<>();

        listHeaders.add("Dons et transactions");

        List<String[]> donsEtTransactions = new ArrayList<>();
        donsEtTransactions.add(new String[]{"Comment faire un don ?", "Choisissez une association, puis cliquez sur 'Faire un don' et suivez les instructions."});
        donsEtTransactions.add(new String[]{"Quels moyens de paiement sont acceptés ?", "Vous pouvez payer par carte bancaire, PayPal ou virement."});
        donsEtTransactions.add(new String[]{"Est-ce que mon don est sécurisé ?", "Oui, nous utilisons un système de paiement sécurisé conforme aux normes PCI-DSS."});
        donsEtTransactions.add(new String[]{"Puis-je obtenir un reçu fiscal ?", "Oui, un reçu est automatiquement généré si l'association est habilitée à en délivrer."});

        listHeaders.add("Compte utilisateur");

        List<String[]> compteUtilisateur = new ArrayList<>();
        compteUtilisateur.add(new String[]{"Comment créer un compte ?", "Cliquez sur 'S'inscrire' depuis l'écran d'accueil et remplissez le formulaire."});
        compteUtilisateur.add(new String[]{"J'ai oublié mon mot de passe, que faire ?", "Utilisez la fonction 'Mot de passe oublié' sur l'écran de connexion."});
        compteUtilisateur.add(new String[]{"Puis-je modifier mes informations personnelles ?", "Oui, dans l'onglet 'Mon compte' vous pouvez modifier vos données."});

        listHeaders.add("Problèmes techniques");

        List<String[]> problemesTechniques = new ArrayList<>();
        problemesTechniques.add(new String[]{"L'application plante, que faire ?", "Essayez de redémarrer l'application ou de la réinstaller. Si le problème persiste, contactez-nous."});
        problemesTechniques.add(new String[]{"Je ne reçois pas les notifications", "Assurez-vous que les notifications sont activées dans les paramètres de votre téléphone."});
        problemesTechniques.add(new String[]{"Un bug est survenu pendant un don", "Le montant ne sera pas débité. Vous pouvez réessayer ou contacter le support si besoin."});

        listHeaders.add("À propos de l’application");

        List<String[]> aPropos = new ArrayList<>();
        aPropos.add(new String[]{"Quel est le but de cette application ?", "Elle permet de faire des dons facilement à des associations engagées."});
        aPropos.add(new String[]{"L’application est-elle gratuite ?", "Oui, elle est 100% gratuite pour les utilisateurs."});
        aPropos.add(new String[]{"Comment sont choisies les associations ?", "Elles sont sélectionnées selon leur sérieux, leur transparence et leur impact social."});

        listHeaders.add("Autre questions");

        List<String[]> autreQuestions = new ArrayList<>();
        autreQuestions.add(new String[]{"L'application est-elle accessible aux personnes handicapées ?", "Oui, elle est compatible avec les lecteurs d’écran et respecte les normes d’accessibilité WCAG."});
        autreQuestions.add(new String[]{"Puis-je faire un don pour une cause liée au handicap ?", "Oui, plusieurs associations partenaires œuvrent pour l’inclusion et le soutien aux personnes en situation de handicap."});

        listChildren.put(listHeaders.get(0), donsEtTransactions);
        listChildren.put(listHeaders.get(1), compteUtilisateur);
        listChildren.put(listHeaders.get(2), problemesTechniques);
        listChildren.put(listHeaders.get(3), aPropos);
        listChildren.put(listHeaders.get(4), autreQuestions);

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
        }
        else if (id == R.id.nav_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }

        // Fermer le menu après un clic
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.END);

        return true;
    }
}