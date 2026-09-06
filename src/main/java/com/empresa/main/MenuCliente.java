package com.empresa.main;

import com.empresa.dao.ClienteDao;
import com.empresa.modelo.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controlador de consola para el CRUD de {@link Cliente}.
 * Metodos cortos para mantener baja complejidad ciclomatica.
 * @author Paula Martínez
 * @version 1.0
 */
public class MenuCliente {

    private final ClienteDao clienteDao;
    private final Scanner scanner;

    /**
     * @param clienteDao repositorio de clientes
     * @param scanner    lector de entrada estandar
     */
    public MenuCliente(ClienteDao clienteDao, Scanner scanner) {
        this.clienteDao = clienteDao;
        this.scanner    = scanner;
    }

    /** Muestra el submenu de clientes y despacha la opcion elegida. */
    public void mostrarMenu() {
        boolean continuar = true;
        while (continuar) {
            imprimirOpciones();
            int opcion = leerEntero("Opcion: ");
            continuar = despacharOpcion(opcion);
        }
    }

    /** Imprime las opciones del submenu. */
    private void imprimirOpciones() {
        System.out.println("\n===== MENU CLIENTES =====");
        System.out.println("1. Agregar cliente");
        System.out.println("2. Ver todos los clientes");
        System.out.println("3. Buscar cliente por ID");
        System.out.println("4. Actualizar cliente");
        System.out.println("5. Eliminar cliente");
        System.out.println("0. Volver al menu principal");
    }

    /**
     * Despacha la opcion seleccionada.
     * @param opcion numero elegido
     * @return false si el usuario quiere volver
     */
    private boolean despacharOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarCliente();
            case 2 -> listarClientes();
            case 3 -> buscarCliente();
            case 4 -> actualizarCliente();
            case 5 -> eliminarCliente();
            case 0 -> { return false; }
            default -> System.out.println("Opcion no valida.");
        }
        return true;
    }

    /** Solicita datos y agrega un nuevo cliente. */
    private void agregarCliente() {
        System.out.println("\n-- Agregar Cliente --");
        int id = leerEntero("ID: ");
        if (clienteDao.existeId(id)) {
            System.out.println("Ya existe un cliente con ese ID.");
            return;
        }
        String nombre    = leerTexto("Nombre: ");
        String telefono  = leerTexto("Telefono: ");
        String direccion = leerTexto("Direccion: ");
        clienteDao.agregar(new Cliente(id, nombre, telefono, direccion));
    }

    /** Lista todos los clientes registrados. */
    private void listarClientes() {
        System.out.println("\n-- Listado de Clientes --");
        List<Cliente> clientes = clienteDao.obtenerTodos();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        clientes.forEach(System.out::println);
    }

    /** Busca un cliente por su ID. */
    private void buscarCliente() {
        System.out.println("\n-- Buscar Cliente --");
        int id = leerEntero("ID del cliente: ");
        Optional<Cliente> resultado = clienteDao.obtenerPorId(id);
        resultado.ifPresentOrElse(
            System.out::println,
            () -> System.out.println("Cliente no encontrado.")
        );
    }

    /** Actualiza los datos de un cliente existente. */
    private void actualizarCliente() {
        System.out.println("\n-- Actualizar Cliente --");
        int id = leerEntero("ID del cliente a actualizar: ");
        if (!clienteDao.existeId(id)) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        String nombre    = leerTexto("Nuevo nombre: ");
        String telefono  = leerTexto("Nuevo telefono: ");
        String direccion = leerTexto("Nueva direccion: ");
        boolean exito = clienteDao.actualizar(new Cliente(id, nombre, telefono, direccion));
        mostrarResultado(exito, "Cliente actualizado.", "No se pudo actualizar.");
    }

    /** Elimina un cliente por su ID. */
    private void eliminarCliente() {
        System.out.println("\n-- Eliminar Cliente --");
        int id = leerEntero("ID del cliente a eliminar: ");
        boolean exito = clienteDao.eliminar(id);
        mostrarResultado(exito, "Cliente eliminado.", "Cliente no encontrado.");
    }

    /**
     * Lee un entero del flujo de entrada.
     * @param mensaje prompt al usuario
     * @return entero ingresado
     */
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un numero valido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    /**
     * Lee una cadena de texto del flujo de entrada.
     * @param mensaje prompt al usuario
     * @return texto ingresado
     */
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /**
     * Muestra mensaje de exito o fallo.
     * @param exito        resultado
     * @param msgExito     mensaje exitoso
     * @param msgFallo     mensaje fallido
     */
    private void mostrarResultado(boolean exito, String msgExito, String msgFallo) {
        System.out.println(exito ? msgExito : msgFallo);
    }
}