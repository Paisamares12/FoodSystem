package com.empresa.vista;

import com.empresa.control.IControlCliente;
import com.empresa.control.IControlComida;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Ventana principal de la aplicación de gestión de comidas rápidas.
 *
 * <p>Actúa como contenedor y gestor de navegación entre los módulos
 * del sistema (Clientes y Comidas Rápidas). Diseñada con una interfaz
 * visual atractiva, moderna, limpia, con navegación intuitiva y texto de alto contraste,
 * respetando el patrón MVC.</p>
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
     * Paleta de colores corporativos.
     */
    private static final Color COLOR_FONDO = new Color(245, 247, 250);
    private static final Color COLOR_HEADER = new Color(33, 47, 61);
    private static final Color COLOR_BORDE_CLIENTES = new Color(41, 128, 185);
    private static final Color COLOR_BORDE_COMIDAS = new Color(230, 126, 34);
    private static final Color COLOR_BORDE_SALIR = new Color(192, 57, 43);

    /**
     * Constructor de la ventana principal.
     *
     * @param controlCliente instancia que implementa {@link IControlCliente}
     * @param controlComida  instancia que implementa {@link IControlComida}
     */
    public VistaPrincipal(IControlCliente controlCliente, IControlComida controlComida) {
        super("FoodSystem - Sistema de Gestión de Comidas Rápidas");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setMinimumSize(new Dimension(780, 560));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Vistas de cada módulo
        VistaCliente vistaCliente = new VistaCliente(controlCliente);
        VistaComida vistaComida = new VistaComida(controlComida);
        JPanel panelMenu = crearPanelMenu();

        // Envolver vistas con barra superior de navegación
        JPanel contenedorCliente = crearContenedorModulo("Gestión de Clientes", vistaCliente, COLOR_BORDE_CLIENTES);
        JPanel contenedorComida = crearContenedorModulo("Gestión de Comidas Rápidas", vistaComida, COLOR_BORDE_COMIDAS);

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
        menuBar.setBackground(Color.WHITE);
        menuBar.setBorder(new LineBorder(new Color(220, 224, 230)));

        JMenu menuArchivo = new JMenu("Navegación");
        menuArchivo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

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
        menuAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JMenuItem itemCreditos = new JMenuItem("Información del Sistema");
        itemCreditos.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "FoodSystem - Sistema de Gestión de Comidas Rápidas\n" +
                "Versión 2.0\n\n" +
                "Arquitectura: MVC + DAO + Singleton\n" +
                "Desarrollado para el Taller de Métricas y Calidad de Software.",
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
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_FONDO);

        // Header decorativo superior
        JPanel panelBanner = new JPanel(new GridLayout(2, 1, 5, 5));
        panelBanner.setBackground(COLOR_HEADER);
        panelBanner.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel lblTitulo = new JLabel("FoodSystem", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Sistema Integral de Gestión de Clientes y Comidas Rápidas", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSubtitulo.setForeground(new Color(210, 215, 220));

        panelBanner.add(lblTitulo);
        panelBanner.add(lblSubtitulo);

        // Panel central con tarjetas/botones grandes
        JPanel panelCards = new JPanel(new GridBagLayout());
        panelCards.setBackground(COLOR_FONDO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton btnClientes = crearBotonMenu(
                "GESTIÓN DE CLIENTES",
                "Registrar, consultar, actualizar y dar de baja clientes",
                "#154360",
                COLOR_BORDE_CLIENTES
        );

        JButton btnComidas = crearBotonMenu(
                "GESTIÓN DE COMIDAS RÁPIDAS",
                "Administrar el catálogo de productos, precios e ingredientes",
                "#7E5109",
                COLOR_BORDE_COMIDAS
        );

        JButton btnSalir = crearBotonSimple("Salir del Sistema", new Color(146, 43, 33), COLOR_BORDE_SALIR);

        btnClientes.addActionListener(e -> cardLayout.show(panelContenedor, "clientes"));
        btnComidas.addActionListener(e -> cardLayout.show(panelContenedor, "comidas"));
        btnSalir.addActionListener(e -> System.exit(0));

        gbc.gridy = 0;
        panelCards.add(btnClientes, gbc);
        gbc.gridy = 1;
        panelCards.add(btnComidas, gbc);
        gbc.gridy = 2;
        panelCards.add(btnSalir, gbc);

        panelPrincipal.add(panelBanner, BorderLayout.NORTH);
        panelPrincipal.add(panelCards, BorderLayout.CENTER);

        return panelPrincipal;
    }

    /**
     * Crea un botón estilizado de gran tamaño con texto HTML oscuro y alto contraste.
     */
    private JButton crearBotonMenu(String titulo, String descripcion, String colorHexTitulo, Color colorBorde) {
        String contenidoHtml = "<html><center>" +
                "<b style='font-size:14px; color:" + colorHexTitulo + ";'>" + titulo + "</b><br>" +
                "<span style='font-size:11px; color:#2C3E50;'>" + descripcion + "</span>" +
                "</center></html>";

        JButton boton = new JButton(contenidoHtml);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(440, 68));
        boton.setBorder(new CompoundBorder(
                new LineBorder(colorBorde, 2, true),
                new EmptyBorder(10, 20, 10, 20)
        ));
        return boton;
    }

    /**
     * Crea un botón simple estilizado para el menú con texto de alto contraste.
     */
    private JButton crearBotonSimple(String texto, Color colorTexto, Color colorBorde) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(440, 42));
        boton.setBorder(new CompoundBorder(
                new LineBorder(colorBorde, 2, true),
                new EmptyBorder(8, 15, 8, 15)
        ));
        return boton;
    }

    /**
     * Envuelve un módulo dentro de un contenedor con encabezado estilizado y botón para regresar al menú.
     *
     * @param tituloModulo título del módulo
     * @param panelModulo  panel del módulo (VistaCliente o VistaComida)
     * @param colorTema    color distintivo del módulo
     * @return JPanel organizado con botón de retorno
     */
    private JPanel crearContenedorModulo(String tituloModulo, JPanel panelModulo, Color colorTema) {
        JPanel contenedor = new JPanel(new BorderLayout());

        JPanel barraNavegacion = new JPanel(new BorderLayout(15, 15));
        barraNavegacion.setBorder(new EmptyBorder(10, 15, 10, 15));
        barraNavegacion.setBackground(colorTema);

        JButton btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVolver.setForeground(new Color(21, 67, 96));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setBorder(new CompoundBorder(
                new LineBorder(new Color(21, 67, 96), 1, true),
                new EmptyBorder(6, 14, 6, 14)
        ));
        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, "menu"));

        JLabel lblTitulo = new JLabel(tituloModulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);

        // Panel auxiliar a la derecha para mantener el título centrado
        JPanel placeholder = new JPanel();
        placeholder.setOpaque(false);
        placeholder.setPreferredSize(btnVolver.getPreferredSize());

        barraNavegacion.add(btnVolver, BorderLayout.WEST);
        barraNavegacion.add(lblTitulo, BorderLayout.CENTER);
        barraNavegacion.add(placeholder, BorderLayout.EAST);

        contenedor.add(barraNavegacion, BorderLayout.NORTH);
        contenedor.add(panelModulo, BorderLayout.CENTER);

        return contenedor;
    }
}