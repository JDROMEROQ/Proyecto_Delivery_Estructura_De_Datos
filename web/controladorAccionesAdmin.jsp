<%-- 
    Document   : controladorAccionesAdmin
    Created on : 17/05/2026, 7:34:57 p. m.
    Author     : sebas
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.delivery.model.*" %>
<%
    SistemaDelivery sistema = (SistemaDelivery) application.getAttribute("sistemaGlobal");
    if (sistema == null) {
        sistema = new SistemaDelivery();
        application.setAttribute("sistemaGlobal", sistema);
    }

    String accion = request.getParameter("accion");

    if ("buscar".equals(accion)) {
        String correo = request.getParameter("txtCorreoBuscar");
        Usuarios_Proyecto encontrado = sistema.getTablaUsuarios().buscarHash(correo);
        if (encontrado != null) {
            session.setAttribute("usuarioEncontrado", encontrado);
        } else {
            session.setAttribute("mensajeAdmin", "No se encontró ningún usuario con ese correo.");
        }

    } else if ("eliminar".equals(accion)) {
        String correo = request.getParameter("txtCorreoBuscar");
        boolean ok = sistema.eliminarUsuario(correo);
        session.setAttribute("mensajeAdmin", ok 
            ? "Usuario eliminado correctamente." 
            : "Error al eliminar el usuario.");

    } else if ("asignar".equals(accion)) {
        int idPedido     = Integer.parseInt(request.getParameter("idPedido"));
        int idRepartidor = Integer.parseInt(request.getParameter("idRepartidor"));
        boolean ok = sistema.asignarRepartidor(idPedido, idRepartidor);
        session.setAttribute("mensajeAdmin", ok 
            ? "Repartidor asignado correctamente." 
            : "Error al asignar repartidor.");
    }

    request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
%>
