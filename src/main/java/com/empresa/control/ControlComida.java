package com.empresa.control;

import com.empresa.dao.ComidaDao;
import com.empresa.dao.ConexionBD;
import com.empresa.dao.IDao;
import com.empresa.excepciones.ComidaNoEncontradaException;
import com.empresa.modelo.Comida;
import com.empresa.util.Validador;

import java.util.List;

/**
 * Controlador encargado de gestionar la lógica de negocio relacionada
 * con la entidad {@link Comida}.
 *
 * <p>Implementa la interfaz {@link IControlComida} y asegura que los datos
 * de los productos de comida rápida cumplan con las reglas de negocio
 * (precios positivos, nombres no vacíos, etc.) antes de interactuar con el DAO.
 * Cumple estrictamente con los principios SOLID y POO.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class ControlComida implements IControlComida {

    /**
     * Objeto de acceso a datos para la entidad Comida mediante abstracción.
     */
    private final IDao<Comida, Integer> comidaDao;

    /**
     * Constructor por defecto.
     * Inicializa el DAO utilizando la conexión singleton administrada por {@link ConexionBD}.
     */
    public ControlComida() {
        this.comidaDao = new ComidaDao(ConexionBD.getInstancia().getConexion());
    }

    /**
     * Constructor con inyección de dependencia del DAO.
     * Permite pruebas unitarias o el uso de implementaciones alternativas de DAO (DIP).
     *
     * @param comidaDao instancia del DAO de comidas
     */
    public ControlComida(IDao<Comida, Integer> comidaDao) {
        if (comidaDao == null) {
            throw new IllegalArgumentException("El DAO de comida no puede ser nulo.");
        }
        this.comidaDao = comidaDao;
    }

    /**
     * Registra un nuevo producto de comida rápida tras validar sus propiedades.
     *
     * @param nombre       nombre de la comida
     * @param ingredientes ingredientes o descripción del producto
     * @param precio       precio de venta al público (debe ser mayor a 0)
     * @throws IllegalArgumentException si los datos no son válidos o el precio no es positivo
     */
    @Override
    public void registrarComida(String nombre, String ingredientes, double precio) {
        validarDatos(nombre, ingredientes, precio);
        Comida comida = new Comida(nombre.trim(), ingredientes.trim(), precio);
        comidaDao.crear(comida);
    }

    /**
     * Actualiza la información de una comida rápida existente.
     *
     * @param id           identificador único de la comida
     * @param nombre       nuevo nombre del producto
     * @param ingredientes nueva lista de ingredientes
     * @param precio       nuevo precio de venta (debe ser mayor a 0)
     * @throws ComidaNoEncontradaException si el producto con el ID especificado no existe
     * @throws IllegalArgumentException si los datos son inválidos o el ID no es positivo
     */
    @Override
    public void actualizarComida(int id, String nombre, String ingredientes, double precio) {
        Validador.validarIdPositivo(id, "ID de comida");
        validarDatos(nombre, ingredientes, precio);

        Comida existente = comidaDao.obtenerPorId(id);
        if (existente == null) {
            throw new ComidaNoEncontradaException(id);
        }

        Comida comida = new Comida(id, nombre.trim(), ingredientes.trim(), precio);
        comidaDao.actualizar(comida);
    }

    /**
     * Elimina un producto de comida rápida del sistema.
     *
     * @param id identificador único del producto a eliminar
     * @throws ComidaNoEncontradaException si el producto con el ID especificado no existe
     * @throws IllegalArgumentException si el ID es menor o igual a cero
     */
    @Override
    public void eliminarComida(int id) {
        Validador.validarIdPositivo(id, "ID de comida");

        Comida existente = comidaDao.obtenerPorId(id);
        if (existente == null) {
            throw new ComidaNoEncontradaException(id);
        }

        comidaDao.eliminar(id);
    }

    /**
     * Obtiene una comida rápida según su identificador único.
     *
     * @param id identificador del producto
     * @return objeto {@link Comida} correspondiente
     * @throws ComidaNoEncontradaException si el producto con dicho ID no existe
     * @throws IllegalArgumentException si el ID es menor o igual a cero
     */
    @Override
    public Comida obtenerComida(int id) {
        Validador.validarIdPositivo(id, "ID de comida");
        Comida comida = comidaDao.obtenerPorId(id);
        if (comida == null) {
            throw new ComidaNoEncontradaException(id);
        }
        return comida;
    }

    /**
     * Retorna todas las comidas rápidas almacenadas en el sistema.
     *
     * @return lista de objetos {@link Comida}
     */
    @Override
    public List<Comida> listarComidas() {
        return comidaDao.listarTodos();
    }

    /**
     * Valida los datos requeridos para la creación o actualización de una comida rápida.
     *
     * @param nombre       nombre de la comida
     * @param ingredientes ingredientes o descripción
     * @param precio       precio de venta
     * @throws IllegalArgumentException si los campos están vacíos o el precio es inválido
     */
    private void validarDatos(String nombre, String ingredientes, double precio) {
        Validador.validarTextoNoVacio(nombre, "Nombre de comida");
        Validador.validarTextoNoVacio(ingredientes, "Ingredientes");
        Validador.validarNumeroPositivo(precio, "Precio");
    }
}