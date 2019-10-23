package com.example.ptut_s3;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GestionEtu {
    private List<Etudiant> etuList;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    DonnerRec activity;

    public GestionEtu(DonnerRec activity) {
        this.activity=activity;
        this.etuList = new ArrayList<>();
        this.mDatabase = FirebaseDatabase.getInstance();
        this.mDatabaseRef = mDatabase.getReference("etudiants");
    }

    public void readEtu(){
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.w("chargementDonnes", dataSnapshot.getChildren().toString());
                etuList.clear();
                List<String> keys = new ArrayList<>();
                int i=0;
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Etudiant etu  = keyNode.getValue(Etudiant.class);
                    etuList.add(etu);
                    Log.w("ajoutEtu",etuList.get(i).getNom());
                    i++;
                }
                activity.initialisationCredits();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w( "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public List<Etudiant> getEtuList() {
        return etuList;
    }
}
