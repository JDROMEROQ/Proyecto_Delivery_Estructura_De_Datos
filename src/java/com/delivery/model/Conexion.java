/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;

/**
 *
 * @author dr405
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // 'xe' o 'orcl'
    private static final String USER = "PROYECTO_DELIVERY"; 
    private static final String PASS = "ProyectoDelivery";

    public static Connection conectar() {
        Connection cn = null;
        try {
            // Cargar el driver de la carpeta lib
            Class.forName("oracle.jdbc.driver.OracleDriver");
            cn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión exitosa a Oracle");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return cn;
    }
    
    // Método principal rápido para probar si funciona
    public static void main(String[] args) {
        conectar();
    }
}
