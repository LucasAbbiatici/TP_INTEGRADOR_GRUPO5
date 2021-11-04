package com.example.tp_integrador_grupo5.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

import com.example.tp_integrador_grupo5.R;

public class AgregarUbicacionActivity extends AppCompatActivity {
    private EditText et_cant;
    private Switch sw_ninos;
    private Switch sw_mascotas;
    private Switch sw_discapacitados;
    private Switch sw_ancianos;
    private EditText et_comentarios;


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

    }


}