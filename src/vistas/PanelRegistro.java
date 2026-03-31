package vistas;

import javax.swing.*;
import java.awt.*;

import datos.GestorArchivos;
import  modelo.Asistente;

import java.lang.classfile.instruction.ReturnInstruction;
import java.util.ArrayList;

public class PanelRegistro extends JPanel {
    private JLabel lblNombre, lblApellidoP, lblApellidoM;
    private JLabel lblEdad, lblGenero, lblDireccion;
    private JLabel lblTelContacto, lblTelEmergencia;

    private JTextField txtNombre, txtApellidoP, txtApellidoM;
    private JTextField txtEdad, txtDireccion;
    private JTextField txtTelContacto, txtTelEmergencia;
    private JComboBox<String> cmbGenero;

    private JLabel lblSeccion1, lblSeccion2;

    private JButton btnGuardar, btnCancelar;

    private JLabel lblID;
    private JLabel lblIDValor;
    private int contadorID = 1;

    private ArrayList<Asistente> listaAsistentes;

    public PanelRegistro() {
        setLayout(null);

        listaAsistentes = GestorArchivos.leerAsistentes();
        contadorID = listaAsistentes.size() + 1;

        //DATOS PERSONALES
        lblSeccion1 = new JLabel("Datos Personales");
        lblSeccion1.setFont(new Font("Arial", Font.BOLD, 13));
        lblSeccion1.setBounds(20, 15, 200, 25);
        add(lblSeccion1);

        lblNombre = new JLabel("Nombre(s):");
        lblNombre.setBounds(20, 50, 100, 25);
        add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(120, 50, 150, 25);
        add(txtNombre);

        lblApellidoP = new JLabel("Apellido Paterno:");
        lblApellidoP.setBounds(290, 50, 110, 25);
        add(lblApellidoP);
        txtApellidoP = new JTextField();
        txtApellidoP.setBounds(400, 50, 150, 25);
        add(txtApellidoP);

        lblApellidoM = new JLabel("Apellido Materno:");
        lblApellidoM.setBounds(20, 90, 120, 25);
        add(lblApellidoM);
        txtApellidoM = new JTextField();
        txtApellidoM.setBounds(140, 90, 150, 25);
        add(txtApellidoM);

        lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(320, 90, 50, 25);
        add(lblEdad);
        txtEdad = new JTextField();
        txtEdad.setBounds(370, 90, 60, 25);
        add(txtEdad);

        lblGenero = new JLabel("Genero:");
        lblGenero.setBounds(20, 130, 60, 25);
        add(lblGenero);
        cmbGenero = new JComboBox<>(new String[]{"Seleccionar", "Masculino", "Femenino"});
        cmbGenero.setBounds(80, 130, 130, 25);
        add(cmbGenero);

        lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(240, 130, 70, 25);
        add(lblDireccion);
        txtDireccion = new JTextField();
        txtDireccion.setBounds(310, 130, 250, 25);
        add(txtDireccion);

        lblID = new JLabel("ID del asistente: ");
        lblID.setBounds(20, 320, 120, 25);
        add(lblID);

        lblIDValor = new JLabel("Se asignará al guardar.");
        lblIDValor.setBounds(140, 320, 250, 25);
        lblIDValor.setFont(new Font("Arial", Font.BOLD, 12));
        add(lblIDValor);

        //DATOS DE CONTACTO
        lblSeccion2 = new JLabel("Datos de Contacto");
        lblSeccion2.setFont(new Font("Arial", Font.BOLD, 13));
        lblSeccion2.setBounds(20, 175, 200, 25);
        add(lblSeccion2);

        lblTelContacto = new JLabel("Tel. Contacto:");
        lblTelContacto.setBounds(20, 210, 100, 25);
        add(lblTelContacto);
        txtTelContacto = new JTextField();
        txtTelContacto.setBounds(120, 210, 150, 25);
        add(txtTelContacto);

        lblTelEmergencia = new JLabel("Tel. Emergencia:");
        lblTelEmergencia.setBounds(300, 210, 110, 25);
        add(lblTelEmergencia);
        txtTelEmergencia = new JTextField();
        txtTelEmergencia.setBounds(415, 210, 150, 25);
        add(txtTelEmergencia);

        //BOTONES
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(430, 320, 100, 30);
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().isEmpty() || txtApellidoP.getText().isEmpty() || txtApellidoM.getText().isEmpty()
            || txtTelContacto.getText().isEmpty() || txtTelEmergencia.getText().isEmpty() || txtDireccion.getText().isEmpty()
            || cmbGenero.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(this,
                        "Por favor llene todos los campos",
                        "Campos Incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int edad;
            try {
                edad = Integer.parseInt(txtEdad.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "La edad debe de ser un número",
                        "Dato incorrecto",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (edad < 18 || edad > 80) {
                JOptionPane.showMessageDialog(this,
                        "La edad permitida es entre 18 y 80 años.",
                        "Edad inválida",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            //VALIDAR NÚMERO DE TELÉFONO
            String telContacto = txtTelContacto.getText().trim();
            String telEmergencia = txtTelEmergencia.getText().trim();

            if (telContacto.length() != 10 || !telContacto.matches("\\d+")){
                JOptionPane.showMessageDialog(this,
                        "El teléfono de contacto debe de tener exactamente 10 dígitos.",
                        "Teléfono inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (telEmergencia.length() != 10 || !telEmergencia.matches("\\d+")){
                JOptionPane.showMessageDialog(this,
                        "El teléfono de emergencia debe de tener exactamente 10 dígitos.",
                        "Teléfono inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            //GENERADOR ID
            String id = String.format("ID-%03d", contadorID);
            contadorID++;

            Asistente nuevo = new Asistente(
                    id,
                    txtNombre.getText().trim(),
                    txtApellidoP.getText().trim(),
                    txtApellidoM.getText().trim(),
                    edad,
                    (String) cmbGenero.getSelectedItem(),
                    txtDireccion.getText().trim(),
                    txtTelContacto.getText().trim(),
                    txtTelEmergencia.getText().trim()
            );

            listaAsistentes = GestorArchivos.leerAsistentes();
            listaAsistentes.add(nuevo);
            GestorArchivos.guardarAsistentes(listaAsistentes);

            lblIDValor.setText(id);
            JOptionPane.showMessageDialog(this,
                    "Asistente registrado con éxito.\n" + "Nombre: " + nuevo.getNombreCompleto()
                    + "\n" + "ID asignado: " + id + "\n\n" + "Guarda este ID para futuras consultas.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();
        });
        add(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(545, 320, 100, 30);
        add(btnCancelar);
        btnCancelar.addActionListener(e -> limpiarCampos());

        //ESTILOS
        setBackground(Estilos.FONDO);

        lblSeccion1.setFont(Estilos.FUENTE_SECCION);
        lblSeccion1.setForeground(Estilos.VINO);
        lblSeccion2.setFont(Estilos.FUENTE_SECCION);
        lblSeccion2.setForeground(Estilos.VINO);

        for (Component comp : getComponents()) {
            if (comp instanceof JLabel) {
                JLabel lbl = (JLabel) comp;
                if (lbl.getFont() != Estilos.FUENTE_SECCION) {
                    lbl.setFont(Estilos.FUENTE_LABEL);
                    lbl.setForeground(Estilos.TEXTO);
                }
            }
        }

        Estilos.aplicarCampo(txtNombre);
        Estilos.aplicarCampo(txtApellidoP);
        Estilos.aplicarCampo(txtApellidoM);
        Estilos.aplicarCampo(txtEdad);
        Estilos.aplicarCampo(txtDireccion);
        Estilos.aplicarCampo(txtTelContacto);
        Estilos.aplicarCampo(txtTelEmergencia);
        Estilos.aplicarCampo(cmbGenero);

        Estilos.aplicarBotonPrincipal(btnGuardar);
        Estilos.aplicarBotonSecundario(btnCancelar);

        lblIDValor.setFont(Estilos.FUENTE_TOTAL);
        lblIDValor.setForeground(Estilos.VINO);
    }

    private void limpiarCampos(){
        txtNombre.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        txtEdad.setText("");
        txtDireccion.setText("");
        txtTelContacto.setText("");
        txtTelEmergencia.setText("");
        cmbGenero.setSelectedIndex(0);
    }



}
