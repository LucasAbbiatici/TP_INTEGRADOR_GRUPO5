package com.example.tp_integrador_grupo5.conexion;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tp_integrador_grupo5.entidades.Ubicacion;
import com.example.tp_integrador_grupo5.entidades.Usuario;
import com.google.android.gms.maps.model.LatLng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataDatosDeUbicacion extends AsyncTask<String, Void, String> {

    private Usuario usuario;
    private Ubicacion ubicacion;
    private Context context;
    private ImageView ic_ninios, ic_discapacitados, ic_mascotas, ic_ancianos;
    TextView tv_cant;
    EditText et_comentarios;
    private int id;

    public DataDatosDeUbicacion(Context context, ImageView ic_ninios, ImageView ic_discapacitados, ImageView ic_mascotas, ImageView ic_ancianos, TextView tv_cant, EditText et_comentarios, int id) {
        this.ic_ninios = ic_ninios;
        this.ic_discapacitados = ic_discapacitados;
        this.ic_mascotas = ic_mascotas;
        this.ic_ancianos = ic_ancianos;
        this.tv_cant = tv_cant;
        this.et_comentarios = et_comentarios;
        this.context = context;
        this.id = id;
    }


    @Override
    protected String doInBackground(String... strings) {

        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            if (strings[0] == "datosUbicacion") {

                usuario = new Usuario();

                String query = "SELECT * FROM Ubicaciones WHERE ID_Ubicacion = " + id;
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    ubicacion = new Ubicacion();

                    ubicacion.setId(rs.getInt("ID_Ubicacion"));
                    usuario.setId(rs.getInt("ID_Usuario"));
                    ubicacion.setUsuario(usuario);
                    ubicacion.setLatitud(rs.getDouble("Latitud"));
                    ubicacion.setLongitud(rs.getDouble("Longitud"));
                    ubicacion.setCant_personas(rs.getInt("Cantidad_Personas"));
                    ubicacion.setMascotas(rs.getBoolean("Mascotas"));
                    ubicacion.setAncianos(rs.getBoolean("Ancianos"));
                    ubicacion.setNinios(rs.getBoolean("Ninios"));
                    ubicacion.setDiscapacitados(rs.getBoolean("Discapacitados"));
                    ubicacion.setComentarios(rs.getString("Comentarios"));
                    ubicacion.setCant_reportes(rs.getInt("Cantidad_Reportes"));
                    ubicacion.setEstado(rs.getBoolean("Estado"));

                    ubicacion.setPosition(new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud()));
                    ubicacion.setTitle(String.valueOf(ubicacion.getId()));
                }

                response = "Datos de ubicacion cargados";

            }
                con.close();
                return response;


            } catch(Exception e){
                e.printStackTrace();
                response = "Error en la conexion";
            }

            return response;
    }


        @Override
        protected void onPostExecute (String response){

            if(response.equals("Datos de ubicacion cargados")){
                if(ubicacion.isNinios()) ic_ninios.setColorFilter(Color.GREEN);
                if(ubicacion.isMascotas()) ic_mascotas.setColorFilter(Color.GREEN);
                if(ubicacion.isAncianos()) ic_ancianos.setColorFilter(Color.GREEN);
                if(ubicacion.isDiscapacitados()) ic_discapacitados.setColorFilter(Color.GREEN);

                tv_cant.setText(String.valueOf(ubicacion.getCant_personas()));

                et_comentarios.setText(ubicacion.getComentarios());

            }

        }
}

