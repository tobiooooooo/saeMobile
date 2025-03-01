package com.example.sae;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class LoginAdmin extends AppCompatActivity {

    List<String> admin = List.of("greg","theo","lucas","ethan","ali-shan");
    List<String> adminpsw = List.of("gregmdp","theomdp","lucasmdp","ethanmdp","ali-shanmdp");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
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


    }
}