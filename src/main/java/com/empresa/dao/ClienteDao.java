package com.empresa.dao;

import com.empresa.models.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementacion DAO en memoria para {@link Cliente}.
 * Usa ArrayList como base de datos en memoria.
 * @author FoodSystem
 * @version 1.0
 */
public class ClienteDao implements IDao<Cliente, Integer> {

    /** Almacenamiento en memoria. */
    private final List<Cliente> almacenamiento = new ArrayList<>();

    /**
     * Agrega un nuevo cliente.
     * @param cliente cliente a agregar
     */
    @Override
    public void agregar(Cliente cliente) {
        almacenamiento.add(cliente);
        System.out.println("Cliente agregado correctamente.");
    }

    /**
     * Busca un cliente por su id.
     * @param id identificador
     * @return Optional con el cliente si existe
     */
    @Override
    public Optional<Cliente> obtenerPorId(Integer id) {
        return almacenamiento.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    /**
     * Retorna todos los clientes.
     * @return lista de clientes
     */
    @Override
    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(almacenamiento);
    }

    /**
     * Actualiza los datos de un cliente existente.
     * @param actualizado cliente con los nuevos datos
     * @return true si la actualizacion fue exitosa
     */
    @Override
    public boolean actualizar(Cliente actualizado) {
        Optional<Cliente> encontrado = obtenerPorId(actualizado.getId());
        if (encontrado.isEmpty()) {
            return false;
        }
        aplicarActualizacion(encontrado.get(), actualizado);
        return true;
    }

    /**
     * Copia los campos del cliente actualizado sobre el existente.
     * @param existente  cliente original
     * @param actualizado cliente con los nuevos valores
     */
    private void aplicarActualizacion(Cliente existente, Cliente actualizado) {
        existente.setNombre(actualizado.getNombre());
        existente.setTelefono(actualizado.getTelefono());
        existente.setDireccion(actualizado.getDireccion());
    }

    /**
     * Elimina un cliente por su id.
     * @param id identificador del cliente
     * @return true si fue eliminado
     */
    @Override
    public boolean eliminar(Integer id) {
        return almacenamiento.removeIf(c -> c.getId() == id);
    }

    /**
     * Verifica si existe un cliente con el id dado.
     * @param id identificador a verificar
     * @return true si ya existe
     */
    public boolean existeId(int id) {
        return obtenerPorId(id).isPresent();
    }
}