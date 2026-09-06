package com.empresa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton que gestiona la conexion JDBC a MySQL.
 *
 * <p>Centraliza la configuracion de la base de datos en un unico punto.
 * Para cambiar credenciales, solo se modifica esta clase.</p>
 *
 * @author FoodSystem
 * @version 2.0
 */
public class ConexionDB {

    /** URL de conexion JDBC a MySQL. Ajustar puerto/schema si es necesario. */
    private static final String URL     = "jdbc:mysql://localhost:3306/foodsystem?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    /** Usuario de MySQL. */
    private static final String USUARIO = "root";

    /** Contrasena de MySQL. */
    private static final String CLAVE   = "root";

    /** Instancia unica de la conexion. */
    private static Connection conexion;

    /** Constructor privado — impide instanciacion externa. */
    private ConexionDB() {}

    /**
     * Retorna la conexion activa, creandola si no existe o esta cerrada.
     *
     * @return objeto {@link Connection} listo para usar
     * @throws SQLException si no se puede establecer la conexion
     */
    public static Connection obtenerConexion() throws SQLException {
        if (estaConexionInactiva()) {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
        }
        return conexion;
    }

    /**
     * Verifica si la conexion esta nula o cerrada.
     *
     * @return true si debe crearse una nueva conexion
     */
    private static boolean estaConexionInactiva() throws SQLException {
        return conexion == null || conexion.isClosed();
    }

    /**
     * Cierra la conexion activa liberando recursos del servidor MySQL.
     * Debe llamarse al finalizar la aplicacion.
     */
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexion a MySQL cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }
}