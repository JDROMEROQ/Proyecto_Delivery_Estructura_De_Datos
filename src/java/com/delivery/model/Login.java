/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class Login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String contraseña = request.getParameter("contraseña");

        // ADMIN
        if(usuario.equals("admin") && contraseña.equals("123")) {

            response.sendRedirect("admin.jsp");

        }
        // CLIENTE
        else if(usuario.equals("Cliente") && contraseña.equals("567")) {

            response.sendRedirect("cliente.jsp");

        }
        // repartidor
        else if(usuario.equals("repartidor") && contraseña.equals("567")) {

            response.sendRedirect("repartidor.jsp");

        }
        // ERROR
        else {

            response.getWriter().println(
                "<h1>Usuario o contraseña incorrectos</h1>"
            );
        }
    }
}