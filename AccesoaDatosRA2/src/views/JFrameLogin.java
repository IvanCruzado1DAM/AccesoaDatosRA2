package views;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import models.Empleado;
import services.ConexionBDSql;
import services.Test;

public class JFrameLogin extends JFrame {

	private JButton Enter, Close, Register;
	private final Icon IconRegister = new ImageIcon("icons/IconRegister.png"),
			IconEnter = new ImageIcon("icons/IconGetIn.png"), IconClose = new ImageIcon("icons/IconSignOff.png");
	private JLabel User, Password;
	private JTextField Usertext;
	private JPasswordField Passwordtext;
	protected static Empleado EmActivo; // Almacenamos aqui el Empleado que ha entrado para
	// poder acceder a el en todas las vistas

	public JFrameLogin() {
		super("Inicio Sesion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(653, 408);
		setResizable(false);
		setLocationRelativeTo(null);
		// icono
		ImageIcon icono = new ImageIcon("icons/IconRegister.png");
		setIconImage(icono.getImage());

		setContentPane(new JPanel() {
			BufferedImage backgroundImage;
			{
				try {
//---------------------------Carga de imagen de fondo--------------------------//
					backgroundImage = ImageIO.read(new File("background/backgroundLogin.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
//-------------------------------Dibuja la imagen de fondo------------------------//
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		});

		User = new JLabel("Usuario: ");
		User.setBounds(84, 84, 79, 44);
		User.setFont(new Font("Arial", Font.PLAIN, 16));

		Password = new JLabel("Clave: ");
		Password.setBounds(84, 162, 54, 37);
		Password.setFont(new Font("Arial", Font.PLAIN, 16));

		Usertext = new JTextField(20);
		Usertext.setBounds(215, 93, 300, 30);

		Passwordtext = new JPasswordField(20);
		Passwordtext.setBounds(215, 169, 300, 30);

		Enter = new JButton("Entrar");
		Enter.setBounds(99, 244, 160, 60);
		Enter.setFont(new Font("Arial", Font.PLAIN, 16));
		Enter.setIcon(IconEnter);
		Enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (Usertext.getText().isEmpty() || Passwordtext.getPassword().length == 0) {
						JOptionPane.showMessageDialog(JFrameLogin.this, "*Rellene todos los campos", "Aviso",
								JOptionPane.ERROR_MESSAGE);
						//Obligamos a que el usertext tenga letra mayuscula al principio, luego tenga letras minusculas, mayuscualas o letras
						//hasta llegar a 16 o minimo 6
					} else if (!Usertext.getText()
							.matches("^[A-Z](?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{6,16}$")) {
						JOptionPane.showMessageDialog(JFrameLogin.this,
								"Campo Usuario \n" + "*Debe tener primer caracter en Mayuscula \n "
										+ "Debe tener letras, al menos un numero \n"
										+ "*Y puede tener un caracter especial",
								"Aviso", JOptionPane.ERROR_MESSAGE);
						//Comprueba si hay al menos una minuscula, mayuscula y numero, debe tener longitud de al menos 6 a 10
					} else if (!String.valueOf(Passwordtext.getPassword())
							.matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{6,10}$")) {
						JOptionPane.showMessageDialog(
								JFrameLogin.this, "CAMPO CLAVE \n" + "*Debe contener 1 Digito\n"
										+ "*Debe tener Mayuscula y minuscula\n " + "*Debe tener longitud entre 6 a 10",
								"Aviso", JOptionPane.ERROR_MESSAGE);
					} else if (!Usertext.getText().isEmpty() && !(Passwordtext.getPassword().length == 0)) {
						if (Test.os.getExisteEmpleado(ConexionBDSql.obtener(), Usertext.getText(),
								String.valueOf(Passwordtext.getPassword())).toString() == null) {
						} else {
							JOptionPane.showMessageDialog(JFrameLogin.this, "Bienvenido " + Usertext.getText());
							//Guardamos el empleado que ha iniciado
							EmActivo = Test.os.getExisteEmpleado(ConexionBDSql.obtener(), Usertext.getText(),
									String.valueOf(Passwordtext.getPassword()));
							dispose();
							JFrameAdmin jf = new JFrameAdmin();
							jf.setVisible(true);
						}
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(JFrameLogin.this, "El Usuario no existe (puede registrarlo)",
							"Informacion", JOptionPane.INFORMATION_MESSAGE);
					Passwordtext.setText("");
				}
			}
		});

		Register = new JButton("Registrar");
		Register.setFont(new Font("Arial", Font.PLAIN, 16));
		Register.setBounds(360, 244, 160, 60);
		Register.setIcon(IconRegister);
		Register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				JFrameRegister jf = new JFrameRegister();
				jf.setVisible(true);
				Usertext.setText("");
				Passwordtext.setText("");
			}
		});

		Close = new JButton("Cerrar");
		Close.setBounds(477, 10, 141, 49);
		Close.setFont(new Font("Arial", Font.PLAIN, 16));
		Close.setIcon(IconClose);
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
		getContentPane().add(User);
		getContentPane().add(Password);
		getContentPane().add(Usertext);
		getContentPane().add(Passwordtext);
		getContentPane().add(Enter);
		getContentPane().add(Close);
		getContentPane().add(Register);
	}
}
