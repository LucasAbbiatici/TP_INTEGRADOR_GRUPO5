package com.example.tp_integrador_grupo5.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.tp_integrador_grupo5.entidades.Ubicacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class DataReporte extends AsyncTask<String, Void, String> {

    private Context context;
    private int idUbicacion;

    public DataReporte(Context context, int id) {
        this.context = context;
        this.idUbicacion = id;
    }

    @Override
    protected String doInBackground(String... strings) {


        String response = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            if(strings[0] == "reportar") {

                String query = "UPDATE Ubicaciones SET Cantidad_Reportes = (Cantidad_Reportes + 1) WHERE ID_Ubicacion = ? ;";

                PreparedStatement st = con.prepareStatement(query);
                st.setInt(1,idUbicacion);

                st.executeUpdate();

                response = "Ubicacion reportada";

            }

            con.close();

        }catch (Exception e){
            e.printStackTrace();
            response = "Error en la conexion";

        }

        return response;

    }

    @Override
    protected void onPostExecute(String response) {

        if(response == "Ubicacion reportada"){
            Toast.makeText(context, response, Toast.LENGTH_LONG).show();
        }

    }
}
