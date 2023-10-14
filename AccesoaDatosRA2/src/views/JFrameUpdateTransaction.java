package views;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
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
import models.Transaccion;
import services.ConexionBDSql;
import services.Test;

public class JFrameUpdateTransaction extends JFrame {

	private JLabel Idtransaction, Employee, Supplier, Brand, Product, Amount, date;
	private JTextField Idtransactiontext, Employeetext, Amounttext, datetext;
	private static JComboBox<String> Suppliertext, Producttext, Brandtext;
	private List<String> ListSupplier = new ArrayList<>();
	private List<String> ListProduct = new ArrayList<>();
	private List<String> ListBrand = new ArrayList<>();
	private JButton Update, Return, UpdateDate;
	private final Icon IconUpdate = new ImageIcon("icons/IconUpdate.png"),
			IconReturn = new ImageIcon("icons/IconReturn.png");
	// Variable para la fecha, estatica ya que es la misma para toda las clases
	private static Date d = new Date(new java.util.Date().getTime());
	private static Date d2 ;

	public JFrameUpdateTransaction() {
		super("Actualizar Transacciones | Empleado : " + JFrameLogin.EmActivo.getUsername());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(951, 695);
		setResizable(false);
		setLocationRelativeTo(null);

		setContentPane(new JPanel() {
			BufferedImage backgroundImage;
			{
				try {
//---------------------------Carga de imagen de fondo--------------------------//
					backgroundImage = ImageIO.read(new File("background/backgroundTransactions.jpg"));
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

		Idtransaction = new JLabel("Idtransacccion");
		Idtransaction.setBounds(34, 98, 112, 25);
		Employee = new JLabel("Empleado");
		Employee.setBounds(277, 449, 58, 37);
		Supplier = new JLabel("Proveedor");
		Supplier.setBounds(464, 89, 112, 42);
		Brand = new JLabel("Marca");
		Brand.setBounds(34, 228, 112, 29);
		Product = new JLabel("Nombre");
		Product.setBounds(464, 228, 97, 29);
		Amount = new JLabel("Cantidad");
		Amount.setBounds(34, 337, 102, 42);
		date = new JLabel("Fecha");
		date.setBounds(464, 340, 97, 37);

		Idtransactiontext = new JTextField(10);
		Idtransactiontext.setBounds(126, 95, 300, 30);
		Idtransactiontext.setText(String.valueOf(JFrameTransactions.t.getIdinventario()));
		Idtransactiontext.setEditable(false);
		Employeetext = new JTextField(10);
		Employeetext.setBounds(266, 489, 86, 33);

		try {
			// Ponemos el empleado que realiza la actualizacion
			Employeetext.setText(
					String.valueOf(Test.os.getEmpleado(ConexionBDSql.obtener(), JFrameTransactions.t.getIdempleado())));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Employeetext.setEditable(false);
		Suppliertext = new JComboBox<String>();
		Suppliertext.setFont(new Font("Arial", Suppliertext.getFont().getStyle(), Suppliertext.getFont().getSize()));
		Suppliertext.setBounds(571, 85, 300, 50);
		ListSupplier = new ArrayList<>();
		try {
			// Rellenamos el combobox de proveedores, sin que se repitan
			for (Proveedor pro : Test.os.getAllProveedor(ConexionBDSql.obtener())) {
				String m = pro.getNombre();
				if (!ListSupplier.contains(m)) {
					ListSupplier.add(m);
					Suppliertext.addItem(m);
				}
			}
			// Ponemos el proveedor de la transaccion seleccionado para actualizar
			Suppliertext.setSelectedItem(
					Test.os.getProveedor(ConexionBDSql.obtener(), JFrameTransactions.t.getIdproveedor()).getNombre());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Producttext = new JComboBox<String>();
		Producttext.setFont(new Font("Arial", Producttext.getFont().getStyle(), Producttext.getFont().getSize()));
		Producttext.setBounds(571, 217, 300, 50);
		ListProduct = new ArrayList<>();
		try {
			// Rellenamos el combobox con el nombre de la transaccion , sin que se repitan
			for (Producto em : Test.os.getAllProducts(ConexionBDSql.obtener())) {
				String m = em.getNombre();
				if (!ListProduct.contains(m)) {
					ListProduct.add(m);
					Producttext.addItem(m);
				}
			}
			Producttext.setSelectedItem(
					Test.os.getProduct(ConexionBDSql.obtener(), JFrameTransactions.t.getIdproducto()).getNombre());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Brandtext = new JComboBox<String>();
		Brandtext.setFont(new Font("Arial", Brandtext.getFont().getStyle(), Brandtext.getFont().getSize()));
		Brandtext.setBounds(126, 217, 300, 50);
		ListBrand = new ArrayList<>();
		try {
			// Rellenamos el combobox con las marcas de los productos, sin que se repitan
			for (models.Producto p : Test.os.getAllProducts(ConexionBDSql.obtener())) {
				String m = p.getMarca();
				if (!ListBrand.contains(m)) {
					ListBrand.add(m);
					Brandtext.addItem(m);
				}
			}
			// Ponemos la marca del producto seleccionado para actualizar
			Brandtext.setSelectedItem(
					Test.os.getProduct(ConexionBDSql.obtener(), JFrameTransactions.t.getIdproducto()).getMarca());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Amounttext = new JTextField(10);
		Amounttext.setBounds(126, 344, 300, 29);
		Amounttext.setText(String.valueOf(JFrameTransactions.t.getCantidad()));
		datetext = new JTextField(10);
		datetext.setBounds(571, 343, 300, 30);
		datetext.setText(String.valueOf(JFrameTransactions.dateFormat.format(JFrameTransactions.date)));
		datetext.setEditable(false);
		Update = new JButton("Actualizar");
		Update.setBounds(554, 516, 160, 60);
		Update.setIcon(IconUpdate);
		Update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Connection conexion = null;
				try {
					conexion = ConexionBDSql.obtener();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					// Obtener la transacción y el producto
					Transaccion t = Test.os.getTransaccion(conexion, Integer.valueOf(Idtransactiontext.getText()));
					Producto p = Test.os.getProduct(conexion, getProductoId(conexion, Producttext.getSelectedItem().toString()).getIdproducto());

					// Actualizar el stock del producto
					p.setStock(p.getStock() - t.getCantidad());
					Test.os.saveProducto(conexion, p, 2);

					// Actualizar nuevamente el stock del producto
					p.setStock(p.getStock() + t.getCantidad());
					Test.os.saveProducto(conexion, p, 2);

					// Eliminar la transacción antigua
					Test.os.removeTransaccion(conexion, t);
					
					// Crear y guardar la nueva transacción
					Transaccion tnew = new Transaccion(d2, getProductoId(conexion, Producttext.getSelectedItem().toString()).getIdproducto(),
					        getProveedorId(conexion, Suppliertext.getSelectedItem().toString()).getIdproveedor(),
					        Integer.valueOf(Amounttext.getText()), JFrameLogin.EmActivo.getIduser());
					Test.os.saveTransaccion(conexion, tnew, 1);

					// Mostrar un mensaje de confirmación
					JOptionPane.showMessageDialog(JFrameUpdateTransaction.this, "Actualizacion Realizada Correctamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(JFrameUpdateTransaction.this, "Cantidad solo debe contener numeros",
							"Aviso", JOptionPane.ERROR_MESSAGE);
					Amounttext.setText("");
				}
			}
		});

		Return = new JButton("Return");
		Return.setBounds(233, 516, 160, 60);
		Return.setIcon(IconReturn);
		Return.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				JFrameTransactions jf = new JFrameTransactions();
				jf.setVisible(true);
			}
		});

		UpdateDate = new JButton("Actualizar Fecha");
		UpdateDate.setBounds(640, 373, 146, 50);
		UpdateDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				d2 = new Date(new java.util.Date().getTime());
				datetext.setText(JFrameTransactions.dateFormat.format(d2));
			}
		});

		getContentPane().setLayout(null);
		getContentPane().add(Idtransaction);
		getContentPane().add(Supplier);
		getContentPane().add(Brand);
		getContentPane().add(Product);
		getContentPane().add(Amount);
		getContentPane().add(date);
		getContentPane().add(Idtransactiontext);
		getContentPane().add(Suppliertext);
		getContentPane().add(Brandtext);
		getContentPane().add(Producttext);
		getContentPane().add(Amounttext);
		getContentPane().add(datetext);
		getContentPane().add(UpdateDate);
		getContentPane().add(Update);
		getContentPane().add(Return);
	}

	// Obtenemos el producto a traves del nombre, para poder mostrar solo el
	// nombre, en el jtable
	public Producto getProductoId(Connection conexion, String nombre) throws SQLException {
		Producto product = null;
		try {
			PreparedStatement consulta = conexion.prepareStatement(
					"SELECT idproducto, nombre , marca, precio, img, proveedorid, stock, categoria FROM producto WHERE nombre = ?");
			consulta.setString(1, nombre);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				product = new Producto(resultado.getInt("idproducto"), nombre, resultado.getString("marca"),
						resultado.getFloat("precio"), resultado.getString("img"), resultado.getInt("proveedorid"),
						resultado.getInt("stock"), resultado.getString("categoria"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return product;
	}

	// Obtenemos el proveedor a traves del nombre, para poder mostrar solo el
	// nombre, en el jtable
	public Proveedor getProveedorId(Connection conexion, String nombre) throws SQLException {
		Proveedor Supplier = null;
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT idproveedor, nombre, direccion, numero FROM proveedor WHERE nombre = ?");
			consulta.setString(1, nombre);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				Supplier = new Proveedor(resultado.getInt("idproveedor"), nombre, resultado.getString("direccion"),
						resultado.getInt("numero"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return Supplier;
	}
}
