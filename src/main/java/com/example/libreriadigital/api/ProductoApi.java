package com.example.libreriadigital.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.Producto;
import org.example.model.Categoria;
import org.example.service.AlmacenamientoService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoApi {
    private static final ConcurrentHashMap<Integer, Producto> productos = new ConcurrentHashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);
    private static final AlmacenamientoService almacenamiento = AlmacenamientoService.getInstance();

    // Inicializar con algunos datos de ejemplo
    static {
        // Si hay productos en almacenamiento local, agregarlos
        for (Producto p : almacenamiento.getProductos()) {
            productos.put(p.getId(), p);
            idCounter.set(Math.max(idCounter.get(), p.getId() + 1));
        }
    }

    @GET
    public Response getAllProductos() {
        try {
            List<Producto> listaProductos = new ArrayList<>(productos.values());
            return Response.ok(listaProductos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getProductoById(@PathParam("id") int id) {
        try {
            Producto producto = productos.get(id);
            if (producto != null) {
                return Response.ok(producto).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Producto no encontrado con ID: " + id)
                        .build();
            }
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID de producto inválido")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response createProducto(Producto producto) {
        try {
            if (producto == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Datos del producto no proporcionados")
                        .build();
            }
            if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El nombre del producto es obligatorio")
                        .build();
            }
            if (producto.getPrecio() < 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El precio debe ser mayor o igual a cero")
                        .build();
            }
            if (producto.getStock() < 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El stock debe ser mayor o igual a cero")
                        .build();
            }
            if (producto.getCategoria() == null || producto.getCategoria().getId() == 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("La categoría es obligatoria")
                        .build();
            }
            // Verificar si ya existe un producto con el mismo nombre (opcional)
            boolean nombreExiste = productos.values().stream()
                    .anyMatch(p -> p.getNombre().equalsIgnoreCase(producto.getNombre()));
            if (nombreExiste) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Ya existe un producto con el nombre: " + producto.getNombre())
                        .build();
            }
            int newId = idCounter.getAndIncrement();
            Producto nuevoProducto = new Producto(newId,
                    producto.getNombre().trim(),
                    producto.getPrecio(),
                    producto.getStock(),
                    producto.getCategoria());
            productos.put(newId, nuevoProducto);
            return Response.status(Response.Status.CREATED)
                    .entity(nuevoProducto)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateProducto(@PathParam("id") int id, Producto producto) {
        try {
            if (producto == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Datos del producto no proporcionados")
                        .build();
            }
            if (!productos.containsKey(id)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Producto no encontrado con ID: " + id)
                        .build();
            }
            if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El nombre del producto es obligatorio")
                        .build();
            }
            if (producto.getPrecio() < 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El precio debe ser mayor o igual a cero")
                        .build();
            }
            if (producto.getStock() < 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El stock debe ser mayor o igual a cero")
                        .build();
            }
            if (producto.getCategoria() == null || producto.getCategoria().getId() == 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("La categoría es obligatoria")
                        .build();
            }
            // Verificar si ya existe otro producto con el mismo nombre
            boolean nombreExiste = productos.values().stream()
                    .filter(p -> p.getId() != id)
                    .anyMatch(p -> p.getNombre().equalsIgnoreCase(producto.getNombre()));
            if (nombreExiste) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Ya existe otro producto con el nombre: " + producto.getNombre())
                        .build();
            }
            Producto productoActualizado = new Producto(id,
                    producto.getNombre().trim(),
                    producto.getPrecio(),
                    producto.getStock(),
                    producto.getCategoria());
            productos.put(id, productoActualizado);
            return Response.ok(productoActualizado).build();
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID de producto inválido")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProducto(@PathParam("id") int id) {
        try {
            Producto producto = productos.remove(id);
            if (producto != null) {
                return Response.ok("Producto eliminado exitosamente").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Producto no encontrado con ID: " + id)
                        .build();
            }
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID de producto inválido")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/buscar")
    public Response searchProductos(@QueryParam("nombre") String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El parámetro 'nombre' es obligatorio")
                        .build();
            }
            List<Producto> resultados = productos.values().stream()
                    .filter(producto -> producto.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            return Response.ok(resultados).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/count")
    public Response getProductosCount() {
        try {
            return Response.ok(productos.size()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }
} 