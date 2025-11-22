package com.fitforlife.util;

import java.sql.Connection;

public class PruebaConexionNube {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO PRUEBA DE CONEXIÓN A LA NUBE ---");
        
        // Llamamos a tu clase de conexión
        Connection conn = ConexionBD.getConnection();
        
        if (conn != null) {
            System.out.println("✅ ¡SÍ! La conexión a TiDB Cloud fue EXITOSA.");
            System.out.println("Tu usuario y contraseña son correctos.");
        } else {
            System.out.println("❌ ERROR: No se pudo conectar.");
            System.out.println("Revisa el host, usuario o contraseña en ConexionBD.java");
        }
        
        System.out.println("--- FIN DE LA PRUEBA ---");
    }
}