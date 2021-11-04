package com.example.tp_integrador_grupo5.conexion;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import com.example.tp_integrador_grupo5.activities.MapsActivity;
import com.example.tp_integrador_grupo5.entidades.Ubicacion;
import com.example.tp_integrador_grupo5.entidades.Usuario;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class DataUbicacion extends AsyncTask<String, Void, String> {
    private Ubicacion ubicacion;
    private Context context;
    private ArrayList<Ubicacion> listaUbicaciones;
    private Usuario usuario;
    private GoogleMap mMap;

    public DataUbicacion(Ubicacion ubicacion, Context context) {
        this.ubicacion = ubicacion;
        this.context = context;
    }

    public DataUbicacion(GoogleMap map, Context context){
        this.context = context;
        this.mMap = map;
    }

    public DataUbicacion(Context context){
        this.context = context;
    }

    public ArrayList<Ubicacion> getListaUbicaciones(){
        return listaUbicaciones;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            if(strings[0] == "agregar") {

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

                response = "Ubicacion agregada correctamente";

            }


            if(strings[0] == "listar"){

                usuario = new Usuario();
                ubicacion = new Ubicacion();

                listaUbicaciones = new ArrayList<Ubicacion>();

                String query = "SELECT * FROM Ubicaciones WHERE Estado = '1';";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);

                while(rs.next()){
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

                    listaUbicaciones.add(ubicacion);
                    System.out.println("data ubicacion: ");
                    System.out.println(listaUbicaciones);
                }

                response = "Ubicaciones cargadas";

            }

            con.close();

        }catch (Exception e){
            e.printStackTrace();
            response = "Error en la conexion";

        }

        return response;
    }


    @Override
    protected void onPostExecute(@NonNull String response){
        switch(response) {
            case "Ubicacion agregada correctamente":
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("usuario", ubicacion.getUsuario());
                context.startActivity(i);
                break;
            case "Error en la conexion":
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                break;

            case "Ubicaciones cargadas":
                LatLng posicion;
                for(Ubicacion u:listaUbicaciones){
                    posicion = new LatLng(u.getLatitud(),u.getLongitud());
                    mMap.addMarker(new MarkerOptions().position(posicion).title(String.valueOf(u.getId())));
                }
                break;
        }
    }
}
