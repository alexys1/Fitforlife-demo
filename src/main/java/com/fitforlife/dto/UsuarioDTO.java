package com.fitforlife.dto;

import java.sql.Timestamp;

public class UsuarioDTO {
    private int idUsuario;
    private String nombre;
    private String email;
    private String password;
    private String sexo;
    private int edad;
    private double altura;
    private double pesoActual; // corresponde a "peso_inicial" en la BD
    private Timestamp fechaRegistro;
    private String fotoPerfil;

    // Constructor vacío
    public UsuarioDTO() {}

    // Constructor completo
    public UsuarioDTO(int idUsuario, String nombre, String email, String password,
                      String sexo, int edad, double altura, double pesoActual, Timestamp fechaRegistro, String fotoPerfil) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.sexo = sexo;
        this.edad = edad;
        this.altura = altura;
        this.pesoActual = pesoActual;
        this.fechaRegistro = fechaRegistro;
        this.fotoPerfil= fotoPerfil;
    }
public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPesoActual() {
        return pesoActual;
    }

    public void setPesoActual(double pesoActual) {
        this.pesoActual = pesoActual;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
