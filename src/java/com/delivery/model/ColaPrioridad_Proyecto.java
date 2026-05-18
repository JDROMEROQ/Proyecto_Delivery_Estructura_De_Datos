/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.delivery.model;
import java.util.ArrayList;
/**
 *
 * @author dr405
 */
public class ColaPrioridad_Proyecto {
    private ArrayList<Pedidos_Proyecto> heap;

    public ColaPrioridad_Proyecto() {
        this.heap = new ArrayList<>();
    }
    
    // intercambia las urgencias.
    private void intercambiar(int i, int j) {
        Pedidos_Proyecto temporal = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temporal);
    }
    
    // metodo que cambia la urgencia del indice padre para arriba si es mayor
    private void cambioArriba(int indice) {
        while (indice > 0) {
            int indicePadre = (indice - 1) / 2;
            
            // Si el padre tiene urgencia mayor sube.
            if (heap.get(indice).getUrgencia() > heap.get(indicePadre).getUrgencia()) {
                intercambiar(indice, indicePadre);
                indice = indicePadre;
            } else {
                break;
            }
        }
    }

    // metodo que compara la urgencia de los hijos y los coloca en una urgencia mayor.
    private void cambioAbajo(int indice) {
        int tamano = heap.size();
        while (indice < tamano) {
            int izquierdo = 2 * indice + 1;
            int derecho = 2 * indice + 2;
            int mayor = indice;

            // Compara si el hijo izquierdo tiene más urgencia
            if (izquierdo < tamano && heap.get(izquierdo).getUrgencia() > heap.get(mayor).getUrgencia()) {
                mayor = izquierdo;
            }
            // Compara si el hijo derecho tiene más urgencia
            if (derecho < tamano && heap.get(derecho).getUrgencia() > heap.get(mayor).getUrgencia()) {
                mayor = derecho;
            }

            if (mayor != indice) {
                intercambiar(indice, mayor);
                indice = mayor;
            } else {
                break;
            }
        }
    }
    
    // insercion de prioridad
    public void insertar(Pedidos_Proyecto pedido) {
        heap.add(pedido);
        cambioArriba(heap.size() - 1);
    }
    
    
    public Pedidos_Proyecto extraerMaximo() {
        if (heap.isEmpty()) return null;
        
        Pedidos_Proyecto maximo = heap.get(0);
        Pedidos_Proyecto ultimo = heap.remove(heap.size() - 1);
        
        if (!heap.isEmpty()) {
            heap.set(0, ultimo);
            cambioAbajo(0);
        }
        return maximo;
    }

    public boolean estaVacia() {
        return heap.isEmpty();
    }
    
}
