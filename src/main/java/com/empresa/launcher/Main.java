package com.empresa.launcher;

import com.empresa.control.ControlCliente;
import com.empresa.control.ControlComida;
import com.empresa.control.IControlCliente;
import com.empresa.control.IControlComida;
import com.empresa.vista.VistaPrincipal;

import javax.swing.*;

/**
 * Punto de entrada principal de la aplicación FoodSystem.
 *
 * <p>Esta clase se limita exclusivamente a inicializar los componentes del sistema,
 * instanciando los controladores a través de sus interfaces correspondientes y
 * desplegando la ventana principal en el hilo de despacho de eventos de Swing (EDT).</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class Main {

    /**
     * Método principal de ejecución de la aplicación.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Establecer el Look & Feel del sistema operativo para mejor apariencia visual
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Si falla, utiliza el Look & Feel por defecto de Java (Metal)
        }

        SwingUtilities.invokeLater(() -> {
            IControlCliente controlCliente = new ControlCliente();
            IControlComida controlComida = new ControlComida();

            VistaPrincipal vista = new VistaPrincipal(controlCliente, controlComida);
            vista.setVisible(true);
        });
    }
}