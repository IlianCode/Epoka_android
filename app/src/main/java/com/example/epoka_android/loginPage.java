package com.example.epoka_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class loginPage extends AppCompatActivity {

    Button button_connexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        button_connexion = findViewById(R.id.button_connexion);

    }



}
