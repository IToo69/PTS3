package com.example.ptut_s3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class SelecEtu extends AppCompatActivity {

    RecyclerView etuList;
    EditText nomEtu;
    GestionEtuSelec gestionEtuSelec;
    List<Etudiant> etudiantList;
    EtudiantAdapter etudiantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_etu);

        nomEtu =findViewById(R.id.nomEtu);
        etuList = findViewById(R.id.etuList);

        gestionEtuSelec = new GestionEtuSelec(this);
        gestionEtuSelec.readEtu();

        etuList = findViewById(R.id.etuList);
        etuList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        etudiantList= new ArrayList<>();

        nomEtu.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                etudiantList.clear();
                Log.e("liste etu debut event",gestionEtuSelec.getEtuList().toString());
                for (Etudiant etu : gestionEtuSelec.getEtuList()){
                    if (etu.getPrenom().toLowerCase().matches((nomEtu.getText().toString()+".*").toLowerCase())  ||
                            etu.getNom().toLowerCase().matches((nomEtu.getText().toString()+".*").toLowerCase()) ||
                            (etu.getNom()+" "+etu.getPrenom()).toLowerCase().matches((nomEtu.getText().toString()+".*").toLowerCase()) ||
                            (etu.getPrenom()+" "+etu.getNom()).toLowerCase().matches((nomEtu.getText().toString()+".*").toLowerCase())){
                        etudiantList.add(etu);
                        Log.e("liste etu ", etudiantList.toString());
                    }
                }

                etudiantAdapter = new EtudiantAdapter(etudiantList, SelecEtu.this);

                etuList.setAdapter(etudiantAdapter);

                return false;
            }
        });

    }

    public void initialisation(){
        etudiantList.addAll(gestionEtuSelec.getEtuList());

        Log.e("liste etudiant total",etudiantList.toString());

        etudiantAdapter = new EtudiantAdapter(etudiantList,this);
        etuList.setAdapter(etudiantAdapter);
    }

}