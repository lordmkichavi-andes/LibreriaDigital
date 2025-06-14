package org.example.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Reporte {
    private String tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Map<String, Object> datos;
    private List<Map<String, Object>> detalles;

    public static class Tipos {
        public static final String VENTAS_POR_PERIODO = "VENTAS_POR_PERIODO";
        public static final String PRODUCTOS_MAS_VENDIDOS = "PRODUCTOS_MAS_VENDIDOS";
        public static final String CLIENTES_MAS_FRECUENTES = "CLIENTES_MAS_FRECUENTES";
        public static final String INGRESOS_POR_CATEGORIA = "INGRESOS_POR_CATEGORIA";
        public static final String DESCUENTOS_APLICADOS = "DESCUENTOS_APLICADOS";
    }

    public Reporte() {
    }

    public Reporte(String tipo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Map<String, Object> getDatos() {
        return datos;
    }

    public void setDatos(Map<String, Object> datos) {
        this.datos = datos;
    }

    public List<Map<String, Object>> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Map<String, Object>> detalles) {
        this.detalles = detalles;
    }
} 