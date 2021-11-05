package com.example.tp_integrador_grupo5.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp_integrador_grupo5.entidades.Ubicacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class DataReporte extends AsyncTask<String, Void, String> {

    private Context context;
    private int idUbicacion;
    private int idUsuario;
    private TextView cant_reportes;
    private int cant;

    public DataReporte(Context context, int idUbicacion, int idUsuario) {
        this.context = context;
        this.idUbicacion = idUbicacion;
        this.idUsuario = idUsuario;
    }

    public DataReporte(Context context, int idUsuario, TextView cant_reportes) {
        this.context = context;
        this.idUsuario = idUsuario;
        this.cant_reportes = cant_reportes;
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

            if(strings[0] == "reportesXusuario") {

                String query = "SELECT COUNT(*) AS cant FROM Reportes WHERE ID_Usuario = " + idUsuario;
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery(query);

                if(rs.next()){
                    cant = rs.getInt("cant");
                }

                response = "Cantidad de reportes cargada";

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

        if(response == "Cantidad de reportes cargada"){
            cant_reportes.setText(String.valueOf(cant));
        }

    }
}
