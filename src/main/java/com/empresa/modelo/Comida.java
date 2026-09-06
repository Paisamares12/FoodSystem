package com.empresa.modelo;

/**
 * Representa una comida dentro del sistema.
 *
 * <p>Esta clase pertenece al paquete de modelo y se encarga únicamente
 * de almacenar la información básica de un producto de comida.
 * No contiene lógica de negocio ni elementos relacionados con la
 * presentación.</p>
 *
 * <p>La clase contiene los atributos de identificación, nombre,
 * ingredientes y precio del producto, junto con sus respectivos
 * constructores, métodos getters y setters.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class Comida {

    /**
     * Identificador único del producto.
     */
    private int id;

    /**
     * Nombre de la comida.
     */
    private String nombre;

    /**
     * Lista de ingredientes que componen el producto.
     */
    private String ingredientes;

    /**
     * Precio de venta del producto.
     */
    private double precio;

    /**
     * Constructor vacío de la clase Comida.
     *
     * <p>Permite crear una instancia de Comida sin proporcionar
     * inicialmente sus atributos. Es utilizado, entre otros casos,
     * para facilitar el mapeo de resultados obtenidos mediante JDBC.</p>
     */
    public Comida() {
    }

    /**
     * Constructor completo de la clase Comida.
     *
     * <p>Permite crear un producto estableciendo todos sus atributos
     * desde el momento de su creación.</p>
     *
     * @param id identificador único del producto
     * @param nombre nombre del producto
     * @param ingredientes lista de ingredientes del producto
     * @param precio precio de venta del producto
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
     * @return identificador único del producto
     */
    public int getId() {
        return id;
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
     * Obtiene los ingredientes del producto.
     *
     * @return lista de ingredientes de la comida
     */
    public String getIngredientes() {
        return ingredientes;
    }

    /**
     * Obtiene el precio de venta del producto.
     *
     * @return precio de la comida
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Modifica el identificador del producto.
     *
     * @param id nuevo identificador del producto
     */
    public void setId(int id) {
        this.id = id;
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
     * Modifica los ingredientes del producto.
     *
     * @param ingredientes nueva lista de ingredientes
     */
    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    /**
     * Modifica el precio de venta del producto.
     *
     * @param precio nuevo precio de venta
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
}