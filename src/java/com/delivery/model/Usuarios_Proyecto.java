/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;

/**
 *
 * @author dr405
 */
public class Usuarios_Proyecto {

    // Atributos privados 
    private int idUsuario; 
    private String nombre; 
    private String correo;
    private String clave;
    private String rol;

    // 1. Constructor vacío (para librerías de Java)
    public Usuarios_Proyecto() {
    }

    // 2. Constructor con todos los parámetros (útil al traer datos de la Base de Datos)
    public Usuarios_Proyecto(int idUsuario, String nombre, String correo, String clave, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
    }

    // 3. Getters y Setters 
    public int getIdUsuario() {
        return idUsuario; 
    }
    
    public void setIdUsuario(int idUsuario) { 
        this.idUsuario = idUsuario; 
    }

    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getCorreo() { 
        return correo; 
    }
    
    public void setCorreo(String correo) { 
        this.correo = correo; 
    }

    public String getClave() {
        return clave; 
    }

    public void setClave(String codigo) { 
        this.clave = codigo; 
    }

    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) { 
        this.rol = rol; 
    }
}
