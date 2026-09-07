package com.empresa.vista;

import com.empresa.control.IControlCliente;
import com.empresa.control.IControlComida;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación de gestión de comidas rápidas.
 *
 * <p>Actúa como contenedor y gestor de navegación entre los módulos
 * del sistema (Clientes y Comidas Rápidas). Recibe las interfaces de
 * control para garantizar el bajo acoplamiento y la separación de responsabilidades
 * según el patrón MVC.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class VistaPrincipal extends JFrame {

    /**
     * Administrador de tarjetas para alternar entre paneles.
     */
    private final CardLayout cardLayout;

    /**
     * Panel contenedor principal que aloja las diferentes vistas.
     */
    private final JPanel panelContenedor;

    /**
     * Constructor de la ventana principal.
     *
     * @param controlCliente instancia que implementa {@link IControlCliente}
     * @param controlComida  instancia que implementa {@link IControlComida}
     */
    public VistaPrincipal(IControlCliente controlCliente, IControlComida controlComida) {
        super("Sistema de Gestión de Comidas Rápidas - FoodSystem");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(750, 500));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Vistas de cada módulo
        VistaCliente vistaCliente = new VistaCliente(controlCliente);
        VistaComida vistaComida = new VistaComida(controlComida);
        JPanel panelMenu = crearPanelMenu();

        // Envolver vistas con barra superior de navegación
        JPanel contenedorCliente = crearContenedorModulo("Gestión de Clientes", vistaCliente);
        JPanel contenedorComida = crearContenedorModulo("Gestión de Comidas Rápidas", vistaComida);

        panelContenedor.add(panelMenu, "menu");
        panelContenedor.add(contenedorCliente, "clientes");
        panelContenedor.add(contenedorComida, "comidas");

        configurarBarraMenu();

        add(panelContenedor);
        cardLayout.show(panelContenedor, "menu");
    }

    /**
     * Crea la barra de menú superior de la aplicación.
     */
    private void configurarBarraMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Navegación");
        JMenuItem itemMenu = new JMenuItem("Menú Principal");
        JMenuItem itemClientes = new JMenuItem("Módulo Clientes");
        JMenuItem itemComidas = new JMenuItem("Módulo Comidas Rápidas");
        JMenuItem itemSalir = new JMenuItem("Salir");

        itemMenu.addActionListener(e -> cardLayout.show(panelContenedor, "menu"));
        itemClientes.addActionListener(e -> cardLayout.show(panelContenedor, "clientes"));
        itemComidas.addActionListener(e -> cardLayout.show(panelContenedor, "comidas"));
        itemSalir.addActionListener(e -> System.exit(0));

        menuArchivo.add(itemMenu);
        menuArchivo.addSeparator();
        menuArchivo.add(itemClientes);
        menuArchivo.add(itemComidas);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        JMenu menuAyuda = new JMenu("Acerca de");
        JMenuItem itemCreditos = new JMenuItem("Información del Sistema");
        itemCreditos.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Sistema de Gestión de Comidas Rápidas\n" +
                "Arquitectura: MVC + DAO + Singleton\n" +
                "Taller de Métricas y Calidad de Software",
                "Acerca de FoodSystem",
                JOptionPane.INFORMATION_MESSAGE
        ));
        menuAyuda.add(itemCreditos);

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);
    }

    /**
     * Construye el panel inicial con los accesos directos principales.
     *
     * @return JPanel con el menú principal de bienvenida
     */
    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Sistema de Gestión de Comidas Rápidas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(33, 37, 41));

        JLabel lblSubtitulo = new JLabel("Seleccione una opción para gestionar la información:", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(108, 117, 125));

        JButton btnClientes = new JButton("Gestión de Clientes");
        btnClientes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnClientes.setPreferredSize(new Dimension(280, 45));

        JButton btnComidas = new JButton("Gestión de Comidas Rápidas");
        btnComidas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnComidas.setPreferredSize(new Dimension(280, 45));

        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnSalir.setPreferredSize(new Dimension(280, 35));

        btnClientes.addActionListener(e -> cardLayout.show(panelContenedor, "clientes"));
        btnComidas.addActionListener(e -> cardLayout.show(panelContenedor, "comidas"));
        btnSalir.addActionListener(e -> System.exit(0));

        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);
        gbc.gridy = 1;
        panel.add(lblSubtitulo, gbc);
        gbc.gridy = 2;
        panel.add(btnClientes, gbc);
        gbc.gridy = 3;
        panel.add(btnComidas, gbc);
        gbc.gridy = 4;
        panel.add(btnSalir, gbc);

        return panel;
    }

    /**
     * Envuelve un módulo dentro de un contenedor con encabezado y botón para regresar al menú.
     *
     * @param tituloModulo título del módulo
     * @param panelModulo  panel del módulo (VistaCliente o VistaComida)
     * @return JPanel organizado con botón de retorno
     */
    private JPanel crearContenedorModulo(String tituloModulo, JPanel panelModulo) {
        JPanel contenedor = new JPanel(new BorderLayout());

        JPanel barraNavegacion = new JPanel(new BorderLayout());
        barraNavegacion.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        barraNavegacion.setBackground(new Color(230, 235, 240));

        JButton btnVolver = new JButton("← Volver al Menú");
        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, "menu"));

        JLabel lblTitulo = new JLabel(tituloModulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));

        barraNavegacion.add(btnVolver, BorderLayout.WEST);
        barraNavegacion.add(lblTitulo, BorderLayout.CENTER);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        contenedor.add(barraNavegacion, BorderLayout.NORTH);
        contenedor.add(panelModulo, BorderLayout.CENTER);

        return contenedor;
    }
}