<%-- 
    Document   : controladorAccionesAdmin
    Created on : 17/05/2026, 7:34:57 p. m.
    Author     : dr405
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.delivery.model.SistemaDelivery"%>
<%@page import="com.delivery.model.Usuarios_Proyecto"%>
<%
    SistemaDelivery sistema = (SistemaDelivery) application.getAttribute("sistemaGlobal");
    String accion = request.getParameter("accion");
    String correoBuscar = request.getParameter("txtCorreoBuscar");

    if ("buscar".equals(accion) && correoBuscar != null) {
        // 🔎 Accedemos a tu tablaUsuarios global y usamos tu método buscarHash
        Usuarios_Proyecto usuarioEncontrado = sistema.getTablaUsuarios().buscarHash(correoBuscar); 
        
        if (usuarioEncontrado != null) {
            session.setAttribute("usuarioEncontrado", usuarioEncontrado);
        } else {
            session.setAttribute("mensajeAdmin", "Usuario no encontrado en la Tabla Hash.");
        }
    }
    
    request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
%>
