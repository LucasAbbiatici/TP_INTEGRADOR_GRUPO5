package com.example.tp_integrador_grupo5.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.tp_integrador_grupo5.R;
import com.example.tp_integrador_grupo5.conexion.DataUbicacion;
import com.example.tp_integrador_grupo5.entidades.Ubicacion;
import com.example.tp_integrador_grupo5.entidades.Usuario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tp_integrador_grupo5.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private com.getbase.floatingactionbutton.FloatingActionButton fabActual;
    private com.getbase.floatingactionbutton.FloatingActionButton fabUser;
    private com.getbase.floatingactionbutton.FloatingActionButton fabManual;
    private ImageView icon_mascotas;
    private Usuario usuario;
    private DataUbicacion dataUbicacion;
    private ArrayList<Ubicacion> listaUbicaciones;
    private LatLng posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        // onClick fab agregar ubicacion actual
        fabActual = findViewById(R.id.fab_addActual);
        fabActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AgregarUbicacionActivity.class);
                view.getContext().startActivity(i);
            }
        });

        //onClick fab usuario
        fabUser = findViewById(R.id.fab_user);
        fabUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(MapsActivity.this);
                View popupWindow = inflater.inflate(R.layout.dialog_usuario,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

                builder.setView(popupWindow);

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        //onClick fab agregar manual
        fabManual = findViewById(R.id.fab_addManual);
        fabManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //icon_mascotas = (ImageView) dialog.findViewById(R.id.ic_mascotas);
                //icon_mascotas.setColorFilter(Color.GREEN);

                Intent i = new Intent(getApplicationContext(), SeleccionarUbicacionActivity.class);
                i.putExtra("usuario",usuario);
                startActivity(i);

            }
        });

    }

    public void accionCompartir(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        startActivity(sendIntent);
    }

    public void logout(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

        finish();
    }

    public void redirec_modificarPass(View view){
        Intent i = new Intent(this, CambiarContraseniaActivity.class);
        i.putExtra("usuario",usuario);
        startActivity(i);
    }

    public void redirec_agregarUbicacion(View view){
        Intent i = new Intent(this, AgregarUbicacionActivity.class);
        i.putExtra("usuario",usuario);
        startActivity(i);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        dataUbicacion = new DataUbicacion(mMap, this);

        /*
        listaUbicaciones = new ArrayList<Ubicacion>();
        dataUbicacion = new DataUbicacion(this);
        dataUbicacion.execute("listar");
        listaUbicaciones = dataUbicacion.getListaUbicaciones();


        System.out.println("activity: ");
        System.out.println(listaUbicaciones);

        for(int i=0;i<listaUbicaciones.size();i++){
            System.out.println("activity: ");
            System.out.println(listaUbicaciones.get(i).toString());

            posicion = new LatLng(listaUbicaciones.get(i).getLatitud(),listaUbicaciones.get(i).getLongitud());
            mMap.addMarker(new MarkerOptions().position(posicion).title(String.valueOf(listaUbicaciones.get(i).getId())));
        }

        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

    }

}