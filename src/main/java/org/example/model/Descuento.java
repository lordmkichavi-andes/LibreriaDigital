package org.example.model;

import java.time.LocalDate;

public class Descuento {
    private int id;
    private String codigo;
    private double porcentaje;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String tipo; // "CLIENTE", "PRODUCTO", "CATEGORIA"
    private int referenciaId; // ID del cliente, producto o categoría
    private boolean activo;

    public Descuento(int id, String codigo, double porcentaje, LocalDate fechaInicio, 
                    LocalDate fechaFin, String tipo, int referenciaId) {
        this.id = id;
        this.codigo = codigo;
        this.porcentaje = porcentaje;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipo = tipo;
        this.referenciaId = referenciaId;
        this.activo = true;
    }

    public boolean esValido() {
        LocalDate hoy = LocalDate.now();
        return activo && !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
    }

    public double calcularDescuento(double monto) {
        return monto * (porcentaje / 100.0);
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(int referenciaId) {
        this.referenciaId = referenciaId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
} 