package com.example.ptut_s3;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class GestionRec {
    private List<Recompense> recList;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private DonnerRec activity;
    private RecyclerView dispRec;
    private TextView tot;
    private int aPayer;
    RecompenseAdapter recompenseAdapter;


    public GestionRec(DonnerRec activity, RecyclerView dispRec) {
        this.activity=activity;
        this.recList = new ArrayList<>();
        this.mDatabase = FirebaseDatabase.getInstance();
        this.mDatabaseRef = mDatabase.getReference("recompense");
        this.dispRec=dispRec;
        aPayer=0;
        recompenseAdapter = new RecompenseAdapter(recList,this);
    }

    public void readRec(){
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.w("chargementDonnes", dataSnapshot.getChildren().toString());
                recList.clear();
                List<String> keys = new ArrayList<>();
                int i=0;

                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Recompense rec  = keyNode.getValue(Recompense.class);
                    recList.add(rec);
                    //Log.w("ajoutRecompense",recList.get(i).getNom());
                    i++;
                }

                dispRec.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                dispRec.setAdapter(recompenseAdapter);

                activity.initialisationRec();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w( "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public List<Recompense> getRecList() {
        return recList;
    }

    public int getaPayer() {
        return aPayer;
    }

    public void setaPayer(int aPayer) {
        this.aPayer = aPayer;
    }

    public DonnerRec getActivity() {
        return activity;
    }

    public RecompenseAdapter getRecompenseAdapter() {
        return recompenseAdapter;
    }
}

