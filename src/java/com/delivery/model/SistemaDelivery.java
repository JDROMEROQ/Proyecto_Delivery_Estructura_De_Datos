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
    
    public SistemaDelivery() { // construtor.
        this.tablaUsuarios = new TablaHash<>(11); // Tamaño inicial
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

                        // Guardado en la tabla hash
                        tablaUsuarios.insertarHash(correo, primerAdmin);
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

                        // Objeto guardado y completamente sincronizado en la TablaHash
                        tablaUsuarios.insertarHash(nuevo.getCorreo(), nuevo);
                        return true; // Éxito total en RAM y Disco
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

        return listaFiltrada; // Retornamos la lista 
    }
}
