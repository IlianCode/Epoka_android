package com.example.epoka_android;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class loginPage extends AppCompatActivity {

    Button button_connexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        button_connexion = findViewById(R.id.button_connexion);

    }
    public void connexionTest(){
        EditText editTextUsername;
        EditText editTextPassword;
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextTextPassword);

        String urlServiceWeb = "http://172.16.170.105/api/connexion/" + editTextUsername.getText() + "/"+ editTextPassword.getText();



    }
    private String getServerDataTexteBrut(String urlString){
        InputStream is = null;
        String ch ="";
        //autoriser les opé sur le thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            URL url = new URL(urlString);
            HttpURLConnection connexion = (HttpURLConnection)url.openConnection();
            connexion.connect();
            is = connexion.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ligne;
            while((ligne = br.readLine()) != null){
                ch =ch + ligne;
            };
        }catch (Exception expt){
        };
        return(ch);
    }
}
