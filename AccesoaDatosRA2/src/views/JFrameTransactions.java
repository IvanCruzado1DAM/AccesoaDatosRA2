package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.awt.BorderLayout;

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
	private JButton Register, Delete, Update;

	public JFrameTransactions() {
		super("Transactions");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(879, 581);
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
		
		Register = new JButton ("Resgistrar");
		Register.setBounds(134, 478, 110, 39);
		Register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Foto = new JLabel ("Foto");
		Idtransaction = new JLabel ("Idtransaction");
		empleado = new JLabel ("Empleado");
		proveedor = new JLabel ("Proveedor");
		marca = new JLabel ("Marca");
		producto = new JLabel ("Nombre");
		cantidad = new JLabel ("Cantidad");
		fecha = new JLabel ("Fecha");
		
		Idtransactiontext = new JTextField (10);
		empleadotext = new JTextField (10);
		proveedortext = new JTextField (10);
		marcatext = new JTextField (10);
		productotext = new JTextField (10);
		cantidadtext = new JTextField (10);
		fechatext = new JTextField (10);
		
		Delete = new JButton ("Eliminar");
		Delete.setBounds(502, 478, 271, 39);
		Delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Update = new JButton ("Actualizar");
		Update.setBounds(305, 478, 132, 39);
		Update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		Filtro_Empleado = new JComboBox<String>();
		Filtro_Empleado
				.setFont(new Font("Arial", Filtro_Empleado.getFont().getStyle(), Filtro_Empleado.getFont().getSize()));
		Filtro_Empleado.setBounds(100, 19, 180, 50);
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
		Filtro_Proveedor.setFont(new Font("Arial", Filtro_Marca.getFont().getStyle(), Filtro_Marca.getFont().getSize()));
		Filtro_Proveedor.setBounds(673, 19, 180, 50);
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
		
		Filtro_Marca= new JComboBox <String> ();
		Filtro_Marca.setFont(new Font("Arial", Filtro_Marca.getFont().getStyle(), Filtro_Marca.getFont().getSize()));
		Filtro_Marca.setBounds(295, 18, 180, 50);
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
		
		Filtro_Producto= new JComboBox <String> ();
		Filtro_Producto.setFont(new Font("Arial", Filtro_Producto.getFont().getStyle(), Filtro_Producto.getFont().getSize()));
		Filtro_Producto.setBounds(295, 18, 180, 50);
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
		 getContentPane().add(producto);
		 getContentPane().add(fecha);
		 getContentPane().add(Idtransactiontext);
		 getContentPane().add(proveedortext);
		 getContentPane().add(empleadotext);
		 getContentPane().add(productotext);
		 getContentPane().add(fechatext);
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
}
