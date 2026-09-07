package com.empresa.control;

import com.empresa.modelo.Cliente;
import java.util.List;

/**
 * Interfaz que define el contrato para la lógica de negocio y operaciones
 * relacionadas con la entidad {@link Cliente}.
 *
 * <p>Permite desacoplar la capa de presentación (Vista) de la implementación
 * concreta del controlador, facilitando el mantenimiento y cumplimiento
 * del principio de inversión de dependencias (DIP).</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public interface IControlCliente {

    /**
     * Registra un nuevo cliente tras validar sus datos de entrada.
     *
     * @param nombre    nombre completo del cliente
     * @param telefono  número de teléfono de contacto
     * @param direccion dirección de residencia o entrega
     * @throws IllegalArgumentException si algún campo obligatorio no cumple las validaciones
     */
    void registrarCliente(String nombre, String telefono, String direccion);

    /**
     * Actualiza los datos de un cliente existente tras validar sus datos.
     *
     * @param id        identificador único del cliente a actualizar
     * @param nombre    nuevo nombre del cliente
     * @param telefono  nuevo teléfono de contacto
     * @param direccion nueva dirección del cliente
     * @throws IllegalArgumentException si los datos no son válidos o el ID no es correcto
     */
    void actualizarCliente(int id, String nombre, String telefono, String direccion);

    /**
     * Elimina un cliente del sistema a partir de su identificador.
     *
     * @param id identificador único del cliente a eliminar
     */
    void eliminarCliente(int id);

    /**
     * Busca y obtiene un cliente según su identificador único.
     *
     * @param id identificador del cliente
     * @return objeto {@link Cliente} correspondiente, o null si no se encuentra
     */
    Cliente obtenerCliente(int id);

    /**
     * Obtiene la lista completa de clientes registrados en el sistema.
     *
     * @return lista de objetos {@link Cliente}
     */
    List<Cliente> listarClientes();
}
