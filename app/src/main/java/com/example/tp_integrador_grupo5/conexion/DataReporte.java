package com.example.tp_integrador_grupo5.conexion;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp_integrador_grupo5.activities.IReloadMapa;
import com.example.tp_integrador_grupo5.activities.MapsActivity;
import com.example.tp_integrador_grupo5.entidades.Ubicacion;
import com.example.tp_integrador_grupo5.entidades.Usuario;
import com.google.android.gms.maps.GoogleMap;
import com.mysql.fabric.xmlrpc.base.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class DataReporte extends AsyncTask<String, Void, String> {

    private Context context;
    private int idUbicacion;
    private TextView cant_reportes;
    private int cant;
    private DataUbicacion du;
    private Usuario usuario;
    private IReloadMapa rm;

    public DataReporte(Context context, int idUbicacion, Usuario usuario, IReloadMapa rm) {
        this.context = context;
        this.idUbicacion = idUbicacion;
        this.usuario = usuario;
        this.rm = rm;
    }

    public DataReporte(Context context, Usuario usuario, TextView cant_reportes) {
        this.context = context;
        this.usuario = usuario;
        this.cant_reportes = cant_reportes;
    }

    @Override
    protected String doInBackground(String... strings) {

        String response = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            if(strings[0] == "reportar") {

                String query = "INSERT INTO Reportes Values (?,?);";
                PreparedStatement pst = con.prepareStatement(query);

                pst.setInt(1, usuario.getId());
                pst.setInt(2, idUbicacion);

                pst.executeUpdate();

                String query2 = "UPDATE Ubicaciones SET Cantidad_Reportes = (Cantidad_Reportes + 1) WHERE ID_Ubicacion = ? ;";

                pst = con.prepareStatement(query2);
                pst.setInt(1, idUbicacion);

                pst.executeUpdate();

                String query3 = "SELECT Cantidad_Reportes FROM Ubicaciones WHERE ID_Ubicacion = " + idUbicacion;
                Statement st2 = con.createStatement();
                ResultSet rs = st2.executeQuery(query3);

                if(rs.next()){
                    if(rs.getInt("Cantidad_Reportes") == 10){

                        String query4 = "UPDATE Ubicaciones SET Estado = 0 WHERE ID_Ubicacion = ? ;";
                        PreparedStatement pst2 = con.prepareStatement(query4);

                        pst2.setInt(1, idUbicacion);

                        pst2.executeUpdate();

                    }
                }


                response = "Ubicacion reportada";

            }

            if(strings[0] == "reportesXusuario") {

                String query = "SELECT COUNT(*) AS cant FROM Reportes WHERE ID_Usuario = " + usuario.getId();
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
            rm.reloadMapa();
        }

        if(response == "Error en la conexion"){
            Toast.makeText(context, "Ya reportaste esta ubicación.", Toast.LENGTH_LONG).show();
        }

        if(response == "Cantidad de reportes cargada"){
            cant_reportes.setText(String.valueOf(cant));
        }

    }
}
