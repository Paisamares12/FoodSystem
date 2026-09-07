package com.empresa.modelo;

import java.util.Objects;

/**
 * Representa la entidad Comida rápida dentro del dominio del sistema.
 *
 * <p>Esta clase encapsula las propiedades de un producto alimenticio
 * (identificador, nombre, ingredientes y precio), garantizando la
 * integridad del modelo mediante encapsulamiento estricto.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class Comida {

    /**
     * Identificador único del producto (clave primaria).
     */
    private int id;

    /**
     * Nombre de la comida rápida.
     */
    private String nombre;

    /**
     * Lista o detalle de ingredientes del producto.
     */
    private String ingredientes;

    /**
     * Precio unitario de venta al público.
     */
    private double precio;

    /**
     * Constructor por defecto.
     * Permite instanciar un producto vacío para mapeo u operaciones dinámicas.
     */
    public Comida() {
    }

    /**
     * Constructor para creación de productos nuevos (sin ID asignado por la BD).
     *
     * @param nombre       nombre de la comida
     * @param ingredientes ingredientes que componen el producto
     * @param precio       precio de venta al público
     */
    public Comida(String nombre, String ingredientes, double precio) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
    }

    /**
     * Constructor completo para productos existentes (con ID).
     *
     * @param id           identificador único del producto
     * @param nombre       nombre de la comida
     * @param ingredientes ingredientes que componen el producto
     * @param precio       precio de venta al público
     */
    public Comida(int id, String nombre, String ingredientes, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
    }

    /**
     * Obtiene el identificador del producto.
     *
     * @return identificador numérico
     */
    public int getId() {
        return id;
    }

    /**
     * Modifica el identificador del producto.
     *
     * @param id nuevo identificador numérico
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return nombre de la comida
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del producto.
     *
     * @param nombre nuevo nombre de la comida
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los ingredientes del producto.
     *
     * @return lista de ingredientes
     */
    public String getIngredientes() {
        return ingredientes;
    }

    /**
     * Modifica la lista de ingredientes del producto.
     *
     * @param ingredientes nuevos ingredientes
     */
    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    /**
     * Obtiene el precio de venta del producto.
     *
     * @return precio unitario
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Modifica el precio de venta del producto.
     *
     * @param precio nuevo precio unitario
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Retorna una representación textual del producto de comida rápida.
     *
     * @return cadena con los datos del producto
     */
    @Override
    public String toString() {
        return "Comida{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ingredientes='" + ingredientes + '\'' +
                ", precio=" + precio +
                '}';
    }
}