package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Cliente;
import org.example.service.AlmacenamientoService;

import java.io.IOException;
import java.util.List;

public class ClienteServlet extends HttpServlet {
    private final AlmacenamientoService almacenamiento = AlmacenamientoService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            // Listar todos los clientes
            List<Cliente> clientes = almacenamiento.getClientes();
            mostrarClientes(response, clientes);
        } else if (action.equals("ver")) {
            // Ver un cliente específico
            int id = Integer.parseInt(request.getParameter("id"));
            Cliente cliente = almacenamiento.getCliente(id);
            if (cliente != null) {
                mostrarCliente(response, cliente);
            } else {
                response.getWriter().println("<h1>Cliente no encontrado</h1>");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action != null && action.equals("agregar")) {
            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String telefono = request.getParameter("telefono");
            
            Cliente cliente = almacenamiento.agregarCliente(nombre, email, telefono);
            response.sendRedirect(request.getContextPath() + "/clientes");
        } else if (action != null && action.equals("eliminar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            almacenamiento.eliminarCliente(id);
            response.sendRedirect(request.getContextPath() + "/clientes");
        }
    }

    private void mostrarClientes(HttpServletResponse response, List<Cliente> clientes) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Lista de Clientes</h1>");
        response.getWriter().println("<table border='1'>");
        response.getWriter().println("<tr><th>ID</th><th>Nombre</th><th>Email</th><th>Teléfono</th><th>Acciones</th></tr>");
        
        for (Cliente cliente : clientes) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + cliente.getId() + "</td>");
            response.getWriter().println("<td>" + cliente.getNombre() + "</td>");
            response.getWriter().println("<td>" + cliente.getEmail() + "</td>");
            response.getWriter().println("<td>" + cliente.getTelefono() + "</td>");
            response.getWriter().println("<td>");
            response.getWriter().println("<a href='clientes?action=ver&id=" + cliente.getId() + "'>Ver</a> | ");
            response.getWriter().println("<form method='post' style='display:inline;'>");
            response.getWriter().println("<input type='hidden' name='action' value='eliminar'>");
            response.getWriter().println("<input type='hidden' name='id' value='" + cliente.getId() + "'>");
            response.getWriter().println("<input type='submit' value='Eliminar'>");
            response.getWriter().println("</form>");
            response.getWriter().println("</td>");
            response.getWriter().println("</tr>");
        }
        
        response.getWriter().println("</table>");
        response.getWriter().println("<h2>Agregar Nuevo Cliente</h2>");
        response.getWriter().println("<form method='post'>");
        response.getWriter().println("<input type='hidden' name='action' value='agregar'>");
        response.getWriter().println("Nombre: <input type='text' name='nombre' required><br>");
        response.getWriter().println("Email: <input type='email' name='email' required><br>");
        response.getWriter().println("Teléfono: <input type='text' name='telefono' required><br>");
        response.getWriter().println("<input type='submit' value='Agregar Cliente'>");
        response.getWriter().println("</form>");
        response.getWriter().println("</body></html>");
    }

    private void mostrarCliente(HttpServletResponse response, Cliente cliente) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Detalles del Cliente</h1>");
        response.getWriter().println("<p>ID: " + cliente.getId() + "</p>");
        response.getWriter().println("<p>Nombre: " + cliente.getNombre() + "</p>");
        response.getWriter().println("<p>Email: " + cliente.getEmail() + "</p>");
        response.getWriter().println("<p>Teléfono: " + cliente.getTelefono() + "</p>");

        // Historial de ventas
        var ventas = almacenamiento.getVentasPorCliente(cliente.getId());
        response.getWriter().println("<h2>Historial de Compras</h2>");
        if (ventas.isEmpty()) {
            response.getWriter().println("<p>Este cliente no ha realizado compras.</p>");
        } else {
            response.getWriter().println("<table border='1'>");
            response.getWriter().println("<tr><th>ID Venta</th><th>Fecha</th><th>Total</th><th>Estado</th><th>Productos</th></tr>");
            for (var venta : ventas) {
                response.getWriter().println("<tr>");
                response.getWriter().println("<td>" + venta.getId() + "</td>");
                response.getWriter().println("<td>" + venta.getFecha() + "</td>");
                response.getWriter().println("<td>$" + venta.getTotal() + "</td>");
                response.getWriter().println("<td>" + venta.getEstado() + "</td>");
                response.getWriter().println("<td>");
                response.getWriter().println("<ul>");
                for (var detalle : venta.getDetalles()) {
                    response.getWriter().println("<li>" + detalle.getProducto().getNombre() + " (" + detalle.getCantidad() + " x $" + detalle.getPrecioUnitario() + ") = $" + detalle.getSubtotal() + "</li>");
                }
                response.getWriter().println("</ul>");
                response.getWriter().println("</td>");
                response.getWriter().println("</tr>");
            }
            response.getWriter().println("</table>");
        }
        response.getWriter().println("<a href='clientes'>Volver a la lista</a>");
        response.getWriter().println("</body></html>");
    }
} 