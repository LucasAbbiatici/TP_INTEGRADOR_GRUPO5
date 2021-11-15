package com.example.tp_integrador_grupo5.conexion;

public class DataDB {

    //Información de la BD
    public static String host="sql10.freesqldatabase.com";
    public static String port="3306";
    public static String nameBD="sql10451383";
    public static String user="sql10451383";
    public static String pass="7u6fCjZumS";

    //Información para la conexion
    public static String urlMySQL = "jdbc:mysql://" + host + ":" + port + "/" + nameBD;
    public static String driver = "com.mysql.jdbc.Driver";

}
