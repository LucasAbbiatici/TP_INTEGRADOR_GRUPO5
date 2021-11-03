package com.example.tp_integrador_grupo5.entidades;

public class Reporte {

    private Usuario usuario;
    private Ubicacion ubicacion;
    private boolean estado;

    public Reporte(){}

    public Reporte(Usuario usuario, Ubicacion ubicacion, boolean estado) {
        this.usuario = usuario;
        this.ubicacion = ubicacion;
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "usuario=" + usuario +
                ", ubicacion=" + ubicacion +
                ", estado=" + estado +
                '}';
    }
}
