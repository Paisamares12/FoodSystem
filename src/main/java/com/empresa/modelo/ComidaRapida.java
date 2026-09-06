package com.empresa.modelo;

/**
 * Entidad ComidaRapida — solo atributos, constructor, getters y setters.
 * Sin logica de negocio ni de presentacion.
 *
 * @author FoodSystem
 * @version 2.0
 */
public class ComidaRapida {

    private int    id;
    private String nombre;
    private String ingredientes;
    private double precio;

    /** Constructor vacio requerido por JDBC ResultSet mapping. */
    public ComidaRapida() {}

    /**
     * Constructor completo.
     * @param id           identificador unico
     * @param nombre       nombre del producto
     * @param ingredientes lista de ingredientes
     * @param precio       precio de venta
     */
    public ComidaRapida(int id, String nombre, String ingredientes, double precio) {
        this.id           = id;
        this.nombre       = nombre;
        this.ingredientes = ingredientes;
        this.precio       = precio;
    }

    public int    getId()            { return id; }
    public String getNombre()        { return nombre; }
    public String getIngredientes()  { return ingredientes; }
    public double getPrecio()        { return precio; }

    public void setId(int id)                       { this.id           = id; }
    public void setNombre(String nombre)            { this.nombre       = nombre; }
    public void setIngredientes(String ingredientes){ this.ingredientes = ingredientes; }
    public void setPrecio(double precio)            { this.precio       = precio; }
}