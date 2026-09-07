package com.empresa.vista;

import com.empresa.control.IControlCliente;
import com.empresa.modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel de interfaz gráfica encargado de la gestión (CRUD) de clientes.
 *
 * <p>Esta vista se comunica exclusivamente con la capa de control a través de
 * la interfaz {@link IControlCliente}. La vista captura la interacción del usuario,
 * delega las operaciones al controlador y muestra los resultados y errores
 * sin contener lógica de negocio ni acceso directo a la base de datos.</p>
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
     * Constructor de la vista de clientes.
     *
     * @param controlCliente instancia del controlador que implementa {@link IControlCliente}
     */
    public VistaCliente(IControlCliente controlCliente) {
        this.controlCliente = controlCliente;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        construirFormulario();
        construirTabla();
        cargarClientes();
    }

    /**
     * Construye y organiza el panel superior con el formulario de entrada y los botones de acción.
     */
    private void construirFormulario() {
        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(new Color(240, 240, 240));

        txtNombre = new JTextField();
        txtTelefono = new JTextField();
        txtDireccion = new JTextField();

        form.add(new JLabel("ID (Automático):"));
        form.add(txtId);
        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("Teléfono:"));
        form.add(txtTelefono);
        form.add(new JLabel("Dirección:"));
        form.add(txtDireccion);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(form, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        add(panelSuperior, BorderLayout.NORTH);
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
                return false; // Las celdas no se editan directamente en la tabla
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
        scrollPane.setBorder(BorderFactory.createTitledBorder("Clientes Registrados"));
        add(scrollPane, BorderLayout.CENTER);
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
            mostrarError("Ocurrió un error inesperado al guardar el cliente: " + ex.getMessage());
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
     *
     * @param mensaje texto informativo
     */
    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Despliega una ventana emergente de error para el usuario.
     *
     * @param mensaje texto de error
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}