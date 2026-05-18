<%-- 
    Document   : cliente
    Created on : 17/05/2026, 4:11:02 p. m.
    Author     : sebas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.delivery.model.Usuarios_Proyecto"%>
<%@page import="com.delivery.model.SistemaDelivery"%>
<%@ page import="com.delivery.model.Pedidos_Proyecto" %>
<%
    // Seguridad: Si no hay sesión o no es cliente, para afuera
    Usuarios_Proyecto clienteLogueado = (Usuarios_Proyecto) session.getAttribute("usuarioLogueado");
    if (clienteLogueado == null || !clienteLogueado.getRol().equalsIgnoreCase("cliente")) {
        response.sendRedirect(request.getContextPath() + "/redirect.jsp");
        return;
    }

    SistemaDelivery sistema = (SistemaDelivery) application.getAttribute("sistemaGlobal");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Portal de Clientes - Delivery</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f6f9; color: #333; }
        .navbar { background-color: #28a745; padding: 15px; color: white; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: white; text-decoration: none; font-weight: bold; border: 1px solid white; padding: 5px 10px; border-radius: 4px; }
        .navbar a:hover { background: white; color: #28a745; }
        .container { max-width: 800px; margin: 30px auto; padding: 0 20px; }
        .card { background: white; padding: 25px; border-radius: 6px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); margin-bottom: 20px; }
        h3 { border-bottom: 2px solid #e9ecef; padding-bottom: 10px; color: #495057; margin-top: 0; }
        label { font-weight: bold; display: block; margin-bottom: 5px; color: #666; }
        input[type="text"], select { width: 100%; padding: 10px; margin-bottom: 15px; border: 1px solid #ced4da; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; font-weight: bold; }
        button:hover { background-color: #218838; }
        .alert { background-color: #d1ecf1; color: #0c5460; padding: 12px; border-radius: 4px; margin-bottom: 20px; text-align: center; font-size: 14px; }
        pre { background: #272822; color: #f8f8f2; padding: 15px; border-radius: 5px; font-family: 'Consolas', monospace; }
    </style>
</head>
<body>

<div class="navbar">
    <h2>Panel de Pedidos: Bienvenido, <%= clienteLogueado.getCorreo() %></h2>
    <a href="${pageContext.request.contextPath}/logout.jsp">Cerrar Sesión</a>
</div>

<div class="container">

    <%-- Mensajes de Éxito --%>
    <% if (session.getAttribute("mensajeCliente") != null) { %>
        <div class="alert">
            <%= session.getAttribute("mensajeCliente") %>
            <% session.removeAttribute("mensajeCliente"); %>
        </div>
    <% } %>

    <%-- FORMULARIO PARA CREAR PEDIDO --%>
    <div class="card">
        <h3>Solicitar Nuevo Envío a Domicilio</h3>
        <form action="${pageContext.request.contextPath}/controladorCrearPedido.jsp" method="POST">
            
            <label>Descripción del Paquete / Mandado:</label>
            <input type="text" name="txtDescripcion" placeholder="Ej. Hamburguesa doble con papas grandes y soda" required>
            
            <label>Dirección Exacta de Entrega:</label>
            <input type="text" name="txtDireccion" placeholder="Ej. 10a Avenida 3-45, Zona 1, Ciudad" required>
            
            <label>Nivel de Urgencia (Prioridad del Heap):</label>
            <select name="cmbUrgencia">
                <option value="1">Prioridad Baja</option>
                <option value="2">Prioridad Media</option>
                <option value="3">Prioridad Alta</option>
            </select>
            
            <button type="submit">Enviar Pedido al Sistema</button>
        </form>
    </div>
        
        <%-- SECCIÓN: ESTADO E HISTORIAL DE PEDIDOS --%>
    <%
        java.util.LinkedList<Pedidos_Proyecto> misPedidos = sistema.obtenerPedidosPorCliente(clienteLogueado.getIdUsuario());
    %>
    <div style="max-width: 800px; margin: 20px auto; padding: 0 20px;">
        <div style="background: white; padding: 20px; border-radius: 6px; box-shadow: 0 2px 4px rgba(0,0,0,0.05);">
            <h3 style="border-bottom: 2px solid #e9ecef; padding-bottom: 10px; color: #495057;">
                Mis Pedidos — Estado e Historial
            </h3>

            <% if (misPedidos.isEmpty()) { %>
                <p style="text-align:center; color:#666; padding:20px;">
                    No tienes pedidos registrados aún.
                </p>
            <% } else { %>
            <table style="width:100%; border-collapse:collapse; font-size:14px;">
                <tr style="background:#343a40; color:white;">
                    <th style="padding:10px;">ID</th>
                    <th style="padding:10px;">Descripción</th>
                    <th style="padding:10px;">Dirección</th>
                    <th style="padding:10px;">Urgencia</th>
                    <th style="padding:10px;">Estado</th>
                </tr>
                <% for (Pedidos_Proyecto p : misPedidos) { 
                    String colorEstado;
                    switch (p.getEstado()) {
                        case "PENDIENTE": colorEstado = "#ffc107"; break;
                        case "ASIGNADO":  colorEstado = "#007bff"; break;
                        case "ENTREGADO": colorEstado = "#28a745"; break;
                        default:          colorEstado = "#6c757d";
                    }
                %>
                <tr style="border-bottom:1px solid #dee2e6;">
                    <td style="padding:10px; text-align:center;">#<%= p.getIdPedido() %></td>
                    <td style="padding:10px;"><%= p.getDescripcion() %></td>
                    <td style="padding:10px;"><%= p.getDireccionEntrega() %></td>
                    <td style="padding:10px; text-align:center;">
                        <% 
                            String nivelUrgencia;
                            switch (p.getUrgencia()) {
                                case 1: nivelUrgencia = "Baja"; break;
                                case 2: nivelUrgencia = "Media"; break;
                                case 3: nivelUrgencia = "Alta"; break;
                                default: nivelUrgencia = String.valueOf(p.getUrgencia());
                            }
                        %>
                        <%= nivelUrgencia %>
                    </td>
                    <td style="padding:10px; text-align:center;">
                        <span style="background:<%= colorEstado %>; color:white; padding:4px 10px; border-radius:12px; font-size:12px; font-weight:bold;">
                            <%= p.getEstado() %>
                        </span>
                    </td>
                </tr>
                <% } %>
            </table>
            <% } %>
        </div>
    </div>

    <%-- ESTADO ACADÉMICO DEL MONTÍCULO (HEAP) --%>
    <div class="card">
        <h3>Estado de la Cola de Prioridad </h3>
        <p style="font-size: 14px; color: #666;">
            Cada vez que agregas un pedido arriba, tu estructura de datos de tipo <strong>Montículo</strong> 
            la reubica en memoria usando el método de flotación.
        </p>
        <pre><%
            if (sistema != null) {
                java.util.LinkedList<Pedidos_Proyecto> colaPedidos = sistema.obtenerPedidosPorCliente(clienteLogueado.getIdUsuario());
                if (colaPedidos.isEmpty()) {
                    out.print("// Cola de prioridad vacia. No hay pedidos pendientes.");
                } else {
                    out.print("--- COLA DE PRIORIDAD (MAX-HEAP) ---\n\n");
                    for (Pedidos_Proyecto p : colaPedidos) {
                        if (p.getEstado().equals("PENDIENTE") || p.getEstado().equals("ASIGNADO")) {
                            out.print("  Urgencia [" + p.getUrgencia() + "] --> Pedido #" 
                                + p.getIdPedido() + ": " + p.getDescripcion() 
                                + " | Estado: " + p.getEstado() + "\n");
                        }
                    }
                }
            }
        %></pre>
    </div>

</div>

</body>
</html>
