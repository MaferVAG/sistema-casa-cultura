package vistas;

import datos.GestorArchivos;
import modelo.Asistente;
import modelo.Inscripcion;
import modelo.Taller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PanelConsulta extends JPanel {

    private JComboBox<String> cmbTalleres;
    private JTable tablaAsistentes;
    private DefaultTableModel modeloTabla;

    private JLabel lblNombre, lblID, lblEdad, lblGenero;
    private JLabel lblDireccion, lblTelContacto, lblTelEmergencia;
    private JLabel lblTaller, lblHorario, lblMaterial, lblMensualidad;

    private ArrayList<Taller> talleres;
    private ArrayList<Asistente> asistentes;

    public PanelConsulta() {
        setLayout(null);

        talleres   = GestorArchivos.leerTalleres();
        asistentes = GestorArchivos.leerAsistentes();

        //SELECCIONAR TALER
        JLabel lblSelTaller = new JLabel("Seleccionar taller:");
        lblSelTaller.setFont(new Font("Arial", Font.BOLD, 13));
        lblSelTaller.setBounds(20, 20, 140, 25);
        add(lblSelTaller);

        cmbTalleres = new JComboBox<>();
        cmbTalleres.addItem("Seleccionar taller");
        for (Taller t : talleres) {
            cmbTalleres.addItem(t.getNombre());
        }
        cmbTalleres.setBounds(165, 20, 200, 25);
        add(cmbTalleres);

        //TABLA ASISTENTES
        JLabel lblInscritos = new JLabel("Asistentes inscritos:");
        lblInscritos.setFont(new Font("Arial", Font.BOLD, 13));
        lblInscritos.setBounds(20, 60, 180, 25);
        add(lblInscritos);

        String[] columnas = {"ID", "Nombre completo", "Horario", "Mensualidad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaAsistentes = new JTable(modeloTabla);
        tablaAsistentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaAsistentes);
        scroll.setBounds(20, 88, 620, 120);
        add(scroll);

        //INFO ASISTENTE
        JLabel lblDetalle = new JLabel("Detalle del asistente seleccionado:");
        lblDetalle.setFont(new Font("Arial", Font.BOLD, 13));
        lblDetalle.setBounds(20, 222, 280, 25);
        add(lblDetalle);

        JPanel panelDetalle = new JPanel();
        panelDetalle.setLayout(null);
        panelDetalle.setBounds(20, 250, 620, 160);
        panelDetalle.setBackground(new Color(245, 248, 255));
        panelDetalle.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 153)));
        add(panelDetalle);

        //DATOS PERSONALES IZQ
        String[] etiquetas = {"ID:", "Nombre:", "Edad:", "Género:", "Dirección:", "Tel. Contacto:", "Tel. Emergencia:"};
        JLabel[] valores = new JLabel[7];
        int y = 12;
        for (int i = 0; i < etiquetas.length; i++) {
            JLabel lbl = new JLabel(etiquetas[i]);
            lbl.setFont(new Font("Arial", Font.BOLD, 10));
            lbl.setBounds(10, y, 100, 18);
            panelDetalle.add(lbl);

            valores[i] = new JLabel("—");
            valores[i].setFont(new Font("Arial", Font.PLAIN, 10));
            valores[i].setBounds(110, y, 180, 18);
            panelDetalle.add(valores[i]);
            y += 20;
        }

        lblID            = valores[0];
        lblNombre        = valores[1];
        lblEdad          = valores[2];
        lblGenero        = valores[3];
        lblDireccion     = valores[4];
        lblTelContacto   = valores[5];
        lblTelEmergencia = valores[6];

        //TALLERES DER
        String[] etiq2 = {"Taller:", "Horario:", "Material:", "Mensualidad:"};
        JLabel[] vals2 = new JLabel[4];
        int y2 = 12;
        for (int i = 0; i < etiq2.length; i++) {
            JLabel lbl = new JLabel(etiq2[i]);
            lbl.setFont(new Font("Arial", Font.BOLD, 10));
            lbl.setBounds(315, y2, 90, 18);
            panelDetalle.add(lbl);

            vals2[i] = new JLabel("—");
            vals2[i].setFont(new Font("Arial", Font.PLAIN, 10));
            vals2[i].setBounds(405, y2, 200, 18);
            panelDetalle.add(vals2[i]);
            y2 += 20;
        }

        lblTaller      = vals2[0];
        lblHorario     = vals2[1];
        lblMaterial    = vals2[2];
        lblMensualidad = vals2[3];
        lblMensualidad.setFont(new Font("Arial", Font.BOLD, 11));
        lblMensualidad.setForeground(new Color(0, 51, 153));

        //ACCIONES
        cmbTalleres.addActionListener(e -> cargarAsistentesPorTaller());

        tablaAsistentes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetalle();
            }
        });
    }

    private void cargarAsistentesPorTaller() {
        modeloTabla.setRowCount(0);
        limpiarDetalle();

        //SELECCIONAR TALLER
        int index = cmbTalleres.getSelectedIndex();
        if (index == 0) return;

        String tallerElegido = (String) cmbTalleres.getSelectedItem();
        asistentes = GestorArchivos.leerAsistentes();

        for (Asistente a : asistentes) {
            for (Inscripcion i : a.getInscripciones()) {
                if (i.getTaller().getNombre().equals(tallerElegido)) {
                    modeloTabla.addRow(new Object[]{
                            a.getId(),
                            a.getNombreCompleto(),
                            i.getHorario(),
                            String.format("$%.2f", i.getCostoTotal())
                    });
                }
            }
        }

        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No hay asistentes inscritos en " + tallerElegido,
                    "Sin inscritos",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarDetalle() {
        int fila = tablaAsistentes.getSelectedRow();
        if (fila < 0) return;

        String id = (String) modeloTabla.getValueAt(fila, 0);
        Asistente encontrado = null;

        for (Asistente a : asistentes) {
            if (a.getId().equals(id)) {
                encontrado = a;
                break;
            }
        }

        if (encontrado == null) return;

        //DATOS PERSONALES
        lblID.setText(encontrado.getId());
        lblNombre.setText(encontrado.getNombreCompleto());
        lblEdad.setText(encontrado.getEdad() + " años");
        lblGenero.setText(encontrado.getGenero());
        lblDireccion.setText(encontrado.getDireccion());
        lblTelContacto.setText(encontrado.getTelContacto());
        lblTelEmergencia.setText(encontrado.getTelEmergencia());

        //DATOS TALLER ELEGIDO
        String tallerElegido = (String) cmbTalleres.getSelectedItem();
        for (Inscripcion i : encontrado.getInscripciones()) {
            if (i.getTaller().getNombre().equals(tallerElegido)) {
                lblTaller.setText(i.getTaller().getNombre());
                lblHorario.setText(i.getHorario());
                lblMaterial.setText(i.isIncluyeMaterial() ? "Sí" : "No");
                lblMensualidad.setText(String.format("$%.2f / mes", encontrado.calcularMensualidad()));
                break;
            }
        }
    }

    private void limpiarDetalle() {
        lblID.setText("—");
        lblNombre.setText("—");
        lblEdad.setText("—");
        lblGenero.setText("—");
        lblDireccion.setText("—");
        lblTelContacto.setText("—");
        lblTelEmergencia.setText("—");
        lblTaller.setText("—");
        lblHorario.setText("—");
        lblMaterial.setText("—");
        lblMensualidad.setText("—");
    }
}