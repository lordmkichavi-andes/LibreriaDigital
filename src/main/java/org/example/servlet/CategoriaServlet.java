package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Categoria;
import org.example.service.AlmacenamientoService;

import java.io.IOException;
import java.util.List;

public class CategoriaServlet extends HttpServlet {
    private final AlmacenamientoService almacenamiento = AlmacenamientoService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            // Listar todas las categorías
            List<Categoria> categorias = almacenamiento.getCategorias();
            mostrarCategorias(response, categorias);
        } else if (action.equals("ver")) {
            // Ver una categoría específica
            int id = Integer.parseInt(request.getParameter("id"));
            Categoria categoria = almacenamiento.getCategoria(id);
            if (categoria != null) {
                mostrarCategoria(response, categoria);
            } else {
                response.getWriter().println("<h1>Categoría no encontrada</h1>");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action != null && action.equals("agregar")) {
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            
            Categoria categoria = almacenamiento.agregarCategoria(nombre, descripcion);
            response.sendRedirect(request.getContextPath() + "/categorias");
        }
    }

    private void mostrarCategorias(HttpServletResponse response, List<Categoria> categorias) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Lista de Categorías</h1>");
        response.getWriter().println("<table border='1'>");
        response.getWriter().println("<tr><th>ID</th><th>Nombre</th><th>Descripción</th><th>Acciones</th></tr>");
        
        for (Categoria categoria : categorias) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + categoria.getId() + "</td>");
            response.getWriter().println("<td>" + categoria.getNombre() + "</td>");
            response.getWriter().println("<td>" + categoria.getDescripcion() + "</td>");
            response.getWriter().println("<td>");
            response.getWriter().println("<a href='categorias?action=ver&id=" + categoria.getId() + "'>Ver</a>");
            response.getWriter().println("</td>");
            response.getWriter().println("</tr>");
        }
        
        response.getWriter().println("</table>");
        response.getWriter().println("<h2>Agregar Nueva Categoría</h2>");
        response.getWriter().println("<form method='post'>");
        response.getWriter().println("<input type='hidden' name='action' value='agregar'>");
        response.getWriter().println("Nombre: <input type='text' name='nombre' required><br>");
        response.getWriter().println("Descripción: <textarea name='descripcion' required></textarea><br>");
        response.getWriter().println("<input type='submit' value='Agregar Categoría'>");
        response.getWriter().println("</form>");
        response.getWriter().println("</body></html>");
    }

    private void mostrarCategoria(HttpServletResponse response, Categoria categoria) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Detalles de la Categoría</h1>");
        response.getWriter().println("<p>ID: " + categoria.getId() + "</p>");
        response.getWriter().println("<p>Nombre: " + categoria.getNombre() + "</p>");
        response.getWriter().println("<p>Descripción: " + categoria.getDescripcion() + "</p>");
        
        response.getWriter().println("<h2>Productos en esta categoría</h2>");
        response.getWriter().println("<table border='1'>");
        response.getWriter().println("<tr><th>ID</th><th>Nombre</th><th>Precio</th><th>Stock</th></tr>");
        
        for (var producto : almacenamiento.getProductosPorCategoria(categoria.getId())) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + producto.getId() + "</td>");
            response.getWriter().println("<td>" + producto.getNombre() + "</td>");
            response.getWriter().println("<td>$" + producto.getPrecio() + "</td>");
            response.getWriter().println("<td>" + producto.getStock() + "</td>");
            response.getWriter().println("</tr>");
        }
        
        response.getWriter().println("</table>");
        response.getWriter().println("<p><a href='categorias'>Volver a la lista</a></p>");
        response.getWriter().println("</body></html>");
    }
} 