package com.empresa.vista;

import com.empresa.control.ControlCliente;
import com.empresa.modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaCliente extends JPanel {

    private final ControlCliente controlCliente;

    private JTextField txtNombre, txtTelefono, txtDireccion;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public VistaCliente(ControlCliente controlCliente) {
        this.controlCliente = controlCliente;
        setLayout(new BorderLayout());
        construirFormulario();
        construirTabla();
        cargarClientes(); // le pide los datos al control, no al DAO
    }

    private void construirFormulario() {
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));

        txtNombre = new JTextField();
        txtTelefono = new JTextField();
        txtDireccion = new JTextField();
        JButton btnGuardar = new JButton("Guardar Cliente");

        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("Teléfono:"));
        form.add(txtTelefono);
        form.add(new JLabel("Dirección:"));
        form.add(txtDireccion);
        form.add(new JLabel());
        form.add(btnGuardar);

        // La vista solo recolecta el texto y se lo entrega al control.
        // No decide si es válido, no arma reglas de negocio.
        btnGuardar.addActionListener(e -> {
            controlCliente.registrarCliente(
                    txtNombre.getText(),
                    txtTelefono.getText(),
                    txtDireccion.getText()
            );
            limpiarCampos();
            cargarClientes();
        });

        add(form, BorderLayout.NORTH);
    }

    private void construirTabla() {
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Teléfono", "Dirección"}, 0
        );
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void cargarClientes() {
        modeloTabla.setRowCount(0);

        // Aquí SÍ "toca" objetos Cliente, pero solo para mostrarlos.
        // No los crea, no los valida, no los guarda: eso ya lo hizo el control.
        List<Cliente> clientes = controlCliente.listarClientes();

        for (Cliente c : clientes) {
            modeloTabla.addRow(new Object[]{
                    c.getId(), c.getNombre(), c.getTelefono(), c.getDireccion()
            });
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
    }
}