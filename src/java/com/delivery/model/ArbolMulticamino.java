/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;

class ArbolMulticamino<T> {

    private NodoMulticamino<T> raiz;

    // Constructor
    public ArbolMulticamino() {
        raiz = new NodoMulticamino<>();
    }

    // Obtener raíz
    public NodoMulticamino<T> getRaiz() {
        return raiz;
    }

    // Recorrido del árbol
    public void recorrer(NodoMulticamino<T> nodo) {

        if (nodo == null) {
            return;
        }

        System.out.println("Nodo encontrado:");

        // Mostrar tablas hash
        for (TablaHash<T> tabla : nodo.getTablas()) {

            for (T elemento : tabla.obtenerTodos()) {
                System.out.println(elemento);
            }
        }

        // Recorrer hijos
        for (NodoMulticamino<T> hijo : nodo.getHijos()) {
            recorrer(hijo);
        }
    }
}