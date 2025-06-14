package org.example.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int id;
    private Cliente cliente;
    private List<DetalleVenta> detalles;
    private LocalDateTime fecha;
    private double total;
    private String estado; // PENDIENTE, COMPLETADA, CANCELADA
    private String notas; // Campo para observaciones de la venta

    public Venta(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
        this.fecha = LocalDateTime.now();
        this.total = 0.0;
        this.estado = "PENDIENTE";
        this.notas = "";
    }

    public void agregarProducto(Producto producto, int cantidad) {
        DetalleVenta detalle = new DetalleVenta(producto, cantidad);
        detalles.add(detalle);
        calcularTotal();
    }

    private void calcularTotal() {
        this.total = detalles.stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum();
    }

    public void completarVenta() {
        this.estado = "COMPLETADA";
        // Actualizar stock de productos
        for (DetalleVenta detalle : detalles) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() - detalle.getCantidad());
        }
    }

    public void cancelarVenta() {
        this.estado = "CANCELADA";
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
} 