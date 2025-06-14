package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Producto;
import org.example.model.Categoria;
import org.example.service.AlmacenamientoService;

import java.io.IOException;
import java.util.List;

public class ProductoServlet extends HttpServlet {
    private final AlmacenamientoService almacenamiento = AlmacenamientoService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            // Listar todos los productos
            List<Producto> productos = almacenamiento.getProductos();
            mostrarProductos(response, productos);
        } else if (action.equals("ver")) {
            // Ver un producto específico
            int id = Integer.parseInt(request.getParameter("id"));
            Producto producto = almacenamiento.getProducto(id);
            if (producto != null) {
                mostrarProducto(response, producto);
            } else {
                response.getWriter().println("<h1>Producto no encontrado</h1>");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action != null && action.equals("agregar")) {
            String nombre = request.getParameter("nombre");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            int categoriaId = Integer.parseInt(request.getParameter("categoriaId"));
            
            Categoria categoria = almacenamiento.getCategoria(categoriaId);
            if (categoria != null) {
                Producto producto = almacenamiento.agregarProducto(nombre, precio, stock, categoria);
                response.sendRedirect(request.getContextPath() + "/productos");
            } else {
                response.getWriter().println("<h1>Error: Categoría no encontrada</h1>");
            }
        } else if (action != null && action.equals("eliminar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            almacenamiento.eliminarProducto(id);
            response.sendRedirect(request.getContextPath() + "/productos");
        }
    }

    private void mostrarProductos(HttpServletResponse response, List<Producto> productos) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Lista de Productos</h1>");
        response.getWriter().println("<table border='1'>");
        response.getWriter().println("<tr><th>ID</th><th>Nombre</th><th>Precio</th><th>Stock</th><th>Categoría</th><th>Acciones</th></tr>");
        
        for (Producto producto : productos) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + producto.getId() + "</td>");
            response.getWriter().println("<td>" + producto.getNombre() + "</td>");
            response.getWriter().println("<td>$" + producto.getPrecio() + "</td>");
            response.getWriter().println("<td>" + producto.getStock() + "</td>");
            response.getWriter().println("<td>" + producto.getCategoria().getNombre() + "</td>");
            response.getWriter().println("<td>");
            response.getWriter().println("<a href='productos?action=ver&id=" + producto.getId() + "'>Ver</a> | ");
            response.getWriter().println("<form method='post' style='display:inline;'>");
            response.getWriter().println("<input type='hidden' name='action' value='eliminar'>");
            response.getWriter().println("<input type='hidden' name='id' value='" + producto.getId() + "'>");
            response.getWriter().println("<input type='submit' value='Eliminar'>");
            response.getWriter().println("</form>");
            response.getWriter().println("</td>");
            response.getWriter().println("</tr>");
        }
        
        response.getWriter().println("</table>");
        response.getWriter().println("<h2>Agregar Nuevo Producto</h2>");
        response.getWriter().println("<form method='post'>");
        response.getWriter().println("<input type='hidden' name='action' value='agregar'>");
        response.getWriter().println("Nombre: <input type='text' name='nombre' required><br>");
        response.getWriter().println("Precio: <input type='number' step='0.01' name='precio' required><br>");
        response.getWriter().println("Stock: <input type='number' name='stock' required><br>");
        response.getWriter().println("Categoría: <select name='categoriaId' required>");
        
        for (Categoria categoria : almacenamiento.getCategorias()) {
            response.getWriter().println("<option value='" + categoria.getId() + "'>" + 
                categoria.getNombre() + "</option>");
        }
        
        response.getWriter().println("</select><br>");
        response.getWriter().println("<input type='submit' value='Agregar Producto'>");
        response.getWriter().println("</form>");
        response.getWriter().println("</body></html>");
    }

    private void mostrarProducto(HttpServletResponse response, Producto producto) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Detalles del Producto</h1>");
        response.getWriter().println("<p>ID: " + producto.getId() + "</p>");
        response.getWriter().println("<p>Nombre: " + producto.getNombre() + "</p>");
        response.getWriter().println("<p>Precio: $" + producto.getPrecio() + "</p>");
        response.getWriter().println("<p>Stock: " + producto.getStock() + "</p>");
        response.getWriter().println("<p>Categoría: " + producto.getCategoria().getNombre() + "</p>");
        response.getWriter().println("<a href='productos'>Volver a la lista</a>");
        response.getWriter().println("</body></html>");
    }
} 