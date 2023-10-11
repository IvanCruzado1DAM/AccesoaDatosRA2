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
import javax.swing.JOptionPane;
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

	private JLabel NombreTabla, Foto;
	private static JTableBloqueoCeldas model;
	private static JTable table;
	private static JComboBox<String> Filtro_Empleado, Filtro_Proveedor, Filtro_Marca, Filtro_Producto;
	private List<String> Empleado = new ArrayList<>();
	private List<String> Proveedor = new ArrayList<>();
	private List<String> Marca = new ArrayList<>();
	private List<String> Nombre = new ArrayList<>();
	private JButton Register, Delete, Update, Return;
	protected static Transaccion t ;

	public JFrameTransactions() {
		super("Transactions");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1159, 699);
		setResizable(false);
		setLocationRelativeTo(null);

		NombreTabla = new JLabel("Transactions");
		NombreTabla.setBounds(26, 19, 122, 50);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 79, 806, 439);

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
		Register.setBounds(853, 568, 120, 60);
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
		Foto.setBounds(863, 79, 258, 287);

		Delete = new JButton("Eliminar");
		Delete.setBounds(618, 568, 120, 60);
		Delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Test.os.removeTransaccion(ConexionBDSql.obtener(), Test.os.getTransaccion(ConexionBDSql.obtener(), Integer.valueOf(model.getValueAt(table.getSelectedRow(), 0).toString())));
				    JOptionPane.showMessageDialog(JFrameTransactions.this, "Transaccion Eliminada Correctamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				    EscribirTabla();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Update = new JButton("Actualizar");
		Update.setBounds(371, 568, 120, 60);
		Update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					t = Test.os.getTransaccion(ConexionBDSql.obtener(), Integer.valueOf(model.getValueAt(table.getSelectedRow(), 0).toString()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch (ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(JFrameTransactions.this, "Seleccione una transaccion para Actualizar", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
                dispose();
                JFrameUpdateTransaction jf = new JFrameUpdateTransaction();
                jf.setVisible(true);
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
		Filtro_Producto.setBounds(696, 18, 180, 50);
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
		Filtro_Marca.setBounds(491, 19, 180, 50);
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
		getContentPane().add(Return);
		getContentPane().add(Delete);
		getContentPane().add(Register);
		getContentPane().add(Return);
		getContentPane().add(Foto);

	}

	public static void EscribirTabla() {
		try {
			for (int x = 0; x < (table.getRowCount() * Test.os.getAllTransacciones(ConexionBDSql.obtener()).size()); x++) {
				if (table.getRowCount() > 0)
					model.removeRow(0);
			}
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
		if(!nombre.equals("Ninguno")) {
		try {
			PreparedStatement consulta = conexion.prepareStatement("SELECT idinventario, fecha, idproducto, idproveedor, cantidad, idempleado FROM transaccion WHERE idproveedor = (SELECT idproveedor FROM proveedor WHERE nombre = ?)");
			consulta.setString(1, nombre);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaTransacciones.add(new Transaccion(resultado.getInt("idinventario"), resultado.getDate("fecha"),
						resultado.getInt("idproducto"), resultado.getInt("idproveedor"), resultado.getInt("cantidad"),
						resultado.getInt("idempleado")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		
		try {
			for (int x = 0; x < (table.getRowCount() * ListaTransacciones.size()); x++) {
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
		}else {
			EscribirTabla();
		}
	}
}
