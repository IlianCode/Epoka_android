package com.example.epoka_android;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends  loginPage {

    TextView tvDate;
    TextView tvDate2;
    DatePickerDialog.OnDateSetListener setListener;

    //initialisation des variable de spinner
    Spinner spinner;
    TextView tvSpinner;


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
                String dateD = day+"/"+month+"/"+year;
                String dateH = year+"-"+month+"-"+day;
                globalVariable.dateDebut = new getDate(dateD, dateH);

                tvDate.setText(globalVariable.dateDebut.displayValue);
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
                        String date2D = day+"/"+month+"/"+year;
                        String date2H = year+"-"+month+"-"+day;
                        globalVariable.dateFin = new getDate(date2D, date2H);
                        tvDate2.setText(globalVariable.dateFin.displayValue);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //  asignation des variables du spinner
        spinner=findViewById(R.id.spinner);
        tvSpinner = findViewById(R.id.tvSpinner);
        //tableau pour le spinner
        ArrayList<getSpinner> listeCommune = new ArrayList<>();


        //=============
        //requete pouir remplir le spinner avec les differentes communes

        String url = "http://192.168.1.5:3000/api/communes";
        //appel de l'api
        RequestQueue requestQueue = Volley.newRequestQueue( this);

        JsonArrayRequest getRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest response ", response.toString());
                        listeCommune.add(new getSpinner("Choisir une commune", 0));
                        for (int i = 0; i < response.length(); ++i) {
                            JSONObject rec = null;
                            try {
                                rec = response.getJSONObject(i);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                String nom = rec.getString("co_nom");
                                Integer id = rec.getInt("co_id");
                                listeCommune.add(new getSpinner(nom, id));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest response ", error.toString());
                    }
                }

        );
        //execution de l'appel de la requete
        requestQueue.add(getRequest);

        // creation et remplissage du searchable spinner
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
    public void addMission(View view){
        //=============


        String url = "http://192.168.1.5:3000/api/mission/"+globalVariable.dateDebut.HiddenValue()+"/"+globalVariable.dateFin.HiddenValue()+"/0/0/"+globalVariable.salarieId+"/"+((getSpinner)spinner.getSelectedItem()).HiddenValue()+"/"+globalVariable.salarieCommuneAgence;
        Log.e("fez", url);

        //appel de l'api
        RequestQueue requestQueue = Volley.newRequestQueue( this);

        JsonArrayRequest getRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest response ", response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest response ", error.toString());
                    }
                }

        );
        //execution de l'appel de la requete
        requestQueue.add(getRequest);
        Toast.makeText(getApplicationContext(), "Mission correctement envoyée", Toast.LENGTH_SHORT).show();
    }
    public class getSpinner
    {
        private String displayValue;
        private int hiddenValue;

        // Constructeur
        public getSpinner(String d, Integer h)
        {
            displayValue = d;
            hiddenValue = h;
        }

        // Accesseur
        public int HiddenValue()
        {
                return hiddenValue;
        }

        // Override de la fonction  ToString
        @Override
        public  String toString()
        {
            return displayValue;
        }
    }
    //creation d'une classe pour avoir une date affichée et une date cachée a envoyer a l'api
    public static class getDate
    {
        private String displayValue;
        private String hiddenValue;

        // Constructeur
        public getDate(String d, String h)
        {
            displayValue = d;
            hiddenValue = h;
        }

        // Accesseur
        public String HiddenValue()
        {
            return hiddenValue;
        }

        // Override de la fonction  ToString
        @Override
        public  String toString()
        {
            return displayValue;
        }
    }


}