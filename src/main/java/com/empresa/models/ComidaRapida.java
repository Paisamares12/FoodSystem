package com.empresa.models;

/**
 * Entidad que representa un producto de comida rapida.
 * @author Paula Martínez
 * @version 1.0
 */
public class ComidaRapida {

    private int id;
    private String nombre;
    private String ingredientes;
    private double precio;

    /**
     * @param id           identificador unico
     * @param nombre       nombre del producto
     * @param ingredientes ingredientes del producto
     * @param precio       precio de venta
     */
    public ComidaRapida(int id, String nombre, String ingredientes, double precio) {
        this.id           = id;
        this.nombre       = nombre;
        this.ingredientes = ingredientes;
        this.precio       = precio;
    }

    public int getId()             { return id; }
    public String getNombre()      { return nombre; }
    public String getIngredientes(){ return ingredientes; }
    public double getPrecio()      { return precio; }

    public void setId(int id)                        { this.id = id; }
    public void setNombre(String nombre)             { this.nombre = nombre; }
    public void setIngredientes(String ingredientes) { this.ingredientes = ingredientes; }
    public void setPrecio(double precio)             { this.precio = precio; }

    @Override
    public String toString() {
        return String.format(
            "ComidaRapida{id=%d, nombre='%s', ingredientes='%s', precio=%.2f}",
            id, nombre, ingredientes, precio
        );
    }
}