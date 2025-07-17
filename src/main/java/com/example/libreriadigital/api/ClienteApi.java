package com.example.libreriadigital.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteApi {
    private static final ConcurrentHashMap<Integer, Cliente> clientes = new ConcurrentHashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger(3); // Empezar en 3 porque ya tenemos 2
    
    // Inicializar con algunos datos de ejemplo
    static {
        Cliente cliente1 = new Cliente(1, "Juan Pérez", "juan.perez@email.com", "123456789");
        clientes.put(1, cliente1);
        
        Cliente cliente2 = new Cliente(2, "María García", "maria.garcia@email.com", "987654321");
        clientes.put(2, cliente2);
    }
    
    @GET
    public Response getAllClientes() {
        try {
            List<Cliente> listaClientes = new ArrayList<>(clientes.values());
            return Response.ok(listaClientes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response getClienteById(@PathParam("id") int id) {
        try {
            Cliente cliente = clientes.get(id);
            if (cliente != null) {
                return Response.ok(cliente).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Cliente no encontrado con ID: " + id)
                        .build();
            }
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID de cliente inválido")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }
    
    @POST
    public Response createCliente(Cliente cliente) {
        try {
            // Validaciones
            if (cliente == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Datos del cliente no proporcionados")
                        .build();
            }
            
            if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El nombre del cliente es obligatorio")
                        .build();
            }
            
            if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El email del cliente es obligatorio")
                        .build();
            }
            
            // Verificar si ya existe un cliente con el mismo email
            boolean emailExiste = clientes.values().stream()
                    .anyMatch(c -> c.getEmail().equalsIgnoreCase(cliente.getEmail()));
            if (emailExiste) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Ya existe un cliente con el email: " + cliente.getEmail())
                        .build();
            }
            
            int newId = idCounter.getAndIncrement();
            Cliente nuevoCliente = new Cliente(newId, 
                cliente.getNombre().trim(), 
                cliente.getEmail().trim(), 
                cliente.getTelefono() != null ? cliente.getTelefono().trim() : "");
            clientes.put(newId, nuevoCliente);
            
            return Response.status(Response.Status.CREATED)
                    .entity(nuevoCliente)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response updateCliente(@PathParam("id") int id, Cliente cliente) {
        try {
            // Validaciones
            if (cliente == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Datos del cliente no proporcionados")
                        .build();
            }
            
            if (!clientes.containsKey(id)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Cliente no encontrado con ID: " + id)
                        .build();
            }
            
            if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El nombre del cliente es obligatorio")
                        .build();
            }
            
            if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El email del cliente es obligatorio")
                        .build();
            }
            
            // Verificar si ya existe otro cliente con el mismo email (excluyendo el actual)
            boolean emailExiste = clientes.values().stream()
                    .filter(c -> c.getId() != id)
                    .anyMatch(c -> c.getEmail().equalsIgnoreCase(cliente.getEmail()));
            if (emailExiste) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Ya existe otro cliente con el email: " + cliente.getEmail())
                        .build();
            }
            
            Cliente clienteActualizado = new Cliente(id, 
                cliente.getNombre().trim(), 
                cliente.getEmail().trim(), 
                cliente.getTelefono() != null ? cliente.getTelefono().trim() : "");
            clientes.put(id, clienteActualizado);
            
            return Response.ok(clienteActualizado).build();
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID de cliente inválido")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteCliente(@PathParam("id") int id) {
        try {
            Cliente cliente = clientes.remove(id);
            if (cliente != null) {
                return Response.ok("Cliente eliminado exitosamente").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Cliente no encontrado con ID: " + id)
                        .build();
            }
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID de cliente inválido")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }
    
    @GET
    @Path("/search")
    public Response searchClientes(@QueryParam("nombre") String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El parámetro 'nombre' es obligatorio")
                        .build();
            }
            
            List<Cliente> resultados = clientes.values().stream()
                    .filter(cliente -> cliente.getNombre().toLowerCase().contains(nombre.toLowerCase()))
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
    public Response getClientesCount() {
        try {
            return Response.ok(clientes.size()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno del servidor: " + e.getMessage())
                    .build();
        }
    }
} 