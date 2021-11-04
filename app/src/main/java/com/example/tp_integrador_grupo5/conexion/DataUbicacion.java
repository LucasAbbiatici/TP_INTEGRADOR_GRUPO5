package com.example.tp_integrador_grupo5.conexion;

import android.content.Context;
import android.os.AsyncTask;

import com.example.tp_integrador_grupo5.entidades.Ubicacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.xml.transform.Result;

public class DataUbicacion extends AsyncTask<String, Void, String> {
    private Ubicacion ubicacion;
    private Context context;

    public DataUbicacion(Ubicacion ubicacion, Context context) {
        this.ubicacion = ubicacion;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            String query = "INSERT INTO Ubicaciones (ID_Usuario, Latitud, Longitud, Cantidad_Personas, " +
                    "Mascotas, Ancianos, Ninios, Discapacitados, Comentarios, Cantidad_Reportes, Estado) Values (?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1,ubicacion.getUsuario().getId());
            st.setDouble(2,ubicacion.getLatitud());
            st.setDouble(3,ubicacion.getLongitud());
            st.setInt(4,ubicacion.getCant_personas());
            st.setBoolean(5,ubicacion.isMascotas());
            st.setBoolean(6,ubicacion.isAncianos());
            st.setBoolean(7,ubicacion.isNinios());
            st.setBoolean(8,ubicacion.isDiscapacitados());
            st.setString(9,ubicacion.getComentarios());
            st.setInt(10,ubicacion.getCant_reportes());
            st.setBoolean(11,ubicacion.isEstado());

            st.executeUpdate();
            con.close();
            response = "Ubicacion Agregada Correctamente";

        }catch (Exception e){
            e.printStackTrace();
            response = "Error en la conexión";
        }

        return response;
    }


    @Override
    protected void onPostExecute(String response){

    }
}
