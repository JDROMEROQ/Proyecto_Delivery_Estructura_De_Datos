<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Sistema Delivery</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 320px; }
        h2 { text-align: center; color: #333; }
        input[type="email"], input[type="password"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; margin-bottom: 15px; }
        button { width: 100%; padding: 10px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; width: 100%; }
        button:hover { background-color: #218838; }
        .error { color: red; text-align: center; margin-bottom: 15px; font-size: 14px; }
        .register-link { text-align: center; margin-top: 15px; font-size: 14px; }
        .register-link a { color: #007bff; text-decoration: none; }
    </style>
</head>
<body>

<div class="login-card">
    <h2>Iniciar Sesión</h2>
    
    <% if (session.getAttribute("errorLogin") != null) { %>
        <div class="error"><%= session.getAttribute("errorLogin") %></div>
        <% session.removeAttribute("errorLogin"); %>
    <% } %>

    <form action="${pageContext.request.contextPath}/controladorLogin.jsp" method="POST">
        <input type="email" name="txtCorreo" placeholder="ejemplo@delivery.com" required>
        <input type="password" name="txtPassword" placeholder="Contraseña" required>
        <button type="submit">Ingresar</button>
    </form>

    <div class="register-link">
        ¿No tienes cuenta? <a href="${pageContext.request.contextPath}/redirect.jsp?accion=irARegistro">Regístrate aquí</a>
    </div>
</div>

</body>
</html>
