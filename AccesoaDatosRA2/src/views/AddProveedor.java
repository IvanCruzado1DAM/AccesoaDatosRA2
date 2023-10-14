package views;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.Producto;
import models.Proveedor;
import services.ConexionBDSql;
import services.ObjectService;
import services.Test;

import javax.swing.SwingConstants;
import java.awt.Window.Type;

public class AddProveedor extends JFrame {

	// Product window add-modify
	private JFrame SupplierWindowAM;
	private JLabel lblName, lblAddress, lblNumber;
	private JTextField txtName, txtAddress, txtNumber;
	private JButton btnBack, btnInsert;
	

	ObjectService OS = new ObjectService();

	public AddProveedor() throws ClassNotFoundException, SQLException {
	    createWindow();
	}

	private void createWindow() throws ClassNotFoundException, SQLException {
		SupplierWindowAM = new JFrame("Menú");
		SupplierWindowAM.setTitle("Menú insertar proveedor");
		SupplierWindowAM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SupplierWindowAM.setBounds(100, 100, 420, 331);
		SupplierWindowAM.setLocationRelativeTo(null);
		SupplierWindowAM.setContentPane(new JPanel() {
			BufferedImage backgroundImage;
			{
				try {
//---------------------------Load your background image--------------------------//
					backgroundImage = ImageIO.read(new File("background/backgroundTransactions.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
//-------------------------------Draw the background image------------------------//
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		});
		SupplierWindowAM.getContentPane().setLayout(null);
	    
	    lblName = new JLabel("Nombre:");
	    lblName.setBounds(96, 110, 56, 18);
	    SupplierWindowAM.getContentPane().add(lblName);

	    lblAddress = new JLabel("Dirección:");
	    lblAddress.setBounds(93, 146, 70, 18);
	    SupplierWindowAM.getContentPane().add(lblAddress);

	    lblNumber = new JLabel("Número:");
	    lblNumber.setBounds(96, 184, 56, 18);
	    SupplierWindowAM.getContentPane().add(lblNumber);

	    txtName = new JTextField();
	    txtName.setBounds(185, 110, 96, 19);
	    SupplierWindowAM.getContentPane().add(txtName);
	    txtName.setColumns(10);

	    txtAddress = new JTextField();
	    txtAddress.setColumns(10);
	    txtAddress.setBounds(185, 146, 96, 19);
	    SupplierWindowAM.getContentPane().add(txtAddress);

	    txtNumber = new JTextField();
	    txtNumber.setColumns(10);
	    txtNumber.setBounds(185, 184, 96, 19);
	    SupplierWindowAM.getContentPane().add(txtNumber);
	    

	    btnInsert = new JButton("Insertar");
	    ImageIcon iconoRegisterOriginal = new ImageIcon("icons/IconInsert.png");
		Image imagenRegisterOriginal = iconoRegisterOriginal.getImage();
		Image nuevaImagenRegister =imagenRegisterOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon iconoRegisterRedimensionado = new ImageIcon(nuevaImagenRegister);
		btnInsert.setIcon(iconoRegisterRedimensionado);
	    btnInsert.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(txtNumber.getText().matches("[0-9]{9}")) {
	    			Proveedor p=new Proveedor(txtName.getText(),txtAddress.getText(),Integer.parseInt(txtNumber.getText()));
		    		try {
						Test.os.saveProveedor(ConexionBDSql.obtener(), p,1);
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		SupplierWindowAM.dispose();
		    		try {
						CrudProveedor cp=new CrudProveedor();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    		}else {
	    			JOptionPane.showMessageDialog(AddProveedor.this,"El campo 'Número' solo admite 9 números","Error",JOptionPane.ERROR_MESSAGE);
	    		}
	    		
	    	}
	    });
	    btnInsert.setBounds(141, 230, 107, 33);
	    SupplierWindowAM.getContentPane().add(btnInsert);

	    btnBack = new JButton("Volver");
	    btnBack.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		SupplierWindowAM.dispose();
	    		try {
					CrudProveedor cp=new CrudProveedor();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	}
	    });
	    ImageIcon iconoExitOriginal = new ImageIcon("./icons/IconReturn.png");
		Image imagenExitOriginal = iconoExitOriginal.getImage();
		Image nuevaImagenExit =imagenExitOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon iconoExitRedimensionado = new ImageIcon(nuevaImagenExit);
		btnBack.setIcon(iconoExitRedimensionado);
	    btnBack.setBounds(289, 10, 107, 33);
	    SupplierWindowAM.getContentPane().add(btnBack);
	    
	    JLabel lblNewLabel = new JLabel("Insertar Proveedor");
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 27));
	    lblNewLabel.setBounds(50, 41, 300, 52);
	    SupplierWindowAM.getContentPane().add(lblNewLabel);
	    SupplierWindowAM.setVisible(true);
	}

}
