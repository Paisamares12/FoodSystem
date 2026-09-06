package com.empresa.dao;

import com.empresa.models.ComidaRapida;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementacion DAO en memoria para {@link ComidaRapida}.
 * Usa ArrayList como base de datos en memoria.
 * @author Paula Martínez
 * @version 1.0
 */
public class ComidaRapidaDao implements IDao<ComidaRapida, Integer> {

    /** Almacenamiento en memoria. */
    private final List<ComidaRapida> almacenamiento = new ArrayList<>();

    /**
     * Agrega un nuevo producto.
     * @param producto producto a agregar
     */
    @Override
    public void agregar(ComidaRapida producto) {
        almacenamiento.add(producto);
        System.out.println("Producto agregado correctamente.");
    }

    /**
     * Busca un producto por su id.
     * @param id identificador
     * @return Optional con el producto si existe
     */
    @Override
    public Optional<ComidaRapida> obtenerPorId(Integer id) {
        return almacenamiento.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    /**
     * Retorna todos los productos.
     * @return lista de productos
     */
    @Override
    public List<ComidaRapida> obtenerTodos() {
        return new ArrayList<>(almacenamiento);
    }

    /**
     * Actualiza los datos de un producto existente.
     * @param actualizado producto con los nuevos datos
     * @return true si la actualizacion fue exitosa
     */
    @Override
    public boolean actualizar(ComidaRapida actualizado) {
        Optional<ComidaRapida> encontrado = obtenerPorId(actualizado.getId());
        if (encontrado.isEmpty()) {
            return false;
        }
        aplicarActualizacion(encontrado.get(), actualizado);
        return true;
    }

    /**
     * Copia los campos del producto actualizado sobre el existente.
     * @param existente  producto original
     * @param actualizado producto con los nuevos valores
     */
    private void aplicarActualizacion(ComidaRapida existente, ComidaRapida actualizado) {
        existente.setNombre(actualizado.getNombre());
        existente.setIngredientes(actualizado.getIngredientes());
        existente.setPrecio(actualizado.getPrecio());
    }

    /**
     * Elimina un producto por su id.
     * @param id identificador del producto
     * @return true si fue eliminado
     */
    @Override
    public boolean eliminar(Integer id) {
        return almacenamiento.removeIf(p -> p.getId() == id);
    }

    /**
     * Verifica si existe un producto con el id dado.
     * @param id identificador a verificar
     * @return true si ya existe
     */
    public boolean existeId(int id) {
        return obtenerPorId(id).isPresent();
    }
}