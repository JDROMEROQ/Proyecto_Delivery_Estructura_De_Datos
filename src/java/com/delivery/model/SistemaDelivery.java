/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;
import java.util.LinkedList;
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
        
        // Lo guardamos en la Tabla Hash
        tablaUsuarios.insertarHash(correo, primerAdmin);
        System.out.println("Registro inicial completado con éxito. Ahora puede iniciar sesión.");
    }
    
    // metodo para entrar al menu principal luego de haber creado un admin inicial.
    public void ejecutarMenuPrincipal() {
        boolean ejecutarSistema = true;
        System.out.println("====== BIENVENIDO AL SISTEMA DE DELIVERY ======");

        while (ejecutarSistema) {
            // Validar si la tabla está vacía para forzar el Administrador Inicial
            if (tablaUsuarios.obtenerTodos().isEmpty()) {
                registrarPrimerAdmin();
            } else {
                System.out.println("\n=== MENÚ DE INICIO ===");
                System.out.println("1. Iniciar Sesión");
                System.out.println("2. Registrarse");
                System.out.println("3. Salir del programa");
                System.out.print("Seleccione una opción: ");
                
                int opcion = leer.nextInt();
                leer.nextLine(); // Limpiar el buffer de entrada

                switch (opcion) {
                    case 1:
                        manejarFlujoLogin(); 
                        break;
                    case 2:
                        manejarFlujoRegistro(); // ➕ Nueva sección interactiva para registrar
                        break;
                    case 3:
                        System.out.println("¡Gracias por utilizar Sistema Delivery! Saliendo...");
                        ejecutarSistema = false;
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            }
        }
    }
    
    // metodo para el manejo de las opciones del inicio de sesion
    private void manejarFlujoLogin() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Ingrese su correo electrónico: ");
        String correo = leer.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String clave = leer.nextLine();

        Usuarios_Proyecto usuarioLogueado = realizarLogin(correo, clave);

        if (usuarioLogueado != null) {
            String rol = usuarioLogueado.getRol().toUpperCase();

            switch (rol) {
                case "ADMIN":
                    mostrarMenuAdmin(usuarioLogueado);
                    break;
                case "CLIENTE":
                    mostrarMenuCliente(usuarioLogueado);
                    break;
                case "REPARTIDOR":
                    mostrarMenuRepartidor(usuarioLogueado);
                    break;
                default:
                    System.out.println("⚠️ Error crítico: El usuario tiene un rol no reconocido.");
                    break;
            }
        }
    
    }
    
    // Interfaz de consola para capturar los datos de un nuevo registro general
    private void manejarFlujoRegistro() {
        System.out.println("\n--- REGISTRO DE NUEVO USUARIO ---");
        System.out.print("Nombre completo: ");
        String nombre = leer.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = leer.nextLine();
        System.out.print("Contraseña: ");
        String clave = leer.nextLine();
        
        System.out.println("Seleccione el rol:");
        System.out.println("1. Cliente");
        System.out.println("2. Repartidor");
        System.out.print("Opción: ");
        int opRol = leer.nextInt();
        leer.nextLine(); // Limpiar buffer
        
        String rolSelected = (opRol == 2) ? "REPARTIDOR" : "CLIENTE";
        int idActual = contadorIds++;
        
        Usuarios_Proyecto nuevoUsuario = new Usuarios_Proyecto(idActual, nombre, correo, clave, rolSelected);
        registrarUsuario(nuevoUsuario); // Llama a la validación lógica
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
    
    // MENUS POR ROL
    private void mostrarMenuAdmin(Usuarios_Proyecto admin) {
        boolean enPanelAdmin = true;

        while (enPanelAdmin) {
            System.out.println("--- PANEL DE ADMINISTRADOR ---");
            System.out.println("Bienvenido: " + admin.getNombre());
            System.out.println("Que desea realizar?");
            System.out.println("1. Registrar nuevo usuario (Admin, Cliente, Repartidor)");
            System.out.println("2. Buscar usuario por correo");
            System.out.println("3. Eliminar usuario del sistema");
            System.out.println("4. Listar y clasificar usuarios por Rol");
            System.out.println("5. Cerrar Sesión (Regresar al Menú Inicial)");
            System.out.print("Seleccione una opción: ");

            int opcion = leer.nextInt();
            leer.nextLine();

            switch (opcion) {
                case 1:
                    // Utilizamos el flujo de registro ya creado
                    manejarRegistroDesdeAdmin();
                    break;
                case 2:
                    manejarBusquedaAdmin();
                    break;
                case 3:
                    manejarEliminacionAdmin();
                    break;
                case 4:
                    // Metodo para listar a todos los usuarios
                    listarUsuariosPorRol();
                    break;
                case 5:
                    System.out.println("Cerrando sesión de administrador...");
                    enPanelAdmin = false; // cierre del bucle y regreso al menú principal
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    
    //Metodo para listar usuarios por clasificacion (cliente, repartidor, administrador)
    public void listarUsuariosPorRol() {
        // Le pedimos a la tabla la lista de todos los usuarios
        LinkedList<Usuarios_Proyecto> todos = tablaUsuarios.obtenerTodos();

        // Validación por si la tabla esta vacia
        if (todos.isEmpty()) {
            System.out.println("No hay usuarios registrados en el sistema.");
            return;
        }

        // Creacion de tres listas específicas para clasificar
        LinkedList<Usuarios_Proyecto> admins = new LinkedList<>();
        LinkedList<Usuarios_Proyecto> clientes = new LinkedList<>();
        LinkedList<Usuarios_Proyecto> repartidores = new LinkedList<>();

        // Recorrido de la lista y clasificacion según el rol
        for (Usuarios_Proyecto u : todos) {
            String rol = u.getRol().toUpperCase();
            if (rol.equals("ADMIN")) {
                admins.add(u);
            } else if (rol.equals("CLIENTE")) {
                clientes.add(u);
            } else if (rol.equals("REPARTIDOR")) {
                repartidores.add(u);
            }
        }

        // Mostrando de forma ordenada los usuarios.
        System.out.println(" --- REPORTE DE USUARIOS ---");
        System.out.println("ADMINISTRADORES] Ocupación: " + admins.size());
        if (admins.isEmpty()) {
            System.out.println("(No hay administradores adicionales)");
        } else {
            for (Usuarios_Proyecto u : admins) {
                System.out.println("| ID: " + u.getIdUsuario() + " | Nombre: " + u.getNombre() + " | Correo: " + u.getCorreo());
            }
        }

        System.out.println("[CLIENTES] Ocupación: " + clientes.size());
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
        } else {
            for (Usuarios_Proyecto u : clientes) {
                System.out.println("   • ID: " + u.getIdUsuario() + " | Nombre: " + u.getNombre() + " | Correo: " + u.getCorreo());
            }
        }

        System.out.println("[REPARTIDORES] Ocupación: " + repartidores.size());
        if (repartidores.isEmpty()) {
            System.out.println("No hay repartidores registrados");
        } else {
            for (Usuarios_Proyecto u : repartidores) {
                System.out.println("   • ID: " + u.getIdUsuario() + " | Nombre: " + u.getNombre() + " | Correo: " + u.getCorreo());
            }
        }
    }

    private void manejarBusquedaAdmin() {
        System.out.println("--- BUSCAR USUARIO ---");
        System.out.print("Ingrese el correo electrónico del usuario a buscar: ");
        String correoBuscar = leer.nextLine();

        // Consultamos directamente a nuestra Tabla Hash
        Usuarios_Proyecto user = tablaUsuarios.buscarHash(correoBuscar);

        // Resultado de la búsqueda
        if (user != null) {
            System.out.println("Usuario Encontrado");
            System.out.println("• ID Usuario:  " + user.getIdUsuario());
            System.out.println("• Nombre:      " + user.getNombre());
            System.out.println("• Correo:      " + user.getCorreo());
            System.out.println("• Rol Asignado: " + user.getRol());
        } else {
            System.out.println("No se encontró ningún usuario registrado con el correo: " + correoBuscar);
        }
    }
    
    private void mostrarMenuCliente(Usuarios_Proyecto cliente) {
        System.out.println("\n🛵 Panel de Cliente: " + cliente.getNombre());
        // Lógica de pedidos
    }

    private void mostrarMenuRepartidor(Usuarios_Proyecto repartidor) {
        System.out.println("\n📦 Panel de Repartidor: " + repartidor.getNombre());
        // Lógica de entregas
    }
}
