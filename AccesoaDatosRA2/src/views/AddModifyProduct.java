package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
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

@SuppressWarnings("serial")
public class AddModifyProduct extends JFrame {

	// Product window add-modify
	private JFrame ProductWindowAM;
	private JLabel lblInsert, lblName, lblBrand, lblPrice, lblSupplier, lblStock, lblCategory, lblImage;
	private JTextField txtName, txtBrand, txtPrice, txtStock, txtPath;
	private JButton btnBack, btnInsert, btnModify, btnAddImg;
	private JComboBox<Proveedor> comboBox;
	@SuppressWarnings("rawtypes")
	private JComboBox comboCategory;

	ManejadorBtn maneBtn = new ManejadorBtn();
	// manejador imagen
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createWindow() throws ClassNotFoundException, SQLException {

		ProductWindowAM = new JFrame("Insert Menu");
		ProductWindowAM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ProductWindowAM.setBounds(100, 100, 420, 580);
		ProductWindowAM.setLocationRelativeTo(null);
		ProductWindowAM.getContentPane().setLayout(null);

		ProductWindowAM.setContentPane(new JLabel(new ImageIcon("./background/backgroundTransactions.jpg")));

		lblName = new JLabel("Nombre:");
		lblName.setBounds(62, 110, 102, 18);
		ProductWindowAM.getContentPane().add(lblName);

		lblBrand = new JLabel("Marca:");
		lblBrand.setBounds(62, 146, 102, 18);
		ProductWindowAM.getContentPane().add(lblBrand);

		lblPrice = new JLabel("Precio:");
		lblPrice.setBounds(62, 184, 102, 18);
		ProductWindowAM.getContentPane().add(lblPrice);

		lblSupplier = new JLabel("Proveedor:");
		lblSupplier.setBounds(62, 226, 102, 18);
		ProductWindowAM.getContentPane().add(lblSupplier);

		lblStock = new JLabel("Stock:");
		lblStock.setBounds(62, 268, 102, 18);
		ProductWindowAM.getContentPane().add(lblStock);

		lblCategory = new JLabel("Categoria:");
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

		comboCategory = new JComboBox();
		comboCategory.setBounds(185, 310, 96, 19);
		comboCategory.addItem("Alimentacion");
		comboCategory.addItem("Tecnologia");
		comboCategory.addItem("Ropa");
		comboCategory.addItem("Mueble");
		comboCategory.setSelectedIndex(0);
		ProductWindowAM.getContentPane().add(comboCategory);

		comboBox = new JComboBox<>();
		List<Proveedor> listProveedor = OS.getAllProveedor(ConexionBDSql.obtener());
		for (Proveedor p : listProveedor) {
			comboBox.addItem(p);
		}

		comboBox.setBounds(141, 226, 255, 19);
		ProductWindowAM.getContentPane().add(comboBox);

		lblImage = new JLabel("Imagen:");
		lblImage.setBounds(62, 354, 51, 18);
		ProductWindowAM.getContentPane().add(lblImage);

		txtPath = new JTextField();
		txtPath.setEnabled(false);
		txtPath.setEditable(false);
		txtPath.setColumns(10);
		txtPath.setBounds(141, 354, 140, 19);
		ProductWindowAM.getContentPane().add(txtPath);

		btnAddImg = new JButton("INSERTAR");
		btnAddImg.setBounds(291, 353, 110, 21);
		btnAddImg.addActionListener(im);
		ProductWindowAM.getContentPane().add(btnAddImg);

		btnBack = new JButton("VOLVER");
		btnBack.setBounds(222, 436, 85, 21);
		btnBack.addActionListener(maneBtn);
		ProductWindowAM.getContentPane().add(btnBack);

		if (productId == 0) {

			// Label choose an option
			lblInsert = new JLabel("Menu insertar");
			lblInsert.setToolTipText("text choice");
			lblInsert.setFont(new Font("Tahoma", Font.PLAIN, 25));
			lblInsert.setBounds(100, 10, 223, 66);
			ProductWindowAM.getContentPane().add(lblInsert);

			btnInsert = new JButton("INSERTAR");
			btnInsert.setBounds(64, 436, 100, 21);
			btnInsert.addActionListener(maneBtn);
			ProductWindowAM.getContentPane().add(btnInsert);

		} else {
			// Label choose an option
			lblInsert = new JLabel("MENU MODIFICAR");
			lblInsert.setFont(new Font("Tahoma", Font.PLAIN, 25));
			lblInsert.setBounds(100, 10, 223, 66);
			ProductWindowAM.getContentPane().add(lblInsert);

			btnModify = new JButton("MODIFICAR");
			btnModify.setBounds(64, 436, 110, 21);
			btnModify.addActionListener(maneBtn);
			ProductWindowAM.getContentPane().add(btnModify);

			try {
				product = OS.getProduct(ConexionBDSql.obtener(), productId);

			} catch (ClassNotFoundException | SQLException e) {

				JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: vuelve a intentarlo","Error", JOptionPane.ERROR_MESSAGE);
			}
			// Insertar los datos que vamos a modificar
			txtName.setText(product.getNombre());
			// txtCategory.setText(product.getCategoria());
			comboCategory.setSelectedItem(product.getCategoria());
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
			if (obj == btnInsert) {
				System.out.println("Hola estas insertando un producto");
				// insertar producto
				try {
				String nombre = txtName.getText();
				String marca = txtBrand.getText();
				Float precio = Float.parseFloat(txtPrice.getText());
				Proveedor Prove = (Proveedor) comboBox.getSelectedItem();
				int Idproveedor = Prove.getIdproveedor();
				String stock = txtStock.getText();
				String categoria = comboCategory.getSelectedItem().toString();

				if (nombre.equals("") || marca.equals("") || precio == 0 || stock.equals("")) {
					JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: Los campos no pueden estar vacíos.",
							"Error de Registro", JOptionPane.ERROR_MESSAGE);
				} else {

					int stockint = Integer.parseInt(stock);
					String img = ("images/" + nombre.replace(" ", "") + marca.replace(" ", "") +Idproveedor+ extension);

					Producto p = new Producto(nombre, marca, precio, img, Idproveedor, stockint, categoria);
					try {
						os.saveProducto(ConexionBDSql.obtener(), p, 1);
					} catch (ClassNotFoundException | SQLException e1) {
						JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: vuelve a intentarlo","Error", JOptionPane.ERROR_MESSAGE);
					}

					if (sourcer != null) {
						try {
							Files.copy(sourcer, destination);
						} catch (IOException e1) {
						JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: vuelve a intentarlo","Error", JOptionPane.ERROR_MESSAGE);
						}
					}

					JOptionPane.showMessageDialog(AddModifyProduct.this, "El producto se ha registrado correctamente.",
							"Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
					productId = 0;
					ProductWindowAM.setVisible(false);
					try {
						new CrudProducto();
					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: vuelve a intentarlo","Error", JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: vuelve a intentarlo","Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				}catch (NumberFormatException e0) {
					JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: Los campos numericos no pueden ser String.",
							"Error de Registro", JOptionPane.ERROR_MESSAGE);
				}
				
				// volver
			} else if (obj == btnBack) {
				productId = 0;
				try {
					new CrudProducto();
				} catch (ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: vuelve a intentarlo","Error", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: vuelve a intentarlo","Error", JOptionPane.ERROR_MESSAGE);
				}
				ProductWindowAM.setVisible(false);

			} else if (obj == btnModify) {
				// modificar producto
				try {
				String nombre = txtName.getText();
				String marca = txtBrand.getText();
				Float precio = Float.parseFloat(txtPrice.getText());
				Proveedor Prove = (Proveedor) comboBox.getSelectedItem();
				int Idproveedor = Prove.getIdproveedor();
				String stock = txtStock.getText();
				String categoria = comboCategory.getSelectedItem().toString();
				String img = txtPath.getText();
				if (nombre.equals("") || marca.equals("") || precio == 0 || stock.equals("")) {
					JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: Los campos no pueden estar vacíos.",
							"Error de Registro", JOptionPane.ERROR_MESSAGE);
				}
				int stockint = Integer.parseInt(stock);
				try {
					os.saveProducto(ConexionBDSql.obtener(), (new Producto(product.getIdproducto(), nombre, marca,
							precio, img, Idproveedor, stockint, categoria)), 2);
				} catch (ClassNotFoundException | SQLException e1) {
					JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: vuelve a intentarlo","Error", JOptionPane.ERROR_MESSAGE);
				}

				JOptionPane.showMessageDialog(AddModifyProduct.this, "El producto se ha actualizado correctamente.",
						"Actualizado Exitoso", JOptionPane.INFORMATION_MESSAGE);
				productId = 0;
				ProductWindowAM.setVisible(false);
				try {
					new CrudProducto();
				} catch (ClassNotFoundException | SQLException e1) {
					JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: vuelve a intentarlo","Error", JOptionPane.ERROR_MESSAGE);
				} 
			}
				catch(NumberFormatException ee) {
					JOptionPane.showMessageDialog(AddModifyProduct.this, "Error: Los campos numericos no pueden ser String.",
							"Error de Modificacion", JOptionPane.ERROR_MESSAGE);
				}
		}
	}
	}

	// Manejador de insertar imagen
	public class insertImg implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Hola esto es el manejador img");
			Proveedor Prove = (Proveedor) comboBox.getSelectedItem();
			int Idproveedor = Prove.getIdproveedor();
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter soloImg = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png", "jpeg");
			fileChooser.setFileFilter(soloImg);
			fileChooser.showSaveDialog(null);

			String nombre = txtName.getText();
			String marca = txtBrand.getText();
			if (!nombre.equals("") && !marca.equals("")) {
				if (fileChooser.getSelectedFile() != null) {
					extension = fileChooser.getSelectedFile().toString()
							.substring(fileChooser.getSelectedFile().toString().lastIndexOf('.'));
					File imagenes = new File("images/" + txtName.getText().replace(" ", "")
							+ txtBrand.getText().replace(" ", "") + Idproveedor+  extension);
					sourcer = fileChooser.getSelectedFile().getAbsoluteFile().toPath();
					destination = imagenes.toPath();
					txtPath.setText(destination.toString());
					JOptionPane.showMessageDialog(AddModifyProduct.this, "Imagen insertada", "INFO",
							JOptionPane.INFORMATION_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(AddModifyProduct.this,
						"Necesitas escribir el nombre y la marca del producto", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
