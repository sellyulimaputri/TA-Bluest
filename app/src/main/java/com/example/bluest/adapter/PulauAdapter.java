package com.example.bluest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluest.R;
import com.example.bluest.data.Place;
import com.example.bluest.data.pulau;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PulauAdapter extends RecyclerView.Adapter<PulauAdapter.PulauViewHolder>{
    private List<pulau> pulauList;


    public PulauAdapter(List<pulau> pulauList) {
        this.pulauList = pulauList;
    }
    @NonNull
    @Override
    public PulauViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pulau, parent, false);
        return new PulauViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PulauViewHolder holder, int position) {
        pulau pulau = pulauList.get(position);

        // Set data ke tampilan holder
//        holder.textViewFoto.setText(place.foto);
        holder.textViewName.setText(pulau.nama);

        // Load gambar menggunakan Picasso atau metode lainnya
        Picasso.get().load(pulau.foto).into(holder.imageViewPhoto);

    }

    @Override
    public int getItemCount() {
        return pulauList.size();
    }
    public static class PulauViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageViewPhoto;

        public PulauViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewPhoto = itemView.findViewById(R.id.imageViewPhoto);
        }
    }
}
