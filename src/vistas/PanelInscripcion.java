package vistas;

import datos.GestorArchivos;
import modelo.Asistente;
import modelo.Inscripcion;
import modelo.Taller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelInscripcion extends JPanel {

    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tablaTalleres;
    private JComboBox<String> cmbHorario;
    private JCheckBox chkMaterial;
    private JLabel lblCosto;
    private JButton btnInscribir, btnCancelar;

    private ArrayList<Taller> talleres;
    private ArrayList<Asistente> asistentes;
    private Asistente asistenteSeleccionado;

    private JLabel lblNombreAsistente, lblIDAsistente;
    private JPanel panelAsistente;

    public PanelInscripcion() {
        setLayout(null);

        talleres   = GestorArchivos.leerTalleres();
        asistentes = GestorArchivos.leerAsistentes();

        //BUSCAR
        JLabel lblBuscar = new JLabel("Buscar por ID:");
        lblBuscar.setBounds(20, 20, 100, 25);
        add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(120, 20, 150, 25);
        add(txtBuscar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(280, 20, 80, 25);
        add(btnBuscar);

        //PANEL INFO ASISTENTE
        panelAsistente = new JPanel();
        panelAsistente.setLayout(null);
        panelAsistente.setBounds(20, 52, 580, 35);
        panelAsistente.setBackground(new Color(232, 244, 255));
        panelAsistente.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 153)));
        panelAsistente.setVisible(false);
        add(panelAsistente);

        JLabel lblIcono = new JLabel("👤");
        lblIcono.setBounds(8, 8, 25, 20);
        panelAsistente.add(lblIcono);

        lblIDAsistente = new JLabel("");
        lblIDAsistente.setBounds(30, 8, 80, 20);
        lblIDAsistente.setFont(new Font("Arial", Font.BOLD, 11));
        lblIDAsistente.setForeground(new Color(0, 51, 153));
        panelAsistente.add(lblIDAsistente);

        lblNombreAsistente = new JLabel("");
        lblNombreAsistente.setBounds(115, 8, 350, 20);
        lblNombreAsistente.setFont(new Font("Arial", Font.PLAIN, 11));
        panelAsistente.add(lblNombreAsistente);

        //TABLA TALLERES
        JLabel lblTalleres = new JLabel("Talleres disponibles:");
        lblTalleres.setFont(new Font("Arial", Font.BOLD, 13));
        lblTalleres.setBounds(20, 60, 200, 25);
        add(lblTalleres);

        String[] columnas = {"Taller", "Instructor", "Costo/mes", "Costo Material"};
        String[][] datos  = cargarDatosTalleres();

        tablaTalleres = new JTable(datos, columnas);
        tablaTalleres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaTalleres);
        scroll.setBounds(20, 95, 580, 110);
        add(scroll);

        //OPCIONES
        JLabel lblOpciones = new JLabel("Opciones del taller seleccionado:");
        lblOpciones.setFont(new Font("Arial", Font.BOLD, 13));
        lblOpciones.setBounds(20, 215, 250, 25);
        add(lblOpciones);

        JLabel lblHorario = new JLabel("Horario:");
        lblHorario.setBounds(20, 250, 60, 25);
        add(lblHorario);

        cmbHorario = new JComboBox<>();
        cmbHorario.setBounds(80, 250, 280, 25);
        add(cmbHorario);

        chkMaterial = new JCheckBox("Comprar material en la Casa de la Cultura");
        chkMaterial.setBounds(20, 285, 320, 25);
        add(chkMaterial);

        lblCosto = new JLabel("Costo total: $0.00");
        lblCosto.setFont(new Font("Arial", Font.BOLD, 12));
        lblCosto.setForeground(new Color(0, 51, 153));
        lblCosto.setBounds(20, 320, 280, 25);
        add(lblCosto);

        //BOTONES
        btnInscribir = new JButton("Inscribir");
        btnInscribir.setBounds(430, 370, 100, 30);
        add(btnInscribir);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(545, 370, 90, 30);
        add(btnCancelar);

        //ACCIONES
        tablaTalleres.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaTalleres.getSelectedRow();
                if (fila >= 0) {
                    Taller t = talleres.get(fila);
                    cmbHorario.removeAllItems();
                    for (String h : t.getHorarios()) {
                        cmbHorario.addItem(h);
                    }
                    actualizarCosto();
                }
            }
        });

        //ACTUALIZAR COSTOS
        chkMaterial.addItemListener(e -> actualizarCosto());

        btnBuscar.addActionListener(e -> buscarAsistente());

        btnInscribir.addActionListener(e -> inscribir());

        btnCancelar.addActionListener(e -> {
            txtBuscar.setText("");
            asistenteSeleccionado = null;
            chkMaterial.setSelected(false);
            lblCosto.setText("Costo total: $0.00");
            panelAsistente.setVisible(false);
        });
    }

    private String[][] cargarDatosTalleres() {
        String[][] datos = new String[talleres.size()][4];
        for (int i = 0; i < talleres.size(); i++) {
            Taller t = talleres.get(i);
            datos[i][0] = t.getNombre();
            datos[i][1] = t.getInstructor();
            datos[i][2] = "$" + t.getCosto();
            datos[i][3] = "$" + t.getCostoMaterial();
        }
        return datos;
    }

    private void actualizarCosto() {
        int fila = tablaTalleres.getSelectedRow();
        if (fila >= 0) {
            Taller t = talleres.get(fila);
            double costo = t.getCosto();
            if (chkMaterial.isSelected()) {
                costo += t.getCostoMaterial();
            }
            lblCosto.setText(String.format("Costo total: $%.2f / mes", costo));
        }
    }

    private void buscarAsistente() {
        asistentes = GestorArchivos.leerAsistentes();
        String id = txtBuscar.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa el ID del asistente",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        asistentes = GestorArchivos.leerAsistentes();
        asistenteSeleccionado = null;

        for (Asistente a : asistentes) {
            if (a.getId().equalsIgnoreCase(id)) {
                asistenteSeleccionado = a;
                break;
            }
        }

        if (asistenteSeleccionado == null) {
            panelAsistente.setVisible(false);
            JOptionPane.showMessageDialog(this,
                    "No se encontró ningún asistente con el ID: " + id,
                    "No encontrado",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            lblIDAsistente.setText(asistenteSeleccionado.getId());
            lblNombreAsistente.setText(asistenteSeleccionado.getNombreCompleto());
            panelAsistente.setVisible(true);
        }
    }

    private void inscribir() {

        asistentes = GestorArchivos.leerAsistentes();

        if (asistenteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Primero busca al asistente por ID",
                    "Sin asistente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int fila = tablaTalleres.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un taller de la lista",
                    "Sin taller", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idActual = asistenteSeleccionado.getId();

        asistenteSeleccionado = null;
        for (Asistente a : asistentes) {
            if (a.getId().equals(idActual)) {
                asistenteSeleccionado = a;
                break;
            }
        }

        if (asistenteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Error al encontrar el asistente, vuelve a buscarlo",
                    "Error", JOptionPane.ERROR_MESSAGE);
            panelAsistente.setVisible(false);
            return;
        }

        Taller tallerElegido   = talleres.get(fila);
        String horarioElegido  = (String) cmbHorario.getSelectedItem();
        boolean conMaterial    = chkMaterial.isSelected();

        //VERIFICAR INSCRIPCION
        for (Inscripcion i : asistenteSeleccionado.getInscripciones()) {
            if (i.getTaller().getNombre().equals(tallerElegido.getNombre())) {
                JOptionPane.showMessageDialog(this,
                        asistenteSeleccionado.getNombreCompleto() +
                                " ya está inscrito en " + tallerElegido.getNombre(),
                        "Ya inscrito", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Inscripcion nueva = new Inscripcion(tallerElegido, horarioElegido, conMaterial);
        asistenteSeleccionado.agregarInscripcion(nueva);
        GestorArchivos.guardarAsistentes(asistentes);

        JOptionPane.showMessageDialog(this,
                asistenteSeleccionado.getNombreCompleto() +
                        " inscrito en " + tallerElegido.getNombre() +
                        "\nHorario: " + horarioElegido +
                        "\nCosto total: $" + nueva.getCostoTotal(),
                "Inscripción exitosa", JOptionPane.INFORMATION_MESSAGE);

        txtBuscar.setText("");
        asistenteSeleccionado = null;
        panelAsistente.setVisible(false);
        chkMaterial.setSelected(false);
        lblCosto.setText("Costo total: $0.00");
    }
}