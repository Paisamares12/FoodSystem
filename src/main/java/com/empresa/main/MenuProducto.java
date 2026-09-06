package com.empresa.main;

import com.empresa.dao.ComidaRapidaDao;
import com.empresa.models.ComidaRapida;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controlador de consola para el CRUD de {@link ComidaRapida}.
 * Metodos cortos para mantener baja complejidad ciclomatica.
 * @author Paula Martínez
 * @version 1.0
 */
public class MenuProducto {

    private final ComidaRapidaDao productoDao;
    private final Scanner scanner;

    /**
     * @param productoDao repositorio de productos
     * @param scanner     lector de entrada estandar
     */
    public MenuProducto(ComidaRapidaDao productoDao, Scanner scanner) {
        this.productoDao = productoDao;
        this.scanner     = scanner;
    }

    /** Muestra el submenu de productos y despacha la opcion elegida. */
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
        System.out.println("\n===== MENU PRODUCTOS =====");
        System.out.println("1. Agregar producto");
        System.out.println("2. Ver todos los productos");
        System.out.println("3. Buscar producto por ID");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("0. Volver al menu principal");
    }

    /**
     * Despacha la opcion seleccionada.
     * @param opcion numero elegido
     * @return false si el usuario quiere volver
     */
    private boolean despacharOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarProducto();
            case 2 -> listarProductos();
            case 3 -> buscarProducto();
            case 4 -> actualizarProducto();
            case 5 -> eliminarProducto();
            case 0 -> { return false; }
            default -> System.out.println("Opcion no valida.");
        }
        return true;
    }

    /** Solicita datos y agrega un nuevo producto. */
    private void agregarProducto() {
        System.out.println("\n-- Agregar Producto --");
        int id = leerEntero("ID: ");
        if (productoDao.existeId(id)) {
            System.out.println("Ya existe un producto con ese ID.");
            return;
        }
        String nombre       = leerTexto("Nombre: ");
        String ingredientes = leerTexto("Ingredientes: ");
        double precio       = leerDecimal("Precio: ");
        productoDao.agregar(new ComidaRapida(id, nombre, ingredientes, precio));
    }

    /** Lista todos los productos registrados. */
    private void listarProductos() {
        System.out.println("\n-- Listado de Productos --");
        List<ComidaRapida> productos = productoDao.obtenerTodos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        productos.forEach(System.out::println);
    }

    /** Busca un producto por su ID. */
    private void buscarProducto() {
        System.out.println("\n-- Buscar Producto --");
        int id = leerEntero("ID del producto: ");
        Optional<ComidaRapida> resultado = productoDao.obtenerPorId(id);
        resultado.ifPresentOrElse(
            System.out::println,
            () -> System.out.println("Producto no encontrado.")
        );
    }

    /** Actualiza los datos de un producto existente. */
    private void actualizarProducto() {
        System.out.println("\n-- Actualizar Producto --");
        int id = leerEntero("ID del producto a actualizar: ");
        if (!productoDao.existeId(id)) {
            System.out.println("Producto no encontrado.");
            return;
        }
        String nombre       = leerTexto("Nuevo nombre: ");
        String ingredientes = leerTexto("Nuevos ingredientes: ");
        double precio       = leerDecimal("Nuevo precio: ");
        boolean exito = productoDao.actualizar(new ComidaRapida(id, nombre, ingredientes, precio));
        mostrarResultado(exito, "Producto actualizado.", "No se pudo actualizar.");
    }

    /** Elimina un producto por su ID. */
    private void eliminarProducto() {
        System.out.println("\n-- Eliminar Producto --");
        int id = leerEntero("ID del producto a eliminar: ");
        boolean exito = productoDao.eliminar(id);
        mostrarResultado(exito, "Producto eliminado.", "Producto no encontrado.");
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
     * Lee un decimal del flujo de entrada.
     * @param mensaje prompt al usuario
     * @return decimal ingresado
     */
    private double leerDecimal(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextDouble()) {
            System.out.print("Ingrese un valor decimal valido: ");
            scanner.next();
        }
        double valor = scanner.nextDouble();
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
     * @param exito    resultado
     * @param msgExito mensaje exitoso
     * @param msgFallo mensaje fallido
     */
    private void mostrarResultado(boolean exito, String msgExito, String msgFallo) {
        System.out.println(exito ? msgExito : msgFallo);
    }
}