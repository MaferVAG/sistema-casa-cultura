package vistas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import datos.GestorArchivos;
import modelo.Asistente;

public class DialogEditarAsistente extends JDialog {

    private JTextField txtNombre, txtApellidoP, txtApellidoM;
    private JTextField txtEdad, txtDireccion;
    private JTextField txtTelContacto, txtTelEmergencia;
    private JComboBox<String> cmbGenero;
    private JButton btnGuardar, btnCancelar;

    public DialogEditarAsistente(JFrame parent, Asistente asistente, ArrayList<Asistente> listaCompleta) {
        super(parent, "Casa de la Cultura – UAM Azcapotzalco :: Editar Datos", true);
        setSize(560, 380);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Editar Datos del Asistente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setBounds(160, 15, 280, 25);
        add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre(s):");
        lblNombre.setBounds(20, 60, 100, 25);
        add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(120, 60, 150, 25);
        add(txtNombre);

        JLabel lblApellidoP = new JLabel("Primer Apellido:");
        lblApellidoP.setBounds(290, 60, 110, 25);
        add(lblApellidoP);
        txtApellidoP = new JTextField();
        txtApellidoP.setBounds(400, 60, 130, 25);
        add(txtApellidoP);

        JLabel lblApellidoM = new JLabel("Segundo Apellido:");
        lblApellidoM.setBounds(20, 100, 120, 25);
        add(lblApellidoM);
        txtApellidoM = new JTextField();
        txtApellidoM.setBounds(140, 100, 150, 25);
        add(txtApellidoM);

        JLabel lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(320, 100, 50, 25);
        add(lblEdad);
        txtEdad = new JTextField();
        txtEdad.setBounds(370, 100, 60, 25);
        add(txtEdad);

        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setBounds(20, 140, 70, 25);
        add(lblGenero);
        cmbGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        cmbGenero.setBounds(90, 140, 120, 25);
        add(cmbGenero);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(240, 140, 70, 25);
        add(lblDireccion);
        txtDireccion = new JTextField();
        txtDireccion.setBounds(310, 140, 220, 25);
        add(txtDireccion);

        JLabel lblTelContacto = new JLabel("Tel. Contacto:");
        lblTelContacto.setBounds(20, 180, 100, 25);
        add(lblTelContacto);
        txtTelContacto = new JTextField();
        txtTelContacto.setBounds(120, 180, 150, 25);
        add(txtTelContacto);

        JLabel lblTelEmergencia = new JLabel("Tel. Emergencia:");
        lblTelEmergencia.setBounds(290, 180, 115, 25);
        add(lblTelEmergencia);
        txtTelEmergencia = new JTextField();
        txtTelEmergencia.setBounds(405, 180, 125, 25);
        add(txtTelEmergencia);

        txtNombre.setText(asistente.getNombre());
        txtApellidoP.setText(asistente.getApellidoP());
        txtApellidoM.setText(asistente.getApellidoM());
        txtEdad.setText(String.valueOf(asistente.getEdad()));
        cmbGenero.setSelectedItem(asistente.getGenero());
        txtDireccion.setText(asistente.getDireccion());
        txtTelContacto.setText(asistente.getTelContacto());
        txtTelEmergencia.setText(asistente.getTelEmergencia());

        //BOTONES
        btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBounds(290, 290, 140, 30);
        add(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(445, 290, 90, 30);
        add(btnCancelar);

        //ACCION
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().isEmpty() ||
                    txtApellidoP.getText().isEmpty() ||
                    txtEdad.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nombre, Primer Apellido, Segundo Apellido y Edad son obligatorios",
                        "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //VALIDAR EDAD
            try {
                Integer.parseInt(txtEdad.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "La edad debe ser un número",
                        "Dato incorrecto",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            asistente.setNombre(txtNombre.getText().trim());
            asistente.setApellidoP(txtApellidoP.getText().trim());
            asistente.setApellidoM(txtApellidoM.getText().trim());
            asistente.setEdad(Integer.parseInt(txtEdad.getText().trim()));
            asistente.setGenero((String) cmbGenero.getSelectedItem());
            asistente.setDireccion(txtDireccion.getText().trim());
            asistente.setTelContacto(txtTelContacto.getText().trim());
            asistente.setTelEmergencia(txtTelEmergencia.getText().trim());

            GestorArchivos.guardarAsistentes(listaCompleta);

            String telContacto   = txtTelContacto.getText().trim();
            String telEmergencia = txtTelEmergencia.getText().trim();

            if (telContacto.length() != 10 || !telContacto.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,
                        "El teléfono de contacto debe tener exactamente 10 dígitos",
                        "Teléfono inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (telEmergencia.length() != 10 || !telEmergencia.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,
                        "El teléfono de emergencia debe tener exactamente 10 dígitos",
                        "Teléfono inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int edad;
            try {
                edad = Integer.parseInt(txtEdad.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "La edad debe ser un número",
                        "Dato incorrecto",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (edad < 18 || edad > 80) {
                JOptionPane.showMessageDialog(this,
                        "La edad debe ser entre 18 y 80 años",
                        "Edad inválida",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Datos actualizados con éxito.",
                    "Actualización exitosa", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
