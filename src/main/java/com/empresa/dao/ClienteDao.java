package com.empresa.dao;

import com.empresa.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de gestionar las operaciones de acceso a datos
 * relacionadas con la entidad {@link Cliente}.
 *
 * <p>Esta clase implementa la interfaz {@link IDao} y proporciona
 * las operaciones CRUD necesarias para crear, consultar, actualizar
 * y eliminar clientes en la base de datos MySQL.</p>
 *
 * <p>Sigue los principios SOLID delegando la persistencia de forma aislada
 * (SRP) y permitiendo sustitución polimórfica (LSP).</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class ClienteDao implements IDao<Cliente, Integer> {

    /**
     * Conexión inyectada utilizada para realizar las operaciones en la BD.
     */
    private final Connection conexion;

    /**
     * Constructor de ClienteDao.
     *
     * @param conexion conexión activa con la base de datos
     * @throws IllegalArgumentException si la conexión es nula
     */
    public ClienteDao(Connection conexion) {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexión a la base de datos no puede ser nula.");
        }
        this.conexion = conexion;
    }

    /**
     * Inserta un nuevo cliente en la base de datos.
     *
     * @param cliente cliente que se desea registrar
     * @throws RuntimeException si ocurre un error de comunicación SQL
     */
    @Override
    public void crear(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, telefono, direccion) VALUES (?, ?, ?)";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getTelefono());
            statement.setString(3, cliente.getDireccion());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar el cliente en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param cliente cliente que contiene los datos actualizados
     * @throws RuntimeException si ocurre un error de comunicación SQL
     */
    @Override
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre = ?, telefono = ?, direccion = ? WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getTelefono());
            statement.setString(3, cliente.getDireccion());
            statement.setInt(4, cliente.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el cliente en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un cliente de la base de datos utilizando su identificador.
     *
     * @param id identificador del cliente que se desea eliminar
     * @throws RuntimeException si ocurre un error de comunicación SQL
     */
    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el cliente de la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Busca un cliente utilizando su identificador único.
     *
     * @param id identificador del cliente
     * @return cliente encontrado o null si no existe
     * @throws RuntimeException si ocurre un error de comunicación SQL
     */
    @Override
    public Cliente obtenerPorId(Integer id) {
        String sql = "SELECT id, nombre, telefono, direccion FROM cliente WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return new Cliente(
                            resultado.getInt("id"),
                            resultado.getString("nombre"),
                            resultado.getString("telefono"),
                            resultado.getString("direccion")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar el cliente en la base de datos: " + e.getMessage(), e);
        }

        return null;
    }

    /**
     * Obtiene todos los clientes almacenados en la base de datos.
     *
     * @return lista con todos los clientes registrados
     * @throws RuntimeException si ocurre un error de comunicación SQL
     */
    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nombre, telefono, direccion FROM cliente ORDER BY id ASC";

        try (PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                Cliente cliente = new Cliente(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getString("telefono"),
                        resultado.getString("direccion")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los clientes desde la base de datos: " + e.getMessage(), e);
        }

        return clientes;
    }
}
