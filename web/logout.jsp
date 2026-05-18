<%-- 
    Document   : logout
    Created on : 17/05/2026, 7:38:00 p. m.
    Author     : sebas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.invalidate(); // Destruye la sesión actual del usuario
    response.sendRedirect(request.getContextPath() + "/redirect.jsp"); // Regresa al Login
%>
