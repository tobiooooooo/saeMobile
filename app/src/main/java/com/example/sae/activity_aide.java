package com.example.sae;

import android.content.Context;
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

        prepareData(this);

        HelpAdapter helpAdapter = new HelpAdapter(this, listHeaders, listChildren);
        expandableListView.setAdapter(helpAdapter);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));


        Button logInBtn = (Button) findViewById(R.id.log_in_btn);

//        logInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity_aide.this, LoginAdmin.class);
//                startActivity(intent);
//            }
//
//        });
        ImageButton settingsIcon = findViewById(R.id.settings_icon);

        settingsIcon.setOnClickListener(v -> {
            Intent intent = new Intent(activity_aide.this, SettingsActivity.class);
            startActivity(intent);
        });

    }

    private void prepareData(Context context)
    {
        listHeaders = new ArrayList<>();
        listChildren = new HashMap<>();

        listHeaders.add(context.getString(R.string.faq_cat_dons));

        List<String[]> donsEtTransactions = new ArrayList<>();
        donsEtTransactions.add(new String[]{
                context.getString(R.string.faq_don_comment),
                context.getString(R.string.faq_don_comment_desc)
        });
        donsEtTransactions.add(new String[]{
                context.getString(R.string.faq_paiement_moyens),
                context.getString(R.string.faq_paiement_moyens_desc)
        });
        donsEtTransactions.add(new String[]{
                context.getString(R.string.faq_don_securise),
                context.getString(R.string.faq_don_securise_desc)
        });
        donsEtTransactions.add(new String[]{
                context.getString(R.string.faq_recu_fiscal),
                context.getString(R.string.faq_recu_fiscal_desc)
        });


        listHeaders.add(context.getString(R.string.faq_cat_technique));

        List<String[]> problemesTechniques = new ArrayList<>();
        problemesTechniques.add(new String[]{
                context.getString(R.string.faq_bug_plante),
                context.getString(R.string.faq_bug_plante_desc)
        });
        problemesTechniques.add(new String[]{
                context.getString(R.string.faq_bug_notifications),
                context.getString(R.string.faq_bug_notifications_desc)
        });
        problemesTechniques.add(new String[]{
                context.getString(R.string.faq_bug_don),
                context.getString(R.string.faq_bug_don_desc)
        });


        listHeaders.add(context.getString(R.string.faq_cat_appli));

        List<String[]> aPropos = new ArrayList<>();
        aPropos.add(new String[]{
                context.getString(R.string.faq_appli_but),
                context.getString(R.string.faq_appli_but_desc)
        });
        aPropos.add(new String[]{
                context.getString(R.string.faq_appli_gratuite),
                context.getString(R.string.faq_appli_gratuite_desc)
        });
        aPropos.add(new String[]{
                context.getString(R.string.faq_appli_selection),
                context.getString(R.string.faq_appli_selection_desc)
        });


        listHeaders.add(context.getString(R.string.faq_cat_autre));

        List<String[]> autreQuestions = new ArrayList<>();
        autreQuestions.add(new String[]{
                context.getString(R.string.faq_access_handicap),
                context.getString(R.string.faq_access_handicap_desc)
        });
        autreQuestions.add(new String[]{
                context.getString(R.string.faq_don_handicap),
                context.getString(R.string.faq_don_handicap_desc)
        });

        listChildren.put(listHeaders.get(0), donsEtTransactions);
        listChildren.put(listHeaders.get(1), problemesTechniques);
        listChildren.put(listHeaders.get(2), aPropos);
        listChildren.put(listHeaders.get(3), autreQuestions);

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!QRHelper.handleQRResult(this, requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}