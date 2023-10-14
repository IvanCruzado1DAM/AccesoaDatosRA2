package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import java.awt.Font;
import java.awt.Graphics;

public class JFrameRegister extends JFrame {

	private JLabel Username, Password;
	private JTextField Usernametext;
	private JPasswordField Passwordtext;
	private JButton Register, Close, Login;
	private final Icon IconRegister = new ImageIcon("icons/IconRegister.png"), IconReturn = new ImageIcon("icons/IconReturn.png")
			,IconClose = new ImageIcon("icons/IconSignOff.png");

	public JFrameRegister() {
		super("Registro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(654, 424);
		setResizable(false);
		setLocationRelativeTo(null);
		
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

		Username = new JLabel("Nombre Usuario: ");
		Username.setFont(new Font("Arial", Font.PLAIN, 16));
		Username.setBounds(66, 92, 141, 45);

		Password = new JLabel("Contraseña: ");
		Password.setFont(new Font("Arial", Font.PLAIN, 16));
		Password.setBounds(102, 159, 89, 45);

		Usernametext = new JTextField(20);
		Usernametext.setBounds(217, 101, 300, 30);

		Passwordtext = new JPasswordField(20);
		Passwordtext.setBounds(217, 168, 300, 30);

		Register = new JButton("Registrar");
		Register.setBounds(234, 278, 160, 60);
		Register.setIcon(IconRegister);
		Register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (Usernametext.getText().isEmpty() || Passwordtext.getPassword().length == 0) {
						JOptionPane.showMessageDialog(JFrameRegister.this, "*Rellene todos los campos", "Aviso",
								JOptionPane.ERROR_MESSAGE);
						//Obligamos a que el usertext tenga letra mayuscula al principio, luego tenga letras minusculas, mayuscualas o letras
						//hasta llegar a 16 o minimo 6
					} else if (!Usernametext.getText().matches("^[A-Z](?=\\w*\\d)(?=\\w*[a-z])\\S{6,16}$")) {
						JOptionPane.showMessageDialog(JFrameRegister.this,
								"Campo Usuario \n" + "*Debe tener primer caracter en Mayuscula \n " + "Debe tener letras, al menos un numero \n"
						        + "*Y puede tener un caracter especial",
								"Aviso", JOptionPane.ERROR_MESSAGE);
						//Comprueba si hay al menos una minuscula, mayuscula y numero, debe tener longitud de al menos 6 a 10
					} else if (!String.valueOf(Passwordtext.getPassword()).matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{6,10}$")) {
						JOptionPane.showMessageDialog(JFrameRegister.this, "CAMPO CLAVE \n" + "*Debe contener 1 Digito\n"
								+"*Debe tener Mayuscula y minuscula\n "+ "*Debe tener longitud entre 6 a 10",
								"Aviso", JOptionPane.ERROR_MESSAGE);
					} else if (!Usernametext.getText().isEmpty() && !(Passwordtext.getPassword().length == 0)) {
						//Si los campos no estan vacios y el usuario no existe se registrara sino, nos avisara de que existe
						if (Test.os.getExisteEmpleado(ConexionBDSql.obtener(), Usernametext.getText(), String.valueOf(Passwordtext.getPassword())) == null){
							Empleado e1 = new Empleado(Usernametext.getText(),
									String.valueOf(Passwordtext.getPassword()));
							Test.os.saveEmpleado(ConexionBDSql.obtener(), e1, 1);
							JOptionPane.showMessageDialog(JFrameRegister.this, "Empleando Registrado", "Informacion",
									JOptionPane.INFORMATION_MESSAGE);
							Usernametext.setText("");
							Passwordtext.setText("");
						}else {
							JOptionPane.showMessageDialog(JFrameRegister.this, "Empleando Ya existe", "Informacion",
									JOptionPane.INFORMATION_MESSAGE);
							Passwordtext.setText("");
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

		Login = new JButton("Login");
		Login.setBounds(422, 278, 160, 60);
		Login.setIcon(IconReturn);
		Login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				JFrameLogin jf = new JFrameLogin();
				jf.setVisible(true);
			}
		});

		Close = new JButton("Cerrar");
		Close.setBounds(31, 278, 160, 60);
		Close.setIcon(IconClose);
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
