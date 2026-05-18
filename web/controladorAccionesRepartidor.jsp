<%-- 
    Document   : controladorAccionesRepartidor
    Created on : 17/05/2026, 11:05:36 p. m.
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

    int idPedido = Integer.parseInt(request.getParameter("idPedido"));
    boolean ok = sistema.marcarEntregado(idPedido);

    session.setAttribute("mensajeRepartidor", ok 
        ? "Pedido #" + idPedido + " marcado como entregado correctamente." 
        : "Error al marcar el pedido como entregado.");

    request.getRequestDispatcher("/WEB-INF/repartidor.jsp").forward(request, response);
%>
