package com.example.tp_integrador_grupo5.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tp_integrador_grupo5.R;
import com.example.tp_integrador_grupo5.conexion.DataUser;
import com.example.tp_integrador_grupo5.entidades.Usuario;

import java.util.regex.Pattern;

public class CambiarContraseniaActivity extends AppCompatActivity {
    private Usuario usuario;
    private DataUser dataUser;
    private EditText et_contraseñaActual;
    private EditText et_contraseñaNueva;
    private EditText et_contraseñaRepetir;
    private ImageButton btn_showPass_modNueva;
    private ImageButton btn_showPass_modActual;
    private boolean flag1 = true;
    private boolean flag2 = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasenia);

        et_contraseñaActual = (EditText) findViewById(R.id.et_contraActual);
        et_contraseñaNueva = (EditText) findViewById(R.id.et_nuevaContra);
        et_contraseñaRepetir = (EditText) findViewById(R.id.et_repetirContra);

        btn_showPass_modActual = (ImageButton) findViewById(R.id.btn_showPass_modActual);
        btn_showPass_modNueva = (ImageButton) findViewById(R.id.btn_showPass_modNueva);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");


    }


    public void onClickCambiarPass(View view){
        if(validarContraseña()){
            dataUser = new DataUser(et_contraseñaNueva.getText().toString(), usuario,this);
            dataUser.execute("modificar");
        }
    }

    private boolean validarContraseña(){
        Pattern patternPass = Pattern.compile("^(?=.*[0-9])(?=.*[A-Za-z])(?=\\S+$).{8,}$");
        boolean matches1 = patternPass.matcher(et_contraseñaNueva.getText().toString()).matches();

        if(ControlIsEmpty()){
            Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        //valido que la contraseña actual introducida sea valida
        if(!et_contraseñaActual.getText().toString().equals(usuario.getPassword())){
            Toast.makeText(this, "contraseña actual erronea", Toast.LENGTH_SHORT).show();
            return false;

            //valido que la contraseña nueva y la repetida sean iguales
        }else if(!et_contraseñaNueva.getText().toString().equals(et_contraseñaRepetir.getText().toString())) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;

            //Valido que la contraseña nueva y la repetida introducida tengan mas de 7 caracteres
        }else if(!matches1){
            Toast.makeText(this, "Las contraseñas deben contener 8 caracteres o mas y contener mínimo un número y una letra", Toast.LENGTH_SHORT).show();
            return false;

            //Valido que la contraseña nueva y actual sean diferentes
        }else if(et_contraseñaNueva.getText().toString().equals(et_contraseñaActual.getText().toString())){
            Toast.makeText(this, "La contraseña nueva no puede ser igual a la actual", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean ControlIsEmpty(){
        boolean isEmpty = false;
        if ( et_contraseñaActual.getText().toString().isEmpty() ) isEmpty = true;
        if ( et_contraseñaRepetir.getText().toString().isEmpty() ) isEmpty = true;
        if ( et_contraseñaNueva.getText().toString().isEmpty() ) isEmpty = true;
        return isEmpty;
    }

    public void showPassModNueva(View view){
        if(flag1){
            et_contraseñaNueva.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            et_contraseñaRepetir.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            btn_showPass_modNueva.setImageResource(R.drawable.hidden);
            flag1 = false;
        } else{
            et_contraseñaNueva.setTransformationMethod(PasswordTransformationMethod.getInstance());
            et_contraseñaRepetir.setTransformationMethod(PasswordTransformationMethod.getInstance());
            btn_showPass_modNueva.setImageResource(R.drawable.view);
            flag1 = true;
        }
    }

    public void showPassModActual(View view){
        if(flag2){
            et_contraseñaActual.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            btn_showPass_modActual.setImageResource(R.drawable.hidden);
            flag2 = false;
        }else{
            et_contraseñaActual.setTransformationMethod(PasswordTransformationMethod.getInstance());
            btn_showPass_modActual.setImageResource(R.drawable.view);
            flag2 = true;
        }
    }
}