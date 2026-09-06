package com.empresa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión entre la aplicación y la base de datos.
 *
 * <p>Esta clase proporciona un método para establecer una conexión
 * mediante JDBC y centraliza los datos necesarios para conectarse
 * al servidor de base de datos.</p>
 *
 * <p>En este proyecto se utiliza MySQL como sistema gestor de
 * bases de datos.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class ConexionBD {

    /**
     * Dirección de conexión a la base de datos.
     */
    private static final String URL =
            "jdbc:mysql://localhost:3306/empresa";

    /**
     * Usuario utilizado para acceder a la base de datos.
     */
    private static final String USUARIO = "root";

    /**
     * Contraseña utilizada para acceder a la base de datos.
     */
    private static final String PASSWORD = "";

    /**
     * Constructor privado para evitar la creación de objetos
     * innecesarios de esta clase.
     */
    private ConexionBD() {
    }

    /**
     * Establece una conexión con la base de datos.
     *
     * @return objeto Connection con la conexión establecida
     * @throws SQLException si ocurre un error al establecer
     *         la conexión con la base de datos
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }
}
