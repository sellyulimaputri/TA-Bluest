package com.example.bluest.bottombar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bluest.adapter.PulauAdapter;
import com.example.bluest.data.Place;
import com.example.bluest.adapter.PlacesAdapter;
import com.example.bluest.R;
import com.example.bluest.data.pulau;
import com.example.bluest.maps.WisataMaps;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private Drawable getDrawableWithRadius() {

        GradientDrawable gradientDrawable   =   new GradientDrawable();
        gradientDrawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});
//        gradientDrawable.setColor(Color.RED);
        return gradientDrawable;
    }
    private List<pulau> pulauList;
    private RecyclerView recyclerViewPulau;
    private PulauAdapter pulauAdapter;
    private List<Place> placeList;
    private RecyclerView recyclerView;
    private PlacesAdapter placesAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        Button button = (Button) view.findViewById(R.id.bukamaps);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(),MapsActivity.class));
//            }
//        });
//        TextView nama =getActivity().findViewById(R.id.nama);
//        LinearLayout layout =getActivity().findViewById(R.id.linear);
//        layout.setBackground(getDrawableWithRadius());
//
//        String user = getActivity().getIntent().getStringExtra("user");
//        nama.setText(user+", Mau Kemana Hari ini?");
        recyclerView = view.findViewById(R.id.recyclerView);
        placeList = new ArrayList<>();
        placesAdapter = new PlacesAdapter(placeList);

        // Set layout manager dan adapter untuk RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                placesAdapter.setOnItemClickListener(new PlacesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Place place) {
                // Panggil metode untuk membuka PlaceMaps dengan latitude dan longitude
                openPlaceMaps(place.getLatitude(), place.getLongitude());
            }
        });
        recyclerView.setAdapter(placesAdapter);

        recyclerViewPulau = view.findViewById(R.id.recyclerViewPulau);
        pulauList = new ArrayList<>();
        pulauAdapter = new PulauAdapter(pulauList);
        recyclerViewPulau.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPulau.setAdapter(pulauAdapter);


        fetchDataFromApiPulau();
        // Panggil metode untuk mengambil data dari API
        fetchDataFromApi();
        return view;
    }
        private void openPlaceMaps(double latitude, double longitude) {
        Intent intent = new Intent(getActivity(), WisataMaps.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }
    private void fetchDataFromApiPulau(){
        String apiUrl = "https://api-git-main-selly-ulima-putris-projects.vercel.app/api/pulau";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
                    String nama = null;
                    String foto = null;
                    Integer langitude = null;
                    Integer longitude = null;
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject pulauJson = response.getJSONObject(i);
                            try {
                                nama = pulauJson.getString("nama");
                                foto = pulauJson.getString("foto");
                                langitude = pulauJson.getInt("langitude");
                                longitude = pulauJson.getInt("longitude");
                                pulau pulau = new pulau();
                                pulau.nama = nama;
                                pulau.foto = foto;
                                pulau.latitude = langitude;
                                pulau.longitude = longitude;
                                pulauList.add(pulau);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        pulauAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                }
        );
        Volley.newRequestQueue(requireContext()).add(request);
    }
    private void fetchDataFromApi() {
        // Buat permintaan API menggunakan Volley atau metode lainnya
        String apiUrl = "https://api-git-main-selly-ulima-putris-projects.vercel.app/api/places";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
//                    String id = null;
                    String nama = null;
                    String alamat = null;
                    String deskripsi = null;
                    String foto = null;
                    Double latitude = null;
                    Double longitude = null;
                    // Proses respons JSON
                    try {

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject placeJson = response.getJSONObject(i);
                            try{

//                                id = placeJson.getString("id");
                                nama = placeJson.getString("nama");
                                alamat = placeJson.getString("alamat");
                                deskripsi = placeJson.getString("deskripsi");
                                foto = placeJson.getString("foto");
                                latitude = placeJson.getDouble("latitude");
                                longitude = placeJson.getDouble("longitude");
                                Place place= new Place();
//                                place.id = Integer.parseInt(id);
                                place.nama = nama;
                                place.alamat = alamat;
                                place.deskripsi = deskripsi;
                                place.foto = foto;
                                place.latitude = latitude;
                                place.longitude = longitude;
                                placeList.add(place);
                            }catch (Exception e) {
                                e.printStackTrace();
                            }

//                            // Parsing data JSON ke objek Place
//                            Place place = new Place();
//                            place.id(placeJson.getInt("id"));
//                            place.nama(placeJson.getString("nama"));
//                            place.setAlamat(placeJson.getString("alamat"));
//                            place.setDeskripsi(placeJson.getString("deskripsi"));
//                            place.setFoto(placeJson.getString("foto"));
//
//                            // Tambahkan Place ke daftar
//                            placeList.add(place);
                        }

                        // Pemberitahuan bahwa data telah berubah
                        placesAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle kesalahan saat mengambil data
                    Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                }
        );

        // Tambahkan permintaan ke antrian Volley (pastikan Anda sudah menginisialisasi antrian sebelumnya)
        Volley.newRequestQueue(requireContext()).add(request);
    }
}