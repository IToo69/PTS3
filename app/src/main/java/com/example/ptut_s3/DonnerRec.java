package com.example.ptut_s3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DonnerRec extends AppCompatActivity {

    TextView solde;
    int credits ;
    int creditDep ;
    GestionEtu gestionEtu;
    int pointsTrophy;
    RecyclerView recList;
    GestionRec gestionRec;
    TextView achat;
    TextView restant;
    Button valider;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Etudiant student;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donner_rec);

        student = (Etudiant) getIntent().getSerializableExtra("student");


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        solde = findViewById(R.id.solde);

        recList = findViewById(R.id.dispRec);

        valider=findViewById(R.id.valider);

        gestionEtu = new GestionEtu(this);
        gestionEtu.readEtu();

        gestionRec = new GestionRec(this,recList);
        gestionRec.readRec();

        achat = findViewById(R.id.achat);
        restant = findViewById(R.id.restant);

        Log.e("listeEtuCrea",gestionEtu.getEtuList().toString());

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("etudiants").child(student.getId()).child("creditDep").setValue(student.getCreditDep()+Integer.valueOf(achat.getText().toString()));
                gestionRec.setaPayer(0);
                for (RecompenseAdapter.ViewHolder viewHolder : gestionRec.getRecompenseAdapter().getViewHolders()){
                    viewHolder.tot.setText(String.valueOf(0));
                }
                initialisationRec();
            }
        });


    }

    public void initialisationCredits(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://katastrophyk.univ-lemans.fr/BackOfficePL/api/getStudentTotalPoints.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            Iterator<String> keys = json.keys();
                            keys.next();
                            String res = json.getString(keys.next());
                            pointsTrophy=Integer.parseInt(res);
                            Log.e("pointsTrophyRes",String.valueOf(Integer.parseInt(res)));

                            Log.e("listeEtu",gestionEtu.getEtuList().toString());

                            for (Etudiant etu  : gestionEtu.getEtuList()){
                                Log.e("identifiantdeletudiant",etu.getId());
                                if (etu.getId().equals(student.getId())){
                                    Log.e("points depenser",String.valueOf(etu.getCreditDep()));
                                    creditDep = etu.getCreditDep();
                                    student=etu;
                                }
                            }

                            Log.e("pointsTrophy",String.valueOf(pointsTrophy));
                            credits = pointsTrophy - creditDep;

                            solde.setText(String.valueOf(credits));
                            restant.setText(solde.getText());

                        } catch (JSONException e) {
                            solde.setText(response);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("student_id", student.getId());

                return params;
            }
        };

        queue.add(postRequest);
    }

    public void initialisationRec(){
        achat.setText(String.valueOf(gestionRec.getaPayer()));
        restant.setText(String.valueOf(credits-Integer.valueOf(achat.getText().toString())));
        Log.e("credits",String.valueOf(credits));
        if (Integer.valueOf(restant.getText().toString())<0){
            valider.setEnabled(false);
            restant.setTextColor(Color.RED);
            Toast.makeText(this,"pas assez de credits pour effectuer cet achat",Toast.LENGTH_SHORT).show();
        }
        if (Integer.valueOf(restant.getText().toString())>=0){
            valider.setEnabled(true);
            restant.setTextColor(Color.BLACK);
        }
    }

}
