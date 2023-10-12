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

	private JLabel Idtransaction, empleado, proveedor, marca, producto, cantidad, fecha;
	private JTextField Idtransactiontext, empleadotext, cantidadtext, fechatext;
	private static JComboBox<String> Proveedortext, Productotext, Marcatext;
	private List<String> Proveedor = new ArrayList<>();
	private List<String> Producto = new ArrayList<>();
	private List<String> Marca = new ArrayList<>();
	private JButton Update, Return;
	private final Icon IconUpdate = new ImageIcon("icons/IconUpdate.png"), IconReturn = new ImageIcon("icons/IconReturn.png");
	private Date d = new Date(0);

	public JFrameUpdateTransaction() {
		super("Actualizar Transacciones");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(951, 695);
		setResizable(false);
		setLocationRelativeTo(null);
		
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

		Idtransaction = new JLabel("Idtransacccion");
		Idtransaction.setBounds(34, 98, 112, 25);
		empleado = new JLabel("Empleado");
		empleado.setBounds(277, 449, 58, 37);
		proveedor = new JLabel("Proveedor");
		proveedor.setBounds(464, 89, 112, 42);
		marca = new JLabel("Marca");
		marca.setBounds(34, 228, 112, 29);
		producto = new JLabel("Nombre");
		producto.setBounds(464, 228, 97, 29);
		cantidad = new JLabel("Cantidad");
		cantidad.setBounds(34, 337, 102, 42);
		fecha = new JLabel("Fecha");
		fecha.setBounds(464, 340, 97, 37);

		Idtransactiontext = new JTextField(10);
		Idtransactiontext.setBounds(126, 95, 300, 30);
		Idtransactiontext.setText(String.valueOf(JFrameTransactions.t.getIdinventario()));
		Idtransactiontext.setEditable(false);
		empleadotext = new JTextField(10);
		empleadotext.setBounds(266, 489, 86, 33);
		
		try {
			empleadotext.setText(
					String.valueOf(Test.os.getEmpleado(ConexionBDSql.obtener(), JFrameTransactions.t.getIdempleado())));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		empleadotext.setEditable(false);
		Proveedortext = new JComboBox<String>();
		Proveedortext.setFont(new Font("Arial", Proveedortext.getFont().getStyle(), Proveedortext.getFont().getSize()));
		Proveedortext.setBounds(571, 85, 300, 50);
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
		Productotext.setBounds(571, 217, 300, 50);
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
		Marcatext.setBounds(126, 217, 300, 50);
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
		cantidadtext.setBounds(126, 344, 300, 29);
		cantidadtext.setText(String.valueOf(JFrameTransactions.t.getCantidad()));
		fechatext = new JTextField(10);
		fechatext.setBounds(571, 343, 300, 30);
		fechatext.setText(String.valueOf(d));
		Update = new JButton("Actualizar");
		Update.setBounds(554, 516, 160, 60);
		Update.setIcon(IconUpdate);
		Update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					 Transaccion t = new Transaccion ();
					 t = Test.os.getTransaccion(ConexionBDSql.obtener(), Integer.valueOf(Idtransactiontext.getText()));
					 Producto p = Test.os.getProduct(ConexionBDSql.obtener(), getProductoId(ConexionBDSql.obtener(), Productotext.getSelectedItem().toString()).getIdproducto());
					 p.setStock(p.getStock()-t.getCantidad());
					 Test.os.saveProducto(ConexionBDSql.obtener(), p, 2);
				     t.setIdproducto(getProductoId(ConexionBDSql.obtener(),Productotext.getSelectedItem().toString()).getIdproducto());
					 t.setIdproveedor(getProveedorId(ConexionBDSql.obtener(),Proveedortext.getSelectedItem().toString()).getIdproveedor());
					 t.setCantidad(Integer.valueOf(cantidadtext.getText()));				
					 Test.os.saveTransaccion(ConexionBDSql.obtener(), t, 2);
					 p.setStock(p.getStock()+t.getCantidad());
					 Test.os.saveProducto(ConexionBDSql.obtener(), p, 2);
					 JOptionPane.showMessageDialog(JFrameUpdateTransaction.this, "Actualizacion Realizada Correctamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
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

		getContentPane().setLayout(null);
		getContentPane().add(Idtransaction);
		getContentPane().add(proveedor);
		getContentPane().add(marca);
		getContentPane().add(producto);
		getContentPane().add(cantidad);
		getContentPane().add(fecha);
		getContentPane().add(Idtransactiontext);
		getContentPane().add(Proveedortext);
		getContentPane().add(Marcatext);
		getContentPane().add(Productotext);
		getContentPane().add(cantidadtext);
		getContentPane().add(fechatext);
		getContentPane().add(Update);
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
