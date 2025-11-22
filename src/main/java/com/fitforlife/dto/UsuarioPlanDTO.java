package com.fitforlife.dto;

import java.sql.Timestamp;

public class UsuarioPlanDTO {
    private int idUsuarioPlan;
    private int idUsuario;
    private int idPlan;
    private boolean activo;
    private Timestamp fechaAsignacion;

    // Constructor vacío
    public UsuarioPlanDTO() {}

    // Constructor completo
    public UsuarioPlanDTO(int idUsuarioPlan, int idUsuario, int idPlan, boolean activo, Timestamp fechaAsignacion) {
        this.idUsuarioPlan = idUsuarioPlan;
        this.idUsuario = idUsuario;
        this.idPlan = idPlan;
        this.activo = activo;
        this.fechaAsignacion = fechaAsignacion;
    }

    // Getters y setters
    public int getIdUsuarioPlan() {
        return idUsuarioPlan;
    }

    public void setIdUsuarioPlan(int idUsuarioPlan) {
        this.idUsuarioPlan = idUsuarioPlan;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Timestamp getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Timestamp fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
