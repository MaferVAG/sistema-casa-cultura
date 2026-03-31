package vistas;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    private JTabbedPane pestanas;

    public VentanaPrincipal(){
        setTitle("Casa de la Cultura - UAM Azcapotzalco :: Menú Principal");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        pestanas = new JTabbedPane(EXIT_ON_CLOSE);
        pestanas.setTabPlacement(JTabbedPane.TOP);;
        pestanas.addChangeListener(e -> {
            int index = pestanas.getSelectedIndex();
            if (index == 3) {
                PanelAdministracion panel = (PanelAdministracion) pestanas.getComponentAt(3);
                panel.recargarDatos();
            }
        });

        pestanas.addTab("Registro de Asistentes", new PanelRegistro());
        pestanas.addTab("Inscripción a Talleres", new PanelInscripcion());
        pestanas.addTab("Consultar Asistentes", new PanelConsulta());
        pestanas.addTab("Administración de Inscripciones", new PanelAdministracion());

        pestanas.setBackground(Estilos.FONDO);
        pestanas.setForeground(Estilos.VINO);
        pestanas.setFont(Estilos.FUENTE_SECCION);

        add(pestanas);

        setVisible(true);
    }
}
