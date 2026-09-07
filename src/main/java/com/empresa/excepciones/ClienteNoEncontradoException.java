package com.empresa.excepciones;

/**
 * Excepción personalizada lanzada cuando no se encuentra un cliente
 * en la base de datos o en las operaciones del sistema.
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class ClienteNoEncontradoException extends RuntimeException {

    /**
     * Constructor con mensaje descriptivo del error.
     *
     * @param mensaje detalle de la causa de la excepción
     */
    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con identificador del cliente no encontrado.
     *
     * @param id identificador del cliente
     */
    public ClienteNoEncontradoException(int id) {
        super("No se encontró ningún cliente con el ID: " + id);
    }
}
