package com.empresa.control;

import com.empresa.modelo.Comida;
import java.util.List;

/**
 * Interfaz que define el contrato para la lógica de negocio y operaciones
 * relacionadas con la entidad {@link Comida}.
 *
 * <p>Permite desacoplar la vista de la implementación concreta del controlador
 * de comidas rápidas, garantizando una arquitectura extensible y organizada.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public interface IControlComida {

    /**
     * Registra un nuevo producto de comida rápida tras validar sus datos.
     *
     * @param nombre       nombre de la comida rápida
     * @param ingredientes lista de ingredientes principales
     * @param precio       precio de venta al público (debe ser mayor a 0)
     * @throws IllegalArgumentException si los datos no cumplen con las reglas de negocio
     */
    void registrarComida(String nombre, String ingredientes, double precio);

    /**
     * Actualiza la información de una comida rápida existente.
     *
     * @param id           identificador único del producto
     * @param nombre       nuevo nombre del producto
     * @param ingredientes nueva lista de ingredientes
     * @param precio       nuevo precio de venta
     * @throws IllegalArgumentException si los datos o el precio son inválidos
     */
    void actualizarComida(int id, String nombre, String ingredientes, double precio);

    /**
     * Elimina un producto de comida rápida del sistema mediante su identificador.
     *
     * @param id identificador único del producto a eliminar
     */
    void eliminarComida(int id);

    /**
     * Busca y obtiene una comida rápida a partir de su ID.
     *
     * @param id identificador del producto
     * @return objeto {@link Comida} encontrado o null si no existe
     */
    Comida obtenerComida(int id);

    /**
     * Obtiene la lista completa de comidas rápidas registradas en la base de datos.
     *
     * @return lista de objetos {@link Comida}
     */
    List<Comida> listarComidas();
}
