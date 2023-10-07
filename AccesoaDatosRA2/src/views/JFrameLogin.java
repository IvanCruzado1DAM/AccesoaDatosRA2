package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import models.Empleado;
import services.ConexionBDSql;
import services.Test;

public class JFrameLogin extends JFrame {

	private JButton Entrar, Salir;
	private JLabel Usuario, Clave;
	private JTextField Usuariotext;
	private JPasswordField Clavetext;
	protected Empleado EmActivo; //Almacenamos aqui el Empleado que ha entrado para poder acceder a el en todas las vistas

	public JFrameLogin() {
		super("Inicio Sesion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(653, 408);
		setResizable(false);
		setLocationRelativeTo(null);

		Usuario = new JLabel("Usuario: ");
		Usuario.setFont(new Font("Arial", Font.PLAIN, 16));
		Usuario.setBounds(85, 69, 79, 44);

		Clave = new JLabel("Clave: ");
		Clave.setFont(new Font("Arial", Font.PLAIN, 16));
		Clave.setBounds(85, 147, 54, 37);

		Usuariotext = new JTextField(20);
		Usuariotext.setBounds(216, 78, 300, 30);

		Clavetext = new JPasswordField(20);
		Clavetext.setBounds(216, 154, 300, 30);

		Entrar = new JButton("Entrar");
		Entrar.setFont(new Font("Arial", Font.PLAIN, 16));
		Entrar.setBounds(81, 267, 120, 60);
		Entrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (Usuariotext.getText().isEmpty() || Clavetext.getPassword().length==0) {
						JOptionPane.showMessageDialog(JFrameLogin.this, "*Rellene todos los campos", "Aviso",
								JOptionPane.ERROR_MESSAGE);
				}else if(!Usuariotext.getText().matches("^[A-Z][a-z]+")) {
						JOptionPane.showMessageDialog(JFrameLogin.this, "*Campo Usuario debe tener solo letras\n"
								+ "*Debe tener primer caracter en Mayuscula", "Aviso",
								JOptionPane.ERROR_MESSAGE);
					
				}else if(!Usuariotext.getText().isEmpty() && !(Clavetext.getPassword().length==0)) {
						for (Empleado em : Test.os.getAllEmpleados(ConexionBDSql.obtener())) {
							if (em.getUsername().equals(Usuariotext.getText())
									&& em.getPassword().equals(String.valueOf(Clavetext.getPassword()))) {
								JOptionPane.showMessageDialog(JFrameLogin.this, "Bienvenido " + Usuario.getText());
							    EmActivo = em;
							}
						}
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		Salir = new JButton("Salir");
		Salir.setFont(new Font("Arial", Font.PLAIN, 16));
		Salir.setBounds(377, 267, 120, 60);
		Salir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (JOptionPane.showConfirmDialog(JFrameLogin.this, "Desea salir del programa?", "Aviso",
						JOptionPane.INFORMATION_MESSAGE) == 0)
					dispose();
			}
		});
		getContentPane().setLayout(null);

		getContentPane().add(Usuario);
		getContentPane().add(Clave);
		getContentPane().add(Usuariotext);
		getContentPane().add(Clavetext);
		getContentPane().add(Entrar);
		getContentPane().add(Salir);
	}
}
