package com.example.tp_integrador_grupo5;

public class validaciones {

    public static final boolean contraseñaTamaño(int tam){
        if(tam < 8) {
            return false;
        }
        return true;
    }

    public static final boolean contraseñaActual(String act, String pass){
        if(act != pass){
            return false;
        }
        return true;
    }

}
