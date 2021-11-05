package com.example.tp_integrador_grupo5.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.tp_integrador_grupo5.R;
import com.example.tp_integrador_grupo5.conexion.DataUser;
import com.example.tp_integrador_grupo5.entidades.Usuario;

public class CambiarContraseniaActivity extends AppCompatActivity {
    private Usuario usuario;
    private DataUser dataUser;
    private EditText et_contraseñaActual;
    private EditText et_contraseñaNueva;
    private EditText et_contraseñaRepetir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasenia);

        et_contraseñaActual = (EditText) findViewById(R.id.et_contraActual);
        et_contraseñaNueva = (EditText) findViewById(R.id.et_nuevaContra);
        et_contraseñaRepetir = (EditText) findViewById(R.id.et_repetirContra);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

    }


    public void onClickCambiarPass(View view){
        dataUser = new DataUser(et_contraseñaNueva.getText().toString(), usuario,this);
        dataUser.execute("modificar");

    }
}