package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AcercaDeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(
            "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
                "<title>Acerca de - Librería Digital</title>" +
                "<style>" +
                    "body { font-family: Arial, sans-serif; margin: 20px; line-height: 1.6; }" +
                    ".container { max-width: 800px; margin: 0 auto; padding: 20px; }" +
                    "h1 { color: #4CAF50; }" +
                    ".section { margin-bottom: 20px; padding: 15px; background-color: #f5f5f5; border-radius: 5px; }" +
                    ".footer { margin-top: 30px; text-align: center; color: #666; }" +
                "</style>" +
            "</head>" +
            "<body>" +
                "<div class='container'>" +
                    "<h1>Librería Digital</h1>" +
                    
                    "<div class='section'>" +
                        "<h2>Descripción</h2>" +
                        "<p>Librería Digital es un sistema integral para la gestión de ventas de libros y materiales de lectura, permitiendo administrar clientes, productos, categorías y descuentos de manera eficiente.</p>" +
                    "</div>" +
                    
                    "<div class='section'>" +
                        "<h2>Funcionalidades Principales</h2>" +
                        "<ul>" +
                            "<li>Gestión de Clientes</li>" +
                            "<li>Gestión de Productos y Categorías</li>" +
                            "<li>Proceso de Ventas</li>" +
                            "<li>Sistema de Descuentos</li>" +
                            "<li>Reportes y Estadísticas</li>" +
                            "<li>Historial de Compras por Cliente</li>" +
                        "</ul>" +
                    "</div>" +
                    
                    "<div class='section'>" +
                        "<h2>Características Técnicas</h2>" +
                        "<ul>" +
                            "<li>Desarrollado en Java</li>" +
                            "<li>Utiliza Jakarta EE (Servlet API)</li>" +
                            "<li>Interfaz web responsive</li>" +
                            "<li>Almacenamiento en memoria</li>" +
                        "</ul>" +
                    "</div>" +
                    
                    "<div class='section'>" +
                        "<h2>Versión</h2>" +
                        "<p>Versión 1.0</p>" +
                    "</div>" +
                    
                    "<div class='footer'>" +
                        "<p>© 2024 Librería Digital</p>" +
                    "</div>" +
                "</div>" +
            "</body>" +
            "</html>"
        );
    }
} 