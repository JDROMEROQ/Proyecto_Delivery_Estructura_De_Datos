<%-- 
    Document   : controladorCrearPedido
    Created on : 17/05/2026, 7:52:22 p. m.
    Author     : sebas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.delivery.model.SistemaDelivery"%>
<%@page import="com.delivery.model.Pedidos_Proyecto"%>
<%@page import="com.delivery.model.Usuarios_Proyecto"%>
<%
    // 1. Recuperamos el sistema global de la memoria de la aplicación
    SistemaDelivery sistema = (SistemaDelivery) application.getAttribute("sistemaGlobal");
    
    // 2. Recuperamos el usuario que está logueado en la sesión para saber su ID
    Usuarios_Proyecto clienteLogueado = (Usuarios_Proyecto) session.getAttribute("usuarioLogueado");

    String descripcion = request.getParameter("txtDescripcion");
    String direccion = request.getParameter("txtDireccion");
    String urgenciaStr = request.getParameter("cmbUrgencia");

    if (descripcion != null && direccion != null && urgenciaStr != null && clienteLogueado != null) {
        int urgencia = Integer.parseInt(urgenciaStr);

        // 3. Creamos el objeto de tu clase de pedidos nativa
        Pedidos_Proyecto nuevoPedido = new Pedidos_Proyecto();
        nuevoPedido.setIdCliente(clienteLogueado.getIdUsuario()); // Vinculamos el ID del usuario
        nuevoPedido.setDescripcion(descripcion);
        nuevoPedido.setDireccionEntrega(direccion);
        nuevoPedido.setUrgencia(urgencia);
        nuevoPedido.setEstado("PENDIENTE");

        // 4. Invocamos tu método del Heap que rescatamos en SistemaDelivery
        sistema.registrarPedido(nuevoPedido);

        session.setAttribute("mensajeCliente", "¡Pedido enviado con éxito! Ordenada en el Heap con prioridad: " + urgencia);
    } else {
        session.setAttribute("mensajeCliente", "Error: No se pudo registrar el pedido.");
    }

    // Regresamos al panel del cliente
    request.getRequestDispatcher("/WEB-INF/cliente.jsp").forward(request, response);
%>