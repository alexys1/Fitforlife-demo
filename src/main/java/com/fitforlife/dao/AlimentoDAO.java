package com.fitforlife.dao;

import com.fitforlife.dto.AlimentoDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.fitforlife.dto.IMCRegistroDTO;

public class AlimentoDAO {
    private Connection conn;

    // Constructor que recibe la conexión (para usar en los Servlets)
    public AlimentoDAO(Connection conn) {
        this.conn = conn;
    }

    // Listar alimentos
    public List<AlimentoDTO> listarAlimentos() {
        List<AlimentoDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM Alimento";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new AlimentoDTO(
                        rs.getInt("id_alimento"),
                        rs.getString("nombre"),
                        rs.getInt("calorias"),
                        rs.getDouble("proteinas"),
                        rs.getDouble("carbohidratos"),
                        rs.getDouble("grasas"),
                        rs.getString("porcion"),
                        rs.getString("categoria"),
                        rs.getString("etiquetaSalud")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Registrar alimento
    public boolean registrarAlimento(AlimentoDTO alimento) {
        String sql = "INSERT INTO Alimento (nombre, calorias, proteinas, carbohidratos, grasas, porcion, categoria , etiqueta_salud) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, alimento.getNombre());
            ps.setInt(2, alimento.getCalorias());
            ps.setDouble(3, alimento.getProteinas());
            ps.setDouble(4, alimento.getCarbohidratos());
            ps.setDouble(5, alimento.getGrasas());
            ps.setString(6, alimento.getPorcion());
            ps.setString(7, alimento.getCategoria());
            ps.setString(8, alimento.getEtiquetaSalud());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Dentro de la clase AlimentoDAO

public List<AlimentoDTO> listarAlimentosPorCategoria(String categoria) {
    List<AlimentoDTO> lista = new ArrayList<>();
    String sql = "SELECT * FROM Alimento WHERE categoria = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, categoria);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            lista.add(new AlimentoDTO(
                    rs.getInt("id_alimento"),
                    rs.getString("nombre"),
                    rs.getInt("calorias"),
                    rs.getDouble("proteinas"),
                    rs.getDouble("carbohidratos"),
                    rs.getDouble("grasas"),
                    rs.getString("porcion"),
                    rs.getString("categoria"),
                    rs.getString("etiquetaSalud")
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}
public List<AlimentoDTO> listarAlimentosRecomendados(String categoria, String condicionUsuario) {
    List<AlimentoDTO> lista = new ArrayList<>();
    String sqlBase = "SELECT * FROM Alimento WHERE categoria = ? ";
    
    // Lógica de recomendación
    String orderBySql = "ORDER BY CASE " +
                        "WHEN ? = 'Bajo peso' AND etiqueta_salud = 'Alto en Proteína' THEN 0 " +
                        "WHEN (? = 'Sobrepeso' OR ? = 'Obesidad') AND (etiqueta_salud = 'Bajo en Grasa' OR etiqueta_salud = 'Bajo en Carbs') THEN 0 " +
                        "WHEN ? = 'Normal' AND etiqueta_salud = 'Normal' THEN 0 " +
                        "ELSE 1 " +
                        "END, nombre ASC";

    try (PreparedStatement ps = conn.prepareStatement(sqlBase + orderBySql)) {
        // Parámetros para el WHERE
        ps.setString(1, categoria);
        
        // Parámetros para el ORDER BY (se repiten)
        ps.setString(2, condicionUsuario);
        ps.setString(3, condicionUsuario);
        ps.setString(4, condicionUsuario);
        ps.setString(5, condicionUsuario);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            AlimentoDTO alimento = new AlimentoDTO();
            alimento.setIdAlimento(rs.getInt("id_alimento"));
            alimento.setNombre(rs.getString("nombre"));
            alimento.setCalorias(rs.getInt("calorias"));
            alimento.setProteinas(rs.getDouble("proteinas"));
            alimento.setCarbohidratos(rs.getDouble("carbohidratos"));
            alimento.setGrasas(rs.getDouble("grasas"));
            alimento.setPorcion(rs.getString("porcion"));
            alimento.setCategoria(rs.getString("categoria"));
            alimento.setEtiquetaSalud(rs.getString("etiqueta_salud")); // <-- Llenamos el nuevo campo
            lista.add(alimento);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}
}
