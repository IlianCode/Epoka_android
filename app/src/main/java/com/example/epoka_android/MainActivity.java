package com.example.epoka_android;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView tvDate;
    TextView tvDate2;
    DatePickerDialog.OnDateSetListener setListener;

    //initialisation des variable de spinner
    Spinner spinner;
    TextView tvSpinner;
    TextView textView6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDate = findViewById(R.id.tv_date);
        tvDate2 = findViewById(R.id.tv_date2);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                tvDate.setText(date);
            }
        };
        tvDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog= new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        tvDate2.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //  asignation des variables du spinner
        spinner=findViewById(R.id.spinner);
        tvSpinner = findViewById(R.id.tvSpinner);
        //tableau pour le spinner
        ArrayList<String> listeCommune = new ArrayList<>();
        //appel de l'api

        String url = "https://jsonplaceholder.typicode.com/posts";
        JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String title = null;

                    try {
                        title = response.getJSONObject("").getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listeCommune.add(title);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listeCommune.add("error");
                Log.d("TAG", error.toString());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(JsonObjectRequest);




        //ajout de commune
        listeCommune.add("Choisir commune");
        listeCommune.add("Bourg-en-Bresse");
        listeCommune.add("Saint-Denis-lès-Bourg");
        listeCommune.add("Montmerle-sur-Saône");
        listeCommune.add("Guéreins");
        listeCommune.add("Montceaux");
        listeCommune.add("Genouilleux");

        //set adaptater :
        spinner.setAdapter(new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,listeCommune));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    Toast.makeText(getApplicationContext(),
                            "Veuillez choisir une commune", Toast.LENGTH_SHORT).show();
                    String ewi ="";
                    tvSpinner.setText(ewi);
                }else {
                     //recup la valeur choisie
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}