package com.empresa.modelo;

/**
 * Entidad Cliente — solo atributos, constructor, getters y setters.
 * Sin logica de negocio ni de presentacion.
 *
 * @author FoodSystem
 * @version 2.0
 */
public class Cliente {

    private int    id;
    private String nombre;
    private String telefono;
    private String direccion;

    /** Constructor vacio requerido por JDBC ResultSet mapping. */
    public Cliente() {}

    /**
     * Constructor completo.
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

    public int    getId()        { return id; }
    public String getNombre()    { return nombre; }
    public String getTelefono()  { return telefono; }
    public String getDireccion() { return direccion; }

    public void setId(int id)               { this.id        = id; }
    public void setNombre(String nombre)    { this.nombre    = nombre; }
    public void setTelefono(String tel)     { this.telefono  = tel; }
    public void setDireccion(String dir)    { this.direccion = dir; }
}