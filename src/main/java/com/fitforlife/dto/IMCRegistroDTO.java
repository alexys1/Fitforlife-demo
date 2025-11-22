package com.fitforlife.dto;

import java.sql.Timestamp;

public class IMCRegistroDTO {

    // Atributos que coinciden con las columnas de la tabla IMC_Registros
    private int idImc;
    private int idUsuario;
    private double peso;
    private double altura;
    private double imc;
    private String estado;
    private Timestamp fecha;

    // Constructor vacío (buena práctica)
    public IMCRegistroDTO() {
    }

    // Constructor con todos los parámetros para facilitar la creación de objetos
    public IMCRegistroDTO(int idImc, int idUsuario, double peso, double altura, double imc, String estado, Timestamp fecha) {
        this.idImc = idImc;
        this.idUsuario = idUsuario;
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
        this.estado = estado;
        this.fecha = fecha;
    }

    // Getters y Setters para cada atributo

    public int getIdImc() {
        return idImc;
    }

    public void setIdImc(int idImc) {
        this.idImc = idImc;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    // (Opcional) Método toString para facilitar la depuración
    @Override
    public String toString() {
        return "IMCRegistroDTO{" +
                "idImc=" + idImc +
                ", idUsuario=" + idUsuario +
                ", peso=" + peso +
                ", altura=" + altura +
                ", imc=" + imc +
                ", estado='" + estado + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}