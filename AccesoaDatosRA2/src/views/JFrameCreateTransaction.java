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

public class JFrameCreateTransaction extends JFrame {

	private JLabel Idtransaction, empleado, proveedor, marca, producto, cantidad, fecha;
	private JTextField cantidadtext, fechatext;
	private JButton Register, Return;
	private final Icon IconRegister = new ImageIcon("icons/IconRegister.png"), IconReturn = new ImageIcon("icons/IconReturn.png");
	private static JComboBox<String> Proveedortext, Productotext, Marcatext;
	private List<String> Proveedor = new ArrayList<>();
	private List<String> Producto = new ArrayList<>();
	private List<String> Marca = new ArrayList<>();
	private Date d = new Date(new java.util.Date().getTime());

	public JFrameCreateTransaction() {
		super("Crear Transacciones");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(954, 579);
		setResizable(false);
		setLocationRelativeTo(null);
		Idtransaction = new JLabel("Idtransacccion");
		Idtransaction.setBounds(41, 105, 112, 25);
		empleado = new JLabel("Empleado");
		empleado.setBounds(277, 449, 58, 37);
		proveedor = new JLabel("Proveedor");
		proveedor.setBounds(50, 106, 112, 42);
		marca = new JLabel("Marca");
		marca.setBounds(50, 225, 112, 29);
		producto = new JLabel("Nombre");
		producto.setBounds(475, 113, 97, 29);
		cantidad = new JLabel("Cantidad");
		cantidad.setBounds(475, 218, 102, 42);
		fecha = new JLabel("Fecha");
		fecha.setBounds(746, 221, 41, 37);
		
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

		Proveedortext = new JComboBox<String>();
		Proveedortext.setFont(new Font("Arial", Proveedortext.getFont().getStyle(), Proveedortext.getFont().getSize()));
		Proveedortext.setBounds(117, 102, 300, 50);
		Proveedor = new ArrayList<>();
		try {
			for (Proveedor pro : Test.os.getAllProveedor(ConexionBDSql.obtener())) {
				String m = pro.getNombre();
				if (!Proveedor.contains(m)) {
					Proveedor.add(m);
					Proveedortext.addItem(m);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Productotext = new JComboBox<String>();
		Productotext.setFont(new Font("Arial", Productotext.getFont().getStyle(), Productotext.getFont().getSize()));
		Productotext.setBounds(535, 102, 300, 50);
		Producto = new ArrayList<>();
		try {
			for (Producto em : Test.os.getAllProducts(ConexionBDSql.obtener())) {
				String m = em.getNombre();
				if (!Producto.contains(m)) {
					Producto.add(m);
					Productotext.addItem(m);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Marcatext = new JComboBox<String>();
		Marcatext.setFont(new Font("Arial", Marcatext.getFont().getStyle(), Marcatext.getFont().getSize()));
		Marcatext.setBounds(117, 214, 300, 50);
		Marca = new ArrayList<>();
		try {
			for (models.Producto p : Test.os.getAllProducts(ConexionBDSql.obtener())) {
				String m = p.getMarca();
				if (!Marca.contains(m)) {
					Marca.add(m);
					Marcatext.addItem(m);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		cantidadtext = new JTextField(10);
		cantidadtext.setBounds(530, 225, 160, 29);
		fechatext = new JTextField(10);
		fechatext.setBounds(797, 224, 73, 30);
		fechatext.setText(String.valueOf(d));
		fechatext.setEditable(false);
		
		Register = new JButton("Registrar");
		Register.setBounds(511, 423, 160, 60);
		Register.setIcon(IconRegister);
		Register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 try {
					 Transaccion t = new Transaccion ( d, getProductoId(ConexionBDSql.obtener(), Productotext.getSelectedItem().toString()).getIdproducto(), 
							 getProveedorId(ConexionBDSql.obtener(), Proveedortext.getSelectedItem().toString()).getIdproveedor(),Integer.valueOf(cantidadtext.getText()), JFrameLogin.EmActivo.getIduser());
					 Test.os.saveTransaccion(ConexionBDSql.obtener(), t, 1);
					 Producto p = Test.os.getProduct(ConexionBDSql.obtener(), getProductoId(ConexionBDSql.obtener(), Productotext.getSelectedItem().toString()).getIdproducto());
					 p.setStock(p.getStock() + t.getCantidad());
					 Test.os.saveProducto(ConexionBDSql.obtener(), p, 2);
					 JOptionPane.showMessageDialog(JFrameCreateTransaction.this, "Transaccion agregada correctamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				 } catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
		getContentPane().add(proveedor);
		getContentPane().add(marca);
		getContentPane().add(producto);
		getContentPane().add(cantidad);
		getContentPane().add(fecha);
		getContentPane().add(Proveedortext);
		getContentPane().add(Marcatext);
		getContentPane().add(Productotext);
		getContentPane().add(cantidadtext);
		getContentPane().add(fechatext);
		getContentPane().add(Register);
		getContentPane().add(Return);
	}

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
	
	public Proveedor getProveedorId(Connection conexion, String nombre) throws SQLException {
		Proveedor proveedor = null;
		try {
			PreparedStatement consulta = conexion.prepareStatement(
					"SELECT idproveedor, nombre, direccion, numero FROM proveedor WHERE nombre = ?");
			consulta.setString(1, nombre);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				proveedor = new Proveedor(resultado.getInt("idproveedor"), nombre, resultado.getString("direccion"),
						resultado.getInt("numero"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return proveedor;
	}
}
