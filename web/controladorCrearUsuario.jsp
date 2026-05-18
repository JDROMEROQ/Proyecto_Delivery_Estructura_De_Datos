<%-- 
    Document   : controladorCrearUsuario
    Created on : 17/05/2026, 7:34:09 p. m.
    Author     : dr405
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.delivery.model.*" %>
<%
    SistemaDelivery sistema = (SistemaDelivery) application.getAttribute("sistemaGlobal");
    if (sistema == null) {
        sistema = new SistemaDelivery();
        application.setAttribute("sistemaGlobal", sistema);
    }

    String nombre = request.getParameter("txtNombre");
    String correo = request.getParameter("txtCorreo");
    String clave  = request.getParameter("txtPassword");
    String rol    = request.getParameter("cmbRol").toUpperCase();

    // Mapear al valor exacto que acepta Oracle
    if (rol.equals("ADMIN")) rol = "ADMINISTRADOR";

    Usuarios_Proyecto nuevo = new Usuarios_Proyecto(0, nombre, correo, clave, rol);
    boolean ok = sistema.registrarUsuario(nuevo);

    if (ok) {
        session.setAttribute("mensajeAdmin", "Usuario " + correo + " registrado correctamente como " + rol);
    } else {
        session.setAttribute("mensajeAdmin", "Error: el correo ya existe o hubo un problema.");
    }
    request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
%>