package com.fitforlife.dao;

import com.fitforlife.dto.UsuarioDTO;
import java.sql.*;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * CORREGIDO: Registra un nuevo usuario, incluyendo el campo foto_perfil (que será null al inicio).
     * Devuelve el ID del nuevo usuario.
     */
    public int registrarUsuario(UsuarioDTO usuario) {
        String sql = "INSERT INTO Usuarios (nombre, email, password, sexo, edad, altura, peso_actual, foto_perfil) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getSexo()); // Asumiendo que getSexo() devuelve String ("M" o "F")
            ps.setInt(5, usuario.getEdad());
            ps.setDouble(6, usuario.getAltura());
            ps.setDouble(7, usuario.getPesoActual());
            ps.setString(8, usuario.getFotoPerfil()); // Se insertará null para nuevos usuarios

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Devuelve el nuevo ID
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Devuelve 0 si hubo un error
    }
    
    /**
     * Actualiza los datos principales de un usuario en su perfil.
     */
    public boolean actualizarUsuario(UsuarioDTO usuario) {
        String sql = "UPDATE Usuarios SET nombre = ?, edad = ?, altura = ?, peso_actual = ? WHERE id_usuario = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setInt(2, usuario.getEdad());
            ps.setDouble(3, usuario.getAltura());
            ps.setDouble(4, usuario.getPesoActual());
            ps.setInt(5, usuario.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * CORRECCIÓN PRINCIPAL: El método login ahora lee la columna 'foto_perfil'
     * y la guarda en el objeto UsuarioDTO.
     */
    public UsuarioDTO login(String email, String password) {
        String sql = "SELECT * FROM Usuarios WHERE email = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Usamos setters para mayor claridad y evitar errores con el constructor
                UsuarioDTO usuario = new UsuarioDTO();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword(rs.getString("password"));
                usuario.setSexo(rs.getString("sexo"));
                usuario.setEdad(rs.getInt("edad"));
                usuario.setAltura(rs.getDouble("altura"));
                usuario.setPesoActual(rs.getDouble("peso_actual"));
                usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                // ¡LÍNEA CLAVE! Leemos el nombre del archivo de la foto
                usuario.setFotoPerfil(rs.getString("foto_perfil")); 
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actualiza únicamente el campo de la foto de perfil para un usuario.
     */
    public boolean actualizarFotoPerfil(int idUsuario, String nombreArchivo) {
        String sql = "UPDATE Usuarios SET foto_perfil = ? WHERE id_usuario = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreArchivo);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
