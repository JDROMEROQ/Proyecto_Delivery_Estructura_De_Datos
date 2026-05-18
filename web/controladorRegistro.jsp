<%-- 
    Document   : controladorRegistro
    Created on : 17/05/2026, 7:22:03 p. m.
    Author     : dr405
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.delivery.model.SistemaDelivery"%>
<%@page import="com.delivery.model.Usuarios_Proyecto"%>
<%
    // Recuperamos el sistema de la memoria global "application" para no perder los datos
    SistemaDelivery sistema = (SistemaDelivery) application.getAttribute("sistemaGlobal");
    if (sistema == null) {
        sistema = new SistemaDelivery();
        application.setAttribute("sistemaGlobal", sistema);
    }

    String nombre = request.getParameter("txtNombre");
    String correo = request.getParameter("txtCorreo");
    String password = request.getParameter("txtPassword");

    if (correo != null && password != null) {
        
        // Lógica de asignación de Rol según el contador de usuarios en la sesión de la app
        Integer totalUsuarios = (Integer) application.getAttribute("contadorUsuarios");
        if (totalUsuarios == null) {
            totalUsuarios = 0;
        }

        String rolAsignado = "cliente";
        if (totalUsuarios == 0) {
            rolAsignado = "admin"; // ¡El primero en llegar es el Jefe!
        }

        // Creamos el objeto nativo
        Usuarios_Proyecto nuevoUsuario = new Usuarios_Proyecto();
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setClave(password); // Ajusta al nombre exacto de tu setter de contraseña
        nuevoUsuario.setRol(rolAsignado);
        // nuevoUsuario.setNombre(nombre); // Descomenta si tu clase tiene atributo nombre

        // Insertamos el usuario dentro de tu Tabla Hash real a través de tu método de registro
        // (Ajusta "registrarUsuario" al método exacto que use tu SistemaDelivery para guardar en la Tabla Hash)
        sistema.registrarUsuario(nuevoUsuario); 

        // Actualizamos nuestro contador global de control
        totalUsuarios++;
        application.setAttribute("contadorUsuarios", totalUsuarios);

        // Guardamos un mensaje de éxito y mandamos al Login
        session.setAttribute("errorLogin", "¡Registro exitoso como " + rolAsignado.toUpperCase() + "! Ya puedes iniciar sesión.");
        response.sendRedirect(request.getContextPath() + "/redirect.jsp");
    } else {
        response.sendRedirect(request.getContextPath() + "/redirect.jsp?accion=irARegistro");
    }
%>