package views;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import models.Producto;
import models.Proveedor;
import services.ConexionBDSql;
import services.ObjectService;
import javax.swing.SwingConstants;

public class AddProveedor extends JFrame {

	// Product window add-modify
	private JFrame ProductWindowAM;
	private JLabel lblInsert, lblName, lblBrand, lblPrice, lblSupplier, lblStock, lblCategory;
	private JTextField txtName, txtBrand, txtPrice, txtStock, txtCategory;
	private JButton btnBack, btnInsert;
	

	ObjectService OS = new ObjectService();
	public static int productId;
	private Producto product;

	public AddProveedor() throws ClassNotFoundException, SQLException {
	    createWindow();
	}

	private void createWindow() throws ClassNotFoundException, SQLException {
	    ProductWindowAM = new JFrame("Menú");
	    ProductWindowAM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    ProductWindowAM.setBounds(100, 100, 420, 331);
	    ProductWindowAM.setLocationRelativeTo(null);
	    ProductWindowAM.getContentPane().setLayout(null);

	    lblName = new JLabel("Nombre:");
	    lblName.setBounds(96, 110, 56, 18);
	    ProductWindowAM.getContentPane().add(lblName);

	    lblBrand = new JLabel("Dirección:");
	    lblBrand.setBounds(93, 146, 70, 18);
	    ProductWindowAM.getContentPane().add(lblBrand);

	    lblPrice = new JLabel("Número:");
	    lblPrice.setBounds(96, 184, 56, 18);
	    ProductWindowAM.getContentPane().add(lblPrice);

	    txtName = new JTextField();
	    txtName.setBounds(185, 110, 96, 19);
	    ProductWindowAM.getContentPane().add(txtName);
	    txtName.setColumns(10);

	    txtBrand = new JTextField();
	    txtBrand.setColumns(10);
	    txtBrand.setBounds(185, 146, 96, 19);
	    ProductWindowAM.getContentPane().add(txtBrand);

	    txtPrice = new JTextField();
	    txtPrice.setColumns(10);
	    txtPrice.setBounds(185, 184, 96, 19);
	    ProductWindowAM.getContentPane().add(txtPrice);
	    

	    btnInsert = new JButton("Insertar");
	    ImageIcon iconoRegisterOriginal = new ImageIcon("./icons/IconInsert.png");
		Image imagenRegisterOriginal = iconoRegisterOriginal.getImage();
		Image nuevaImagenRegister =imagenRegisterOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon iconoRegisterRedimensionado = new ImageIcon(nuevaImagenRegister);
		btnInsert.setIcon(iconoRegisterRedimensionado);
	    btnInsert.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	}
	    });
	    btnInsert.setBounds(141, 230, 107, 33);
	    ProductWindowAM.getContentPane().add(btnInsert);

	    btnBack = new JButton("Volver");
	    ImageIcon iconoExitOriginal = new ImageIcon("./icons/IconReturn.png");
		Image imagenExitOriginal = iconoExitOriginal.getImage();
		Image nuevaImagenExit =imagenExitOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon iconoExitRedimensionado = new ImageIcon(nuevaImagenExit);
		btnBack.setIcon(iconoExitRedimensionado);
	    btnBack.setBounds(289, 10, 107, 33);
	    ProductWindowAM.getContentPane().add(btnBack);
	    
	    JLabel lblNewLabel = new JLabel("Insertar Proveedor");
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 27));
	    lblNewLabel.setBounds(50, 41, 300, 52);
	    ProductWindowAM.getContentPane().add(lblNewLabel);
	    ProductWindowAM.setVisible(true);
	}

}
