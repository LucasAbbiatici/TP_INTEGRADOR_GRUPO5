package com.example.tp_integrador_grupo5.entidades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Ubicacion implements ClusterItem {

    private int id;
    private Usuario usuario;
    private double latitud;
    private double longitud;
    private int cant_personas;
    private boolean mascotas;
    private boolean ancianos;
    private boolean ninios;
    private boolean discapacitados;
    private String comentarios;
    private int cant_reportes;
    private boolean estado;

    private LatLng position;
    private String title;
    private String snippet;

    public Ubicacion(){
        this.cant_reportes = 0;
        this.estado = true;
    }

    public Ubicacion(int id, Usuario usuario, double latitud, double longitud, int cant_personas, boolean mascotas, boolean ancianos,
                     boolean ninios, boolean discapacitados, String comentarios, int cant_reportes, boolean estado) {
        this.id = id;
        this.usuario = usuario;
        this.latitud = latitud;
        this.longitud = longitud;
        this.cant_personas = cant_personas;
        this.mascotas = mascotas;
        this.ancianos = ancianos;
        this.ninios = ninios;
        this.discapacitados = discapacitados;
        this.comentarios = comentarios;
        this.cant_reportes = cant_reportes;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getCant_personas() {
        return cant_personas;
    }

    public void setCant_personas(int cant_personas) {
        this.cant_personas = cant_personas;
    }

    public boolean isMascotas() {
        return mascotas;
    }

    public void setMascotas(boolean mascotas) {
        this.mascotas = mascotas;
    }

    public boolean isAncianos() {
        return ancianos;
    }

    public void setAncianos(boolean ancianos) {
        this.ancianos = ancianos;
    }

    public boolean isNinios() {
        return ninios;
    }

    public void setNinios(boolean ninios) {
        this.ninios = ninios;
    }

    public boolean isDiscapacitados() {
        return discapacitados;
    }

    public void setDiscapacitados(boolean discapacitados) {
        this.discapacitados = discapacitados;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getCant_reportes() {
        return cant_reportes;
    }

    public void setCant_reportes(int cant_reportes) {
        this.cant_reportes = cant_reportes;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", cant_personas=" + cant_personas +
                ", mascotas=" + mascotas +
                ", ancianos=" + ancianos +
                ", ninios=" + ninios +
                ", discapacitados=" + discapacitados +
                ", comentarios='" + comentarios + '\'' +
                ", cant_reportes=" + cant_reportes +
                ", estado=" + estado +
                '}';
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return position;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return snippet;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
