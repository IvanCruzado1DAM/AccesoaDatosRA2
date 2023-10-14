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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

public class JFrameCreateTransaction extends JFrame {

	private JLabel Idtransaction, Employees, Supplier, Brand, Product, Amount, date;
	private JTextField Amounttext, datetext;
	private JButton Register, Return;
	private final Icon IconRegister = new ImageIcon("icons/IconRegister.png"), IconReturn = new ImageIcon("icons/IconReturn.png");
	private static JComboBox<String> Suppliertext, Producttext, Brandtext;
	private List<String> ListSupplier = new ArrayList<>();
	private List<String> ListProduct = new ArrayList<>();
	private List<String> ListBrand = new ArrayList<>();
	private Date d = new Date(new java.util.Date().getTime());
	
	public JFrameCreateTransaction() {
		super("Crear Transacciones| Empleado: " + JFrameLogin.EmActivo.getUsername());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(954, 579);
		setResizable(false);
		setLocationRelativeTo(null);
		Idtransaction = new JLabel("Idtransacccion");
		Idtransaction.setBounds(41, 105, 112, 25);
		Employees = new JLabel("Empleado");
		Employees.setBounds(277, 449, 58, 37);
		Supplier = new JLabel("Proveedor");
		Supplier.setBounds(50, 106, 112, 42);
		Brand = new JLabel("Marca");
		Brand.setBounds(50, 225, 112, 29);
		Product = new JLabel("Nombre");
		Product.setBounds(475, 113, 97, 29);
		Amount = new JLabel("Cantidad");
		Amount.setBounds(475, 218, 102, 42);
		date = new JLabel("Fecha");
		date.setBounds(641, 8, 41, 37);
		
		setContentPane(new JPanel() {
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

		Suppliertext = new JComboBox<String>();
		Suppliertext.setFont(new Font("Arial", Suppliertext.getFont().getStyle(), Suppliertext.getFont().getSize()));
		Suppliertext.setBounds(117, 102, 300, 50);
		ListSupplier = new ArrayList<>();
		try {
			for (Proveedor pro : Test.os.getAllProveedor(ConexionBDSql.obtener())) {
				String m = pro.getNombre();
				if (!ListSupplier.contains(m)) {
					ListSupplier.add(m);
					Suppliertext.addItem(m);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Producttext = new JComboBox<String>();
		Producttext.setFont(new Font("Arial", Producttext.getFont().getStyle(), Producttext.getFont().getSize()));
		Producttext.setBounds(535, 102, 300, 50);
		ListProduct = new ArrayList<>();
		try {
			for (Producto em : Test.os.getAllProducts(ConexionBDSql.obtener())) {
				String m = em.getNombre();
				if (!ListProduct.contains(m)) {
					ListProduct.add(m);
					Producttext.addItem(m);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Brandtext = new JComboBox<String>();
		Brandtext.setFont(new Font("Arial", Brandtext.getFont().getStyle(), Brandtext.getFont().getSize()));
		Brandtext.setBounds(117, 214, 300, 50);
		ListBrand = new ArrayList<>();
		try {
			for (models.Producto p : Test.os.getAllProducts(ConexionBDSql.obtener())) {
				String m = p.getMarca();
				if (!ListBrand.contains(m)) {
					ListBrand.add(m);
					Brandtext.addItem(m);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		Amounttext = new JTextField(10);
		Amounttext.setBounds(535, 225, 300, 29);
		
		datetext = new JTextField(10);
		datetext.setBounds(692, 11, 236, 30);
		//Aplicamos el formato para date
		datetext.setText(String.valueOf(JFrameTransactions.dateFormat.format(d)));
		datetext.setEditable(false);
		
		Register = new JButton("Registrar");
		Register.setBounds(511, 423, 160, 60);
		Register.setIcon(IconRegister);
		Register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 try {
					 Transaccion t = new Transaccion ( d, getProductoId(ConexionBDSql.obtener(), Producttext.getSelectedItem().toString()).getIdproducto(), 
							 getProveedorId(ConexionBDSql.obtener(), Suppliertext.getSelectedItem().toString()).getIdproveedor(),Integer.valueOf(Amounttext.getText()), JFrameLogin.EmActivo.getIduser());
					 //Creamos la transaccion y la guardamos
					 Test.os.saveTransaccion(ConexionBDSql.obtener(), t, 1);
					 Producto p = Test.os.getProduct(ConexionBDSql.obtener(), getProductoId(ConexionBDSql.obtener(), Producttext.getSelectedItem().toString()).getIdproducto());
					 //Ajustamos el producto con la transaccion y lo guardamos
					 p.setStock(p.getStock() + t.getCantidad());
					 Test.os.saveProducto(ConexionBDSql.obtener(), p, 2);
					 JOptionPane.showMessageDialog(JFrameCreateTransaction.this, "Transaccion agregada correctamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				 } catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					 JOptionPane.showMessageDialog(JFrameCreateTransaction.this, "Cantidad solo debe contener numeros", "Aviso",  JOptionPane.ERROR_MESSAGE);
					    Amounttext.setText("");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Return = new JButton("Return");
		Return.setBounds(253, 423, 160, 60);
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

		getContentPane().setLayout(null);
		getContentPane().add(Supplier);
		getContentPane().add(Brand);
		getContentPane().add(Product);
		getContentPane().add(Amount);
		getContentPane().add(date);
		getContentPane().add(Suppliertext);
		getContentPane().add(Brandtext);
		getContentPane().add(Producttext);
		getContentPane().add(Amounttext);
		getContentPane().add(datetext);
		getContentPane().add(Register);
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
			PreparedStatement consulta = conexion.prepareStatement(
					"SELECT idproveedor, nombre, direccion, numero FROM proveedor WHERE nombre = ?");
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
