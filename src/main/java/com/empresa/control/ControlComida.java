package com.empresa.control;

import com.empresa.dao.ComidaDao;
import com.empresa.dao.ConexionBD;
import com.empresa.modelo.Comida;

import java.util.List;

/**
 * Controlador encargado de la lógica de negocio relacionada
 * con la entidad {@link Comida}.
 *
 * <p>Es el único punto de acceso entre la vista y el DAO de comidas.
 * Aquí se realizan las validaciones antes de delegar la operación
 * al DAO correspondiente.</p>
 *
 * @author Paula Martínez
 * @version 1.0
 */
public class ControlComida {

    private final ComidaDao comidaDao;

    public ControlComida() {
        this.comidaDao = new ComidaDao(ConexionBD.getInstancia().getConexion());
    }

    public void registrarComida(String nombre, String ingredientes, double precio) {
        validarDatos(nombre, ingredientes, precio);
        Comida comida = new Comida(nombre, ingredientes, precio); // sin id
        comidaDao.crear(comida);
    }

    public void actualizarComida(int id, String nombre, String ingredientes, double precio) {
        validarDatos(nombre, ingredientes, precio);
        Comida comida = new Comida(id, nombre, ingredientes, precio); // con id
        comidaDao.actualizar(comida);
    }

    public void eliminarComida(int id) {
        comidaDao.eliminar(id);
    }

    public Comida obtenerComida(int id) {
        return comidaDao.obtenerPorId(id);
    }

    public List<Comida> listarComidas() {
        return comidaDao.listarTodos();
    }

    /**
     * Válida las reglas de negocio básicas antes de persistir una comida.
     *
     * @throws IllegalArgumentException si algún dato es inválido
     */
    private void validarDatos(String nombre, String ingredientes, double precio) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (ingredientes == null || ingredientes.isBlank()) {
            throw new IllegalArgumentException("Los ingredientes no pueden estar vacíos.");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero.");
        }
    }
}