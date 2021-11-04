package com.example.tp_integrador_grupo5.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tp_integrador_grupo5.R;
import com.example.tp_integrador_grupo5.databinding.ActivityMapsBinding;
import com.example.tp_integrador_grupo5.entidades.Usuario;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SeleccionarUbicacionActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private TextView direccion;
    private Geocoder geocoder;
    private Button boton_continuar;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng arg0)
            {
                double latitud = arg0.latitude;
                double longitud = arg0.longitude;

                LayoutInflater inflater = LayoutInflater.from(SeleccionarUbicacionActivity.this);
                View popupWindow = inflater.inflate(R.layout.dialog_ubicacion,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(SeleccionarUbicacionActivity.this);

                builder.setView(popupWindow);

                AlertDialog dialog = builder.create();
                dialog.show();

                direccion = (TextView) dialog.findViewById(R.id.tv_direccion);

                try {
                    direccion.setText(traerDireccion(latitud,longitud));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                boton_continuar = (Button) dialog.findViewById(R.id.btn_continuar);
                boton_continuar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), AgregarUbicacionActivity.class);
                        i.putExtra("latitud",latitud);
                        i.putExtra("longitud",longitud);
                        i.putExtra("usuario",usuario);
                        startActivity(i);

                    }
                });

            }
        });

    }

    private String traerDireccion(double lat, double lng) throws IOException {
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String direc = obj.getAddressLine(0);

            return direc;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error, dirección no encontrada.";


        }

    }

}