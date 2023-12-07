package com.example.bluest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>{
    private List<Place> placeList;

    public PlacesAdapter(List<Place> placeList) {
        this.placeList = placeList;
    }
    @NonNull
    @Override
    public PlacesAdapter.PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesAdapter.PlaceViewHolder holder, int position) {
        Place place = placeList.get(position);

        // Set data ke tampilan holder
//        holder.textViewFoto.setText(place.foto);
        holder.textViewName.setText(place.nama);
        holder.textViewAddress.setText(place.alamat);
        holder.textViewDeskripsi.setText(place.deskripsi);

        // Load gambar menggunakan Picasso atau metode lainnya
        Picasso.get().load(place.foto).into(holder.imageViewPhoto);
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }
    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewAddress;
//        TextView textViewFoto;
        TextView textViewDeskripsi;
        ImageView imageViewPhoto;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
//            textViewFoto = itemView.findViewById(R.id.textViewFoto);
            textViewDeskripsi = itemView.findViewById(R.id.textViewDeskripsi);
            imageViewPhoto = itemView.findViewById(R.id.imageViewPhoto);
        }
    }
}
