package com.example.ptut_s3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreerRec extends AppCompatActivity {

    private Button ajouter;
    private EditText nom;
    private EditText prix;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_rec);
        ajouter=findViewById(R.id.ajouter);
        nom=findViewById(R.id.nom);
        prix=findViewById(R.id.prix);

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recompense rec = new Recompense(Integer.parseInt(prix.getText().toString()),nom.getText().toString());
                myRef.child("recompense").child(nom.getText().toString()).setValue(rec);
            }
        });
    }
}
