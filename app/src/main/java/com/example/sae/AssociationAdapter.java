package com.example.sae;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AssociationAdapter extends RecyclerView.Adapter<AssociationAdapter.ViewHolder>
{
    private List<Association> associations;
    private Context context;


    public AssociationAdapter(List<Association> associations, Context context) {
        this.associations = associations;
        this.context = context;
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

        // Charger l'image depuis drawable
        int imageRes = context.getResources().getIdentifier(association.getImage(), "drawable", context.getPackageName());
        holder.imageView.setImageResource(imageRes);
    }


    @Override
    public int getItemCount() {
        return associations.size();
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
}
