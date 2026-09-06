package com.empresa.dao;

import java.util.List;

/**
 * Define las operaciones básicas de acceso a datos para una entidad.
 *
 * <p>Esta interfaz establece las operaciones CRUD (Crear, Leer,
 * Actualizar y Eliminar) que deben implementar las clases DAO
 * encargadas de interactuar con la base de datos.</p>
 *
 * <p>Los tipos genéricos permiten utilizar la misma interfaz
 * con diferentes entidades y tipos de identificadores.</p>
 *
 * @param <T> tipo de entidad que será gestionada
 * @param <ID> tipo de dato utilizado como identificador de la entidad
 *
 * @author Paula Martínez
 * @version 2.0
 */
public interface IDao<T, ID> {

    /**
     * Crea una nueva entidad en la base de datos.
     *
     * @param entidad entidad que se desea almacenar
     */
    void crear(T entidad);

    /**
     * Actualiza una entidad existente en la base de datos.
     *
     * @param entidad entidad con los datos actualizados
     */
    void actualizar(T entidad);

    /**
     * Elimina una entidad de la base de datos utilizando su identificador.
     *
     * @param id identificador de la entidad que se desea eliminar
     */
    void eliminar(ID id);

    /**
     * Obtiene una entidad utilizando su identificador.
     *
     * @param id identificador de la entidad que se desea consultar
     * @return entidad encontrada o null si no existe
     */
    T obtenerPorId(ID id);

    /**
     * Obtiene todas las entidades almacenadas en la base de datos.
     *
     * @return lista con todas las entidades
     */
    List<T> listarTodos();
}
