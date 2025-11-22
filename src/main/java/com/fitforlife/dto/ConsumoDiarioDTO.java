
package com.fitforlife.dto;


import java.sql.Timestamp;

public class ConsumoDiarioDTO {
    private int idConsumo;
    private int idUsuario;
    private int idAlimento;
    private double cantidad; // cuántas porciones consumió
    private Timestamp fecha;

    public ConsumoDiarioDTO() {}

    public ConsumoDiarioDTO(int idConsumo, int idUsuario, int idAlimento, double cantidad, Timestamp fecha) {
        this.idConsumo = idConsumo;
        this.idUsuario = idUsuario;
        this.idAlimento = idAlimento;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getIdConsumo() { return idConsumo; }
    public void setIdConsumo(int idConsumo) { this.idConsumo = idConsumo; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdAlimento() { return idAlimento; }
    public void setIdAlimento(int idAlimento) { this.idAlimento = idAlimento; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
}
