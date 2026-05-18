<%-- 
    Document   : redirect
    Created on : 17/05/2026, 7:01:03 p. m.
    Author     : dr405
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.delivery.model.Usuarios_Proyecto" %>
<%
    String accion = request.getParameter("accion");
    if ("irARegistro".equals(accion)) {
        response.sendRedirect(request.getContextPath() + "/registro.jsp");
        return;
    }
    
    Usuarios_Proyecto user = (Usuarios_Proyecto) session.getAttribute("usuarioLogueado");
    
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    
    String rol = user.getRol().toUpperCase().trim();
    switch (rol) {
        case "ADMINISTRADOR":
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
            break;
        case "CLIENTE":
            request.getRequestDispatcher("/WEB-INF/cliente.jsp").forward(request, response);
            break;
        case "REPARTIDOR":
            request.getRequestDispatcher("/WEB-INF/repartidor.jsp").forward(request, response);
            break;
        default:
            response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
%>