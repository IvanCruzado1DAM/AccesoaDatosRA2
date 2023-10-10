package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.Producto;
import models.Proveedor;
import services.ConexionBDSql;
import services.ObjectService;


public class AddModifyProduct extends JFrame {

	// Product window add-modify
	private JFrame ProductWindowAM;
	private JLabel lblInsert, lblName, lblBrand, lblPrice, lblSupplier, lblStock, lblCategory, lblImage;
	private JTextField txtName, txtBrand, txtPrice, txtStock, txtCategory, txtPath;
	private JButton btnBack, btnInsert, btnModify,btnAddImg;
	private JComboBox<Proveedor> comboBox;

	ManejadorBtn maneBtn = new ManejadorBtn();
	//manejador imagen
	insertImg im = new insertImg();

	
	ObjectService OS = new ObjectService();
	public static int productId;
	private Producto product;

	// imagen
	private String extension;
	private Path sourcer, destination;

	public AddModifyProduct() throws ClassNotFoundException, SQLException {
		createWindow();
	}

	private void createWindow() throws ClassNotFoundException, SQLException {

		ProductWindowAM = new JFrame("Insert Menu");
		ProductWindowAM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ProductWindowAM.setBounds(100, 100, 420, 580);
		ProductWindowAM.setLocationRelativeTo(null);
		ProductWindowAM.getContentPane().setLayout(null);

		lblName = new JLabel("Name:");
		lblName.setBounds(62, 110, 102, 18);
		ProductWindowAM.getContentPane().add(lblName);

		lblBrand = new JLabel("Brand:");
		lblBrand.setBounds(62, 146, 102, 18);
		ProductWindowAM.getContentPane().add(lblBrand);

		lblPrice = new JLabel("Price:");
		lblPrice.setBounds(62, 184, 102, 18);
		ProductWindowAM.getContentPane().add(lblPrice);

		lblSupplier = new JLabel("Supplier:");
		lblSupplier.setBounds(62, 226, 102, 18);
		ProductWindowAM.getContentPane().add(lblSupplier);

		lblStock = new JLabel("Stock:");
		lblStock.setBounds(62, 268, 102, 18);
		ProductWindowAM.getContentPane().add(lblStock);

		lblCategory = new JLabel("Category:");
		lblCategory.setBounds(62, 310, 102, 18);
		ProductWindowAM.getContentPane().add(lblCategory);

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

		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(185, 268, 96, 19);
		ProductWindowAM.getContentPane().add(txtStock);

		txtCategory = new JTextField();
		txtCategory.setColumns(10);
		txtCategory.setBounds(185, 310, 96, 19);
		ProductWindowAM.getContentPane().add(txtCategory);

		comboBox = new JComboBox<>();
		List<Proveedor> listProveedor = OS.getAllProveedor(ConexionBDSql.obtener());
		for (Proveedor p : listProveedor) {
			comboBox.addItem(p);
		}

		comboBox.setBounds(185, 225, 96, 19);
		ProductWindowAM.getContentPane().add(comboBox);

		lblImage = new JLabel("Image:");
		lblImage.setBounds(62, 354, 51, 18);
		ProductWindowAM.getContentPane().add(lblImage);

		txtPath = new JTextField();
		txtPath.setEnabled(false);
		txtPath.setEditable(false);
		txtPath.setColumns(10);
		txtPath.setBounds(141, 354, 140, 19);
		ProductWindowAM.getContentPane().add(txtPath);

		btnAddImg = new JButton("Add IMG");
		btnAddImg.setBounds(291, 353, 85, 21);
		btnAddImg.addActionListener(im);
		ProductWindowAM.getContentPane().add(btnAddImg);

		btnBack = new JButton("Back");
		btnBack.setBounds(222, 436, 85, 21);
		btnBack.addActionListener(maneBtn);
		ProductWindowAM.getContentPane().add(btnBack);

		if (productId == 0) {

			// Label choose an option
			lblInsert = new JLabel("Insert Menu");
			lblInsert.setToolTipText("text choice");
			lblInsert.setFont(new Font("Tahoma", Font.PLAIN, 25));
			lblInsert.setBounds(100, 10, 223, 66);
			ProductWindowAM.add(lblInsert);
			
			btnInsert = new JButton("Insert");
			btnInsert.setBounds(64, 436, 85, 21);
			btnInsert.addActionListener(maneBtn);
			ProductWindowAM.getContentPane().add(btnInsert);
			

		} else {
			// Label choose an option
			lblInsert = new JLabel("Modify Menu");
			lblInsert.setToolTipText("text choice");
			lblInsert.setFont(new Font("Tahoma", Font.PLAIN, 25));
			lblInsert.setBounds(100, 10, 223, 66);
			ProductWindowAM.add(lblInsert);
			
			btnModify = new JButton("Modify");
			btnModify.setBounds(64, 436, 85, 21);
			btnModify.addActionListener(maneBtn);
			ProductWindowAM.getContentPane().add(btnModify);

			try {
				product = OS.getProduct(ConexionBDSql.obtener(), productId);

			} catch (ClassNotFoundException | SQLException e) {

				e.printStackTrace();
			}
			// Insertar los datos que vamos a modificar
			txtName.setText(product.getNombre());
			txtCategory.setText(product.getCategoria());
			txtBrand.setText(product.getMarca());
			txtStock.setText(String.valueOf(product.getStock()));
			txtPath.setText(product.getImg());
			txtPrice.setText(String.valueOf(product.getPrecio()));
		}

		ProductWindowAM.setVisible(true);
	}

	private class ManejadorBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ObjectService os = new ObjectService();
			Object obj = e.getSource();
			System.out.println("Hola");
			if (obj == btnInsert) {
				System.out.println("Hola");
				// insertar producto
				// String nombre, String marca, Float precio, String img, int proveedorid, int
				// stock,String categoria
				String nombre = txtName.getText();
				String marca = txtBrand.getText();
				Float precio = Float.parseFloat(txtPrice.getText());
				String imagen = txtPath.getText();
				String proveedor = (String) comboBox.getSelectedItem();
				String stock = txtStock.getText();
				String categoria = txtCategory.getText();
				if (nombre.equals("") || marca.equals("") || precio.equals("") || proveedor.equals("")
						|| stock.equals("") || categoria.equals("")) {
					JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: Los campos no pueden estar vacíos.",
							"Error de Registro", JOptionPane.ERROR_MESSAGE);
				}
//				int idproov = Integer.parseInt(proveedor);
//				int stockint = Integer.parseInt(stock);
//				String img = ("imagen/producto/" + nombre.replace(" ", "") + marca.replace(" ", "") + extension);
//				try {
//					os.saveProducto(ConexionBDSql.obtener(),
//							(new Producto(nombre, marca, precio, img, idproov, stockint, categoria)), 1);
//				} catch (ClassNotFoundException e1) {
//					e1.printStackTrace();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//				if (sourcer != null)
//					try {
//						Files.copy(sourcer, destination);
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
				
				JOptionPane.showMessageDialog(AddModifyProduct.this, "El producto se ha registrado correctamente.",
						"Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
				productId = 0;
				ProductWindowAM.setVisible(false);
				try {
					new CrudProducto();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//volver
			} else if (obj == btnBack) {
				productId = 0;
				try {
					new CrudProducto();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				ProductWindowAM.setVisible(false);

			}
			else {
				
			}
		}
	}
	
	// Manejador de insertar imagen
	public class insertImg implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Hola esto es el manejador img");
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter soloImg = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png", "jpeg");
			fileChooser.setFileFilter(soloImg);
			fileChooser.showSaveDialog(null);

			if (fileChooser.getSelectedFile() != null) {
				extension = fileChooser.getSelectedFile().toString()
						.substring(fileChooser.getSelectedFile().toString().lastIndexOf('.'));
				File imagenes = new File("imagen/producto/" + txtName.getText().replace(" ", "") + txtBrand.getText().replace(" ", "") + extension);
				sourcer = fileChooser.getSelectedFile().getAbsoluteFile().toPath();
				destination = imagenes.toPath();
				JOptionPane.showMessageDialog(AddModifyProduct.this, "Imagen insertada", "INFO",
						JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}
}
