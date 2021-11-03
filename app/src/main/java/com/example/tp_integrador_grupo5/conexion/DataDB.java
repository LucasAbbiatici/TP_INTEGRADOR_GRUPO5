package com.example.tp_integrador_grupo5.conexion;

public class DataDB {

    //Información de la BD
    public static String host="sql10.freesqldatabase.com";
    public static String port="3306";
    public static String nameBD="sql10448656";
    public static String user="sql10448656";
    public static String pass="itPS3z8TJ7";

    //Información para la conexion
    public static String urlMySQL = "jdbc:mysql://" + host + ":" + port + "/" + nameBD;
    public static String driver = "com.mysql.jdbc.Driver";

}
