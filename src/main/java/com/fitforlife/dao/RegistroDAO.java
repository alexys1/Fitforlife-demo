
package com.fitforlife.dao;


import com.fitforlife.dto.RegistroDTO;
import com.fitforlife.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroDAO {

    // Insertar un nuevo registro
    public boolean registrar(RegistroDTO registro) {
        String sql = "INSERT INTO Registro (id_usuario, peso, altura, edad, sexo, fecha_registro) VALUES (?, ?, ?, ?, ?, NOW())";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, registro.getIdUsuario());
            ps.setDouble(2, registro.getPeso());
            ps.setDouble(3, registro.getAltura());
            ps.setInt(4, registro.getEdad());
            ps.setString(5, registro.getSexo());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en registrar: " + e.getMessage());
            return false;
        }
    }

    // Obtener registro por id_usuario
    public RegistroDTO obtenerPorUsuario(int idUsuario) {
        String sql = "SELECT * FROM Registro WHERE id_usuario = ? ORDER BY fecha_registro DESC LIMIT 1";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RegistroDTO registro = new RegistroDTO();
                    registro.setIdRegistro(rs.getInt("id_registro"));
                    registro.setIdUsuario(rs.getInt("id_usuario"));
                    registro.setPeso(rs.getDouble("peso"));
                    registro.setAltura(rs.getDouble("altura"));
                    registro.setEdad(rs.getInt("edad"));
                    registro.setSexo(rs.getString("sexo"));
                    registro.setFechaRegistro(rs.getString("fecha_registro"));
                    return registro;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en obtenerPorUsuario: " + e.getMessage());
        }
        return null;
    }

    // Listar todos los registros
    public List<RegistroDTO> listar() {
        List<RegistroDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM Registro";
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                RegistroDTO registro = new RegistroDTO();
                registro.setIdRegistro(rs.getInt("id_registro"));
                registro.setIdUsuario(rs.getInt("id_usuario"));
                registro.setPeso(rs.getDouble("peso"));
                registro.setAltura(rs.getDouble("altura"));
                registro.setEdad(rs.getInt("edad"));
                registro.setSexo(rs.getString("sexo"));
                registro.setFechaRegistro(rs.getString("fecha_registro"));
                lista.add(registro);
            }
        } catch (SQLException e) {
            System.out.println("Error en listar: " + e.getMessage());
        }
        return lista;
    }

    // Actualizar un registro
    public boolean actualizar(RegistroDTO registro) {
        String sql = "UPDATE Registro SET peso = ?, altura = ?, edad = ?, sexo = ? WHERE id_registro = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, registro.getPeso());
            ps.setDouble(2, registro.getAltura());
            ps.setInt(3, registro.getEdad());
            ps.setString(4, registro.getSexo());
            ps.setInt(5, registro.getIdRegistro());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en actualizar: " + e.getMessage());
            return false;
        }
    }

    // Eliminar un registro
    public boolean eliminar(int idRegistro) {
        String sql = "DELETE FROM Registro WHERE id_registro = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idRegistro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en eliminar: " + e.getMessage());
            return false;
        }
    }
}
