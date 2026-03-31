package vistas;

import datos.GestorArchivos;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JButton btnCancelar;

    public VentanaLogin() {
        setTitle("Casa de la Cultura - UAM Azcapotzalco :: Inicio de Sesión");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setBounds(130, 20, 200, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo);

        lblUsuario = new JLabel("Usuario");
        lblUsuario.setBounds(60, 80, 80, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(150, 80, 180, 25);
        add(txtUsuario);

        lblContrasena = new JLabel("Contraseña");
        lblContrasena.setBounds(60, 120, 80, 25);
        add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(150, 120, 180, 25);
        add(txtContrasena);

        btnIngresar= new JButton("Ingresar");
        btnIngresar.setBounds(90, 180, 100, 30);
        add(btnIngresar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(210, 180, 100, 30);
        add(btnCancelar);

        btnIngresar.addActionListener(e ->{
            String usuario = txtUsuario.getText();
            String contrasena = new String(txtContrasena.getPassword());

            String[] credenciales = GestorArchivos.leerAdmin();

            if (usuario.equals(credenciales[0]) && contrasena.equals(credenciales[1])){
                new VentanaPrincipal().setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(this,
                        "Usuario o contraseña incorrectos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> System.exit((0)));

        //ESRILO
        getContentPane().setBackground(Estilos.FONDO);
        lblTitulo.setFont(Estilos.FUENTE_TITULO);
        lblTitulo.setForeground(Estilos.VINO);
        lblUsuario.setFont(Estilos.FUENTE_LABEL);
        lblContrasena.setFont(Estilos.FUENTE_LABEL);
        Estilos.aplicarCampo(txtUsuario);
        Estilos.aplicarCampo(txtContrasena);
        Estilos.aplicarBotonPrincipal(btnIngresar);
        Estilos.aplicarBotonSecundario(btnCancelar);

        setVisible(true);


    }

}
