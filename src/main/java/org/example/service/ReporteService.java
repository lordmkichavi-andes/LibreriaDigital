package org.example.service;

import org.example.model.Reporte;
import org.example.model.Venta;
import org.example.model.Producto;
import org.example.model.Cliente;
import org.example.model.Categoria;
import org.example.model.Descuento;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReporteService {
    private final AlmacenamientoService almacenamiento;

    public ReporteService() {
        this.almacenamiento = new AlmacenamientoService();
    }

    public Reporte generarReporte(String tipo, LocalDate fechaInicio, LocalDate fechaFin) {
        Reporte reporte = new Reporte(tipo, fechaInicio, fechaFin);
        
        switch (tipo) {
            case Reporte.Tipos.VENTAS_POR_PERIODO:
                return generarReporteVentasPorPeriodo(reporte);
            case Reporte.Tipos.PRODUCTOS_MAS_VENDIDOS:
                return generarReporteProductosMasVendidos(reporte);
            case Reporte.Tipos.CLIENTES_MAS_FRECUENTES:
                return generarReporteClientesMasFrecuentes(reporte);
            case Reporte.Tipos.INGRESOS_POR_CATEGORIA:
                return generarReporteIngresosPorCategoria(reporte);
            case Reporte.Tipos.DESCUENTOS_APLICADOS:
                return generarReporteDescuentosAplicados(reporte);
            default:
                throw new IllegalArgumentException("Tipo de reporte no válido: " + tipo);
        }
    }

    private Reporte generarReporteVentasPorPeriodo(Reporte reporte) {
        List<Venta> ventas = almacenamiento.getVentas().stream()
                .filter(v -> !v.getFecha().toLocalDate().isBefore(reporte.getFechaInicio()) && 
                           !v.getFecha().toLocalDate().isAfter(reporte.getFechaFin()))
                .collect(Collectors.toList());

        Map<String, Object> datos = new HashMap<>();
        datos.put("Total Ventas", ventas.size());
        datos.put("Total Ingresos", ventas.stream()
                .mapToDouble(Venta::getTotal)
                .sum());
        datos.put("Promedio por Venta", ventas.isEmpty() ? 0.0 :
                ventas.stream()
                        .mapToDouble(Venta::getTotal)
                        .average()
                        .orElse(0.0));

        List<Map<String, Object>> detalles = ventas.stream()
                .map(v -> {
                    Map<String, Object> detalle = new HashMap<>();
                    detalle.put("id", v.getId());
                    detalle.put("fecha", v.getFecha());
                    detalle.put("cliente", v.getCliente().getNombre());
                    detalle.put("total", v.getTotal());
                    detalle.put("descuento", almacenamiento.calcularDescuentoTotal(v));
                    detalle.put("estado", v.getEstado());
                    return detalle;
                })
                .collect(Collectors.toList());

        reporte.setDatos(datos);
        reporte.setDetalles(detalles);
        return reporte;
    }

    private Reporte generarReporteProductosMasVendidos(Reporte reporte) {
        Map<Producto, Integer> ventasPorProducto = new HashMap<>();
        
        almacenamiento.getVentas().stream()
                .filter(v -> !v.getFecha().toLocalDate().isBefore(reporte.getFechaInicio()) && 
                           !v.getFecha().toLocalDate().isAfter(reporte.getFechaFin()))
                .flatMap(v -> v.getDetalles().stream())
                .forEach(detalle -> {
                    Producto producto = detalle.getProducto();
                    ventasPorProducto.merge(producto, detalle.getCantidad(), Integer::sum);
                });

        Map<String, Object> datos = new HashMap<>();
        datos.put("Total Productos Vendidos", ventasPorProducto.values().stream()
                .mapToInt(Integer::intValue)
                .sum());
        datos.put("Productos Únicos", ventasPorProducto.size());

        List<Map<String, Object>> detalles = ventasPorProducto.entrySet().stream()
                .sorted(Map.Entry.<Producto, Integer>comparingByValue().reversed())
                .map(entry -> {
                    Map<String, Object> detalle = new HashMap<>();
                    detalle.put("producto", entry.getKey().getNombre());
                    detalle.put("categoria", entry.getKey().getCategoria().getNombre());
                    detalle.put("cantidad", entry.getValue());
                    detalle.put("precio_unitario", entry.getKey().getPrecio());
                    detalle.put("total", entry.getValue() * entry.getKey().getPrecio());
                    return detalle;
                })
                .collect(Collectors.toList());

        reporte.setDatos(datos);
        reporte.setDetalles(detalles);
        return reporte;
    }

    private Reporte generarReporteClientesMasFrecuentes(Reporte reporte) {
        Map<Cliente, List<Venta>> ventasPorCliente = almacenamiento.getVentas().stream()
                .filter(v -> !v.getFecha().toLocalDate().isBefore(reporte.getFechaInicio()) && 
                           !v.getFecha().toLocalDate().isAfter(reporte.getFechaFin()))
                .collect(Collectors.groupingBy(Venta::getCliente));

        Map<String, Object> datos = new HashMap<>();
        datos.put("Total Clientes", ventasPorCliente.size());
        datos.put("Promedio Compras por Cliente", ventasPorCliente.values().stream()
                .mapToInt(List::size)
                .average()
                .orElse(0.0));

        List<Map<String, Object>> detalles = ventasPorCliente.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .map(entry -> {
                    Map<String, Object> detalle = new HashMap<>();
                    detalle.put("cliente", entry.getKey().getNombre());
                    detalle.put("email", entry.getKey().getEmail());
                    detalle.put("compras", entry.getValue().size());
                    detalle.put("total_gastado", entry.getValue().stream()
                            .mapToDouble(Venta::getTotal)
                            .sum());
                    return detalle;
                })
                .collect(Collectors.toList());

        reporte.setDatos(datos);
        reporte.setDetalles(detalles);
        return reporte;
    }

    private Reporte generarReporteIngresosPorCategoria(Reporte reporte) {
        Map<Categoria, Double> ingresosPorCategoria = new HashMap<>();

        almacenamiento.getVentas().stream()
                .filter(v -> !v.getFecha().toLocalDate().isBefore(reporte.getFechaInicio()) && 
                           !v.getFecha().toLocalDate().isAfter(reporte.getFechaFin()))
                .flatMap(v -> v.getDetalles().stream())
                .forEach(detalle -> {
                    Categoria categoria = detalle.getProducto().getCategoria();
                    double subtotal = detalle.getCantidad() * detalle.getPrecioUnitario();
                    ingresosPorCategoria.merge(categoria, subtotal, Double::sum);
                });

        Map<String, Object> datos = new HashMap<>();
        datos.put("Total Ingresos", ingresosPorCategoria.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum());
        datos.put("Categorías", ingresosPorCategoria.size());

        List<Map<String, Object>> detalles = ingresosPorCategoria.entrySet().stream()
                .sorted(Map.Entry.<Categoria, Double>comparingByValue().reversed())
                .map(entry -> {
                    Map<String, Object> detalle = new HashMap<>();
                    detalle.put("categoria", entry.getKey().getNombre());
                    detalle.put("ingresos", entry.getValue());
                    return detalle;
                })
                .collect(Collectors.toList());

        reporte.setDatos(datos);
        reporte.setDetalles(detalles);
        return reporte;
    }

    private Reporte generarReporteDescuentosAplicados(Reporte reporte) {
        Map<String, Double> descuentosPorTipo = new HashMap<>();

        almacenamiento.getVentas().stream()
                .filter(v -> !v.getFecha().toLocalDate().isBefore(reporte.getFechaInicio()) && 
                           !v.getFecha().toLocalDate().isAfter(reporte.getFechaFin()))
                .forEach(venta -> {
                    double descuentoTotal = almacenamiento.calcularDescuentoTotal(venta);
                    if (descuentoTotal > 0) {
                        descuentosPorTipo.merge("Descuento Total", descuentoTotal, Double::sum);
                    }
                });

        Map<String, Object> datos = new HashMap<>();
        datos.put("Total Descuentos", descuentosPorTipo.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum());
        datos.put("Tipos de Descuento", descuentosPorTipo.size());

        List<Map<String, Object>> detalles = descuentosPorTipo.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> detalle = new HashMap<>();
                    detalle.put("tipo", entry.getKey());
                    detalle.put("monto", entry.getValue());
                    return detalle;
                })
                .collect(Collectors.toList());

        reporte.setDatos(datos);
        reporte.setDetalles(detalles);
        return reporte;
    }
} 