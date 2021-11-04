package com.example.tp_integrador_grupo5.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.tp_integrador_grupo5.R;
import com.example.tp_integrador_grupo5.conexion.DataUser;
import com.example.tp_integrador_grupo5.entidades.Usuario;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private EditText et_mail;
    private EditText et_password;
    private Usuario usuario;
    private DataUser dataUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogBuilder();

        et_mail = (EditText) findViewById(R.id.et_mail);
        et_password = (EditText) findViewById(R.id.et_password);

    }

    private void dialogBuilder(){

        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.dialog_compartirUbicacion).setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setTitle("Compartir Ubicación");

        alertDialog = builder.create();

    }

    public void redirec_register(View view){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void ingresar(View view){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            alertDialog.show();
        } else {

            usuario = new Usuario();

            usuario.setEmail(et_mail.getText().toString());
            usuario.setPassword(et_password.getText().toString());

            dataUser = new DataUser(usuario, this);
            dataUser.execute("login");

        }

    }

}