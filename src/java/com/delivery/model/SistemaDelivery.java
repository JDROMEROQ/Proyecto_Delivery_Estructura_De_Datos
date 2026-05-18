package com.delivery.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class SistemaDelivery {
    private TablaHash<Usuarios_Proyecto> tablaUsuarios;
    private ArbolMulticamino<Usuarios_Proyecto> arbolUsuarios;
    private ColaPrioridad_Proyecto colaPedidos = new ColaPrioridad_Proyecto();
    
    public SistemaDelivery() { // constructor.
        this.tablaUsuarios = new TablaHash<>(11);
        this.arbolUsuarios = new ArbolMulticamino<>();
        cargarUsuarios();
        cargarPedidos(); 
    }
    
    // Metodo para sincronizar el arbol multicamino y la tabla hash
    private void sincronizarEnEstructuras(Usuarios_Proyecto usuario) {
        // Insertamos en la Tabla Hash principal con toda su información
        tablaUsuarios.insertarHash(usuario.getCorreo(), usuario);
        
        // Al insertar en el árbol multicamino, obtenemos el nodo raíz del árbol
        NodoMulticamino<Usuarios_Proyecto> raiz = arbolUsuarios.getRaiz();
        
        // Si el nodo raíz no tiene tablas hash inicializadas, le agregamos una
        if (raiz.getTablas().isEmpty()) {
            raiz.getTablas().add(tablaUsuarios);
        }
    }
    
    // Metodo inicial del programa.
    public boolean registrarPrimerAdmin(String nombre, String correo, String clave) {
        if (!tablaUsuarios.obtenerTodos().isEmpty()) {
            return false;
        }

        String sql = "INSERT INTO USUARIOS_PROYECTO (ID_USUARIO, CORREO, CLAVE, NOMBRE, ROL) VALUES (USUARIOS_SEQ.NEXTVAL, ?, ?, ?, 'ADMINISTRADOR')";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, clave);
            ps.setString(3, nombre);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                // Obtenemos el ID que se generó
                String sqlId = "SELECT USUARIOS_SEQ.CURRVAL FROM DUAL";
                try (PreparedStatement ps2 = cn.prepareStatement(sqlId);
                     ResultSet rs = ps2.executeQuery()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        Usuarios_Proyecto primerAdmin = new Usuarios_Proyecto(idGenerado, nombre, correo, clave, "ADMIN");
                        sincronizarEnEstructuras(primerAdmin);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al registrar Admin: " + e.getMessage());
        }
        return false;
    }
    
    // Metodo para registrar usuarios y validaciones correspondientes..
   public boolean registrarUsuario(Usuarios_Proyecto nuevo) {
        if (tablaUsuarios.buscarHash(nuevo.getCorreo()) != null) {
            return false;
        }

        String sql = "INSERT INTO USUARIOS_PROYECTO (ID_USUARIO, CORREO, CLAVE, NOMBRE, ROL) VALUES (USUARIOS_SEQ.NEXTVAL, ?, ?, ?, ?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, nuevo.getCorreo());
            ps.setString(2, nuevo.getClave());
            ps.setString(3, nuevo.getNombre());
            ps.setString(4, nuevo.getRol());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                String sqlId = "SELECT USUARIOS_SEQ.CURRVAL FROM DUAL";
                try (PreparedStatement ps2 = cn.prepareStatement(sqlId);
                     ResultSet rs = ps2.executeQuery()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        nuevo.setIdUsuario(idGenerado);
                        sincronizarEnEstructuras(nuevo);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
        return false;
    }
    
    // Valida los datos ingresados en el login.
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
    
    // Metodo para listar usuarios por clasificacion (cliente, repartidor, administrador)
    public LinkedList<Usuarios_Proyecto> listarUsuariosPorRol(String rolBuscado) {
        LinkedList<Usuarios_Proyecto> todos = tablaUsuarios.obtenerTodos();
        LinkedList<Usuarios_Proyecto> listaFiltrada = new LinkedList<>();

        for (Usuarios_Proyecto u : todos) {
            // Si el rol coincide, lo agregamos
            if (u.getRol().equalsIgnoreCase(rolBuscado)) {
                listaFiltrada.add(u);
            }
        }

        return listaFiltrada; // Conservamos el retorno
    }
    
    // Carga inicial de datos al levantar el Servidor Web
    public void cargarUsuarios() {
        this.tablaUsuarios = new TablaHash<>(11);
        this.arbolUsuarios = new ArbolMulticamino<>();
        
        String sql = "SELECT ID_USUARIO, NOMBRE, CORREO, CLAVE, ROL FROM USUARIOS_PROYECTO";
        
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Usuarios_Proyecto u = new Usuarios_Proyecto(
                    rs.getInt("ID_USUARIO"),
                    rs.getString("NOMBRE"),
                    rs.getString("CORREO"),
                    rs.getString("CLAVE"),
                    rs.getString("ROL")
                );
                // Inyectamos el usuario recuperado en ambas estructuras de memoria
                sincronizarEnEstructuras(u);
            }
            // Estructuras (Tabla Hash y Árbol) sincronizadas desde Oracle SQL
        } catch (Exception e) {
            System.out.println("Error al cargar usuarios desde la BD: " + e.getMessage());
        }
    }
    
   
    
    // metodo para el pedido.
    public boolean registrarPedido(Pedidos_Proyecto nuevoPedido) {
        String sql = "INSERT INTO PEDIDOS_PROYECTO (ID_PEDIDO, ID_CLIENTE, DESCRIPCION, DIRECCION_ENTREGA, URGENCIA) VALUES (PEDIDOS_SEQ.NEXTVAL, ?, ?, ?, ?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, nuevoPedido.getIdCliente());
            ps.setString(2, nuevoPedido.getDescripcion());
            ps.setString(3, nuevoPedido.getDireccionEntrega());
            ps.setInt(4, nuevoPedido.getUrgencia());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                String sqlId = "SELECT PEDIDOS_SEQ.CURRVAL FROM DUAL";
                try (PreparedStatement ps2 = cn.prepareStatement(sqlId);
                     ResultSet rs = ps2.executeQuery()) {
                    if (rs.next()) {
                        nuevoPedido.setIdPedido(rs.getInt(1));
                        nuevoPedido.setEstado("PENDIENTE");
                        colaPedidos.insertar(nuevoPedido);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al insertar pedido: " + e.getMessage());
        }
        return false;
    }

    // Carga los pedidos ya registrados en oracle
    public void cargarPedidos() {
        this.colaPedidos = new ColaPrioridad_Proyecto();
        String sql = "SELECT ID_PEDIDO, ID_CLIENTE, ID_REPARTIDOR, DESCRIPCION, DIRECCION_ENTREGA, ESTADO, URGENCIA FROM PEDIDOS_PROYECTO WHERE ESTADO IN ('PENDIENTE', 'ASIGNADO')";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Pedidos_Proyecto p = new Pedidos_Proyecto(
                    rs.getInt("ID_PEDIDO"),
                    rs.getInt("ID_CLIENTE"),
                    rs.getObject("ID_REPARTIDOR") != null ? rs.getInt("ID_REPARTIDOR") : null,
                    rs.getString("DESCRIPCION"),
                    rs.getString("DIRECCION_ENTREGA"),
                    rs.getString("ESTADO"),
                    rs.getInt("URGENCIA")
                );
                colaPedidos.insertar(p);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar pedidos: " + e.getMessage());
        }
    }
    
    // Eliminar usuario de BD y estructuras
    public boolean eliminarUsuario(String correo) {
        Usuarios_Proyecto u = tablaUsuarios.buscarHash(correo);
        if (u == null) return false;

        String sql = "DELETE FROM USUARIOS_PROYECTO WHERE CORREO = ?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, correo);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                tablaUsuarios.eliminarHash(correo);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
        return false;
    }
    
    // Obtener todos los pedidos (no solo pendientes)
    public java.util.LinkedList<Pedidos_Proyecto> obtenerTodosPedidos() {
        java.util.LinkedList<Pedidos_Proyecto> lista = new java.util.LinkedList<>();
        String sql = "SELECT ID_PEDIDO, ID_CLIENTE, ID_REPARTIDOR, DESCRIPCION, DIRECCION_ENTREGA, ESTADO, URGENCIA FROM PEDIDOS_PROYECTO ORDER BY URGENCIA DESC";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Pedidos_Proyecto(
                    rs.getInt("ID_PEDIDO"),
                    rs.getInt("ID_CLIENTE"),
                    rs.getObject("ID_REPARTIDOR") != null ? rs.getInt("ID_REPARTIDOR") : null,
                    rs.getString("DESCRIPCION"),
                    rs.getString("DIRECCION_ENTREGA"),
                    rs.getString("ESTADO"),
                    rs.getInt("URGENCIA")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al obtener pedidos: " + e.getMessage());
        }
        return lista;
    }
    
    // Asignar repartidor a pedido
    public boolean asignarRepartidor(int idPedido, int idRepartidor) {
        String sql = "UPDATE PEDIDOS_PROYECTO SET ID_REPARTIDOR = ?, ESTADO = 'ASIGNADO' WHERE ID_PEDIDO = ?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idRepartidor);
            ps.setInt(2, idPedido);
            System.out.println("Asignando repartidor " + idRepartidor + " al pedido " + idPedido);
            int filas = ps.executeUpdate();
            System.out.println("Filas afectadas: " + filas);
            if (filas > 0) {
                cargarPedidos();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al asignar repartidor: " + e.getMessage());
        }
        return false;
    }
    
    
    // Pedidos asignados a un repartidor específico
    public java.util.LinkedList<Pedidos_Proyecto> obtenerPedidosPorRepartidor(int idRepartidor) {
        java.util.LinkedList<Pedidos_Proyecto> lista = new java.util.LinkedList<>();
        String sql = "SELECT ID_PEDIDO, ID_CLIENTE, ID_REPARTIDOR, DESCRIPCION, DIRECCION_ENTREGA, ESTADO, URGENCIA FROM PEDIDOS_PROYECTO WHERE ID_REPARTIDOR = ? AND ESTADO = 'ASIGNADO'";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idRepartidor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Pedidos_Proyecto(
                        rs.getInt("ID_PEDIDO"),
                        rs.getInt("ID_CLIENTE"),
                        rs.getObject("ID_REPARTIDOR") != null ? rs.getInt("ID_REPARTIDOR") : null,
                        rs.getString("DESCRIPCION"),
                        rs.getString("DIRECCION_ENTREGA"),
                        rs.getString("ESTADO"),
                        rs.getInt("URGENCIA")
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener pedidos repartidor: " + e.getMessage());
        }
        return lista;
    }
    
    

    // Marcar pedido como entregado
    public boolean marcarEntregado(int idPedido) {
        String sql = "UPDATE PEDIDOS_PROYECTO SET ESTADO = 'ENTREGADO' WHERE ID_PEDIDO = ?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                cargarPedidos();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al marcar entregado: " + e.getMessage());
        }
        return false;
    }
    
    public java.util.LinkedList<Pedidos_Proyecto> obtenerPedidosPorCliente(int idCliente) {
        java.util.LinkedList<Pedidos_Proyecto> lista = new java.util.LinkedList<>();
        String sql = "SELECT ID_PEDIDO, ID_CLIENTE, ID_REPARTIDOR, DESCRIPCION, DIRECCION_ENTREGA, ESTADO, URGENCIA FROM PEDIDOS_PROYECTO WHERE ID_CLIENTE = ? ORDER BY ID_PEDIDO DESC";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Pedidos_Proyecto(
                        rs.getInt("ID_PEDIDO"),
                        rs.getInt("ID_CLIENTE"),
                        rs.getObject("ID_REPARTIDOR") != null ? rs.getInt("ID_REPARTIDOR") : null,
                        rs.getString("DESCRIPCION"),
                        rs.getString("DIRECCION_ENTREGA"),
                        rs.getString("ESTADO"),
                        rs.getInt("URGENCIA")
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Error obtener pedidos cliente: " + e.getMessage());
        }
        return lista;
    }
    
    public java.util.LinkedList<Usuarios_Proyecto> obtenerTodos() {
        return tablaUsuarios.obtenerTodos();
    }

    public ColaPrioridad_Proyecto getColaPedidos() {
        return colaPedidos;
    }
    
    public TablaHash<Usuarios_Proyecto> getTablaUsuarios() { 
        return tablaUsuarios; 
    }
    
     // Getter para recorrer el árbol
    public ArbolMulticamino<Usuarios_Proyecto> getArbolUsuarios() {
        return arbolUsuarios; 
    }
}