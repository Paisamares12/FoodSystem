package com.empresa.util;

/**
 * Clase utilitaria encargada de la validación general de campos y datos de entrada.
 *
 * <p>Proporciona métodos estáticos reutilizables para garantizar que los datos
 * suministrados cumplan con los estándares requeridos antes de ser procesados
 * por los controladores y persistidos en la base de datos.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public final class Validador {

    /**
     * Constructor privado para evitar la instanciación directa de una clase utilitaria.
     */
    private Validador() {
        throw new UnsupportedOperationException("No se permite instanciar la clase de utilidades.");
    }

    /**
     * Valida que una cadena de texto no sea nula ni contenga únicamente espacios en blanco.
     *
     * @param texto        cadena a validar
     * @param nombreCampo  nombre descriptivo del campo para el mensaje de excepción
     * @throws IllegalArgumentException si el texto es nulo o está en blanco
     */
    public static void validarTextoNoVacio(String texto, String nombreCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo '" + nombreCampo + "' no puede estar vacío.");
        }
    }

    /**
     * Valida que un valor numérico decimal sea estrictamente positivo (mayor a cero).
     *
     * @param valor        valor numérico a verificar
     * @param nombreCampo  nombre del campo descriptivo
     * @throws IllegalArgumentException si el valor es menor o igual a cero
     */
    public static void validarNumeroPositivo(double valor, String nombreCampo) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El campo '" + nombreCampo + "' debe ser un valor numérico mayor a cero.");
        }
    }

    /**
     * Valida que un identificador numérico entero sea positivo (mayor a cero).
     *
     * @param id           identificador numérico
     * @param nombreCampo  nombre descriptivo del campo
     * @throws IllegalArgumentException si el ID es menor o igual a cero
     */
    public static void validarIdPositivo(int id, String nombreCampo) {
        if (id <= 0) {
            throw new IllegalArgumentException("El " + nombreCampo + " debe ser un número entero positivo.");
        }
    }
}
