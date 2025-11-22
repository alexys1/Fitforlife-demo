package com.fitforlife.dto;

public class RegistroDTO {
    private int idRegistro;
    private int idUsuario;
    private double peso;
    private double altura;
    private int edad;
    private String sexo;
    private String fechaRegistro;

    // Constructor vacío
    public RegistroDTO() {}

    // Constructor con todos los campos
    public RegistroDTO(int idRegistro, int idUsuario, double peso, double altura, int edad, String sexo, String fechaRegistro) {
        this.idRegistro = idRegistro;
        this.idUsuario = idUsuario;
        this.peso = peso;
        this.altura = altura;
        this.edad = edad;
        this.sexo = sexo;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
