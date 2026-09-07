package com.empresa.launcher;

import com.empresa.control.ControlCliente;
import com.empresa.control.ControlComida;
import com.empresa.vista.VistaPrincipal;

import javax.swing.*;

/**
 * Punto de entrada de la aplicación.
 *
 * <p>Se limita a instanciar los controladores y lanzar la vista
 * principal. No contiene lógica de negocio ni de presentación.</p>
 *
 * @author Paula Martínez
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControlCliente controlCliente = new ControlCliente();
            ControlComida controlComida = new ControlComida();

            VistaPrincipal vista = new VistaPrincipal(controlCliente, controlComida);
            vista.setVisible(true);
        });
    }
}