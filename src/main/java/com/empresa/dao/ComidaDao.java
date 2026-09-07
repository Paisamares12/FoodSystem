package com.empresa.dao;

import com.empresa.modelo.Comida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de gestionar las operaciones de acceso a datos
 * relacionadas con la entidad {@link Comida}.
 *
 * <p>Esta clase implementa la interfaz {@link IDao} y proporciona
 * las operaciones CRUD necesarias para crear, consultar, actualizar
 * y eliminar comidas en la base de datos.</p>
 *
 * <p>La clase utiliza JDBC para establecer la comunicación con
 * la base de datos.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class ComidaDao implements IDao<Comida, Integer> {

    /**
     * Conexión utilizada para realizar las operaciones
     * con la base de datos.
     */
    private Connection conexion;

    /**
     * Constructor de ComidaDao.
     *
     * @param conexion conexión activa con la base de datos
     */
    public ComidaDao(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Inserta una nueva comida en la base de datos.
     *
     * @param comida comida que se desea registrar
     */
    @Override
    public void crear(Comida comida) {
        String sql = "INSERT INTO comida " +
                "(nombre, ingredientes, precio) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, comida.getNombre());
            statement.setString(2, comida.getIngredientes());
            statement.setDouble(3, comida.getPrecio());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los datos de una comida existente.
     *
     * @param comida comida que contiene los datos actualizados
     */
    @Override
    public void actualizar(Comida comida) {
        String sql = "UPDATE comida SET " +
                "nombre = ?, ingredientes = ?, precio = ? " +
                "WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, comida.getNombre());
            statement.setString(2, comida.getIngredientes());
            statement.setDouble(3, comida.getPrecio());
            statement.setInt(4, comida.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una comida de la base de datos.
     *
     * @param id identificador de la comida que se desea eliminar
     */
    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM comida WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca una comida utilizando su identificador.
     *
     * @param id identificador de la comida
     * @return comida encontrada o null si no existe
     */
    @Override
    public Comida obtenerPorId(Integer id) {
        String sql = "SELECT id, nombre, ingredientes, precio " +
                "FROM comida WHERE id = ?";

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
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtiene todas las comidas almacenadas en la base de datos.
     *
     * @return lista con todas las comidas registradas
     */
    @Override
    public List<Comida> listarTodos() {
        List<Comida> comidas = new ArrayList<>();

        String sql = "SELECT id, nombre, ingredientes, precio " +
                "FROM comida";

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
            e.printStackTrace();
        }

        return comidas;
    }
}
