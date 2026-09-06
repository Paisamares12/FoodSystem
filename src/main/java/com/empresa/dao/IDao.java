package com.empresa.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz generica que define el contrato CRUD para cualquier entidad.
 *
 * <p>Aplica el principio de Inversion de Dependencias (DIP):
 * las capas superiores dependen de esta abstraccion, no de la implementacion.</p>
 *
 * @param <T>  tipo de la entidad
 * @param <ID> tipo del identificador
 * @author FoodSystem
 * @version 2.0
 */
public interface IDao<T, ID> {

    /** Persiste una nueva entidad en la base de datos. @param entidad objeto a guardar */
    void agregar(T entidad);

    /** Busca una entidad por su identificador. @param id clave primaria @return Optional con la entidad */
    Optional<T> obtenerPorId(ID id);

    /** Retorna todas las entidades almacenadas. @return lista completa */
    List<T> obtenerTodos();

    /** Actualiza los datos de una entidad existente. @param entidad con datos nuevos @return true si exitoso */
    boolean actualizar(T entidad);

    /** Elimina una entidad por su identificador. @param id clave primaria @return true si eliminado */
    boolean eliminar(ID id);
}