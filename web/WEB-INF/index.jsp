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
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #666; }
        input[type="text"], input[type="password"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        button:hover { background-color: #218838; }
        .error { color: red; text-align: center; margin-bottom: 15px; font-size: 14px; }
    </style>
</head>
<body>

<div class="login-card">
    <h2>Iniciar Sesión</h2>
    
    <%-- Muestra un mensaje si las credenciales son incorrectas --%>
    <% if(request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>

    <%-- El formulario envía los datos a login.htm usando el método POST --%>
    <form action="login.htm" method="POST">
        <div class="form-group">
            <label>Correo Electrónico:</label>
            <input type="text" name="txtCorreo" required placeholder="ejemplo@delivery.com">
        </div>
        <div class="form-group">
            <label>Contraseña:</label>
            <input type="password" name="txtPassword" required placeholder="********">
        </div>
        <button type="submit">Ingresar</button>
    </form>
</div>

</body>
</html>
