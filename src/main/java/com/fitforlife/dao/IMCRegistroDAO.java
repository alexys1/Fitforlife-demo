
package com.fitforlife.dao;

import com.fitforlife.dto.IMCRegistroDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IMCRegistroDAO {
    private Connection conn;

    public IMCRegistroDAO(Connection conn) {
        this.conn = conn;
    }

    // Insertar nuevo registro de IMC
   public boolean registrarIMC(IMCRegistroDTO registro) {
    // SQL corregido: Eliminamos la columna "fecha" para que la BD use el valor por defecto
    String sql = "INSERT INTO IMC_Registros (id_usuario, peso, altura, imc, estado) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, registro.getIdUsuario());
        ps.setDouble(2, registro.getPeso());
        ps.setDouble(3, registro.getAltura());
        ps.setDouble(4, registro.getImc());
        ps.setString(5, registro.getEstado());
        // Ya no enviamos la fecha: ps.setTimestamp(6, registro.getFecha());

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


public IMCRegistroDTO obtenerUltimoIMCporUsuario(int idUsuario) {
    String sql = "SELECT * FROM IMC_Registros WHERE id_usuario = ? ORDER BY fecha DESC LIMIT 1";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            IMCRegistroDTO registro = new IMCRegistroDTO();
            registro.setIdImc(rs.getInt("id_imc"));
            registro.setIdUsuario(rs.getInt("id_usuario"));
            registro.setPeso(rs.getDouble("peso"));
            registro.setAltura(rs.getDouble("altura"));
            registro.setImc(rs.getDouble("imc"));
            registro.setEstado(rs.getString("estado"));
            registro.setFecha(rs.getTimestamp("fecha"));
            return registro;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; // Devuelve null si el usuario no tiene registros de IMC
}
}
