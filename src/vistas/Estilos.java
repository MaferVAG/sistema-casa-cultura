package vistas;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Estilos {

    //COLORES
    public static final Color VINO        = new Color(100, 0, 30);
    public static final Color VINO_CLARO  = new Color(140, 20, 50);
    public static final Color ROJO        = new Color(180, 30, 50);
    public static final Color FONDO       = new Color(245, 240, 242);
    public static final Color FONDO_PANEL = new Color(255, 250, 252);
    public static final Color BLANCO      = Color.WHITE;
    public static final Color TEXTO       = new Color(40, 10, 15);
    public static final Color TEXTO_CLARO = new Color(120, 80, 90);
    public static final Color BORDE       = new Color(200, 170, 175);
    public static final Color ACENTO      = new Color(220, 50, 70);

    //TYPO
    public static final Font FUENTE_TITULO   = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FUENTE_SECCION  = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FUENTE_LABEL    = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FUENTE_CAMPO    = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FUENTE_BOTON    = new Font("Segoe UI", Font.BOLD, 11);
    public static final Font FUENTE_TABLA    = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FUENTE_TOTAL    = new Font("Segoe UI", Font.BOLD, 13);

    //BOTON PRINC
    public static void aplicarBotonPrincipal(JButton btn) {
        btn.setBackground(VINO);
        btn.setForeground(BLANCO);
        btn.setFont(FUENTE_BOTON);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        //HOVER EFECTO
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(VINO_CLARO);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(VINO);
            }
        });
    }

    //BOTON SEC
    public static void aplicarBotonSecundario(JButton btn) {
        btn.setBackground(FONDO);
        btn.setForeground(VINO);
        btn.setFont(FUENTE_BOTON);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(VINO, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(235, 220, 225));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(FONDO);
            }
        });
    }

    //BOTON !!
    public static void aplicarBotonPeligro(JButton btn) {
        btn.setBackground(new Color(160, 20, 30));
        btn.setForeground(BLANCO);
        btn.setFont(FUENTE_BOTON);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(200, 30, 45));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(160, 20, 30));
            }
        });
    }

    //CAMPO TEXTO
    public static void aplicarCampo(JTextField campo) {
        campo.setFont(FUENTE_CAMPO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE, 1),
                new EmptyBorder(3, 6, 3, 6)
        ));
        campo.setBackground(BLANCO);
        campo.setForeground(TEXTO);
    }

    public static void aplicarCampo(JPasswordField campo) {
        campo.setFont(FUENTE_CAMPO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE, 1),
                new EmptyBorder(3, 6, 3, 6)
        ));
        campo.setBackground(BLANCO);
        campo.setForeground(TEXTO);
    }

    public static void aplicarCampo(JComboBox<?> combo) {
        combo.setFont(FUENTE_CAMPO);
        combo.setBackground(BLANCO);
        combo.setForeground(TEXTO);
    }

    //TABLA
    public static void aplicarTabla(JTable tabla) {
        tabla.setFont(FUENTE_TABLA);
        tabla.setRowHeight(24);
        tabla.setGridColor(new Color(230, 215, 220));
        tabla.setSelectionBackground(new Color(180, 30, 50));
        tabla.setSelectionForeground(BLANCO);
        tabla.setShowHorizontalLines(true);
        tabla.setShowVerticalLines(false);
        tabla.getTableHeader().setFont(FUENTE_SECCION);
        tabla.getTableHeader().setBackground(VINO);
        tabla.getTableHeader().setForeground(BLANCO);
        tabla.setBackground(BLANCO);
        tabla.setFillsViewportHeight(true);
    }

    //PANEL
    public static void aplicarPanel(JPanel panel) {
        panel.setBackground(FONDO_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE, 1),
                new EmptyBorder(8, 8, 8, 8)
        ));
    }

    //LABEL SECCION
    public static JLabel crearLabelSeccion(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FUENTE_SECCION);
        lbl.setForeground(VINO);
        return lbl;
    }

    //LABEL NORMAL
    public static JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FUENTE_LABEL);
        lbl.setForeground(TEXTO);
        return lbl;
    }
}