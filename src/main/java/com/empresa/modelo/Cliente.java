package com.empresa.modelo;

/**
 * Representa un cliente dentro del sistema.
 *
 * <p>Esta clase pertenece al paquete de modelo y se encarga únicamente
 * de almacenar la información básica de un cliente. No contiene lógica
 * de negocio ni elementos relacionados con la presentación.</p>
 *
 * <p>La clase contiene los atributos de identificación, nombre,
 * teléfono y dirección del cliente, junto con sus respectivos
 * constructores, métodos getters y setters.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class Cliente {

    /**
     * Identificador único del cliente.
     */
    private int id;

    /**
     * Nombre completo del cliente.
     */
    private String nombre;

    /**
     * Número de teléfono del cliente.
     */
    private String telefono;

    /**
     * Dirección de entrega del cliente.
     */
    private String direccion;

    /**
     * Constructor vacío de la clase Cliente.
     *
     * <p>Permite crear una instancia de Cliente sin proporcionar
     * inicialmente sus atributos. Es utilizado, entre otros casos,
     * para facilitar el mapeo de resultados obtenidos mediante JDBC.</p>
     */
    public Cliente() {
    }

    /**
     * Constructor completo de la clase Cliente.
     *
     * <p>Permite crear un cliente estableciendo todos sus atributos
     * desde el momento de su creación.</p>
     *
     * @param id identificador único del cliente
     * @param nombre nombre completo del cliente
     * @param telefono número de teléfono del cliente
     * @param direccion dirección de entrega del cliente
     */
    public Cliente(int id, String nombre, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    /**
     * Obtiene el identificador del cliente.
     *
     * @return identificador único del cliente
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return nombre completo del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     *
     * @return número de teléfono del cliente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Obtiene la dirección de entrega del cliente.
     *
     * @return dirección del cliente
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Modifica el identificador del cliente.
     *
     * @param id nuevo identificador del cliente
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Modifica el nombre del cliente.
     *
     * @param nombre nuevo nombre completo del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Modifica el número de teléfono del cliente.
     *
     * @param tel nuevo número de teléfono del cliente
     */
    public void setTelefono(String tel) {
        this.telefono = tel;
    }

    /**
     * Modifica la dirección de entrega del cliente.
     *
     * @param dir nueva dirección de entrega del cliente
     */
    public void setDireccion(String dir) {
        this.direccion = dir;
    }
}