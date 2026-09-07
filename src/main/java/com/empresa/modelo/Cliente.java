package com.empresa.modelo;

import java.util.Objects;

/**
 * Representa la entidad Cliente dentro del dominio del sistema.
 *
 * <p>Esta clase es un componente puro del modelo (POJO / Bean) y se encarga
 * de encapsular el estado y los datos de un cliente. Sigue estrictamente
 * el principio de encapsulamiento mediante atributos privados y métodos
 * de acceso público.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class Cliente {

    /**
     * Identificador único del cliente (clave primaria).
     */
    private int id;

    /**
     * Nombre completo del cliente.
     */
    private String nombre;

    /**
     * Número telefónico de contacto del cliente.
     */
    private String telefono;

    /**
     * Dirección física de despacho o residencia.
     */
    private String direccion;

    /**
     * Constructor por defecto.
     * Permite instanciar un objeto vacío para mapeo u operaciones dinámicas.
     */
    public Cliente() {
    }

    /**
     * Constructor para creación de clientes nuevos (sin identificador asignado por la BD).
     *
     * @param nombre    nombre completo del cliente
     * @param telefono  teléfono de contacto
     * @param direccion dirección de entrega
     */
    public Cliente(String nombre, String telefono, String direccion) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    /**
     * Constructor completo para clientes existentes (con identificador).
     *
     * @param id        identificador único del cliente
     * @param nombre    nombre completo del cliente
     * @param telefono  teléfono de contacto
     * @param direccion dirección de entrega
     */
    public Cliente(int id, String nombre, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    /**
     * Obtiene el identificador único del cliente.
     *
     * @return identificador numérico
     */
    public int getId() {
        return id;
    }

    /**
     * Modifica el identificador del cliente.
     *
     * @param id nuevo identificador numérico
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre completo del cliente.
     *
     * @return nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre completo del cliente.
     *
     * @param nombre nuevo nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el número telefónico del cliente.
     *
     * @return teléfono de contacto
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Modifica el número telefónico del cliente.
     *
     * @param telefono nuevo teléfono de contacto
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección de entrega del cliente.
     *
     * @return dirección de entrega
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Modifica la dirección de entrega del cliente.
     *
     * @param direccion nueva dirección
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    /**
     * Retorna una representación textual amigable del cliente.
     *
     * @return cadena con los datos del cliente
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}