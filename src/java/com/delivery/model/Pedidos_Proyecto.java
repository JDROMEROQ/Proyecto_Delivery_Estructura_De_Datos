/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;

/**
 *
 * @author dr405
 */
public class Pedidos_Proyecto {
    private int idPedido;
    private int idCliente;
    private Integer idRepartidor; // Usamos Integer por si inicia en null
    private String descripcion;
    private String direccionEntrega;
    private String estado; 
    private int urgencia;  

    // 1. Constructor 
    public Pedidos_Proyecto() {
    }

    // 2. Constructor completo para cuando carguemos datos desde Oracle
    public Pedidos_Proyecto(int idPedido, int idCliente, Integer idRepartidor, String descripcion, String direccionEntrega, String estado, int urgencia) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idRepartidor = idRepartidor;
        this.descripcion = descripcion;
        this.direccionEntrega = direccionEntrega;
        this.estado = estado;
        this.urgencia = urgencia;
    }
    
    // Getters y Setters
    public int getIdPedido() { 
        return idPedido; 
    }
    
    public void setIdPedido(int idPedido) { 
        this.idPedido = idPedido; 
    }

    public int getIdCliente() { 
        return idCliente; 
    }
    
    public void setIdCliente(int idCliente) { 
        this.idCliente = idCliente; 
    }

    public Integer getIdRepartidor() { 
        return idRepartidor; 
    }
    
    public void setIdRepartidor(Integer idRepartidor) {
        this.idRepartidor = idRepartidor; 
    }

    public String getDescripcion() { 
        return descripcion; 
    }
    
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }

    public String getDireccionEntrega() { 
        return direccionEntrega; 
    }
    
    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getEstado() {
        return estado; 
    }
    
    public void setEstado(String estado) { 
        this.estado = estado; 
    }

    public int getUrgencia() { 
        return urgencia; 
    }
    
    public void setUrgencia(int urgencia) { 
        this.urgencia = urgencia;
    }
}
