package org.example;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ConsultaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Hola Mundo desde Servlet!</h1>");
        response.getWriter().println("<p>Este es un servlet simplificado sin conexión a base de datos.</p>");
        response.getWriter().println("</body></html>");
    }
}
