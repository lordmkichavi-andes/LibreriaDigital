package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.example.model.Producto;
import org.example.model.Categoria;
import org.example.service.AlmacenamientoService;

import java.io.IOException;
import java.util.List;

public class ProductoServlet extends HttpServlet {
    private final AlmacenamientoService almacenamiento = AlmacenamientoService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String API_BASE_URL = "http://localhost:8080/libreria-digital/api";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            // Listar todos los productos
            List<Producto> productos = obtenerProductos();
            mostrarProductos(response, productos);
        } else if (action.equals("ver")) {
            // Ver un producto específico
            int id = Integer.parseInt(request.getParameter("id"));
            Producto producto = obtenerProducto(id);
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
                crearProducto(nombre, precio, stock, categoria);
            }
            response.sendRedirect(request.getContextPath() + "/productos");
        } else if (action != null && action.equals("eliminar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            eliminarProducto(id);
            response.sendRedirect(request.getContextPath() + "/productos");
        }
    }

    private List<Producto> obtenerProductos() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(API_BASE_URL + "/productos");
            String jsonResponse = httpClient.execute(httpGet, response ->
                EntityUtils.toString(response.getEntity()));
            
            return objectMapper.readValue(jsonResponse, new TypeReference<List<Producto>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private Producto obtenerProducto(int id) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(API_BASE_URL + "/productos/" + id);
            String jsonResponse = httpClient.execute(httpGet, response ->
                EntityUtils.toString(response.getEntity()));
            return objectMapper.readValue(jsonResponse, Producto.class);
        } catch (Exception e) {
            return null;
        }
    }

    private void crearProducto(String nombre, double precio, int stock, Categoria categoria) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(API_BASE_URL + "/productos");
            Producto producto = new Producto(0, nombre, precio, stock, categoria);
            String jsonProducto = objectMapper.writeValueAsString(producto);
            httpPost.setEntity(new StringEntity(jsonProducto, org.apache.hc.core5.http.ContentType.APPLICATION_JSON));
            httpPost.setHeader("Content-Type", "application/json");
            httpClient.execute(httpPost, response -> EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            // No hacer nada
        }
    }

    private void eliminarProducto(int id) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(API_BASE_URL + "/productos/" + id);
            httpClient.execute(httpDelete, response -> EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            // No hacer nada
        }
    }

    private void mostrarProductos(HttpServletResponse response, List<Producto> productos) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Lista de Productos (desde API REST)</h1>");
        response.getWriter().println("<table border='1'>");
        response.getWriter().println("<tr><th>ID</th><th>Nombre</th><th>Precio</th><th>Stock</th><th>Categoría</th><th>Acciones</th></tr>");
        for (Producto producto : productos) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + producto.getId() + "</td>");
            response.getWriter().println("<td>" + producto.getNombre() + "</td>");
            response.getWriter().println("<td>$" + producto.getPrecio() + "</td>");
            response.getWriter().println("<td>" + producto.getStock() + "</td>");
            response.getWriter().println("<td>" + (producto.getCategoria() != null ? producto.getCategoria().getNombre() : "") + "</td>");
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
            response.getWriter().println("<option value='" + categoria.getId() + "'>" + categoria.getNombre() + "</option>");
        }
        response.getWriter().println("</select><br>");
        response.getWriter().println("<input type='submit' value='Agregar Producto'>");
        response.getWriter().println("</form>");
        response.getWriter().println("</body></html>");
    }

    private void mostrarProducto(HttpServletResponse response, Producto producto) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Detalles del Producto (desde API REST)</h1>");
        response.getWriter().println("<p>ID: " + producto.getId() + "</p>");
        response.getWriter().println("<p>Nombre: " + producto.getNombre() + "</p>");
        response.getWriter().println("<p>Precio: $" + producto.getPrecio() + "</p>");
        response.getWriter().println("<p>Stock: " + producto.getStock() + "</p>");
        response.getWriter().println("<p>Categoría: " + (producto.getCategoria() != null ? producto.getCategoria().getNombre() : "") + "</p>");
        response.getWriter().println("<a href='productos'>Volver a la lista</a>");
        response.getWriter().println("</body></html>");
    }
} 