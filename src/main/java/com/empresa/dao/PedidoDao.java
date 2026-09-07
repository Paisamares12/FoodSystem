package com.empresa.dao;

import com.empresa.modelo.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de gestionar las operaciones de acceso a datos
 * relacionadas con la entidad {@link Pedido}.
 *
 * <p>Esta clase implementa la interfaz {@link IDao} y proporciona
 * las operaciones CRUD necesarias para crear, consultar, actualizar
 * y eliminar pedidos en la base de datos.</p>
 *
 * <p>La clase utiliza JDBC para realizar las operaciones sobre
 * la tabla pedido.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class PedidoDao implements IDao<Pedido, Integer> {

    /**
     * Conexión utilizada para realizar las operaciones
     * con la base de datos.
     */
    private Connection con;

    /**
     * Constructor de PedidoDao.
     *
     * <p>Obtiene la conexión mediante la instancia única
     * proporcionada por {@link ConexionBD}.</p>
     */
    public PedidoDao() {
        this.con = ConexionBD.getInstancia().getConexion();
    }

    /**
     * Inserta un nuevo pedido en la base de datos.
     *
     * @param pedido pedido que se desea registrar
     */
    @Override
    public void crear(Pedido pedido) {

        String sql = "INSERT INTO pedido " +
                "(id_cliente, id_comida, cantidad, total) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pedido.getIdCliente());
            ps.setInt(2, pedido.getIdComida());
            ps.setInt(3, pedido.getCantidad());
            ps.setDouble(4, pedido.getTotal());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al crear pedido", e);
        }
    }

    /**
     * Actualiza los datos de un pedido existente.
     *
     * @param pedido pedido que contiene los datos actualizados
     */
    @Override
    public void actualizar(Pedido pedido) {

        String sql = "UPDATE pedido SET " +
                "id_cliente = ?, " +
                "id_comida = ?, " +
                "cantidad = ?, " +
                "total = ? " +
                "WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pedido.getIdCliente());
            ps.setInt(2, pedido.getIdComida());
            ps.setInt(3, pedido.getCantidad());
            ps.setDouble(4, pedido.getTotal());
            ps.setInt(5, pedido.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar pedido", e);
        }
    }

    /**
     * Elimina un pedido de la base de datos.
     *
     * @param id identificador del pedido que se desea eliminar
     */
    @Override
    public void eliminar(Integer id) {

        String sql = "DELETE FROM pedido WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar pedido", e);
        }
    }

    /**
     * Busca un pedido utilizando su identificador.
     *
     * @param id identificador del pedido
     * @return pedido encontrado o null si no existe
     */
    @Override
    public Pedido obtenerPorId(Integer id) {

        String sql = "SELECT id, id_cliente, id_comida, " +
                "cantidad, total " +
                "FROM pedido WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Pedido(
                            rs.getInt("id"),
                            rs.getInt("id_cliente"),
                            rs.getInt("id_comida"),
                            rs.getInt("cantidad"),
                            rs.getDouble("total")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al obtener pedido", e);
        }

        return null;
    }

    /**
     * Obtiene todos los pedidos almacenados en la base de datos.
     *
     * @return lista con todos los pedidos registrados
     */
    @Override
    public List<Pedido> listarTodos() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT id, id_cliente, id_comida, " +
                "cantidad, total FROM pedido";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comida"),
                        rs.getInt("cantidad"),
                        rs.getDouble("total")
                );

                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar pedidos", e);
        }

        return pedidos;
    }
}