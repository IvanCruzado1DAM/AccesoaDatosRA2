package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

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

public class AddModifyProveedor extends JFrame {

	// Product window add-modify
	private JFrame ProductWindowAM;
	private JLabel lblInsert, lblName, lblBrand, lblPrice, lblSupplier, lblStock, lblCategory;
	private JTextField txtName, txtBrand, txtPrice, txtStock, txtCategory;
	private JButton btnBack, btnInsert;
	
	Manejador mane=new Manejador();

	ObjectService OS = new ObjectService();
	public static int productId;
	private Producto product;

	public AddModifyProveedor() throws ClassNotFoundException, SQLException {
	    createWindow();
	}

	private void createWindow() throws ClassNotFoundException, SQLException {
	    ProductWindowAM = new JFrame("Menú");
	    ProductWindowAM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    ProductWindowAM.setBounds(100, 100, 420, 419);
	    ProductWindowAM.setLocationRelativeTo(null);
	    ProductWindowAM.getContentPane().setLayout(null);

	    lblName = new JLabel("Nombre:");
	    lblName.setBounds(62, 110, 102, 18);
	    ProductWindowAM.getContentPane().add(lblName);

	    lblBrand = new JLabel("Dirección:");
	    lblBrand.setBounds(62, 146, 102, 18);
	    ProductWindowAM.getContentPane().add(lblBrand);

	    lblPrice = new JLabel("Número:");
	    lblPrice.setBounds(62, 184, 102, 18);
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

	    JComboBox<Proveedor> comboBox = new JComboBox<>();
	    List<Proveedor> listProveedor= OS.getAllProveedor(ConexionBDSql.obtener());
	    for(Proveedor p:listProveedor) {
	    	comboBox.addItem(p);
	    }
	    
	    comboBox.setBounds(185, 225, 96, 19);
	    ProductWindowAM.getContentPane().add(comboBox);

	    btnInsert = new JButton("Insert");
	    btnInsert.setBounds(67, 314, 85, 21);
	    ProductWindowAM.getContentPane().add(btnInsert);

	    btnBack = new JButton("Back");
	    btnBack.setBounds(216, 314, 85, 21);
	    btnBack.addActionListener(mane);
	    ProductWindowAM.getContentPane().add(btnBack);
	    
	    JLabel lblNewLabel = new JLabel("Editar Proveedor");
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 27));
	    lblNewLabel.setBounds(51, 21, 300, 52);
	    ProductWindowAM.getContentPane().add(lblNewLabel);

	    if (productId == 0) {

	        // Label choose an option
	        lblInsert = new JLabel("Insert Menu");
	        lblInsert.setToolTipText("text choice");
	        lblInsert.setFont(new Font("Tahoma", Font.PLAIN, 25));
	        lblInsert.setBounds(100, 10, 223, 66);
	        ProductWindowAM.getContentPane().add(lblInsert);

	    } else {
	        // Label choose an option
	        lblInsert = new JLabel("Modify Menu");
	        lblInsert.setToolTipText("text choice");
	        lblInsert.setFont(new Font("Tahoma", Font.PLAIN, 25));
	        lblInsert.setBounds(100, 10, 223, 66);
	        ProductWindowAM.getContentPane().add(lblInsert);

	        try {
	            product = OS.getProduct(ConexionBDSql.obtener(), productId);

	        } catch (ClassNotFoundException | SQLException e) {

	            e.printStackTrace();
	        }
	        // Fill with all the data
	        txtName.setText(product.getNombre());
	        txtCategory.setText(product.getCategoria());
	        txtBrand.setText(product.getMarca());
	        txtStock.setText(String.valueOf(product.getStock()));
	        txtPrice.setText(String.valueOf(product.getPrecio()));
	    }

	    ProductWindowAM.setVisible(true);
	}

	private class Manejador implements ActionListener {

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        Object obj = e.getSource();
	        if (obj == btnInsert) {

	            productId = 0;
	        } else if (obj == btnBack) {
	            productId = 0;
	            try {
					new CrudProducto();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            ProductWindowAM.setVisible(false);

	        }
	    }
	}
}