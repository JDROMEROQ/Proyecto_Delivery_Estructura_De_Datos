/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controlador puro de Spring sin usar 'implements'
 * @author dr405
 */
@Controller
public class ControladorLogin {

    // Instancia global de tu lógica con Oracle y estructuras
    private static SistemaDelivery sistema = new SistemaDelivery();

    // Reemplaza el método handleRequest por una función propia normal
    @RequestMapping(value = "/login.htm", method = RequestMethod.POST)
    public ModelAndView procesarLogin(HttpServletRequest request, HttpServletResponse response) {
        
        // 1. Capturamos los datos que viajan desde el formulario JSP
        String correo = request.getParameter("txtCorreo");
        String password = request.getParameter("txtPassword");

        // 2. Buscamos en tu Tabla Hash real
        Usuarios_Proyecto usuarioAutenticado = sistema.realizarLogin(correo, password);

        if (usuarioAutenticado != null) {
            // Guardamos el usuario en la sesión para recordar el acceso
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuarioAutenticado);
            
            String rol = usuarioAutenticado.getRol().toLowerCase().trim();

            // 3. Redirección inteligente según el rol de la BD
            switch (rol) {
                case "admin":
                case "administrador":
                    return new ModelAndView("Administrador"); // Abre /WEB-INF/jsp/Administrador.jsp
                case "cliente":
                    return new ModelAndView("Cliente");       // Abre /WEB-INF/jsp/Cliente.jsp
                case "repartidor":
                    return new ModelAndView("repartidor");    // Abre /WEB-INF/jsp/repartidor.jsp
                default:
                    request.setAttribute("error", "El rol asignado no cuenta con un espacio de trabajo.");
                    return new ModelAndView("index");
            }
        } else {
            // Error si no coincide en tus estructuras de datos
            request.setAttribute("error", "Correo electrónico o contraseña incorrectos.");
            return new ModelAndView("index");
        }
    }
}
