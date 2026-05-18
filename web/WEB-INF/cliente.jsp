<%-- 
    Document   : cliente
    Created on : 17/05/2026, 4:11:02 p. m.
    Author     : sebas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.delivery.model.Usuarios_Proyecto"%>
<%@page import="com.delivery.model.SistemaDelivery"%>
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
                <option value="1">Prioridad Baja (Entrega estándar)</option>
                <option value="2">Prioridad Media (Envío moderado)</option>
                <option value="3">Prioridad Alta (¡Emergencia / Entrega Inmediata!)</option>
            </select>
            
            <button type="submit">Enviar Pedido al Sistema</button>
        </form>
    </div>

    <%-- ESTADO ACADÉMICO DEL MONTÍCULO (HEAP) --%>
    <div class="card">
        <h3>Estado de la Cola de Prioridad </h3>
        <p style="font-size: 14px; color: #666;">
            Cada vez que agregas un pedido arriba, tu estructura de datos de tipo <strong>Montículo</strong> 
            la reubica en memoria usando el método de flotación.
        </p>
        <pre><%
            if (sistema != null && sistema.getColaPedidos() != null) {
                out.print("// Cola de prioridad sincronizada y lista en memoria RAM.\n");
                out.print("// Estado del Heap: Activo esperando asignación a repartidores.");
            } else {
                out.print("// El Heap de pedidos no se ha inicializado.");
            }
        %></pre>
    </div>

</div>

</body>
</html>
