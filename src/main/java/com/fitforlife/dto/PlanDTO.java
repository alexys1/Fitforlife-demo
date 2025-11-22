package com.fitforlife.dto;

public class PlanDTO {
    private int idPlan;
    private String nombre;
    private String descripcion;
    private int caloriasRecomendadas;

    // Constructor vacío
    public PlanDTO() {}

    // Constructor con parámetros (el que usa tu DAO)
    public PlanDTO(int idPlan, String nombre, String descripcion, int caloriasRecomendadas) {
        this.idPlan = idPlan;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.caloriasRecomendadas = caloriasRecomendadas;
    }

    // Getters y Setters
    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCaloriasRecomendadas() {
        return caloriasRecomendadas;
    }

    public void setCaloriasRecomendadas(int caloriasRecomendadas) {
        this.caloriasRecomendadas = caloriasRecomendadas;
    }
}

