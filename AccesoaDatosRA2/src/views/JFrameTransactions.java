package views;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import models.Empleado;
import models.Producto;
import models.Proveedor;
import models.Transaccion;
import services.ConexionBDSql;
import services.Test;

public class JFrameTransactions extends JFrame {

	private JLabel NombreTabla, Foto;
	private static JTableBloqueoCeldas model;
	private static JTable table;
	private static JComboBox<String> Filtro_Empleado, Filtro_Proveedor, Filtro_Marca, Filtro_Producto, Tipo_Transaccion;
	private List<String> Empleado = new ArrayList<>();
	private List<String> Proveedor = new ArrayList<>();
	private List<String> Marca = new ArrayList<>();
	private List<String> Nombre = new ArrayList<>();
	private JButton Register, Delete, Update, Return, GenerateReport;
	private final Icon IconRegister = new ImageIcon("icons/IconRegister.png"), IconDelete = new ImageIcon("icons/IconDelete.png"), IconUpdate = new ImageIcon("icons/IconUpdate.png"),
			IconReturn = new ImageIcon("icons/IconReturn.png"), IconGenerateReport = new ImageIcon("icons/IconDocument.png");
	protected static Transaccion t;
	private static String fEmpleado = "Empleados", fProveedor = "Proveedores", fMarca = "Marcas", fProducto = "Productos", tTransaccion = "TiposTransacciones";
	private static List<Transaccion> ListaTransacciones = new ArrayList<>();

	public JFrameTransactions() {
		super("Transacciones | Empleado : " + JFrameLogin.EmActivo.getUsername());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1159, 699);
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

		NombreTabla = new JLabel("Transacciones");
		NombreTabla.setBounds(10, 19, 122, 50);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 79, 806, 439);

		table = new JTable();
		model = new JTableBloqueoCeldas();
		table.getTableHeader().setReorderingAllowed(true);
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);
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
		try {
			ListaTransacciones = Test.os.getAllTransacciones(ConexionBDSql.obtener());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EscribirTabla();

		Register = new JButton("Resgistrar");
		Register.setBounds(722, 568, 160, 60);
		Register.setIcon(IconRegister);
		Register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				JFrameCreateTransaction jf = new JFrameCreateTransaction();
				jf.setVisible(true);
			}
		});

		Foto = new JLabel("");
		Foto.setBounds(863, 79, 258, 287);
		Delete = new JButton("Eliminar");
		Delete.setBounds(507, 568, 140, 60);
		Delete.setIcon(IconDelete);
		Delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					t = Test.os.getTransaccion(ConexionBDSql.obtener(),
							Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString()));
					Producto p = Test.os
							.getProduct(
									ConexionBDSql.obtener(), Test.os
											.getTransaccion(ConexionBDSql.obtener(),
													Integer.valueOf(
															model.getValueAt(table.getSelectedRow(), 0).toString()))
											.getIdproducto());
					p.setStock(p.getStock() - t.getCantidad());
					Test.os.removeTransaccion(ConexionBDSql.obtener(), Test.os.getTransaccion(ConexionBDSql.obtener(),
							Integer.valueOf(model.getValueAt(table.getSelectedRow(), 0).toString())));
					JOptionPane.showMessageDialog(JFrameTransactions.this, "Transaccion Eliminada Correctamente",
							"Informacion", JOptionPane.INFORMATION_MESSAGE);
					Test.os.saveProducto(ConexionBDSql.obtener(), p, 2);
					EscribirTabla();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(JFrameTransactions.this, "Selecciona una Transaccion para eliminar",
							"Aviso", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		Update = new JButton("Actualizar");
		Update.setBounds(290, 568, 160, 60);
		Update.setIcon(IconUpdate);
		Update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					t = Test.os.getTransaccion(ConexionBDSql.obtener(),
							Integer.valueOf(model.getValueAt(table.getSelectedRow(), 0).toString()));
					dispose();
					JFrameUpdateTransaction jf = new JFrameUpdateTransaction();
					jf.setVisible(true);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(JFrameTransactions.this, "Seleccione una transaccion para Actualizar",
							"Aviso", JOptionPane.WARNING_MESSAGE);
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(JFrameTransactions.this,
							"No ha seleccionado ninguna transaccion para actualizar", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		Return = new JButton("Volver");
		Return.setBounds(66, 568, 140, 60);
		Return.setIcon(IconReturn);
		Return.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				JFrameAdmin jf = new JFrameAdmin();
				jf.setVisible(true);
			}
		});

		GenerateReport = new JButton("Generar Informe");
		GenerateReport.setBounds(928, 568, 180, 60);
		GenerateReport.setIcon(IconGenerateReport);
		GenerateReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EscribirTexto(fEmpleado, fProveedor, fMarca, fProducto, tTransaccion);
			}
		});

		Filtro_Empleado = new JComboBox<String>();
		Filtro_Empleado.setFont(new Font("Arial", Filtro_Empleado.getFont().getStyle(), Filtro_Empleado.getFont().getSize()));
		Filtro_Empleado.setBounds(111, 18, 180, 50);
		Empleado = new ArrayList<>();
		Filtro_Empleado.addItem("Empleados");
		Filtro_Empleado.setSelectedIndex(0);
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

		Filtro_Empleado.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					fEmpleado = (String) Filtro_Empleado.getSelectedItem();
					TransaccionesConFiltros(ConexionBDSql.obtener(), fProveedor, fEmpleado, fMarca, fProducto,
							tTransaccion);
				} catch (NumberFormatException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Filtro_Proveedor = new JComboBox<String>();
		Filtro_Proveedor.setFont(new Font("Arial", Filtro_Proveedor.getFont().getStyle(), Filtro_Proveedor.getFont().getSize()));
		Filtro_Proveedor.setBounds(301, 19, 180, 50);
		Proveedor = new ArrayList<>();
		Filtro_Proveedor.addItem("Proveedores");
		Filtro_Proveedor.setSelectedIndex(0);
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

		Filtro_Proveedor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					fProveedor = (String) Filtro_Proveedor.getSelectedItem();
					TransaccionesConFiltros(ConexionBDSql.obtener(), fProveedor, fEmpleado, fMarca, fProducto,
							tTransaccion);
				} catch (NumberFormatException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Filtro_Producto = new JComboBox<String>();
		Filtro_Producto.setFont(new Font("Arial", Filtro_Producto.getFont().getStyle(), Filtro_Producto.getFont().getSize()));
		Filtro_Producto.setBounds(696, 18, 180, 50);
		Nombre = new ArrayList<>();
		Filtro_Producto.addItem("Productos");
	    Filtro_Producto.setSelectedIndex(0);
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

		Filtro_Producto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					fProducto = (String) Filtro_Producto.getSelectedItem();
					TransaccionesConFiltros(ConexionBDSql.obtener(), fProveedor, fEmpleado, fMarca, fProducto,
							tTransaccion);
				} catch (NumberFormatException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Filtro_Marca = new JComboBox<String>();
		Filtro_Marca.setFont(new Font("Arial", Filtro_Marca.getFont().getStyle(), Filtro_Marca.getFont().getSize()));
		Filtro_Marca.setBounds(506, 19, 180, 50);
		Marca = new ArrayList<>();
		Filtro_Marca.addItem("Marcas");
		Filtro_Marca.setSelectedIndex(0);
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

		Filtro_Marca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					fMarca = (String) Filtro_Marca.getSelectedItem();
					TransaccionesConFiltros(ConexionBDSql.obtener(), fProveedor, fEmpleado, fMarca, fProducto,
							tTransaccion);
				} catch (NumberFormatException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Tipo_Transaccion = new JComboBox<String>();
		Tipo_Transaccion
				.setFont(new Font("Arial", Filtro_Marca.getFont().getStyle(), Filtro_Marca.getFont().getSize()));
		Tipo_Transaccion.setBounds(902, 19, 180, 50);
		Tipo_Transaccion.addItem("TiposTransacciones");
		Tipo_Transaccion.addItem("Exportacion");
		Tipo_Transaccion.addItem("Importacion");
        Tipo_Transaccion.setSelectedIndex(0);
		Tipo_Transaccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					tTransaccion = (String) Tipo_Transaccion.getSelectedItem();
					TransaccionesConFiltros(ConexionBDSql.obtener(), fProveedor, fEmpleado, fMarca, fProducto,
							tTransaccion);
				} catch (NumberFormatException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ImageIcon i = new ImageIcon(
							Test.os.getProduct(
									ConexionBDSql.obtener(), Test.os
											.getTransaccion(ConexionBDSql.obtener(),
													Integer.valueOf(
															table.getValueAt(table.getSelectedRow(), 0).toString()))
											.getIdproducto())
									.getImg());
					ImageIcon icon = new ImageIcon(
							i.getImage().getScaledInstance(Foto.getWidth(), Foto.getHeight(), Image.SCALE_DEFAULT));
					Foto.setIcon(icon);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
		getContentPane().add(Tipo_Transaccion);
		getContentPane().add(Register);
		getContentPane().add(Update);
		getContentPane().add(Delete);
		getContentPane().add(Return);
		getContentPane().add(Delete);
		getContentPane().add(Register);
		getContentPane().add(Return);
		getContentPane().add(Foto);
		getContentPane().add(GenerateReport);

	}

	private static void EscribirTabla() {
		try {
			for (int x = 0; x < (table.getRowCount() * 6); x++) {
				if (table.getRowCount() > 0)
					model.removeRow(0);
			}
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

	private void TransaccionesConFiltros(Connection conexion, String proveedornombre, String empleadonombre,
			String marca, String productonombre, String TipoTransaccion) throws SQLException {
		ListaTransacciones = new ArrayList<>();
		System.out.println("Proveedor: " + proveedornombre);
		System.out.println("Producto: " + productonombre);
		System.out.println("Empleado: " + empleadonombre);
		System.out.println("Marca: " + marca);
		System.out.println("TipoT: " + TipoTransaccion);
		if (!proveedornombre.equals("Proveedores") && !empleadonombre.equals("Empleados") && !marca.equals("Marcas")
				&& !productonombre.equals("Productos") && !TipoTransaccion.equals("TiposTransacciones") ) {
			System.out.println("1 IF");
			ListaTransacciones.clear();
			if(TipoTransaccion.equals("Exportacion")) {
			try {
				PreparedStatement consulta = conexion.prepareStatement(
						"SELECT idinventario, fecha, idproducto, idproveedor, cantidad, idempleado FROM transaccion WHERE idproveedor = (SELECT idproveedor FROM proveedor WHERE nombre = ?)"
								+ "AND idempleado = (SELECT idempleado FROM empleado WHERE username = ? )"
								+ "AND idproducto = (SELECT idproducto FROM producto WHERE marca = ? AND nombre = ?)"
			                 	+ "AND cantidad < 0");
				consulta.setString(1, proveedornombre);
				consulta.setString(2, empleadonombre);
				consulta.setString(3, marca);
				consulta.setString(4, productonombre);
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					ListaTransacciones.add(new Transaccion(resultado.getInt("idinventario"), resultado.getDate("fecha"),
							resultado.getInt("idproducto"), resultado.getInt("idproveedor"),
							resultado.getInt("cantidad"), resultado.getInt("idempleado")));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}else if (TipoTransaccion.equals("Importacion")) {
				try {
					PreparedStatement consulta = conexion.prepareStatement(
							"SELECT idinventario, fecha, idproducto, idproveedor, cantidad, idempleado FROM transaccion WHERE idproveedor = (SELECT idproveedor FROM proveedor WHERE nombre = ?)"
									+ "AND idempleado = (SELECT idempleado FROM empleado WHERE username = ? )"
									+ "AND idproducto = (SELECT idproducto FROM producto WHERE marca = ? AND nombre = ?)"
				                 	+ "AND cantidad > 0");
					consulta.setString(1, proveedornombre);
					consulta.setString(2, empleadonombre);
					consulta.setString(3, marca);
					consulta.setString(4, productonombre);
					ResultSet resultado = consulta.executeQuery();
					while (resultado.next()) {
						ListaTransacciones.add(new Transaccion(resultado.getInt("idinventario"), resultado.getDate("fecha"),
								resultado.getInt("idproducto"), resultado.getInt("idproveedor"),
								resultado.getInt("cantidad"), resultado.getInt("idempleado")));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		} else if (!proveedornombre.equals("Proveedores") || !empleadonombre.equals("Empleados")
				|| !marca.equals("Marcas") || !productonombre.equals("Productos")
				|| !TipoTransaccion.equals("TiposTransacciones")) {
			System.out.println("2 IF");
			ListaTransacciones.clear();
			if(TipoTransaccion.equals("Exportacion")) {
			try {
				PreparedStatement consulta = conexion.prepareStatement(
						"SELECT idinventario, fecha, idproducto, idproveedor, cantidad, idempleado FROM transaccion WHERE idproveedor = (SELECT idproveedor FROM proveedor WHERE nombre = " + proveedornombre + ")"
								+ "OR idempleado = (SELECT idempleado FROM empleado WHERE username = " + empleadonombre+ ")"
								+ "OR idproducto = (SELECT idproducto FROM producto WHERE marca = " + marca +" OR nombre = " + productonombre + ")"
								+ "OR idproveedor = (SELECT idproveedor FROM proveedor WHERE nombre = " + proveedornombre + ") AND idempleado = (SELECT idempleado FROM empleado WHERE username = " + empleadonombre + ")"
								+ "OR idproveedor = (SELECT idproveedor FROM proveedor WHERE nombre =  " + proveedornombre + ") AND idproducto = (SELECT idproducto FROM producto WHERE marca = " + marca +" OR nombre = " + productonombre +")"
								+ "OR idempleado = (SELECT idempleado FROM empleado WHERE username = " + empleadonombre+ " ) AND idproducto = (SELECT idproducto FROM producto WHERE marca = " + marca +" OR nombre = " +productonombre + ")"
								+ "AND cantidad < 0");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					ListaTransacciones.add(new Transaccion(resultado.getInt("idinventario"), resultado.getDate("fecha"),
							resultado.getInt("idproducto"), resultado.getInt("idproveedor"),
							resultado.getInt("cantidad"), resultado.getInt("idempleado")));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}else if (TipoTransaccion.equals("Importacion")) {
				try {
					PreparedStatement consulta = conexion.prepareStatement(
							"SELECT idinventario, fecha, idproducto, idproveedor, cantidad, idempleado FROM transaccion WHERE idproveedor = (SELECT idproveedor FROM proveedor WHERE nombre = " + proveedornombre + ")"
									+ "OR idempleado = (SELECT idempleado FROM empleado WHERE username = " + empleadonombre+ ")"
									+ "OR idproducto = (SELECT idproducto FROM producto WHERE marca = " + marca +" OR nombre = " + productonombre + ")"
									+ "OR idproveedor = (SELECT idproveedor FROM proveedor WHERE nombre = " + proveedornombre + ") AND idempleado = (SELECT idempleado FROM empleado WHERE username = " + empleadonombre + ")"
									+ "OR idproveedor = (SELECT idproveedor FROM proveedor WHERE nombre =  " + proveedornombre + ") AND idproducto = (SELECT idproducto FROM producto WHERE marca = " + marca +" OR nombre = " + productonombre +")"
									+ "OR idempleado = (SELECT idempleado FROM empleado WHERE username = " + empleadonombre+ " ) AND idproducto = (SELECT idproducto FROM producto WHERE marca = " + marca +" OR nombre = " +productonombre + ")"
									+ "AND cantidad > 0");
					ResultSet resultado = consulta.executeQuery();
					while (resultado.next()) {
						ListaTransacciones.add(new Transaccion(resultado.getInt("idinventario"), resultado.getDate("fecha"),
								resultado.getInt("idproducto"), resultado.getInt("idproveedor"),
								resultado.getInt("cantidad"), resultado.getInt("idempleado")));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (proveedornombre.equals("Proveedores") && empleadonombre.equals("Empleados") && marca.equals("Marcas")
				&& productonombre.equals("Productos") && TipoTransaccion.equals("TiposTransacciones")) {
			try {
				ListaTransacciones = Test.os.getAllTransacciones(ConexionBDSql.obtener());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EscribirTabla();
		}
		EscribirTabla();
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
		System.out.println("Producto: " + product);
		return product;
	}

	private static void EscribirTexto(String... Filtros) {
		try {
			BufferedWriter bw;
			if (!Filtros.toString().isEmpty()) {
				bw = new BufferedWriter(
						new FileWriter("Reports/TransaccionesConFiltros" + Arrays.toString(Filtros) + ".txt"));
			} else {
				bw = new BufferedWriter(new FileWriter("Reports/Transacciones.txt"));
			}
			for (Transaccion t : ListaTransacciones) {
				bw.write(t.toString() + "\n");
			}
			bw.close();
			JOptionPane.showMessageDialog(null, "Informe Generado ", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
