<%-- 
    Document   : controladorLogin
    Created on : 17/05/2026, 6:43:42 p. m.
    Author     : dr405
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.delivery.model.*" %>
<%
    SistemaDelivery sistema = (SistemaDelivery) application.getAttribute("sistemaGlobal");

    String correo = request.getParameter("txtCorreo");
    String clave  = request.getParameter("txtPassword");

    Usuarios_Proyecto user = sistema.realizarLogin(correo, clave);

    if(sistema == null){
        sistema = new SistemaDelivery();
        application.setAttribute("sistemaGlobal", sistema);
    }
    
    if (user != null) {
        session.setAttribute("usuarioLogueado", user);
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
    } else {
        session.setAttribute("errorLogin", "Correo o contraseña incorrectos.");
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
%>