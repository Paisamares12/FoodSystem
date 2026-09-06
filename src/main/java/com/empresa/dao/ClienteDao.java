package com.empresa.dao;

import com.empresa.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementacion DAO para {@link Cliente} usando MySQL via JDBC.
 *
 * <p>Cada metodo abre su propio PreparedStatement con try-with-resources,
 * garantizando cierre automatico de recursos incluso ante excepciones.</p>
 *
 * @author FoodSystem
 * @version 2.0
 */
public class ClienteDao implements IDao<Cliente, Integer> {

    private static final String SQL_INSERTAR      = "INSERT INTO clientes (id, nombre, telefono, direccion) VALUES (?, ?, ?, ?)";
    private static final String SQL_BUSCAR_ID     = "SELECT * FROM clientes WHERE id = ?";
    private static final String SQL_OBTENER_TODOS = "SELECT * FROM clientes ORDER BY id";
    private static final String SQL_ACTUALIZAR    = "UPDATE clientes SET nombre=?, telefono=?, direccion=? WHERE id=?";
    private static final String SQL_ELIMINAR      = "DELETE FROM clientes WHERE id=?";
    private static final String SQL_EXISTE_ID     = "SELECT COUNT(*) FROM clientes WHERE id=?";

    /**
     * Inserta un nuevo cliente en la base de datos.
     * @param cliente datos del cliente a persistir
     */
    @Override
    public void agregar(Cliente cliente) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR)) {
            ps.setInt(1, cliente.getId());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getDireccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar cliente: " + e.getMessage());
        }
    }

    /**
     * Busca un cliente por su ID en la base de datos.
     * @param id identificador del cliente
     * @return Optional con el cliente si existe
     */
    @Override
    public Optional<Cliente> obtenerPorId(Integer id) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapearCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Retorna todos los clientes ordenados por ID.
     * @return lista de clientes
     */
    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_OBTENER_TODOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza nombre, telefono y direccion de un cliente existente.
     * @param cliente objeto con los nuevos datos (el ID identifica el registro)
     * @return true si se modifico al menos una fila
     */
    @Override
    public boolean actualizar(Cliente cliente) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getDireccion());
            ps.setInt(4, cliente.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina el cliente con el ID dado.
     * @param id identificador del cliente a borrar
     * @return true si se elimino al menos una fila
     */
    @Override
    public boolean eliminar(Integer id) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si ya existe un cliente con el ID dado.
     * @param id identificador a verificar
     * @return true si el ID ya esta en uso
     */
    public boolean existeId(int id) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_EXISTE_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Error al verificar ID: " + e.getMessage());
            return false;
        }
    }

    /**
     * Construye un objeto {@link Cliente} a partir de la fila actual del ResultSet.
     * @param rs ResultSet posicionado en la fila
     * @return instancia de Cliente mapeada
     * @throws SQLException si falla la lectura de alguna columna
     */
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("telefono"),
            rs.getString("direccion")
        );
    }
}