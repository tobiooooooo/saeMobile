package com.example.sae;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.List;

public class activity_aide extends AppCompatActivity {

    private ExpandableListView expandableListView;
//    private HelpAdapter helpAdapter;
    private List<String> listHeaders;
    private HashMap<String,List<String>> listchildren;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aide);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        expandableListView = findViewById(R.id.expListView);
//
//        prepareData();
//
//        listAdapter = new HelpAdapter(this, listHeaders, listchildren);
//        expandableListView.setAdapter(listAdapter);
//
    }

    private void prepareData()
    {

    }
}