package com.example.tp_integrador_grupo5.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tp_integrador_grupo5.R;
import com.example.tp_integrador_grupo5.conexion.DataReporte;
import com.example.tp_integrador_grupo5.conexion.DataUbicacion;
import com.example.tp_integrador_grupo5.entidades.Ubicacion;
import com.example.tp_integrador_grupo5.entidades.Usuario;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
    private DataReporte dataReporte;
    private ArrayList<Ubicacion> listaUbicaciones;
    LocationManager locationManager;

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

                TextView tv_infoMail = (TextView) dialog.findViewById(R.id.tv_infoMail);
                TextView tv_infoNombre = (TextView) dialog.findViewById(R.id.tv_infoNombre);
                TextView tv_infoApellido = (TextView) dialog.findViewById(R.id.tv_infoApellido);
                TextView tv_reportes = (TextView) dialog.findViewById(R.id.tv_reportes);
                TextView tv_ubicacionesAgregadas = (TextView) dialog.findViewById(R.id.tv_ubicacionesAgregadas);

                tv_infoMail.setText(usuario.getEmail());
                tv_infoNombre.setText(usuario.getNombre());
                tv_infoApellido.setText(usuario.getApellido());

                dataReporte = new DataReporte(MapsActivity.this, usuario.getId(), tv_reportes);
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        dataUbicacion = new DataUbicacion(mMap, usuario,this);
        dataUbicacion.execute("listar");

    }

}