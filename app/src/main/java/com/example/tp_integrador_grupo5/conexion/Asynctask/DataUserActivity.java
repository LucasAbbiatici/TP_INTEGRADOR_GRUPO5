package com.example.tp_integrador_grupo5.conexion.Asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.example.tp_integrador_grupo5.conexion.DataDB;
import com.example.tp_integrador_grupo5.entidades.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataUserActivity extends AsyncTask<String, Void, String> {

    private Usuario usuario;
    private Context context;
    private static String result2;

    public DataUserActivity(Usuario usuario, Context context) {
        this.usuario = usuario;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try {
            result2 = " ";
            Class.forName(DataDB.driver);
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            PreparedStatement statement = con.prepareStatement("INSERT INTO Usuarios (Nombre, Apellido, Email, " +
                    "Password) VALUES (? ,? ,? ,? )");
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getEmail());
            statement.setString(4, usuario.getPassword());

            statement.executeUpdate();
            con.close();
            response = "Conexion exitosa";
        }
        catch (Exception e){
            e.printStackTrace();
            result2 = "Conexion no exitosa";
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        System.out.println(response);
    }
}
