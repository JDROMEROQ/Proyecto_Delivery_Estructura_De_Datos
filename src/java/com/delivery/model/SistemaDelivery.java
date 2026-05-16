/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;
import java.util.Scanner;
/**
 *
 * @author dr405
 */
public class SistemaDelivery {
    private TablaHash<Usuarios_Proyecto> tablaUsuarios; 
    private Scanner leer;
    private int contadorIds; // atributo para generar un codigo similar a primary key en la base de datos.
   

    public SistemaDelivery() { // construtor.
        this.tablaUsuarios = new TablaHash<>(11); // Tamaño inicial
        this.leer = new Scanner(System.in);
         this.contadorIds = 1;
    }
    
    // metodo inicial del programa.
    private void registrarPrimerAdmin() {
        System.out.println("--- Primer Registro ---");
        System.out.print("Nombre completo: ");
        String nombre = leer.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = leer.nextLine();
        System.out.print("Contraseña: ");
        String clave = leer.nextLine();
        
        int idActual = contadorIds++;

        // Creamos el usuario fijando el rol como "ADMIN" automáticamente
        Usuarios_Proyecto primerAdmin = new Usuarios_Proyecto(idActual, nombre, correo, clave, "ADMIN");
        
        // Lo guardamos en tu Tabla Hash
        tablaUsuarios.insertarHash(correo, primerAdmin);
        System.out.println("Registro inicial completado con éxito. Ahora puede iniciar sesión.");
    }

    public void ejecutarMenuPrincipal() {
        boolean ejecutarSistema = true;

        System.out.println("====== BIENVENIDO AL SISTEMA DE DELIVERY ======");

        while (ejecutarSistema) {
            // Validar si la tabla está vacía para forzar el Administrador
            if (tablaUsuarios.obtenerTodos().isEmpty()) {
                registrarPrimerAdmin();
            } else {
                // Si ya hay usuarios, mostramos el menú de acceso general
                System.out.println("\n=== MENÚ DE INICIO ===");
                System.out.println("1. Iniciar Sesión");
                System.out.println("2. Registrase");
                System.out.println("2. Salir del programa");
                System.out.print("Seleccione una opción: ");
                
                int opcion = leer.nextInt();
                leer.nextLine(); // Limpiar el buffer de entrada

                switch (opcion) {
                    case 1:
                        manejarFlujoLogin(); // Va al login y luego a los submenús
                        break;
                    case 2:
                        System.out.println("¡Gracias por utilizar Sistema Delivery! Saliendo...");
                        ejecutarSistema = false;
                        break;
                    default:
                        System.out.println("Opción no válida. Intentelo de nuevo.");
                }
            }
        }
    }
    
    // Metodo para registrar usuarios y validaciones correspondientes.
    public void registrarUsuario(Usuarios_Proyecto nuevo) { 
        // Verificacion si ya existe antes de registrarlo
        if (tablaUsuarios.buscarHash(nuevo.getCorreo()) != null) {
            System.out.println("Error: El correo " + nuevo.getCorreo() + " ya está registrado.");
        } else {
            tablaUsuarios.insertarHash(nuevo.getCorreo(), nuevo);
            System.out.println("Usuario registrado con éxito.");
        }
    }
    
    // valida los datos ingresados en el login.
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
