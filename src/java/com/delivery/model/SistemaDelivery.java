/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author dr405
 */
public class SistemaDelivery {
    private TablaHash<Usuarios_Proyecto> tablaUsuarios;
    private ArbolMulticamino<Usuarios_Proyecto> arbolUsuarios;
    
    public SistemaDelivery() { // construtor.
        this.tablaUsuarios = new TablaHash<>(11); // Tamaño inicial
        this.arbolUsuarios = new ArbolMulticamino<>();
        cargarUsuariosDesdeBD();
    }
    
    // metodo para sincronizar el arbol multicamino y las tabla hash
    private void sincronizarEnEstructuras(Usuarios_Proyecto usuario) {
        // Insertamos en la Tabla Hash principal con toda su información
        tablaUsuarios.insertarHash(usuario.getCorreo(), usuario);
        
        // Al insertar en el árbol multicamino, obtenemos el nodo raíz del árbol
        NodoMulticamino<Usuarios_Proyecto> raiz = arbolUsuarios.getRaiz();
        
        // Si el nodo raíz no tiene tablas hash inicializadas, le agregamos una
        if (raiz.getTablas().isEmpty()) {
            raiz.getTablas().add(tablaUsuarios);
        }
    }
    
    // metodo inicial del programa.
    public boolean registrarPrimerAdmin(String nombre, String correo, String clave) {
        // Si por la tabla ya tiene usuarios, bloqueamos el flujo
        if (!tablaUsuarios.obtenerTodos().isEmpty()) {
            return false;
        }

        // Comando SQL para que Oracle ponga el ID
        String sql = "INSERT INTO USUARIOS (CORREO, CLAVE, NOMBRE, ROL) VALUES (?, ?, ?, 'ADMIN')";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, correo);
            ps.setString(2, clave);
            ps.setString(3, nombre);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                // Recuperamos el ID que generó Oracle
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGeneradoPorOracle = generatedKeys.getInt(1);

                        // Objeto creado con el ID 
                        Usuarios_Proyecto primerAdmin = new Usuarios_Proyecto(idGeneradoPorOracle, nombre, correo, clave, "ADMIN");
                        // Sincronizamos en la Tabla Hash y Árbol simultáneamente
                        sincronizarEnEstructuras(primerAdmin);
                        return true; 
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al registrar el Admin Inicial en la BD: " + e.getMessage());
        }
        return false;
    }
    
    // Metodo para registrar usuarios y validaciones correspondientes..
    public boolean registrarUsuario(Usuarios_Proyecto nuevo) { 
        // Evitamos correos duplicados 
        if (tablaUsuarios.buscarHash(nuevo.getCorreo()) != null) {
            return false; 
        }

        // Conexion con Oracle SQL para insertar permanentemente
        String sql = "INSERT INTO USUARIOS (CORREO, CLAVE, NOMBRE, ROL) VALUES (?, ?, ?, ?)";

        // Uso de try-with-resources para asegurar que las conexiones se cierren solas
        try (Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nuevo.getCorreo());
            ps.setString(2, nuevo.getClave());
            ps.setString(3, nuevo.getNombre());
            ps.setString(4, nuevo.getRol());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                // ID autoincremental que generó Oracle
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGeneradoPorOracle = generatedKeys.getInt(1);

                        // Asignamos al objeto el ID de la base de datos
                        nuevo.setIdUsuario(idGeneradoPorOracle);

                        // Sincronizamos en la Tabla Hash y Árbol simultáneamente
                        sincronizarEnEstructuras(nuevo);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al registrar en la Base de Datos: " + e.getMessage());
        }

        return false;
    }
    
    // valida los datos ingresados en el login.
    public Usuarios_Proyecto realizarLogin(String correoIngresado, String claveIngresada) {
        // Buscamos al usuario en la Tabla Hash
        Usuarios_Proyecto user = tablaUsuarios.buscarHash(correoIngresado);

        // Si el usuario no existe, devolvemos null
        if (user == null) {
            return null; 
        }

        // Si la contraseña coincide, devolvemos el objeto del usuario
        if (user.getClave().equals(claveIngresada)) {
            return user; 
        } else {
            // Si la contraseña no coincide, devolvemos null
            return null;
        }
    }
    
    //Metodo para listar usuarios por clasificacion (cliente, repartidor, administrador)
    public LinkedList<Usuarios_Proyecto> listarUsuariosPorRol(String rolBuscado) {
        LinkedList<Usuarios_Proyecto> todos = tablaUsuarios.obtenerTodos();
        LinkedList<Usuarios_Proyecto> listaFiltrada = new LinkedList<>();

        for (Usuarios_Proyecto u : todos) {
            // Si el rol coincide, lo agregamos
            if (u.getRol().equalsIgnoreCase(rolBuscado)) {
                listaFiltrada.add(u);
            }
        }

<<<<<<< HEAD
        // 4. Mostrando en bloques de forma ordenada 
        System.out.println("       REPORTE GLOBAL DE USUARIOS        ");

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
            System.out.println("   (No hay clientes registrados)");
        } else {
            for (Usuarios_Proyecto u : clientes) {
                System.out.println("   • ID: " + u.getIdUsuario() + " | Nombre: " + u.getNombre() + " | Correo: " + u.getCorreo());
            }
        }

        System.out.println("\n [REPARTIDORES] Ocupación: " + repartidores.size());
        if (repartidores.isEmpty()) {
            System.out.println("   (No hay repartidores registrados)");
        } else {
            for (Usuarios_Proyecto u : repartidores) {
                System.out.println("   • ID: " + u.getIdUsuario() + " | Nombre: " + u.getNombre() + " | Correo: " + u.getCorreo());
            }
        }
        System.out.println("=========================================");
=======
        return listaFiltrada; // Retornamos la lista 
>>>>>>> 59aee9bb3db55fda73e18dd551351076e12863c0
    }
    
    // metodo para obtener datos guardados en sql
    // Carga inicial de datos al levantar el Servidor Web
    public void cargarUsuariosDesdeBD() {
        this.tablaUsuarios = new TablaHash<>(11);
        this.arbolUsuarios = new ArbolMulticamino<>();
        
        String sql = "SELECT ID_USUARIO, NOMBRE, CORREO, CLAVE, ROL FROM USUARIOS";
        
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Usuarios_Proyecto u = new Usuarios_Proyecto(
                    rs.getInt("ID_USUARIO"),
                    rs.getString("NOMBRE"),
                    rs.getString("CORREO"),
                    rs.getString("CLAVE"),
                    rs.getString("ROL")
                );
                // Inyectamos el usuario recuperado en ambas estructuras de memoria
                sincronizarEnEstructuras(u);
            }
            // Estructuras (Tabla Hash y Árbol) sincronizadas desde Oracle SQL
        } catch (Exception e) {
            System.out.println("Error al cargar usuarios desde la BD: " + e.getMessage());
        }
    }
    
<<<<<<< HEAD
    private void mostrarMenuCliente(Usuarios_Proyecto cliente) {
        System.out.println("\nPanel de Cliente: " + cliente.getNombre());
        // Lógica de pedidos
    }

    private void mostrarMenuRepartidor(Usuarios_Proyecto repartidor) {
        System.out.println("\nPanel de Repartidor: " + repartidor.getNombre());
        // Lógica de entregas
=======
    // Getter para que se recorrer el árbol
    public ArbolMulticamino<Usuarios_Proyecto> getArbolUsuarios() {
        return arbolUsuarios;
>>>>>>> 59aee9bb3db55fda73e18dd551351076e12863c0
    }
}
