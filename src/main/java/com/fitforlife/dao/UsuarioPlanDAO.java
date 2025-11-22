
package com.fitforlife.dao;
import com.fitforlife.dto.UsuarioPlanDTO;
import java.sql.*;

public class UsuarioPlanDAO {
    private Connection conn;

    public UsuarioPlanDAO(Connection conn) {
        this.conn = conn;
    }

    // Asignar plan a usuario
    public boolean asignarPlan(UsuarioPlanDTO dto) {
    // SQL corregido: Solo insertamos los IDs. La BD se encarga de la fecha y el estado "activo"
    String sql = "INSERT INTO Usuario_Plan (id_usuario, id_plan) VALUES (?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, dto.getIdUsuario());
        ps.setInt(2, dto.getIdPlan());
        // Ya no enviamos "activo" ni "fecha_asignacion"

        return ps.executeUpdate() > 0;
        
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
