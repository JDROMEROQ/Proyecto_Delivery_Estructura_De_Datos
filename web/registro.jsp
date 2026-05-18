<%-- 
    Document   : registro
    Created on : 17/05/2026, 7:21:45 p. m.
    Author     : dr405
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro - Sistema Delivery</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .register-card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 320px; }
        h2 { text-align: center; color: #333; }
        input[type="text"], input[type="email"], input[type="password"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; margin-bottom: 15px; }
        button { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; width: 100%; }
        button:hover { background-color: #0056b3; }
        .back-link { text-align: center; margin-top: 15px; font-size: 14px; }
        .back-link a { color: #666; text-decoration: none; }
    </style>
</head>
<body>

<div class="register-card">
    <h2>Crear Cuenta</h2>
    
   <form action="${pageContext.request.contextPath}/controladorRegistro.jsp" method="POST">
        <input type="text" name="txtNombre" placeholder="Nombre Completo" required>
        <input type="email" name="txtCorreo" placeholder="Correo electrónico" required>
        <input type="password" name="txtPassword" placeholder="Contraseña" required>
        <button type="submit">Registrarme</button>
    </form>

    <div class="back-link">
        <a href="${pageContext.request.contextPath}/redirect.jsp">Volver al Login</a>
    </div>
</div>

</body>
</html>
