package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Cliente;
import org.example.model.Producto;
import org.example.model.Venta;
import org.example.service.AlmacenamientoService;

import java.io.IOException;
import java.util.List;

public class VentaServlet extends HttpServlet {
    private final AlmacenamientoService almacenamiento = AlmacenamientoService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            // Listar todas las ventas
            List<Venta> ventas = almacenamiento.getVentas();
            mostrarVentas(response, ventas);
        } else if (action.equals("ver")) {
            // Ver una venta específica
            int id = Integer.parseInt(request.getParameter("id"));
            Venta venta = almacenamiento.getVenta(id);
            if (venta != null) {
                mostrarVenta(response, venta);
            } else {
                response.getWriter().println("<h1>Venta no encontrada</h1>");
            }
        } else if (action.equals("nueva")) {
            // Mostrar formulario para nueva venta
            mostrarFormularioNuevaVenta(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action != null) {
            switch (action) {
                case "crear":
                    crearVenta(request, response);
                    break;
                case "agregarProducto":
                    agregarProductoAVenta(request, response);
                    break;
                case "completar":
                    completarVenta(request, response);
                    break;
                case "cancelar":
                    cancelarVenta(request, response);
                    break;
                case "agregarNota":
                    agregarNota(request, response);
                    break;
            }
        }
    }

    private void crearVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int clienteId = Integer.parseInt(request.getParameter("clienteId"));
        Venta venta = almacenamiento.crearVenta(clienteId);
        if (venta != null) {
            response.sendRedirect(request.getContextPath() + "/ventas?action=ver&id=" + venta.getId());
        } else {
            response.getWriter().println("<h1>Error al crear la venta</h1>");
        }
    }

    private void agregarProductoAVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int ventaId = Integer.parseInt(request.getParameter("ventaId"));
        int productoId = Integer.parseInt(request.getParameter("productoId"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        
        Venta venta = almacenamiento.getVenta(ventaId);
        Producto producto = almacenamiento.getProducto(productoId);
        
        if (venta != null && producto != null && venta.getEstado().equals("PENDIENTE")) {
            venta.agregarProducto(producto, cantidad);
            response.sendRedirect(request.getContextPath() + "/ventas?action=ver&id=" + ventaId);
        } else {
            response.getWriter().println("<h1>Error al agregar producto a la venta</h1>");
        }
    }

    private void completarVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int ventaId = Integer.parseInt(request.getParameter("ventaId"));
        if (almacenamiento.completarVenta(ventaId)) {
            response.sendRedirect(request.getContextPath() + "/ventas");
        } else {
            response.getWriter().println("<h1>Error al completar la venta</h1>");
        }
    }

    private void cancelarVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int ventaId = Integer.parseInt(request.getParameter("ventaId"));
        if (almacenamiento.cancelarVenta(ventaId)) {
            response.sendRedirect(request.getContextPath() + "/ventas");
        } else {
            response.getWriter().println("<h1>Error al cancelar la venta</h1>");
        }
    }

    private void agregarNota(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int ventaId = Integer.parseInt(request.getParameter("ventaId"));
        String nota = request.getParameter("nota");
        
        Venta venta = almacenamiento.getVenta(ventaId);
        if (venta != null) {
            venta.setNotas(nota);
            response.sendRedirect(request.getContextPath() + "/ventas?action=ver&id=" + ventaId);
        } else {
            response.getWriter().println("<h1>Error: Venta no encontrada</h1>");
        }
    }

    private void mostrarVentas(HttpServletResponse response, List<Venta> ventas) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Lista de Ventas</h1>");
        response.getWriter().println("<a href='ventas?action=nueva' style='margin-bottom: 20px; display: inline-block;'>Nueva Venta</a>");
        response.getWriter().println("<table border='1'>");
        response.getWriter().println("<tr><th>ID</th><th>Cliente</th><th>Fecha</th><th>Total</th><th>Estado</th><th>Acciones</th></tr>");
        
        for (Venta venta : ventas) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + venta.getId() + "</td>");
            response.getWriter().println("<td>" + venta.getCliente().getNombre() + "</td>");
            response.getWriter().println("<td>" + venta.getFecha() + "</td>");
            response.getWriter().println("<td>$" + venta.getTotal() + "</td>");
            response.getWriter().println("<td>" + venta.getEstado() + "</td>");
            response.getWriter().println("<td>");
            response.getWriter().println("<a href='ventas?action=ver&id=" + venta.getId() + "'>Ver</a>");
            if (venta.getEstado().equals("PENDIENTE")) {
                response.getWriter().println(" | ");
                response.getWriter().println("<form method='post' style='display:inline;'>");
                response.getWriter().println("<input type='hidden' name='action' value='completar'>");
                response.getWriter().println("<input type='hidden' name='ventaId' value='" + venta.getId() + "'>");
                response.getWriter().println("<input type='submit' value='Completar'>");
                response.getWriter().println("</form>");
                response.getWriter().println(" | ");
                response.getWriter().println("<form method='post' style='display:inline;'>");
                response.getWriter().println("<input type='hidden' name='action' value='cancelar'>");
                response.getWriter().println("<input type='hidden' name='ventaId' value='" + venta.getId() + "'>");
                response.getWriter().println("<input type='submit' value='Cancelar'>");
                response.getWriter().println("</form>");
            }
            response.getWriter().println("</td>");
            response.getWriter().println("</tr>");
        }
        
        response.getWriter().println("</table>");
        response.getWriter().println("</body></html>");
    }

    private void mostrarVenta(HttpServletResponse response, Venta venta) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Detalles de la Venta</h1>");
        response.getWriter().println("<p>ID: " + venta.getId() + "</p>");
        response.getWriter().println("<p>Cliente: " + venta.getCliente().getNombre() + "</p>");
        response.getWriter().println("<p>Fecha: " + venta.getFecha() + "</p>");
        response.getWriter().println("<p>Estado: " + venta.getEstado() + "</p>");
        
        // Mostrar notas actuales
        response.getWriter().println("<h2>Notas y Observaciones</h2>");
        response.getWriter().println("<div style='background-color: #f5f5f5; padding: 10px; margin: 10px 0;'>");
        response.getWriter().println("<p>" + (venta.getNotas().isEmpty() ? "Sin notas" : venta.getNotas()) + "</p>");
        response.getWriter().println("</div>");
        
        // Formulario para agregar/editar notas
        response.getWriter().println("<form method='post'>");
        response.getWriter().println("<input type='hidden' name='action' value='agregarNota'>");
        response.getWriter().println("<input type='hidden' name='ventaId' value='" + venta.getId() + "'>");
        response.getWriter().println("<textarea name='nota' rows='4' style='width: 100%;'>" + venta.getNotas() + "</textarea><br>");
        response.getWriter().println("<input type='submit' value='Guardar Notas'>");
        response.getWriter().println("</form>");
        
        response.getWriter().println("<h2>Productos</h2>");
        response.getWriter().println("<table border='1'>");
        response.getWriter().println("<tr><th>Producto</th><th>Cantidad</th><th>Precio Unitario</th><th>Subtotal</th></tr>");
        
        for (var detalle : venta.getDetalles()) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + detalle.getProducto().getNombre() + "</td>");
            response.getWriter().println("<td>" + detalle.getCantidad() + "</td>");
            response.getWriter().println("<td>$" + detalle.getPrecioUnitario() + "</td>");
            response.getWriter().println("<td>$" + detalle.getSubtotal() + "</td>");
            response.getWriter().println("</tr>");
        }
        
        response.getWriter().println("</table>");
        
        // Mostrar descuentos aplicados
        double descuentoTotal = almacenamiento.calcularDescuentoTotal(venta);
        if (descuentoTotal > 0) {
            response.getWriter().println("<h3>Descuentos Aplicados</h3>");
            response.getWriter().println("<p>Total de descuentos: $" + descuentoTotal + "</p>");
        }
        
        response.getWriter().println("<h3>Subtotal: $" + venta.getTotal() + "</h3>");
        response.getWriter().println("<h3>Total con Descuentos: $" + (venta.getTotal() - descuentoTotal) + "</h3>");
        
        if (venta.getEstado().equals("PENDIENTE")) {
            response.getWriter().println("<h2>Agregar Producto</h2>");
            response.getWriter().println("<form method='post'>");
            response.getWriter().println("<input type='hidden' name='action' value='agregarProducto'>");
            response.getWriter().println("<input type='hidden' name='ventaId' value='" + venta.getId() + "'>");
            response.getWriter().println("Producto: <select name='productoId' required>");
            
            for (Producto producto : almacenamiento.getProductos()) {
                response.getWriter().println("<option value='" + producto.getId() + "'>" + 
                    producto.getNombre() + " - $" + producto.getPrecio() + " (Stock: " + producto.getStock() + ")</option>");
            }
            
            response.getWriter().println("</select><br>");
            response.getWriter().println("Cantidad: <input type='number' name='cantidad' min='1' required><br>");
            response.getWriter().println("<input type='submit' value='Agregar Producto'>");
            response.getWriter().println("</form>");
        }
        
        response.getWriter().println("<p><a href='ventas'>Volver a la lista</a></p>");
        response.getWriter().println("</body></html>");
    }

    private void mostrarFormularioNuevaVenta(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Nueva Venta</h1>");
        response.getWriter().println("<form method='post'>");
        response.getWriter().println("<input type='hidden' name='action' value='crear'>");
        response.getWriter().println("Cliente: <select name='clienteId' required>");
        
        for (Cliente cliente : almacenamiento.getClientes()) {
            response.getWriter().println("<option value='" + cliente.getId() + "'>" + 
                cliente.getNombre() + " (" + cliente.getEmail() + ")</option>");
        }
        
        response.getWriter().println("</select><br>");
        response.getWriter().println("<input type='submit' value='Crear Venta'>");
        response.getWriter().println("</form>");
        response.getWriter().println("<p><a href='ventas'>Volver a la lista</a></p>");
        response.getWriter().println("</body></html>");
    }
} 