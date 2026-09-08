package com.empresa.vista;

import com.empresa.control.IControlComida;
import com.empresa.modelo.Comida;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * Panel de interfaz gráfica encargado de la gestión (CRUD) de comidas rápidas.
 *
 * <p>Esta vista se comunica exclusivamente con la capa de control a través de
 * la interfaz {@link IControlComida}. Captura la entrada del usuario, delega las
 * operaciones al controlador y presenta los productos y mensajes con una interfaz
 * visual estilizada, limpia, moderna y con cabeceras de tabla de alta legibilidad.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class VistaComida extends JPanel {

    /**
     * Referencia a la interfaz del controlador de comidas rápidas.
     */
    private final IControlComida controlComida;

    /**
     * Campos de texto del formulario.
     */
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtIngredientes;
    private JTextField txtPrecio;

    /**
     * Componentes de la tabla de visualización.
     */
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Paleta de colores con texto oscuro de alto contraste.
     */
    private static final Color COLOR_FONDO = new Color(248, 249, 250);
    private static final Color COLOR_CARD = Color.WHITE;
    private static final Color COLOR_TEXTO = new Color(33, 37, 41);         // Texto oscuro nítido
    private static final Color COLOR_TITULO = new Color(175, 96, 26);       // Naranja oscuro corporativo
    private static final Color COLOR_TEXTO_VERDE = new Color(20, 90, 50);   // Verde oscuro para Guardar
    private static final Color COLOR_TEXTO_AZUL = new Color(21, 67, 96);    // Azul oscuro para Actualizar
    private static final Color COLOR_TEXTO_ROJO = new Color(146, 43, 33);   // Rojo oscuro para Eliminar
    private static final Color COLOR_TEXTO_GRIS = new Color(44, 62, 80);    // Gris oscuro para Limpiar

    /**
     * Constructor de la vista de comidas rápidas.
     *
     * @param controlComida instancia del controlador que implementa {@link IControlComida}
     */
    public VistaComida(IControlComida controlComida) {
        this.controlComida = controlComida;
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_FONDO);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        construirFormulario();
        construirTabla();
        cargarComidas();
    }

    /**
     * Construye y organiza el panel superior con el formulario de entrada y los botones de acción.
     */
    private void construirFormulario() {
        JPanel cardFormulario = new JPanel(new BorderLayout(10, 10));
        cardFormulario.setBackground(COLOR_CARD);
        cardFormulario.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 205, 215), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Título del formulario
        JLabel lblTituloSeccion = new JLabel("Datos del Producto de Comida Rápida");
        lblTituloSeccion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTituloSeccion.setForeground(COLOR_TITULO);
        lblTituloSeccion.setBorder(new EmptyBorder(0, 0, 10, 0));
        cardFormulario.add(lblTituloSeccion, BorderLayout.NORTH);

        // Grid del Formulario
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBackground(COLOR_CARD);

        txtId = crearCampoTexto();
        txtId.setEditable(false);
        txtId.setBackground(new Color(245, 245, 245));
        txtId.setToolTipText("Generado automáticamente por el sistema");

        txtNombre = crearCampoTexto();
        txtIngredientes = crearCampoTexto();
        txtPrecio = crearCampoTexto();

        form.add(crearEtiqueta("ID (Automático):"));
        form.add(txtId);
        form.add(crearEtiqueta("Nombre de la Comida:"));
        form.add(txtNombre);
        form.add(crearEtiqueta("Ingredientes / Descripción:"));
        form.add(txtIngredientes);
        form.add(crearEtiqueta("Precio de Venta ($):"));
        form.add(txtPrecio);

        // Botones de acción con texto oscuro en negrita y bordes de color para máxima visibilidad
        JButton btnGuardar = crearBoton("Guardar", COLOR_TEXTO_VERDE, new Color(39, 174, 96));
        JButton btnActualizar = crearBoton("Actualizar", COLOR_TEXTO_AZUL, new Color(41, 128, 185));
        JButton btnEliminar = crearBoton("Eliminar", COLOR_TEXTO_ROJO, new Color(192, 57, 43));
        JButton btnLimpiar = crearBoton("Limpiar", COLOR_TEXTO_GRIS, new Color(127, 140, 141));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 12));
        panelBotones.setBackground(COLOR_CARD);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        cardFormulario.add(form, BorderLayout.CENTER);
        cardFormulario.add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarComida());
        btnActualizar.addActionListener(e -> actualizarComida());
        btnEliminar.addActionListener(e -> eliminarComida());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        add(cardFormulario, BorderLayout.NORTH);
    }

    /**
     * Construye la tabla donde se muestran las comidas registradas y agrega
     * el evento de selección para autocompletar el formulario al hacer clic en una fila.
     */
    private void construirTabla() {
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Ingredientes", "Precio ($)"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setForeground(COLOR_TEXTO);
        tabla.setSelectionBackground(new Color(254, 237, 222));
        tabla.setSelectionForeground(new Color(110, 44, 0));
        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(230, 233, 236));

        // Centrar columna de ID en las celdas
        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centerCellRenderer);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(60);

        // Alinear a la derecha la columna de Precio en las celdas
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tabla.getColumnModel().getColumn(3).setCellRenderer(rightCellRenderer);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(90);

        // Renderizador de cabecera con texto oscuro, fondo suave y bordes nítidos
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setForeground(new Color(175, 96, 26)); // Naranja/marrón oscuro nítido
                label.setBackground(new Color(254, 243, 230)); // Fondo cálido suave
                label.setHorizontalAlignment(column == 0 ? JLabel.CENTER : (column == 3 ? JLabel.RIGHT : JLabel.LEFT));
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 1, new Color(220, 205, 190)),
                        BorderFactory.createEmptyBorder(6, 8, 6, 8)
                ));
                return label;
            }
        };

        for (int i = 0; i < tabla.getColumnModel().getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        JTableHeader header = tabla.getTableHeader();
        header.setPreferredSize(new Dimension(0, 34));
        header.setReorderingAllowed(false);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtIngredientes.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtPrecio.setText(modeloTabla.getValueAt(fila, 3).toString());
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 205, 215), 1, true),
                BorderFactory.createTitledBorder(
                        null, "Menú y Catálogo de Comidas Rápidas",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        COLOR_TITULO
                )
        ));

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Crea una etiqueta con tipografía limpia y color oscuro de alta legibilidad.
     */
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(COLOR_TEXTO);
        return label;
    }

    /**
     * Crea un campo de texto con estilos modernos y padding interior.
     */
    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setForeground(COLOR_TEXTO);
        campo.setBorder(new CompoundBorder(
                new LineBorder(new Color(180, 185, 195), 1, true),
                new EmptyBorder(6, 8, 6, 8)
        ));
        return campo;
    }

    /**
     * Crea un botón estilizado con texto oscuro en negrita y borde de color para máxima legibilidad.
     */
    private JButton crearBoton(String texto, Color colorTexto, Color colorBorde) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(120, 36));
        boton.setBorder(new CompoundBorder(
                new LineBorder(colorBorde, 2, true),
                new EmptyBorder(6, 12, 6, 12)
        ));
        return boton;
    }

    /**
     * Procesa la acción de guardar una nueva comida rápida delegando al controlador.
     */
    private void guardarComida() {
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            controlComida.registrarComida(
                    txtNombre.getText(),
                    txtIngredientes.getText(),
                    precio
            );
            limpiarCampos();
            cargarComidas();
            mostrarMensaje("Producto de comida rápida registrado con éxito.");
        } catch (NumberFormatException ex) {
            mostrarError("El precio debe ser un número válido (ej. 15000 o 15000.50).");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        } catch (Exception ex) {
            mostrarError("Error inesperado al guardar la comida: " + ex.getMessage());
        }
    }

    /**
     * Procesa la acción de actualizar los datos de una comida seleccionada.
     */
    private void actualizarComida() {
        if (txtId.getText().isBlank()) {
            mostrarError("Seleccione una comida de la tabla para actualizar.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            controlComida.actualizarComida(
                    id,
                    txtNombre.getText(),
                    txtIngredientes.getText(),
                    precio
            );
            limpiarCampos();
            cargarComidas();
            mostrarMensaje("Producto de comida rápida actualizado con éxito.");
        } catch (NumberFormatException ex) {
            mostrarError("Verifique que el ID y el precio sean valores numéricos válidos.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        } catch (Exception ex) {
            mostrarError("Error al actualizar la comida: " + ex.getMessage());
        }
    }

    /**
     * Procesa la eliminación de la comida seleccionada en la tabla.
     */
    private void eliminarComida() {
        if (txtId.getText().isBlank()) {
            mostrarError("Seleccione una comida de la tabla para eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar la comida rápida seleccionada?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                controlComida.eliminarComida(id);
                limpiarCampos();
                cargarComidas();
                mostrarMensaje("Producto eliminado correctamente.");
            } catch (NumberFormatException ex) {
                mostrarError("El identificador del producto no es válido.");
            } catch (Exception ex) {
                mostrarError("Error al eliminar la comida: " + ex.getMessage());
            }
        }
    }

    /**
     * Consulta al controlador la lista de comidas y actualiza las filas de la tabla.
     */
    private void cargarComidas() {
        modeloTabla.setRowCount(0);
        try {
            List<Comida> comidas = controlComida.listarComidas();
            for (Comida c : comidas) {
                modeloTabla.addRow(new Object[]{
                        c.getId(), c.getNombre(), c.getIngredientes(), c.getPrecio()
                });
            }
        } catch (Exception ex) {
            mostrarError("Error al cargar la lista de comidas: " + ex.getMessage());
        }
    }

    /**
     * Limpia los campos de texto del formulario y deselecciona la tabla.
     */
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtIngredientes.setText("");
        txtPrecio.setText("");
        tabla.clearSelection();
    }

    /**
     * Despliega una ventana emergente informativa para el usuario.
     */
    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Despliega una ventana emergente de error para el usuario.
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}