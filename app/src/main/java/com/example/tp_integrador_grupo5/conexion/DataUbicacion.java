package com.example.tp_integrador_grupo5.conexion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.tp_integrador_grupo5.R;
import com.example.tp_integrador_grupo5.activities.IReloadMapa;
import com.example.tp_integrador_grupo5.activities.MapsActivity;
import com.example.tp_integrador_grupo5.entidades.Ubicacion;
import com.example.tp_integrador_grupo5.entidades.Usuario;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataUbicacion extends AsyncTask<String, Void, String> {
    private Ubicacion ubicacion;
    private Context context;
    private ArrayList<Ubicacion> listaUbicaciones;
    private Usuario usuario;
    private GoogleMap mMap;
    private int idUbicacion;
    private DataDatosDeUbicacion dlu;
    private int idUsuario;
    private int cant;
    private TextView cant_ubicaciones;
    private IReloadMapa rm;

    public DataUbicacion(Ubicacion ubicacion, Context context) {
        this.ubicacion = ubicacion;
        this.context = context;
    }

    public DataUbicacion(GoogleMap map, Usuario usuario, Context context, IReloadMapa rm){
        this.context = context;
        this.mMap = map;
        this.usuario = usuario;
        this.rm = rm;
    }

    public DataUbicacion(Context context, int idUsuario, TextView cant_ubicaciones){
        this.context = context;
        this.idUsuario = idUsuario;
        this.cant_ubicaciones = cant_ubicaciones;
    }

    public DataUbicacion(Context context, int idUbicacion){
        this.context = context;
        this.idUbicacion = idUbicacion;
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

                Usuario user = new Usuario();

                listaUbicaciones = new ArrayList<Ubicacion>();

                String query = "SELECT * FROM Ubicaciones WHERE Estado = 1";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);

                while(rs.next()){
                    ubicacion = new Ubicacion();

                    ubicacion.setId(rs.getInt("ID_Ubicacion"));
                    user.setId(rs.getInt("ID_Usuario"));
                    ubicacion.setUsuario(user);
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

                    listaUbicaciones.add(ubicacion);

                }
                response = "Ubicaciones cargadas";

            }

            if(strings[0] == "ubicacionesXusuario") {

                String query = "SELECT COUNT(*) AS cant FROM Ubicaciones WHERE ID_Usuario = " + idUsuario;
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery(query);

                if(rs.next()){
                    cant = rs.getInt("cant");
                }

                response = "Cantidad de ubicaciones cargadas";

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
        System.out.println(response);
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
                ClusterManager clusterManager = new ClusterManager<Ubicacion>(context, mMap);

                clusterManager.addItems(listaUbicaciones);

                mMap.setOnCameraIdleListener(clusterManager);
                mMap.setOnMarkerClickListener(clusterManager);

                clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener() {
                    @Override
                    public boolean onClusterItemClick(ClusterItem item) {
                        idUbicacion = Integer.parseInt(item.getTitle());
                        armarDialog();
                        return false;
                    }
                });

                break;

            case "Cantidad de ubicaciones cargadas":
                cant_ubicaciones.setText(String.valueOf(cant));
                break;
        }
    }

    private void armarDialog(){

        LayoutInflater inflater = LayoutInflater.from(context);
        View popupWindow = inflater.inflate(R.layout.dialog_datosubicacion,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setView(popupWindow);

        AlertDialog dialog = builder.create();
        dialog.show();

        ImageView ic_ninios = (ImageView) dialog.findViewById(R.id.ic_ninos);
        ImageView ic_discapacitados = (ImageView) dialog.findViewById(R.id.ic_discapacitados);
        ImageView ic_mascotas = (ImageView) dialog.findViewById(R.id.ic_mascotas);
        ImageView ic_ancianos = (ImageView) dialog.findViewById(R.id.ic_ancianos);

        TextView tv_cant = (TextView) dialog.findViewById(R.id.tv_variableCantPersonas);

        EditText et_comentarios = (EditText) dialog.findViewById(R.id.et_comentariosGenerales);

        ImageButton btn_reportar = (ImageButton) dialog.findViewById(R.id.btn_report);
        ImageButton btn_share = (ImageButton) dialog.findViewById(R.id.btn_share);
        ImageButton btn_maps = (ImageButton) dialog.findViewById(R.id.btn_maps);

        dlu = new DataDatosDeUbicacion(context, ic_ninios, ic_discapacitados, ic_mascotas, ic_ancianos, tv_cant, et_comentarios, idUbicacion);
        dlu.execute("datosUbicacion");

        btn_reportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertaReporte;

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Por favor, sólo reporte la ubicación si la persona no se encuentra en el lugar.").setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataReporte dataReporte = new DataReporte(context, idUbicacion ,usuario, rm);
                        dataReporte.execute("reportar");
                    }
                }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setTitle("¿QUIERE REPORTAR ESTA UBICACION?");

                alertaReporte = builder.create();
                alertaReporte.show();
                
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dlu = new DataDatosDeUbicacion(context, idUbicacion);
               dlu.execute("Compartir");
            }
        });

        btn_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);

                builder2.setMessage("Se abrirá la aplicación Google Maps. ¿Desea continuar?").setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dlu = new DataDatosDeUbicacion(context, idUbicacion);
                        dlu.execute("Maps");
                    }
                }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setTitle("Ir a Google Maps");

                AlertDialog dialog2 = builder2.create();
                dialog2.show();
            }
        });

    }

}
