package com.empresa.vista;

import com.empresa.control.IControlComida;
import com.empresa.modelo.Comida;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel de interfaz gráfica encargado de la gestión (CRUD) de comidas rápidas.
 *
 * <p>Esta vista se comunica exclusivamente con la capa de control a través de
 * la interfaz {@link IControlComida}. Captura la entrada del usuario, delega las
 * operaciones al controlador y presenta los productos y mensajes correspondientes.</p>
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
     * Constructor de la vista de comidas rápidas.
     *
     * @param controlComida instancia del controlador que implementa {@link IControlComida}
     */
    public VistaComida(IControlComida controlComida) {
        this.controlComida = controlComida;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        construirFormulario();
        construirTabla();
        cargarComidas();
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
        txtIngredientes = new JTextField();
        txtPrecio = new JTextField();

        form.add(new JLabel("ID (Automático):"));
        form.add(txtId);
        form.add(new JLabel("Nombre Comida:"));
        form.add(txtNombre);
        form.add(new JLabel("Ingredientes:"));
        form.add(txtIngredientes);
        form.add(new JLabel("Precio ($):"));
        form.add(txtPrecio);

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

        btnGuardar.addActionListener(e -> guardarComida());
        btnActualizar.addActionListener(e -> actualizarComida());
        btnEliminar.addActionListener(e -> eliminarComida());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        add(panelSuperior, BorderLayout.NORTH);
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
        scrollPane.setBorder(BorderFactory.createTitledBorder("Menú de Comidas Rápidas"));
        add(scrollPane, BorderLayout.CENTER);
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