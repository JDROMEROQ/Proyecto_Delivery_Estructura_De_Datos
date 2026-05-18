package com.delivery.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controlador de Autenticación compatible con el servidor Tomcat
 * @author dr405
 */
@Controller
public class ControladorLogin {

    private static SistemaDelivery sistema = new SistemaDelivery();

    // Este método se activa cuando entras por primera vez a index.htm
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public ModelAndView mostrarLogin() {
        return new ModelAndView("index"); // Carga index.jsp de forma limpia
    }

    // Este método atrapa el envío de los datos del formulario por POST
    @RequestMapping(value = "/login.htm", method = RequestMethod.POST)
    public ModelAndView procesarLogin(HttpServletRequest request, HttpServletResponse response) {
        String correo = request.getParameter("txtCorreo");
        String password = request.getParameter("txtPassword");

        Usuarios_Proyecto usuarioAutenticado = sistema.realizarLogin(correo, password);

        if (usuarioAutenticado != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuarioAutenticado);
            
            String rol = usuarioAutenticado.getRol().toLowerCase().trim();

            switch (rol) {
                case "admin":
                case "administrador":
                    return new ModelAndView("Administrador");
                case "cliente":
                    return new ModelAndView("Cliente");
                case "repartidor":
                    return new ModelAndView("repartidor");
                default:
                    request.setAttribute("error", "El rol asignado no cuenta con un espacio de trabajo.");
                    return new ModelAndView("index");
            }
        } else {
            request.setAttribute("error", "Correo electrónico o contraseña incorrectos.");
            return new ModelAndView("index");
        }
    }
}