package com.fitforlife.dto;

public class AlimentoDTO {
    private int idAlimento;
    private String nombre;
    private int calorias;
    private double proteinas;
    private double carbohidratos;
    private double grasas;
    private String porcion;
    private String categoria;
    private String etiquetaSalud;

    // Constructor vacío
    public AlimentoDTO() {}

    // Constructor con parámetros
    public AlimentoDTO(int idAlimento, String nombre, int calorias, double proteinas,
                       double carbohidratos, double grasas, String porcion, String categoria, String etiquetaSalud) {
        this.idAlimento = idAlimento;
        this.nombre = nombre;
        this.calorias = calorias;
        this.proteinas = proteinas;
        this.carbohidratos = carbohidratos;
        this.grasas = grasas;
        this.porcion = porcion;
        this.categoria = categoria;
        this.etiquetaSalud= etiquetaSalud;
    }
public String getEtiquetaSalud() {
        return etiquetaSalud;
    }
public void setEtiquetaSalud(String etiquetaSalud) {
        this.etiquetaSalud = etiquetaSalud;
    }
    // Getters y setters
    public int getIdAlimento() { return idAlimento; }
    public void setIdAlimento(int idAlimento) { this.idAlimento = idAlimento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCalorias() { return calorias; }
    public void setCalorias(int calorias) { this.calorias = calorias; }

    public double getProteinas() { return proteinas; }
    public void setProteinas(double proteinas) { this.proteinas = proteinas; }

    public double getCarbohidratos() { return carbohidratos; }
    public void setCarbohidratos(double carbohidratos) { this.carbohidratos = carbohidratos; }

    public double getGrasas() { return grasas; }
    public void setGrasas(double grasas) { this.grasas = grasas; }

    public String getPorcion() { return porcion; }
    public void setPorcion(String porcion) { this.porcion = porcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
