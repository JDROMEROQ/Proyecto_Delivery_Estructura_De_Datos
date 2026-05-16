/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;

/**
 *
 * @author dr405
 */
public class Main {
    
    // El punto de entrada obligatorio para que Java pueda darle a "Play"
    public static void main(String[] args) {
        // 1. Instanciamos tu clase cerebro
        SistemaDelivery sistema = new SistemaDelivery();
        
        // 2. Encendemos el bucle que creamos para los menús
        sistema.ejecutarMenuPrincipal();
    }
}
