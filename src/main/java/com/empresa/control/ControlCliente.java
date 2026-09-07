package com.empresa.control;

import com.empresa.dao.ClienteDao;
import com.empresa.dao.ConexionBD;
import com.empresa.modelo.Cliente;

import java.util.List;

/**
 * Controlador encargado de la lógica de negocio relacionada
 * con la entidad {@link Cliente}.
 *
 * <p>Es el único punto de acceso entre la vista y el DAO de clientes.
 * Aquí se realizan las validaciones antes de delegar la operación
 * al DAO correspondiente.</p>
 *
 * @author Paula Martínez
 * @version 1.0
 */
public class ControlCliente {

    private final ClienteDao clienteDao;

    public ControlCliente() {
        this.clienteDao = new ClienteDao(ConexionBD.getInstancia().getConexion());
    }

    public void registrarCliente(String nombre, String telefono, String direccion) {
        validarDatos(nombre, telefono, direccion);
        Cliente cliente = new Cliente(nombre, telefono, direccion); // sin id
        clienteDao.crear(cliente);
    }

    public void actualizarCliente(int id, String nombre, String telefono, String direccion) {
        validarDatos(nombre, telefono, direccion);
        Cliente cliente = new Cliente(id, nombre, telefono, direccion); // con id
        clienteDao.actualizar(cliente);
    }

    public void eliminarCliente(int id) {
        clienteDao.eliminar(id);
    }

    public Cliente obtenerCliente(int id) {
        return clienteDao.obtenerPorId(id);
    }

    public List<Cliente> listarClientes() {
        return clienteDao.listarTodos();
    }

    /**
     * Valida las reglas de negocio básicas antes de persistir un cliente.
     *
     * @throws IllegalArgumentException si algún dato es inválido
     */
    private void validarDatos(String nombre, String telefono, String direccion) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        }
        if (direccion == null || direccion.isBlank()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía.");
        }
    }
}