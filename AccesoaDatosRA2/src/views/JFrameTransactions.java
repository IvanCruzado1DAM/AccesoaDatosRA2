package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import models.Empleado;
import models.Producto;
import models.Proveedor;
import models.Transaccion;
import services.ConexionBDSql;
import services.Test;

public class JFrameTransactions extends JFrame {

	private JLabel NombreTabla, Foto, Idtransaction, empleado, proveedor, marca, producto, cantidad, fecha;
	private JTextField Idtransactiontext, empleadotext, proveedortext, marcatext, productotext, cantidadtext, fechatext;
	private static JTableBloqueoCeldas model;
	private static JTable table;
	private static JComboBox<String> Filtro_Empleado, Filtro_Proveedor, Filtro_Marca, Filtro_Producto;
	private List<String> Empleado = new ArrayList<>();
	private List<String> Proveedor = new ArrayList<>();
	private List<String> Marca = new ArrayList<>();
	private List<String> Nombre = new ArrayList<>();
	private JButton Register, Delete, Update, Return;

	public JFrameTransactions() {
		super("Transactions");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1159, 699);
		setResizable(false);
		setLocationRelativeTo(null);

		NombreTabla = new JLabel("Transactions");
		NombreTabla.setBounds(26, 19, 122, 50);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 79, 806, 366);

		table = new JTable();
		model = new JTableBloqueoCeldas();
		table.setModel(model);

		scrollPane.setViewportView(table);

		model.addColumn("ID");
		model.addColumn("EMPLEADO");
		model.addColumn("PROVEEDOR");
		model.addColumn("MARCA");
		model.addColumn("PRODUCTO");
		model.addColumn("CANTIDAD");
		model.addColumn("FECHA");
		table.setRowHeight(30);

		EscribirTabla();
 
		Register = new JButton("Resgistrar");
		Register.setBounds(851, 475, 120, 60);
		Register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				JFrameCreateTransaction jf = new JFrameCreateTransaction();
				jf.setVisible(true);
			}
		});

		Foto = new JLabel("Foto");
		Foto.setBounds(891, 79, 203, 299);
		Idtransaction = new JLabel("Idtransaction");
		Idtransaction.setBounds(46, 456, 70, 29);
		empleado = new JLabel("Empleado");
		empleado.setBounds(277, 449, 58, 37);
		proveedor = new JLabel("Proveedor");
		proveedor.setBounds(159, 452, 58, 30);
		marca = new JLabel("Marca");
		marca.setBounds(391, 452, 42, 37);
		producto = new JLabel("Nombre");
		producto.setBounds(506, 455, 42, 31);
		cantidad = new JLabel("Cantidad");
		cantidad.setBounds(634, 452, 58, 37);
		fecha = new JLabel("Fecha");
		fecha.setBounds(738, 456, 42, 31);

		Idtransactiontext = new JTextField(10);
		Idtransactiontext.setBounds(36, 491, 86, 29);
		Idtransactiontext.setEditable(false);
		empleadotext = new JTextField(10);
		empleadotext.setBounds(266, 489, 86, 33);
		empleadotext.setEditable(false);
		proveedortext = new JTextField(10);
		proveedortext.setBounds(141, 491, 94, 29);
		marcatext = new JTextField(10);
		marcatext.setBounds(371, 491, 99, 29);
		productotext = new JTextField(10);
		productotext.setBounds(494, 491, 99, 28);
		cantidadtext = new JTextField(10);
		cantidadtext.setBounds(620, 487, 94, 37);
		fechatext = new JTextField(10);
		fechatext.setBounds(724, 491, 114, 28);

		Delete = new JButton("Eliminar");
		Delete.setBounds(634, 568, 120, 60);
		Delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		Update = new JButton("Actualizar");
		Update.setBounds(371, 568, 120, 60);
		Update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		Return = new JButton("Return");
		Return.setBounds(127, 568, 120, 60);
		Return.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				JFrameAdmin jf = new JFrameAdmin();
				jf.setVisible(true);
			}
		});

		Filtro_Empleado = new JComboBox<String>();
		Filtro_Empleado.setFont(new Font("Arial", Filtro_Empleado.getFont().getStyle(), Filtro_Empleado.getFont().getSize()));
		Filtro_Empleado.setBounds(290, 19, 180, 50);
		Empleado = new ArrayList<>();
		Filtro_Empleado.addItem("Ninguno");
		try {
			for (Empleado em : Test.os.getAllEmpleados(ConexionBDSql.obtener())) {
				String m = em.getUsername();
				if (!Empleado.contains(m)) {
					Empleado.add(m);
					Filtro_Empleado.addItem(m);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Filtro_Proveedor = new JComboBox<String>();
		Filtro_Proveedor.setFont(
				new Font("Arial", Filtro_Proveedor.getFont().getStyle(), Filtro_Proveedor.getFont().getSize()));
		Filtro_Proveedor.setBounds(93, 19, 180, 50);
		Proveedor = new ArrayList<>();
		Filtro_Proveedor.addItem("Ninguno");
		try {
			for (Proveedor p : Test.os.getAllProveedor(ConexionBDSql.obtener())) {
				String m = p.getNombre();
				if (!Proveedor.contains(m)) {
					Proveedor.add(m);
					Filtro_Proveedor.addItem(m);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Filtro_Proveedor.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					TransaccionesConFiltros(ConexionBDSql.obtener(), (String) Filtro_Proveedor.getSelectedItem());
				} catch (NumberFormatException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Filtro_Producto = new JComboBox<String>();
		Filtro_Producto
				.setFont(new Font("Arial", Filtro_Producto.getFont().getStyle(), Filtro_Producto.getFont().getSize()));
		Filtro_Producto.setBounds(700, 18, 180, 50);
		Nombre = new ArrayList<>();
		Filtro_Producto.addItem("Ninguno");
		try {
			for (Producto p : Test.os.getAllProducts(ConexionBDSql.obtener())) {
				String n = p.getNombre();
				if (!Nombre.contains(n)) {
					Nombre.add(n);
					Filtro_Producto.addItem(n);
				}
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Filtro_Marca = new JComboBox<String>();
		Filtro_Marca.setFont(new Font("Arial", Filtro_Marca.getFont().getStyle(), Filtro_Marca.getFont().getSize()));
		Filtro_Marca.setBounds(499, 19, 180, 50);
		Marca = new ArrayList<>();
		Filtro_Marca.addItem("Ninguno");
		try {
			for (Producto p : Test.os.getAllProducts(ConexionBDSql.obtener())) {
				String m = p.getMarca();
				if (!Marca.contains(m)) {
					Marca.add(m);
					Filtro_Marca.addItem(m);
				}
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (table.getSelectedRow() != -1) {
					Object o = model.getValueAt(table.getSelectedRow(), 0);
					try {
						for (Transaccion t : Test.os.getAllTransacciones(ConexionBDSql.obtener())) {
							if (t.getIdinventario() == Integer.valueOf(o.toString())) {
								Idtransactiontext.setEditable(true);
								Idtransactiontext.setText(o.toString());
								Idtransactiontext.setEditable(false);
								for (Proveedor p : Test.os.getAllProveedor(ConexionBDSql.obtener())) {
									if(p.getIdproveedor()==t.getIdproveedor())
								    proveedortext.setText(p.getNombre());
								}
								empleadotext.setEditable(true);
								empleadotext.setText(JFrameLogin.EmActivo.getUsername());
								empleadotext.setEditable(false);
								for (Producto p : Test.os.getAllProducts(ConexionBDSql.obtener())) {
									if (p.getIdproducto() == t.getIdproducto()) {
										marcatext.setText(p.getMarca());
										productotext.setText(p.getNombre());
									}
								}
								cantidadtext.setText(String.valueOf(t.getCantidad()));
								fechatext.setText(String.valueOf(t.getFecha()));
							}
						}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		getContentPane().setLayout(null);

		getContentPane().add(scrollPane);
		getContentPane().add(NombreTabla);
		getContentPane().add(Filtro_Empleado);
		getContentPane().add(Filtro_Proveedor);
		getContentPane().add(Filtro_Marca);
		getContentPane().add(Filtro_Producto);
		getContentPane().add(Register);
		getContentPane().add(Update);
		getContentPane().add(Delete);
		getContentPane().add(Idtransaction);
		getContentPane().add(proveedor);
		getContentPane().add(empleado);
		getContentPane().add(marca);
		getContentPane().add(producto);
		getContentPane().add(cantidad);
		getContentPane().add(fecha);
		getContentPane().add(Idtransactiontext);
		getContentPane().add(proveedortext);
		getContentPane().add(empleadotext);
		getContentPane().add(marcatext);
		getContentPane().add(productotext);
		getContentPane().add(cantidadtext);
		getContentPane().add(fechatext);
		getContentPane().add(Return);
		getContentPane().add(Foto);

	}

	public static void EscribirTabla() {
		try {
			for (Transaccion t : Test.os.getAllTransacciones(ConexionBDSql.obtener())) {
				Producto p = Test.os.getProduct(ConexionBDSql.obtener(), t.getIdproducto());
				Proveedor pro = Test.os.getProveedor(ConexionBDSql.obtener(), t.getIdproveedor());
				Empleado em = Test.os.getEmpleado(ConexionBDSql.obtener(), t.getIdempleado());
				Object[] Fila = new Object[model.getColumnCount()];
				Fila[0] = t.getIdinventario();
				Fila[1] = em.getUsername();
				Fila[2] = pro.getNombre();
				Fila[3] = p.getMarca();
				Fila[4] = p.getNombre();
				Fila[5] = t.getCantidad();
				Fila[6] = t.getFecha();
				model.addRow(Fila);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void EscribirTablaFiltro() {
		try {
			for (Transaccion t : Test.os.getAllTransacciones(ConexionBDSql.obtener())) {
				Producto p = Test.os.getProduct(ConexionBDSql.obtener(), t.getIdproducto());
				Proveedor pro = Test.os.getProveedor(ConexionBDSql.obtener(), t.getIdproveedor());
				Empleado em = Test.os.getEmpleado(ConexionBDSql.obtener(), t.getIdempleado());
				Object[] Fila = new Object[model.getColumnCount()];
				Fila[0] = t.getIdinventario();
				Fila[1] = em.getUsername();
				Fila[2] = pro.getNombre();
				Fila[3] = p.getMarca();
				Fila[4] = p.getNombre();
				Fila[5] = t.getCantidad();
				Fila[6] = t.getFecha();
				model.addRow(Fila);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void TransaccionesConFiltros (Connection conexion, String nombre) throws SQLException {
		List<Transaccion> ListaTransacciones = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT idinventario, fecha, idproducto, idproveedor, cantidad, idempleado FROM transaccion WHERE idproveedor = (SELECT idproveedor FROM proveedor WHERE nombre = " + nombre);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaTransacciones.add(new Transaccion(resultado.getInt("idinventario"), resultado.getDate("fecha"),
						resultado.getInt("idproducto"), resultado.getInt("idproveedor"), resultado.getInt("cantidad"),
						resultado.getInt("idempleado")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		
		for (int x = 0; x < (table.getRowCount() * ListaTransacciones.size()); x++) {
			if (table.getRowCount() > 0)
				model.removeRow(0);
		}
		
		try {
			for (Transaccion t : ListaTransacciones) {
				Producto p = Test.os.getProduct(ConexionBDSql.obtener(), t.getIdproducto());
				Proveedor pro = Test.os.getProveedor(ConexionBDSql.obtener(), t.getIdproveedor());
				Empleado em = Test.os.getEmpleado(ConexionBDSql.obtener(), t.getIdempleado());
				Object[] Fila = new Object[model.getColumnCount()];
				Fila[0] = t.getIdinventario();
				Fila[1] = em.getUsername();
				Fila[2] = pro.getNombre();
				Fila[3] = p.getMarca();
				Fila[4] = p.getNombre();
				Fila[5] = t.getCantidad();
				Fila[6] = t.getFecha();
				model.addRow(Fila);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
