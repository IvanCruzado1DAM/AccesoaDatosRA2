package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import models.Empleado;
import models.Producto;
import models.Proveedor;
import models.Transaccion;
import services.ConexionBDSql;
import services.Test;

public class JFrameCreateTransaction extends JFrame {

	private JLabel Idtransaction, empleado, proveedor, marca, producto, cantidad, fecha;
	private JTextField Idtransactiontext, cantidadtext, fechatext;
	private JButton Register, Return;
	private static JComboBox<String> Proveedortext, Productotext, Marcatext;
	private List<String> Proveedor = new ArrayList<>();
	private List<String> Producto = new ArrayList<>();
	private List<String> Marca = new ArrayList<>();
	private Date d = new Date(124);

	public JFrameCreateTransaction() {
		super("Crear Transacciones");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(951, 695);
		setResizable(false);
		setLocationRelativeTo(null);

		Idtransaction = new JLabel("Idtransacccion");
		Idtransaction.setBounds(41, 105, 112, 25);
		empleado = new JLabel("Empleado");
		empleado.setBounds(277, 449, 58, 37);
		proveedor = new JLabel("Proveedor");
		proveedor.setBounds(471, 96, 112, 42);
		marca = new JLabel("Marca");
		marca.setBounds(41, 192, 112, 29);
		producto = new JLabel("Nombre");
		producto.setBounds(471, 192, 97, 29);
		cantidad = new JLabel("Cantidad");
		cantidad.setBounds(39, 286, 102, 42);
		fecha = new JLabel("Fecha");
		fecha.setBounds(471, 289, 97, 37);

		Idtransactiontext = new JTextField(10);
		Idtransactiontext.setBounds(140, 102, 300, 30);
		Idtransactiontext.setEditable(false);
		Proveedortext = new JComboBox<String>();
		Proveedortext.setFont(new Font("Arial", Proveedortext.getFont().getStyle(), Proveedortext.getFont().getSize()));
		Proveedortext.setBounds(570, 92, 300, 50);
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
		Productotext.setBounds(570, 181, 300, 50);
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
		Marcatext.setBounds(140, 181, 300, 50);
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
		cantidadtext.setBounds(140, 293, 300, 29);
		fechatext = new JTextField(10);
		fechatext.setBounds(570, 292, 300, 30);
		fechatext.setText(String.valueOf(d));
		fechatext.setEditable(false);
		
		Register = new JButton("Registrar");
		Register.setBounds(520, 516, 120, 60);
		Register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				d.toLocalDate();
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
		Return.setBounds(273, 516, 120, 60);
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
