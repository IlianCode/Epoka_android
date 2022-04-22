package com.example.epoka_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class loginPage extends AppCompatActivity {
    private String someVariable;

    Button button_connexion;
    EditText idUser;
    EditText pwdUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        button_connexion = findViewById(R.id.button_connexion);

    }

    public void toConnect(View view){
        pwdUser = findViewById(R.id.editTextTextPassword);
        idUser = findViewById(R.id.editTextUsername);

        String newIdUser = idUser.getText().toString();
        String newPwdUser = pwdUser.getText().toString();
        //Toast.makeText(this, "user id : "+ newIdUser + " user pwd : " + newPwdUser, Toast.LENGTH_SHORT).show();

        String url = "http://192.168.1.5:3000/api/connexion/"+newIdUser+"/"+ newPwdUser;
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

                        for (int i = 0; i < response.length(); ++i) {
                            JSONObject rec = null;
                            try {
                                rec = response.getJSONObject(i);
                                Log.e("Reponse : ", String.valueOf(rec));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                globalVariable.salarieId = rec.getString("sa_id");
                                globalVariable.salarieNom = rec.getString("sa_nom");
                                globalVariable.salariePrenom = rec.getString("sa_prenom");
                                globalVariable.salarieCommuneAgence = rec.getInt("ag_idcommune");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                        openActivityMain();
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest response ", error.toString());

                    }
                }

        );
        //appel de la requete
        requestQueue.add(getRequest);
    }
    public void openActivityMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


//variavble globale de l'application

    public static class globalVariable {
        public static String salarieId;
        public static String salarieNom;
        public static String salariePrenom;
        public static int salarieCommuneAgence;
        public static MainActivity.getDate dateDebut;
        public static MainActivity.getDate dateFin;
    }

}
