package vistas;

import datos.GestorArchivos;
import modelo.Asistente;
import modelo.Inscripcion;
import modelo.Taller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PanelAdministracion extends JPanel {

    private JComboBox<String> cmbTalleres;
    private JTable tablaAsistentes;
    private DefaultTableModel modeloAsistentes;
    private JTable tablaTalleres;
    private DefaultTableModel modeloTalleres;

    private JButton btnEditarDatos, btnActualizar, btnDarBaja, btnDarBajaTotal;

    private ArrayList<Taller> talleres;
    private ArrayList<Asistente> asistentes;
    private Asistente asistenteSeleccionado;

    public PanelAdministracion() {
        setLayout(null);

        talleres   = GestorArchivos.leerTalleres();
        asistentes = GestorArchivos.leerAsistentes();

        //SELECTOR DE TALLER
        JLabel lblTaller = new JLabel("Seleccionar taller:");
        lblTaller.setFont(new Font("Arial", Font.BOLD, 12));
        lblTaller.setBounds(20, 15, 140, 25);
        add(lblTaller);

        cmbTalleres = new JComboBox<>();
        cmbTalleres.addItem("Todos los asistentes");
        for (Taller t : talleres) cmbTalleres.addItem(t.getNombre());
        cmbTalleres.setBounds(165, 15, 200, 25);
        add(cmbTalleres);

        // ── Tabla asistentes ──────────────────────────
        JLabel lblAsistentes = new JLabel("Asistentes:");
        lblAsistentes.setFont(new Font("Arial", Font.BOLD, 12));
        lblAsistentes.setBounds(20, 50, 120, 25);
        add(lblAsistentes);

        String[] colAsistentes = {"ID", "Nombre", "Mensualidad"};
        modeloAsistentes = new DefaultTableModel(colAsistentes, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaAsistentes = new JTable(modeloAsistentes);
        tablaAsistentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollA = new JScrollPane(tablaAsistentes);
        scrollA.setBounds(20, 75, 280, 150);
        add(scrollA);

        //TABLA TALLERES
        JLabel lblTalleresAsis = new JLabel("Talleres inscritos:");
        lblTalleresAsis.setFont(new Font("Arial", Font.BOLD, 12));
        lblTalleresAsis.setBounds(315, 50, 160, 25);
        add(lblTalleresAsis);

        String[] colTalleres = {"Taller", "Horario", "Material", "Costo"};
        modeloTalleres = new DefaultTableModel(colTalleres, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaTalleres = new JTable(modeloTalleres);
        tablaTalleres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollT = new JScrollPane(tablaTalleres);
        scrollT.setBounds(315, 75, 325, 150);
        add(scrollT);

        //BOTRONES
        btnEditarDatos = new JButton("Editar Datos");
        btnEditarDatos.setBounds(20, 235, 130, 28);
        add(btnEditarDatos);

        btnDarBajaTotal = new JButton("Dar de Baja Total");
        btnDarBajaTotal.setBounds(160, 235, 140, 28);
        add(btnDarBajaTotal);

        btnActualizar = new JButton("Actualizar Material");
        btnActualizar.setBounds(315, 235, 155, 28);
        add(btnActualizar);

        btnDarBaja = new JButton("Dar de Baja Taller");
        btnDarBaja.setBounds(480, 235, 155, 28);
        add(btnDarBaja);

        //ACCIONES
        cmbTalleres.addActionListener(e -> cargarAsistentes());

        tablaAsistentes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarTalleresDeAsistente();
        });

        btnEditarDatos.addActionListener(e -> editarDatos());
        btnActualizar.addActionListener(e -> actualizarMaterial());
        btnDarBaja.addActionListener(e -> darDeBajaTaller());
        btnDarBajaTotal.addActionListener(e -> darDeBajaTotal());

        cargarAsistentes();
    }

    private void cargarAsistentes() {
        modeloAsistentes.setRowCount(0);
        modeloTalleres.setRowCount(0);
        asistenteSeleccionado = null;
        asistentes = GestorArchivos.leerAsistentes();

        int index = cmbTalleres.getSelectedIndex();

        for (Asistente a : asistentes) {
            if (index == 0) {
                // Todos los asistentes
                modeloAsistentes.addRow(new Object[]{
                        a.getId(),
                        a.getNombreCompleto(),
                        String.format("$%.2f", a.calcularMensualidad())
                });
            } else {
                String tallerElegido = (String) cmbTalleres.getSelectedItem();
                for (Inscripcion i : a.getInscripciones()) {
                    if (i.getTaller().getNombre().equals(tallerElegido)) {
                        modeloAsistentes.addRow(new Object[]{
                                a.getId(),
                                a.getNombreCompleto(),
                                String.format("$%.2f", a.calcularMensualidad())
                        });
                        break;
                    }
                }
            }
        }
    }

    private void cargarTalleresDeAsistente() {
        modeloTalleres.setRowCount(0);
        int fila = tablaAsistentes.getSelectedRow();
        if (fila < 0) return;

        String id = (String) modeloAsistentes.getValueAt(fila, 0);
        for (Asistente a : asistentes) {
            if (a.getId().equals(id)) {
                asistenteSeleccionado = a;
                break;
            }
        }

        if (asistenteSeleccionado == null) return;

        for (Inscripcion i : asistenteSeleccionado.getInscripciones()) {
            modeloTalleres.addRow(new Object[]{
                    i.getTaller().getNombre(),
                    i.getHorario(),
                    i.isIncluyeMaterial() ? "Sí" : "No",
                    String.format("$%.2f", i.getCostoTotal())
            });
        }
    }

    private void editarDatos() {
        if (asistenteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un asistente primero",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        DialogEditarAsistente dialog = new DialogEditarAsistente(parent, asistenteSeleccionado, asistentes);
        dialog.setVisible(true);
        cargarAsistentes();
    }

    private void actualizarMaterial() {
        if (asistenteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un asistente primero",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int filaTaller = tablaTalleres.getSelectedRow();
        if (filaTaller < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un taller de la lista derecha",
                    "Sin taller", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Inscripcion inscripcion = asistenteSeleccionado.getInscripciones().get(filaTaller);
        boolean estadoActual = inscripcion.isIncluyeMaterial();
        String mensaje = estadoActual
                ? "¿Cancelar la adquisición de material para " + inscripcion.getTaller().getNombre() + "?"
                : "¿Agregar material para " + inscripcion.getTaller().getNombre() + "?";

        int confirm = JOptionPane.showConfirmDialog(this, mensaje,
                "Actualizar material", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            inscripcion.setIncluyeMaterial(!estadoActual);
            GestorArchivos.guardarAsistentes(asistentes);
            cargarAsistentes();
            JOptionPane.showMessageDialog(this,
                    "Material actualizado con éxito.",
                    "Actualizado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void darDeBajaTaller() {
        if (asistenteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un asistente primero",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int filaTaller = tablaTalleres.getSelectedRow();
        if (filaTaller < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona el taller del que quieres dar de baja",
                    "Sin taller", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Inscripcion inscripcion = asistenteSeleccionado.getInscripciones().get(filaTaller);

        String razon = JOptionPane.showInputDialog(this,
                "¿Dar de baja a " + asistenteSeleccionado.getNombre() +
                        " del taller " + inscripcion.getTaller().getNombre() + "?\n\n" +
                        "Razón (opcional):",
                "Confirmar baja", JOptionPane.WARNING_MESSAGE);

        if (razon != null) {
            asistenteSeleccionado.eliminarInscripcion(inscripcion);
            GestorArchivos.guardarAsistentes(asistentes);
            cargarAsistentes();
            JOptionPane.showMessageDialog(this,
                    "Baja del taller registrada con éxito.",
                    "Baja exitosa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void darDeBajaTotal() {
        if (asistenteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un asistente primero",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Dar de baja TOTAL a " + asistenteSeleccionado.getNombreCompleto() + "?\n" +
                        "Se eliminarán todas sus inscripciones y su registro.",
                "Confirmar baja total", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            asistentes.remove(asistenteSeleccionado);
            GestorArchivos.guardarAsistentes(asistentes);
            asistenteSeleccionado = null;
            cargarAsistentes();
            JOptionPane.showMessageDialog(this,
                    "Asistente dado de baja con éxito.",
                    "Baja total exitosa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void recargarDatos(){
        asistentes = GestorArchivos.leerAsistentes();
        cargarAsistentes();
    }
}