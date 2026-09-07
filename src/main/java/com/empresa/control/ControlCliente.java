package com.empresa.control;

import com.empresa.dao.ClienteDao;
import com.empresa.dao.ConexionBD;
import com.empresa.dao.IDao;
import com.empresa.excepciones.ClienteNoEncontradoException;
import com.empresa.modelo.Cliente;
import com.empresa.util.Validador;

import java.util.List;

/**
 * Controlador encargado de gestionar la lógica de negocio relacionada
 * con la entidad {@link Cliente}.
 *
 * <p>Implementa la interfaz {@link IControlCliente}, actuando como intermediario
 * entre la capa de presentación (Vista) y la capa de acceso a datos (DAO).
 * Se encarga de validar los datos ingresados antes de realizar cualquier operación
 * de persistencia, cumpliendo con los principios SOLID (SRP, DIP, LSP).</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class ControlCliente implements IControlCliente {

    /**
     * Objeto de acceso a datos para la entidad Cliente mediante abstracción.
     */
    private final IDao<Cliente, Integer> clienteDao;

    /**
     * Constructor por defecto.
     * Inicializa el DAO utilizando la conexión singleton administrada por {@link ConexionBD}.
     */
    public ControlCliente() {
        this.clienteDao = new ClienteDao(ConexionBD.getInstancia().getConexion());
    }

    /**
     * Constructor con inyección de dependencia del DAO.
     * Permite pruebas unitarias o el uso de implementaciones alternativas de DAO (DIP).
     *
     * @param clienteDao instancia del DAO de clientes
     */
    public ControlCliente(IDao<Cliente, Integer> clienteDao) {
        if (clienteDao == null) {
            throw new IllegalArgumentException("El DAO de cliente no puede ser nulo.");
        }
        this.clienteDao = clienteDao;
    }

    /**
     * Registra un nuevo cliente en el sistema tras validar sus campos.
     *
     * @param nombre    nombre completo del cliente
     * @param telefono  teléfono de contacto
     * @param direccion dirección de residencia o despacho
     * @throws IllegalArgumentException si algún dato no es válido
     */
    @Override
    public void registrarCliente(String nombre, String telefono, String direccion) {
        validarDatos(nombre, telefono, direccion);
        Cliente cliente = new Cliente(nombre.trim(), telefono.trim(), direccion.trim());
        clienteDao.crear(cliente);
    }

    /**
     * Actualiza los datos de un cliente existente tras validar sus campos.
     *
     * @param id        identificador único del cliente a actualizar
     * @param nombre    nuevo nombre del cliente
     * @param telefono  nuevo teléfono de contacto
     * @param direccion nueva dirección del cliente
     * @throws ClienteNoEncontradoException si el cliente con el ID proporcionado no existe
     * @throws IllegalArgumentException si los datos no son válidos o el ID no es positivo
     */
    @Override
    public void actualizarCliente(int id, String nombre, String telefono, String direccion) {
        Validador.validarIdPositivo(id, "ID de cliente");
        validarDatos(nombre, telefono, direccion);

        Cliente existente = clienteDao.obtenerPorId(id);
        if (existente == null) {
            throw new ClienteNoEncontradoException(id);
        }

        Cliente cliente = new Cliente(id, nombre.trim(), telefono.trim(), direccion.trim());
        clienteDao.actualizar(cliente);
    }

    /**
     * Elimina un cliente del sistema según su identificador único.
     *
     * @param id identificador único del cliente a eliminar
     * @throws ClienteNoEncontradoException si el cliente con el ID proporcionado no existe
     * @throws IllegalArgumentException si el ID es menor o igual a cero
     */
    @Override
    public void eliminarCliente(int id) {
        Validador.validarIdPositivo(id, "ID de cliente");

        Cliente existente = clienteDao.obtenerPorId(id);
        if (existente == null) {
            throw new ClienteNoEncontradoException(id);
        }

        clienteDao.eliminar(id);
    }

    /**
     * Obtiene un cliente a partir de su ID.
     *
     * @param id identificador del cliente
     * @return objeto {@link Cliente} encontrado
     * @throws ClienteNoEncontradoException si no existe ningún cliente con dicho ID
     * @throws IllegalArgumentException si el ID es menor o igual a cero
     */
    @Override
    public Cliente obtenerCliente(int id) {
        Validador.validarIdPositivo(id, "ID de cliente");
        Cliente cliente = clienteDao.obtenerPorId(id);
        if (cliente == null) {
            throw new ClienteNoEncontradoException(id);
        }
        return cliente;
    }

    /**
     * Retorna la lista con todos los clientes registrados en la base de datos.
     *
     * @return lista de objetos {@link Cliente}
     */
    @Override
    public List<Cliente> listarClientes() {
        return clienteDao.listarTodos();
    }

    /**
     * Valida los datos requeridos para la creación o actualización de un cliente.
     *
     * @param nombre    nombre del cliente
     * @param telefono  teléfono del cliente
     * @param direccion dirección del cliente
     * @throws IllegalArgumentException si algún campo está vacío o es nulo
     */
    private void validarDatos(String nombre, String telefono, String direccion) {
        Validador.validarTextoNoVacio(nombre, "Nombre");
        Validador.validarTextoNoVacio(telefono, "Teléfono");
        Validador.validarTextoNoVacio(direccion, "Dirección");
    }
}