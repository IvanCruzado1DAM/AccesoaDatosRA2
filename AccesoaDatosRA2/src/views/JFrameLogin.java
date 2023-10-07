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

	private JButton Enter, Close, Register;
	private JLabel Usuario, Clave;
	private JTextField Usuariotext;
	private JPasswordField Clavetext;
	protected static Empleado EmActivo; //Almacenamos aqui el Empleado que ha entrado para 
	//poder acceder a el en todas las vistas

	public JFrameLogin() {
		super("Inicio Sesion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(653, 408);
		setResizable(false);
		setLocationRelativeTo(null);

		Usuario = new JLabel("Usuario: ");
		Usuario.setBounds(85, 69, 79, 44);
		Usuario.setFont(new Font("Arial", Font.PLAIN, 16));

		Clave = new JLabel("Clave: ");
		Clave.setBounds(85, 147, 54, 37);
		Clave.setFont(new Font("Arial", Font.PLAIN, 16));

		Usuariotext = new JTextField(20);
		Usuariotext.setBounds(216, 78, 300, 30);

		Clavetext = new JPasswordField(20);
		Clavetext.setBounds(216, 154, 300, 30);

		Enter = new JButton("Enter");
		Enter.setBounds(249, 267, 120, 60);
		Enter.setFont(new Font("Arial", Font.PLAIN, 16));
		Enter.addActionListener(new ActionListener() {
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
				}else if(!(Clavetext.getPassword().length>4)) {
					JOptionPane.showMessageDialog(JFrameLogin.this, "*Campo Clave debe tener longitud mayor a 4\n"
							, "Aviso",
							JOptionPane.ERROR_MESSAGE);
				}else if(!Usuariotext.getText().isEmpty() && !(Clavetext.getPassword().length==0)) {
					boolean encontrado = false;
						for (Empleado em : Test.os.getAllEmpleados(ConexionBDSql.obtener())) {
							if (em.getUsername().equals(Usuariotext.getText())
									&& em.getPassword().equals(String.valueOf(Clavetext.getPassword()))) {
								JOptionPane.showMessageDialog(JFrameLogin.this, "Bienvenido " + Usuariotext.getText());
							    encontrado = true;
								EmActivo = em;
							    dispose();
							    JFrameAdmin jf = new JFrameAdmin ();
							    jf.setVisible(true);
							}
						}
						if(encontrado==false) {
							JOptionPane.showMessageDialog(JFrameLogin.this, "El Empleado no existe ");
						    Usuariotext.setText("");
						    Clavetext.setText("");
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
		
		Register = new JButton ("Register");
		Register.setFont(new Font("Arial", Font.PLAIN, 16));
		Register.setBounds(422, 267, 120, 60);
		Register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				JFrameRegister jf = new JFrameRegister ();
				jf.setVisible(true);
				Usuariotext.setText("");
				Clavetext.setText("");
			}
		});

		Close = new JButton("Close");
		Close.setBounds(70, 267, 120, 60);
		Close.setFont(new Font("Arial", Font.PLAIN, 16));
		Close.addActionListener(new ActionListener() {
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
		getContentPane().add(Enter);
		getContentPane().add(Close);
		getContentPane().add(Register);
	}
}
