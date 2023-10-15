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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mysql.cj.x.protobuf.MysqlxResultset.Row;

import models.Empleado;
import models.Producto;
import models.Proveedor;
import models.Transaccion;
import services.ConexionBDSql;
import services.Test;

public class JFrameTransactions extends JFrame {

	private JLabel NameTable, Picture;
	// Clase modificada para bloquear celdas del jtable
	private static JTableBloqueoCeldas model;
	private static JTable table;
	private static JComboBox<String> Filter_Employee, Filter_Supplier, Filter_Brand, Filter_Product, Transaction_Type;
	private List<String> Employee = new ArrayList<>();
	private List<String> Supplier = new ArrayList<>();
	private List<String> Brand = new ArrayList<>();
	private List<String> Name = new ArrayList<>();
	private JButton Register, Delete, Update, Return, GenerateReport;
	private final Icon IconRegister = new ImageIcon("icons/IconRegister.png"),
			IconDelete = new ImageIcon("icons/IconDelete.png"), IconUpdate = new ImageIcon("icons/IconUpdate.png"),
			IconReturn = new ImageIcon("icons/IconReturn.png"),
			IconGenerateReport = new ImageIcon("icons/IconDocument.png");
	// Declaramos como static y protected para poder acceder a ella en las otras
	// clases
	protected static Transaccion t;
	private static String fEmployee = "Empleados", fSupplier = "Proveedores", fBrand = "brands", fProduct = "Productos",
			tTransaction = "TiposTransacciones";
	private static List<Transaccion> ListaTransacciones = new ArrayList<>();
	protected static Date date;

	// Para dar un formato con horas, minutos y segundos a fecha (ademas de dia,
	// año, mes)
	protected static DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss z");

	public JFrameTransactions() {
		super("Transacciones | Empleado : " + JFrameLogin.EmActivo.getUsername());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1159, 699);
		setResizable(false);
		setLocationRelativeTo(null);

		// Con estas dos lineas conseguimos tener el icono en e logo de la app cuando se
		// ejecuta
		ImageIcon IconTransaction = new ImageIcon("icons/IconTransactions.png");
		this.setIconImage(IconTransaction.getImage());
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

		NameTable = new JLabel("Transacciones");
		NameTable.setBounds(10, 19, 122, 50);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 79, 806, 439);

		table = new JTable();
		model = new JTableBloqueoCeldas();
		// Permite que ordenemos de arriba a abajo las columnas, dando en el nombre de
		// esta
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
		// Ajustamos el tamaño de jtable, en filas y en la columna de fecha
		table.setRowHeight(30);
		table.getColumnModel().getColumn(6).setPreferredWidth(180);
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

		Register = new JButton("Registrar");
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

		Picture = new JLabel("");
		Picture.setBounds(863, 79, 258, 287);
		Delete = new JButton("Eliminar");
		Delete.setBounds(507, 568, 140, 60);
		Delete.setIcon(IconDelete);
		Delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					// Recogemos la transaccion a eliminar
					t = Test.os.getTransaccion(ConexionBDSql.obtener(),
							Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString()));
					// Recuperamos el producto para reestablecer su valor al de antes de la
					// transaccion
					Producto p = Test.os
							.getProduct(
									ConexionBDSql.obtener(), Test.os
											.getTransaccion(ConexionBDSql.obtener(),
													Integer.valueOf(
															model.getValueAt(table.getSelectedRow(), 0).toString()))
											.getIdproducto());
					p.setStock(p.getStock() - t.getCantidad());
					// Eliminamos la transaccion
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
					// Recuperamos la transaccion seleccionada en el jtable con el id
					t = Test.os.getTransaccion(ConexionBDSql.obtener(),
							Integer.valueOf(model.getValueAt(table.getSelectedRow(), 0).toString()));
					// Obtenemos la fecha de la transaccion para, poder recuperar en la siguiente
					// clase
					date = Test.os.getTransaccion(ConexionBDSql.obtener(),
							Integer.valueOf(model.getValueAt(table.getSelectedRow(), 0).toString())).getFecha();
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
				ExcelCreator.main(fEmployee, fSupplier, fBrand, fProduct, tTransaction);
			}
		});

		Filter_Employee = new JComboBox<String>();
		Filter_Employee
				.setFont(new Font("Arial", Filter_Employee.getFont().getStyle(), Filter_Employee.getFont().getSize()));
		Filter_Employee.setBounds(111, 18, 180, 50);
		Employee = new ArrayList<>();
		Filter_Employee.addItem("Empleados");
		Filter_Employee.setSelectedIndex(0);
		try {
			// Rellenamos el filtro de empleado con el nombre para que sea mas facil , sin
			// que se repita
			for (Empleado em : Test.os.getAllEmpleados(ConexionBDSql.obtener())) {
				String m = em.getUsername();
				if (!Employee.contains(m)) {
					Employee.add(m);
					Filter_Employee.addItem(m);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Filter_Employee.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					// Pasa los diferentes filtros, que son static y estan declarados arriba para
					// poder acceder a ellos en toda la clase
					fEmployee = (String) Filter_Employee.getSelectedItem();
					TransaccionesConFiltros(ConexionBDSql.obtener(), fSupplier, fEmployee, fBrand, fProduct,
							tTransaction);
				} catch (NumberFormatException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Filter_Supplier = new JComboBox<String>();
		Filter_Supplier
				.setFont(new Font("Arial", Filter_Supplier.getFont().getStyle(), Filter_Supplier.getFont().getSize()));
		Filter_Supplier.setBounds(301, 19, 180, 50);
		Supplier = new ArrayList<>();
		Filter_Supplier.addItem("Proveedores");
		Filter_Supplier.setSelectedIndex(0);
		try {
			// Rellenamos el combobox de proveedor con su nombre para filtrar con ellos, sin
			// que se repitan
			for (Proveedor p : Test.os.getAllProveedor(ConexionBDSql.obtener())) {
				String m = p.getNombre();
				if (!Supplier.contains(m)) {
					Supplier.add(m);
					Filter_Supplier.addItem(m);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Filter_Supplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					// Pasa los diferentes filtros, que son static y estan declarados arriba para
					// poder acceder a ellos en toda la clase
					fSupplier = (String) Filter_Supplier.getSelectedItem();
					TransaccionesConFiltros(ConexionBDSql.obtener(), fSupplier, fEmployee, fBrand, fProduct,
							tTransaction);
				} catch (NumberFormatException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Filter_Product = new JComboBox<String>();
		Filter_Product
				.setFont(new Font("Arial", Filter_Product.getFont().getStyle(), Filter_Product.getFont().getSize()));
		Filter_Product.setBounds(696, 18, 180, 50);
		Name = new ArrayList<>();
		Filter_Product.addItem("Productos");
		Filter_Product.setSelectedIndex(0);
		try {
			// Rellenamos el combobox de productos con su nombre para filtrar con ellos, sin
			// que repita
			for (Producto p : Test.os.getAllProducts(ConexionBDSql.obtener())) {
				String n = p.getNombre();
				if (!Name.contains(n)) {
					Name.add(n);
					Filter_Product.addItem(n);
				}
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Filter_Product.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					// Pasa los diferentes filtros, que son static y estan declarados arriba para
					// poder acceder a ellos en toda la clase
					fProduct = (String) Filter_Product.getSelectedItem();
					TransaccionesConFiltros(ConexionBDSql.obtener(), fSupplier, fEmployee, fBrand, fProduct,
							tTransaction);
				} catch (NumberFormatException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Filter_Brand = new JComboBox<String>();
		Filter_Brand.setFont(new Font("Arial", Filter_Brand.getFont().getStyle(), Filter_Brand.getFont().getSize()));
		Filter_Brand.setBounds(506, 19, 180, 50);
		Brand = new ArrayList<>();
		Filter_Brand.addItem("Marcas");
		Filter_Brand.setSelectedIndex(0);
		try {
			// Rellenamos el combobox de marcas para filtrar con ellas, sin que se repita
			for (Producto p : Test.os.getAllProducts(ConexionBDSql.obtener())) {
				String m = p.getMarca();
				if (!Brand.contains(m)) {
					Brand.add(m);
					Filter_Brand.addItem(m);
				}
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Filter_Brand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					// Pasa los diferentes filtros, que son static y estan declarados arriba para
					// poder acceder a ellos en toda la clase
					fBrand = (String) Filter_Brand.getSelectedItem();
					TransaccionesConFiltros(ConexionBDSql.obtener(), fSupplier, fEmployee, fBrand, fProduct,
							tTransaction);
				} catch (NumberFormatException | ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Transaction_Type = new JComboBox<String>();
		Transaction_Type.setFont(
				new Font("Arial", Transaction_Type.getFont().getStyle(), Transaction_Type.getFont().getSize()));
		Transaction_Type.setBounds(902, 19, 180, 50);
		Transaction_Type.addItem("TiposTransacciones");
		Transaction_Type.addItem("Exportacion");
		Transaction_Type.addItem("Importacion");
		Transaction_Type.setSelectedIndex(0);
		Transaction_Type.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					// Pasa los diferentes filtros, que son static y estan declarados arriba para
					// poder acceder a ellos en toda la clase
					tTransaction = (String) Transaction_Type.getSelectedItem();
					TransaccionesConFiltros(ConexionBDSql.obtener(), fSupplier, fEmployee, fBrand, fProduct,
							tTransaction);
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
					ImageIcon icon = new ImageIcon(i.getImage().getScaledInstance(Picture.getWidth(),
							Picture.getHeight(), Image.SCALE_DEFAULT));
					Picture.setIcon(icon);
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
		getContentPane().add(NameTable);
		getContentPane().add(Filter_Employee);
		getContentPane().add(Filter_Supplier);
		getContentPane().add(Filter_Brand);
		getContentPane().add(Filter_Product);
		getContentPane().add(Transaction_Type);
		getContentPane().add(Register);
		getContentPane().add(Update);
		getContentPane().add(Delete);
		getContentPane().add(Return);
		getContentPane().add(Delete);
		getContentPane().add(Register);
		getContentPane().add(Return);
		getContentPane().add(Picture);
		getContentPane().add(GenerateReport);

	}

	private static void EscribirTabla() {
		try {
			while (model.getRowCount() > 0) {
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
				Fila[6] = String.valueOf(dateFormat.format(t.getFecha()));
				model.addRow(Fila);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void TransaccionesConFiltros(Connection conexion, String suppliername, String employeename, String brand,
			String productname, String TransactionType) throws SQLException {
		ListaTransacciones = new ArrayList<>();
		String sql = null;
		// Cuando no elegimos nada los valores puesto son estos, lo cual serian nulos
		if (suppliername.equals("Proveedores"))
			suppliername = null;
		if (employeename.equals("Empleados"))
			employeename = null;
		if (brand.equals("brands"))
			brand = null;
		if (productname.equals("Productos"))
			productname = null;
		if (TransactionType.equals("Exportacion")) {
			// Filtramos primero con if para separar si es exportacion o importacion,
			// mediante Join unimos las tablas y colocamos un identicador(apodo)
			// a las tablas para acortar, como el valor puede ser nulo(no elegir nada en el
			// filtro) colocamos esa condicion o si la elegido, puede ser
			// filtro o varios
			sql = "SELECT t.* FROM transaccion t " + "JOIN producto p ON t.idproducto = p.idproducto "
					+ "JOIN empleado e ON t.idempleado = e.idempleado "
					+ "JOIN proveedor pr ON t.idproveedor = pr.idproveedor " + "WHERE (p.nombre = ? OR ? IS NULL ) "
					+ "AND (p.marca = ? OR ? IS NULL) " + "AND (e.username = ? OR ? IS NULL) "
					+ "AND (pr.nombre = ? OR ? IS NULL)" + "AND cantidad < 0";
		} else if (TransactionType.equals("Importacion")) {
			sql = "SELECT t.* FROM transaccion t " + "JOIN producto p ON t.idproducto = p.idproducto "
					+ "JOIN empleado e ON t.idempleado = e.idempleado "
					+ "JOIN proveedor pr ON t.idproveedor = pr.idproveedor " + "WHERE (p.nombre = ? OR ? IS NULL ) "
					+ "AND (p.marca = ? OR ? IS NULL) " + "AND (e.username = ? OR ? IS NULL) "
					+ "AND (pr.nombre = ? OR ? IS NULL)" + "AND cantidad > 0";
		} else if (TransactionType.equals("TiposTransacciones")) {
			sql = "SELECT t.* FROM transaccion t " + "JOIN producto p ON t.idproducto = p.idproducto "
					+ "JOIN empleado e ON t.idempleado = e.idempleado "
					+ "JOIN proveedor pr ON t.idproveedor = pr.idproveedor " + "WHERE (p.nombre = ? OR ? IS NULL ) "
					+ "AND (p.marca = ? OR ? IS NULL) " + "AND (e.username = ? OR ? IS NULL) "
					+ "AND (pr.nombre = ? OR ? IS NULL)";
		}
		try {
			PreparedStatement consulta = conexion.prepareStatement(sql);
			consulta.setString(1, productname);
			consulta.setString(2, productname);
			consulta.setString(3, brand);
			consulta.setString(4, brand);
			consulta.setString(5, employeename);
			consulta.setString(6, employeename);
			consulta.setString(7, suppliername);
			consulta.setString(8, suppliername);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaTransacciones.add(new Transaccion(resultado.getInt("idinventario"), resultado.getDate("fecha"),
						resultado.getInt("idproducto"), resultado.getInt("idproveedor"), resultado.getInt("cantidad"),
						resultado.getInt("idempleado")));
			}
		} catch (Exception e) {

		}
		EscribirTabla();
	}

	// Obtenemos el producto por el nombre para poder poner el nombre simplemente en
	// tabla
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

			bw.close();
			JOptionPane.showMessageDialog(null, "Informe Generado ", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class ExcelCreator {
		public static void main(String... Filtros) {
			Workbook workbook = new XSSFWorkbook(); // Crear nuevo libro de trabajo
			Sheet sheet = (Sheet) workbook.createSheet("Informe"); // Crear nueva hoja

			org.apache.poi.ss.usermodel.Row row = sheet.createRow(0); // Crear nueva fila
			Cell cell = row.createCell(0); // Crear nueva celda en la fila
			cell.setCellValue("Empleados"); // Establecer valor de la celda
			Cell cell2 = row.createCell(1);
			cell2.setCellValue("Proveedores");
			Cell cell3 = row.createCell(2);
			cell3.setCellValue("Marcas");
			Cell cell4 = row.createCell(3);
			cell4.setCellValue("Producto");
			Cell cell5 = row.createCell(4);
			cell5.setCellValue("Cantidad");
			Cell cell6 = row.createCell(5);
			cell6.setCellValue("TipoTransaccion");
			int cont = 0;
			for (Transaccion t : ListaTransacciones) {
				row = sheet.createRow(++cont);
				cell = row.createCell(0); // Crear nueva celda en la fila
				try {
					cell.setCellValue(Test.os.getEmpleado(ConexionBDSql.obtener(), t.getIdempleado()).getUsername());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Establecer valor de la celda
				cell2 = row.createCell(1);
				try {
					cell2.setCellValue(Test.os.getProveedor(ConexionBDSql.obtener(), t.getIdproveedor()).getNombre());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cell3 = row.createCell(2);
				try {
					cell3.setCellValue(Test.os.getProduct(ConexionBDSql.obtener(), t.getIdproducto()).getMarca());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cell4 = row.createCell(3);
				try {
					cell4.setCellValue(Test.os.getProduct(ConexionBDSql.obtener(), t.getIdproducto()).getNombre());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cell5 = row.createCell(4);
				cell5.setCellValue(t.getCantidad());
				cell6 = row.createCell(5);
				if(t.getCantidad()>0)
				cell6.setCellValue("Importacion");
				else
				cell6.setCellValue("Exportacion");
			}

			try (FileOutputStream outputStream = new FileOutputStream(
					"Reports/TransaccionesConFiltros" + Arrays.toString(Filtros) + ".xlsx")) {
				workbook.write(outputStream); // Escribir libro de trabajo en el archivo
               JOptionPane.showMessageDialog(null, "Informe Creado");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}