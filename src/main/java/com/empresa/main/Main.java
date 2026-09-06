package com.empresa.main;

import com.empresa.dao.ClienteDao;
import com.empresa.dao.ComidaRapidaDao;

import java.util.Scanner;

/**
 * Punto de entrada principal del sistema de gestion de comidas rapidas.
 *
 * <p>Inicializa los DAOs, crea los menus y gestiona el ciclo principal.
 * Delega cada responsabilidad a su modulo correspondiente.</p>
 *
 * @author Paula Martínez
 * @version 1.0
 */
public class Main {

    /**
     * Metodo principal de la aplicacion.
     * @param args argumentos de linea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Scanner         scanner     = new Scanner(System.in);
        ClienteDao      clienteDao  = new ClienteDao();
        ComidaRapidaDao productoDao = new ComidaRapidaDao();

        MenuCliente  menuCliente  = new MenuCliente(clienteDao, scanner);
        MenuProducto menuProducto = new MenuProducto(productoDao, scanner);

        ejecutarCicloPrincipal(menuCliente, menuProducto, scanner);

        scanner.close();
        System.out.println("Sistema cerrado. Hasta luego!");
    }

    /**
     * Ciclo principal que muestra el menu raiz y enruta al submenu elegido.
     * @param menuCliente  manejador del submenu de clientes
     * @param menuProducto manejador del submenu de productos
     * @param scanner      lector de entrada estandar
     */
    private static void ejecutarCicloPrincipal(
            MenuCliente menuCliente,
            MenuProducto menuProducto,
            Scanner scanner) {

        boolean continuar = true;
        while (continuar) {
            imprimirMenuPrincipal();
            int opcion = leerOpcion(scanner);
            continuar = despacharOpcionPrincipal(opcion, menuCliente, menuProducto);
        }
    }

    /** Imprime las opciones del menu principal. */
    private static void imprimirMenuPrincipal() {
        System.out.println("\n================================");
        System.out.println("  SISTEMA DE COMIDAS RAPIDAS");
        System.out.println("================================");
        System.out.println("1. Gestion de Clientes");
        System.out.println("2. Gestion de Productos");
        System.out.println("0. Salir");
        System.out.println("================================");
    }

    /**
     * Lee un numero entero del flujo de entrada.
     * @param scanner lector de entrada
     * @return entero ingresado
     */
    private static int leerOpcion(Scanner scanner) {
        System.out.print("Seleccione una opcion: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un numero valido: ");
            scanner.next();
        }
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    /**
     * Despacha la opcion del menu principal.
     * @param opcion       opcion elegida
     * @param menuCliente  manejador de clientes
     * @param menuProducto manejador de productos
     * @return false si el usuario elige salir
     */
    private static boolean despacharOpcionPrincipal(
            int opcion,
            MenuCliente menuCliente,
            MenuProducto menuProducto) {

        switch (opcion) {
            case 1 -> menuCliente.mostrarMenu();
            case 2 -> menuProducto.mostrarMenu();
            case 0 -> { return false; }
            default -> System.out.println("Opcion no valida. Intente nuevamente.");
        }
        return true;
    }
}