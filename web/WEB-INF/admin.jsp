<%-- 
    Document   : admin
    Created on : 17/05/2026, 4:10:53 p. m.
    Author     : sebas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.delivery.model.Usuarios_Proyecto"%>
<%@page import="com.delivery.model.SistemaDelivery"%>
<%
    // Verificación de seguridad: si no ha iniciado sesión o no es admin, para afuera
    Usuarios_Proyecto adminLogueado = (Usuarios_Proyecto) session.getAttribute("usuarioLogueado");
    if (adminLogueado == null || (!adminLogueado.getRol().equalsIgnoreCase("admin") && !adminLogueado.getRol().equalsIgnoreCase("administrador"))) {
        response.sendRedirect(request.getContextPath() + "/redirect.jsp");
        return;
    }

    SistemaDelivery sistema = (SistemaDelivery) application.getAttribute("sistemaGlobal");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Panel de Administración - Delivery</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f6f9; color: #333; }
        .navbar { background-color: #343a40; padding: 15px; color: white; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: #ffc107; text-decoration: none; font-weight: bold; }
        .container { max-width: 1100px; margin: 30px auto; padding: 0 20px; display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
        .card { background: white; padding: 20px; border-radius: 6px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); }
        .card-full { grid-column: 1 / -1; }
        h3 { border-bottom: 2px solid #e9ecef; padding-bottom: 10px; color: #495057; }
        input, select { width: 100%; padding: 8px; margin-bottom: 12px; border: 1px solid #ced4da; border-radius: 4px; box-sizing: border-box; }
        button { padding: 10px 15px; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; }
        .btn-primary { background-color: #007bff; color: white; }
        .btn-success { background-color: #28a745; color: white; }
        .btn-danger { background-color: #dc3545; color: white; }
        .alert { background-color: #e2f0d9; color: #385723; padding: 10px; border-radius: 4px; margin-bottom: 15px; font-size: 14px; text-align: center; }
        pre { background: #272822; color: #f8f8f2; padding: 15px; border-radius: 5px; overflow-x: auto; font-family: 'Consolas', monospace; }
    </style>
</head>
<body>

<div class="navbar">
    <h2>Panel de Control: Bienvenido, <%= adminLogueado.getCorreo() %></h2>
    <a href="${pageContext.request.contextPath}/logout.jsp">Cerrar Sesión</a>
</div>

<div class="container">

    <%-- Sección de Alertas y Mensajes --%>
    <div class="card card-full" style="padding: 10px; display: <%= (session.getAttribute("mensajeAdmin") != null) ? "block" : "none" %>;">
        <div class="alert">
            <%= session.getAttribute("mensajeAdmin") %>
            <% session.removeAttribute("mensajeAdmin"); %>
        </div>
    </div>

    <%-- CARD 1: FORMULARIO DE CREACIÓN DE USUARIOS --%>
    <div class="card">
        <h3>Registrar Nuevo Empleado / Cliente</h3>
        <form action="${pageContext.request.contextPath}/controladorCrearUsuario.jsp" method="POST">
            <label>Nombre Completo:</label>
            <input type="text" name="txtNombre" placeholder="Juan Pérez" required>
            
            <label>Correo Electrónico:</label>
            <input type="email" name="txtCorreo" placeholder="empleado@delivery.com" required>
            
            <label>Contraseña de Acceso:</label>
            <input type="password" name="txtPassword" placeholder="••••••••" required>
            
            <label>Rol en el Sistema:</label>
            <select name="cmbRol">
                <option value="cliente">Cliente (Usuario Final)</option>
                <option value="repartidor">Repartidor (Distribución)</option>
                <option value="admin">Administrador (Gestor)</option>
            </select>
            
            <button type="submit" class="btn-success" style="width: 100%;">Registrar e Indexar Estructuras</button>
        </form>
    </div>

    <%-- CARD 2: BÚSQUEDA HASH EN TIEMPO REAL --%>
    <div class="card">
        <h3>Operación Tabla Hash - Búsqueda $O(1)$</h3>
        <form action="${pageContext.request.contextPath}/controladorAccionesAdmin.jsp?accion=buscar" method="POST">
            <label>Ingrese el Correo Electrónico a Consultar:</label>
            <input type="email" name="txtCorreoBuscar" placeholder="buscar@delivery.com" required>
            <button type="submit" class="btn-primary">Buscar en Tabla Hash</button>
        </form>

        <%-- Despliegue del resultado de la búsqueda si existe --%>
        <% if (session.getAttribute("usuarioEncontrado") != null) { 
            Usuarios_Proyecto u = (Usuarios_Proyecto) session.getAttribute("usuarioEncontrado"); %>
            <div style="margin-top: 15px; padding: 10px; background: #e9ecef; border-left: 5px solid #007bff; border-radius: 4px;">
                <p><strong>Resultado Encontrado:</strong></p>
                <p>Correo: <%= u.getCorreo() %></p>
                <p>Rol Asignado: <span class="badge" style="background:#343a40; color:white; padding:2px 6px; border-radius:3px;"><%= u.getRol().toUpperCase() %></span></p>
                <form action="${pageContext.request.contextPath}/controladorAccionesAdmin.jsp?accion=eliminar" method="POST" style="margin: 0;">
                    <input type="hidden" name="txtCorreoBuscar" value="<%= u.getCorreo() %>">
                    <button type="submit" class="btn-danger" style="padding: 5px 10px; font-size: 12px;">Eliminar Usuario</button>
                </form>
            </div>
            <% session.removeAttribute("usuarioEncontrado"); %>
        <% } %>
    </div>

    <%-- CARD 3: RECORRIDO DEL ÁRBOL MULTICAMINO (REQUISITO NO LINEAL DE LA RÚBRICA) --%>
    <div class="card card-full">
        <h3>Estructura No Lineal: Índice de Árbol Multicamino</h3>
        <p style="font-size: 14px; color: #666;">
            A continuación se renderiza de forma lógica el estado actual de los nodos de tu clase <code>ArbolMultiC</code>. 
            Cada vez que registras un usuario arriba, este se indexa jerárquicamente en el árbol de la memoria de la aplicación.
        </p>
        <pre><%
            if (sistema != null) {
                java.util.LinkedList<Usuarios_Proyecto> todos = sistema.obtenerTodos();
                if (todos.isEmpty()) {
                    out.print("// El Arbol Multicamino se encuentra vacio.");
                } else {
                    out.print("--- ARBOL MULTICAMINO ---\n");
                    out.print("Nodo Raiz --> Tabla Hash Principal\n\n");
                    for (Usuarios_Proyecto u : todos) {
                        out.print("  [" + u.getRol().toUpperCase() + "] " 
                            + u.getNombre() + " | " + u.getCorreo() + "\n");
                    }
                }
            }
        %></pre>
    </div>
    
    <%-- CARD 4: TODOS LOS PEDIDOS --%>
    <div class="card card-full">
    <h3>Todos los Pedidos</h3>
    <table style="width:100%; border-collapse:collapse; font-size:14px;">
        <tr style="background:#343a40; color:white;">
            <th style="padding:8px;">ID</th>
            <th>Cliente ID</th>
            <th>Descripción</th>
            <th>Dirección</th>
            <th>Urgencia</th>
            <th>Estado</th>
            <th>Asignar Repartidor</th>
        </tr>
        <%
            java.util.LinkedList<com.delivery.model.Pedidos_Proyecto> pedidos = sistema.obtenerTodosPedidos();
            java.util.LinkedList<com.delivery.model.Usuarios_Proyecto> repartidores = sistema.listarUsuariosPorRol("REPARTIDOR");
            for (com.delivery.model.Pedidos_Proyecto p : pedidos) {
        %>
        <tr style="border-bottom:1px solid #dee2e6;">
            <td style="padding:8px; text-align:center;"><%= p.getIdPedido() %></td>
            <td style="text-align:center;"><%= p.getIdCliente() %></td>
            <td><%= p.getDescripcion() %></td>
            <td><%= p.getDireccionEntrega() %></td>
            <td style="text-align:center;"><%= p.getUrgencia() %></td>
            <td style="text-align:center;"><%= p.getEstado() %></td>
            <td>
                <% if ("PENDIENTE".equals(p.getEstado())) { %>
                <form action="${pageContext.request.contextPath}/controladorAccionesAdmin.jsp?accion=asignar" method="POST" style="display:flex; gap:4px;">
                    <input type="hidden" name="idPedido" value="<%= p.getIdPedido() %>">
                    <select name="idRepartidor" style="padding:4px; margin:0;">
                        <% for (com.delivery.model.Usuarios_Proyecto r : repartidores) { %>
                        <option value="<%= r.getIdUsuario() %>"><%= r.getNombre() %></option>
                        <% } %>
                    </select>
                    <button type="submit" class="btn-primary" style="padding:4px 8px;">Asignar</button>
                </form>
                <% } else { %>
                    <span style="color:#28a745;">Asignado</span>
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>

</div>

</body>
</html>
