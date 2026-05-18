<%-- 
    Document   : controladorCrearUsuario
    Created on : 17/05/2026, 7:34:09 p. m.
    Author     : dr405
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.delivery.model.SistemaDelivery"%>
<%@page import="com.delivery.model.Usuarios_Proyecto"%>
<%
    // Recuperamos el sistema de la memoria global compartida
    SistemaDelivery sistema = (SistemaDelivery) application.getAttribute("sistemaGlobal");
    if (sistema == null) {
        sistema = new SistemaDelivery();
        application.setAttribute("sistemaGlobal", sistema);
    }

    String nombre = request.getParameter("txtNombre");
    String correo = request.getParameter("txtCorreo");
    String password = request.getParameter("txtPassword");
    String rol = request.getParameter("cmbRol");

    if (correo != null && password != null && rol != null) {
        Usuarios_Proyecto nuevoUsuario = new Usuarios_Proyecto();
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setClave(password);
        nuevoUsuario.setRol(rol);
        // nuevoUsuario.setNombre(nombre); // Descomenta si tu clase maneja nombre

        // Insertamos en tus estructuras reales (Tabla Hash y Árbol)
        sistema.registrarUsuario(nuevoUsuario);

        // Actualizamos el contador global de usuarios en memoria
        Integer totalUsuarios = (Integer) application.getAttribute("contadorUsuarios");
        if (totalUsuarios == null) totalUsuarios = 0;
        application.setAttribute("contadorUsuarios", totalUsuarios + 1);

        session.setAttribute("mensajeAdmin", "¡Usuario (" + rol.toUpperCase() + ") creado exitosamente en la Tabla Hash!");
        response.sendRedirect(request.getContextPath() + "/WEB-INF/admin.jsp"); // Recarga el panel de forma interna
    } else {
        session.setAttribute("mensajeAdmin", "Error: Campos incompletos.");
        response.sendRedirect(request.getContextPath() + "/WEB-INF/admin.jsp");
    }
%>