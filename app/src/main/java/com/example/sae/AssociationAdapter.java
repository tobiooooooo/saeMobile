package com.example.sae;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AssociationAdapter extends RecyclerView.Adapter<AssociationAdapter.ViewHolder>
{
    private List<Association> associations;
    private List<Association> associationsFull; // Liste complète pour la recherche
    private Context context;

    private OnAssociationClickListener listener;

    public AssociationAdapter(List<Association> associations, Context context, OnAssociationClickListener listener) {
        this.associations = associations;
        this.associationsFull = new ArrayList<>(associations);
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_association, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Association association = associations.get(position);
        holder.tvNom.setText(association.getNom());
        holder.tvDescription.setText(association.getDescription());

        try {
            InputStream is = context.getAssets().open("logos/" + association.getImage());
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            holder.imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            holder.imageView.setImageResource(R.drawable.default_image);
        }

        // Détection du clic sur une association
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAssociationClick(association.getNom());
            }
        });
    }

    @Override
    public int getItemCount() {
        return associations.size();
    }

    public void filter(String text) {
        associations.clear();
        if (text.isEmpty()) {
            associations.addAll(associationsFull); // Si la barre est vide, tout afficher
        } else {
            String searchText = text.toLowerCase();
            for (Association item : associationsFull) {
                if (item.getNom().toLowerCase().contains(searchText)) {
                    associations.add(item); // Ajouter seulement ceux qui contiennent le texte
                }
            }
        }
        notifyDataSetChanged(); // Mettre à jour l'affichage
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvNom, tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvNom = itemView.findViewById(R.id.tvNom);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
    public void setAssociations(List<Association> newAssociations) {
        associations.clear();
        associations.addAll(newAssociations);

        associationsFull.clear();
        associationsFull.addAll(newAssociations);

        notifyDataSetChanged();
    }


}
