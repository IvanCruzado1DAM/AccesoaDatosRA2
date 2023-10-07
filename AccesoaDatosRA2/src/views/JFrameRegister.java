package views;

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
import java.awt.Font;

public class JFrameRegister extends JFrame {

	private JLabel Username, Password;
	private JTextField Usernametext;
	private JPasswordField Passwordtext;
	private JButton Register, Close, Login;

	public JFrameRegister() {
		super("Registro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(654, 424);
		setResizable(false);
		setLocationRelativeTo(null);

		Username = new JLabel("Username: ");
		Username.setFont(new Font("Arial", Font.PLAIN, 16));
		Username.setBounds(106, 92, 86, 45);

		Password = new JLabel("Password: ");
		Password.setFont(new Font("Arial", Font.PLAIN, 16));
		Password.setBounds(106, 159, 78, 45);

		Usernametext = new JTextField(20);
		Usernametext.setBounds(247, 101, 300, 30);

		Passwordtext = new JPasswordField(20);
		Passwordtext.setBounds(247, 168, 300, 30);

		Register = new JButton("Register");
		Register.setBounds(247, 278, 120, 60);
		Register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (Usernametext.getText().isEmpty() || Passwordtext.getPassword().length == 0) {
						JOptionPane.showMessageDialog(JFrameRegister.this, "*Rellene todos los campos", "Aviso",
								JOptionPane.ERROR_MESSAGE);
					} else if (!Usernametext.getText().matches("^[A-Z][a-z]+")) {
						JOptionPane.showMessageDialog(JFrameRegister.this,
								"*Campo Usuario debe tener solo letras\n" + "*Debe tener primer caracter en Mayuscula",
								"Aviso", JOptionPane.ERROR_MESSAGE);
					} else if (!(Passwordtext.getPassword().length > 4)) {
						JOptionPane.showMessageDialog(JFrameRegister.this, "*Campo Clave debe tener longitud mayor a 4\n",
								"Aviso", JOptionPane.ERROR_MESSAGE);
					} else if (!Usernametext.getText().isEmpty() && !(Passwordtext.getPassword().length == 0)) {
						if (Test.os.getExisteEmpleado(ConexionBDSql.obtener(), Usernametext.getText()) == null) {
							Empleado e1 = new Empleado(Usernametext.getText(),
									String.valueOf(Passwordtext.getPassword()));
							Test.os.saveEmpleado(ConexionBDSql.obtener(), e1, 1);
							JOptionPane.showMessageDialog(JFrameRegister.this, "Usuario Registrado", "Informacion",
									JOptionPane.INFORMATION_MESSAGE);
						};
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

		Login = new JButton("Login");
		Login.setBounds(442, 278, 120, 60);
		Login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				JFrameLogin jf = new JFrameLogin();
				jf.setVisible(true);
			}
		});

		Close = new JButton("Close");
		Close.setBounds(64, 278, 120, 60);
		Close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		getContentPane().setLayout(null);

		getContentPane().add(Username);
		getContentPane().add(Password);
		getContentPane().add(Usernametext);
		getContentPane().add(Passwordtext);
		getContentPane().add(Close);
		getContentPane().add(Login);
		getContentPane().add(Register);
	}
}
