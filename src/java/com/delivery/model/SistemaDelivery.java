/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;

/**
 *
 * @author dr405
 */
public class SistemaDelivery {
    
    /*
        Checkout de la nueva rama o branche...
    */
    private TablaHash<Usuarios_Proyecto> tablaUsuarios;

    public SistemaDelivery() {
        this.tablaUsuarios = new TablaHash<>(11); // Tamaño inicial
    }

    public void registrarUsuario(Usuarios_Proyecto nuevo) {
        // Verificacion si ya existe antes de registrarlo
        if (tablaUsuarios.buscarHash(nuevo.getCorreo()) != null) {
            System.out.println("Error: El correo " + nuevo.getCorreo() + " ya está registrado.");
        } else {
            tablaUsuarios.insertarHash(nuevo.getCorreo(), nuevo);
            System.out.println("Usuario registrado con éxito.");
        }
    }
    
    public Usuarios_Proyecto realizarLogin(String correoIngresado, String claveIngresada) {
        // Buscamos al usuario en la Tabla Hash usando el correo como llave
        Usuarios_Proyecto user = tablaUsuarios.buscarHash(correoIngresado);

        // Validación de existencia
        if (user == null) {
            System.out.println("Correo no encontrado, por favor vuelva a ingresar un correo registrado.");
            return null; 
        }

        // ️Validación de contraseña
        if (user.getClave().equals(claveIngresada)) {
            System.out.println("¡Bienvenido al sistema, " + user.getNombre() + "!");
            return user; // Devolver el objeto para saber quién inició sesión
        } else {
            System.out.println("Contraseña incorrecta. Intente de nuevo.");
            return null;
        }
    }
}
