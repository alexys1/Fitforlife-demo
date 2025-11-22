package com.fitforlife.dao;

import com.fitforlife.dto.ProgresoDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgresoDAO {
    private Connection conn;

    public ProgresoDAO(Connection conn) {
        this.conn = conn;
    }

    // Insertar un nuevo registro de progreso
    public boolean registrarProgreso(ProgresoDTO progreso) {
        String sql = "INSERT INTO Registro_Progreso (id_usuario, peso_meta, peso_semana, semana) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, progreso.getIdUsuario());
            ps.setDouble(2, progreso.getPesoMeta());
            ps.setDouble(3, progreso.getPesoSemana());
            ps.setInt(4, progreso.getSemana());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Listar todo el historial de progreso de un usuario
    // MODIFICADO: Ordenado por fecha descendente para obtener el más reciente primero.
    public List<ProgresoDTO> listarProgresoPorUsuario(int idUsuario) {
        List<ProgresoDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM Registro_Progreso WHERE id_usuario = ? ORDER BY fecha DESC LIMIT 10";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ProgresoDTO(
                        rs.getInt("id_progreso"),
                        rs.getInt("id_usuario"),
                        rs.getDouble("peso_meta"),
                        rs.getDouble("peso_semana"),
                        rs.getInt("semana"),
                        rs.getTimestamp("fecha")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ==========================================================
    // == MÉTODOS NUEVOS PARA LA LÓGICA DE EDICIÓN DIARIA ==
    // ==========================================================

    /**
     * Busca si ya existe un registro de progreso para un usuario en el día de hoy.
     * @param idUsuario El ID del usuario.
     * @return El ProgresoDTO de hoy si existe, de lo contrario null.
     */
    public ProgresoDTO obtenerProgresoDeHoy(int idUsuario) {
        String sql = "SELECT * FROM Registro_Progreso WHERE id_usuario = ? AND DATE(fecha) = CURDATE() LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ProgresoDTO(
                        rs.getInt("id_progreso"),
                        rs.getInt("id_usuario"),
                        rs.getDouble("peso_meta"),
                        rs.getDouble("peso_semana"),
                        rs.getInt("semana"),
                        rs.getTimestamp("fecha")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actualiza el peso de un registro de progreso existente.
     * @param idProgreso El ID del registro a actualizar.
     * @param nuevoPeso El nuevo valor del peso.
     * @return true si la actualización fue exitosa.
     */
    public boolean actualizarProgreso(int idProgreso, double nuevoPeso) {
        String sql = "UPDATE Registro_Progreso SET peso_semana = ? WHERE id_progreso = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, nuevoPeso);
            ps.setInt(2, idProgreso);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}