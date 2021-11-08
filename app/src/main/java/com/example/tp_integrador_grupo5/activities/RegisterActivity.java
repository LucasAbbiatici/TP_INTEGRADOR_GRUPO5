package com.example.tp_integrador_grupo5.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tp_integrador_grupo5.R;
import com.example.tp_integrador_grupo5.conexion.DataUser;
import com.example.tp_integrador_grupo5.entidades.Usuario;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_nombre;
    private EditText et_apellido;
    private EditText et_email2;
    private EditText et_pass;
    private EditText et_repetirpass;
    private DataUser dataUser;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_apellido = (EditText) findViewById(R.id.et_apellido);
        et_email2 = (EditText) findViewById(R.id.et_email2);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_repetirpass = (EditText) findViewById(R.id.et_repetirpass);
    }

    //Evento del boton registrarse cuando se hace click.
    public void RegisterOnClick(View view){
        if (ControlIsEmpty()){
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_LONG).show();
        }
        else if (!et_pass.getText().toString().equals(et_repetirpass.getText().toString())){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }
        else if(et_pass.getText().length() < 8 || et_repetirpass.getText().length() < 8) {
            Toast.makeText(this, "Las contraseñas deben contener 8 caracteres o mas", Toast.LENGTH_SHORT).show();
        } else {
            usuario = new Usuario();
            usuario.setNombre(et_nombre.getText().toString());
            usuario.setApellido(et_apellido.getText().toString());
            usuario.setEmail(et_email2.getText().toString());
            usuario.setPassword(et_pass.getText().toString());
            dataUser = new DataUser(usuario, this);
            dataUser.execute("register");
        }
    }

    private boolean ControlIsEmpty(){
        boolean isEmpty = false;
        if ( et_nombre.getText().toString().isEmpty() ) isEmpty = true;
        if ( et_apellido.getText().toString().isEmpty() ) isEmpty = true;
        if ( et_email2.getText().toString().isEmpty() ) isEmpty = true;
        if ( et_pass.getText().toString().isEmpty() ) isEmpty = true;
        if ( et_repetirpass.getText().toString().isEmpty() ) isEmpty = true;
        return isEmpty;
    }
}