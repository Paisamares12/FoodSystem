package com.empresa.vista;

import com.empresa.control.IControlCliente;
import com.empresa.modelo.Cliente;

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
 * Panel de interfaz gráfica encargado de la gestión (CRUD) de clientes.
 *
 * <p>Esta vista se comunica exclusivamente con la capa de control a través de
 * la interfaz {@link IControlCliente}. La vista captura la interacción del usuario,
 * delega las operaciones al controlador y muestra los resultados y errores
 * con una interfaz estilizada, limpia, profesional y con cabeceras de tabla de alta legibilidad.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class VistaCliente extends JPanel {

    /**
     * Referencia a la interfaz del controlador de clientes.
     */
    private final IControlCliente controlCliente;

    /**
     * Campos de texto del formulario.
     */
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtDireccion;

    /**
     * Componentes de la tabla de visualización.
     */
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Paleta de colores para una interfaz moderna con texto de alto contraste.
     */
    private static final Color COLOR_FONDO = new Color(248, 249, 250);
    private static final Color COLOR_CARD = Color.WHITE;
    private static final Color COLOR_TEXTO = new Color(33, 37, 41);         // Texto oscuro nítido
    private static final Color COLOR_TITULO = new Color(21, 67, 96);        // Azul oscuro
    private static final Color COLOR_TEXTO_VERDE = new Color(20, 90, 50);   // Verde oscuro para Guardar
    private static final Color COLOR_TEXTO_AZUL = new Color(21, 67, 96);    // Azul oscuro para Actualizar
    private static final Color COLOR_TEXTO_ROJO = new Color(146, 43, 33);   // Rojo oscuro para Eliminar
    private static final Color COLOR_TEXTO_GRIS = new Color(44, 62, 80);    // Gris oscuro para Limpiar

    /**
     * Constructor de la vista de clientes.
     *
     * @param controlCliente instancia del controlador que implementa {@link IControlCliente}
     */
    public VistaCliente(IControlCliente controlCliente) {
        this.controlCliente = controlCliente;
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_FONDO);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        construirFormulario();
        construirTabla();
        cargarClientes();
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
        JLabel lblTituloSeccion = new JLabel("Datos del Cliente");
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
        txtTelefono = crearCampoTexto();
        txtDireccion = crearCampoTexto();

        form.add(crearEtiqueta("ID (Automático):"));
        form.add(txtId);
        form.add(crearEtiqueta("Nombre Completo:"));
        form.add(txtNombre);
        form.add(crearEtiqueta("Teléfono de Contacto:"));
        form.add(txtTelefono);
        form.add(crearEtiqueta("Dirección de Entrega:"));
        form.add(txtDireccion);

        // Botones de acción estilizados con texto oscuro nítido y alto contraste
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

        btnGuardar.addActionListener(e -> guardarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        add(cardFormulario, BorderLayout.NORTH);
    }

    /**
     * Construye la tabla donde se muestran los clientes registrados y agrega
     * el evento de selección para autocompletar el formulario al hacer clic en una fila.
     */
    private void construirTabla() {
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Teléfono", "Dirección"}, 0
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
        tabla.setSelectionBackground(new Color(214, 234, 248));
        tabla.setSelectionForeground(new Color(21, 67, 96));
        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(230, 233, 236));

        // Centrar columna de ID en las celdas
        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centerCellRenderer);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(60);

        // Renderizador de cabecera con texto oscuro, fondo suave y bordes nítidos
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setForeground(new Color(21, 67, 96)); // Azul marino oscuro nítido
                label.setBackground(new Color(235, 240, 245)); // Gris azulado suave
                label.setHorizontalAlignment(column == 0 ? JLabel.CENTER : JLabel.LEFT);
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 1, new Color(189, 195, 199)),
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
                txtTelefono.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtDireccion.setText(modeloTabla.getValueAt(fila, 3).toString());
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 205, 215), 1, true),
                BorderFactory.createTitledBorder(
                        null, "Lista de Clientes Registrados",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        COLOR_TITULO
                )
        ));

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Crea una etiqueta estilizada con texto oscuro de alta legibilidad.
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
     * Crea un botón moderno con texto oscuro en negrita y borde de color para máxima legibilidad.
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
     * Procesa la acción de guardar un nuevo cliente delegando al controlador.
     */
    private void guardarCliente() {
        try {
            controlCliente.registrarCliente(
                    txtNombre.getText(),
                    txtTelefono.getText(),
                    txtDireccion.getText()
            );
            limpiarCampos();
            cargarClientes();
            mostrarMensaje("Cliente registrado con éxito.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        } catch (Exception ex) {
            mostrarError("Ocurrió un error al guardar el cliente: " + ex.getMessage());
        }
    }

    /**
     * Procesa la acción de actualizar los datos de un cliente seleccionado.
     */
    private void actualizarCliente() {
        if (txtId.getText().isBlank()) {
            mostrarError("Seleccione un cliente de la tabla para actualizar.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            controlCliente.actualizarCliente(
                    id,
                    txtNombre.getText(),
                    txtTelefono.getText(),
                    txtDireccion.getText()
            );
            limpiarCampos();
            cargarClientes();
            mostrarMensaje("Cliente actualizado con éxito.");
        } catch (NumberFormatException ex) {
            mostrarError("El ID del cliente seleccionado no es válido.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        } catch (Exception ex) {
            mostrarError("Error al actualizar el cliente: " + ex.getMessage());
        }
    }

    /**
     * Procesa la eliminación del cliente seleccionado en la tabla.
     */
    private void eliminarCliente() {
        if (txtId.getText().isBlank()) {
            mostrarError("Seleccione un cliente de la tabla para eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar el cliente seleccionado?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                controlCliente.eliminarCliente(id);
                limpiarCampos();
                cargarClientes();
                mostrarMensaje("Cliente eliminado correctamente.");
            } catch (NumberFormatException ex) {
                mostrarError("El identificador del cliente no es válido.");
            } catch (Exception ex) {
                mostrarError("Error al eliminar el cliente: " + ex.getMessage());
            }
        }
    }

    /**
     * Consulta al controlador la lista de clientes y actualiza las filas de la tabla.
     */
    private void cargarClientes() {
        modeloTabla.setRowCount(0);
        try {
            List<Cliente> clientes = controlCliente.listarClientes();
            for (Cliente c : clientes) {
                modeloTabla.addRow(new Object[]{
                        c.getId(), c.getNombre(), c.getTelefono(), c.getDireccion()
                });
            }
        } catch (Exception ex) {
            mostrarError("Error al cargar la lista de clientes: " + ex.getMessage());
        }
    }

    /**
     * Limpia los campos de texto del formulario y deselecciona la tabla.
     */
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
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