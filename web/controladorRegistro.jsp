<%-- 
    Document   : controladorRegistro
    Created on : 17/05/2026, 7:22:03 p. m.
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
    
    System.out.println("=== REGISTRO ===");
    System.out.println("Nombre: " + nombre);
    System.out.println("Correo: " + correo);
    System.out.println("Todos: " + sistema.obtenerTodos().size());

    boolean esElPrimero = sistema.obtenerTodos().isEmpty();
    String rol = esElPrimero ? "ADMINISTRADOR" : "CLIENTE";

    System.out.println("Es primero: " + esElPrimero + " | Rol: " + rol);

    boolean ok;
    if (esElPrimero) {
        ok = sistema.registrarPrimerAdmin(nombre, correo, clave);
    } else {
        Usuarios_Proyecto nuevo = new Usuarios_Proyecto(0, nombre, correo, clave, rol);
        ok = sistema.registrarUsuario(nuevo);
    }

    System.out.println("Resultado ok: " + ok);

    if (ok) {
        response.sendRedirect(request.getContextPath() + "/redirect.jsp");
    } else {
        session.setAttribute("errorRegistro", "El correo ya está registrado o hubo un error.");
        response.sendRedirect(request.getContextPath() + "/registro.jsp");
    }
%>