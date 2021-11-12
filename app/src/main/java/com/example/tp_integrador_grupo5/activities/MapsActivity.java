package com.example.tp_integrador_grupo5.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp_integrador_grupo5.R;
import com.example.tp_integrador_grupo5.conexion.DataReporte;
import com.example.tp_integrador_grupo5.conexion.DataUbicacion;
import com.example.tp_integrador_grupo5.entidades.Ubicacion;
import com.example.tp_integrador_grupo5.entidades.Usuario;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.example.tp_integrador_grupo5.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, IReloadMapa {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private com.getbase.floatingactionbutton.FloatingActionButton fabActual;
    private com.getbase.floatingactionbutton.FloatingActionButton fabUser;
    private com.getbase.floatingactionbutton.FloatingActionButton fabManual;
    private Usuario usuario;
    private DataUbicacion dataUbicacion;
    private DataReporte dataReporte;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // onClick fab agregar ubicacion actual
        fabActual = findViewById(R.id.fab_addActual);
        fabActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancellationToken token = new CancellationToken() {
                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }

                    @NonNull
                    @Override
                    public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                        return null;
                    }
                };

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.getCurrentLocation(100, token)
                            .addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        Intent i = new Intent(MapsActivity.this, AgregarUbicacionActivity.class);
                                        double latitude = (double) location.getLatitude();
                                        double longitude = (double) location.getLongitude();
                                        i.putExtra("latitud",latitude);
                                        i.putExtra("longitud",longitude);
                                        i.putExtra("usuario",usuario);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(MapsActivity.this, "No se pudo encontrar la ubicación", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(MapsActivity.this, "La aplicación no tiene los permisos necesarios", Toast.LENGTH_LONG).show();
                }
            }
        });

        //onClick fab usuario
        fabUser = findViewById(R.id.fab_user);
        fabUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(MapsActivity.this);
                View popupWindow = inflater.inflate(R.layout.dialog_usuario, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

                builder.setView(popupWindow);

                AlertDialog dialog = builder.create();
                dialog.show();

                TextView tv_infoMail = (TextView) dialog.findViewById(R.id.tv_infoMail);
                TextView tv_infoNombre = (TextView) dialog.findViewById(R.id.tv_infoNombre);
                TextView tv_infoApellido = (TextView) dialog.findViewById(R.id.tv_infoApellido);
                TextView tv_reportes = (TextView) dialog.findViewById(R.id.tv_reportes);
                TextView tv_ubicacionesAgregadas = (TextView) dialog.findViewById(R.id.tv_ubicacionesAgregadas);

                tv_infoMail.setText(usuario.getEmail());
                tv_infoNombre.setText(usuario.getNombre());
                tv_infoApellido.setText(usuario.getApellido());

                dataReporte = new DataReporte(MapsActivity.this, usuario, tv_reportes);
                dataReporte.execute("reportesXusuario");

                dataUbicacion = new DataUbicacion(MapsActivity.this, usuario.getId(), tv_ubicacionesAgregadas);
                dataUbicacion.execute("ubicacionesXusuario");

            }
        });

        //onClick fab agregar manual
        fabManual = findViewById(R.id.fab_addManual);
        fabManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), SeleccionarUbicacionActivity.class);
                i.putExtra("usuario", usuario);
                startActivity(i);

            }
        });

    }

    public void logout(View view) {
        finish();

        overridePendingTransition(0,0);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

        overridePendingTransition(0,0);
    }

    public void redirec_modificarPass(View view) {
        Intent i = new Intent(this, CambiarContraseniaActivity.class);
        i.putExtra("usuario", usuario);
        startActivity(i);
    }

    public void redirec_agregarUbicacion(View view) {
        Intent i = new Intent(this, AgregarUbicacionActivity.class);
        i.putExtra("usuario", usuario);
        startActivity(i);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        dataUbicacion = new DataUbicacion(mMap, usuario, this, this::reloadMapa);
        dataUbicacion.execute("listar");

    }

    @Override
    public void reloadMapa() {
        finish();

        overridePendingTransition(0,0);

        Intent i = new Intent(getIntent());
        startActivity(i);

        overridePendingTransition(0,0);
    }
}