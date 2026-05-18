<%-- 
    Document   : redirect
    Created on : 17/05/2026, 7:01:03 p. m.
    Author     : dr405
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    
    request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
%>