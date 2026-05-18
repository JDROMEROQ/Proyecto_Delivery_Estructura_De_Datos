<%-- 
    Document   : controladorLogin
    Created on : 17/05/2026, 6:43:42 p. m.
    Author     : dr405
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.delivery.model.SistemaDelivery"%>
<%@page import="com.delivery.model.Usuarios_Proyecto"%>
<%
    // Leemos el mismo espacio de memoria RAM persistente que usó el registro
    SistemaDelivery sistema = (SistemaDelivery) application.getAttribute("sistemaGlobal");
    if (sistema == null) {
        sistema = new SistemaDelivery();
        application.setAttribute("sistemaGlobal", sistema);
    }

    String correo = request.getParameter("txtCorreo");
    String password = request.getParameter("txtPassword");

    if (correo != null && password != null) {
        Usuarios_Proyecto usuarioAutenticado = sistema.realizarLogin(correo, password);

        if (usuarioAutenticado != null) {
            session.setAttribute("usuarioLogueado", usuarioAutenticado);
            String rol = usuarioAutenticado.getRol().toLowerCase().trim();

            if (rol.equals("admin") || rol.equals("administrador")) {
                request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
            } else if (rol.equals("cliente")) {
                request.getRequestDispatcher("/WEB-INF/cliente.jsp").forward(request, response);
            } else if (rol.equals("repartidor")) {
                request.getRequestDispatcher("/WEB-INF/repartidor.jsp").forward(request, response);
            } else {
                session.setAttribute("errorLogin", "El rol asignado no cuenta con un área de trabajo.");
                response.sendRedirect(request.getContextPath() + "/redirect.jsp");
            }
        } else {
            session.setAttribute("errorLogin", "Correo o contraseña incorrectos.");
            response.sendRedirect(request.getContextPath() + "/redirect.jsp");
        }
    } else {
        response.sendRedirect(request.getContextPath() + "/redirect.jsp");
    }
%>