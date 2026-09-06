package com.empresa.models;

/**
 * Entidad que representa a un Cliente del sistema de comidas rapidas.
 *
 * <p>Encapsula los datos personales y de contacto del cliente,
 * aplicando encapsulamiento como principio fundamental de POO.</p>
 *
 * @author FoodSystem
 * @version 1.0
 */
public class Cliente {

    /** Identificador unico del cliente. */
    private int id;

    /** Nombre completo del cliente. */
    private String nombre;

    /** Numero de telefono del cliente. */
    private String telefono;

    /** Direccion de entrega del cliente. */
    private String direccion;

    /**
     * Constructor que inicializa todos los atributos del cliente.
     * @param id        identificador unico
     * @param nombre    nombre completo
     * @param telefono  numero de telefono
     * @param direccion direccion de entrega
     */
    public Cliente(int id, String nombre, String telefono, String direccion) {
        this.id        = id;
        this.nombre    = nombre;
        this.telefono  = telefono;
        this.direccion = direccion;
    }

    /**
     * Retorna el identificador del cliente.
     * @return id del cliente
     */
    public int getId() { return id; }

    /**
     * Retorna el nombre del cliente.
     * @return nombre del cliente
     */
    public String getNombre() { return nombre; }

    /**
     * Retorna el telefono del cliente.
     * @return telefono del cliente
     */
    public String getTelefono() { return telefono; }

    /**
     * Retorna la direccion del cliente.
     * @return direccion del cliente
     */
    public String getDireccion() { return direccion; }

    /**
     * Establece el identificador del cliente.
     * @param id nuevo identificador
     */
    public void setId(int id) { this.id = id; }

    /**
     * Establece el nombre del cliente.
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Establece el telefono del cliente.
     * @param telefono nuevo telefono
     */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /**
     * Establece la direccion del cliente.
     * @param direccion nueva direccion
     */
    public void setDireccion(String direccion) { this.direccion = direccion; }

    /**
     * Retorna representacion textual del cliente.
     * @return cadena con todos los datos
     */
    @Override
    public String toString() {
        return String.format(
            "Cliente{id=%d, nombre='%s', telefono='%s', direccion='%s'}",
            id, nombre, telefono, direccion
        );
    }
}