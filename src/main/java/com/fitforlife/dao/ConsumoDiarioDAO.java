
package com.fitforlife.dao;

import com.fitforlife.dto.ConsumoDiarioDTO;
import java.sql.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class ConsumoDiarioDAO {
    private Connection conn;

    public ConsumoDiarioDAO(Connection conn) {
        this.conn = conn;
    }

    // INSERTAR consumo
    public boolean insertarConsumo(ConsumoDiarioDTO consumo) {
        String sql = "INSERT INTO Consumo_Diario (id_usuario, id_alimento, cantidad, fecha) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, consumo.getIdUsuario());
            ps.setInt(2, consumo.getIdAlimento());
            ps.setDouble(3, consumo.getCantidad());
            ps.setTimestamp(4, consumo.getFecha());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // LISTAR consumos por usuario
    public List<ConsumoDiarioDTO> listarConsumosPorUsuario(int idUsuario) {
        List<ConsumoDiarioDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM Consumo_Diario WHERE id_usuario = ? ORDER BY fecha DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ConsumoDiarioDTO consumo = new ConsumoDiarioDTO(
                        rs.getInt("id_consumo"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_alimento"),
                        rs.getDouble("cantidad"),
                        rs.getTimestamp("fecha")
                );
                lista.add(consumo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ELIMINAR consumo
    public boolean eliminarConsumo(int idConsumo) {
        String sql = "DELETE FROM Consumo_Diario WHERE id_consumo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idConsumo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Map<String, Double> obtenerResumenConsumoHoy(int idUsuario) {
    Map<String, Double> resumen = new HashMap<>();
    // Inicializamos el mapa con ceros
    resumen.put("totalCalorias", 0.0);
    resumen.put("totalProteinas", 0.0);
    resumen.put("totalCarbohidratos", 0.0);
    resumen.put("totalGrasas", 0.0);

    // Esta consulta SQL une el consumo diario con la tabla de alimentos y suma los totales,
    // multiplicando los nutrientes por la cantidad consumida.
    String sql = "SELECT " +
                 "SUM(a.calorias * cd.cantidad) AS totalCalorias, " +
                 "SUM(a.proteinas * cd.cantidad) AS totalProteinas, " +
                 "SUM(a.carbohidratos * cd.cantidad) AS totalCarbohidratos, " +
                 "SUM(a.grasas * cd.cantidad) AS totalGrasas " +
                 "FROM Consumo_Diario cd " +
                 "JOIN Alimento a ON cd.id_alimento = a.id_alimento " +
                 "WHERE cd.id_usuario = ? AND DATE(cd.fecha) = CURDATE()";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Si hay resultados, actualizamos el mapa. Si no, se quedará con los ceros.
            resumen.put("totalCalorias", rs.getDouble("totalCalorias"));
            resumen.put("totalProteinas", rs.getDouble("totalProteinas"));
            resumen.put("totalCarbohidratos", rs.getDouble("totalCarbohidratos"));
            resumen.put("totalGrasas", rs.getDouble("totalGrasas"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resumen;
}
}

