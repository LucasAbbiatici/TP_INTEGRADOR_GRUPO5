package com.example.tp_integrador_grupo5.conexion;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.tp_integrador_grupo5.activities.MainActivity;
import com.example.tp_integrador_grupo5.activities.MapsActivity;
import com.example.tp_integrador_grupo5.conexion.DataDB;
import com.example.tp_integrador_grupo5.entidades.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.xml.transform.Result;

public class DataUser extends AsyncTask<String, Void, String> {

    private Usuario usuario;
    private Context context;
    private String newPass;
    private int id_usuario;

    public DataUser(Usuario usuario, Context context) {
        this.usuario = usuario;
        this.context = context;
    }

    public DataUser(String pass, Usuario usuario, Context context){
        this.newPass = pass;
        this.usuario = usuario;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        String response = "";

        try {
            Class.forName(DataDB.driver);
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            if(strings[0] == "register"){

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM Usuarios WHERE Email = '" + usuario.getEmail() + "' ;");

                if(rs.next()){
                    response = "Email ya utilizado";
                }else{
                    PreparedStatement statement = con.prepareStatement("INSERT INTO Usuarios (Nombre, Apellido, Email, Password) VALUES (? ,? ,? ,? )");
                    statement.setString(1, usuario.getNombre());
                    statement.setString(2, usuario.getApellido());
                    statement.setString(3, usuario.getEmail());
                    statement.setString(4, usuario.getPassword());

                    statement.executeUpdate();
                    response = "Usuario registrado exitosamente";
                }

            }

            if(strings[0] == "login"){

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM Usuarios WHERE Email = '" + usuario.getEmail() + "' AND Password = '" + usuario.getPassword() + "' ;");

                if(rs.next()){
                    usuario.setId(rs.getInt("ID_Usuario"));
                    usuario.setNombre(rs.getString("Nombre"));
                    usuario.setApellido(rs.getString("Apellido"));
                    usuario.setEmail(rs.getString("Email"));
                    usuario.setPassword(rs.getString("Password"));

                    response = "Verificacion de login exitoso";
                } else {
                    response = "Email o contraseña incorrectos";
                }

            }

            if(strings[0] == "modificar"){

                PreparedStatement statement = con.prepareStatement("UPDATE Usuarios SET Password = ? WHERE ID_Usuario = ? ;");
                statement.setString(1, newPass);
                statement.setInt(2, usuario.getId());

                statement.executeUpdate();
                response = "Contraseña modificada exitosamente";

                usuario.setPassword(newPass);

            }

            con.close();

        }
        catch (Exception e){
            e.printStackTrace();
            response = "Error en la conexion a la base de datos";
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        Intent i;

        switch(response){
            case "Usuario registrado exitosamente":
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                i = new Intent(context, MainActivity.class);
                context.startActivity(i);
                break;

            case "Verificacion de login exitoso" :
                i = new Intent(context, MapsActivity.class);
                i.putExtra("usuario", usuario);
                context.startActivity(i);
                break;

            case "Email o contraseña incorrectos" :
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                break;

            case "Contraseña modificada exitosamente" :
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                i = new Intent(context, MapsActivity.class);
                i.putExtra("usuario", usuario);
                context.startActivity(i);
                break;

            case "Error en la conexion a la base de datos" :
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                break;

            case "Email ya utilizado":
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                break;
        }
        
    }
}
