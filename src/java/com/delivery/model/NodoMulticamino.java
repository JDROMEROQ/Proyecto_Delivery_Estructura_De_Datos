/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;
import java.util.ArrayList;

// Nodo del Árbol Multicamino
class NodoMulticamino<T> {

    // Lista de tablas hash almacenadas en el nodo
    private ArrayList<TablaHash<T>> tablas;

    // Hijos del nodo
    private ArrayList<NodoMulticamino<T>> hijos;

    // Constructor
    public NodoMulticamino() {
        tablas = new ArrayList<>();
        hijos = new ArrayList<>();
    }

    // Agregar una tabla hash al nodo
    public void agregarTabla(TablaHash<T> tabla) {
        tablas.add(tabla);
    }

    // Agregar hijo
    public void agregarHijo(NodoMulticamino<T> hijo) {
        hijos.add(hijo);
    }

    // Obtener tablas
    public ArrayList<TablaHash<T>> getTablas() {
        return tablas;
    }

    // Obtener hijos
    public ArrayList<NodoMulticamino<T>> getHijos() {
        return hijos;
    }
}