package com.example.tp_integrador_grupo5.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tp_integrador_grupo5.R;
import com.example.tp_integrador_grupo5.conexion.DataUbicacion;
import com.example.tp_integrador_grupo5.entidades.Ubicacion;
import com.example.tp_integrador_grupo5.entidades.Usuario;
import com.google.android.gms.maps.model.LatLng;

public class AgregarUbicacionActivity extends AppCompatActivity {
    private EditText et_cant;
    private Switch sw_ninos;
    private Switch sw_mascotas;
    private Switch sw_discapacitados;
    private Switch sw_ancianos;
    private EditText et_comentarios;
    private Ubicacion ubicacion;
    private Usuario usuario;
    private double lat;
    private double lng;
    private DataUbicacion dataUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ubicacion);

        et_cant = (EditText) findViewById(R.id.et_cant);
        sw_ninos = (Switch) findViewById(R.id.sw_children);
        sw_mascotas = (Switch) findViewById(R.id.sw_pets);
        sw_discapacitados = (Switch) findViewById(R.id.sw_handicapped);
        sw_ancianos = (Switch) findViewById(R.id.sw_grandparents);
        et_comentarios = (EditText) findViewById(R.id.et_comentarios);

        lat = (double) getIntent().getExtras().getDouble("latitud");
        lng = (double) getIntent().getExtras().getDouble("longitud");

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

    }

    public void onClickAgregarUbicacion(View view){

        if(et_cant.getText().toString().equals("0")){
            Toast.makeText(this, "La cantidad de personas no puede ser 0.", Toast.LENGTH_SHORT).show();
        } else {
            ubicacion = new Ubicacion();

            ubicacion.setUsuario(usuario);
            ubicacion.setLatitud(lat);
            ubicacion.setLongitud(lng);
            ubicacion.setCant_personas(Integer.parseInt(et_cant.getText().toString()));
            ubicacion.setMascotas(sw_mascotas.isChecked());
            ubicacion.setAncianos(sw_ancianos.isChecked());
            ubicacion.setNinios(sw_ninos.isChecked());
            ubicacion.setDiscapacitados(sw_discapacitados.isChecked());
            ubicacion.setComentarios(et_comentarios.getText().toString());

            dataUbicacion = new DataUbicacion(ubicacion, this);
            dataUbicacion.execute("agregar");
        }

    }


}