package com.empresa.excepciones;

/**
 * Excepción personalizada lanzada cuando no se encuentra un producto de comida rápida
 * en la base de datos o en las operaciones del sistema.
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class ComidaNoEncontradaException extends RuntimeException {

    /**
     * Constructor con mensaje descriptivo del error.
     *
     * @param mensaje detalle de la causa de la excepción
     */
    public ComidaNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con identificador de la comida rápida no encontrada.
     *
     * @param id identificador del producto de comida
     */
    public ComidaNoEncontradaException(int id) {
        super("No se encontró ningún producto de comida rápida con el ID: " + id);
    }
}
