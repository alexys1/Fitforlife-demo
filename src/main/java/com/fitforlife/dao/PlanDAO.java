
package com.fitforlife.dao;

import com.fitforlife.dto.PlanDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {
    private Connection conn;

    public PlanDAO(Connection conn) {
        this.conn = conn;
    }

    public List<PlanDTO> listarPlanes() {
        List<PlanDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM Planes";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new PlanDTO(
                        rs.getInt("id_plan"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("calorias_recomendadas")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    // Dentro de la clase PlanDAO

public PlanDTO obtenerPlanPorUsuario(int idUsuario) {
    String sql = "SELECT p.* FROM Planes p " +
                 "JOIN Usuario_Plan up ON p.id_plan = up.id_plan " +
                 "WHERE up.id_usuario = ? AND up.activo = TRUE";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new PlanDTO(
                    rs.getInt("id_plan"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("calorias_recomendadas")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; // Devuelve null si no se encuentra un plan activo
}
public PlanDTO obtenerPlanPorTipo(String tipo) {
    String sql = "SELECT * FROM Planes WHERE tipo = ? LIMIT 1";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, tipo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new PlanDTO(
                    rs.getInt("id_plan"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("calorias_recomendadas")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
}
