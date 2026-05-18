<%-- 
    Document   : controladorLogin
    Created on : 17/05/2026, 6:43:42 p. m.
    Author     : dr405
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.delivery.model.SistemaDelivery"%>
<%@page import="com.delivery.model.Usuarios_Proyecto"%>
<%
    // 1. Instanciamos el núcleo de tu entrega (Tabla Hash)
    SistemaDelivery sistema = new SistemaDelivery();

    // 2. Capturamos los datos enviados desde el formulario nativo
    String correo = request.getParameter("txtCorreo");
    String password = request.getParameter("txtPassword");

    if (correo != null && password != null) {
        // 3. Realizamos la autenticación real en tu lógica interna
        Usuarios_Proyecto usuarioAutenticado = sistema.realizarLogin(correo, password);

        if (usuarioAutenticado != null) {
            // Guardamos el objeto completo en la sesión por si necesitas sus datos luego
            session.setAttribute("usuarioLogueado", usuarioAutenticado);
            
            // Normalizamos la cadena del Rol
            String rol = usuarioAutenticado.getRol().toLowerCase().trim();

            // 4. Despacho interno seguro hacia los JSP dentro de WEB-INF
            if (rol.equals("admin") || rol.equals("administrador")) {
                request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
            } else if (rol.equals("cliente")) {
                request.getRequestDispatcher("/WEB-INF/cliente.jsp").forward(request, response);
            } else if (rol.equals("repartidor")) {
                request.getRequestDispatcher("/WEB-INF/repartidor.jsp").forward(request, response);
            } else {
                session.setAttribute("errorLogin", "El rol obtenido no cuenta con un espacio de trabajo.");
                response.sendRedirect(request.getContextPath() + "/redirect.jsp");
            }
        } else {
            // Credenciales incorrectas
            session.setAttribute("errorLogin", "Correo electrónico o contraseña incorrectos.");
            response.sendRedirect(request.getContextPath() + "/redirect.jsp");
        }
    } else {
        // Intento de entrada forzada sin datos
        response.sendRedirect(request.getContextPath() + "/redirect.jsp");
    }
%>