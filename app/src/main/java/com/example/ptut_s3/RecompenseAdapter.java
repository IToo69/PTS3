package com.example.ptut_s3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class RecompenseAdapter extends RecyclerView.Adapter<RecompenseAdapter.ViewHolder> {
    List<Recompense> recompenseList;
    GestionRec gestionRec;
    List<ViewHolder> viewHolders;

    RecompenseAdapter(List<Recompense> recompenseList,GestionRec gestionRec) {
        this.recompenseList=recompenseList;
        this.gestionRec = gestionRec;
        viewHolders = new ArrayList<>();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.recompense_disp, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolders.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.display(recompenseList.get(position));
    }

    @Override
    public int getItemCount() {
        return recompenseList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nom;
        TextView prix;
        TextView tot;
        ImageButton up;
        ImageButton down;
        Recompense rec;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nom = itemView.findViewById(R.id.nom);
            prix = itemView.findViewById(R.id.prix);
            tot = itemView.findViewById(R.id.tot);
            up = itemView.findViewById(R.id.up);
            down = itemView.findViewById(R.id.down);
            rec=null;

        }

        public void display(Recompense recompense){
            nom.setText(recompense.getNom());
            prix.setText(String.valueOf(recompense.getPrix()));
            tot.setText(String.valueOf(0));
            rec=recompense;

            up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tot.setText(String.valueOf(Integer.parseInt(tot.getText().toString())+rec.getPrix()));
                    gestionRec.setaPayer(gestionRec.getaPayer()+rec.getPrix());
                    gestionRec.getActivity().initialisationRec();
                }
            });
            down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.valueOf(tot.getText().toString())>0){
                        tot.setText(String.valueOf(Integer.parseInt(tot.getText().toString())-rec.getPrix()));
                        gestionRec.setaPayer(gestionRec.getaPayer()-rec.getPrix());
                        gestionRec.getActivity().initialisationRec();
                    }
                    else {
                        Toast.makeText(gestionRec.getActivity(),"impossible d'enlever un item non ajouté au panier",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    public List<ViewHolder> getViewHolders() {
        return viewHolders;
    }
}
