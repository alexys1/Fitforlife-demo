package com.fitforlife.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    
    // DATOS OBTENIDOS DE TU IMAGEN DE TIDB
    private static final String HOST = "gateway01.us-east-1.prod.aws.tidbcloud.com"; 
    private static final String PORT = "4000"; 
    private static final String DB_NAME = "test"; 
    private static final String USER = "34MTXyMoRXVwyPp.root";
    
    // --- ¡IMPORTANTE! ---
    // Pega aquí abajo la contraseña que generaste en el botón "Generate Password"
    private static final String PASSWORD = "x9bdFro2J0tfmx20"; 

    // URL con seguridad SSL activada (Obligatorio para la nube)
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?sslMode=VERIFY_IDENTITY&enabledTLSProtocols=TLSv1.2,TLSv1.3";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión exitosa a la base de datos en la nube (TiDB)!"); 
        } catch (Exception e) {
            System.out.println("Error al conectar con la nube:");
            e.printStackTrace();
        }
        return conn;
    }
}