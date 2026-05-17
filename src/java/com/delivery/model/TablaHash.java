/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;
import java.util.LinkedList; // libreria para evitar colisiones de en la tabla hash
/**
 *
 * @author dr405
 */
public class TablaHash<T> {
    // Arreglo de listas enlazadas 
    private LinkedList<Nodo<T>>[] tabla;
    private int capacidad;      // Tamaño del arreglo
    private int numElementos;   // Cuántos objetos guardados hay
    private final double factorDeCarga = 0.75; // Umbral para crecer

    // Nodo Genérico
    private static class Nodo<T> {
        String llave; // Guardado de CORREO
        T valor;      // Guardado del objeto (Cliente, Repartidor, Administrador.)

        Nodo(String llave, T valor) {
            this.llave = llave;
            this.valor = valor;
        }
    }

    // Constructor inicial
    public TablaHash(int capacidadInicial) {
        this.capacidad = capacidadInicial;
        this.numElementos = 0;
        // Creamos el arreglo de listas
        this.tabla = new LinkedList[capacidad];
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new LinkedList<>();
        }
    }
    
    // Metodo para obtener el indice que tendra dentro del arreglo
    private int generarHash(String llave) {
        // Obtenemos el código numérico 
        int hash = llave.hashCode();

        // Aplicamos el módulo para que encaje en el arreglo
        int indice = hash % capacidad;

        // Si el resultado es negativo, se ajustara sumando la capacidad
        if (indice < 0) {
            indice += capacidad;
        }
        return indice;
    }
    
    private void reOrdenarHash() {
        LinkedList<Nodo<T>>[] tablaVieja = tabla;

        // 1. Aumentamos la capacidad
        capacidad *= 2; 
        numElementos = 0;

        // 2. Creamos la nueva tabla con el nuevo tamaño
        tabla = new LinkedList[capacidad];
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new LinkedList<>();
        }

        // 3. Volvemos a insertar todo lo que había antes
        for (LinkedList<Nodo<T>> lista : tablaVieja) {
            for (Nodo<T> nodo : lista) {
                insertarHash(nodo.llave, nodo.valor); // Re-insertamos con la nueva capacidad
            }
        }
    }
    
    public void insertarHash(String llave, T valor) {
        int indice = generarHash(llave);
        LinkedList<Nodo<T>> lista = tabla[indice];

        // Buscamos si el correo ya está en la lista de esta casilla
        for (Nodo<T> nodo : lista) {
            if (nodo.llave.equals(llave)) {
                nodo.valor = valor; // Actualizamos si ya existe
                return;
            }
        }

        // Si llegamos aquí, es porque el correo es nuevo
        lista.add(new Nodo<>(llave, valor));
        numElementos++;

        // ¿Debemos crecer?
        if ((double) numElementos / capacidad >= factorDeCarga) {
            reOrdenarHash(); 
        }
    }
    
    public T buscarHash(String llave) {
        // Verificación si la llave es nula
        if (llave == null) {
            return null; 
        }
        
        int indice = generarHash(llave);
        LinkedList<Nodo<T>> lista = tabla[indice];

        // Recorremos la cadena en esa posición
        for (Nodo<T> nodo : lista) {
            if (nodo.llave.equals(llave)) {
                return nodo.valor; //Devuelve el objeto
            }
        }

        // Si la llave no existe en la tabla devuelve null.
        return null; 
    }
    
    // Metodo para eliminar de la tabla Hash un elemento
    public boolean eliminarHash(String llave) {
        int indice = generarHash(llave);
        LinkedList<Nodo<T>> lista = tabla[indice];

        for (Nodo<T> nodo : lista) {
            if (nodo.llave.equals(llave)) {
                lista.remove(nodo); // Quitamos el nodo de la lista
                numElementos--;     // Se actualizara el contador
                return true;        // Confirmacion que se elimino
            }
        }
        return false; // No se encontró nada para eliminar
    }
    
    public LinkedList<T> obtenerTodos() {
        LinkedList<T> todosLosElementos = new LinkedList<>();

        // Recorremos el ARREGLO INTERNO de la TablaHash (cámbialo por cómo se llame tu arreglo ahí)
        for (LinkedList<Nodo<T>> lista : tabla) { 
            if (lista != null) {
                for (Nodo<T> nodo : lista) {
                    todosLosElementos.add(nodo.valor);
                }
            }
        }
        return todosLosElementos;
    }
}
