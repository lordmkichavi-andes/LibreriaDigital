package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.*;
import org.example.service.AlmacenamientoService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DescuentoServlet extends HttpServlet {
    private final AlmacenamientoService almacenamiento = AlmacenamientoService.getInstance();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            // Listar todos los descuentos
            List<Descuento> descuentos = almacenamiento.getDescuentos();
            mostrarDescuentos(response, descuentos);
        } else if (action.equals("nuevo")) {
            // Mostrar formulario para nuevo descuento
            mostrarFormularioNuevoDescuento(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action != null && action.equals("agregar")) {
            String codigo = request.getParameter("codigo");
            double porcentaje = Double.parseDouble(request.getParameter("porcentaje"));
            LocalDate fechaInicio = LocalDate.parse(request.getParameter("fechaInicio"), formatter);
            LocalDate fechaFin = LocalDate.parse(request.getParameter("fechaFin"), formatter);
            String tipo = request.getParameter("tipo");
            int referenciaId = Integer.parseInt(request.getParameter("referenciaId"));
            
            almacenamiento.agregarDescuento(codigo, porcentaje, fechaInicio, fechaFin, tipo, referenciaId);
            response.sendRedirect(request.getContextPath() + "/descuentos");
        }
    }

    private void mostrarDescuentos(HttpServletResponse response, List<Descuento> descuentos) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Lista de Descuentos</h1>");
        response.getWriter().println("<a href='descuentos?action=nuevo' style='margin-bottom: 20px; display: inline-block;'>Nuevo Descuento</a>");
        response.getWriter().println("<table border='1'>");
        response.getWriter().println("<tr><th>Código</th><th>Porcentaje</th><th>Fecha Inicio</th><th>Fecha Fin</th><th>Tipo</th><th>Estado</th></tr>");
        
        for (Descuento descuento : descuentos) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + descuento.getCodigo() + "</td>");
            response.getWriter().println("<td>" + descuento.getPorcentaje() + "%</td>");
            response.getWriter().println("<td>" + descuento.getFechaInicio().format(formatter) + "</td>");
            response.getWriter().println("<td>" + descuento.getFechaFin().format(formatter) + "</td>");
            response.getWriter().println("<td>" + descuento.getTipo() + "</td>");
            response.getWriter().println("<td>" + (descuento.esValido() ? "Activo" : "Inactivo") + "</td>");
            response.getWriter().println("</tr>");
        }
        
        response.getWriter().println("</table>");
        response.getWriter().println("</body></html>");
    }

    private void mostrarFormularioNuevoDescuento(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Nuevo Descuento</h1>");
        response.getWriter().println("<form method='post'>");
        response.getWriter().println("<input type='hidden' name='action' value='agregar'>");
        
        response.getWriter().println("Código: <input type='text' name='codigo' required><br>");
        response.getWriter().println("Porcentaje: <input type='number' step='0.01' name='porcentaje' required><br>");
        response.getWriter().println("Fecha Inicio: <input type='date' name='fechaInicio' required><br>");
        response.getWriter().println("Fecha Fin: <input type='date' name='fechaFin' required><br>");
        
        response.getWriter().println("Tipo: <select name='tipo' id='tipo' onchange='actualizarReferencias()' required>");
        response.getWriter().println("<option value='CLIENTE'>Cliente</option>");
        response.getWriter().println("<option value='CATEGORIA'>Categoría</option>");
        response.getWriter().println("</select><br>");
        
        response.getWriter().println("Referencia: <select name='referenciaId' id='referenciaId' required>");
        response.getWriter().println("</select><br>");
        
        response.getWriter().println("<input type='submit' value='Crear Descuento'>");
        response.getWriter().println("</form>");
        
        // JavaScript para actualizar las referencias según el tipo seleccionado
        response.getWriter().println("<script>");
        response.getWriter().println("function actualizarReferencias() {");
        response.getWriter().println("    var tipo = document.getElementById('tipo').value;");
        response.getWriter().println("    var select = document.getElementById('referenciaId');");
        response.getWriter().println("    select.innerHTML = '';");
        
        // Clientes
        response.getWriter().println("    if (tipo === 'CLIENTE') {");
        for (Cliente cliente : almacenamiento.getClientes()) {
            response.getWriter().println("        var option = document.createElement('option');");
            response.getWriter().println("        option.value = '" + cliente.getId() + "';");
            response.getWriter().println("        option.text = '" + cliente.getNombre() + "';");
            response.getWriter().println("        select.appendChild(option);");
        }
        response.getWriter().println("    }");
        
        // Categorías
        response.getWriter().println("    else if (tipo === 'CATEGORIA') {");
        for (Categoria categoria : almacenamiento.getCategorias()) {
            response.getWriter().println("        var option = document.createElement('option');");
            response.getWriter().println("        option.value = '" + categoria.getId() + "';");
            response.getWriter().println("        option.text = '" + categoria.getNombre() + "';");
            response.getWriter().println("        select.appendChild(option);");
        }
        response.getWriter().println("    }");
        
        response.getWriter().println("}");
        response.getWriter().println("actualizarReferencias();"); // Inicializar al cargar
        response.getWriter().println("</script>");
        
        response.getWriter().println("<p><a href='descuentos'>Volver a la lista</a></p>");
        response.getWriter().println("</body></html>");
    }
} 