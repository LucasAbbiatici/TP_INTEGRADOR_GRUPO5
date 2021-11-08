package com.example.tp_integrador_grupo5.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tp_integrador_grupo5.R;
import com.example.tp_integrador_grupo5.conexion.DataUser;
import com.example.tp_integrador_grupo5.entidades.Usuario;
import com.example.tp_integrador_grupo5.validaciones;

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
        if(validarContraseña()){
            dataUser = new DataUser(et_contraseñaNueva.getText().toString(), usuario,this);
            dataUser.execute("modificar");
        }
    }

    private boolean validarContraseña(){
        //valido que la contraseña actual introducida sea valida
        if(!et_contraseñaActual.getText().toString().equals(usuario.getPassword())){
            Toast.makeText(this, "contraseña actual erronea", Toast.LENGTH_SHORT).show();
            return false;

            //valido que la contraseña nueva y la repetida sean iguales
        }else if(!et_contraseñaNueva.getText().toString().equals(et_contraseñaRepetir.getText().toString())) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;

            //Valido que la contraseña nueva y la actual introducida tengan mas de 7 caracteres
        }else if(et_contraseñaNueva.getText().length() < 8 || et_contraseñaRepetir.getText().length() < 8){
            Toast.makeText(this, "Las contraseñas deben contener 8 caracteres o mas", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}