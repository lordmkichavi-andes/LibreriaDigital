package org.example.service;

import org.example.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class AlmacenamientoService {
    private static AlmacenamientoService instance;
    private final List<Cliente> clientes;
    private final List<Producto> productos;
    private final List<Venta> ventas;
    private final List<Categoria> categorias;
    private final List<Descuento> descuentos;
    private final AtomicInteger clienteIdCounter;
    private final AtomicInteger productoIdCounter;
    private final AtomicInteger ventaIdCounter;
    private final AtomicInteger categoriaIdCounter;
    private final AtomicInteger descuentoIdCounter;

    public AlmacenamientoService() {
        this.clientes = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.ventas = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.descuentos = new ArrayList<>();
        clienteIdCounter = new AtomicInteger(1);
        productoIdCounter = new AtomicInteger(1);
        ventaIdCounter = new AtomicInteger(1);
        categoriaIdCounter = new AtomicInteger(1);
        descuentoIdCounter = new AtomicInteger(1);
        
        // Datos de ejemplo
        Categoria electronica = agregarCategoria("Electrónica", "Productos electrónicos y gadgets");
        Categoria ropa = agregarCategoria("Ropa", "Prendas de vestir");
        
        agregarCliente("Juan Pérez", "juan@email.com", "123456789");
        agregarCliente("María García", "maria@email.com", "987654321");
        
        agregarProducto("Laptop", 999.99, 10, electronica);
        agregarProducto("Smartphone", 499.99, 20, electronica);
        agregarProducto("Camisa", 29.99, 50, ropa);
        agregarProducto("Pantalón", 39.99, 30, ropa);

        // Descuentos de ejemplo
        agregarDescuento("CLIENTE10", 10.0, LocalDate.now(), LocalDate.now().plusMonths(1), "CLIENTE", 1);
        agregarDescuento("ELEC20", 20.0, LocalDate.now(), LocalDate.now().plusMonths(1), "CATEGORIA", 1);
    }

    public static AlmacenamientoService getInstance() {
        if (instance == null) {
            instance = new AlmacenamientoService();
        }
        return instance;
    }

    // Métodos para Categorías
    public List<Categoria> getCategorias() {
        return new ArrayList<>(categorias);
    }

    public Categoria agregarCategoria(String nombre, String descripcion) {
        Categoria categoria = new Categoria(categoriaIdCounter.getAndIncrement(), nombre, descripcion);
        categorias.add(categoria);
        return categoria;
    }

    public Categoria getCategoria(int id) {
        return categorias.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Métodos para Descuentos
    public List<Descuento> getDescuentos() {
        return new ArrayList<>(descuentos);
    }

    public Descuento agregarDescuento(String codigo, double porcentaje, LocalDate fechaInicio, 
                                    LocalDate fechaFin, String tipo, int referenciaId) {
        Descuento descuento = new Descuento(descuentoIdCounter.getAndIncrement(), codigo, 
                                          porcentaje, fechaInicio, fechaFin, tipo, referenciaId);
        descuentos.add(descuento);
        return descuento;
    }

    public List<Descuento> getDescuentosValidos() {
        return descuentos.stream()
                .filter(Descuento::esValido)
                .collect(Collectors.toList());
    }

    public List<Descuento> getDescuentosPorTipo(String tipo) {
        return descuentos.stream()
                .filter(d -> d.getTipo().equals(tipo) && d.esValido())
                .collect(Collectors.toList());
    }

    // Métodos para Clientes
    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    public Cliente agregarCliente(String nombre, String email, String telefono) {
        Cliente cliente = new Cliente(clienteIdCounter.getAndIncrement(), nombre, email, telefono);
        clientes.add(cliente);
        return cliente;
    }

    public Cliente getCliente(int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean eliminarCliente(int id) {
        return clientes.removeIf(c -> c.getId() == id);
    }

    // Métodos para Productos
    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }

    public Producto agregarProducto(String nombre, double precio, int stock, Categoria categoria) {
        Producto producto = new Producto(productoIdCounter.getAndIncrement(), nombre, precio, stock, categoria);
        productos.add(producto);
        return producto;
    }

    public Producto getProducto(int id) {
        return productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean eliminarProducto(int id) {
        return productos.removeIf(p -> p.getId() == id);
    }

    public List<Producto> getProductosPorCategoria(int categoriaId) {
        return productos.stream()
                .filter(p -> p.getCategoria().getId() == categoriaId)
                .collect(Collectors.toList());
    }

    // Métodos para Ventas
    public List<Venta> getVentas() {
        return new ArrayList<>(ventas);
    }

    public Venta crearVenta(int clienteId) {
        Cliente cliente = getCliente(clienteId);
        if (cliente == null) {
            return null;
        }
        Venta venta = new Venta(ventaIdCounter.getAndIncrement(), cliente);
        ventas.add(venta);
        return venta;
    }

    public Venta getVenta(int id) {
        return ventas.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Venta> getVentasPorCliente(int clienteId) {
        return ventas.stream()
                .filter(v -> v.getCliente().getId() == clienteId)
                .collect(Collectors.toList());
    }

    public List<Venta> getVentasPorEstado(String estado) {
        return ventas.stream()
                .filter(v -> v.getEstado().equals(estado))
                .collect(Collectors.toList());
    }

    public boolean completarVenta(int ventaId) {
        Venta venta = getVenta(ventaId);
        if (venta != null && venta.getEstado().equals("PENDIENTE")) {
            venta.completarVenta();
            return true;
        }
        return false;
    }

    public boolean cancelarVenta(int ventaId) {
        Venta venta = getVenta(ventaId);
        if (venta != null && venta.getEstado().equals("PENDIENTE")) {
            venta.cancelarVenta();
            return true;
        }
        return false;
    }

    public double calcularDescuentoTotal(Venta venta) {
        AtomicReference<Double> descuentoTotal = new AtomicReference<>(0.0);
        Cliente cliente = venta.getCliente();
        
        // Descuentos por cliente
        descuentos.stream()
                .filter(d -> d.getTipo().equals("CLIENTE") && 
                           d.getReferenciaId() == cliente.getId() && 
                           d.esValido())
                .forEach(d -> descuentoTotal.updateAndGet(current -> 
                    current + d.calcularDescuento(venta.getTotal())));

        // Descuentos por categoría
        for (var detalle : venta.getDetalles()) {
            Producto producto = detalle.getProducto();
            descuentos.stream()
                    .filter(d -> d.getTipo().equals("CATEGORIA") && 
                               d.getReferenciaId() == producto.getCategoria().getId() && 
                               d.esValido())
                    .forEach(d -> descuentoTotal.updateAndGet(current -> 
                        current + d.calcularDescuento(detalle.getSubtotal())));
        }

        return descuentoTotal.get();
    }
} 