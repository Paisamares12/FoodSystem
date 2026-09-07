package com.empresa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión con la base de datos.
 *
 * <p>Esta clase implementa el patrón de diseño Singleton,
 * garantizando que exista una única instancia de ConexionDB
 * durante la ejecución de la aplicación.</p>
 *
 * <p>La conexión se establece mediante JDBC utilizando
 * un servidor MySQL.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class ConexionBD {

    /**
     * Única instancia de la clase ConexionDB.
     */
    private static ConexionBD instancia;

    /**
     * Conexión activa con la base de datos.
     */
    private Connection conexion;

    /**
     * URL de conexión a la base de datos.
     */
    private final String url =
            "jdbc:mysql://localhost:3306/comidas_rapidas";

    /**
     * Usuario de la base de datos.
     */
    private final String user = "root";

    /**
     * Contraseña de la base de datos.
     */
    private final String pass = "password";

    /**
     * Constructor privado.
     *
     * <p>Al ser privado, evita que otras clases puedan crear
     * directamente objetos de tipo ConexionDB. La instancia
     * debe obtenerse mediante {@link #getInstancia()}.</p>
     */
    private ConexionBD() {

        try {
            conexion = DriverManager.getConnection(
                    url,
                    user,
                    pass
            );

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al conectar a la base de datos", e
            );
        }
    }

    /**
     * Obtiene la única instancia de ConexionDB.
     *
     * <p>Si todavía no existe una instancia, se crea.
     * Si ya existe, se devuelve la instancia existente.</p>
     *
     * @return instancia única de ConexionDB
     */
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /**
     * Obtiene la conexión activa con la base de datos.
     *
     * @return conexión JDBC con la base de datos
     */
    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(url, user, pass);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar/reconectar la BD", e);
        }
        return conexion;
    }
}