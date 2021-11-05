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
    private int idUsuario;

    public DataReporte(Context context, int idUbicacion, int idUsuario) {
        this.context = context;
        this.idUbicacion = idUbicacion;
        this.idUsuario = idUsuario;
    }

    @Override
    protected String doInBackground(String... strings) {


        String response = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            if(strings[0] == "reportar") {

                String query = "INSERT INTO Reportes Values (?,?,1);";
                PreparedStatement st = con.prepareStatement(query);

                st.setInt(1, idUsuario);
                st.setInt(2, idUbicacion);

                st.executeUpdate();

                String query2 = "UPDATE Ubicaciones SET Cantidad_Reportes = (Cantidad_Reportes + 1) WHERE ID_Ubicacion = ? ;";

                st = con.prepareStatement(query2);
                st.setInt(1, idUbicacion);

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

        if(response == "Error en la conexion"){
            Toast.makeText(context, "Ya reportaste esta ubicación.", Toast.LENGTH_LONG).show();
        }

    }
}
