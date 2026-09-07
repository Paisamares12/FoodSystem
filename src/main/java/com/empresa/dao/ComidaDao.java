package com.empresa.dao;

import com.empresa.modelo.Comida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de gestionar las operaciones de acceso a datos
 * relacionadas con la entidad {@link Comida}.
 *
 * <p>Esta clase implementa la interfaz {@link IDao} y proporciona
 * las operaciones CRUD necesarias para crear, consultar, actualizar
 * y eliminar productos de comida rápida en la base de datos MySQL.</p>
 *
 * <p>Sigue los principios SOLID delegando la persistencia de forma aislada
 * (SRP) y permitiendo sustitución polimórfica (LSP).</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class ComidaDao implements IDao<Comida, Integer> {

    /**
     * Conexión inyectada utilizada para realizar las operaciones en la BD.
     */
    private final Connection conexion;

    /**
     * Constructor de ComidaDao.
     *
     * @param conexion conexión activa con la base de datos
     * @throws IllegalArgumentException si la conexión es nula
     */
    public ComidaDao(Connection conexion) {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexión a la base de datos no puede ser nula.");
        }
        this.conexion = conexion;
    }

    /**
     * Inserta una nueva comida en la base de datos.
     *
     * @param comida comida que se desea registrar
     * @throws RuntimeException si ocurre un error de comunicación SQL
     */
    @Override
    public void crear(Comida comida) {
        String sql = "INSERT INTO comida (nombre, ingredientes, precio) VALUES (?, ?, ?)";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, comida.getNombre());
            statement.setString(2, comida.getIngredientes());
            statement.setDouble(3, comida.getPrecio());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar la comida en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza los datos de una comida existente.
     *
     * @param comida comida que contiene los datos actualizados
     * @throws RuntimeException si ocurre un error de comunicación SQL
     */
    @Override
    public void actualizar(Comida comida) {
        String sql = "UPDATE comida SET nombre = ?, ingredientes = ?, precio = ? WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, comida.getNombre());
            statement.setString(2, comida.getIngredientes());
            statement.setDouble(3, comida.getPrecio());
            statement.setInt(4, comida.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la comida en la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina una comida de la base de datos.
     *
     * @param id identificador de la comida que se desea eliminar
     * @throws RuntimeException si ocurre un error de comunicación SQL
     */
    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM comida WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la comida de la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Busca una comida utilizando su identificador único.
     *
     * @param id identificador de la comida
     * @return comida encontrada o null si no existe
     * @throws RuntimeException si ocurre un error de comunicación SQL
     */
    @Override
    public Comida obtenerPorId(Integer id) {
        String sql = "SELECT id, nombre, ingredientes, precio FROM comida WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return new Comida(
                            resultado.getInt("id"),
                            resultado.getString("nombre"),
                            resultado.getString("ingredientes"),
                            resultado.getDouble("precio")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar la comida en la base de datos: " + e.getMessage(), e);
        }

        return null;
    }

    /**
     * Obtiene todas las comidas almacenadas en la base de datos.
     *
     * @return lista con todas las comidas registradas
     * @throws RuntimeException si ocurre un error de comunicación SQL
     */
    @Override
    public List<Comida> listarTodos() {
        List<Comida> comidas = new ArrayList<>();
        String sql = "SELECT id, nombre, ingredientes, precio FROM comida ORDER BY id ASC";

        try (PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                Comida comida = new Comida(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getString("ingredientes"),
                        resultado.getDouble("precio")
                );
                comidas.add(comida);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las comidas desde la base de datos: " + e.getMessage(), e);
        }

        return comidas;
    }
}
