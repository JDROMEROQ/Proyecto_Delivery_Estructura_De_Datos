<%-- 
    Document   : redirect
    Created on : 17/05/2026, 7:01:03 p. m.
    Author     : dr405
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String accion = request.getParameter("accion");
    if ("irARegistro".equals(accion)) {
        request.getRequestDispatcher("/WEB-INF/registro.jsp").forward(request, response);
    } else {
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }
%>