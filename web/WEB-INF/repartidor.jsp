<%-- 
    Document   : repartidor
    Created on : 17/05/2026, 4:11:08 p. m.
    Author     : sebas
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.delivery.model.*" %>
<%@ page import="java.util.LinkedList" %>
<%
    Usuarios_Proyecto repartidorLogueado = (Usuarios_Proyecto) session.getAttribute("usuarioLogueado");
    if (repartidorLogueado == null || !repartidorLogueado.getRol().equalsIgnoreCase("REPARTIDOR")) {
        response.sendRedirect(request.getContextPath() + "/redirect.jsp");
        return;
    }

    SistemaDelivery sistema = (SistemaDelivery) application.getAttribute("sistemaGlobal");
    if (sistema == null) {
        sistema = new SistemaDelivery();
        application.setAttribute("sistemaGlobal", sistema);
    }

    LinkedList<Pedidos_Proyecto> pedidos = sistema.obtenerPedidosPorRepartidor(repartidorLogueado.getIdUsuario());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Panel Repartidor - Delivery</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f6f9; color: #333; }
        .navbar { background-color: #343a40; padding: 15px; color: white; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: #ffc107; text-decoration: none; font-weight: bold; }
        .container { max-width: 1000px; margin: 30px auto; padding: 0 20px; }
        .card { background: white; padding: 20px; border-radius: 6px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); margin-bottom: 20px; }
        h3 { border-bottom: 2px solid #e9ecef; padding-bottom: 10px; color: #495057; }
        table { width: 100%; border-collapse: collapse; font-size: 14px; }
        th { background: #343a40; color: white; padding: 10px; text-align: left; }
        td { padding: 10px; border-bottom: 1px solid #dee2e6; }
        .btn-success { background-color: #28a745; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer; font-weight: bold; }
        .btn-success:hover { background-color: #218838; }
        .alert { background-color: #e2f0d9; color: #385723; padding: 10px; border-radius: 4px; margin-bottom: 15px; font-size: 14px; text-align: center; }
        .empty { text-align: center; color: #666; padding: 30px; font-size: 16px; }
    </style>
</head>
<body>

<div class="navbar">
    <h2>Panel Repartidor: Bienvenido, <%= repartidorLogueado.getNombre() %></h2>
    <a href="${pageContext.request.contextPath}/logout.jsp">Cerrar Sesión</a>
</div>

<div class="container">

    <% if (session.getAttribute("mensajeRepartidor") != null) { %>
    <div class="alert"><%= session.getAttribute("mensajeRepartidor") %></div>
    <% session.removeAttribute("mensajeRepartidor"); %>
    <% } %>

    <div class="card">
        <h3>Pedidos Asignados</h3>
        <% if (pedidos.isEmpty()) { %>
            <div class="empty">No tienes pedidos asignados actualmente.</div>
        <% } else { %>
        <table>
            <tr>
                <th>ID</th>
                <th>Descripción</th>
                <th>Dirección de Entrega</th>
                <th>Urgencia</th>
                <th>Estado</th>
                <th>Acción</th>
            </tr>
            <% for (Pedidos_Proyecto p : pedidos) { %>
            <tr>
                <td><%= p.getIdPedido() %></td>
                <td><%= p.getDescripcion() %></td>
                <td><%= p.getDireccionEntrega() %></td>
                <td><%= p.getUrgencia() %></td>
                <td><%= p.getEstado() %></td>
                <td>
                    <form action="${pageContext.request.contextPath}/controladorAccionesRepartidor.jsp" method="POST">
                        <input type="hidden" name="idPedido" value="<%= p.getIdPedido() %>">
                        <button type="submit" class="btn-success">Marcar Entregado</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
        <% } %>
    </div>

</div>
</body>
</html>