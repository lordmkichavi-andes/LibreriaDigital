package org.example.servlet;

import org.example.model.Reporte;
import org.example.service.ReporteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ReporteServlet extends HttpServlet {
    private final ReporteService reporteService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ReporteServlet() {
        this.reporteService = new ReporteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        if (accion == null) {
            mostrarFormularioReportes(response);
        } else {
            String tipo = request.getParameter("tipo");
            String fechaInicioStr = request.getParameter("fechaInicio");
            String fechaFinStr = request.getParameter("fechaFin");
            
            LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
            LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);
            
            Reporte reporte = reporteService.generarReporte(tipo, fechaInicio, fechaFin);
            mostrarReporte(reporte, response);
        }
    }

    private void mostrarFormularioReportes(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(
            "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
                "<title>Generar Reportes</title>" +
                "<style>" +
                    "body { font-family: Arial, sans-serif; margin: 20px; }" +
                    ".container { max-width: 800px; margin: 0 auto; }" +
                    ".form-group { margin-bottom: 15px; }" +
                    "label { display: block; margin-bottom: 5px; }" +
                    "select, input { width: 100%; padding: 8px; margin-bottom: 10px; }" +
                    "button { padding: 10px 20px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }" +
                    "button:hover { background-color: #45a049; }" +
                "</style>" +
            "</head>" +
            "<body>" +
                "<div class=\"container\">" +
                    "<h1>Generar Reportes</h1>" +
                    "<form action=\"reportes\" method=\"get\">" +
                        "<input type=\"hidden\" name=\"accion\" value=\"generar\">" +
                        
                        "<div class=\"form-group\">" +
                            "<label for=\"tipo\">Tipo de Reporte:</label>" +
                            "<select name=\"tipo\" id=\"tipo\" required>" +
                                "<option value=\"VENTAS_POR_PERIODO\">Ventas por Período</option>" +
                                "<option value=\"PRODUCTOS_MAS_VENDIDOS\">Productos más Vendidos</option>" +
                                "<option value=\"CLIENTES_MAS_FRECUENTES\">Clientes más Frecuentes</option>" +
                                "<option value=\"INGRESOS_POR_CATEGORIA\">Ingresos por Categoría</option>" +
                                "<option value=\"DESCUENTOS_APLICADOS\">Descuentos Aplicados</option>" +
                            "</select>" +
                        "</div>" +
                        
                        "<div class=\"form-group\">" +
                            "<label for=\"fechaInicio\">Fecha Inicio:</label>" +
                            "<input type=\"date\" name=\"fechaInicio\" id=\"fechaInicio\" required>" +
                        "</div>" +
                        
                        "<div class=\"form-group\">" +
                            "<label for=\"fechaFin\">Fecha Fin:</label>" +
                            "<input type=\"date\" name=\"fechaFin\" id=\"fechaFin\" required>" +
                        "</div>" +
                        
                        "<button type=\"submit\">Generar Reporte</button>" +
                    "</form>" +
                "</div>" +
            "</body>" +
            "</html>"
        );
    }

    private void mostrarReporte(Reporte reporte, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        StringBuilder html = new StringBuilder();
        
        html.append(
            "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
                "<title>Reporte</title>" +
                "<style>" +
                    "body { font-family: Arial, sans-serif; margin: 20px; }" +
                    ".container { max-width: 1200px; margin: 0 auto; }" +
                    ".resumen { background-color: #f5f5f5; padding: 20px; margin-bottom: 20px; border-radius: 5px; }" +
                    ".resumen-item { margin-bottom: 10px; }" +
                    "table { width: 100%; border-collapse: collapse; margin-top: 20px; }" +
                    "th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }" +
                    "th { background-color: #4CAF50; color: white; }" +
                    "tr:hover { background-color: #f5f5f5; }" +
                    ".volver { display: inline-block; padding: 10px 20px; background-color: #4CAF50; " +
                             "color: white; text-decoration: none; margin-top: 20px; }" +
                    ".volver:hover { background-color: #45a049; }" +
                "</style>" +
            "</head>" +
            "<body>" +
                "<div class=\"container\">" +
                    "<h1>Reporte: "
        ).append(obtenerTituloReporte(reporte.getTipo())).append("</h1>");
        
        // Mostrar resumen
        html.append("<div class='resumen'><h2>Resumen</h2>");
        for (Map.Entry<String, Object> entry : reporte.getDatos().entrySet()) {
            html.append("<div class='resumen-item'>")
                .append("<strong>").append(entry.getKey()).append(":</strong> ")
                .append(entry.getValue())
                .append("</div>");
        }
        html.append("</div>");
        
        // Mostrar detalles
        html.append("<h2>Detalles</h2><table><thead><tr>");
        for (String columna : obtenerColumnasReporte(reporte.getTipo())) {
            html.append("<th>").append(columna).append("</th>");
        }
        html.append("</tr></thead><tbody>");
        
        for (Map<String, Object> detalle : reporte.getDetalles()) {
            html.append("<tr>");
            for (String columna : obtenerColumnasReporte(reporte.getTipo())) {
                html.append("<td>").append(detalle.get(columna.toLowerCase())).append("</td>");
            }
            html.append("</tr>");
        }
        
        html.append(
                "</tbody>" +
            "</table>" +
            "<a href=\"reportes\" class=\"volver\">Volver</a>" +
            "</div>" +
            "</body>" +
            "</html>"
        );
        
        response.getWriter().println(html.toString());
    }

    private String obtenerTituloReporte(String tipo) {
        switch (tipo) {
            case Reporte.Tipos.VENTAS_POR_PERIODO:
                return "Ventas por Período";
            case Reporte.Tipos.PRODUCTOS_MAS_VENDIDOS:
                return "Productos más Vendidos";
            case Reporte.Tipos.CLIENTES_MAS_FRECUENTES:
                return "Clientes más Frecuentes";
            case Reporte.Tipos.INGRESOS_POR_CATEGORIA:
                return "Ingresos por Categoría";
            case Reporte.Tipos.DESCUENTOS_APLICADOS:
                return "Descuentos Aplicados";
            default:
                return "Reporte";
        }
    }

    private String[] obtenerColumnasReporte(String tipo) {
        switch (tipo) {
            case Reporte.Tipos.VENTAS_POR_PERIODO:
                return new String[]{"ID", "Fecha", "Cliente", "Total", "Descuento", "Estado"};
            case Reporte.Tipos.PRODUCTOS_MAS_VENDIDOS:
                return new String[]{"Producto", "Categoría", "Cantidad", "Precio Unitario", "Total"};
            case Reporte.Tipos.CLIENTES_MAS_FRECUENTES:
                return new String[]{"Cliente", "Email", "Compras", "Total Gastado"};
            case Reporte.Tipos.INGRESOS_POR_CATEGORIA:
                return new String[]{"Categoría", "Ingresos"};
            case Reporte.Tipos.DESCUENTOS_APLICADOS:
                return new String[]{"Tipo", "Monto"};
            default:
                return new String[]{};
        }
    }
} 