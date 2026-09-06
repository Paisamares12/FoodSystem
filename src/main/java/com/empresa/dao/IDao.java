package com.empresa.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz generica CRUD (Data Access Object).
 * @param <T>  tipo de la entidad
 * @param <ID> tipo del identificador
 * @author FoodSystem
 * @version 1.0
 */
public interface IDao<T, ID> {

    /** Agrega una nueva entidad. @param entidad objeto a persistir */
    void agregar(T entidad);

    /** Obtiene entidad por id. @param id identificador @return Optional con la entidad */
    Optional<T> obtenerPorId(ID id);

    /** Retorna todas las entidades. @return lista completa */
    List<T> obtenerTodos();

    /** Actualiza una entidad. @param entidad con datos nuevos @return true si exitoso */
    boolean actualizar(T entidad);

    /** Elimina una entidad. @param id identificador @return true si eliminado */
    boolean eliminar(ID id);
}