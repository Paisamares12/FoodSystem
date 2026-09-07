package com.empresa.vista;

import com.empresa.control.ControlComida;
import com.empresa.modelo.Comida;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel encargado de mostrar y capturar datos relacionados
 * con las comidas rápidas.
 *
 * <p>No contiene lógica de negocio: toda validación y persistencia
 * se delega a {@link ControlComida}.</p>
 *
 * @author Paula Martínez
 * @version 2.0
 */
public class VistaComida extends JPanel {

    private final ControlComida controlComida;

    private JTextField txtId, txtNombre, txtIngredientes, txtPrecio;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public VistaComida(ControlComida controlComida) {
        this.controlComida = controlComida;
        setLayout(new BorderLayout());
        construirFormulario();
        construirTabla();
        cargarComidas();
    }

    private void construirFormulario() {
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNombre = new JTextField();
        txtIngredientes = new JTextField();
        txtPrecio = new JTextField();

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        form.add(new JLabel("ID:"));
        form.add(txtId);
        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("Ingredientes:"));
        form.add(txtIngredientes);
        form.add(new JLabel("Precio:"));
        form.add(txtPrecio);

        JPanel panelBotones = new JPanel(new FlowLayout());
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

    private void construirTabla() {
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Ingredientes", "Precio"}, 0
        );
        tabla = new JTable(modeloTabla);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtIngredientes.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtPrecio.setText(modeloTabla.getValueAt(fila, 3).toString());
            }
        });

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void guardarComida() {
        try {
            controlComida.registrarComida(
                    txtNombre.getText(),
                    txtIngredientes.getText(),
                    Double.parseDouble(txtPrecio.getText())
            );
            limpiarCampos();
            cargarComidas();
        } catch (NumberFormatException ex) {
            mostrarError("El precio debe ser un número válido.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void actualizarComida() {
        if (txtId.getText().isBlank()) {
            mostrarError("Seleccione una comida de la tabla para actualizar.");
            return;
        }
        try {
            int id = Integer.parseInt(txtId.getText());
            controlComida.actualizarComida(
                    id,
                    txtNombre.getText(),
                    txtIngredientes.getText(),
                    Double.parseDouble(txtPrecio.getText())
            );
            limpiarCampos();
            cargarComidas();
        } catch (NumberFormatException ex) {
            mostrarError("Verifique que el ID y el precio sean válidos.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void eliminarComida() {
        if (txtId.getText().isBlank()) {
            mostrarError("Seleccione una comida de la tabla para eliminar.");
            return;
        }
        int id = Integer.parseInt(txtId.getText());
        controlComida.eliminarComida(id);
        limpiarCampos();
        cargarComidas();
    }

    private void cargarComidas() {
        modeloTabla.setRowCount(0);
        List<Comida> comidas = controlComida.listarComidas();

        for (Comida c : comidas) {
            modeloTabla.addRow(new Object[]{
                    c.getId(), c.getNombre(), c.getIngredientes(), c.getPrecio()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtIngredientes.setText("");
        txtPrecio.setText("");
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}