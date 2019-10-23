package com.example.ptut_s3;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.ViewHolder> {
    List<Etudiant> etudiantList;
    List<ViewHolder> viewHolders;
    SelecEtu activity;

    public EtudiantAdapter(List<Etudiant> etudiantList, SelecEtu activity){
        this.etudiantList = etudiantList;
        this.viewHolders = new ArrayList<>();
        this.activity=activity;
    }

    @NonNull
    @Override
    public EtudiantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.etu_disp, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolders.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.display(etudiantList.get(position));
    }

    @Override
    public int getItemCount() {
        return etudiantList.size();
    }

    public List<ViewHolder> getViewHolders() {
        return viewHolders;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomEtu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomEtu = itemView.findViewById(R.id.nomEtu);

        }

        public void display(final Etudiant etudiant){
            nomEtu.setText(etudiant.getNom()+" "+etudiant.getPrenom());

            nomEtu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("click pour next page",v.toString());
                    Intent intent = new Intent(activity, DonnerRec.class);
                    intent.putExtra("student", etudiant);
                    activity.startActivity(intent);

                }
            });
        }

        public TextView getNomEtu() {
            return nomEtu;
        }
    }

}
