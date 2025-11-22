package com.fitforlife.dto;

import java.sql.Timestamp;

public class ProgresoDTO {
    private int idProgreso;
    private int idUsuario;
    private double pesoMeta;
    private double pesoSemana;
    private int semana;
    private Timestamp fecha;

    // Constructor vacío
    public ProgresoDTO() {}

    // Constructor con todos los campos
    public ProgresoDTO(int idProgreso, int idUsuario, double pesoMeta, double pesoSemana, int semana, Timestamp fecha) {
        this.idProgreso = idProgreso;
        this.idUsuario = idUsuario;
        this.pesoMeta = pesoMeta;
        this.pesoSemana = pesoSemana;
        this.semana = semana;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getIdProgreso() { return idProgreso; }
    public void setIdProgreso(int idProgreso) { this.idProgreso = idProgreso; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public double getPesoMeta() { return pesoMeta; }
    public void setPesoMeta(double pesoMeta) { this.pesoMeta = pesoMeta; }

    public double getPesoSemana() { return pesoSemana; }
    public void setPesoSemana(double pesoSemana) { this.pesoSemana = pesoSemana; }

    public int getSemana() { return semana; }
    public void setSemana(int semana) { this.semana = semana; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
}
