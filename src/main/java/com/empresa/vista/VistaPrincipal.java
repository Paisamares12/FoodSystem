package com.empresa.vista;

import com.empresa.control.ControlCliente;
import com.empresa.control.ControlComida;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación.
 *
 * <p>Actúa únicamente como contenedor de navegación entre los
 * paneles de gestión. No contiene lógica de negocio: toda
 * operación se delega a los controladores.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class VistaPrincipal extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel panelContenedor;

    public VistaPrincipal(ControlCliente controlCliente, ControlComida controlComida) {
        super("Gestión de Comidas Rápidas");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Paneles de cada módulo (reciben el control, no el DAO)
        VistaCliente vistaCliente = new VistaCliente(controlCliente);
        VistaComida vistaComida = new VistaComida(controlComida);
        JPanel panelMenu = crearPanelMenu();

        panelContenedor.add(panelMenu, "menu");
        panelContenedor.add(vistaCliente, "clientes");
        panelContenedor.add(vistaComida, "comidas");

        add(panelContenedor);
        cardLayout.show(panelContenedor, "menu");
    }

    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JButton btnClientes = new JButton("Gestionar Clientes");
        JButton btnComidas = new JButton("Gestionar Comidas Rápidas");
        JButton btnSalir = new JButton("Salir");

        btnClientes.addActionListener(e -> cardLayout.show(panelContenedor, "clientes"));
        btnComidas.addActionListener(e -> cardLayout.show(panelContenedor, "comidas"));
        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(btnClientes);
        panel.add(btnComidas);
        panel.add(btnSalir);

        return panel;
    }
}