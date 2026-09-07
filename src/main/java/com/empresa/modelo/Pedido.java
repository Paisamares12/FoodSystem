package com.empresa.modelo;

/**
 * Representa un pedido dentro del sistema de comidas.
 *
 * <p>Esta clase pertenece al paquete de modelo y almacena la
 * información necesaria para relacionar un cliente con una comida
 * y registrar la cantidad solicitada y el valor total del pedido.</p>
 *
 * <p>La clase no contiene lógica de negocio ni elementos relacionados
 * con la presentación.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class Pedido {

    /**
     * Identificador único del pedido.
     */
    private int id;

    /**
     * Identificador del cliente que realiza el pedido.
     */
    private int idCliente;

    /**
     * Identificador de la comida incluida en el pedido.
     */
    private int idComida;

    /**
     * Cantidad de unidades solicitadas.
     */
    private int cantidad;

    /**
     * Valor total del pedido.
     */
    private double total;

    /**
     * Constructor vacío de la clase Pedido.
     *
     * <p>Permite crear una instancia de Pedido sin proporcionar
     * inicialmente sus atributos. Puede utilizarse para facilitar
     * el mapeo de resultados obtenidos mediante JDBC.</p>
     */
    public Pedido() {
    }

    /**
     * Constructor completo de la clase Pedido.
     *
     * <p>Permite crear un pedido estableciendo todos sus atributos
     * desde el momento de su creación.</p>
     *
     * @param id identificador único del pedido
     * @param idCliente identificador del cliente que realiza el pedido
     * @param idComida identificador de la comida solicitada
     * @param cantidad cantidad de unidades solicitadas
     * @param total valor total del pedido
     */
    public Pedido(int id, int idCliente, int idComida,
                  int cantidad, double total) {
        this.id = id;
        this.idCliente = idCliente;
        this.idComida = idComida;
        this.cantidad = cantidad;
        this.total = total;
    }

    /**
     * Obtiene el identificador del pedido.
     *
     * @return identificador único del pedido
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el identificador del cliente.
     *
     * @return identificador del cliente asociado al pedido
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Obtiene el identificador de la comida.
     *
     * @return identificador de la comida asociada al pedido
     */
    public int getIdComida() {
        return idComida;
    }

    /**
     * Obtiene la cantidad solicitada.
     *
     * @return cantidad de unidades del producto
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Obtiene el valor total del pedido.
     *
     * @return valor total del pedido
     */
    public double getTotal() {
        return total;
    }

    /**
     * Modifica el identificador del pedido.
     *
     * @param id nuevo identificador del pedido
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Modifica el identificador del cliente.
     *
     * @param idCliente nuevo identificador del cliente
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Modifica el identificador de la comida.
     *
     * @param idComida nuevo identificador de la comida
     */
    public void setIdComida(int idComida) {
        this.idComida = idComida;
    }

    /**
     * Modifica la cantidad solicitada.
     *
     * @param cantidad nueva cantidad de unidades
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Modifica el valor total del pedido.
     *
     * @param total nuevo valor total del pedido
     */
    public void setTotal(double total) {
        this.total = total;
    }
}