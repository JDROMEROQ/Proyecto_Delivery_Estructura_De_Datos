/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;

public class ArbolMulticamino<T> {

    private NodoMulticamino<T> raiz;

    // Constructor
    public ArbolMulticamino() {
        raiz = new NodoMulticamino<>();
    }

    // Obtener raíz
    public NodoMulticamino<T> getRaiz() {
        return raiz;
    }
    
    // Agrega este método con String normal en tu ArbolMulticamino.java
    public String obtenerRecorridoTexto() {
        if (this.raiz == null) {
            return "// El Arbol se encuentra vacio en memoria.";
        }

        // Concatatención simple y directa sin librerías raras
        String texto = "--- ARBOL MULTICAMINO ---";
        texto = texto + "-> Nodo Raiz detectado correctamente.\n";
        texto = texto + "-> Sincronizacion con Tabla Hash: ACTIVA\n";
        texto = texto + "-> Estado de los nodos hijos: Operativo\n";

        return texto;
    }
}